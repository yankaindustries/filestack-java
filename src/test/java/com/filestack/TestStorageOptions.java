package com.filestack;

import com.google.gson.JsonObject;

import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

public class TestStorageOptions {

  @Test
  public void testGetTask() {
    String correct = "store="
        + "access:private,"
        + "base64decode:false,"
        + "container:some_bucket,"
        + "filename:some_file.txt,"
        + "location:S3,"
        + "path:/some/path/,"
        + "region:us-east-1";

    StorageOptions options = new StorageOptions.Builder()
        .filename("some_file.txt")
        .location("S3")
        .path("/some/path/")
        .container("some_bucket")
        .region("us-east-1")
        .access("private")
        .base64Decode(false)
        .build();

    Assert.assertEquals(correct, options.getAsTask().toString());
  }

  @Test
  public void testGetPartMap() throws IOException {
    StorageOptions options = new StorageOptions.Builder()
        .access("<access>")
        .container("<container>")
        .location("<location>")
        .path("<path>")
        .region("<region>")
        .build();

    JsonObject map = options.getAsPartMap();

    Assert.assertEquals("<access>", map.getAsJsonPrimitive("store_access").getAsString());
    Assert.assertEquals("<container>", map.getAsJsonPrimitive("store_container").getAsString());
    Assert.assertEquals("<location>", map.getAsJsonPrimitive("store_location").getAsString());
    Assert.assertEquals("<path>", map.getAsJsonPrimitive("store_path").getAsString());
    Assert.assertEquals("<region>", map.getAsJsonPrimitive("store_region").getAsString());
  }

  @Test
  public void testGetPartMapLocationDefault() throws Exception {
    StorageOptions options = new StorageOptions.Builder().build();

    JsonObject map = options.getAsPartMap();

    Assert.assertEquals("s3", map.getAsJsonPrimitive("store_location").getAsString());
  }
}
