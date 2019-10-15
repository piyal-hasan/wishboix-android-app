package com.example.mdpiyalhasan.broatcastdemo;

/**
 * Created by piyal on 2/6/2018.
 */
public class SmsModel {
    private int key;
    private String sms;
    private String time;
    private String date;
    private String phone_number;

    public SmsModel(String sms, String time, String date, String phone_number) {
        this.sms = sms;
        this.time = time;
        this.date = date;
        this.phone_number = phone_number;
    }

    public int getKey() {
        return key;
    }

    public SmsModel(int key, String sms, String phone_number) {
        this.key = key;
        this.sms = sms;
        this.phone_number = phone_number;
    }

    public SmsModel(String sms, String phone_number) {
        this.sms = sms;
        this.phone_number = phone_number;
    }

    public SmsModel() {

    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public SmsModel(int key, String sms, String time, String date, String phone_number) {
        this.key = key;
        this.sms = sms;
        this.time = time;
        this.date = date;
        this.phone_number = phone_number;
    }
}
