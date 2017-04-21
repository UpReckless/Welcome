package com.welcome.studio.welcome.ui.module.watchers.post;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.app.Injector;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.model.data.User;
import com.welcome.studio.welcome.ui.BaseMainFragment;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.Layout;
import com.welcome.studio.welcome.util.Helper;
import com.welcome.studio.welcome.util.tagview.TagsView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.Bind;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by @mistreckless on 31.03.2017. !
 */
@Layout(id = R.layout.fragment_post_watcher)
public class PostWatcher extends BaseMainFragment implements PostWatcherView {
    @Inject
    PostWatcherPresenter presenter;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.img_thumb)
    ImageView imgThumb;
    @Bind(R.id.txt_rating)
    TextView txtRating;
    @Bind(R.id.txt_name)
    TextView txtName;
    @Bind(R.id.txt_report_count)
    TextView txtReportCount;
    @Bind(R.id.img_report)
    ImageView imgReport;
    @Bind(R.id.txt_address)
    TextView txtAdress;
    @Bind(R.id.img_content)
    ImageView imgContent;
    @Bind(R.id.img_like)
    ImageView imgLike;
    @Bind(R.id.txt_like_count)
    TextView txtLikeCount;
    @Bind(R.id.img_comment)
    ImageView imgComment;
    @Bind(R.id.txt_comment_count)
    TextView txtCommentCount;
    @Bind(R.id.img_willcome)
    ImageView imgWillcome;
    @Bind(R.id.txt_willcome_count)
    TextView txtWillcomeCount;
    @Bind(R.id.tags_view)
    TagsView tagsView;
    @Bind(R.id.txt_description)
    TextView txtDesc;
    @Bind(R.id.txt_time)
    TextView txtTime;

    public static PostWatcher newInstance(Post post, boolean isRealTime) {
        PostWatcher postWatcher = new PostWatcher();
        Bundle args = new Bundle();
        args.putSerializable("post", post);
        args.putBoolean("real_time", isRealTime);
        postWatcher.setArguments(args);
        return postWatcher;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
//        if (Injector.getInstance().getMainComponent()==null && getActivity() instanceof MainActivity)
//            Injector.getInstance().plus(new MainModule((View)getActivity())).inject((MainActivity) getActivity());

        super.onCreate(savedInstanceState);
        presenter.setVariables((Post) getArguments().getSerializable("post"), getArguments().getBoolean("real_time"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    protected Object getRouter() {
        return getActivity();
    }

    @NonNull
    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public String getFragmentTag() {
        return null;
    }

    @Override
    protected boolean isAddedToBackStack() {
        return true;
    }

    @Override
    protected void inject() {
        Injector.getInstance().plus(new PostWatcherModule()).inject(this);
    }

    @Override
    protected Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.app_name);
    }

    @Override
    public void initUi(@NonNull Post post, User user) {
        Picasso.with(getContext()).load(post.getAuthor().getThumbRef() != null ? Uri.parse(post.getAuthor().getThumbRef()) : null).error(R.mipmap.img_avatar).into(imgThumb);
        txtName.setText(post.getAuthor().getName());
        txtAdress.setText(post.getAddress());
        txtDesc.setText(post.getDescription());
        txtRating.setText(String.valueOf(Helper.countRating(post.getAuthor().getRating())));
        txtTime.setText(new SimpleDateFormat("HH:mm").format(new Date(post.getTime())) + " - "
                + new SimpleDateFormat("HH:mm").format(new Date(post.getDeleteTime())));
        if (post.getTags() != null) {
            tagsView.removeAllTags();
            tagsView.addTags(post.getTags().toArray(new String[post.getTags().size()]));
        }
        Picasso.with(getContext()).load(Uri.parse(post.getContentRef())).networkPolicy(NetworkPolicy.OFFLINE)
                .error(R.drawable.load_holder)
                .into(imgContent, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(getContext()).load(Uri.parse(post.getContentRef())).error(R.drawable.load_holder).into(imgContent);
                    }
                });
        updateVariables(post, user);
        RxView.clicks(imgLike)
                .debounce(300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(v -> presenter.likeClicked());
        RxView.clicks(imgWillcome)
                .debounce(300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(v -> presenter.willcomeClicked());
        RxView.clicks(imgReport)
                .debounce(300, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                .subscribe(v -> presenter.reportClicked());
        imgComment.setOnClickListener(v -> presenter.commentClicked());

        txtLikeCount.setOnClickListener(v -> presenter.likeCountClicked());
        txtWillcomeCount.setOnClickListener(v -> presenter.willcomeCountClicked());
        txtReportCount.setOnClickListener(v -> presenter.reportCountClicked());

        imgThumb.setOnClickListener(v -> presenter.profileClicked());
        txtName.setOnClickListener(v -> presenter.profileClicked());
        txtRating.setOnClickListener(v -> presenter.profileClicked());
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updatePost(Post post, User user) {
        updateVariables(post, user);
    }

    private void updateVariables(Post post, User user) {
        imgLike.setImageDrawable(getResources().getDrawable(post.isLiked() ? R.mipmap.ic_heart_grey600_36dp : R.mipmap.ic_heart_outline_grey600_36dp));
        imgWillcome.setImageDrawable(getResources().getDrawable(post.isWillcomed() || post.getAuthor().getuId() == user.getId() ? R.mipmap.ic_willcome_clicked : R.mipmap.ic_willcome));
        imgReport.setImageDrawable(getResources().getDrawable(post.isReported() ? R.mipmap.ic_report_clicked : R.mipmap.ic_report));

        txtCommentCount.setText(post.getComments() != null ? String.valueOf(post.getComments().size()) : "");
        txtLikeCount.setText(post.getLikes() != null ? String.valueOf(post.getLikes().size()) : "");
        txtWillcomeCount.setText(post.getWillcomes() != null ? String.valueOf(post.getWillcomes().size()) : "");
        txtReportCount.setText(post.getReports() != null ? String.valueOf(post.getReports().size()) : "");
    }
}
