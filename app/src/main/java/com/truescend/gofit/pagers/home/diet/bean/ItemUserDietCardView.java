package com.truescend.gofit.pagers.home.diet.bean;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.truescend.gofit.R;
import com.truescend.gofit.pagers.common.bean.ItemBase;



/**
 * 用户饮食卡片布局
 */
public class ItemUserDietCardView extends ItemBase {

    View llDietStatistics;

    View llDietFoodsExtraMeal;

    View rlDietPlanThinBodyStart;

    TextView tvDietPlanThinBodyStart;

    View llDietTargetSetting;

    TextView tvCalorieData;

    TextView tvDietMealDetails;

    public ItemUserDietCardView(View view) {
        super(view);

       llDietStatistics = view.findViewById(R.id.llDietStatistics);
        llDietFoodsExtraMeal = view.findViewById(R.id.llDietListMeal);
         rlDietPlanThinBodyStart = view.findViewById(R.id.rlDietPlanThinBodyStart);
         tvDietPlanThinBodyStart = view.findViewById(R.id.tvDietPlanThinBodyStart);
         llDietTargetSetting = view.findViewById(R.id.llDietTargetSetting);
        tvCalorieData = view.findViewById(R.id.tvCalorieData);
        tvDietMealDetails = view.findViewById(R.id.tvDietMealDetails);
    }


    public void setDietStatisticsClickListener(View.OnClickListener listener) {
        llDietStatistics.setOnClickListener(listener);
    }

    public void setDietListMealClickListener(View.OnClickListener listener) {
        llDietFoodsExtraMeal.setOnClickListener(listener);
    }

    public void setDietTargetSettingClickListener(View.OnClickListener listener) {
        llDietTargetSetting.setOnClickListener(listener);
    }

    public void setDietPlanThinBodyButtonEnable(boolean enable) {
        rlDietPlanThinBodyStart.setVisibility(enable ? View.VISIBLE : View.GONE);
    }

    public void setDietPlanThinBodyButtonClickListener(View.OnClickListener listener) {
        tvDietPlanThinBodyStart.setOnClickListener(listener);
    }

    public void setDietCalorieData(CharSequence content) {
        tvCalorieData.setText(content);
    }

    public void setDietMealDetails(CharSequence content) {
        if (TextUtils.isEmpty(content)) {
            tvDietMealDetails.setText("");
            tvDietMealDetails.setVisibility(View.GONE);
        }else {
            tvDietMealDetails.setText(content);
            tvDietMealDetails.setVisibility(View.VISIBLE);
        }
    }
}
