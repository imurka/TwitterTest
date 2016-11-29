package com.test.api;

import com.test.api.twitter.TwitterClient;
import com.test.api.twitter.TwitterClientImpl;
import org.glassfish.jersey.client.oauth1.AccessToken;
import org.glassfish.jersey.client.oauth1.ConsumerCredentials;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TwitterAPITest {

    private TwitterClient twitterClient;


    @Before
    public void init(){
        final ConsumerCredentials consumerCredentials = new ConsumerCredentials("gCXtCF4yPhBUWVpEBUBYzbcPP", "OjSFhUXPgYnGhiD2bryI1HQuVfef0AyMRTqsSM8OCwuvqhpzEA");
        final AccessToken storedToken = new AccessToken("796136285400403972-LxLPQCVYs839m6nE7yEmiXy6nbN4VTu", "UbjZRiBDrvbTb9LEh9Wbmp9wEwsty0eqqqsXfkvRv9Le3");
        twitterClient = new TwitterClientImpl(consumerCredentials, storedToken);
    }

    /**
     *  Given Valid authentication credentials
     *  When User post valid tweet
     *  Then Status Code is 200
     */
    @Test
    public void testTweet() {
        Assert.assertTrue(twitterClient.tweet("Ability toissue authenticated requests on behalf", 200));
    }

    /**
     *  Given Valid authentication credentials
     *  When User post tweet that exceed allowed length
     *  Then Status Code is 403
     */
    @Test
    public void testTweetOverLength() {
        Assert.assertTrue(twitterClient.tweet("thrown when a tweet cannot be posted due to the user having no allowance remaining to post. Despite the text in the error message indicating that this error is only thrown when a daily limit is reached, this error will be thrown whenever a posting limitation has been reached. Posting allowances have roaming windows of time of unspecified duration.", 403));
    }

    /**
     *  Given Valid authentication credentials
     *  When User upload valid image
     *  Then Status Code is 200
     */
    @Test
    public void testValidFileUpload(){
        Assert.assertTrue(twitterClient.uploadFile("media/images.jpg", 200));
    }

    /**
     *  Given Valid authentication credentials
     *  When User upload file with invation extention
     *  Then Status Code is 400
     */
    @Test
    public void testInvalidFileUpload(){
        Assert.assertTrue(twitterClient.uploadFile("media/build.gradle", 400));
    }
}
