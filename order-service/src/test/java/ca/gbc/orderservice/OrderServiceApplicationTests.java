package ca.gbc.orderservice;

import ca.gbc.orderservice.stub.InventoryClientStub;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;


@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceApplicationTests {

    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("testdb")
            .withUsername("admin")
            .withPassword("password");

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

   static {
        postgreSQLContainer.start();
   }


    @Test
    void placeOrderTest() {
        // Request body for placing an order
        String requestBody = """
                {
                    "skuCode": "sku001",
                    "price": 100.00,
                    "quantity": 5
                }
                """;

        InventoryClientStub.stubInventoryCall("sku0001", 10);

        var responseBodyString =  RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/order")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .body().asString();
        assertThat(responseBodyString, Matchers.is("Order Placed Successfully"));
    }


}
