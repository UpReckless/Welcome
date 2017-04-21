package com.welcome.studio.welcome.squarecamera_mock;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.app.Injector;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.model.entity.Author;
import com.welcome.studio.welcome.model.repository.LocationRepository;
import com.welcome.studio.welcome.model.repository.UserRepository;
import com.welcome.studio.welcome.util.Constance;
import com.welcome.studio.welcome.util.tagview.OnTagClickListener;
import com.welcome.studio.welcome.util.tagview.Tag;
import com.welcome.studio.welcome.util.tagview.TagsView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by @mistreckless on 17.03.2017. !
 */

public class CustomPhotoSettingsFragment extends Fragment implements OnTagClickListener {
    @Bind(R.id.edt_description)
    EditText edtDescription;
    @Bind(R.id.txt_time)
    TextView txtTime;
    @Bind(R.id.txt_place)
    TextView txtPlace;
    @Bind(R.id.chb_dress)
    CheckBox chbDressCode;
    @Bind(R.id.img_picture)
    SquareImageView imgPicture;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.tag_view)
    TagsView tagsView;

    @Inject
    UserRepository userRepository;
    @Inject
    LocationRepository locationRepository;

    private TagAdapter tagAdapter;
    private String photoPath;
    private String address;
    private boolean shareBtnEnabled;
    private double lat;
    private double lon;

    public static CustomPhotoSettingsFragment newInstance(Uri uri) {
        CustomPhotoSettingsFragment fragment = new CustomPhotoSettingsFragment();
        Bundle args = new Bundle();
        args.putString("path", uri.getPath());
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_custom_photo_settings, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Injector.getInstance().plus(new PhotoModule()).inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void onStart() {
        super.onStart();
        initUi();
    }

    private void initUi() {
        photoPath = getArguments().getString("path");
        if (photoPath == null) getActivity().onBackPressed();
        Picasso.with(getContext()).load(new File(photoPath)).into(imgPicture);
        txtTime.setText(new SimpleDateFormat().format(new Date(System.currentTimeMillis())));
        initTagAdapter();
        tagsView.setOnTagDeleteListener(((position, tag) -> {
            tag.isDeletable = false;
            tagAdapter.addTag(tag);
        }));
        findAddress();
    }

    private void findAddress() {
        locationRepository.getLastKnownLocation()
                .observeOn(Schedulers.io())
                .flatMap(locationRepository::reverseGeocodeLocation)
                .map(addresses -> addresses.get(0))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(address -> {
                    shareBtnEnabled = true;
                    this.address = address.getThoroughfare();
                    lat=address.getLatitude();
                    lon=address.getLongitude();
                    txtPlace.setText(this.address);
                }, throwable -> {
                    Toast.makeText(getContext(), "Cannot determines address", Toast.LENGTH_SHORT).show();
                });
    }

    @OnClick(R.id.btn_share)
    public void onShareBtnClick() {
        if (shareBtnEnabled) {
            TimePickerDialog.Builder builder = new TimePickerDialog.Builder(getContext());
            TimePicker timePicker = new TimePicker(getContext());
            builder.setTitle("Время окончания")
                    .setView(timePicker)
                    .setCancelable(true)
                    .setPositiveButton("Share", (dialog, which) -> {
                        Post post = generatePost(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                        ((CameraActivity)getActivity()).returnPost(post);
                    })
                    .create()
                    .show();
        } else
            Toast.makeText(getContext(), "Something wrong, please check network connections and rerun app", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTagClick(int position, Tag tag) {
        tag.isDeletable = true;
        tagsView.addTag(tag);
    }

    private void initTagAdapter() {
        List<Tag> tags = new ArrayList<>();
        tags.add(createTag("#нямка", "#ffffff", "#dddddd", "#555555", "#ffffff"));
        tags.add(createTag("#туса", "#ffffff", "#dddddd", "#555555", "#ffffff"));
        tags.add(createTag("#простопосидеть", "#ffffff", "#dddddd", "#555555", "#ffffff"));
        tags.add(createTag("#халява", "#ffffff", "#dddddd", "#555555", "#ffffff"));
        tags.add(createTag("#ништяки", "#ffffff", "#dddddd", "#555555", "#ffffff"));
        tags.add(createTag("#экстрим", "#ffffff", "#dddddd", "#555555", "#ffffff"));
        tags.add(createTag("#чтотодругое", "#ffffff", "#dddddd", "#555555", "#ffffff"));
        tagAdapter = new TagAdapter(tags, this);
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

    private Post generatePost(int currentHour, int currentMinute) {
        User user = userRepository.getUserCache();
        Post post = new Post();
        post.setAuthor(new Author(user.getId(), user.getNickname(), user.getRating(), user.getPhotoRef()));
        post.setPostType(Constance.PostType.NORMAL);
        post.setContentType(Constance.ContentType.PHOTO);
        post.setContentPath(photoPath);
        post.setDressCode(chbDressCode.isChecked());
        post.setDescription(edtDescription.getText().toString());
        post.setTags(getSelectedTags());
        post.setCity(user.getCity());
        post.setCountry(user.getCountry());
        post.setTime(System.currentTimeMillis());
        post.setDeleteTime(calculateDeleteTime(currentHour, currentMinute));
        post.setOffset(calculateOffset());
        post.setAddress(address);
        post.setLat(lat);
        post.setLon(lon);
        Log.e("offset", String.valueOf(post.getOffset()));
        return post;
    }

    private long calculateDeleteTime(int currentHour, int currentMinute) {
        Calendar currentCalendar = Calendar.getInstance();
        Log.e("currentMonth", currentCalendar.getTime().getMonth() + "");
        Log.e("currentHour", currentCalendar.getTime().getHours() + "");
        Log.e("currentMinute", currentCalendar.getTime().getMinutes() + "");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, currentMinute);
        calendar.set(Calendar.HOUR_OF_DAY, currentHour);
        if (currentCalendar.getTimeInMillis() >= calendar.getTimeInMillis()) {
            try {
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
            } catch (Exception e) {
                calendar.set(Calendar.DAY_OF_MONTH, 1);
            }
            if (currentCalendar.getTimeInMillis() > calendar.getTimeInMillis()) {
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) == 12 ? 1 : calendar.get(Calendar.MONTH) + 1);
                if (currentCalendar.getTimeInMillis() > calendar.getTimeInMillis())
                    calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
            }
        }
        return calendar.getTimeInMillis();
    }

    private double calculateOffset() {
        return (double) Calendar.getInstance().get(Calendar.ZONE_OFFSET) / 1000 / 60 / 60.0;
    }

    public List<String> getSelectedTags() {
        List<String> tags = new ArrayList<>();
        for (Tag tag :
                tagsView.getTags()) {
            tags.add(tag.text);
        }
        return tags;
    }
}
