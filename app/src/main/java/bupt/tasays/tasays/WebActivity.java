package bupt.tasays.tasays;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);
        Intent intent=getIntent();
        String url=intent.getStringExtra("destUrl");
        webView=findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed(){
        webView.onPause();
        finish();
    }

    @Override
    protected void onDestroy() {
        webView.loadUrl("file:///android_asset/nonexistent.html");
        super.onDestroy();
    }
}
