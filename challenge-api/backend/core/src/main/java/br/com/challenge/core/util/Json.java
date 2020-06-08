package br.com.challenge.core.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Json wrapper wanna be
 */
public class Json {
    private Map<String, Object> body;

    private Json() {
        body = new HashMap<>();
    }

    //New instance
    public static Json inst() {
        return new Json();
    }

    //Key value binding
    public <T> Json kv(String key,  T value) {
        this.body.put(key, (T) value);
        return this;
    }

    //Returns the map
    public <T> Map<String, T> toMap() {
        return (Map<String, T>) this.body;
    }
}
