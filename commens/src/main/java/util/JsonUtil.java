package util;

import com.google.gson.Gson;

public class JsonUtil {

    static Gson gson=new Gson();
    public static String toJson(Object value){
        return gson.toJson(value);
    }
    public static <T> T fromJson(String jsonStr,T clazz){
        return (T) gson.fromJson(jsonStr,clazz.getClass());
    }
}
