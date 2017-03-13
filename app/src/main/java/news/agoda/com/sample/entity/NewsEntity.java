package news.agoda.com.sample.entity;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This represents a news item
 */
public class NewsEntity {
    private static final String TAG = NewsEntity.class.getSimpleName();
    private String title;
    private String summary;
    private String articleUrl;
    private String byline;
    private String publishedDate;
    private List<MediaEntity> mediaEntityList;

    public NewsEntity(JSONObject jsonObject) {
        try {
            mediaEntityList = new ArrayList<>();
            title = jsonObject.optString("title", "");
            summary = jsonObject.optString("abstract", "");
            articleUrl = jsonObject.optString("url", "");
            byline = jsonObject.optString("byline", "");
            publishedDate = jsonObject.optString("published_date", "");
            JSONArray mediaArray = jsonObject.getString("multimedia").equals("") ? new JSONArray() : jsonObject.getJSONArray("multimedia");
            for (int i = 0; i < mediaArray.length(); i++) {
                JSONObject mediaObject = mediaArray.getJSONObject(i);
                MediaEntity mediaEntity = new MediaEntity(mediaObject);
                mediaEntityList.add(mediaEntity);
            }

        } catch (JSONException exception) {
            Log.e(TAG, exception.getMessage());
        }
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public String getByline() {
        return byline;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public List<MediaEntity> getMediaEntity() {
        return mediaEntityList;
    }

    public MediaEntity getBiggestMediaEntity(){ return mediaEntityList.size() == 0 ? null : mediaEntityList.get(mediaEntityList.size() - 1); }
}
