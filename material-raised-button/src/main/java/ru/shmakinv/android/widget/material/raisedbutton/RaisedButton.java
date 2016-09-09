package ru.shmakinv.android.widget.material.raisedbutton;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.devspark.robototextview.widget.RobotoButton;

import java.util.Arrays;

/**
 * RaisedButton
 *
 * @author Vyacheslav Shmakin
 * @version 28.08.2016
 */
public class RaisedButton extends FrameLayout {

    protected CardView mRootView;
    protected RobotoButton mButton;

    protected Integer mButtonHeight;
    protected Integer mButtonWidth;
    protected float mCornerRadius;
    protected float mDefaultElevation;
    protected float mMaxElevation;
    protected int mInnerPaddingLeft;
    protected int mInnerPaddingRight;
    protected int mInnerPaddingTop;
    protected int mInnerPaddingBottom;
    protected int mInnerPaddings;
    protected boolean mLanded = false;

    protected final int[] mAttrs = new int[]{
            android.R.attr.layout_width,
            android.R.attr.layout_height,
            android.R.attr.background,
            android.R.attr.ellipsize,
            android.R.attr.minLines,
            android.R.attr.maxLines,
            android.R.attr.minWidth,
            android.R.attr.maxWidth,
            android.R.attr.minHeight,
            android.R.attr.maxHeight,
            android.R.attr.textColor,
            android.R.attr.textSize,
            android.R.attr.text,
            android.R.attr.enabled};

    protected OnTouchListener mElevationUpdateCallback = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (mDefaultElevation == mMaxElevation) {
                // No elevation animation
                return false;
            }

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mLanded = false;
                    RaisedButtonHelper.animateCardElevation(
                            mRootView,
                            RaisedButtonHelper.ANIMATION_DURATION_NORMAL,
                            mDefaultElevation,
                            mMaxElevation);
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if (!mLanded) {
                        RaisedButtonHelper.animateCardElevation(
                                mRootView,
                                RaisedButtonHelper.ANIMATION_DURATION_NORMAL,
                                mMaxElevation,
                                mDefaultElevation);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    boolean outside = !RaisedButtonHelper.isInsideViewBounds(
                            mRootView,
                            (int) event.getRawX(),
                            (int) event.getRawY());

                    if (outside && !mLanded) {
                        mLanded = true;
                        RaisedButtonHelper.animateCardElevation(
                                mRootView,
                                RaisedButtonHelper.ANIMATION_DURATION_NORMAL,
                                mMaxElevation,
                                mDefaultElevation);
                    }
                    break;
            }
            return false;
        }
    };

    public RaisedButton(Context context) {
        super(context);
        init(context, null);
    }

    public RaisedButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RaisedButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RaisedButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        inflate(context, R.layout.layout_material_raised_button, this);

        mRootView = (CardView) findViewById(R.id.rb_root);
        mButton = (RobotoButton) findViewById(R.id.rb_button);
        initResources(context, attrs);
        mButton.setOnTouchListener(mElevationUpdateCallback);
    }

    private void initResources(@NonNull Context context, @NonNull AttributeSet attrs) {
        initSystemAttributes(context, attrs);
        initCustomAttributes(context, attrs);
    }

    protected void initSystemAttributes(@NonNull Context context, @NonNull AttributeSet attrs) {
        Arrays.sort(mAttrs);
        final TypedArray appearance = context.obtainStyledAttributes(attrs, mAttrs, 0, 0);
        if (appearance != null) {
            int count = appearance.getIndexCount();

            for (int i = 0; i < count; i++) {
                int index = appearance.getIndex(i);

                switch (mAttrs[index]) {
                    case android.R.attr.layout_height:
                    case android.R.attr.layout_width:
                        setUpWidthAndHeight(appearance, index);
                        break;
                    case android.R.attr.background:
                        setUpBackground(appearance.getDrawable(index));
                        break;
                    case android.R.attr.ellipsize:
                        setEllipsizedText(appearance.getInt(index, 3));
                        break;
                    case android.R.attr.minLines:
                        mButton.setMinLines(appearance.getInt(index, 2));
                        break;
                    case android.R.attr.maxLines:
                        mButton.setMaxLines(appearance.getInt(index, 2));
                        break;
                    case android.R.attr.minWidth:
                        mButton.setMinWidth((int) appearance.getDimension(index, 0));
                        break;
                    case android.R.attr.maxWidth:
                        mButton.setMaxWidth((int) appearance.getDimension(index, Integer.MAX_VALUE));
                        break;
                    case android.R.attr.minHeight:
                        mButton.setMinHeight((int) appearance.getDimension(index, 0));
                        break;
                    case android.R.attr.maxHeight:
                        mButton.setMaxHeight((int) appearance.getDimension(index, Integer.MAX_VALUE));
                        break;
                    case android.R.attr.textColor:
                        ColorStateList colorList = appearance.getColorStateList(index);
                        int color = appearance.getColor(index, 0);
                        if (colorList != null) {
                            mButton.setTextColor(colorList);
                        } else {
                            mButton.setTextColor(color);
                        }
                        break;
                    case android.R.attr.textSize:
                        mButton.setTextSize(
                                TypedValue.COMPLEX_UNIT_PX,
                                appearance.getDimensionPixelSize(index, 0));
                        break;
                    case android.R.attr.text:
                        mButton.setText(appearance.getString(index));
                        break;
                    case android.R.attr.enabled:
                        setEnabled(appearance.getBoolean(index, true));
                        break;
                }
            }
            appearance.recycle();
        }
    }

    protected void setUpWidthAndHeight(@NonNull TypedArray attributes, int attrIndex) {
        if (mAttrs[attrIndex] == android.R.attr.layout_width) {
            mButtonWidth = attributes.getLayoutDimension(attrIndex, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else if (mAttrs[attrIndex] == android.R.attr.layout_height) {
            mButtonHeight = attributes.getLayoutDimension(attrIndex, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        if (mButtonWidth != null && mButtonHeight != null) {
            ViewGroup.LayoutParams rootParams = mRootView.getLayoutParams();
            int rootHeight = mButtonHeight >= 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : mButtonHeight;
            int rootWidth = mButtonWidth >= 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : mButtonWidth;

            rootParams.height = rootHeight;
            rootParams.width = rootWidth;

            mRootView.setLayoutParams(rootParams);

            ViewGroup.LayoutParams params = mButton.getLayoutParams();
            params.height = mButtonHeight;
            params.width = mButtonWidth;
            mButton.setLayoutParams(params);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewGroup.LayoutParams rootViewParams = mRootView.getLayoutParams();
        ViewGroup.LayoutParams param = getLayoutParams();
        param.height = rootViewParams.height;
        param.width = rootViewParams.width;
        setLayoutParams(param);
    }

    @SuppressWarnings("deprecation")
    protected void setUpBackground(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mButton.setBackground(drawable);
            setBackground(null);
        } else {
            mButton.setBackgroundDrawable(drawable);
            setBackgroundDrawable(null);
        }
    }

    @NonNull
    public RobotoButton getButtonView() {
        return mButton;
    }

    @NonNull
    public CardView getRootView() {
        return mRootView;
    }

    @Override
    public void setEnabled(boolean enabled) {
        mButton.setEnabled(enabled);
    }

    @Override
    public boolean isEnabled() {
        return mButton.isEnabled();
    }

    protected void initCustomAttributes(@NonNull Context context, @NonNull AttributeSet attrs) {
        final TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.RaisedButton);
        if (attr != null) {
            mCornerRadius = getDefauiltIfNoResource(
                    attr,
                    R.styleable.RaisedButton_rb_cornerRadius,
                    R.dimen.def_rb_button_corner_radius);

            mDefaultElevation = getDefauiltIfNoResource(
                    attr,
                    R.styleable.RaisedButton_rb_elevation,
                    R.dimen.def_rb_button_elevation);

            mMaxElevation = getDefauiltIfNoResource(
                    attr,
                    R.styleable.RaisedButton_rb_maxElevation,
                    R.dimen.def_rb_button_max_elevation);

            initInnerPaddings(attr);

            mRootView.setRadius(mCornerRadius);
            mRootView.setCardElevation(mDefaultElevation);
            mRootView.setMaxCardElevation(mMaxElevation);
            attr.recycle();
        }
    }

    protected float getDefauiltIfNoResource(@NonNull TypedArray attrs, int attrIndex, @DimenRes int defRes) {
        float defValue = getResources().getDimension(defRes);
        return attrs.getDimension(attrIndex, defValue);
    }

    protected void initInnerPaddings(@NonNull TypedArray attrs) {
        mInnerPaddings = (int) attrs.getDimension(R.styleable.RaisedButton_rb_innerPaddings, 0);

        if (mInnerPaddings == 0) {
            // no padding defined
            float defPadding = getResources().getDimension(R.dimen.def_rb_button_padding);
            mInnerPaddingLeft = (int) attrs.getDimension(R.styleable.RaisedButton_rb_innerPaddingLeft, defPadding);
            mInnerPaddingRight = (int) attrs.getDimension(R.styleable.RaisedButton_rb_innerPaddingRight, defPadding);
            mInnerPaddingTop = (int) attrs.getDimension(R.styleable.RaisedButton_rb_innerPaddingTop, defPadding);
            mInnerPaddingBottom = (int) attrs.getDimension(R.styleable.RaisedButton_rb_innerPaddingBottom, defPadding);
            mButton.setPadding(mInnerPaddingLeft, mInnerPaddingTop, mInnerPaddingRight, mInnerPaddingBottom);
        } else {
            mButton.setPadding(mInnerPaddings, mInnerPaddings, mInnerPaddings, mInnerPaddings);
        }
    }

    protected void setEllipsizedText(int ellipsize) {
        switch (ellipsize) {
            case 1:
                mButton.setEllipsize(TextUtils.TruncateAt.START);
                break;
            case 2:
                mButton.setEllipsize(TextUtils.TruncateAt.MIDDLE);
                break;
            case 3:
                mButton.setEllipsize(TextUtils.TruncateAt.END);
                break;
            case 4:
                mButton.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                break;
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        mButton.setOnClickListener(l);
    }
}

