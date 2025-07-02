package com.truescend.gofit.pagers.home.sleep.details;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.truescend.gofit.App;
import com.truescend.gofit.R;
import com.truescend.gofit.pagers.base.BaseActivity;
import com.truescend.gofit.pagers.home.bean.ItemDetailsTitle;
import com.truescend.gofit.pagers.home.bean.ItemStatus;
import com.truescend.gofit.utils.AppShareUtil;
import com.truescend.gofit.utils.ResUtil;
import com.truescend.gofit.views.BarChartView;
import com.truescend.gofit.views.TitleLayout;
import com.truescend.gofit.views.bean.LabelMonth;
import com.truescend.gofit.views.bean.LabelWeek;
import com.truescend.gofit.views.bean.LabelYear;
import java.util.List;


/**
 * 功能：睡眠详情界面
 * Author:Created by 泽鑫 on 2017/12/7 15:54.
 */

public class SleepDetailsActivity extends BaseActivity<SleepDetailsPresenterImpl, ISleepDetailsContract.IView> implements ISleepDetailsContract.IView {

    TitleLayout ilTitle;

    View ilSleepDetailsTitle;

    View ilSleepDetailsStandardDays;

    View ilSleepDetailsDailySteps;

    View ilSleepDetailsDailyConsume;

    View ilSleepDetailsTotalDistance;

    View ilSleepDetailsDailyDistance;

    View ilSleepDetailsTargetSteps;

    BarChartView bcvSleepChart;

    TextView tvDate;


    private ItemDetailsTitle sleepDetailsTitle;//界面标题

    private ItemStatus greaterThanGoodDaysStatus;//大于良好天数
    private ItemStatus validTotalSleepTimeStatus;//有效睡眠时长
    private ItemStatus validAvgSleepTimeStatus;//日均有效睡眠时长
    private ItemStatus maxSingleSleepTimeStatus;//单词最长睡眠
    private ItemStatus validSleepPercentStatus;//有效睡眠占比
    private ItemStatus qualityOfSleepStatus;//睡眠质量

    @Override
    protected SleepDetailsPresenterImpl initPresenter() {
        return new SleepDetailsPresenterImpl(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_sleep_details;
    }

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {
      ilTitle = findViewById(R.id.tlTitle);
         ilSleepDetailsTitle = findViewById(R.id.ilSleepDetailsTitle);
        ilSleepDetailsStandardDays= findViewById(R.id.ilSleepDetailsStandardDays);
         ilSleepDetailsDailySteps= findViewById(R.id.ilSleepDetailsDailySteps);
         ilSleepDetailsDailyConsume= findViewById(R.id.ilSleepDetailsDailyConsume);
        ilSleepDetailsTotalDistance= findViewById(R.id.ilSleepDetailsTotalDistance);
         ilSleepDetailsDailyDistance = findViewById(R.id.ilSleepDetailsDailyDistance);
         ilSleepDetailsTargetSteps = findViewById(R.id.ilSleepDetailsTargetSteps);
         bcvSleepChart = findViewById(R.id.bcvSleepChart);
         tvDate = findViewById(R.id.tvDate);



        initTitle();
        initView();
        simulationData();
        initStepChart();
        getPresenter().requestLoadWeekChart(App.getSelectedCalendar());
    }

    private void initStepChart() {
        bcvSleepChart.setDrawLabel(true);
        bcvSleepChart.setDrawBorder(true);
        bcvSleepChart.setDrawLabelLimit(true);
        bcvSleepChart.setDrawLimitLine(false);
        bcvSleepChart.setDrawZeroLimitLine(true);
        bcvSleepChart.setBarColor(0xFF7046A8);


        //分割线
        bcvSleepChart.setLimitLine(new float[]{0, 1, 2, 3, 4}, new String[]{"", getString(R.string.content_bad), getString(R.string.content_general), getString(R.string.content_well), getString(R.string.content_good)});
    }


    @Override
    public void onUpdateWeekChartData(List<Integer> data) {
        bcvSleepChart.setBarWidth(BarChartView.BAR_WIDTH_MEDIUM);
        bcvSleepChart.setDataType(new LabelWeek());
        bcvSleepChart.setData(data);

    }

    @Override
    public void onUpdateMonthChartData(List<Integer> data) {
        bcvSleepChart.setBarWidth(BarChartView.BAR_WIDTH_MEDIUM);
        bcvSleepChart.setDataType(new LabelMonth());
        bcvSleepChart.setData(data);
    }

    @Override
    public void onUpdateYearChartData(List<Integer> data) {
        bcvSleepChart.setBarWidth(BarChartView.BAR_WIDTH_MEDIUM);
        bcvSleepChart.setDataType(new LabelYear());
        bcvSleepChart.setData(data);
    }

    @Override
    public void onUpdateDateRange(String dateFromAndTo) {
        tvDate.setText(dateFromAndTo);
    }

    /**
     * 初始化标题
     */
    private void initTitle() {
        ilTitle.setTitleShow(false);
        sleepDetailsTitle = new ItemDetailsTitle(ilSleepDetailsTitle);
        sleepDetailsTitle.setFirstText(R.string.unit_week);
        sleepDetailsTitle.setSecondText(R.string.unit_month);
        sleepDetailsTitle.setThirdText(R.string.unit_year);

        sleepDetailsTitle.setLeftIconOnClickListener(onClick);
        sleepDetailsTitle.setRightIconOnClickListener(onClick);
        sleepDetailsTitle.setFirstOnClickListener(onClick);
        sleepDetailsTitle.setSecondOnClickListener(onClick);
        sleepDetailsTitle.setThirdOnClickListener(onClick);
    }

    /**
     * 点击事件
     */
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivDetailsTitleLeftIcon:
                    onBackPressed();
                    break;
                case R.id.ivDetailsTitleRightIcon:
                    AppShareUtil.shareCapture(SleepDetailsActivity.this);
                    break;
                case R.id.rbDetailsTitleFirst:
                    getPresenter().requestLoadWeekChart(App.getSelectedCalendar());
                    break;
                case R.id.rbDetailsTitleSecond:
                    getPresenter().requestLoadMonthChart(App.getSelectedCalendar());
                    break;
                case R.id.rbDetailsTitleThird:
                    getPresenter().requestLoadYearChart(App.getSelectedCalendar());
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 初始化视图
     */
    private void initView() {
        greaterThanGoodDaysStatus = new ItemStatus(ilSleepDetailsStandardDays);
        greaterThanGoodDaysStatus.setSubTitle(R.string.content_greatest_good);//填充格子标题文字

        validTotalSleepTimeStatus = new ItemStatus(ilSleepDetailsDailySteps);
        validTotalSleepTimeStatus.setSubTitle(R.string.content_total_time_sleep);//填充格子标题文字

        validAvgSleepTimeStatus = new ItemStatus(ilSleepDetailsDailyConsume);
        validAvgSleepTimeStatus.setSubTitle(R.string.content_daily_time_sleep);//填充格子标题文字

        maxSingleSleepTimeStatus = new ItemStatus(ilSleepDetailsTotalDistance);
        maxSingleSleepTimeStatus.setSubTitle(R.string.content_longest_single_sleep);//填充格子标题文字

        validSleepPercentStatus = new ItemStatus(ilSleepDetailsDailyDistance);
        validSleepPercentStatus.setSubTitle(R.string.content_proportion_of_sleep);//填充格子标题文字

        qualityOfSleepStatus = new ItemStatus(ilSleepDetailsTargetSteps);
        qualityOfSleepStatus.setSubTitle(R.string.content_quality_sleep);//填充格子标题文字

    }

    /**
     * 模拟数据填充
     */
    private void simulationData() {

    }


    @Override
    public void onUpdateItemChartData(String goodDays, CharSequence validTotalSleepTime, CharSequence validAvgSleepTime, CharSequence maxSingleSleepTime, int validSleepPercent, String quality) {
        greaterThanGoodDaysStatus.setTitle(goodDays);

        validTotalSleepTimeStatus.setTitle(validTotalSleepTime);

        validAvgSleepTimeStatus.setTitle(validAvgSleepTime);

        maxSingleSleepTimeStatus.setTitle(maxSingleSleepTime);

        validSleepPercentStatus.setTitle(ResUtil.format("%d%%", validSleepPercent));

        qualityOfSleepStatus.setTitle(quality);
    }

}
