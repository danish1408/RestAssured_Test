package testAPI;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utility.BaseClass;

import java.io.IOException;

public class GetBuildDate {



        public Response response;
        public JsonPath jsonPathEvaluator;
        String path = "C:\\Users\\DANISH\\IdeaProjects\\RestAssured\\TestData_BuildDate_107.xlsx";



        @BeforeMethod
        public void getURL() {
            BaseClass.init();
            RestAssured.baseURI = BaseClass.prop.getProperty("baseURL");
            RequestSpecification httpRequest = RestAssured.given();
           response = (Response)httpRequest.get(BaseClass.prop.getProperty("builddate"), new Object[0]);
           jsonPathEvaluator = response.jsonPath();
        }

        @Test(priority = 1)
        public void validateRespHeadBuild() {
            Assert.assertEquals(200, this.response.getStatusCode());
            Assert.assertEquals("application/json;charset=UTF-8", response.getHeaders().getValue("content-type"));
        }

        @DataProvider(name = "readTestDataBuildDate")
        public Object[][] provideTestBuildDate() {
            Object[][] o = new Object[0][];

            try {
                o = BaseClass.readTestData(path);
            } catch (IOException var3) {
              var3.printStackTrace();
            }

            return o;
        }

        @Test(priority = 2,  dataProvider = "readTestDataBuildDate" )
        public void validateBody(Object x, Object y) {
            String year1 = (String)x;
            String year2 = (String)y;
            Assert.assertEquals(year2, jsonPathEvaluator.get("wkda." + year1));
        }

}


