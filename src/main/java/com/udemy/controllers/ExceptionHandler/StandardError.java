package com.udemy.controllers.ExceptionHandler;

import java.io.Serializable;

public class StandardError implements Serializable {
    private Integer statusHtpp;
    private String mensagem;
    private Long timeStamp;

    public StandardError(Integer statusHtpp, String mensagem, Long timeStamp) {
        this.statusHtpp = statusHtpp;
        this.mensagem = mensagem;
        this.timeStamp = timeStamp;
    }

    public Integer getStatusHtpp() {
        return statusHtpp;
    }

    public void setStatusHtpp(Integer statusHtpp) {
        this.statusHtpp = statusHtpp;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
