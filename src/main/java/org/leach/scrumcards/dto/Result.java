package org.leach.scrumcards.dto;

/**
 * @author Leach
 * @date 2017/9/25
 */
public class Result<T> {

    private int code;

    private String msg;

    private T model;

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

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }
}
