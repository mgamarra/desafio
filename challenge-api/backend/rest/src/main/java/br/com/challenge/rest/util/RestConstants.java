package br.com.challenge.rest.util;

public interface RestConstants {
    String GENERIC_ID_ATTR = "id";
    String ALPHANUMERIC_DASH_REGEX = "[0-9a-zA-Z\\-]*";
    String RESOURCE_ID_HEADER = "Resource-ID";
    String DEFAULT_MEDIA_TYPE = "application/json";
    String MULTIPART_MEDIA_TYPE = "multipart/form-data";

    /*
        /{id:[0-9a-zA-Z\-]*}
    */
    String ID_ALPHA_DASH_PATH = "/{" + GENERIC_ID_ATTR + ":" + ALPHANUMERIC_DASH_REGEX + "}"; // E.g:  /resources/asdas1-12321-sad-dad

    /**
     * Root Paths
     */
    String INDEX_HTML = "/static/index.html";
    String BASE_REST_V1_PATH = "/rest/v1/";
    String AUTH_PATH = BASE_REST_V1_PATH + "/auth";
    String ADDRESS_PATH = BASE_REST_V1_PATH + "/addresses";
    String USER_PATH = BASE_REST_V1_PATH + "/users";

}
