package br.com.challenge.core.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JsonUtil {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static <T> T fromString(String string, Class<T> clazz) {
        try {
            return MAPPER.readValue(string, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("The given string value: %s cannot be transformed to Json object", string));
        }
    }

    public static <T> T fromString(String string, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(string, typeReference);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("The given string value: %s cannot be transformed to Json object", string));
        }
    }

    public static String toString(Object value) {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(String.format("The given Json object value: %s cannot be transformed to a String", value));
        }
    }

    public static JsonNode toJsonNode(String value) {
        try {
            return MAPPER.readTree(value);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static JsonNode toJsonNode(JsonParser jsonParser) {
        try {
            return MAPPER.readTree(jsonParser);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static boolean equals(String string1, String string2) {
        return toJsonNode(string1).equals(toJsonNode(string2));
    }

    public static boolean notEquals(String string1, String string2) {
        return !equals(string1, string2);
    }

    @SuppressWarnings("unchecked")
    public static <T> T clone(T value) {
        return fromString(toString(value), (Class<T>) value.getClass());
    }

    public static String escape(String json) {
//		return StringEscapeUtils.escapeJson(json);
        System.out.println("not implemented yet. :D");
        return json;
    }
}
