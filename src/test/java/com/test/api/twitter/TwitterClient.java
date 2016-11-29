package com.test.api.twitter;

import com.test.api.model.MediaUploadResponse;


/**
 * Created by Iryna on 29.11.2016.
 */
public interface TwitterClient {

    boolean tweet(String tweet, int status);

    boolean uploadFile(String file, int status);

}
