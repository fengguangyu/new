package com.wujingjingguanxueyuan.yidaogan.mvp.presenter;


import com.wujingjingguanxueyuan.yidaogan.mvp.bean.Post;
import com.wujingjingguanxueyuan.yidaogan.mvp.model.BmobModel;
import com.wujingjingguanxueyuan.yidaogan.mvp.view.ShowPostsView;

import java.util.List;

import rx.functions.Action1;

/**
 * Created on 17/8/31 17:47
 */

public class ShowPostPresenter {
    private ShowPostsView mShowPostsView;
    private BmobModel mBmobModel;

    public ShowPostPresenter(ShowPostsView showPostsView) {
        mShowPostsView = showPostsView;
        mBmobModel = new BmobModel();
    }


    public void showPosts(){//int page,int count){
        mShowPostsView.showDialog();
        mBmobModel.findPosts()//(page,count)
                .subscribe(new Action1<List<Post>>() {
                    @Override
                    public void call(List<Post> posts) {

                        mShowPostsView.hideDialog();
                        mShowPostsView.showPosts(posts);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mShowPostsView.hideDialog();
                        mShowPostsView.showError(throwable);
                    }
                });
    }


}
