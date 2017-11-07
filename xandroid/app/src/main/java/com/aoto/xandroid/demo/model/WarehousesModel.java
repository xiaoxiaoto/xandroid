package com.aoto.xandroid.demo.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by 赵德华 on 2017/8/9.
 */

@Table(name = "WAREHOUSES", onCreated = "CREATE UNIQUE INDEX WAREHOUSES_UNIQUE ON WAREHOUSES(WCODE, WNAME)")
public class WarehousesModel implements Serializable {
    @Column(
            name = "ID",
            isId = true,
            autoGen = true
    )
    private int id;
    @Column(name = "WCODE", property = "NOT NULL")
    private String wcode;
    @Column(name = "WNAME", property = "NOT NULL")
    private String wname;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "CLASSIFY")
    private String classify;
    @Column(name = "STATE")
    private String state;
    @Column(name = "LONGITUDE")
    private double longitude;
    @Column(name = "LATITUDE")
    private double latitude;
    @Column(name = "CREATEUSER")
    private int createUser;
    @Column(name = "CREATEDATE")
    private String createDate;
    @Column(name = "FLAG")
    private String flag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWcode() {
        return wcode;
    }

    public void setWcode(String wcode) {
        this.wcode = wcode;
    }

    public String getWname() {
        return wname;
    }

    public void setWname(String wname) {
        this.wname = wname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getCreateUser() {
        return createUser;
    }

    public void setCreateUser(int createUser) {
        this.createUser = createUser;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
