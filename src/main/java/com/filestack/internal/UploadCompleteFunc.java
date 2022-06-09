package com.filestack.internal;

import com.filestack.FileLink;
import com.filestack.internal.responses.CompleteResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.reactivex.Flowable;

import java.util.concurrent.Callable;

/**
 * Function to be passed to {@link Flowable#fromCallable(Callable)}.
 * Handles completing a multipart upload, gets metadata for final file.
 * In intelligent ingestion mode the {@link UploadService#complete(JsonObject)} call may return a
 * 202 response while the parts are still processing. In this case the {@link RetryNetworkFunc}
 * will handle it like a failure and automatically retry.
 */
public class UploadCompleteFunc implements Callable<Prog> {
  private final UploadService uploadService;
  private final Upload upload;
  
  UploadCompleteFunc(UploadService uploadService, Upload upload) {
    this.uploadService = uploadService;
    this.upload = upload;
  }
  
  @Override
  public Prog call() throws Exception {
    final long startTime = System.currentTimeMillis() / 1000;
    final JsonObject params = upload.baseParams.deepCopy();

    if (!upload.intel) {
      JsonArray jsonArray = new JsonArray();
      for (int i = 0; i < upload.etags.length; i++) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("part_number", i + 1);
        jsonObject.addProperty("etag", upload.etags[i]);
        jsonArray.add(jsonObject);
      }
      params.add("parts", jsonArray);
    }

    if (upload.uploadTags != null && !upload.uploadTags.isEmpty()) {
      JsonObject uploadJson = new JsonObject();
      upload.uploadTags.forEach(uploadJson::addProperty);
      params.add("upload_tags", uploadJson);
    }

    RetryNetworkFunc<CompleteResponse> func;
    func = new RetryNetworkFunc<CompleteResponse>(5, 5, Upload.DELAY_BASE) {

      @Override
      Response<CompleteResponse> work() throws Exception {
        return uploadService.complete(params);
      }
    };

    CompleteResponse response = func.call();
    FileLink fileLink = new FileLink(upload.clientConf, response.getHandle());

    long endTime = System.currentTimeMillis() / 1000;
    return new Prog(startTime, endTime, fileLink);
  }
}
