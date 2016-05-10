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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import market.zy.com.myapplication.R;
import market.zy.com.myapplication.activity.BaseActivity;
import market.zy.com.myapplication.engine.qiniu.UploadStategy;
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

    @Bind(R.id.goods_title)
    protected EditText mTitleEditText;

    @Bind(R.id.goods_content)
    protected EditText mContentEditText;

    @Bind(R.id.goods_cover)
    protected ImageView mImageCover;

    @Bind(R.id.goods_add_image)
    protected ImageView mAddImage;

    @Bind(R.id.goods_add_layout)
    protected LinearLayout mAddLayout;

    @Bind(R.id.goods_price)
    protected TextView mPriceTextView;

    @Bind(R.id.goods_choose_type)
    protected TextView mChooseTypeTextView;

    @Bind(R.id.goods_add_outer_layout)
    protected LinearLayout mOuterLayout;

    @Bind(R.id.edit_floating_button)
    protected FloatingActionButton mEditButton;

    @Bind(R.id.save_floating_button)
    protected FloatingActionButton mSaveButton;

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
                            .load(selectedImageUri)
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
        mSendButton.setEnabled(false);
        mSaveButton.setEnabled(false);
        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isButtonsHide) {
                    isButtonsHide = false;
                    mView.setEnabled(false);
                    mSendButton.setEnabled(true);
                    mSaveButton.setEnabled(true);
                    mEditButton.setImageResource(R.mipmap.ic_close_white_36dp);
                    showButtons();
                } else {
                    mView.setEnabled(true);
                    isButtonsHide =true;
                    mSendButton.setEnabled(false);
                    mSaveButton.setEnabled(false);
                    mEditButton.setImageResource(R.mipmap.ic_mode_edit_white_36dp);
                    hideButtons();
                }
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        newImageView.setElevation(7f);
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

    private void showButtons() {
        ViewPropertyAnimator.animate(mSendButton)
                .rotation(-720f)
                .translationY(-mEditButton.getHeight())
                .setDuration(500);

        ViewPropertyAnimator.animate(mSaveButton)
                .rotation(-720f)
                .translationY(-mEditButton.getHeight() * 2)
                .setDuration(500);
    }

    private void hideButtons() {
        ViewPropertyAnimator.animate(mSendButton)
                .rotation(0f)
                .translationY(0)
                .setDuration(500);

        ViewPropertyAnimator.animate(mSaveButton)
                .rotation(0f)
                .translationY(0)
                .setDuration(500);
    }

    private void uploadImages(final MaterialDialog dialog) {
        new UploadStategy().uploadImages(this, selectedImagePath.get(0), dialog);
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
}
