package com.genesys.csaba.hw.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genesys.csaba.hw.api.pojo.User;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class RestApiTest {
    private static final Logger logger = LoggerFactory.getLogger(RestApiTest.class);

    @Test(priority = 12)
    @Parameters({"host", "endpoint"})
    public void firstEmailContainsAtSymbol(String host, String endpoint) {
        logger.info("Starting test...");
        logger.debug("Creating http client");
        OkHttpClient client = new OkHttpClient();
        logger.info("Creating request");
        Request request = new Request.Builder()
                .url(host + endpoint)
                .build();

        logger.info("Sending GET request to {}{}", host, endpoint);
        try (Response response = client.newCall(request).execute()) {
            logger.info("Parsing response JSON");
            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            User[] users;
            try {
                logger.info("Users in request:");
                users = objectMapper.readValue(response.body().string(), User[].class);
                for (User u : users) {
                    logger.info("{} | {}", u.getName(), u.getEmail());
                }
                Assert.assertTrue(users[0].getEmail().contains("@"));
            } catch (JsonProcessingException e) {
                logger.error("An exception occurred during JSON parsing");
                logger.error("Error message: {}", e.getMessage());
                Assert.fail();
            }
        } catch (IOException e) {
            logger.error("An I/O exception occurred during API request");
            logger.error("Error message: {}", e.getMessage());
            Assert.fail();
        }
    }
}

