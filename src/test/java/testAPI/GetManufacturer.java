package testAPI;


import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.annotations.*;
import utility.BaseClass;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;


public class GetManufacturer {

    Response response;
    JsonPath jsonPathEvaluator;
  //  String  path= "C:\\Users\\DANISH\\IdeaProjects\\RestAssured\\TestData_ManuFacturer.xlsx";
   String path= "TestData_ManuFacturer.xlsx";
    Object [][]o;;

    private static final Logger LOGGER = LogManager.getLogger("GetManufacturer.class" );

    @BeforeClass
    public void getURL() {
        PropertyConfigurator.configure("src\\test\\java\\config\\log4J.properties");
        BaseClass.init();
        RestAssured.baseURI =  BaseClass.prop.getProperty("baseURL");
        RequestSpecification httpRequest = RestAssured.given();
        response = httpRequest.get(BaseClass.prop.getProperty("manufacturer"));
        jsonPathEvaluator = response.jsonPath();
        LOGGER.info("Validating Manufacturer");
        LOGGER.info(response.body().print());

    }

    @Test(priority=1)
    public void validateRespHeadManu(){
        LOGGER.info("Validate Response Header...");
       Assert.assertEquals( response.getStatusCode(), 200);
        Assert.assertTrue( true,response.getHeaders().getValue("access-control-max-age" ));



    }



    @Test(priority = 2)
     public void validateBodyHeadManu(){
        LOGGER.info("Validating Body Header..");
        Assert.assertEquals(jsonPathEvaluator.get("pageSize"), 2147483647);


    }

    @DataProvider(name = "readData")

    public Object[][] provideTestData() {
        LOGGER.info("Fetching Test Data..");
            try {
            o = BaseClass.readTestData(path);
        } catch (IOException e) {
                LOGGER.trace("Exception Occured..", e);
            e.printStackTrace();


        }

        return o;
    }

    @Test(priority=3, dataProvider = "readData")
    public void validateBodyManu(Object x , Object y) {
       String code= (String)x;
        String model= (String)y;
        LOGGER.info("Validating Body Content.." + code + ":" + model);
        Assert.assertEquals(jsonPathEvaluator.get("wkda."+code), model);

    }

    @AfterClass()

   public void endofTest(){

        LOGGER.info("****End of ManuFacturer Test****");

    }



}
