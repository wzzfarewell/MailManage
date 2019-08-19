package com.ncu.mailmanage.pojo;

import java.util.Date;

public class Mail {
    private Long mailId;

    private String title;

    private Date sendTime;

    private String body;

    public Mail(Long mailId, String title, Date sendTime, String body) {
        this.mailId = mailId;
        this.title = title;
        this.sendTime = sendTime;
        this.body = body;
    }

    public Mail() {
        super();
    }

    public Long getMailId() {
        return mailId;
    }

    public void setMailId(Long mailId) {
        this.mailId = mailId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body == null ? null : body.trim();
    }
}