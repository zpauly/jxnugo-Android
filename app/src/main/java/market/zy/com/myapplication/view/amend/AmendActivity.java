package market.zy.com.myapplication.view.amend;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import market.zy.com.myapplication.Constants;
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.base.BaseActivity;
import market.zy.com.myapplication.entity.user.UserBasicInfo;
import market.zy.com.myapplication.entity.user.amend.AmendUserInfo;
import market.zy.com.myapplication.presenter.amend.AmendContract;
import market.zy.com.myapplication.presenter.amend.AmendPresenter;
import market.zy.com.myapplication.utils.PhotoUtil;

/**
 * Created by zpauly on 16-5-22.
 */
public class AmendActivity extends BaseActivity implements AmendContract.View {
    private static final int SELECT_PICTURE = 1;

    private AmendContract.Presenter mPresenter;

    @Bind(R.id.amend_appbar)
    protected AppBarLayout mAppbarLayout;

    @Bind(R.id.amend_toolbar)
    protected Toolbar mToolbar;

    @Bind(R.id.amend_avatar)
    protected CircleImageView mAvatar;

    @Bind(R.id.amend_contract)
    protected EditText mContract;

    @Bind(R.id.amend_username)
    protected EditText mUsername;

    @Bind(R.id.amend_location)
    protected EditText mLocation;

    @Bind(R.id.amend_user_tag)
    protected EditText mUserTag;

    @Bind(R.id.amend_sex)
    protected AppCompatSpinner mSexSeleter;

    @Bind(R.id.amend_save)
    protected FloatingActionButton mSaveButton;

    private String avatarPath;
    private String contract;
    private String nickname;
    private String location;
    private String userTag;
    private String sex;

    private AmendUserInfo amendUserInfo;

    /*private Subscriber<AmendStates> amendSubscriber;
    private Subscriber<QiniuUploadToken> tokenSubscriber;

    private String token;*/

    private MaterialDialog confirmDialog;
    private MaterialDialog uploadDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amend_userinfo);
        ButterKnife.bind(this);

        setOnBackTwiceToTrue();

        initView();
    }

    @Override
    protected void onPause() {
        mPresenter.stop();
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri imageUri = data.getData();
                avatarPath = PhotoUtil.getPath(this, imageUri);
                Glide.with(AmendActivity.this)
                        .load(avatarPath)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(mAvatar);
            }
        }
    }

    private void initView() {
        new AmendPresenter(this, this);
        mPresenter.start();

        setUpToolbar();

        setUpButton();

        setUpSpinner();

        initText();
    }

    private void initText() {
        avatarPath = userInfo.getAvatar();
        userTag = userInfo.getAbout_me();
        nickname = userInfo.getName();
        contract = userInfo.getContactMe();
        location = userInfo.getLocation();
        sex = userInfo.getSex();

        Glide.with(this)
                .load(avatarPath)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(mAvatar);
        mUserTag.setText(userTag);
        mUsername.setText(nickname);
        mContract.setText(contract);
        mLocation.setText(location);
        if (sex != null && !sex.equals("")) {
            ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) mSexSeleter.getAdapter();
            int position = adapter.getPosition(sex);
            mSexSeleter.setSelection(position);
        }
        if (sex.length() > 5) {
            sex = getString(R.string.male);
        }
    }

    private void setUpToolbar() {
        mToolbar.setTitle(R.string.amend_user_info);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setUpSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sex, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSexSeleter.setAdapter(adapter);
        mSexSeleter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1 :
                        sex = getString(R.string.male);
                        break;
                    case 2 :
                        sex = getString(R.string.female);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void amend() {
        /*amendSubscriber = new Subscriber<AmendStates>() {
            @Override
            public void onCompleted() {
                uploadDialog.dismiss();
                showSnackbarTipShort(getCurrentFocus(), R.string.amend_success);
                UserBasicInfo info = new UserBasicInfo();
                info.setUserId(userInfo.getUserId());
                info.setFollowed(userInfo.getFollowed());
                info.setFollowers(userInfo.getFollowers());
                info.setLast_seen(userInfo.getLast_seen());
                info.setMember_since(userInfo.getMember_since());
                info.setPostCollectionCount(userInfo.getPostCollectionCount());
                info.setPostCount(userInfo.getPostCount());
                info.setUserName(userInfo.getUserName());
                info.setName(nickname);
                info.setAvatar(key);
                info.setAbout_me(userTag);
                info.setContactMe(contract);
                info.setLocation(location);
                info.setSex(sex);
                UserInfoDao.deleteUserInfo();
                UserInfoDao.insertUserInfo(info);
                finish();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                uploadDialog.dismiss();
                showSnackbarTipShort(getCurrentFocus(), R.string.error_upload);
            }

            @Override
            public void onNext(AmendStates amendSuccess) {

            }
        };
        getTextContent(key);
        String auth = AuthUtil.getAuthFromUsernameAndPassword(SPUtil.getInstance(this).getCurrentUsername()
                , SPUtil.getInstance(this).getCurrentPassword());
        JxnuGoNetMethod.getInstance().amendUserInfo(amendSubscriber, auth, amendUserInfo);*/
        mPresenter.amend();
    }

    private void setUpButton() {
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog = new MaterialDialog.Builder(AmendActivity.this)
                        .title(R.string.upload)
                        .content(R.string.can_upload)
                        .positiveText(R.string.yes)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                uploadDialog = new MaterialDialog.Builder(AmendActivity.this)
                                        .title(R.string.uploading)
                                        .content(R.string.please_wait)
                                        .progress(true, 0)
                                        .build();
                                uploadDialog.show();
                                if (!avatarPath.equals(userInfo.getAvatar()))
                                    uploadAvatar();
                                else
                                    amend();
                            }
                        })
                        .negativeText(R.string.no)
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPicSelect();
            }
        });
    }

    private void uploadAvatar() {
        /*tokenSubscriber = new Subscriber<QiniuUploadToken>() {
            @Override
            public void onCompleted() {
                UploadImages.getInstance().uploadImages(avatarPath, token
                        , new OnUploadListener() {
                            @Override
                            public void onCompleted(String key, ResponseInfo info, JSONObject response) {
                                if (info.isOK()) {
                                    try {
                                        String photoKey = response.getString("key");
                                        amend(Constants.PIC_BASE_URL + photoKey);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                }
                            }

                            @Override
                            public void onProcessing(String key, double percent) {

                            }

                            @Override
                            public boolean onCancelled() {
                                return false;
                            }
                        });
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(QiniuUploadToken qiniuUploadToken) {
                token = qiniuUploadToken.getUptoken();
            }
        };
        TokenMethod.getInstance().getUploadToken(tokenSubscriber);*/
        mPresenter.uploadAvatar(avatarPath);
    }

    /*private void getTextContent(String avatar) {
        nickname = mUsername.getText().toString();
        userTag = mUserTag.getText().toString();
        contract = mContract.getText().toString();
        location = mLocation.getText().toString();
        amendUserInfo = new AmendUserInfo();
        amendUserInfo.setAvatar(avatar);
        amendUserInfo.setAbout_me(userTag);
        amendUserInfo.setContact(contract);
        amendUserInfo.setLocation(location);
        amendUserInfo.setName(nickname);
        amendUserInfo.setSex(sex);
        amendUserInfo.setUserId(userInfo.getUserId());
    }*/

    @Override
    public AmendUserInfo getText() {
        nickname = mUsername.getText().toString();
        userTag = mUserTag.getText().toString();
        contract = mContract.getText().toString();
        location = mLocation.getText().toString();
        amendUserInfo = new AmendUserInfo();
        amendUserInfo.setAvatar(avatarPath);
        amendUserInfo.setAbout_me(userTag);
        amendUserInfo.setContact(contract);
        amendUserInfo.setLocation(location);
        amendUserInfo.setName(nickname);
        amendUserInfo.setSex(sex);
        amendUserInfo.setUserId(userInfo.getUserId());
        return amendUserInfo;
    }

    @Override
    public UserBasicInfo getNewUserInfo() {
        UserBasicInfo info = new UserBasicInfo();
        info.setUserId(userInfo.getUserId());
        info.setFollowed(userInfo.getFollowed());
        info.setFollowers(userInfo.getFollowers());
        info.setLast_seen(userInfo.getLast_seen());
        info.setMember_since(userInfo.getMember_since());
        info.setPostCollectionCount(userInfo.getPostCollectionCount());
        info.setPostCount(userInfo.getPostCount());
        info.setUserName(userInfo.getUserName());
        info.setName(nickname);
        info.setAvatar(avatarPath);
        info.setAbout_me(userTag);
        info.setContactMe(contract);
        info.setLocation(location);
        info.setSex(sex);
        return info;
    }

    @Override
    public void showUploadError() {
        uploadDialog.dismiss();
        showSnackbarTipShort(getCurrentFocus(), R.string.error_upload);
    }

    @Override
    public void showUploadSuccess() {
        mPresenter.setUserInfo();
        showSnackbarTipShort(getCurrentFocus(), R.string.upload_successly);
        uploadDialog.dismiss();
        finish();
    }

    @Override
    public void getAvatarKey(JSONObject response) {
        try {
            String photoKey = response.getString("key");
            avatarPath = Constants.PIC_BASE_URL + photoKey;
            amend();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void startPicSelect() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    @Override
    public void setPresenter(AmendContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
