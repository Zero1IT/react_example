package com.gstu.pda.exceptions.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * createdAt: 10/10/2020
 * project: One
 *
 * @author Alex
 */
public class RestError {

    private Map<String, List<String>> errors;

    public RestError() {
        this.errors = new HashMap<>();
    }

    public void addError(String field, String message) {
        final List<String> messages = errors.computeIfAbsent(field, key -> new ArrayList<>());
        messages.add(message);
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, List<String>> errors) {
        this.errors = errors;
    }
}
