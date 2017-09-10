package message;


import java.io.Serializable;

public class Message implements Serializable {
    String result;
    Object message;

    public static String success_state="ok";
    public static String fail_state="fail";
    public String getResult() {
        return result;
    }

    public Message(String result, Object message) {
        this.result = result;
        this.message = message;
    }

    public Message() {
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Serializable message) {
        this.message = message;
    }
    public static Message createMessage(String state,Object  message){
        return new Message(state,message);
    }
}
