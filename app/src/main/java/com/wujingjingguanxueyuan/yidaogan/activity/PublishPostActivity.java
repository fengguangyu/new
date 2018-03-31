package com.wujingjingguanxueyuan.yidaogan.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.wujingjingguanxueyuan.yidaogan.R;
import com.wujingjingguanxueyuan.yidaogan.base.BaseActivity;
import com.wujingjingguanxueyuan.yidaogan.bean.User;
import com.wujingjingguanxueyuan.yidaogan.event.RefreshPostEvent;
import com.wujingjingguanxueyuan.yidaogan.mvp.bean.Post;
import com.wujingjingguanxueyuan.yidaogan.mvp.presenter.PublishPostPresenter;
import com.wujingjingguanxueyuan.yidaogan.mvp.view.PublishPostView;
import com.wujingjingguanxueyuan.yidaogan.utils.BmobUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;


/**
 * Created on 17/8/31 16:54
 */

public class PublishPostActivity extends BaseActivity implements PublishPostView {


    @Bind(R.id.tool_bar)
    Toolbar mToolBar;
    @Bind(R.id.btn_publish)
    Button mBtnPublish;
    @Bind(R.id.edt_post_content)
    EditText mEdtPostContent;
    private PublishPostPresenter mPublishPostPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_post);
        ButterKnife.bind(this);
        mToolBar.setTitle("发布帖子");
        mToolBar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        mToolBar.setBackgroundColor(ContextCompat.getColor(this, R.color.color_theme));
        mPublishPostPresenter = new PublishPostPresenter(this);
    }

    @Override
    protected void setActivityTitle() {

    }

    @Override
    protected void getLayoutToView() {

    }

    @Override
    protected void initValues() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setViewsListener() {

    }

    @Override
    protected void setViewsFunction() {

    }

    @OnClick(R.id.btn_publish)
    public void onViewClicked() {
        String content = mEdtPostContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            BmobUtils.toast(this, "请输入帖子内容！");
            return;
        }
        Post post = new Post();
        post.setContent(content);
        post.setAuthor(BmobUser.getCurrentUser(User.class));
        post.save();
        mPublishPostPresenter.publishPost(content);
    }

    @Override
    public void showDialog() {

    }

    @Override
    public void hideDialog() {

    }

    @Override
    public void showError(Throwable throwable) {

        BmobUtils.toast(this, throwable.getMessage());
    }

    @Override
    public void publishSuccess() {


        EventBus.getDefault().post(new RefreshPostEvent());
        BmobUtils.toast(this, "发表帖子成功");
        finish();


    }
}
