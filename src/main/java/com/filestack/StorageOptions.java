package com.filestack;

import com.filestack.internal.Util;
import com.filestack.transforms.TransformTask;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.RequestBody;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Configure storage options for uploads and transformation stores. */
public class StorageOptions implements Serializable {
  private static final String DEFAULT_FILENAME = "java-sdk-upload-%d";
  private static final String DEFAULT_MIME_TYPE = "application/octet-stream";

  private String access;
  private Boolean base64Decode;
  private String container;
  private String filename;
  private String location;
  private String mimeType;
  private String path;
  private String region;
  private List<String> workflows;

  // Private to enforce use of the builder
  private StorageOptions() { }

  public String getFilename() {
    return filename;
  }

  public String getMimeType() {
    return mimeType;
  }

  /** Get these options as a task. */
  public TransformTask getAsTask() {
    TransformTask task = new TransformTask("store");
    addToTask(task);
    return task;
  }

  /** Add these options to an existing task. */
  public void addToTask(TransformTask task) {
    task.addOption("access", access);
    task.addOption("base64decode", base64Decode);
    task.addOption("container", container);
    task.addOption("filename", filename);
    task.addOption("location", location);
    task.addOption("path", path);
    task.addOption("region", region);
    task.addOption("workflows", workflows);
  }

  /** Get these options as a part map to use for uploads. */
  public JsonObject getAsPartMap() {
    JsonObject map = new JsonObject();
    addToJson(map, "access", access);
    addToJson(map, "container", container);
    addToJson(map, "location", location != null ? location : "s3");
    addToJson(map, "path", path);
    addToJson(map, "region", region);

    if (workflows != null && !workflows.isEmpty()) {
      JsonArray jsonArray = createJsonArray(workflows);
      map.add("workflows", jsonArray);
    }

    // A name and MIME type must be set for uploads, so we set a default here but not in "build"
    // If we're not using the instance for an upload, we don't want to set these defaults
    if (Util.isNullOrEmpty(filename)) {
      long timestamp = System.currentTimeMillis() / 1000L;
      filename = String.format(DEFAULT_FILENAME, timestamp);
    }

    // There's too many variables in guessing MIME types, esp between platforms
    // Either the user sets it themselves or we use a default
    if (Util.isNullOrEmpty(mimeType)) {
      mimeType = DEFAULT_MIME_TYPE;
    }

    return map;
  }

  /** Get these options as JSON to use for cloud integrations. */
  public JsonObject getAsJson() {
    JsonObject json = new JsonObject();
    addToJson(json, "access", access);
    addToJson(json, "container", container);
    addToJson(json, "filename", filename);
    addToJson(json, "location", location);
    addToJson(json, "path", path);
    addToJson(json, "region", region);
    return json;
  }

  public Builder newBuilder() {
    return new Builder(this);
  }

  private static JsonArray createJsonArray(List<String> list) {
    JsonArray jsonArray = new JsonArray();
    for (String item : list) {
      jsonArray.add(item);
    }
    return jsonArray;
  }

  private static void addToMap(Map<String, RequestBody> map, String key, @Nullable String value) {
    if (value != null) {
      map.put(key, Util.createStringPart(value));
    }
  }

  private static void addToJson(JsonObject json, String key, @Nullable String value) {
    if (value != null) {
      json.addProperty(key, value);
    }
  }

  public static class Builder {
    // Setting these is optional
    private Boolean base64Decode;
    private String access;
    private String container;
    private String filename;
    private String location;
    private String mimeType;
    private String path;
    private String region;
    private List<String> workflows;

    public Builder() { }

    /** Create a new builder using an existing options config. */
    public Builder(StorageOptions existing) {
      access = existing.access;
      base64Decode = existing.base64Decode;
      container = existing.container;
      filename = existing.filename;
      location = existing.location;
      path = existing.path;
      region = existing.region;
      mimeType = existing.mimeType;
      workflows = existing.workflows;
    }

    public Builder access(String access) {
      this.access = access;
      return this;
    }

    public Builder base64Decode(boolean base64Decode) {
      this.base64Decode = base64Decode;
      return this;
    }

    public Builder container(String container) {
      this.container = container;
      return this;
    }

    public Builder filename(String filename) {
      this.filename = filename;
      return this;
    }

    public Builder location(String location) {
      this.location = location;
      return this;
    }

    public Builder path(String path) {
      this.path = path;
      return this;
    }

    public Builder region(String region) {
      this.region = region;
      return this;
    }

    public Builder mimeType(String contentType) {
      this.mimeType = contentType;
      return this;
    }

    public Builder workflows(List<String> workflows) {
      this.workflows = new ArrayList<>(workflows);
      return this;
    }

    /**
     * Builds new {@link StorageOptions}.
     */
    public StorageOptions build() {
      StorageOptions building = new StorageOptions();

      building.access = access;
      building.base64Decode = base64Decode;
      building.container = container;
      building.filename = filename;
      building.location = location;
      building.mimeType = mimeType;
      building.path = path;
      building.region = region;
      building.workflows = workflows;

      return building;
    }
  }
}
