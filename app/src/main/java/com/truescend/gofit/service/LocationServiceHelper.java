package com.truescend.gofit.service;

import android.content.Context;
import android.location.Address;

import com.amap.api.location.AMapLocation;
import com.sn.map.bean.SNLocation;
import com.sn.map.impl.GaoDeLocationImpl;
import com.sn.map.impl.GoogleLocationImpl;
import com.sn.map.interfaces.ILocation;
import com.sn.utils.SNLog;
import com.truescend.gofit.utils.MapType;

import java.io.Closeable;
import java.lang.ref.WeakReference;

/**
 * 作者:东芝(2018/3/10).
 * 功能:位置定位服务
 */

public class LocationServiceHelper implements ILocation.LocationListener, Closeable {
    private ILocation location;
    private WeakReference<Context> context;


    public void startLocation() {
        if (location != null) {
            SNLog.i("天气服务:开始定位");
            location.start();
        }
    }

    public LocationServiceHelper(Context context) {
        this.context = new WeakReference<Context>(context.getApplicationContext());
        switchLocationType();
    }

    public void switchLocationType() {
        Context context = getContext();
        if (context == null) return;

        switch (MapType.getSmartMapType()) {
            case UNKNOWN:
                SNLog.i("天气服务:地图类型:未知,暂时选用高德地图作为默认定位");
            case A_MAP:
                SNLog.i("天气服务:地图类型:高德");
                location = new GaoDeLocationImpl(context, 1, 1);
                break;
            case GOOGLE_MAP:
                if (MapType.isGooglePlayServicesAvailable(context)) {
                    SNLog.i("天气服务:地图类型:谷歌");
                    location = new GoogleLocationImpl(context, 1000, 1000);
                }else{
                    SNLog.i("天气服务:地图类型:谷歌,但无法使用!用户未安装谷歌服务");
                }
                break;
            default:
                break;
        }
        if (location != null) {
            location.setLocationListener(this);
        }
    }

    @Override
    public void onLocationChanged(SNLocation location) {
        double latitude = 0;
        double longitude = 0;
        String city = null;
        if (location != null) {
            if (location.getLocation() instanceof Address) {
                Address addr = location.getLocation();
                if (addr != null && addr.hasLatitude() && addr.hasLongitude()) {
                    city = addr.getLocality();
                    latitude = addr.getLatitude();
                    longitude = addr.getLongitude();
                }
            }
            if (location.getLocation() instanceof AMapLocation) {
                AMapLocation addr = location.getLocation();
                if (addr != null && addr.getLatitude() > 0 && addr.getLongitude() > 0) {
                    city = addr.getCity();
                    latitude = addr.getLatitude();
                    longitude = addr.getLongitude();
                }
            }
        }
        SNLog.i("天气服务:定位成功:"+city);
        if (onLocationListener != null) {
            onLocationListener.onLocationChanged(city, latitude, longitude);
        }
    }

    @Override
    public void onSignalChanged(int level) {

    }

    public void refreshLocationType() {
        switchLocationType();
    }

    public void pause() {
        if (location != null) {
            location.stop();
        }
    }

    @Override
    public void close() {
        pause();
        if (this.context != null) {
            this.context.clear();
        }
    }

    private OnLocationListener onLocationListener;

    public void setOnLocationListener(OnLocationListener onLocationListener) {
        this.onLocationListener = onLocationListener;
    }

    public Context getContext() {
        return this.context.get();
    }

    public interface OnLocationListener {
        void onLocationChanged(String city, double latitude, double longitude);
    }
}
