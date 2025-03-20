package com.example.teamcity.api.spec;

import com.example.teamcity.api.config.Config;
import com.example.teamcity.api.models.User;
import io.restassured.authentication.AuthenticationScheme;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.client.methods.RequestBuilder;

import java.util.List;

public class Specification {
    private static Specification spec;

    private String url = Config.getProperties("host") + ":" +Config.getProperties("port");

    private Specification(){

    }

    public static Specification getSpecification(){
        if(spec == null)
            spec = new Specification();
        return spec;
    }

    private RequestSpecBuilder reqBuilder(){
        RequestSpecBuilder requestBuilder = new RequestSpecBuilder();
        requestBuilder.setBaseUri("http://" + url).build();
        requestBuilder.setContentType(ContentType.JSON);
        requestBuilder.setAccept(ContentType.JSON);
        requestBuilder.addFilters(List.of(new RequestLoggingFilter(), new ResponseLoggingFilter()));

        return requestBuilder;
    }

    public RequestSpecification unauthSpec(){
        return reqBuilder().build();
    }

    public RequestSpecification authSpec(User user){
        BasicAuthScheme basicAuth = new BasicAuthScheme();
        basicAuth.setUserName(user.getUser());
        basicAuth.setPassword(user.getPassword());

        return reqBuilder()
                .setAuth(basicAuth)
                .build();
    }
}
