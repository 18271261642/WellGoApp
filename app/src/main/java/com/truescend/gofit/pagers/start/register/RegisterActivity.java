package com.truescend.gofit.pagers.start.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.widget.NestedScrollView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sn.utils.IF;
import com.sn.utils.RegexUtil;
import com.sn.utils.SNToast;
import com.sn.utils.storage.SNStorage;
import com.truescend.gofit.R;
import com.truescend.gofit.ShowPrivacyDialogView;
import com.truescend.gofit.pagers.base.BaseActivity;
import com.truescend.gofit.pagers.common.dialog.LoadingDialog;
import com.truescend.gofit.pagers.device.bean.ItemWeek;
import com.truescend.gofit.views.AppCompatMultiLineSpinner;
import com.truescend.gofit.views.HintMultiLineEditText;


/**
 * 作者:东芝(2018/06/13).
 * 功能:新注册界面
 */
public class RegisterActivity extends BaseActivity<RegisterPresenterImpl, IRegisterContract.IView> implements IRegisterContract.IView, NestedScrollView.OnScrollChangeListener {


    HintMultiLineEditText etEmail;

    HintMultiLineEditText etEmailAgain;

    HintMultiLineEditText etRegisterPasswords;

    HintMultiLineEditText etRegisterConfirmPasswords;

    TextView tvRegister;

    HintMultiLineEditText etAnswer1;

    HintMultiLineEditText etAnswer2;

    NestedScrollView scrollView;

    AppCompatMultiLineSpinner spQuestion1;

    AppCompatMultiLineSpinner spQuestion2;
    private HintMultiLineEditText mLastErrorEditText;

    private CheckBox registerTerms;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, RegisterActivity.class));
    }

    @Override
    protected RegisterPresenterImpl initPresenter() {
        return new RegisterPresenterImpl(this);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {
        etEmail = findViewById(R.id.etEmail);
        etEmailAgain = findViewById(R.id.etEmailAgain);
         etRegisterPasswords = findViewById(R.id.etRegisterPasswords);
        etRegisterConfirmPasswords = findViewById(R.id.etRegisterConfirmPasswords);
         tvRegister = findViewById(R.id.tvRegister);
        etAnswer1 = findViewById(R.id.etAnswer1);
        etAnswer2 = findViewById(R.id.etAnswer2);
         scrollView = findViewById(R.id.scrollView);
         spQuestion1 = findViewById(R.id.spQuestion1);
        spQuestion2 = findViewById(R.id.spQuestion2);


        tvRegister.setOnClickListener(onClickListener);


        setTitle( R.string.title_register );
        scrollView.setOnScrollChangeListener(this);
        registerTerms = findViewById(R.id.registerTerms);
        registerTerms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                registerTerms.setChecked(b);
                SNStorage.setValue("is_privacy",b);
                if(compoundButton.isPressed()){
                    showPrivacyDialog();
                }
            }
        });

        boolean isAllow =  SNStorage.getValue("is_privacy",false);
        registerTerms.setChecked(isAllow);
    }


    private View.OnClickListener onClickListener= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.tvRegister){
                String email = etEmail.getText().toString().trim();
                String emailAgain = etEmailAgain.getText().toString().trim();
                String psw = etRegisterPasswords.getText().toString().trim();
                String pswAgain = etRegisterConfirmPasswords.getText().toString().trim();
                String answer1 = etAnswer1.getText().toString().trim();
                String answer2 = etAnswer2.getText().toString().trim();
                if (IF.isEmpty(email)) {
                    setEditTextErrorTips(etEmail, getString(R.string.content_input_email));
                    return;
                }
                if (!RegexUtil.isEmail(email)) {
                    setEditTextErrorTips(etEmail, getString(R.string.content_input_correct_email));
                    return;
                }
                if (!emailAgain.equals(email)) {
                    setEditTextErrorTips(etEmailAgain, getString(R.string.content_input_email_different));
                    return;
                }
                if (IF.isEmpty(psw)) {
                    setEditTextErrorTips(etRegisterPasswords, getString(R.string.content_input_password));
                    return;
                }

                if (psw.length() < 6 || psw.length() > 14) {
                    setEditTextErrorTips(etRegisterPasswords, getString(R.string.content_password_length));
                    return;
                }

                if (!psw.equals(pswAgain)) {
                    setEditTextErrorTips(etRegisterConfirmPasswords, getString(R.string.content_valid_input));
                    return;
                }
                if (IF.isEmpty(answer1)) {
                    setEditTextErrorTips(etAnswer1, getString(R.string.content_answer));
                    return;
                }
                if (IF.isEmpty(answer2)) {
                    setEditTextErrorTips(etAnswer2, getString(R.string.content_answer));
                    return;
                }
                int indexFromQuestion1 = spQuestion1.getSelectedItemPosition() + 1;
                int indexFromQuestion2 = 3 + spQuestion2.getSelectedItemPosition() + 1;

                if(!registerTerms.isChecked()){
                    Toast.makeText(RegisterActivity.this, getString(R.string.content_terms_accept), Toast.LENGTH_SHORT).show();

                    return;
                }

                getPresenter().requestRegister(email, psw, answer1, answer2, indexFromQuestion1, indexFromQuestion2);
            }
        }
    };






    /**
     * 设置编辑框错误提示信息
     *
     * @param et
     * @param error
     */
    private void setEditTextErrorTips(HintMultiLineEditText et, CharSequence error) {
        mLastErrorEditText = et;
        mLastErrorEditText.requestFocus();
        mLastErrorEditText.setError(error);

    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (mLastErrorEditText != null && mLastErrorEditText.getError() != null) {
            mLastErrorEditText.setError(null);
        }
    }

    @Override
    public void updateRegisterStatue(boolean isSuccess, String msg) {

            SNToast.toast(msg);
        if(isSuccess) {
            finish();
        }
    }



    @Override
    public void onShowLoading(boolean show) {
        if (show) {
            LoadingDialog.show(this, R.string.loading);
        } else {
            LoadingDialog.dismiss();
        }
    }

    private ShowPrivacyDialogView showPrivacyDialogView;
    private void showPrivacyDialog(){

        showPrivacyDialogView = new ShowPrivacyDialogView(this);
        showPrivacyDialogView.show();
        showPrivacyDialogView.setCancelable(false);
        showPrivacyDialogView.setOnPrivacyClickListener(new ShowPrivacyDialogView.OnPrivacyClickListener() {
            @Override
            public void onCancelClick() {
                showPrivacyDialogView.dismiss();
                registerTerms.setClickable(false);
                SNStorage.setValue("is_privacy",false);
                finish();

            }

            @Override
            public void onConfirmClick() {
                showPrivacyDialogView.dismiss();
                SNStorage.setValue("is_privacy",true);
                registerTerms.setClickable(true);
            }
        });
    }

}
