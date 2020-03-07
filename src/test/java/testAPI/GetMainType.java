package testAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utility.BaseClass;

public class GetMainType {

    Response response;
    JsonPath jsonPathEvaluator;



    @BeforeMethod
    public void getURL() {
        BaseClass.init();
        RestAssured.baseURI = BaseClass.prop.getProperty("baseURL");
        RequestSpecification httpRequest = RestAssured.given();
        response = (Response)httpRequest.get(BaseClass.prop.getProperty("maintype"), new Object[0]);
        jsonPathEvaluator = response.jsonPath();
    }

    @Test(priority = 1)
    public void validateRespdHeadMain() {
        Assert.assertEquals(200, this.response.getStatusCode());
        Assert.assertEquals("chunked", response.getHeaders().getValue("transfer-encoding"));
    }

    @Test(priority = 2)
    public void validateBodyHeadMain() {
        Assert.assertEquals(2147483647, jsonPathEvaluator.get("pageSize"));

    }
}


