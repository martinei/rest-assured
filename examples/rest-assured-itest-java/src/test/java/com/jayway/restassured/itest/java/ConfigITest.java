/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jayway.restassured.itest.java;

import com.jayway.restassured.itest.java.support.WithJetty;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.config.EncoderConfig.encoderConfig;
import static com.jayway.restassured.config.RedirectConfig.redirectConfig;
import static com.jayway.restassured.config.RestAssuredConfig.newConfig;
import static org.hamcrest.Matchers.is;

public class ConfigITest extends WithJetty {

    @Test
    public void configCanBeSetPerRequest() throws Exception {
        given().
                config(newConfig().redirect(redirectConfig().followRedirects(false))).
                param("url", "/hello").
        expect().
                statusCode(302).
                header("Location", is("http://localhost:8080/hello")).
        when().
                get("/redirect");
    }

    @Test
    public void supportsSpecifyingDefaultContentCharset() throws Exception {
        given().
                config(newConfig().encoderConfig(encoderConfig().defaultContentCharset("US-ASCII"))).
                body("Something {\\+£???").
        expect().
                header("Content-Type", is("text/plain; charset=US-ASCII")).
        when().
                post("/reflect");
    }
}
