package com.netlab.vc.coursehelper.util.jsonResults;


/**
 * Created by dingfeifei on 17/1/2.
 */

public class PostResult{
    private Boolean success;
    private String posting_id;
    private String postUser_name;//帖子的id String
    private String title;
    private String content;
    private String postUser_id;
    private Number like;
    private Long postDate;
    private Reply[] replys;



    public String getPostUser_name() {
        return postUser_name;
    }

    public void setPostUser_name(String postUser_name) {
        this.postUser_name = postUser_name;
    }

    public Reply[] getReplys() {
        return replys;
    }

    public void setReplys(Reply[] replys) {
        this.replys = replys;
    }



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }




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

    public Long getPostDate() {
        return postDate;
    }

    public void setPostDate(Long postDate) {
        this.postDate = postDate;
    }
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }


}
