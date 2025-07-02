package com.truescend.gofit.pagers.device;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.sn.blesdk.ble.SNBLEHelper;
import com.sn.blesdk.net.bean.DeviceInfo;
import com.truescend.gofit.R;
import com.truescend.gofit.pagers.base.BaseFragment;
import com.truescend.gofit.pagers.common.dialog.SearchBandDialog;
import com.truescend.gofit.pagers.device.bean.ItemDeviceIcon;
import com.truescend.gofit.utils.PageJumpUtil;
import com.truescend.gofit.views.BadgeHelper;
import com.truescend.gofit.views.BatteryView;


/**
 * 功能:设备管理界面;
 * Author:Created by 泽鑫 on 2017/11/20 09:36.
 */
public class DeviceFragment extends BaseFragment<DevicePresenterImpl, IDeviceContract.IView> implements IDeviceContract.IView , View.OnClickListener {


    ViewSwitcher vsDeviceSwitch;

    TextView tvDeviceBandName;

    ImageView ivDeviceSetting;


    BatteryView ivDeviceElectric;


    TextView tvDeviceMac;


    View ilDeviceWallpaper;

    View ilDeviceWallpaperTempLeft;

    View ilDeviceWallpaperTempRight;

    View ilDeviceRemoteCamera;

    View ilDeviceSearchBand;

    View ilDeviceHealthReminder;

    View ilDeviceScheduleReminder;

    View ilDeviceAlarmClock;

    View ilDevicePush;

    View llTitle;

    Button btDeviceDialogButton;

    ProgressBar pbDeviceDialogLoading;

    TextView tvDeviceDialogTitle;

    private ItemDeviceIcon wallpaperItem;
    private ItemDeviceIcon cameraItem;
    private ItemDeviceIcon searchItem;
    private ItemDeviceIcon healthReminderItem;
    private ItemDeviceIcon alarmClockItem;
    private ItemDeviceIcon scheduleReminderItem;
    private ItemDeviceIcon pushItem;
    //未读信息小红点
    private BadgeHelper badgeHelper;

    @Override
    public DevicePresenterImpl initPresenter() {
        return new DevicePresenterImpl(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_device;
    }

    @Override
    protected void onCreate(View view) {

         vsDeviceSwitch = view.findViewById(R.id.vsDeviceSwitch);
        tvDeviceBandName = view.findViewById(R.id.tvDeviceBandName);
         ivDeviceSetting = view.findViewById(R.id.ivDeviceSetting);

         ivDeviceElectric = view.findViewById(R.id.ivDeviceElectric);

         tvDeviceMac = view.findViewById(R.id.tvDeviceMac);

         ilDeviceWallpaper = view.findViewById(R.id.ilDeviceWallpaper);
         ilDeviceWallpaperTempLeft = view.findViewById(R.id.ilDeviceWallpaperTempLeft);
         ilDeviceWallpaperTempRight= view.findViewById(R.id.ilDeviceWallpaperTempRight);
         ilDeviceRemoteCamera = view.findViewById(R.id.ilDeviceRemoteCamera);
         ilDeviceSearchBand= view.findViewById(R.id.ilDeviceSearchBand);
        ilDeviceHealthReminder = view.findViewById(R.id.ilDeviceHealthReminder);
         ilDeviceScheduleReminder = view.findViewById(R.id.ilDeviceScheduleReminder);
         ilDeviceAlarmClock= view.findViewById(R.id.ilDeviceAlarmClock);
        ilDevicePush = view.findViewById(R.id.ilDevicePush);
         llTitle = view.findViewById(R.id.llTitle);
       btDeviceDialogButton = view.findViewById(R.id.btDeviceDialogButton);
         pbDeviceDialogLoading = view.findViewById(R.id.pbDeviceDialogLoading);
         tvDeviceDialogTitle = view.findViewById(R.id.tvDeviceDialogTitle);

        btDeviceDialogButton.setOnClickListener(this);
        ivDeviceSetting.setOnClickListener(this);
        ilDeviceRemoteCamera.setOnClickListener(this);
        ilDeviceSearchBand.setOnClickListener(this);
        ilDeviceHealthReminder.setOnClickListener(this);
        ilDeviceScheduleReminder.setOnClickListener(this);
        ilDeviceAlarmClock.setOnClickListener(this);
        ilDeviceWallpaper.setOnClickListener(this);
        ilDevicePush.setOnClickListener(this);



        initItem();
    }


    /**
     * 初始化item布局
     */
    private void initItem() {
        badgeHelper = new BadgeHelper(getActivity())
                .setBadgeType(BadgeHelper.Type.TYPE_TEXT)
                .setBadgeOverlap(true);
        badgeHelper.bindToTargetView(ilDeviceScheduleReminder.findViewById(R.id.ivDeviceIconImage));


        wallpaperItem = new ItemDeviceIcon(ilDeviceWallpaper);
        wallpaperItem.setIcon(R.mipmap.icon_wallpaper);
        wallpaperItem.setTitle(R.string.title_change_wallpaper);


        cameraItem = new ItemDeviceIcon(ilDeviceRemoteCamera);
        cameraItem.setIcon(R.mipmap.icon_camera);
        cameraItem.setTitle(R.string.item_device_remote_camera);

        searchItem = new ItemDeviceIcon(ilDeviceSearchBand);
        searchItem.setIcon(R.mipmap.icon_search_band);
        searchItem.setTitle(R.string.item_device_search_band);

        healthReminderItem = new ItemDeviceIcon(ilDeviceHealthReminder);
        healthReminderItem.setIcon(R.mipmap.icon_health_reminder);
        healthReminderItem.setTitle(R.string.item_device_health_reminder);

        alarmClockItem = new ItemDeviceIcon(ilDeviceAlarmClock);
        alarmClockItem.setIcon(R.mipmap.icon_alarm_clock);
        alarmClockItem.setTitle(R.string.item_device_alarm_clock);

        scheduleReminderItem = new ItemDeviceIcon(ilDeviceScheduleReminder);
        scheduleReminderItem.setIcon(R.mipmap.icon_schedule_reminder);
        scheduleReminderItem.setTitle(R.string.item_device_schedule_reminder);

        pushItem = new ItemDeviceIcon(ilDevicePush);
        pushItem.setIcon(R.mipmap.icon_push);
        pushItem.setTitle(R.string.item_device_push);


    }

    @Override
    protected void onVisible() {
        super.onVisible();
        getPresenter().requestGetDeviceInfo();
        getPresenter().requestGetBandName();
        getPresenter().requestGetMacAddress();
        getPresenter().requestUnreadSchedule();
        getPresenter().requestGetElectric();
    }



    public void onClick(View view) {
        if (!SNBLEHelper.isConnected()) {
            PageJumpUtil.startScanningAndBindActivity(getContext());
            return;
        }
        switch (view.getId()) {
            case R.id.ilDeviceWallpaper:
                PageJumpUtil.startDeviceWallpaperActivity(getContext());
                break;
            case R.id.ivDeviceSetting:
                PageJumpUtil.startDeviceSettingActivity(getContext());
                break;
            case R.id.btDeviceDialogButton:
                PageJumpUtil.startScanningAndBindActivity(getContext());
                break;
            case R.id.ilDeviceRemoteCamera:
                PageJumpUtil.startRemoteCameraActivity(getContext());
                break;
            case R.id.ilDeviceSearchBand:
                SearchBandDialog dialog = new SearchBandDialog(getContext());
                dialog.show();
                break;
            case R.id.ilDeviceHealthReminder:
                PageJumpUtil.startHealthReminderActivity(getContext());
                break;
            case R.id.ilDeviceScheduleReminder:
                PageJumpUtil.startScheduleActivity(getContext());
                break;
            case R.id.ilDeviceAlarmClock:
                PageJumpUtil.startAlarmClockActivity(getContext());
                break;
            case R.id.ilDevicePush:
                PageJumpUtil.startPushActivity(getContext());
                break;

        }
    }

    @Override
    public void updateBandName(String name) {
        tvDeviceBandName.setText(name);
    }

    @Override
    public void updateMacAddress(String mac) {
        tvDeviceMac.setText(mac);
    }

    @Override
    public void updateElectric(int electric, int curLevelStep, int maxLevelStep) {
        if (maxLevelStep < 1 && curLevelStep < 1) {
            ivDeviceElectric.setMax(4);
            if (electric <= 10) {
                ivDeviceElectric.setProgress(1);
            } else if (electric < 40) {
                ivDeviceElectric.setProgress(2);
            } else if (electric < 80) {
                ivDeviceElectric.setProgress(3);
            } else {
                ivDeviceElectric.setProgress(4);
            }
        } else {
            ivDeviceElectric.setMax(maxLevelStep - 1);
            ivDeviceElectric.setProgress(curLevelStep);
        }

    }

    @Override
    public void updateUnreadMessages(int number) {
        //设置未读信息
        badgeHelper.setBadgeNumber(number);
    }

    @Override
    public void updateDeviceInfo(DeviceInfo deviceInfo) {
        if (deviceInfo != null) {
            boolean supportWallpaper = deviceInfo.isSupportWallpaper();
            ilDeviceWallpaper.setVisibility(supportWallpaper ? View.VISIBLE : View.GONE);
            ilDeviceWallpaperTempLeft.setVisibility(supportWallpaper ? View.VISIBLE : View.GONE);
            ilDeviceWallpaperTempRight.setVisibility(supportWallpaper ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void updateDeviceStatus(boolean isConnected, boolean isUnbind) {
        if (vsDeviceSwitch == null) {
            return;
        }
        getPresenter().requestGetBandName();
        if (isConnected) {
            getPresenter().requestGetDeviceInfo();
            getPresenter().requestGetMacAddress();
            getPresenter().requestUnreadSchedule();
            getPresenter().requestGetElectric();
        }
        if (isUnbind) {
            llTitle.setVisibility(View.GONE);
            vsDeviceSwitch.setDisplayedChild(1);
            pbDeviceDialogLoading.setVisibility(View.GONE);
            tvDeviceDialogTitle.setText(R.string.content_device_no_bind);
            btDeviceDialogButton.setText(R.string.content_scan_device);
        } else {
            llTitle.setVisibility(View.VISIBLE);
            vsDeviceSwitch.setDisplayedChild(0);
            pbDeviceDialogLoading.setVisibility(View.GONE);
            tvDeviceDialogTitle.setText(R.string.content_reconnecting);
            btDeviceDialogButton.setText(R.string.content_manual_reconnect);
        }
    }
}
