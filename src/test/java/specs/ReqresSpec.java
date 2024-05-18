package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.*;
import static io.restassured.http.ContentType.JSON;

public class ReqresSpec {
    public static ResponseSpecification createUserResponseSpec = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(STATUS)
            .log(BODY)
            .build();

public static ResponseSpecification getUserNotFoundResponseSpec = new ResponseSpecBuilder()
        .expectStatusCode(404)
        .log(STATUS)
        .log(BODY)
        .build();

public static ResponseSpecification singleUserResponseSpec = new ResponseSpecBuilder()
        .expectStatusCode(200)
        .log(STATUS)
        .log(BODY)
        .build();

public static RequestSpecification userRequestSpec = with()
        .filter(withCustomTemplates())
        .contentType(JSON)
        .log().uri();
}
