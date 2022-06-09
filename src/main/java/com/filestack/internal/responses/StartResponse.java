package com.filestack.internal.responses;

import com.filestack.internal.Util;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.Map;
import okhttp3.RequestBody;

/** Response object from multipart start endpoint. */
@SuppressWarnings("unused")
public class StartResponse {
  private String uri;
  private String region;
  @SerializedName("location_url")
  private String locationUrl;
  @SerializedName("upload_id")
  private String uploadId;
  @SerializedName("upload_type")
  private String uploadType;

  /**
   * Return {@link Map} of params needed for subsequent multipart calls. For convenience.
   */
  public Map<String, String> getUploadParams() {
    Map<String, String> parameters = new HashMap<>();
    parameters.put("uri", uri);
    parameters.put("region", region);
    parameters.put("upload_id", uploadId);
    return parameters;
  }

  public boolean isIntelligent() {
    return uploadType != null && uploadType.equals("intelligent_ingestion");
  }
}
