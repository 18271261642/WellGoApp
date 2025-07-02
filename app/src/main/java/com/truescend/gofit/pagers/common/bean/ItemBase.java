package com.truescend.gofit.pagers.common.bean;

import android.view.View;

import java.util.List;


/**
 * 功能:所有Item的父类
 * Author:Created by 泽鑫 on 2018/3/1 17:03.
 */
@Deprecated
public class ItemBase {



    public ItemBase(View view) {

    }

    public void unBind() {

    }

    public static void unBindAll(ItemBase... items) {
        for (int i = 0; i < items.length; i++) {
            ItemBase item = items[i];
            if (item != null) {
                item.unBind();
            }
        }
    }public static void unBindAll(List<ItemBase> items) {
        for (int i = 0; i < items.size(); i++) {
            ItemBase item = items.get(i);
            if (item != null) {
                item.unBind();
            }
        }
    }
}
