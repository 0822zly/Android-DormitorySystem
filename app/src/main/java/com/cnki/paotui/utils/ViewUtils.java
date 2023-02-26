package com.cnki.paotui.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;

public class ViewUtils {

    /**
     * 设置圆角的背景
     *
     * @param width 线的宽度
     * @param widthcolor 线的颜色
     */
    public static void setbackDrawable(Context context,View v, int radius,String color,int width , String widthcolor ) {

        GradientDrawable drawable=new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);

        drawable.setColor(Color.parseColor(color));
        drawable.setCornerRadius(dp2px(context, radius));
        drawable.setStroke(dp2px(context, dp2px(context, width)), Color.parseColor(widthcolor));
        v.setBackground(drawable);
    }

    /**
     * 设置圆角的背景
     *
     * @param
     * @param v       View
     */
    public static void setbackDrawable(Context context,View v, int radius,String color) {
        GradientDrawable drawable=new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(dp2px(context, radius));
        drawable.setColor(Color.parseColor(color));
        v.setBackground(drawable);
    }
    /**
     * 设置圆角的背景
     *
     * @param context 上下文
     * @param v       View
     */
    public static void setbackDrawable(Context context,View v, int radius,int color) {
        GradientDrawable drawable=new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(dp2px(context, radius));
        drawable.setColor(context.getResources().getColor(color));
        v.setBackground(drawable);
    }

    /**
     * 根据手机的分辨率dp 转成px(像素)
     */
    public  static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /*
     * 设置搜索html样式
     * */
    public static SpannableStringBuilder getHtmlStyle(String strTitle){
        return   getHtmlStyle(strTitle,"#1C7FEE");

    }
    /*蓝色：#1C7FEE
     * 设置搜索html样式
     * */
    public static SpannableStringBuilder getHtmlStyle(String strTitle,String color){
        try {
            int index = -1,end = -1;
            ForegroundColorSpan span = new ForegroundColorSpan(Color.parseColor(color));// 要显示的颜色
            if (!TextUtils.isEmpty(strTitle)) {
                SpannableStringBuilder builder = new SpannableStringBuilder(strTitle);

                index = strTitle.indexOf("#");// 从第几个匹配上
                end = strTitle.indexOf("$");


                if (index != -1 && end > index) {
                    // 有这个关键字用builder显示
                    builder.setSpan(span, index, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    SpannableStringBuilder delete = builder.delete(strTitle.indexOf("#"),
                            strTitle.lastIndexOf("#") + 1);
                    SpannableStringBuilder delete2 = delete.delete(delete.toString().indexOf("$"),
                            delete.toString().lastIndexOf("$") + 1);

                    return delete2;
                }
                return builder;
            }
        } catch (Exception e) {
            e.printStackTrace();
            SpannableStringBuilder builder = new SpannableStringBuilder(strTitle);

            return builder;
        }
        SpannableStringBuilder builder = new SpannableStringBuilder(strTitle);

        return builder;


    }

}

