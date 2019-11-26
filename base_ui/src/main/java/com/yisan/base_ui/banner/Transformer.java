package com.yisan.base_ui.banner;


import androidx.viewpager.widget.ViewPager;

import com.yisan.base_ui.banner.transformer.AccordionTransformer;
import com.yisan.base_ui.banner.transformer.BackgroundToForegroundTransformer;
import com.yisan.base_ui.banner.transformer.CubeInTransformer;
import com.yisan.base_ui.banner.transformer.CubeOutTransformer;
import com.yisan.base_ui.banner.transformer.DefaultTransformer;
import com.yisan.base_ui.banner.transformer.DepthPageTransformer;
import com.yisan.base_ui.banner.transformer.FlipHorizontalTransformer;
import com.yisan.base_ui.banner.transformer.FlipVerticalTransformer;
import com.yisan.base_ui.banner.transformer.ForegroundToBackgroundTransformer;
import com.yisan.base_ui.banner.transformer.RotateDownTransformer;
import com.yisan.base_ui.banner.transformer.RotateUpTransformer;
import com.yisan.base_ui.banner.transformer.ScaleInOutTransformer;
import com.yisan.base_ui.banner.transformer.StackTransformer;
import com.yisan.base_ui.banner.transformer.TabletTransformer;
import com.yisan.base_ui.banner.transformer.ZoomInTransformer;
import com.yisan.base_ui.banner.transformer.ZoomOutSlideTransformer;
import com.yisan.base_ui.banner.transformer.ZoomOutTranformer;


public class Transformer {
    public static Class<? extends ViewPager.PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends ViewPager.PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends ViewPager.PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
