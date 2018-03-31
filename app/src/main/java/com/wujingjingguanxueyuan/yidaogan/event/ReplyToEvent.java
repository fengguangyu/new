package com.wujingjingguanxueyuan.yidaogan.event;


import com.wujingjingguanxueyuan.yidaogan.mvp.bean.Comment;

/**
 * Created on 17/9/1 17:42
 */

public class ReplyToEvent {
    Comment replyTo;

    public ReplyToEvent(Comment replyTo) {
        this.replyTo = replyTo;
    }

    public Comment getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(Comment replyTo) {
        this.replyTo = replyTo;
    }
}
