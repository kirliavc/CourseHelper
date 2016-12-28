package com.netlab.vc.coursehelper.util.jsonResults;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dingfeifei on 16/12/23.
 */

public class Forum implements Serializable {
    private String posting_id;
    private String title;
    private String postUser_id;
    private Number like;

    public String getPosting_id() {
        return posting_id;
    }

    public void setPosting_id(String posting_id) {
        this.posting_id = posting_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostUser_id() {
        return postUser_id;
    }

    public void setPostUser_id(String postUser_id) {
        this.postUser_id = postUser_id;
    }

    public Number getLike() {
        return like;
    }

    public void setLike(Number like) {
        this.like = like;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    private Date postDate;
    private Boolean success;

}
