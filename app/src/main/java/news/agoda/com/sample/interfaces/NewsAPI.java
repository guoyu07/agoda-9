package news.agoda.com.sample.interfaces;

import java.util.List;

import news.agoda.com.sample.entity.News;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by kak on 2/17/2017.
 */

public interface  NewsAPI {
    final String url= "http://www.mocky.io/v2/573c89f31100004a1daa8adb/";
    @GET("v2/573c89f31100004a1daa8adb")
    Call<News> getNews();

    class Factory{
        private static NewsAPI service;
        public static NewsAPI getInstance()
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(NewsAPI.class);
            return service;
        }

    }
}
