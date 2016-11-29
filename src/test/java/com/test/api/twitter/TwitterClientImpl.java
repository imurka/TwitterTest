package com.test.api.twitter;

import com.test.api.model.MediaUploadResponse;
import org.glassfish.jersey.client.oauth1.AccessToken;
import org.glassfish.jersey.client.oauth1.ConsumerCredentials;
import org.glassfish.jersey.client.oauth1.OAuth1ClientSupport;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

/**
 * Created by Iryna on 29.11.2016.
 */
public class TwitterClientImpl implements TwitterClient {

    private static final String POST_URI = "https://api.twitter.com/1.1/statuses/update.json";

    private static final String MEDIA_UPLOAD = "https://upload.twitter.com/1.1/media/upload.json";

    private Client client;

    public TwitterClientImpl(ConsumerCredentials consumerCredentials, AccessToken accessToken){

        Feature filterFeature = OAuth1ClientSupport
                .builder(consumerCredentials)
                .feature()
                .accessToken(accessToken)
                .build();

        this.client = ClientBuilder.newBuilder()
                .register(filterFeature)
                .register(JacksonFeature.class)
                .register(MultiPartFeature.class)
                .build();

    }

    @Override
     public boolean tweet(String tweet, int status) {
        Form formData = new Form();
        formData.param("status", tweet);
        Response response = client.target(POST_URI).request().post(Entity.form(formData));
        return  response.getStatus() == status;
    }

    @Override
    public boolean uploadFile(String fileName, int status) {
        MultiPart multiPart = new MultiPart();
        ClassLoader classLoader = getClass().getClassLoader();
        FileDataBodyPart fileUpload = new FileDataBodyPart("media", new File(classLoader.getResource(fileName).getFile()), MediaType.MULTIPART_FORM_DATA_TYPE);
        multiPart.bodyPart(fileUpload);
        Response response = client.target(MEDIA_UPLOAD).request().post(Entity.entity(multiPart, MediaType.MULTIPART_FORM_DATA_TYPE));
        return  response.getStatus() == status;
    }

}
