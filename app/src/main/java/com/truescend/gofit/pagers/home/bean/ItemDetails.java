package com.truescend.gofit.pagers.home.bean;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.truescend.gofit.R;
import com.truescend.gofit.pagers.common.bean.ItemBase;


/**
 * 功能：详情横条布局
 * 适用于每个重要功能的详情布局
 * item_details.xml适配类，用于绑定布局控件，一个viewHolder
 * Author:Created by 泽鑫 on 2017/12/4 18:45.
 */

public class ItemDetails extends ItemBase implements View.OnClickListener {
    public static final int ITEM_TEXT_LOW = 0;
    public static final int ITEM_TEXT_HEIGHT = 1;


    ImageView ivDetailsIconTitle;

    TextView tvDetailsTitle;

    ImageView ivDetailsIconRed;

    TextView tvDetailsTextHeight;

    ImageView ivDetailsIconYellow;

    TextView tvDetailsTextLow;

    private OnTextClickListener textClickListener;

    /**
     * 绑定include进去的视图
     * @param view item视图
     */
    public ItemDetails(View view) {
        super(view);

         ivDetailsIconTitle= view.findViewById(R.id.ivDetailsIconTitle);
         tvDetailsTitle = view.findViewById(R.id.tvDetailsTitle);
         ivDetailsIconRed = view.findViewById(R.id.ivDetailsIconRed);
         tvDetailsTextHeight= view.findViewById(R.id.tvDetailsTextHeight);
         ivDetailsIconYellow = view.findViewById(R.id.ivDetailsIconYellow);
        tvDetailsTextLow = view.findViewById(R.id.tvDetailsTextLow);

        tvDetailsTextLow.setOnClickListener(this);
        tvDetailsTextHeight.setOnClickListener(this);
    }

    public void setIcon(int resId){
        ivDetailsIconTitle.setImageResource(resId);
    }

    public void setIcon(Drawable drawable){
        ivDetailsIconTitle.setImageDrawable(drawable);
    }

    public void setTitle(int resId){
        tvDetailsTitle.setText(resId);
    }

    public void setTitle(String title){
        tvDetailsTitle.setText(title);
    }

    public void setHeightText(int resId){
        tvDetailsTextHeight.setText(resId);
    }

    public void setHeightText(String text){
        tvDetailsTextHeight.setText(text);
    }

    public void setHeightTextVisibility(int visibility){
        tvDetailsTextHeight.setVisibility(visibility);
    }

    public void setLowText(int resId){
        tvDetailsTextLow.setText(resId);
    }

    public void setLowText(String text){
        tvDetailsTextLow.setText(text);
    }

    public void setLowTextVisibility(int visibility){
        tvDetailsTextLow.setVisibility(visibility);
    }

    public void setRedIcon(int resId){
        ivDetailsIconRed.setImageResource(resId);
    }

    public void setRedIcon(Drawable drawable){
        ivDetailsIconRed.setImageDrawable(drawable);
    }

    public void setRedIconVisibility(int visibility){
        ivDetailsIconRed.setVisibility(visibility);
    }

    public void setYellowIcon(int resId){
        ivDetailsIconYellow.setImageResource(resId);
    }

    public void setYellowIcon(Drawable drawable){
        ivDetailsIconYellow.setImageDrawable(drawable);
    }

    public void setYellowIconVisibility(int visibility){
        ivDetailsIconYellow.setVisibility(visibility);
    }

    public void setOnTextClickListener(OnTextClickListener clickListener){
        textClickListener = clickListener;
    }



    @Override
    public void onClick(View view) {
        if (textClickListener == null)return;
        switch (view.getId()){
            case R.id.tvDetailsTextLow:
                textClickListener.onTextClick(ITEM_TEXT_LOW);
                break;
            case R.id.tvDetailsTextHeight:
                textClickListener.onTextClick(ITEM_TEXT_HEIGHT)
                ;
                break;
        }
    }

    public interface OnTextClickListener{
        void onTextClick(int item);
    }
    
}

