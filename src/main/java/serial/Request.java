package serial;

import java.io.Serializable;

public class Request implements Serializable{
    private int code;
    private String msg;

    public Request(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Request{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
