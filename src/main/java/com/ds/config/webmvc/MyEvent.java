package com.ds.config.webmvc;

import org.springframework.context.ApplicationEvent;

public class MyEvent extends ApplicationEvent {

    private String message;

    public MyEvent(Object source) {
        super(source);
    }

    @Override
    public Object getSource() {
        return super.getSource();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
