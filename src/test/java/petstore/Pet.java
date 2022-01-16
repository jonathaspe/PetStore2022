// 1 - Pacote
package petstore;

// 2 - Bibliotecas

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.contains;

// 3 - Classe
public class Pet {
    // 3.1 - Atributos
    String uri = "https://petstore.swagger.io/v2/pet"; // endereço para a classe Pet

    // 3.2 - Métodos e Funções

    public String lerJson(String categoryId) throws IOException {
        // o trecho "throws IOException" foi uma exceção adicionada automaticamente pelo IJ
        return new String(Files.readAllBytes(Paths.get(categoryId)));
    }

    // Incluir - Create - Post
    @Test(priority = 1) // Identifica o método ou função como um teste para o TesteNG
    public void createPet() throws IOException {
        String jsonBody = lerJson("data/pet1.json");

        // Sintaxe Gherkin
        // Dado (Given) - Quando (When) - Então (Then)

        given()
                .contentType("application/json") // comum em API REST
                .log().all()
                .body(jsonBody)
                .when()
                .post(uri)
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Garfield"))
                .body("status", is("available"))
                .body("category.name", is("cat"))
                .body("tags.name", contains("vacinado"))
        ;

    }

    @Test(priority = 2)
    public void searchPet(){
        String petId = "782368276433";

        given()
                .contentType("application/json")
                .log().all()
                .when()
                .get(uri + "/" + petId)
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Garfield"))
                .body("status", is("available"))
                .body("category.name", is("cat"))
        ;
    }

    @Test(priority = 3)
    public void changePet() throws IOException {
        String jsonBody = lerJson("data/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
                .when()
                .put(uri)
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Garfield"))
                .body("category.name", is("cat"))
                .body("status", is("sold"))
        ;
    }

    @Test(priority = 4)
    public void deletePet(){
        String petId = "782368276433";

        given()
                .contentType("application/json")
                .log().all()
                .when()
                .delete(uri + "/" + petId)
                .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(petId))
        ;
    }
}
