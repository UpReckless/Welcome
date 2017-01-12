package com.welcome.studio.welcome.app;

import android.app.Application;

import com.welcome.studio.welcome.model.NetworkModule;
import com.welcome.studio.welcome.util.UtilsModule;


public class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent=buildComponent();
        appComponent.inject(this);
//        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
//            @Override
//            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
//                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
//            }
//
//            @Override
//            public void cancel(ImageView imageView) {
//                Picasso.with(imageView.getContext()).cancelRequest(imageView);
//            }
//
//            @Override
//            public Drawable placeholder(Context ctx, String tag) {
//                if (DrawerImageLoader.Tags.PROFILE.name().equals(tag))
//                    return DrawerUIUtils.getPlaceHolder(ctx);
//                return super.placeholder(ctx,tag);
//            }
//        });
    }

    public static AppComponent getComponent(){
        return appComponent;
    }

    protected AppComponent buildComponent(){
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .utilsModule(new UtilsModule(this))
                .build();
    }
}
