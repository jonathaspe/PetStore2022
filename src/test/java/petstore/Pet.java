// 1 - Pacote
package petstore;

// 2 - Bibliotecas

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

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
    @Test // Identifica o método ou função como um teste para o TesteNG
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
                .statusCode(200);
    }
}
