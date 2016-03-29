
package com.broil.support.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 */
public class ViewUtils {

    /** 得到控件中的字符串/多个时则字符串拼接 */
    public static String getString(View... v) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < v.length; i++) {
            if (v[i] instanceof EditText) {
                stringBuffer.append(((EditText) v[i]).getText().toString()
                        .trim());
            } else if (v[i] instanceof TextView) {
                stringBuffer.append(((TextView) v[i]).getText().toString()
                        .trim());
            } else if (v[i] instanceof Button) {
                stringBuffer
                        .append(((Button) v[i]).getText().toString().trim());
            }
        }
        return stringBuffer.toString();
    }

    public static ProgressDialog getDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
        return dialog;
    }

    /**
     * 截图
     * 
     * @param v 需要进行截图的控件
     * @return 该控件截图的Bitmap对象。
     */
    public static Bitmap captureView(View v) {
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        return v.getDrawingCache();
    }

    /**
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView, Context context) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(MeasureSpec.makeMeasureSpec(
                    context.getResources().getDisplayMetrics().widthPixels, MeasureSpec.EXACTLY), 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount()) - 1);
        listView.setLayoutParams(params);
    }

    /**
     * 计算gridview的高度
     * 
     * @param gridView
     */
    public static void setGridViewHeightBasedOnChildren(GridView gridView,Context context) {
        // 获取GridView对应的Adapter
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int rows;
        int columns = 0;
        int horizontalBorderHeight = 0;
        Class<?> clazz = gridView.getClass();
        try {
            // 利用反射，取得每行显示的个数
            Field column = clazz.getDeclaredField("mRequestedNumColumns");
            column.setAccessible(true);
            columns = (Integer) column.get(gridView);
            // 利用反射，取得横向分割线高度
            Field horizontalSpacing = clazz.getDeclaredField("mRequestedHorizontalSpacing");
            horizontalSpacing.setAccessible(true);
            horizontalBorderHeight = (Integer) horizontalSpacing.get(gridView);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        // 判断数据总数除以每行个数是否整除。不能整除代表有多余，需要加一行
        if (listAdapter.getCount() % columns > 0) {
            rows = listAdapter.getCount() / columns + 1;
        } else {
            rows = listAdapter.getCount() / columns;
        }
        int totalHeight = 0;
        for (int i = 0; i < rows; i++) { // 只计算每项高度*行数
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(MeasureSpec.makeMeasureSpec(
                    context.getResources().getDisplayMetrics().widthPixels, MeasureSpec.EXACTLY), 0);
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight + horizontalBorderHeight * (rows - 1);// 最后加上分割线总高度
        gridView.setLayoutParams(params);
    }

    /**
     *
     * @param refreshLayout
     * @param refreshing
     * @param notify
     */
    public static void setRefreshing(SwipeRefreshLayout refreshLayout, boolean refreshing, boolean notify) {
        Class<? extends SwipeRefreshLayout> refreshLayoutClass = refreshLayout.getClass();
        if (refreshLayoutClass != null) {

            try {
                Method setRefreshing = refreshLayoutClass.getDeclaredMethod("setRefreshing", boolean.class, boolean.class);
                setRefreshing.setAccessible(true);
                setRefreshing.invoke(refreshLayout, refreshing, notify);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
