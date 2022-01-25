package com.udemy.controllers.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Integer statusHtpp, String mensagem, Long timeStamp) {
        super(statusHtpp, mensagem, timeStamp);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addError(String nome, String menssagem) {
        errors.add(new FieldMessage(nome, menssagem));
    }
}
