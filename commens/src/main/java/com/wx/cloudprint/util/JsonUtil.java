package com.wx.cloudprint.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

public class JsonUtil {

    static Gson gson=new Gson();
    public static String toJson(Object value){
        return gson.toJson(value);
    }
    public static <T> T fromJson(String jsonStr,T clazz){
        return (T) gson.fromJson(jsonStr,clazz.getClass());
    }

    public static void main(String []s){

        String json="[\"{\\\"caliper\\\":\\\"70g\\\",\\\"size\\\":\\\"A4\\\",\\\"money\\\":{\\\"mono\\\":{\\\"oneside\\\":1.0,\\\"duplex\\\":2.0},\\\"colorful\\\":{\\\"oneside\\\":1.0,\\\"duplex\\\":2.0}}}\"]";

       json= json.replaceAll("\\\\","");
          Object sss= new Gson().fromJson(json, new TypeToken<List<Map>>() {
          }.getType());
          sss.toString();


    }

}
