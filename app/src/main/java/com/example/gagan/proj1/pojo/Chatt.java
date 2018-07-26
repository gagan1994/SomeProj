package com.example.gagan.proj1.pojo;

import com.example.gagan.proj1.dbhelper.DbHelper;
import com.example.gagan.proj1.utils.Utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Gagan on 4/17/2018.
 */

public class Chatt {
    private String id;
    private String message;
    private String fromId;
    private String toId;
    private Date time;
    private boolean seen;
    private boolean sent;

    public Chatt() {
    }

    public static Chatt getDummyChatt() {
        Chatt chatt = new Chatt();
        chatt.id = "dummy";
        chatt.message = "dummy message";
        chatt.toId = chatt.fromId = DbHelper.getDbHepler().getCurrentUserObj().getId();
        chatt.time = Calendar.getInstance().getTime();
        return chatt;
    }

    public Chatt(User from, User to, String message) {
        this.fromId = from.getId();
        this.toId = to.getId();
        this.message = message;
        id = Utils.getTimeId();
        time = Utils.getCurrentTime();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSentTime() {
        return Utils.getTimeTODisplay(time);
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }
}
