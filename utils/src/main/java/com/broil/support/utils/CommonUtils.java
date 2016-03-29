package com.broil.support.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * CommonUtils
 *
 * @author: broil
 * @date: 2016/3/29
 * @version: V1.0
 */

public class CommonUtils {

    /**
     * 隐藏系统键盘 <br>
     * <b>警告</b> 必须是确定键盘显示时才能调用
     */
    public static void hideKeyBoard(Activity aty) {
        InputMethodManager imm = (InputMethodManager) aty.getSystemService(Context.INPUT_METHOD_SERVICE);
        //得到InputMethodManager的实例
        if (imm.isActive())
        {
            if (!aty.isFinishing() && aty.getCurrentFocus() != null) {
                try {
                    //如果开启
                    imm.hideSoftInputFromWindow(
                            aty.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }
    }

    /**
     * 打卡软键盘
     *
     * @param mEditText 输入框
     * @param mContext 上下文
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText 输入框
     * @param mContext 上下文
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }
}
