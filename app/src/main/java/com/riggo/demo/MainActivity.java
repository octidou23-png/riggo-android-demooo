package com.riggo.demo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Build;
import android.view.WindowInsets;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.webkit.WebResourceRequest;
import android.net.Uri;
import android.widget.FrameLayout;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class MainActivity extends Activity {
    private WebView web;
    private static final String ORIGIN = "https://appassets.androidplatform.net/assets/";

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(Color.rgb(252, 242, 228));
        getWindow().setNavigationBarColor(Color.rgb(252, 242, 228));

        FrameLayout root = new FrameLayout(this);
        web = new WebView(this);
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAllowFileAccess(false);
        settings.setAllowContentAccess(false);
        settings.setBlockNetworkLoads(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        settings.setSupportMultipleWindows(false);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
        if (Build.VERSION.SDK_INT >= 26) settings.setSafeBrowsingEnabled(true);
        web.setWebChromeClient(new WebChromeClient());
        web.setWebViewClient(new WebViewClient(){
            @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri uri = request.getUrl();
                // Demo offline: URLs de otros dominios no abren dentro de la app.
                return uri == null || !"https".equals(uri.getScheme()) ||
                    !"appassets.androidplatform.net".equals(uri.getHost());
            }
        });
        root.addView(web, new FrameLayout.LayoutParams(-1, -1));
        setContentView(root);
        if (Build.VERSION.SDK_INT >= 35) {
            root.setOnApplyWindowInsetsListener((view, insets) -> {
                android.graphics.Insets bars = insets.getInsets(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
                view.setPadding(bars.left, bars.top, bars.right, bars.bottom);
                return insets;
            });
        }
        try (InputStream input = getAssets().open("index.html");
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
            byte[] bytes = new byte[16384];
            int count;
            while ((count = input.read(bytes)) != -1) buffer.write(bytes,0,count);
            String html = new String(buffer.toByteArray(), StandardCharsets.UTF_8);
            html = html.replace("<script src=\"./firebase-public-config.js\"></script>",
                "<script>window.RIGGO_FIREBASE_CONFIG=null;</script>");
            // Renderizar la aplicación sin solicitar una URL remota.
            web.loadDataWithBaseURL(ORIGIN, html, "text/html", "UTF-8", null);
        } catch (Exception e) {
            web.loadDataWithBaseURL(ORIGIN,
               "<html><body><h2>No se pudo abrir RIGGO</h2><p>Reinstalar la demo.</p></body></html>",
               "text/html", "UTF-8", null);
        }
    }
    @Override public void onBackPressed() {
        if (web != null && web.canGoBack()) web.goBack();
        else super.onBackPressed();
    }
    @Override protected void onDestroy() {
        if (web != null) { web.destroy(); web=null; }
        super.onDestroy();
    }
}
