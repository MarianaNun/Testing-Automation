package services;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import services.Insumo;


public class InsumoTest {
	
	private Insumo insumo = new Insumo(3, "Marcador", 400);

	@BeforeMethod
	public void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
		RestAssured.basePath = "/api/insumo";
	}

	@Test
	public void getAll() {
		Response respuesta = RestAssured.given().log().all().get();
		respuesta.prettyPrint();
		assertEquals(respuesta.getStatusCode(), 200);
		JsonPath json = respuesta.jsonPath();
		assertFalse(json.getList("insumos").isEmpty(), "Lista de insumos vacia.");
		assertEquals(json.getInt("insumos[1].id"), 2);
		assertEquals(json.getString("insumos[1].nombre"), "papel A4");
		assertEquals(json.getInt("insumos[1].cantidad"), 5);
	}
	
	@Test
	public void getWithPathParam() {
		Response respuesta = RestAssured.given().log().all()
				.pathParam("id", "3").get("/{id}");
		respuesta.prettyPrint();
		assertEquals(respuesta.getStatusCode(), 200);
		JsonPath json = respuesta.jsonPath();
		assertEquals(json.getInt("id"), 3);
		assertEquals(json.getString("nombre"), "Marcador");
		assertEquals(json.getInt("cantidad"), 400);
		
	}
	
	@Test
	public void addProduct() {
		Response respuesta = RestAssured.given().log().all()
				.contentType(ContentType.JSON).body(insumo).post();
		respuesta.prettyPrint();
		assertEquals(respuesta.statusCode(), 201);
		JsonPath json = respuesta.jsonPath();
		assertEquals(json.getString("mensaje"), "El insumo se ha agregado");
	}
	
	@Test(dependsOnMethods = "addProduct")
	public void getUsingQueryParam() {
		Response respuesta = RestAssured.given().log().all()
				.queryParam("id", insumo.getId()).get();
		respuesta.prettyPrint();
		assertEquals(respuesta.statusCode(), 200);
		JsonPath json = respuesta.jsonPath();
		assertEquals(json.getInt("id"), insumo.getId());
		assertEquals(json.getString("nombre"), insumo.getNombre());
		assertEquals(json.getInt("cantidad"), insumo.getCantidad());
	}
	
	@Test(dependsOnMethods = "getUsingQueryParam")
	public void deleteProduct(){
		Response respuesta = RestAssured.given().log().all()
				.pathParam("id", insumo.getId()).delete("/{id}");
		respuesta.prettyPrint();
		assertEquals(respuesta.statusCode(), 200);
		JsonPath json = respuesta.jsonPath();
		assertEquals(json.getString("message"), "El insumo ha sido eliminado exitosamente.");
	}
		
}
