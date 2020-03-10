package testAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utility.BaseClass;

public class GetMainType {

    Response response;
    JsonPath jsonPathEvaluator;

    private static final Logger LOGGER = LogManager.getLogger("GetMainType.class" );

    @BeforeClass
    public void getURL() {
        BaseClass.init();
        RestAssured.baseURI = BaseClass.prop.getProperty("baseURL");
        RequestSpecification httpRequest = RestAssured.given();
        response = httpRequest.get(BaseClass.prop.getProperty("maintype"));
       jsonPathEvaluator = response.jsonPath();
        LOGGER.info("Validating MainType..");
        LOGGER.info(response.body().print());
    }

    @Test(priority = 1)
    public void validateRespHeadMain() {
        LOGGER.info("Validating Response Header...");
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertEquals("chunked", response.getHeaders().getValue("transfer-encoding"));
    }

    @Test(priority = 2)
    public void validateBodyHeadMain() {
        LOGGER.info("Validate Body header");
        Assert.assertEquals(2147483647, jsonPathEvaluator.get("pageSize"));

    }

    @AfterClass()

    public void endofTest(){

        LOGGER.info("****End of MainType Test****");

    }
}


