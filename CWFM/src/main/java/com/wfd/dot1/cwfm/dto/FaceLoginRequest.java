package com.wfd.dot1.cwfm.dto;


public class FaceLoginRequest {

    private String userId;       // PUSERID
    private String userImage;    // Base64 image
    private String extra1;
    private String extra2;
    private String extra3;
    private String extra4;
    private String extra5;

    // getters & setters
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserImage() {
        return userImage;
    }
    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getExtra1() { return extra1; }
    public void setExtra1(String extra1) { this.extra1 = extra1; }

    public String getExtra2() { return extra2; }
    public void setExtra2(String extra2) { this.extra2 = extra2; }

    public String getExtra3() { return extra3; }
    public void setExtra3(String extra3) { this.extra3 = extra3; }

    public String getExtra4() { return extra4; }
    public void setExtra4(String extra4) { this.extra4 = extra4; }

    public String getExtra5() { return extra5; }
    public void setExtra5(String extra5) { this.extra5 = extra5; }
}

