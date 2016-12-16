package com.netlab.vc.coursehelper.util.jsonResults;

/**
 * Created by Vc on 2016/12/16.
 */

public class SignInInfo {
    int uuid;
    int enable;
    int total;
    int signin_id;
    Boolean success;

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSignin_id() {
        return signin_id;
    }

    public void setSignin_id(int signin_id) {
        this.signin_id = signin_id;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
