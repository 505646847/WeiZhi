package com.xiaoyan.xylibrary.common.tools;

import android.util.SparseArray;
import android.view.View;

/**
 * 用于在getView中使用ViewHolder来增加复用性
 */
public class ViewHolderTools {

    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}
