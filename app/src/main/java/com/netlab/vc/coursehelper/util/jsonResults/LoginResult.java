package com.netlab.vc.coursehelper.util.jsonResults;

/**
 * Created by Vc on 2016/11/19.
 * 用json处理服务器返回的结果信息
 */

public class LoginResult {


    String _id;
    String token;
    Boolean success;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
