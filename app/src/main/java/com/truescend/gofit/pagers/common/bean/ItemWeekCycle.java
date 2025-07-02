package com.truescend.gofit.pagers.common.bean;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.truescend.gofit.R;


/**
 * 功能：星期多选按钮布局
 * 适用于闹钟提醒等，选择每周哪天或哪几天提醒
 * Author:Created by 泽鑫 on 2018/1/6 11:06.
 */
@Deprecated
public class ItemWeekCycle extends ItemBase{

    CheckBox cbWeekCycleSunday;

    CheckBox cbWeekCycleMonday;

    CheckBox cbWeekCycleTuesday;

    CheckBox cbWeekCycleWednesday;

    CheckBox cbWeekCycleThursday;

    CheckBox cbWeekCycleFriday;

    CheckBox cbWeekCycleSaturday;

    public ItemWeekCycle(View view) {
        super(view);
        cbWeekCycleSunday= view.findViewById(R.id.cbWeekCycleSunday);
         cbWeekCycleMonday = view.findViewById(R.id.cbWeekCycleMonday);
         cbWeekCycleTuesday = view.findViewById(R.id.cbWeekCycleTuesday);
         cbWeekCycleWednesday = view.findViewById(R.id.cbWeekCycleWednesday);
         cbWeekCycleThursday = view.findViewById(R.id.cbWeekCycleThursday);
         cbWeekCycleFriday = view.findViewById(R.id.cbWeekCycleFriday);
        cbWeekCycleSaturday = view.findViewById(R.id.cbWeekCycleSaturday);


    }

    public void setSundayCheck(boolean check) {
        cbWeekCycleSunday.setChecked(check);
    }
    public void setMondayCheck(boolean check) {
        cbWeekCycleMonday.setChecked(check);
    }
    public void setTuesdayCheck(boolean check) {
        cbWeekCycleTuesday.setChecked(check);
    }
    public void setWednesdayCheck(boolean check) {
        cbWeekCycleWednesday.setChecked(check);
    }
    public void setThursdayCheck(boolean check) {
        cbWeekCycleThursday.setChecked(check);
    }
    public void setFridayCheck(boolean check) {
        cbWeekCycleFriday.setChecked(check);
    }
    public void setSaturdayCheck(boolean check) {
        cbWeekCycleSaturday.setChecked(check);
    }

    public void setSundayOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        cbWeekCycleSunday.setOnCheckedChangeListener(listener);
    }

    public void setMondayOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        cbWeekCycleMonday.setOnCheckedChangeListener(listener);
    }

    public void setTuesdayOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        cbWeekCycleTuesday.setOnCheckedChangeListener(listener);
    }

    public void setWednesdayOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        cbWeekCycleWednesday.setOnCheckedChangeListener(listener);
    }

    public void setThursdayOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        cbWeekCycleThursday.setOnCheckedChangeListener(listener);
    }
    
    public void setFridayOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        cbWeekCycleFriday.setOnCheckedChangeListener(listener);
    }

    public void setSaturdayOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        cbWeekCycleSaturday.setOnCheckedChangeListener(listener);
    }
    
    
}
