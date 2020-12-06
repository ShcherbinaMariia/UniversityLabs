package app.util;

import spark.Response;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;

public class ResponseHelper {
    public static void processMutationResult(MutationResult mutationResult, Response response) {
        if (mutationResult.isSuccessful()) {
            response.status(HTTP_OK);
        } else {
            response.status(HTTP_BAD_REQUEST);
            response.body(mutationResult.getMessage());
        }
    }
}