package market.zy.com.myapplication.activity.publish;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.BaseActivity;
import market.zy.com.myapplication.utils.PhotoUtil;

/**
 * Created by zpauly on 16-3-27.
 */
public class PublishGoodsActivity extends BaseActivity {
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

    @Bind(R.id.publish_type_select)
    protected Spinner mSelectSpinner;

    @Bind(R.id.goods_add_outer_layout)
    protected LinearLayout mOuterLayout;

    @Bind(R.id.send_floating_button)
    protected FloatingActionButton mSendButton;

    @Bind(R.id.publish_view)
    protected NestedScrollView mView;

    private LinearLayout addingLayout;

    private Uri coverUri;
    private List<Uri> imageUris;

    private int imageLine = 1;
    private int imageCount = 0;
    private List<String> selectedImagePath = new ArrayList<>();
    private List<Uri> selectedImageUri = new ArrayList<>();
    private Uri uri;
    private boolean isCoverImage;
    private boolean isButtonsHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_goods);
        ButterKnife.bind(this);

        setOnBackTwiceToTrue();

        initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri imageUri = data.getData();
                selectedImageUri.add(imageUri);
                uri = imageUri;
                if (isCoverImage) {
                    Glide.with(this)
                            .load(imageUri)
                            .centerCrop()
                            .thumbnail(0.2f)
                            .into(mImageCover);
                    coverUri = imageUri;
                } else {
                    addImageViewDynamiclly(addingLayout, imageUri);
                    imageUris.add(imageUri);
                    imageCount++;
                }
                String path = PhotoUtil.getPath(this, imageUri);
                Toast.makeText(PublishGoodsActivity.this, path, Toast.LENGTH_SHORT).show();
                selectedImagePath.add(path);
            }
        }
    }

    private void initView() {
        isCoverImage = true;
        isButtonsHide = true;
        addingLayout = mAddLayout;
        imageUris = new ArrayList<>();

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
                if (isContentCompleted())
                    return;
                MaterialDialog dialog = new MaterialDialog.Builder(PublishGoodsActivity.this)
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

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void startPicSelect() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);
    }

    private void addImageViewDynamiclly(ViewGroup mViewGroup, Uri path) {
        ImageView newImageView = new ImageView(PublishGoodsActivity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(160, 160);
        layoutParams.setMargins(16, 0, 0, 0);
        Glide.with(this)
                .load(path)
                .centerCrop()
                .thumbnail(0.2f)
                .into(newImageView);
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

    private void uploadImages(final MaterialDialog dialog) {
//        new UploadStategy().uploadImages(this, selectedImagePath.get(0), dialog);
    }

    private void uploadOthers(final MaterialDialog dialog) {
        /*dialog.show();*/
    }

    private void upload() {
        final MaterialDialog imageUploadDialog = new MaterialDialog.Builder(this)
                .title(R.string.uploading_images)
                .content(R.string.please_wait)
                .progress(true, 0)
                .build();

        final MaterialDialog otherUploadDialog = new MaterialDialog.Builder(this)
                .title(R.string.uploading_others)
                .content(R.string.please_wait)
                .progress(true, 0)
                .build();

        imageUploadDialog.show();
        uploadImages(imageUploadDialog);

        if (!imageUploadDialog.isShowing()) {
            uploadOthers(otherUploadDialog);
        }
    }

    private boolean isContentCompleted() {
        if (mBuyTime.getText().toString() != "" && mBuyTime.getText().toString() != null
                && mConnect.getText().toString() != "" && mConnect.getText().toString() != null
                && mContent.getText().toString() != "" && mContent.getText().toString() != null
                && mPrice.getText().toString() != "" && mPrice.getText().toString() != null
                && mQuality.getText().toString() != "" && mQuality.getText().toString() != null
                && mTitle.getText().toString() != "" && mTitle.getText().toString() != null) {
            return true;
        } else {
            showSnackbarTipShort(getCurrentFocus(), R.string.please_complete_info);
            return false;
        }
    }
}
