package in.reqres;

import models.lombok.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.UserSpec.*;

public class ReqresIn {

        @Test
        @DisplayName("Create a user")
        void lombokCreateUser() {

            CreateUserLombokModel сreateUserLombokModel = new CreateUserLombokModel();
            сreateUserLombokModel.setName("Alex");
            сreateUserLombokModel.setJob("developer");

            CreateUserResponseLombokModel response = step("Запрос", () ->
                    given(createUserRequestSpec)
                            .body(сreateUserLombokModel)
                            .when()
                            .post()
                            .then()
                            .spec(createUserResponseSpec)
                            .extract().as(CreateUserResponseLombokModel.class));

          step("Проверка в ответе имени", () ->
                    assertEquals("Alex", response.getName()));
        step("Проверка в ответе работы", () ->
                    assertEquals("developer", response.getJob()));
        }

    @Test
    @DisplayName("Изменение пользователя")
    void updateUser() {

        UpdateUserLombokModel updateUserLombokModel = new UpdateUserLombokModel();
        updateUserLombokModel.setName("morpheus");
        updateUserLombokModel.setJob("qa");

        UpdateUserResponseLombokModel response = step("Запрос", () ->
                given(updateUserRequestSpec)
                        .body(updateUserLombokModel)
                        .when()
                        .put()
                        .then()
                        .spec(updateUserResponseSpec)
                        .extract().as(UpdateUserResponseLombokModel.class));
        step("Проверка в ответе имени", () ->
                assertEquals("morpheus", response.getName()));
        step("Проверка в ответе работы", () ->
                assertEquals("qa", response.getJob()));
    }

    @Test
    @DisplayName("Delete a user")
    void deleteUser() {
            step("Make user delete request and check status code", () ->
                    given(requestSpec)
                            .when()
                            .delete("/users/2")
                            .then()
                            .spec(deleteUserResponseSpec));
        }




        @Test
        void getYearOfSingleResource() {

            GetYearLombokModel response = step("Make request to get resource data", () ->

                    given(baseRequestSpec)
                            .when()
                            .get("/api/unknown/2")
                            .then()
                            .statusCode(200)
                            .spec(resourceDataResponseSpec)
                            .extract().as(GetYearLombokModel.class));


            step("Проверка в ответе имени", () ->
                    assertEquals("2001", response.getData().getYear()));

        }

        @Test
    @DisplayName("Проверка вывода текста и урла для раздела поддержки")
    void checkPagesWithUsers() {

            step("Провить вывод текста и урла для раздела поддержки", () ->
                given(request)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .spec(responseOk200))
                .body("total_pages", is(2));

        }







}

