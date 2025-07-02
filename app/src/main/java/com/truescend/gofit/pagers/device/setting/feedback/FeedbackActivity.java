package com.truescend.gofit.pagers.device.setting.feedback;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sn.utils.SNToast;
import com.sn.utils.view.ViewCompat;
import com.truescend.gofit.R;
import com.truescend.gofit.pagers.base.BaseActivity;
import com.truescend.gofit.pagers.common.dialog.LoadingDialog;
import com.truescend.gofit.pagers.device.setting.DeviceSettingActivity;
import com.truescend.gofit.utils.Constant;
import com.truescend.gofit.utils.ResUtil;
import com.truescend.gofit.views.TitleLayout;


import static com.truescend.gofit.utils.ResUtil.getString;


/**
 * 功能：意见反馈界面
 * 作者:东芝(2018/2/3).
 * 描述:反馈
 */
public class FeedbackActivity extends BaseActivity<FeedbackPresenter, IFeedbackContract.IView> implements IFeedbackContract.IView {

    TitleLayout tlTitle;

    EditText etFeedBackInput;

    EditText etFeedBackEmail;

    EditText etFeedBackBand;

    TextView tvFeedBackPhone;

    TextView tvFeedBackSubmit;

    TextView tvFeedBackHelp;

    @Override
    protected FeedbackPresenter initPresenter() {
        return new FeedbackPresenter(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {
         tlTitle = findViewById(R.id.tlTitle);
         etFeedBackInput = findViewById(R.id.etFeedBackInput);
         etFeedBackEmail = findViewById(R.id.etFeedBackEmail);
        etFeedBackBand = findViewById(R.id.etFeedBackBand);
        tvFeedBackPhone = findViewById(R.id.tvFeedBackPhone);
        tvFeedBackSubmit = findViewById(R.id.tvFeedBackSubmit);
        tvFeedBackHelp = findViewById(R.id.tvFeedBackHelp);

        tvFeedBackSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = ViewCompat.getText(etFeedBackInput);
                String email = ViewCompat.getText(etFeedBackEmail);
                String deviceName = ViewCompat.getText(etFeedBackBand);
                getPresenter().requestSendFeedbackMessage(FeedbackActivity.this, email, deviceName, msg);
            }
        });


        initTitle();
        initView();
    }

    private void initTitle() {
        tlTitle.setTitle(getString(R.string.title_feedback));
    }

    private void initView() {


        tvFeedBackHelp.setText(ResUtil.formatHtml(getString(R.string.content_troubleshooting_help), Constant.URL.HELP_VERIFICATION_CODE));
        tvFeedBackHelp.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onSendFeedbackMessageError(String msg) {
        SNToast.toast(msg);
    }

    @Override
    public void onSendFeedbackMessageSuccess() {
        SNToast.toast(R.string.toast_feedback_success);
        finish();
    }



    @Override
    public void onInputEmailError(String msg) {
        setEditTextErrorTips(etFeedBackEmail, msg);
    }

    @Override
    public void onInputContentError(String msg) {
        setEditTextErrorTips(etFeedBackInput, msg);
    }

    @Override
    public void onInputDeviceNameError(String msg) {
        setEditTextErrorTips(etFeedBackBand, msg);
    }

    @Override
    public void setInputEmail(String value) {
        etFeedBackEmail.setText(value);
//        etFeedBackEmail.setKeyListener(null);
    }

    @Override
    public void setInputDeviceName(String value) {
        etFeedBackBand.setText(value);
        etFeedBackBand.setKeyListener(null);
    }

    @Override
    public void setPhoneName(String value) {
        tvFeedBackPhone.setText(value);
    }

    @Override
    public void onShowLoading(boolean isShow) {
        if (isShow) {
            LoadingDialog.show(this, R.string.dialog_waiting);
        } else {
            LoadingDialog.dismiss();
        }
    }

    /**
     * 设置编辑框错误提示信息
     *
     * @param et    编辑框控件
     * @param error 错误信息
     */
    private void setEditTextErrorTips(final EditText et, CharSequence error) {
        et.setFocusable(true);
        et.setFocusableInTouchMode(true);
        et.requestFocus();
        et.setError(error);
    }

}
