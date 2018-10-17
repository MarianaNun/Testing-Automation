package Services;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Services.Insumo;


public class InsumoTest {
	
	private Insumo insumo = new Insumo(4, "MacBook Pro", 400);

	@BeforeMethod
	public void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
		RestAssured.basePath = "/api/product";
	}

	@Test
	public void getAll() {
		Response respuesta = RestAssured.given().log().all().get();
		respuesta.prettyPrint();
		assertEquals(respuesta.getStatusCode(), 200);
		JsonPath json = respuesta.jsonPath();
		assertFalse(json.getList("products").isEmpty(), "Lista de productos vacia.");
		assertEquals(json.getInt("products[1].id"), 2);
		assertEquals(json.getString("products[1].nombre"), "CPU");
		assertEquals(json.getInt("products[1].cantidad"), 3);
	}
	
	@Test
	public void getWithPathParam() {
		Response respuesta = RestAssured.given().log().all()
				.pathParam("idProducto", "3").get("/{idProducto}");
		respuesta.prettyPrint();
		assertEquals(respuesta.getStatusCode(), 200);
		JsonPath json = respuesta.jsonPath();
		assertEquals(json.getInt("id"), 3);
		assertEquals(json.getString("nombre"), "teclado");
		assertEquals(json.getInt("cantidad"), 20);
		
	}
	
	@Test
	public void addProduct() {
		Response respuesta = RestAssured.given().log().all()
				.contentType(ContentType.JSON).body(insumo).post();
		respuesta.prettyPrint();
		assertEquals(respuesta.statusCode(), 201);
		JsonPath json = respuesta.jsonPath();
		assertEquals(json.getString("message"), "El producto se ha recibido");
	}
	
	@Test(dependsOnMethods = "addProduct")
	public void getUsingQueryParam() {
		Response respuesta = RestAssured.given().log().all()
				.queryParam("productId", insumo.getId()).get();
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
				.pathParam("idProducto", insumo.getId()).delete("/{idProducto}");
		respuesta.prettyPrint();
		assertEquals(respuesta.statusCode(), 200);
		JsonPath json = respuesta.jsonPath();
		assertEquals(json.getString("message"), "El producto ha sido eliminado exitosamente.");
	}
		
}