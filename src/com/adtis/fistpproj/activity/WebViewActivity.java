package com.adtis.fistpproj.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.adtis.fistpproj.R;

public class WebViewActivity extends Activity {

    private WebView webView;
    private WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initView() {
        webView = (WebView) findViewById(R.id.webView);
        /** 互联网页面直接用 */
        webView.loadUrl("https://www.google.com");
        /** 本地文件用：本地文件存放在：assets文件中 */
        webView.loadUrl("file:///android_asset/XX.html");
        /** 还可以直接载入html的字符串 */
        String htmlString = "<h1>Title</h1><p>This is HTML text<br /><i>Formatted in italics</i><br />Anothor Line</p>";
        // 载入这个html页面
        webView.loadData(htmlString, "text/html", "utf-8");
        /**
         * 在WebView中使用JavaScript
         * 可以通过getSettings()获得WebSettings，然后用setJavaScriptEnabled()使能JavaScript
         */
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }
}
