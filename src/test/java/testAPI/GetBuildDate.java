package testAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import utility.BaseClass;

import java.io.IOException;

public class GetBuildDate {
        public Response response;
        public JsonPath jsonPathEvaluator;
        String path = "TestData_BuildDate_107.xlsx";
    private static final Logger LOGGER = LogManager.getLogger("GetBuildDate.class" );



        @BeforeClass
        public void getURL() {
            BaseClass.init();
            RestAssured.baseURI = BaseClass.prop.getProperty("baseURL");
            RequestSpecification httpRequest = RestAssured.given();
            response = httpRequest.get(BaseClass.prop.getProperty("builddate"), new Object[0]);
            jsonPathEvaluator = response.jsonPath();
            LOGGER.info("Validating BuildDate..");
            LOGGER.info(response.body().print());
        }

        @Test(priority = 1)
        public void validateRespHeadBuild() {
            LOGGER.info("Validating Response Header...");
            Assert.assertEquals( response.getStatusCode(),200);
            Assert.assertEquals( response.getHeaders().getValue("content-type"), "application/json;charset=UTF-8");
        }

        @DataProvider(name = "readTestDataBuildDate")
        public Object[][] provideTestBuildDate() {
            LOGGER.info("Fetching Test Data..");
            Object[][] o = new Object[0][];
            try {
                o = BaseClass.readTestData(path);
            } catch (IOException e) {
                LOGGER.trace("Exception Occured..", e);
              e.printStackTrace();
            }

            return o;
        }

        @Test(priority = 2,  dataProvider = "readTestDataBuildDate" )
        public void validateBody(Object x, Object y) {
                     String year1 = (String)x;
            String year2 = (String)y;
            LOGGER.info("Validating Body Content.." + year1 + ":" + year2);
            Assert.assertEquals( jsonPathEvaluator.get("wkda." + year1), year2);
        }

    @AfterClass()

    public void endofTest(){

        LOGGER.info("****End of BuildDate Test****");

    }

}


