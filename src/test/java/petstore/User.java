package petstore;

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class User {
    String uri = "https://petstore.swagger.io/v2/user";

    public String lerJson(String caminhoJson) throws IOException{
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test(priority = 1)
    public void createUser() throws IOException {
        String jsonBody = lerJson("data/user1.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
                .when()
                .post(uri)
                .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is("10987"))
        ;
    }

    @Test(priority = 2)
    public void searchUser(){
        given()
                .contentType("application/json")
                .log().all()
                .when()
                .get(uri + "/" + "jonathaspe")
                .then()
                .log().all()
                .statusCode(200)
                .body("phone", is("81987366473"))
                .body("firstName", is("Jonathas"))
                .body("lastName", is("Santos"))
                .body("id", is(10987))
        ;
    }

    @Test(priority = 3)
    public void changeUser() throws IOException {
        String jsonBody = lerJson("data/user2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
                .when()
                .put(uri + "/" + "jonathaspe")
                .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
        ;
    }

    @Test(priority = 4)
    public void deleteUser(){
        given()
                .contentType("application/json")
                .log().all()
                .when()
                .get(uri + "/" + "jonathaspe")
                .then()
                .log().all()
                .statusCode(200)
                .body("type", is("unknown"))
                .body("message", is("jonathaspe"))
        ;
    }
}


