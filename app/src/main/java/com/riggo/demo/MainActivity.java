package com.riggo.demo;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity {
  private WebView browser;
  private final String origin = "appassets.androidplatform.net";
  private WebResourceResponse empty() {
    return new WebResourceResponse("text/plain","UTF-8",new ByteArrayInputStream(new byte[0]));
  }
  @Override public void onCreate(Bundle state) {
    super.onCreate(state);
    getWindow().setStatusBarColor(Color.rgb(241,225,203));
    getWindow().setNavigationBarColor(Color.rgb(242,230,208));
    FrameLayout root = new FrameLayout(this);
    browser = new WebView(this);
    WebSettings s = browser.getSettings();
    s.setJavaScriptEnabled(true);
    s.setDomStorageEnabled(true);
    s.setAllowFileAccess(false);
    s.setAllowContentAccess(false);
    s.setBlockNetworkLoads(true);
    s.setJavaScriptCanOpenWindowsAutomatically(false);
    s.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
    if (Build.VERSION.SDK_INT >= 26) s.setSafeBrowsingEnabled(true);
    browser.setWebChromeClient(new WebChromeClient());
    browser.setWebViewClient(new WebViewClient() {
      @Override public boolean shouldOverrideUrlLoading(WebView v, WebResourceRequest req) {
        Uri u = req.getUrl();
        return !("https".equals(u.getScheme()) && origin.equals(u.getHost()) && "/assets/index.html".equals(u.getPath()));
      }
      @Override public WebResourceResponse shouldInterceptRequest(WebView v, WebResourceRequest req) {
        Uri u = req.getUrl();
        if (!"https".equals(u.getScheme()) || !origin.equals(u.getHost()) || !"/assets/index.html".equals(u.getPath())) return empty();
        try {
          InputStream in = getAssets().open("index.html");
          return new WebResourceResponse("text/html","UTF-8",in);
        } catch (IOException e) { return empty(); }
      }
    });
    root.addView(browser, new FrameLayout.LayoutParams(-1,-1));
    setContentView(root);
    if (Build.VERSION.SDK_INT >= 35) {
      root.setOnApplyWindowInsetsListener((v,insets)->{
        android.graphics.Insets i = insets.getInsets(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
        v.setPadding(i.left,i.top,i.right,i.bottom);
        return insets;
      });
    }
    browser.loadUrl("https://"+origin+"/assets/index.html");
  }
  @Override public void onBackPressed() {
    if (browser != null && browser.canGoBack()) browser.goBack(); else super.onBackPressed();
  }
  @Override protected void onDestroy() {
    if (browser != null) { browser.destroy(); browser=null; }
    super.onDestroy();
  }
}
