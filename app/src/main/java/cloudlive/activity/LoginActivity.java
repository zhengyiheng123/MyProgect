package cloudlive.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myprogect.R;
import com.talkfun.sdk.offline.PlaybackDownloader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cloudlive.TFApplication;
import cloudlive.consts.MainConsts;
import cloudlive.imageload.GlideImageLoader;
import cloudlive.manager.PopWindowManager;
import cloudlive.net.HttpRequest;
import cloudlive.util.ActivityUtil;
import cloudlive.util.CacheUtils;
import cloudlive.util.DimensionUtils;
import cloudlive.util.SharedPreferencesUtil;
import cloudlive.view.CustomSpinnerView;

/**
 * Created by Administrator on 2017/4/19 0019.
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.login_userId_layout)
    LinearLayout llUserIdLayout;
    @BindView(R.id.login_userId_label)
    TextView tvUserIdLabel;
    @BindView(R.id.login_userId_edit)
    EditText etUserIdEdit;
    @BindView(R.id.login_password_layout)
    LinearLayout llPasswordLayout;
    //    @BindView(R.id.login_password_label)
//    TextView tvPasswordLabel;
    @BindView(R.id.login_password_edit)
    EditText etPasswordEdit;
    //    @BindView(R.id.login_password_hint_tv)
//    TextView tvPasswordHint;
    @BindView(R.id.iv_arrow)
    ImageView ivArrow;
    @BindView(R.id.ll_nickname_layout)
    LinearLayout llNicknameLayout;
    @BindView(R.id.ed_nickname_edit)
    EditText etNicknameEdit;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_error_tip)
    TextView tvErrorTip;
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
    @BindView(R.id.cb_isSelected)
    CheckBox cbRememberId;
    private static final int QR_CODE_CODE = 0;
    public static final int LIVE_TYPE = 4;  //直播
    public static final int PLAYBACK_TYPE = 5; //点播
    private CustomSpinnerView customSpinnerView;
    private int type = LIVE_TYPE; //登录类型，直播/点播
    private ArrayList<String> idList;
    private ArrayList<String> checkIdList;
    private int listMaxSize = 5;
    private List<String> popupWindowListData;
    private long preClickTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getCache();
        initView();
        initEvent();

    }

    private void getCache() {
        idList = (ArrayList<String>) SharedPreferencesUtil.getStringList(this, SharedPreferencesUtil.SP_LOGIN_ID_LIST);
        checkIdList = (ArrayList<String>) SharedPreferencesUtil.getStringList(this, SharedPreferencesUtil.SP_LOGIN_ID_LIST_CHECK);
    }

    private void initView() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        customSpinnerView = new CustomSpinnerView(this);

        if (checkIdList.size() > 0) {
            etUserIdEdit.setText(checkIdList.get(0));
        }
    }

    public void initLogo() {
        String logoUrl = SharedPreferencesUtil.getString(this, SharedPreferencesUtil.SP_LOGIN_LOGO_URL);
        if (!TextUtils.isEmpty(logoUrl)) {
            ivLogo.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
            ivLogo.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
//            RequestOptions requestOptions = new RequestOptions();
//            requestOptions.placeholder(R.mipmap.huan_tuo_icon);
//            Glide.with(this).load(logoUrl).apply(requestOptions).into(ivLogo);
            GlideImageLoader.create(ivLogo).loadImage(logoUrl, R.mipmap.huan_tuo_icon);
        }
    }

    private void initEvent() {
        /**监听ID输入--焦点获取*/
        etUserIdEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                llUserIdLayout.setSelected(b);
                ivArrow.setSelected(false);
                tvUserIdLabel.setTextColor(b == true ? getResources().getColor(R.color.login_blue) : getResources().getColor(R.color.login_gray));
            }
        });

        /**监听密码输入--焦点获取*/
        etPasswordEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                llPasswordLayout.setSelected(b);
            }
        });

        /**监听昵称输入--焦点获取*/
        etNicknameEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                llNicknameLayout.setSelected(b);
                tvNickname.setTextColor(b == true ? getResources().getColor(R.color.login_blue) : getResources().getColor(R.color.login_gray));
            }
        });

        /**ID模式选择*/
        customSpinnerView.setOnSpinnerListener(new CustomSpinnerView.OnSpinnerListener() {
            @Override
            public void onItemClick(int position) {
                type = (position == 0) ? LIVE_TYPE : PLAYBACK_TYPE;
                tvUserIdLabel.setText(getResources().getString(position == 0 ? R.string.live_ID : R.string.playback_ID));
                llNicknameLayout.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onDismiss() {
                ivArrow.setSelected(false);
            }
        });

        etUserIdEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                PopWindowManager.getInstance(LoginActivity.this).dismissPop();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @OnClick({R.id.iv_scan, R.id.login_userId_label, R.id.iv_arrow, R.id.login_btn, R.id.tv_login_old_version,
            R.id.tv_apply_for_try, R.id.login_userId_edit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_scan:  //扫码
                break;
            case R.id.iv_arrow:
            case R.id.login_userId_label:
                ivArrow.setSelected(true);
                customSpinnerView.showAsDropDown(tvUserIdLabel, -DimensionUtils.dip2px(this, 10), 0);
                break;
            case R.id.login_btn: //登录
                login();
                break;
            case R.id.tv_login_old_version: //旧版本登录
                break;
            case R.id.tv_apply_for_try: //申请试用
                break;
            case R.id.login_userId_edit:  //点击ID输入框
                showListPopWindow(idList);
                break;
        }
    }


    /**
     * 登录
     */
    public void login() {
        //id
        final String id = etUserIdEdit.getText().toString().trim();
        if (TextUtils.isEmpty(id)) {
            tvErrorTip.setText("ID不能为空");
            return;
        }
        //password
        String password = etPasswordEdit.getText().toString().trim();

        String nickname = etNicknameEdit.getText().toString().trim();
        if (type == LIVE_TYPE) {
            //nickname
            if (TextUtils.isEmpty(nickname)) {
                tvErrorTip.setText("昵称不能为空");
                return;
            }
        }

        //服务器请求
        String params = type == LIVE_TYPE ? String.format(MainConsts.LIVE_LOGIN_PARAM, id, password, nickname, type) : String.format(MainConsts.PLAYBACK_LOGIN_PARAM, id, password, type);
        HttpRequest request = new HttpRequest(this);
        request.sendRequestWithPost(MainConsts.LOGIN_URL, params, new HttpRequest.IHttpRequestListener() {
            @Override
            public void onRequestCompleted(String responseStr) {
                try {
                    JSONObject jsonObject = new JSONObject(responseStr);
                    int code = jsonObject.optInt("code");
                    if (code == 0) {
                        JSONObject data = jsonObject.optJSONObject("data");
                        if (data != null) {
                            tvErrorTip.setText("");
                            String token = data.optString("access_token");
                            String logo = data.optString("logo");
                            String title = data.optString("title");
                            insertListValueUniq(id.trim());
                            SharedPreferencesUtil.saveString(LoginActivity.this, SharedPreferencesUtil.SP_LOGIN_LOGO_URL, logo);
                            Bundle bundle = new Bundle();
                            bundle.putString(LoginJumpActivity.TOKEN_PARAM, token);
                            bundle.putString(LoginJumpActivity.LOG0_PARAM, logo);
                            bundle.putString(LoginJumpActivity.TITLE_PARAM, title);
                            bundle.putInt(LoginJumpActivity.TYPE_PARAM, type);
                            bundle.putString(LoginJumpActivity.ID_PARAM, id);
                            ActivityUtil.jump(LoginActivity.this, LoginJumpActivity.class, bundle);
                        }
                    } else {
                        tvErrorTip.setText(jsonObject.optString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onIOError(String errorStr) {
                tvErrorTip.setText(errorStr);
            }
        });
    }


    /**
     * 插入列表值
     *
     * @param value
     */
    private void insertListValueUniq(String value) {
        if (idList.contains(value)) {
            idList.remove(value);
        }

        if (checkIdList.contains(value)) {
            checkIdList.remove(value);
        }

        if (cbRememberId.isChecked()) {
            idList.add(0, value);
            checkIdList.add(0, value);
        } else {
            idList.add(value);
        }

        while (checkIdList.size() > listMaxSize) {
            checkIdList.remove(checkIdList.size() - 1);
        }
        while (idList.size() > listMaxSize) {
            idList.remove(idList.size() - 1);
        }

        SharedPreferencesUtil.saveStringList(LoginActivity.this, SharedPreferencesUtil.SP_LOGIN_ID_LIST, idList);
        SharedPreferencesUtil.saveStringList(LoginActivity.this, SharedPreferencesUtil.SP_LOGIN_ID_LIST_CHECK, checkIdList);
    }

    private void showListPopWindow(List<String> listDatas) {
        popupWindowListData = listDatas;
        if (!listDatas.isEmpty() && listDatas.size() > 0) {
            if (etUserIdEdit.isFocusable()) {
                PopWindowManager popWindowManager = PopWindowManager.getInstance(this);
                if (!popWindowManager.isShowing()) {
                    popWindowManager.showPopListView(etUserIdEdit, listDatas);
                    /**监听下拉id列表的点击事件*/
                    popWindowManager.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String item = popupWindowListData.get(position);
                            if (position > 0) {
                                popupWindowListData.remove(position);
                                popupWindowListData.add(0, item);
                            }
                            if (etUserIdEdit.isFocusable()) {
                                etUserIdEdit.setText(item);
                                etUserIdEdit.setSelection(etUserIdEdit.getText().toString().length());
                            }
                            PopWindowManager.getInstance(LoginActivity.this).dismissPop();
                        }
                    });
                }

            }
        }
    }


    private void release() {
        /**退出移除所有的下载任务*/
        PlaybackDownloader.getInstance().destroy();
        CacheUtils.deleteCache(this);
    }

    @Override
    protected void onResume() {
        initLogo();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - preClickTime > 2000) {
            Toast.makeText(getApplicationContext(), getString(R.string.press_again_exit),
                    Toast.LENGTH_SHORT).show();
            preClickTime = System.currentTimeMillis();
            return;
        }
        super.onBackPressed();
        TFApplication.exit();
    }
}
