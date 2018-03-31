package com.wujingjingguanxueyuan.yidaogan.event;


import com.wujingjingguanxueyuan.yidaogan.mvp.bean.Comment;

/**
 * Created on 17/9/1 17:41
 */

public class DeleteCommentEvent {

    Comment comment;

    public DeleteCommentEvent(Comment comment) {
        this.comment = comment;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
