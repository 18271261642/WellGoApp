package com.truescend.gofit.pagers.device.push;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.sn.app.db.data.config.DeviceConfigBean;
import com.sn.app.db.data.config.bean.RemindConfig;
import com.sn.app.db.data.config.bean.TimeCycleSwitch;
import com.sn.blesdk.ble.DeviceType;
import com.sn.blesdk.net.bean.DeviceInfo;
import com.truescend.gofit.R;
import com.truescend.gofit.pagers.base.BaseActivity;
import com.truescend.gofit.pagers.common.bean.ItemBannerButton;
import com.truescend.gofit.pagers.common.dialog.TimeCyclePickerDialog;
import com.truescend.gofit.pagers.device.adapter.AppItemsAdapter;
import com.truescend.gofit.pagers.device.bean.ItemApps;
import com.truescend.gofit.pagers.device.bean.ItemDeviceCommon;
import com.truescend.gofit.pagers.device.bean.ItemPush;
import com.truescend.gofit.utils.PermissionUtils;
import com.truescend.gofit.utils.RecycleViewUtil;
import com.truescend.gofit.utils.ResUtil;
import com.truescend.gofit.views.TitleLayout;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 功能：消息推送页面
 * Author:Created by 泽鑫 on 2017/11/24 17:28.
 */

public class PushActivity extends BaseActivity<PushPresenterImpl, IPushContract.IView> implements IPushContract.IView {


    View ilPushDoNotDisturb;

    View ilPushOtherApps;

    View ilPushCallReminder;

    View ilPushMessageReminder;

    View ilPushTip;


    LinearLayout llPushApp;


    RecyclerView rvOtherApps;


    private ItemDeviceCommon doNotDisturbItem;
    private ItemPush callItem;
    private ItemPush messageItem;
    private ItemBannerButton pushTipItem;
    private AppItemsAdapter mAppItemsAdapter;

    @Override
    protected PushPresenterImpl initPresenter() {
        return new PushPresenterImpl(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_push;
    }

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {

        ilPushTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PermissionUtils.openAccessibility(PushActivity.this);
            }
        });


        initItem();
        getPresenter().requestDeviceConfig();
    }

    @Override
    protected void onCreateTitle(TitleLayout titleLayout) {
        ilPushDoNotDisturb = findViewById(R.id.ilPushDoNotDisturb);
        ilPushOtherApps = findViewById(R.id.ilPushOtherApps);
        ilPushCallReminder = findViewById(R.id.ilPushCallReminder);
        ilPushMessageReminder = findViewById(R.id.ilPushMessageReminder);
        ilPushTip = findViewById(R.id.ilPushTip);

        llPushApp = findViewById(R.id.llPushApp);

        rvOtherApps = findViewById(R.id.rvOtherApps);
        titleLayout.setTitle(getString(R.string.title_message_push));
        titleLayout.setLeftIconFinishActivity(this);
        mAppItemsAdapter = new AppItemsAdapter(this);
        RecycleViewUtil.setAdapter(rvOtherApps, mAppItemsAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initItem() {
        callItem = new ItemPush(ilPushCallReminder);
        callItem.setIcon(R.mipmap.icon_call_reminder);
        callItem.setTitle(R.string.content_call_reminder);

        messageItem = new ItemPush(ilPushMessageReminder);
        messageItem.setIcon(R.mipmap.icon_message_reminder);
        messageItem.setTitle(R.string.content_message_reminder);

        doNotDisturbItem = new ItemDeviceCommon(ilPushDoNotDisturb);
        doNotDisturbItem.setTitle(R.string.content_do_not_disturb);
        DeviceInfo currentDeviceInfo = DeviceType.getCurrentDeviceInfo();
        if (currentDeviceInfo != null && currentDeviceInfo.isSupportBandSelfSetting()) {
            doNotDisturbItem.setIntervalTime(R.string.content_app_unsupport_tips);
            doNotDisturbItem.setIntervalTimeVisibility(View.VISIBLE);
            doNotDisturbItem.setItemClickable(false);
        } else {
            doNotDisturbItem.setItemClickable(true);
            doNotDisturbItem.setIntervalTimeVisibility(View.GONE);
        }
        ItemDeviceCommon otherAppsItem = new ItemDeviceCommon(ilPushOtherApps);
        otherAppsItem.setTitle("其它应用推送");
        otherAppsItem.setIntervalTimeVisibility(View.GONE);
        otherAppsItem.setTimeVisibility(View.INVISIBLE);
        otherAppsItem.setSettingIconVisibility(View.INVISIBLE);
        otherAppsItem.setSwitchVisibility(View.INVISIBLE);

        pushTipItem = new ItemBannerButton(ilPushTip);
        pushTipItem.setTitle(R.string.content_push_warm);
        pushTipItem.setContentVisibility(View.GONE);


    }



    @Override
    public void onUpdateDeviceConfig(final DeviceConfigBean bean) {
        final TimeCycleSwitch doNotDisturb = bean.getDoNotDisturb();
        doNotDisturbItem.setSwitchCheck(doNotDisturb.isOn());
        doNotDisturbItem.setTime(String.format("%s-%s", doNotDisturb.getStartTime(), doNotDisturb.getEndTime()));

        final RemindConfig remindConfig = bean.getRemindConfig();
        callItem.setSwitchChecked(remindConfig.isRemindCall());
        messageItem.setSwitchChecked(remindConfig.isRemindSMS());

        //填充推送app列表
        final List<RemindConfig.Apps> remindAppPushList = remindConfig.getRemindAppPushList();
        LayoutInflater inflater = getLayoutInflater();
        llPushApp.removeAllViews();
        for (int i = 0; i < remindAppPushList.size(); i++) {
            RemindConfig.Apps apps = remindAppPushList.get(i);
            View mItemView = inflater.inflate(R.layout.item_push, null, false);
            ItemPush mItemPush = new ItemPush(mItemView);
            mItemPush.setSwitchChecked(apps.isOn());
            mItemPush.setSwitchTag(i);//临时存储对象 用于事件回调时 能正确区分事件触发位置

            mItemPush.setTitle(getNameFromLanguageResource(apps.getAppName()));
            Glide.with(this).asDrawable().load(apps.getAppIconFile()).into(mItemPush.getIcon());
            mItemPush.setSwitchOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = (int) buttonView.getTag();
                    remindAppPushList.get(position).setOn(isChecked);
                    getPresenter().requestChangeDeviceConfigData(bean);
                }
            });
            llPushApp.addView(mItemView);
        }
        callItem.setSwitchOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                remindConfig.setRemindCall(isChecked);
                getPresenter().requestChangeDeviceConfigData(bean);
            }
        });
        messageItem.setSwitchOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                remindConfig.setRemindSMS(isChecked);
                getPresenter().requestChangeDeviceConfigData(bean);
            }
        });
        doNotDisturbItem.setSwitchOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                doNotDisturb.setOn(isChecked);
                getPresenter().requestChangeDeviceConfigData(bean);
            }
        });
        doNotDisturbItem.setSettingIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeCyclePickerDialog timeCyclePickerDialog = new TimeCyclePickerDialog(PushActivity.this, doNotDisturb.getStartTime(), doNotDisturb.getEndTime());
                timeCyclePickerDialog.setOnSettingListener(new TimeCyclePickerDialog.OnSettingListener() {
                    @Override
                    public void onTimeChanged(String startTime, String endTime) {
                        doNotDisturb.setStartTime(startTime);
                        doNotDisturb.setEndTime(endTime);
                        //存储变化的数据
                        getPresenter().requestChangeDeviceConfigData(bean);
                    }
                });
                timeCyclePickerDialog.show();
            }
        });
        getPresenter().requestLoadAllApps();
    }

    @Override
    public void onUpdateAllApps(List<ItemApps> itemApps) {
        mAppItemsAdapter.setList(itemApps);
    }

    private String getNameFromLanguageResource(String appName) {
        switch (appName) {
            case "FaceBook":
                return ResUtil.getString(R.string.reminder_facebook);
            case "Wechat":
                return ResUtil.getString(R.string.reminder_weChat);
            case "Line":
                return ResUtil.getString(R.string.reminder_line);
            case "Weibo":
                return ResUtil.getString(R.string.reminder_weibo);
            case "Linkedln":
                return ResUtil.getString(R.string.reminder_linkin);
            case "QQ":
                return ResUtil.getString(R.string.reminder_qq);
            case "GooglePlus":
                return ResUtil.getString(R.string.reminder_google_plus);
            case "WhatsApp":
            case "Viber":
            case "Email":
            default:
                return appName;
        }
    }
}
