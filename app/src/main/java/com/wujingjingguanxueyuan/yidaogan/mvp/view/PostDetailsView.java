package com.wujingjingguanxueyuan.yidaogan.mvp.view;


import com.wujingjingguanxueyuan.yidaogan.mvp.bean.Comment;
import com.wujingjingguanxueyuan.yidaogan.mvp.bean.Love;

import java.util.List;

/**
 * Created on 17/8/31 17:14
 */

public interface PostDetailsView extends BmobView{
    //评论相关
    void publishCommentSuccess();
    void deleteCommentSuccess();
    void showComments(List<Comment> comments);

    //帖子相关
    void deletePostSuccess();

    //点赞相关
    void loveSuccess();
    void unloveSuccess();
    void showLoves(List<Love> loves);

}
