package com.raimvititor.paho_mqtt_test.models;

import com.stfalcon.chatkit.commons.models.IUser;

/**
 * Created by Ray on 2017/3/1.
 */

public class Author implements IUser {

    public int id;
    public String name;
    public String avatar;

    public Author(int id, String name, String avatar) {
        super();
        this.id = id;
        this.name = name;
        this.avatar = avatar;
    }

    @Override
    public String getId() {
        return id + "";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }
}
