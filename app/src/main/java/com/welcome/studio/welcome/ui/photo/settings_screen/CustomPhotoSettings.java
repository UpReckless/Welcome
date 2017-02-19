package com.welcome.studio.welcome.ui.photo.settings_screen;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.squareup.picasso.Picasso;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.app.Injector;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.ui.BaseMainFragment;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.Layout;
import com.welcome.studio.welcome.ui.photo.PhotoModule;
import com.welcome.studio.welcome.util.tagview.Tag;
import com.welcome.studio.welcome.util.tagview.TagsView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by Royal on 12.02.2017. !
 */
@Layout(id = R.layout.fragment_custom_photo_settings)
public class CustomPhotoSettings extends BaseMainFragment implements CustomPhotoSettingsView {


    @Inject
    CustomPhotoSettingsPresenter presenter;
    @Bind(R.id.edt_description)
    EditText edtDescription;
    @Bind(R.id.txt_time)
    TextView txtTime;
    @Bind(R.id.txt_place)
    TextView txtPlace;
    @Bind(R.id.chb_dress)
    CheckBox chbDressCode;
    @Bind(R.id.img_picture)
    ImageView imgPicture;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.tag_view)
    TagsView tagsView;

    private TagAdapter tagAdapter;

    public static CustomPhotoSettings newInstance(Post post) {
        CustomPhotoSettings customPhotoSettings = new CustomPhotoSettings();
        Bundle args = new Bundle();
        args.putSerializable("post", post);
        customPhotoSettings.setArguments(args);
        return customPhotoSettings;
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

    @OnClick(R.id.btn_share)
    public void onBtnShareClick() {
        presenter.btnShareClick();
    }

    @Override
    public void initUi() {
        Post post = (Post) getArguments().getSerializable("post");
        assert post != null;
        txtPlace.setText(post.getAddress());
        txtTime.setText(new SimpleDateFormat().format(new Date(post.getTime())));
        tagsView.setOnTagDeleteListener(((position, tag) -> {
            tag.isDeletable=false;
            tagAdapter.addTag(tag);
        }));
        Picasso.with(getContext()).load(new File(post.getContentPath())).into(imgPicture);
        setTagAdapter();
    }

    @Override
    public void showTag(Tag tag) {
        tagsView.addTag(tag);
    }

    @Override
    public void openDialog() {
        String[] tagsName=new String[tagsView.getTags().size()];
        for (int i=0;i<tagsView.getTags().size();i++)
            tagsName[i]=tagsView.getTags().get(i).text;
        TimePickerDialog.Builder builder=new TimePickerDialog.Builder(getContext());
        TimePicker timePicker=new TimePicker(getContext());
        builder.setTitle("Время окончания")
                .setView(timePicker)
                .setCancelable(false)
                .setPositiveButton("Share",(dialog, which) ->
                        presenter.share(edtDescription.getText().toString(),chbDressCode.isChecked(),tagsName,timePicker.getHour(),timePicker.getMinute()))
                .create()
                .show();
    }

    private void setTagAdapter() {
        List<Tag> tags = new ArrayList<>();
        tags.add(createTag("#нямка", "#ffffff", "#dddddd", "#555555", "#ffffff"));
        tags.add(createTag("#туса", "#ffffff", "#dddddd", "#555555", "#ffffff"));
        tags.add(createTag("#простопосидеть", "#ffffff", "#dddddd", "#555555", "#ffffff"));
        tags.add(createTag("#халява", "#ffffff", "#dddddd", "#555555", "#ffffff"));
        tags.add(createTag("#ништяки", "#ffffff", "#dddddd", "#555555", "#ffffff"));
        tags.add(createTag("#экстрим", "#ffffff", "#dddddd", "#555555", "#ffffff"));
        tags.add(createTag("#чтотодругое", "#ffffff", "#dddddd", "#555555", "#ffffff"));
        tagAdapter = new TagAdapter(tags, presenter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(tagAdapter);
    }

    private Tag createTag(String tagName, String textColor, String layoutColor, String pressColor, String borderColor) {
        Tag tag = new Tag(tagName);
        tag.tagTextColor = Color.parseColor(textColor);
        tag.layoutColor = Color.parseColor(layoutColor);
        tag.layoutColorPress = Color.parseColor(pressColor);
        tag.layoutBorderColor = Color.parseColor(borderColor);
        tag.radius = 20f;
        tag.tagTextSize = 14f;
        tag.layoutBorderSize = 1f;
        return tag;
    }
}
