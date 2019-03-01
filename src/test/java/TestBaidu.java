import org.junit.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class TestBaidu {
    @Test
    public void testGetHtml(){
        given()
                .log().all().get("http://www.baidu.com")
                .then().log().all().statusCode(200);

    }

    @Test
    public void testMp3(){
        given()
                .queryParam("mp3")
                .when().get("http://www.baidu.com/s")
                .then().log().all().statusCode(200)
                .body("html.head.title", equalTo("百度一下，你就知道"));
    }
}
