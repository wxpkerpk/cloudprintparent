package com.wx.cloudprint.message;


import java.io.Serializable;

public class Message implements Serializable {
    private String result;
    private Object info;

    public static String success_state="OK";
    public static String fail_state="FAIL";
    public String getResult() {
        return result;
    }

    public Message(String result, Object info) {
        this.result = result;
        this.info = info;
    }

    public Message() {
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public static Message createMessage(String state, Object  message){
        return new Message(state,message);
    }
}
