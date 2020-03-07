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

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;


public class GetManufacturer {

    Response response;
    JsonPath jsonPathEvaluator;
  String  path= "C:\\Users\\DANISH\\IdeaProjects\\RestAssured\\TestData_ManuFacturer.xlsx";
    Object [][]o;;

    @BeforeMethod
    public void getURL() {
        BaseClass.init();
        RestAssured.baseURI =  BaseClass.prop.getProperty("baseURL");
        RequestSpecification httpRequest = RestAssured.given();
        response = httpRequest.get(BaseClass.prop.getProperty("manufacturer"));
        jsonPathEvaluator = response.jsonPath();

    }

    @Test(priority=1)
    public void validateRespHeadManu(){
        Assert.assertEquals(200, response.getStatusCode());
        Assert.assertTrue( true,response.getHeaders().getValue("access-control-max-age" ));

    }



    @Test(priority = 2)
     public void validateBodyHeadManu(){

        Assert.assertEquals(2147483647,jsonPathEvaluator.get("pageSize"));

    }

    @DataProvider(name = "readData")

    public Object[][] provideTestData() {

        try {
            o = BaseClass.readTestData(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return o;
    }

    @Test(priority=3, dataProvider = "readData")
    public void validateBodyManu(Object x , Object y) {
        String code= (String)x;
        String model= (String)y;
        Assert.assertEquals(model,jsonPathEvaluator.get("wkda."+code));

    }
}
