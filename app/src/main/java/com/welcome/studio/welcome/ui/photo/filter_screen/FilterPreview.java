package com.welcome.studio.welcome.ui.photo.filter_screen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.ui.BaseMainFragment;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.Layout;
import com.welcome.studio.welcome.ui.photo.PhotoModule;
import com.welcome.studio.welcome.app.Injector;
import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.imageprocessors.Filter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Royal on 11.02.2017. !
 */
@Layout(id= R.layout.fragment_filter_preview)
public class FilterPreview extends BaseMainFragment implements FilterPreviewView, ThumbnailCallback {

    @Inject
    FilterPresenter presenter;
    @Inject
    Context context;

    @Bind(R.id.thumbnails)
    RecyclerView thumbnailsView;
    @Bind(R.id.img_place_holder)
    ImageView imgPlaceHolder;

    private String photoPath;
    private Bitmap outputImage;

    static {
        System.loadLibrary("NativeImageProcessor");
    }

    public static FilterPreview newInstance(String photoPath){
        FilterPreview filterPreview=new FilterPreview();
        Bundle args=new Bundle();
        args.putString("path",photoPath);
        filterPreview.setArguments(args);
        return filterPreview;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoPath=getArguments().getString("path");
    }

    @Override
    protected Object getRouter() {
        return getParentFragment();
    }

    @NonNull
    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public String getFragmentTag() {
        return getTag();
    }

    @Override
    protected void inject() {
        Injector.getInstance().plus(new PhotoModule()).inject(this);
    }

    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    protected String getToolbarTitle() {
        return null;
    }

    @OnClick(R.id.btn_next)
    public void onBtnNextClick(){
        presenter.next(outputImage);
    }

    @Override
    public void onThumbnailClick(Filter filter) {
        Bitmap bitmap=Bitmap.createScaledBitmap(BitmapFactory.decodeFile(photoPath),640,640,false);
        outputImage=filter.processFilter(bitmap);
        imgPlaceHolder.setImageBitmap(outputImage);
    }

    @Override
    public void initUiWidgets() {
        outputImage=Bitmap.createScaledBitmap(BitmapFactory.decodeFile(photoPath),640,640,false);
        imgPlaceHolder.setImageBitmap(outputImage);
        initHorizontalList();
        bindDataToAdapter();
    }
    private void initHorizontalList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.scrollToPosition(0);
        thumbnailsView.setLayoutManager(layoutManager);
        thumbnailsView.setHasFixedSize(true);
    }

    private void bindDataToAdapter() {
        Handler handler = new Handler();
        Runnable r = () -> {
            Bitmap thumbImage = BitmapFactory.decodeFile(photoPath);
            ThumbnailItem t1 = new ThumbnailItem();
            ThumbnailItem t2 = new ThumbnailItem();
            ThumbnailItem t3 = new ThumbnailItem();
            ThumbnailItem t4 = new ThumbnailItem();
            ThumbnailItem t5 = new ThumbnailItem();
            ThumbnailItem t6 = new ThumbnailItem();

            t1.image = thumbImage;
            t2.image = thumbImage;
            t3.image = thumbImage;
            t4.image = thumbImage;
            t5.image = thumbImage;
            t6.image = thumbImage;
            ThumbnailsManager.clearThumbs();
            ThumbnailsManager.addThumb(t1); // Original Image

            t2.filter = SampleFilters.getStarLitFilter();
            ThumbnailsManager.addThumb(t2);

            t3.filter = SampleFilters.getBlueMessFilter();
            ThumbnailsManager.addThumb(t3);

            t4.filter = SampleFilters.getAweStruckVibeFilter();
            ThumbnailsManager.addThumb(t4);

            t5.filter = SampleFilters.getLimeStutterFilter();
            ThumbnailsManager.addThumb(t5);

            t6.filter = SampleFilters.getNightWhisperFilter();
            ThumbnailsManager.addThumb(t6);

            List<ThumbnailItem> thumbs = ThumbnailsManager.processThumbs(context);

            ThumbnailsAdapter adapter = new ThumbnailsAdapter(thumbs, this);
            thumbnailsView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        };
        handler.post(r);
    }
}
