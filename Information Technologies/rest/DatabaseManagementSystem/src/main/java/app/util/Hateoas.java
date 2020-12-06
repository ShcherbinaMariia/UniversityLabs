package app.util;

import com.google.gson.JsonObject;

import java.util.HashMap;

public class Hateoas {

    public static final Resource HomeResource = new Resource("/", "get");
    public static final Resource DatabasesResource = new Resource("/databases/", "get");
    public static final Resource StorageResource = new Resource("/storage/", "get");

    public static String formatResponse(String content, HashMap<String, Resource> resources) {
        JsonObject response = new JsonObject();

        response.addProperty("content", content);

        JsonObject resourcesJson = new JsonObject();

        resources.forEach((name, resource) -> {
            resourcesJson.add(name, formatResource(resource));
        });
        resourcesJson.add("home", formatResource(HomeResource));

        response.add("resources", resourcesJson);

        return response.toString();
    }

    public static JsonObject formatResource(Resource resource) {
        JsonObject resourceJson = new JsonObject();
        resourceJson.addProperty("href", resource.link);
        resourceJson.addProperty("method", resource.method);
        return resourceJson;
    }

    public static String Root() {
        HashMap<String, Resource> homeResources = new HashMap();
        homeResources.put("databases", DatabasesResource);
        homeResources.put("storage", StorageResource);
        return formatResponse("Welcome to database management system app!", homeResources);
    }
}
