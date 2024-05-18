package tests;

import models.lombok.SingleResourceBody;
import models.lombok.UserCreationResponse;
import models.lombok.UserDataBody;
import models.lombok.UserUpdateResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.ReqresSpec.*;

@DisplayName("Unit 16. Homework test cases")
public class ReqresTests extends TestBase {
    @Test
    @DisplayName("Verify response data after successful user info update with method PUT")
    void successfulUserInfoUpdateTest() {
        UserDataBody userDataBody = new UserDataBody();
        userDataBody.setName("user1");
        userDataBody.setJob("check put");

        UserUpdateResponse response = step("Send request", ()->
                given(userRequestSpec)
                .body(userDataBody)
                .when()
                .put("/users/2")
                .then()
                .spec(singleUserResponseSpec)
                .extract().as(UserUpdateResponse.class));

        step("Check response", ()-> {
            assertThat(userDataBody.getName()).isEqualTo(response.getName());
            assertThat(userDataBody.getJob()).isEqualTo(response.getJob());
            assertThat(response.getUpdatedAt()).isNotNull();
        });
    }

    @Test
    @DisplayName("Verify response data after successful user info update with method PATCH")
    void successfulUserInfoUpdateWithPatchTest() {
        UserDataBody userDataBody = new UserDataBody();
        userDataBody.setName("user2");
        userDataBody.setJob("check patch");

        UserUpdateResponse response = step("Send request", ()->
                given(userRequestSpec)
                .body(userDataBody)
                .when()
                .patch("/users/2")
                .then()
                .spec(singleUserResponseSpec)
                .extract().as(UserUpdateResponse.class));

        step("Check response", ()-> {
            assertThat(userDataBody.getName()).isEqualTo(response.getName());
            assertThat(userDataBody.getJob()).isEqualTo(response.getJob());
            assertThat(response.getUpdatedAt()).isNotNull();
        });
    }

    @Test
    @DisplayName("Verify user info data received using method GET")
    void successfulSingleUserGetTest() {
        SingleResourceBody response = step("Send request", ()->
                given(userRequestSpec)
                .get("/users/2")
                .then()
                .spec(singleUserResponseSpec)
                .extract().as(SingleResourceBody.class));

        step("Check response", () -> {
            assertThat(2).isEqualTo(response.getData().getId());
            assertThat("janet.weaver@reqres.in").isEqualTo(response.getData().getEmail());
            assertThat("Janet").isEqualTo(response.getData().getFirst_name());
            assertThat("Weaver").isEqualTo(response.getData().getLast_name());
            assertThat("https://reqres.in/img/faces/2-image.jpg").isEqualTo(response.getData().getAvatar());
            assertThat("https://reqres.in/#support-heading").isEqualTo(response.getSupport().getUrl());
            assertThat("To keep ReqRes free, contributions towards server costs are appreciated!").isEqualTo(response.getSupport().getText());
        });
    }

    @Test
    @DisplayName("Verify status code 404 - single user not found")
    void checkSingleUserNotFound404Test() {
        step("Send request and check unsuccessful response 404 ", () -> {
            given(userRequestSpec)
                    .get("/users/23")
                    .then()
                    .spec(getUserNotFoundResponseSpec);
        });
    }

    @Test
    @DisplayName("Verify response data after successful user creation using method POST")
    void successfulUserCreationTest() {
        UserDataBody userDataBody = new UserDataBody();
        userDataBody.setName("user3");
        userDataBody.setJob("check post");

        UserCreationResponse response = step("Send request", () ->
                given(userRequestSpec)
                .body(userDataBody)
                .when()
                .post("/users")
                .then()
                .spec(createUserResponseSpec)
                .extract().as(UserCreationResponse.class));

        step("Check response", () -> {
            assertThat(userDataBody.getName()).isEqualTo(response.getName());
            assertThat(userDataBody.getJob()).isEqualTo(response.getJob());
            assertThat(response.id).isNotNull();
            assertThat(response.createdAt).isNotNull();
        });

    }
}
