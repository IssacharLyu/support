package com.broil.support.utils;

import android.util.SparseArray;

import java.util.ArrayList;

/**
 * SparseArrayUtil helps to manage SparseArray conveniently
 *
 * @author: broil
 * @date: 2016/3/29
 * @version: V1.0
 */

public class SparseArrayUtil {

    public static <C> ArrayList<C> asArrayList(SparseArray<C> sparseArray) {
        if (sparseArray == null)
            return new ArrayList<C>();

        ArrayList<C> arrayList = new ArrayList<C>(sparseArray.size());
        for (int i = 0; i < sparseArray.size(); i++)
            arrayList.add(sparseArray.valueAt(i));
        return arrayList;
    }
}
