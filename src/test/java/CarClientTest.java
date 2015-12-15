import com.epam.aqa.shkliarov.entity.CarEntity;
import com.epam.aqa.shkliarov.server.ServerManager;
import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.client.ClientResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

public class CarClientTest {

    private static final String JSON_ARRAY_PATH = "src\\main\\resources\\cars.json";
    public static final String NEW_CAR = "\\src\\main\\resources\\newCar.json";

    private static JSONArray jsonCountryArray;
    private WebTarget target;


    @BeforeMethod
    public void setUp() throws Exception {
        Client client = ClientBuilder.newClient();
        target = client.target(ServerManager.getURI());
    }

    @Test(priority = -1)
    public void testSave() {
        try (InputStream is = new FileInputStream(JSON_ARRAY_PATH)) {
            jsonCountryArray = new JSONArray(IOUtils.toString(is));
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        Response response = target.path("rest").path("cars").request().post(Entity.entity(jsonCountryArray, MediaType.APPLICATION_JSON));
        assertEquals(200, response.getStatus());
        assertEquals("Countries have been successfully added", response.getEntity());
    }

    @Test
    public void testGet() {
        ClientResponse response = target.path("rest").path("cars").request(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        assertEquals(200, response.getStatus());

        response = target.path("rest").path("car").path("2").request(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        assertEquals(200, response.getStatus());
        assertEquals(2, response.getEntity());
    }

    @Test
    public void testPut() throws JSONException {
        JSONObject newCity = new JSONObject(NEW_CAR);
        target.path("rest").path("car").path("3").request().put(Entity.entity(newCity, MediaType.APPLICATION_JSON));

        ClientResponse response = target.path("rest").path("car").path("3").request(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        CarEntity carEntity = (CarEntity) response.getEntity();

        assertEquals(200, response.getStatus());
        assertEquals(3, carEntity.getId());
        assertEquals("Porsche", carEntity.getManufacturer());
    }

    @Test
    public void testDelete() {
        ClientResponse response = target.path("rest").path("country").path("5").request(MediaType.APPLICATION_JSON).delete(ClientResponse.class);
        assertEquals(200, response.getStatus());
        assertEquals("country has been successfully deleted", response.getEntity());

        response = target.path("rest").path("country").path("5").request(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        assertNull(response.getEntity());
    }
}
