
package com.broil.support.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * TODO<Toast 工具类>
 *
 * @author lyu
 * @date: 2015/9/1
 * @version: V1.0
 */
public class Toaster {

    private static Toast mToast = null;

    public static void showToast(Context context, String msg) {

        if (!TextUtils.isEmpty(msg)) {
            if (mToast == null)
                mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            else
                mToast.setText(msg);
            mToast.show();
        }
    }
    public static void showToast(Context context, int id) {
        if (id != 0) {
            if (mToast == null)
                mToast = Toast.makeText(context, id, Toast.LENGTH_SHORT);
            else
                mToast.setText(id);
            mToast.show();
        }
    }
}

