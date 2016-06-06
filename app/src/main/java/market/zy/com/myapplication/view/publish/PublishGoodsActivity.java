package market.zy.com.myapplication.view.publish;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.qiniu.android.http.ResponseInfo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.base.BaseActivity;
import market.zy.com.myapplication.utils.qiniu.UploadImages;
import market.zy.com.myapplication.entity.post.PhotoKey;
import market.zy.com.myapplication.entity.post.publish.NewPost;
import market.zy.com.myapplication.entity.post.publish.PublishStates;
import market.zy.com.myapplication.entity.qiniu.QiniuUploadToken;
import market.zy.com.myapplication.network.JxnuGoNetMethod;
import market.zy.com.myapplication.network.qiniu.upload.OnUploadListener;
import market.zy.com.myapplication.network.qiniu.uploadtoken.TokenMethod;
import market.zy.com.myapplication.utils.AuthUtil;
import market.zy.com.myapplication.utils.PhotoUtil;
import market.zy.com.myapplication.utils.SPUtil;
import rx.Subscriber;

/**
 * Created by zpauly on 16-3-27.
 */
public class PublishGoodsActivity extends BaseActivity implements PublishGoodsContract.View {
    private PublishGoodsContract.Presenter mPresenter;

    private static final int SELECT_PICTURE = 1;

    @Bind(R.id.publish_goods_toolbar)
    protected Toolbar mToolbar;

    @Bind(R.id.goods_title_layout)
    protected TextInputLayout mTitleLayout;

    @Bind(R.id.goods_cover)
    protected ImageView mImageCover;

    @Bind(R.id.goods_add_image)
    protected ImageView mAddImage;

    @Bind(R.id.goods_add_layout)
    protected LinearLayout mAddLayout;

    @Bind(R.id.publish_goods_title)
    protected EditText mTitle;

    @Bind(R.id.publish_goods_content)
    protected EditText mContent;

    @Bind(R.id.publish_goods_price)
    protected EditText mPrice;

    @Bind(R.id.publish_goods_buy_time)
    protected EditText mBuyTime;

    @Bind(R.id.publish_goods_quality)
    protected EditText mQuality;

    @Bind(R.id.publish_contact)
    protected EditText mConnect;

    @Bind(R.id.publish_location)
    protected EditText mLocation;

    @Bind(R.id.publish_num)
    protected EditText mNum;

    @Bind(R.id.publish_type_select)
    protected AppCompatSpinner mSelectSpinner;

    @Bind(R.id.goods_add_outer_layout)
    protected LinearLayout mOuterLayout;

    @Bind(R.id.send_floating_button)
    protected FloatingActionButton mSendButton;

    @Bind(R.id.publish_view)
    protected NestedScrollView mView;

    private LinearLayout addingLayout;

    private Uri coverUri;

    private String title;
    private String content;
    private double price;
    private String quality;
    private String buyTime;
    private String contract;
    private int goodTag;
    private int num;
    private String location;

    private MaterialDialog confirmDialog;
    private MaterialDialog uploadDialog;

    private ImageView mChangeImage;
    private boolean needChangeImage = false;
    private Map<ImageView, Integer> imageIdListMap = new HashMap<>();
    private int imageId;

    private int imageLine = 1;
    private int imageCount = 0;
    private List<String> selectedImagePath = new ArrayList<>();
    private boolean isCoverImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_goods);
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
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri imageUri = data.getData();
                if (isCoverImage) {
                    Glide.with(this)
                            .load(imageUri)
                            .crossFade()
                            .centerCrop()
                            .thumbnail(0.2f)
                            .into(mImageCover);
                    coverUri = imageUri;
                    imageIdListMap.put(mImageCover, 0);
                    String path = PhotoUtil.getPath(this, imageUri);
                    if (selectedImagePath.size() == 0)
                        selectedImagePath.add(path);
                    else
                        selectedImagePath.set(0, path);
                } else {
                    if (needChangeImage) {
                        Glide.with(this)
                                .load(imageUri)
                                .centerCrop()
                                .crossFade()
                                .thumbnail(0.2f)
                                .into(mChangeImage);
                        String path = PhotoUtil.getPath(this, imageUri);
                        int id = imageIdListMap.get(mChangeImage);
                        selectedImagePath.set(id, path);
                    } else {
                        addImageViewDynamiclly(addingLayout, imageUri);
                        imageCount++;
                        imageIdListMap.put(mImageCover, imageId++);
                        String path = PhotoUtil.getPath(this, imageUri);
                        selectedImagePath.add(path);
                    }
                }
            }
        }
    }

    private void initView() {
        isCoverImage = true;
        addingLayout = mAddLayout;

        setUpToolbar();

        setUpText();

        setUpImage();

        setUpButtons();

        setUpSpinner();
    }

    private void setUpText() {
        mTitleLayout.setHint(getString(R.string.title));

    }

    private void setUpImage() {
        mImageCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCoverImage = true;
                startPicSelect();
            }
        });
        mAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCoverImage = false;
                if (imageLine == 1) {
                    if (imageCount == 4) {
                        imageCount = 0;
                        imageLine++;
                        addingLayout = addLinearLayout(mOuterLayout);
                    }
                } else {
                    if (imageCount == 5) {
                        imageCount = 0;
                        imageLine++;
                        addingLayout = addLinearLayout(mOuterLayout);
                    }
                }
                startPicSelect();
            }
        });
    }

    private void setUpToolbar() {
        if (mToolbar != null) {
            mToolbar.setTitle(getString(R.string.publish));
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
    }

    private void setUpButtons() {
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isContentCompleted())
                    return;
                confirmDialog = new MaterialDialog.Builder(PublishGoodsActivity.this)
                        .title(R.string.upload)
                        .content(R.string.can_upload)
                        .positiveText(R.string.yes)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                upload();
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
    }

    private void setUpSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.goods_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSelectSpinner.setAdapter(adapter);
        mSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                goodTag = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                goodTag = 0;
            }
        });
    }

    private void getText() {
        title = mTitle.getText().toString();
        content = mContent.getText().toString();
        price = Double.parseDouble(mPrice.getText().toString());
        quality = mQuality.getText().toString();
        buyTime = mBuyTime.getText().toString();
        contract = mConnect.getText().toString();
        location = mLocation.getText().toString();
        num = Integer.parseInt(mNum.getText().toString());
    }

    private void startPicSelect() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    private void addImageViewDynamiclly(ViewGroup mViewGroup, Uri path) {
        final ImageView newImageView = new ImageView(PublishGoodsActivity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(160, 160);
        layoutParams.setMargins(16, 0, 0, 0);
        Glide.with(this)
                .load(path)
                .centerCrop()
                .thumbnail(0.2f)
                .into(newImageView);
        newImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                needChangeImage = true;
                mChangeImage = newImageView;
                startPicSelect();
            }
        });
        mViewGroup.addView(newImageView, -1, layoutParams);
    }

    private LinearLayout addLinearLayout(ViewGroup mViewGroup) {
        LinearLayout layout = new LinearLayout(PublishGoodsActivity.this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 0, 16);
        mViewGroup.addView(layout, -1, layoutParams);
        return layout;
    }

    private void upload() {
        uploadImages();
    }

    private void uploadImages() {
        uploadDialog = new MaterialDialog.Builder(this)
                .title(R.string.uploading)
                .content(R.string.please_wait)
                .progress(true, 0)
                .build();
        int total = selectedImagePath.size();
        for (String path : selectedImagePath) {
            mPresenter.uploadImages(path, total);
        }
    }

    private boolean isContentCompleted() {
        if (null == mNum.getText() || mNum.getText().toString().equals("")
                ||null == mBuyTime.getText() || mBuyTime.getText().toString().equals("")
                || null == mConnect.getText() || mConnect.getText().toString().equals("")
                || null == mContent.getText() || mContent.getText().toString().equals("")
                || null == mPrice.getText() || mPrice.getText().toString().equals("")
                || null == mQuality.getText() || mQuality.getText().toString().equals("")
                || null == mTitle.getText() || mTitle.getText().toString().equals("")) {
            showSnackbarTipShort(getCurrentFocus(), R.string.please_complete_info);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void completeImagesUpload() {
        uploadDialog.dismiss();
    }

    @Override
    public void showUploadFail() {
        showSnackbarTipShort(getCurrentFocus(), R.string.error_upload);
    }

    @Override
    public void showUploadSuccess() {
        showSnackbarTipShort(getCurrentFocus(), R.string.upload_successly);
    }

    @Override
    public NewPost createNewPost(List<PhotoKey> imageKeys) {
        NewPost newPost = new NewPost();
        getText();
        newPost.setGoodsName(title);
        newPost.setBody(content);
        newPost.setGoodsPrice(price);
        newPost.setPhotos(imageKeys);
        newPost.setContact(contract);
        newPost.setGoodsBuyTime(buyTime);
        newPost.setGoodsLocation(location);
        newPost.setGoodsQuality(quality);
        newPost.setGoodsNum(num);
        newPost.setGoodsTag(goodTag);
        newPost.setUserId(String.valueOf(userInfo.getUserId()));
        return newPost;
    }

    @Override
    public void setPresenter(PublishGoodsContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
