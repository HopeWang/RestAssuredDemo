import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class Xueqiu {
    public static String code;
    public static RequestSpecification requestSpecification;
    public static ResponseSpecification responseSpecification;

    @BeforeClass
    public static  void Login(){
        useRelaxedHTTPSValidation();
        RestAssured.proxy("127.0.0.1",8888);
        //baseURI = "https://api.xueqiu.com";
        requestSpecification = new RequestSpecBuilder().build();
        requestSpecification.port(80);
        requestSpecification.cookie("testerhome","hogwarts");
        requestSpecification.header("User-Agent","Xueqiu Android 11.15");
        //xueqiuLogin();
        responseSpecification = new ResponseSpecBuilder().build();
        responseSpecification.statusCode(200);


    }

    public static void xueqiuLogin(){
        String code = given()
                .header("User-Agent","Xueqiu Android 11.15")
                .queryParam("_t","1GENYMOTIONe965d4095a007d6ff00311052cde048b.4981523399.1551428383976.1551429206391")
                .queryParam("_s","6810f5")
                .cookie("xq_a_token","0e2b948e00ee6d848d9f52f30355102d749095cf")
                .cookie("u","4981523399")
                .formParam("grant_type","password")
                .formParam("telephone","13362371136")
                .formParam("password","e10adc3949ba59abbe56e057f20f883e")
                .formParam("areacode","86")
                .formParam("captcha","")
                .formParam("client_id","JtXbaMn7eP")
                .formParam("client_secret","txsDfr9FphRSPov5oQou74")
                .formParam("sid","1GENYMOTION0fc7e225d6ce0404300370e1d9ddde89")
                .when()
                .post("https://api.xueqiu.com/provider/oauth/token")
                .then()
                .log().all()
                .statusCode(400)
                .extract().path("error_code");

        System.out.println(code);

    }
    @Test
    public void testSearch(){
        useRelaxedHTTPSValidation();
        given().log().all()
                .queryParam("code","sogo")
                .header("Cookie","xq_a_token=8d9b4c9a0922fa4acf0e422386f73b35e43c598f; xq_a_token.sig=qY-F0dwymZ_7cm-Bet8ElBWma8A; xq_r_token=08aa0268446cd48dc83bbdc50bf8d0bbf5926898; xq_r_token.sig=yu41fOD2dC1Xzy_sSnQni8tSw5w; _ga=GA1.2.199549957.1551163645; _gid=GA1.2.742704931.1551163645; u=301551163645134; device_id=040f74e740544aca7b90318205e9d60b; Hm_lvt_1db88642e346389874251b5a1eded6e3=1551163645,1551163671,1551163688,1551193137; _gat=1; Hm_lpvt_1db88642e346389874251b5a1eded6e3=1551193490")
        .when()
                .get("https://xueqiu.com/stock/search.json")
        .then()
                .log().all()
                .statusCode(200)
                .body("stocks.name",hasItem("搜狗")).
                body("stocks.code",hasItem("SOGO"))
                .body("stocks.find { it.code == 'SOGO'}.name", equalTo("搜狗"));
    }

    @Test
    public void testLogin(){



        given()
                .queryParam("sort","relevance")
                .queryParam("count",5)
                .queryParam("page",1)
                .queryParam("q","sogo")
                .cookie("xq_a_token","0e2b948e00ee6d848d9f52f30355102d749095cf")
                .cookie("u","4981523399")
                .cookie("uid",code)
        .when().get("https://api.xueqiu.com/statuses/search.json")
         .then()
                .log().all()
                .statusCode(200);


    }


    @Test
    public void testLogin2(){
        useRelaxedHTTPSValidation();
        Response response = given()
                .header("User-Agent","Xueqiu Android 11.15")
                .queryParam("_t","1GENYMOTION0fc7e225d6ce0404300370e1d9ddde89.1403873776.1551251742736.1551252022792")
                .queryParam("_s","911d93")
                .cookie("xq_a_token","7f36ebcafe8263add66029cb82a8e8126a0f2ac8")
                .cookie("u","1403873776")
                .formParam("grant_type","password")
                .formParam("telephone","13362371136")
                .formParam("password","e10adc3949ba59abbe56e057f20f883e")
                .formParam("areacode","86")
                .formParam("captcha","")
                .formParam("client_id","JtXbaMn7eP")
                .formParam("client_secret","txsDfr9FphRSPov5oQou74")
                .formParam("sid","1GENYMOTION0fc7e225d6ce0404300370e1d9ddde89")
                .when()
                .post("https://api.xueqiu.com/provider/oauth/token")
                .then()
                .log().all()
                .statusCode(400)
                .extract().response();

        System.out.println(response);

    }

    @Test
    public void testPostJson(){
        HashMap<String,Object> map = new HashMap<String, Object>();
        map.put("a",1);
        map.put("b","testerhome");
        map.put("array",new String[] {"111","2222"});
        given()
                .spec(requestSpecification)
                .contentType(ContentType.JSON)
                .body(map)
         .when().post("http://www.baidu.com")
         .then().statusCode(302);
    }



}
