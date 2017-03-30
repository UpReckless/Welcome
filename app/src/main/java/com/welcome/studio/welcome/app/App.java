package com.welcome.studio.welcome.app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.widget.ImageView;

import com.devs.acr.AutoErrorReporter;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.welcome.studio.welcome.BuildConfig;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.model.NetworkModule;
import com.welcome.studio.welcome.util.CircleTransform;
import com.welcome.studio.welcome.util.UtilsModule;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;


public class App extends MultiDexApplication {

    private static AppComponent appComponent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (!BuildConfig.DEBUG)
            AutoErrorReporter.get(this)
                    .setEmailAddresses("www.roal@mail.ru")
                    .setEmailSubject("Error from wlcome")
                    .start();
        appComponent = buildComponent();
        appComponent.inject(this);
        initPicasso();
        initDrawerProfile();
    }

    public static AppComponent getComponent() {
        return appComponent;
    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .utilsModule(new UtilsModule())
                .build();
    }

    private void initPicasso() {
        final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR= chain -> chain.proceed(chain.request())
                .newBuilder()
                .header("Cache-Control",String.format("max-age=%d, only-if-cached, max-stale=%d",120,0))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(new Cache(getCacheDir(), 50 * 1024 * 1024))
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .build();
        OkHttp3Downloader okHttp3Downloader = new OkHttp3Downloader(okHttpClient);
        Picasso picasso = new Picasso.Builder(this)
                .indicatorsEnabled(true)
                .downloader(okHttp3Downloader)
                .build();
        Picasso.setSingletonInstance(picasso);
    }

    private void initDrawerProfile(){
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(getApplicationContext()).load(uri).networkPolicy(NetworkPolicy.OFFLINE)
                        .placeholder(placeholder)
                        .transform(new CircleTransform())
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                Picasso.with(getApplicationContext()).load(uri).error(R.mipmap.img_avatar).transform(new CircleTransform()).into(imageView);
                            }
                        });
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(getApplicationContext()).cancelRequest(imageView);
            }
        });
    }
}
