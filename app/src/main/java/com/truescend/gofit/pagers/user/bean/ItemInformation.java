package com.truescend.gofit.pagers.user.bean;

import android.view.View;
import android.widget.TextView;

import com.truescend.gofit.R;


/**
 * 功能：信息布局
 * 适配item_information.xml
 * Author:Created by 泽鑫 on 2018/1/25 15:53.
 */

public class ItemInformation {

    TextView tvInformationTitle;

    TextView tvInformationContent;

    TextView tvInformationNote;

    public ItemInformation(View view) {
         tvInformationTitle = view.findViewById(R.id.tvInformationTitle);
       tvInformationContent = view.findViewById(R.id.tvInformationContent);
       tvInformationNote= view.findViewById(R.id.tvInformationNote);
    }

    public void setTitle(int resId){
        tvInformationTitle.setText(resId);
    }

    public void setTitle(String title){
        tvInformationTitle.setText(title);
    }

    public void setTitleColor(int resId){
        tvInformationTitle.setTextColor(resId);
    }

    public void setContent(int resId){
        tvInformationContent.setText(resId);
    }

    public void setContent(String content){
        tvInformationContent.setText(content);
    }

    public void setContentVisibility(int visibility){
        tvInformationContent.setVisibility(visibility);
    }

    public void setNote(int resId){
        tvInformationNote.setText(resId);
    }

    public void setNote(String note){
        tvInformationNote.setText(note);
    }

    public void setNoteVisibility(int visibility){
        tvInformationNote.setVisibility(visibility);
    }
}
