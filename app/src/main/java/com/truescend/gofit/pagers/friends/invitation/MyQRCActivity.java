package com.truescend.gofit.pagers.friends.invitation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sn.app.db.data.user.UserBean;
import com.sn.app.utils.AppUserUtil;
import com.sn.utils.SNToast;
import com.truescend.gofit.R;
import com.truescend.gofit.pagers.base.BaseActivity;
import com.truescend.gofit.pagers.home.diet.setting.DietTargetSettingActivity;
import com.truescend.gofit.utils.AppShareUtil;
import com.truescend.gofit.utils.ResUtil;
import com.truescend.gofit.utils.StatusBarUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 作者:东芝(2018/08/22).
 * 功能:面对面邀请好友
 */
public class MyQRCActivity extends BaseActivity<MyQRCPresenterImpl, IMyQRCContract.IView> implements IMyQRCContract.IView {



    ImageView ivWindowBackground;

    ImageView ivBack;

    CircleImageView civUserHeadPortrait;

    TextView tvNickname;

    TextView tvSign;

    TextView tvIdName;

    Button btnSaveImage;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, MyQRCActivity.class));
    }

    @Override
    protected MyQRCPresenterImpl initPresenter() {
        return new MyQRCPresenterImpl(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_my_qrcode;
    }

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {
         ivWindowBackground = findViewById(R.id.ivBackground);
         ivBack = findViewById(R.id.ivBack);
        civUserHeadPortrait = findViewById(R.id.civUserHeadPortrait);
        tvNickname = findViewById(R.id.tvNickname);
         tvSign = findViewById(R.id.tvSign);
        tvIdName = findViewById(R.id.tvIdName);
        btnSaveImage = findViewById(R.id.btnSaveImage);

        findViewById(R.id.ivShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppShareUtil.shareCapture(MyQRCActivity.this);
            }
        });

        btnSaveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter().saveScreenshots();
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        StatusBarUtil.setRootViewFitsSystemWindows(this, false);
        StatusBarUtil.setStatusBarDarkTheme(this, false);
        StatusBarUtil.setStatusBarColor(this, 0x00000000);
        setHDBackground();
        getTitleLayout().setTitleShow(false);


        UserBean user = AppUserUtil.getUser();
        Glide.with(this)
                .asBitmap()
                .load(user.getPortrait())
                .apply(RequestOptions.errorOf(R.mipmap.img_test_picture))
                .apply(RequestOptions.placeholderOf(R.mipmap.img_test_picture))
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(civUserHeadPortrait);

        tvNickname.setText(user.getNickname());
        tvIdName.setText(ResUtil.format("ID:%d", user.getUser_id()));
        tvSign.setText(user.getSign());
    }

    private void setHDBackground() {
        Glide.with(this)
                .load(R.mipmap.icon_my_qrc)
                .into(ivWindowBackground);
    }




    @Override
    public void onSaveScreenshots(String path) {
        btnSaveImage.setEnabled(false);
        btnSaveImage.setClickable(false);
        SNToast.toast(getString(R.string.content_save_picture_to) + path);
    }
}
