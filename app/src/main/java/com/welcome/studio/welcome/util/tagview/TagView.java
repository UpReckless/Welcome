package com.welcome.studio.welcome.util.tagview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.welcome.studio.welcome.R;

/**
 * Created by @mistreckless on 17.02.2017. !
 */

public class TagView extends RelativeLayout {
    private static final String TAG = "TagView";
    private int mWidth;
    private int lineMargin;
    private int tagMargin;
    private int textPaddingLeft;
    private int textPaddingRight;
    private int textPaddingTop;
    private int texPaddingBottom;
    private LayoutInflater mInflater;
    private Tag tag;

    public TagView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //LogUtil.e(TAG, "[TagView]constructor 2");
        init(context, attrs, 0, 0);
    }

    public TagView(Context ctx, AttributeSet attrs, int defStyle) {
        super(ctx, attrs, defStyle);
        //LogUtil.e(TAG, "[TagView]constructor 3");
        init(ctx, attrs, defStyle, defStyle);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TagView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        //LogUtil.e(TAG, "[TagView]constructor 4");
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setTag(Tag tag) {
        this.tag = tag;
        drawTag();
    }


    private void init(Context context, AttributeSet attrs, int defStyle, int defStyleRes) {
        //LogUtil.e(TAG, "[init]");
        Constants.DEBUG = (context.getApplicationContext().getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // get AttributeSet
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.TagsView, defStyle, defStyleRes);
        this.lineMargin = (int) typeArray.getDimension(R.styleable.TagsView_lineMargin, ResolutionUtil.dpToPx(this.getContext(), Constants.DEFAULT_LINE_MARGIN));
        this.tagMargin = (int) typeArray.getDimension(R.styleable.TagsView_tagMargin, ResolutionUtil.dpToPx(this.getContext(), Constants.DEFAULT_TAG_MARGIN));
        this.textPaddingLeft = (int) typeArray.getDimension(R.styleable.TagsView_textPaddingLeft, ResolutionUtil.dpToPx(this.getContext(), Constants.DEFAULT_TAG_TEXT_PADDING_LEFT));
        this.textPaddingRight = (int) typeArray.getDimension(R.styleable.TagsView_textPaddingRight, ResolutionUtil.dpToPx(this.getContext(), Constants.DEFAULT_TAG_TEXT_PADDING_RIGHT));
        this.textPaddingTop = (int) typeArray.getDimension(R.styleable.TagsView_textPaddingTop, ResolutionUtil.dpToPx(this.getContext(), Constants.DEFAULT_TAG_TEXT_PADDING_TOP));
        this.texPaddingBottom = (int) typeArray.getDimension(R.styleable.TagsView_textPaddingBottom, ResolutionUtil.dpToPx(this.getContext(), Constants.DEFAULT_TAG_TEXT_PADDING_BOTTOM));
        typeArray.recycle();
        mWidth = ResolutionUtil.getScreenWidth(context);
        // this.setWillNotDraw(false);
    }

    private void drawTag() {
        if (getVisibility() != View.VISIBLE) return;
      //  LogUtil.e(TAG, "[drawTags]mWidth = " + mWidth);
        // clear all tag
        removeAllViews();
        int index_header = 1;// The header tag of this line

        View tagLayout = mInflater.inflate(R.layout.tagview_item, null);
        tagLayout.setBackgroundDrawable(getSelector(tag));
        TextView tagView = (TextView) tagLayout.findViewById(R.id.tv_tag_item_contain);
        tagView.setText(tag.text);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tagView.getLayoutParams();
        params.setMargins(textPaddingLeft, textPaddingTop, textPaddingRight, texPaddingBottom);
        tagView.setLayoutParams(params);
        tagView.setTextColor(tag.tagTextColor);
        tagView.setTextSize(TypedValue.COMPLEX_UNIT_SP, tag.tagTextSize);

        float tagWidth = tagView.getPaint().measureText(tag.text) + textPaddingLeft + textPaddingRight;
        // tagView padding (left & right)
        // deletable text
        TextView deletableView = (TextView) tagLayout.findViewById(R.id.tv_tag_item_delete);
        deletableView.setVisibility(GONE);
        LayoutParams tagParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //tagParams.setMargins(0, 0, 0, 0);
        //add margin of each line
        tagParams.bottomMargin = lineMargin;
        tagParams.addRule(RelativeLayout.ALIGN_TOP, index_header);
        addView(tagLayout, tagParams);

    }

    private Drawable getSelector(Tag tag) {
        if (tag.background != null) return tag.background;
        StateListDrawable states = new StateListDrawable();
        GradientDrawable gd_normal = new GradientDrawable();
        gd_normal.setColor(tag.layoutColor);
        gd_normal.setCornerRadius(tag.radius);
        if (tag.layoutBorderSize > 0) {
            gd_normal.setStroke(ResolutionUtil.dpToPx(getContext(), tag.layoutBorderSize), tag.layoutBorderColor);
        }
        GradientDrawable gd_press = new GradientDrawable();
        gd_press.setColor(tag.layoutColorPress);
        gd_press.setCornerRadius(tag.radius);
        states.addState(new int[]{android.R.attr.state_pressed}, gd_press);
        //must add state_pressed first，or state_pressed will not take effect
        states.addState(new int[]{}, gd_normal);
        return states;
    }
}
