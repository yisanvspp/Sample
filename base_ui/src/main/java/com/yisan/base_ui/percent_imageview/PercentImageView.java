package com.yisan.base_ui.percent_imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.IntDef;
import androidx.appcompat.widget.AppCompatImageView;

import com.yisan.base_ui.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.yisan.base_ui.percent_imageview.PercentImageView.Basics.BASICS_HEIGHT;
import static com.yisan.base_ui.percent_imageview.PercentImageView.Basics.BASICS_WIDTH;

/**
 * @author：wzh
 * @description:按宽高比列显示的ImageView
 * @packageName: com.yisan.base.view
 * @date：2019/11/25 0025 下午 8:10
 */
public class PercentImageView extends AppCompatImageView {


    @Basics
    private int mBasics;
    private float mPercent;
    private int mWidthMeasureSize;
    private int mHeightMeasureSize;

    public PercentImageView(Context context) {
        this(context, null);
    }

    public PercentImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PercentImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.PercentImageView);
        mBasics = typedArray.getInt(R.styleable.PercentImageView_piv_basics, Basics.BASICS_WIDTH);
        mPercent = typedArray.getFloat(R.styleable.PercentImageView_piv_percent, 1.0F);
        typedArray.recycle();
    }

    /**
     * 设置宽或者高
     *
     * @param basics
     */
    public void setBasics(int basics) {
        if (mBasics == basics) {
            return;
        }
        mBasics = basics;
        resetNewSize();
    }


    /**
     * 设置百分比
     *
     * @param percent 百分比
     */
    public void setPercent(float percent) {
        if (mPercent == percent) {
            return;
        }
        mPercent = percent;
        resetNewSize();
    }

    /**
     * 设置百分比
     *
     * @param basics  宽或者高
     * @param percent 百分比
     */
    public void setPercent(@Basics int basics, float percent) {
        if (mBasics == basics && mPercent == percent) {
            return;
        }
        mBasics = basics;
        mPercent = percent;
        resetNewSize();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //测量
        mWidthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        mHeightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);
        int[] size = calculateNewSize();
        setMeasuredDimension(size[0], size[1]);
    }

    /**
     * 计算百分比显示的宽高
     *
     * @return
     */
    private int[] calculateNewSize() {
        int[] size = new int[]{mWidthMeasureSize, mHeightMeasureSize};
        if (mBasics == Basics.BASICS_WIDTH) {
            size[1] = (int) (mWidthMeasureSize * mPercent);
        } else if (mBasics == Basics.BASICS_HEIGHT) {
            size[0] = (int) (mHeightMeasureSize * mPercent);
        }
        return size;
    }

    /**
     * 重新设置宽和高
     */
    private void resetNewSize() {
        int[] size = calculateNewSize();
        getLayoutParams().width = size[0];
        getLayoutParams().height = size[1];
        requestLayout();
    }


    //利用注解实现常量定义
    @IntDef({BASICS_WIDTH, BASICS_HEIGHT})
    @Retention(RetentionPolicy.SOURCE)
    @interface Basics {
        int BASICS_WIDTH = 0;
        int BASICS_HEIGHT = 1;
    }
}
