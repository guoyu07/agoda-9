package news.agoda.com.sample;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import news.agoda.com.sample.entity.MediaEntity;


/**
 * Created by admin on 11/14/2016 AD.
 */
public class MediaEntityTest {

    @Test public void normalCase() throws Exception {

        String painJsonString = "{\n" +
                "          \"url\": \"http://static01.nyt.com/images/2015/08/18/business/18workplace-web/18workplace-web-articleInline.jpg\",\n" +
                "          \"format\": \"Normal\",\n" +
                "          \"height\": 127,\n" +
                "          \"width\": 190,\n" +
                "          \"type\": \"image\",\n" +
                "          \"subtype\": \"photo\",\n" +
                "          \"caption\": \"Myrna Arias, a saleswoman for Intermex, a money-transfer company, has claimed in a lawsuit that she was required to download an app on her cellphone that tracked her whereabouts 24 hours a day.\",\n" +
                "          \"copyright\": \"Monica Almeida/The New York Times\"\n" +
                "        }";

        JSONObject jsonObject = new JSONObject(painJsonString);

        MediaEntity media = new MediaEntity(jsonObject);

        Assert.assertEquals("normal", media.getFormat());




    }
}
