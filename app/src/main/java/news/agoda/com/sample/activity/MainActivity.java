package news.agoda.com.sample.activity;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import news.agoda.com.sample.entity.NewsEntity;
import news.agoda.com.sample.adapter.NewsListAdapter;
import news.agoda.com.sample.R;
import news.agoda.com.sample.interfaces.NewsAPI;

public class MainActivity extends ListActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private List<NewsEntity> newsItemList;
    private HttpURLConnection urlConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fresco.initialize(this);

        newsItemList = new ArrayList<>();
        setUpListener();
        new LoadData().execute();
        NewsAPI API = NewsAPI.Factory.getInstance();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void setUpListener(){
        ListView listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewsEntity newsEntity = newsItemList.get(position);
                String title = newsEntity.getTitle();
                Intent intent = new Intent(MainActivity.this, DetailViewActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("storyURL", newsEntity.getArticleUrl());
                intent.putExtra("summary", newsEntity.getSummary());
                intent.putExtra("imageURL", newsEntity.getMediaEntity().size() == 0 ? "" : newsEntity.getBiggestMediaEntity().getUrl());

                startActivity(intent);
            }
        });
    }

    class LoadData extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL("http://www.mocky.io/v2/573c89f31100004a1daa8adb");
                urlConnection = (HttpURLConnection) url.openConnection();
                String readStream = readStream(urlConnection.getInputStream());
                JSONObject jsonObject = new JSONObject(readStream);
                JSONArray resultArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < resultArray.length(); i++) {
                    JSONObject newsObject = resultArray.getJSONObject(i);
                    NewsEntity newsEntity = new NewsEntity(newsObject);
                    newsItemList.add(newsEntity);
                }

            } catch (Exception e) {
                Log.d(TAG,e.getMessage());
                e.printStackTrace();
            }finally {
                urlConnection.disconnect();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String json){
            NewsListAdapter adapter = new NewsListAdapter(MainActivity.this, R.layout.list_item_news, newsItemList);
            setListAdapter(adapter);

        }
        private String readStream(InputStream in) {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(in));) {

                String nextLine = "";
                while ((nextLine = reader.readLine()) != null) {
                    sb.append(nextLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }
    }

}
