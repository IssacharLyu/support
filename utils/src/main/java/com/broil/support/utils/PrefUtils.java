/*
 *  Copyright 2016 oil lyu
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.broil.support.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * PrefUtils
 * <p>
 * Created by broil on 2016-3-29.
 */
public class PrefUtils {
    private static String defaultName = PrefUtils.class.getCanonicalName();

    private PrefUtils() {
    }

    public static String getDefaultName() {
        return defaultName;
    }

    public static void setDefaultName(String name) {
        defaultName = name;
    }

    private static SharedPreferences getSharedPreferences(Context context, String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    private static Editor getEditor(Context context, String name) {
        SharedPreferences pre = getSharedPreferences(context, name);
        return pre.edit();
    }

    // Int

    public static void putInt(Context context, String key, int value) {
        if (context == null)
            return;

        Editor editor = getEditor(context, defaultName);
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInt(Context context, String key) {
        return getInt(context, key, -1);
    }

    public static int getInt(Context context, String key, int defaultValue) {
        if (context == null)
            return -1;

        SharedPreferences pre = getSharedPreferences(context, defaultName);
        return pre.getInt(key, defaultValue);
    }

    // String
    public static void putString(Context context, String key, String value) {
        if (context == null)
            return;

        Editor editor = getEditor(context, defaultName);
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key) {
        if (context == null)
            return "";

        SharedPreferences pre = getSharedPreferences(context, defaultName);
        return pre.getString(key, "");
    }

    public static void putBoolean(Context context, String key, boolean value) {
        if (context == null)
            return;

        Editor editor = getEditor(context, defaultName);
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        if (context == null)
            return false;

        return getSharedPreferences(context, defaultName).getBoolean(key, defaultValue);
    }

    public static void putFloat(Context context, String key, float value) {
        if (context == null)
            return;

        Editor editor = getEditor(context, defaultName);
        editor.putFloat(key, value);
        editor.commit();
    }

    public static float getFloat(Context context, String key) {
        return getFloat(context, key, -1);
    }

    public static float getFloat(Context context, String key, float defaultValue) {
        if (context == null)
            return -1;

        return getSharedPreferences(context, defaultName).getFloat(key, defaultValue);
    }

}
