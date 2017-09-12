package message;


import java.io.Serializable;

public class Message implements Serializable {
    String result;
    Object info;

    public static String success_state="ok";
    public static String fail_state="fail";
    public String getResult() {
        return result;
    }

    public Message(String result, Object message) {
        this.result = result;
        this.info = message;
    }

    public Message() {
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getMessage() {
        return info;
    }

    public void setMessage(Serializable message) {
        this.info = message;
    }
    public static Message createMessage(String state,Object  message){
        return new Message(state,message);
    }
}
