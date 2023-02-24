package com.cnki.paotui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cnki.paotui.utils.ThreadPoolExecutorUtil;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class SearchActivity extends AppCompatActivity {

String url="https://www.bbiquge.net/modules/article/search.php?searchtype=articlename&searchkey=%B7%E7%C6%F0%C1%FA%B3%C7";
String url1="https://www.bbiquge.net/modules/article/authorarticle.php?author=%CE%D2%B3%D4%CE%F7%BA%EC%CA%C1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().hide();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        ThreadPoolExecutorUtil.doTask(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection.Response response = Jsoup.connect(url1)
                            .followRedirects(true)
                            .header("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Mobile Safari/537.36")
                            . execute();
                    System.out.println(response.url());
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                Document doc = null;
//                try {
//                    doc = Jsoup.connect("https://www.bbiquge.net/modules/article/search.php?searchtype=articlename&searchkey=%B7%E7%C6%F0%C1%FA%B3%C7").get();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        });
//        new OkHttpClient().newCall(request).enqueue(new Callback() {
//            /**
//             * Called when the request could not be executed due to cancellation, a
//             * connectivity problem or timeout. Because networks can fail during an
//             * exchange, it is possible that the remote server accepted the request
//             * before the failure.
//             *
//             * @param request
//             * @param e
//             */
//            @Override
//            public void onFailure(Request request, IOException e) {
//
//            }
//
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                System.out.println(response.header("location"));
//            }
//
//
//        });
    }
}