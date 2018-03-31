package com.wujingjingguanxueyuan.yidaogan.mvp.view;


import com.wujingjingguanxueyuan.yidaogan.mvp.bean.Post;

import java.util.List;

/**
 * Created on 17/8/31 17:47
 */

public interface ShowPostsView extends BmobView {
    void showPosts(List<Post> posts);
}
