package com.netlab.vc.coursehelper.util.jsonResults;

/**
 * Created by Vc on 2016/12/9.
 */

public class UserInfo {
    String _id;
    String phone;
    String email;
    String name;
    String realName;
    String type;
    String[] avatars;
    Boolean success;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getAvatars() {
        return avatars;
    }

    public void setAvatars(String[] avatars) {
        this.avatars = avatars;
    }
}
