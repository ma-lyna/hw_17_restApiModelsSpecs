package in.reqres;

import models.lombok.*;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.UserSpec.*;

public class ReqresInTests {
    @Test
    void createUserTest() {
        CreateUserLombokModel сreateUserLombokModel = new CreateUserLombokModel();
        сreateUserLombokModel.setName("Alex");
        сreateUserLombokModel.setJob("developer");

        CreateUserResponseLombokModel response = step("Send request", () ->
                given(createUserRequestSpec)
                        .body(сreateUserLombokModel)
                        .when()
                        .post()
                        .then()
                        .spec(createUserResponseSpec)
                        .extract().as(CreateUserResponseLombokModel.class));

        step("Check name in response", () ->
                    assertEquals("Alex", response.getName()));
        step("Check job in response", () ->
                    assertEquals("developer", response.getJob()));
        }

    @Test
    void updateUserTest() {
        UpdateUserLombokModel updateUserLombokModel = new UpdateUserLombokModel();
        updateUserLombokModel.setName("morpheus");
        updateUserLombokModel.setJob("qa");

        UpdateUserResponseLombokModel response = step("Send request", () ->
                given(updateUserRequestSpec)
                        .body(updateUserLombokModel)
                        .when()
                        .put()
                        .then()
                        .spec(updateUserResponseSpec)
                        .extract().as(UpdateUserResponseLombokModel.class));
        step("Check name in response", () ->
                assertEquals("morpheus", response.getName()));
        step("Check job in response", () ->
                assertEquals("qa", response.getJob()));
    }

    @Test
    void deleteUserTest() {
        step("Delete request and check status code", () ->
                given(requestSpec)
                        .when()
                        .delete("/users/2")
                        .then()
                        .spec(deleteUserResponseSpec));
    }

    @Test
    void getYearOfSingleResourceTest() {
        GetYearLombokModel response = step("Send request", () ->

                given(baseRequestSpec)
                        .when()
                        .get("/api/unknown/2")
                        .then()
                        .statusCode(200)
                        .spec(resourceDataResponseSpec)
                        .extract().as(GetYearLombokModel.class));

        step("Check year in response", () ->
                assertEquals("2001", response.getData().getYear()));
    }

    @Test
    void checkPagesWithUsersTest() {
        step("Check total pages", () ->
                given(request)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .spec(responseOk200))
             .body("total_pages", is(2));

    }
}

