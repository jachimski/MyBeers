package org.esiea.jachimski.mybeers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;



public class WebActivity extends AppCompatActivity {

    private static WebView browser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        browser = (WebView) findViewById(R.id.webView);
        String url = "https://www.youtube.com";
        browser.getSettings().getJavaScriptEnabled();
        browser.loadUrl(url);

    }
}