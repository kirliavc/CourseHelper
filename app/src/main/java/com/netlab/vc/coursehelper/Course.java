package com.netlab.vc.coursehelper;

/**
 * Created by Vc on 2016/11/3.
 */

class Course {
    String name;
    String teacher;
    String img;
    public Course(String name,String teacher,String img){
        this.name=name;
        this.teacher=teacher;
        this.img=img;
    }
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }


}
