package com.example.gagan.proj1.pojo;

import android.net.Uri;

import com.example.gagan.proj1.utils.Constant;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Gagan on 4/17/2018.
 */

public class User {
    private String id;
    private String name;
    private String email;
    private String imageUrl;
    private String nickName;
    private boolean onlineStatus;
    private boolean typing;
    private List<String> registeredUserId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public User() {
    }

    public static User getDummyUser() {
        User uSer = new User();
        uSer.email = "abc@gmail.com";
        uSer.name = "display name";
        uSer.nickName = "nickname";
        uSer.imageUrl = Constant.EMPTY_PROFILE_PIC;
        uSer.id = "123";
        uSer.onlineStatus = false;
        uSer.typing = false;
        return uSer;
    }

    public User(GoogleSignInAccount account) {
        email = account.getEmail();
        name = account.getDisplayName();
        nickName = account.getGivenName();
        imageUrl = account.getPhotoUrl() != null ? account.getPhotoUrl().toString() : Constant.EMPTY_PROFILE_PIC;
        id = account.getId();
        onlineStatus = true;
        typing = false;
    }


    public boolean isOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public boolean getTyping() {
        return typing;
    }

    public void setTyping(boolean typing) {
        this.typing = typing;
    }


    public List<String> getRegisteredUserId() {
        return registeredUserId;
    }

    public void setRegisteredUserId(List<String> registeredUserId) {
        this.registeredUserId = registeredUserId;
    }

    public void init(GoogleSignInAccount account) {
        email = account.getEmail();
        name = account.getDisplayName();
        nickName = account.getGivenName();
        imageUrl = account.getPhotoUrl() != null ? account.getPhotoUrl().toString() : Constant.EMPTY_PROFILE_PIC;
        id = account.getId();
        onlineStatus = true;
        typing = false;
    }
}
