package com.welcome.studio.welcome.ui.comment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.welcome.studio.welcome.R;
import com.welcome.studio.welcome.app.Injector;
import com.welcome.studio.welcome.model.data.CommentModel;
import com.welcome.studio.welcome.model.data.Post;
import com.welcome.studio.welcome.ui.BaseMainFragment;
import com.welcome.studio.welcome.ui.BasePresenter;
import com.welcome.studio.welcome.ui.Layout;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by @mistreckless on 28.02.2017. !
 */
@Layout(id = R.layout.fragment_comment)
public class Comment extends BaseMainFragment implements CommentView, CommentAdapterListener {
    private CommentAdapter commentAdapter;
    private boolean sendEnabled;

    @Inject
    CommentPresenter presenter;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.rec_comment_view)
    RecyclerView recyclerView;
    @Bind(R.id.edt_comment)
    EditText edtComment;
    @Bind(R.id.img_send)
    ImageView imgSend;

    public static Comment newInstance(Post post) {
        Comment comment = new Comment();
        Bundle args = new Bundle();
        args.putSerializable("post", post);
        comment.setArguments(args);
        return comment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        commentAdapter = new CommentAdapter(getContext(), this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(commentAdapter);
        presenter.setPost((Post) getArguments().getSerializable("post"));
        presenter.controlSendView(RxTextView.textChanges(edtComment));
        presenter.controlTextChanges(RxTextView.textChanges(edtComment));
    }

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
        return getTag();
    }

    @Override
    protected void inject() {
        Injector.getInstance().plus(new CommentModule()).inject(this);
    }

    @Override
    protected Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.comment_title);
    }

    @OnClick(R.id.img_send)
    public void onSendClick() {
        if (sendEnabled) {
            presenter.sendComment();
        }
    }

    @Override
    public void likeClicked(CommentModel comment, int position) {
        presenter.likeCLicked(comment, position);
    }

    @Override
    public void likeCountClicked(CommentModel comment) {
        presenter.likeCountClicked(comment);
    }

    @Override
    public void userThumbClicked(CommentModel comment) {
        presenter.userThumbClicked(comment);
    }

    @Override
    public void addComments(List<CommentModel> comments) {
        commentAdapter.addComments(comments);
        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount()-1);
    }

    @Override
    public void addComment(CommentModel comment) {
        commentAdapter.addComment(comment);
    }

    @Override
    public void refreshComments(int position) {
        commentAdapter.notifyItemInserted(position);
    }

    @Override
    public void refreshComment(int position) {
        commentAdapter.notifyItemChanged(position);
    }

    @Override
    public void refresh() {
        commentAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateCommentView(CommentModel comment, int position) {
        commentAdapter.updateCommentView(comment, position);
    }

    @Override
    public void setSendView(boolean enabled) {
        sendEnabled=enabled;
        imgSend.setImageDrawable(getContext().getDrawable(enabled ? R.mipmap.ic_send_grey600_36dp : R.mipmap.ic_send_grey600_36dp));
    }

    @Override
    public void updateCommentEvent(CommentModel comment) {
        commentAdapter.updateCommentEvent(comment);
    }

    @Override
    public void hideKeyboard() {
        if (getActivity().getCurrentFocus()!=null){
            InputMethodManager inputMethodManager=(InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
            edtComment.setText("");
        }
    }
}
