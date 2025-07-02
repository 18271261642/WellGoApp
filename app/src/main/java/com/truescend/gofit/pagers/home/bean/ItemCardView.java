package com.truescend.gofit.pagers.home.bean;

import androidx.annotation.StringRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.truescend.gofit.R;
import com.truescend.gofit.pagers.common.bean.ItemBase;


/**
 * 功能：卡片布局
 * 适用于主界面各种重要功能布局
 * item_home_card_view.xml适配类，用于绑定布局控件，一个viewHolder
 * Author:Created by 泽鑫 on 2017/11/30 10:28.
 * 东芝:全改,简化使用
 */
public class ItemCardView extends ItemBase {

    ImageView ivHomeCardPicture;

    TextView tvHomeCardTitle;

    TextView tvHomeCardType;

    TextView tvHomeCardSubTitle;

    public ItemCardView(View view) {
        super(view);

        ivHomeCardPicture = view.findViewById(R.id.ivHomeCardPicture);
         tvHomeCardTitle = view.findViewById(R.id.tvHomeCardTitle);
         tvHomeCardType= view.findViewById(R.id.tvHomeCardType);
         tvHomeCardSubTitle= view.findViewById(R.id.tvHomeCardSubTitle);
    }

    public void setPicture(int resId) {
        ivHomeCardPicture.setImageResource(resId);
    }

    public void setTitle(@StringRes int id) {
        tvHomeCardTitle.setText(id);
    }

    public void setTitle(CharSequence title) {
        tvHomeCardTitle.setText(title);
    }

    public void setSubTitle(@StringRes int id) {
        tvHomeCardSubTitle.setText(id);
    }

    public void setSubTitle(CharSequence title) {
        tvHomeCardSubTitle.setText(title);
    }

    public void setType(int resId) {
        tvHomeCardType.setText(resId);
    }

    public void setType(CharSequence type) {
        tvHomeCardType.setText(type);
    }


}
