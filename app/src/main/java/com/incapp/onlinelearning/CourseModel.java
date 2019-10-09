package com.incapp.onlinelearning;

public class CourseModel {

    public CourseModel() {
    }

    public CourseModel(String title, String image, String detail, String video) {
        this.title = title;
        this.image = image;
        this.detail = detail;
        this.video = video;
    }

    private String title;
    private String image;
    private String detail;
    private String video;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
