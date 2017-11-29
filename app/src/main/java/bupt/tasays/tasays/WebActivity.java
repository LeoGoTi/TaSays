package bupt.tasays.tasays;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar=getSupportActionBar();//隐藏标题栏和状态栏
        if(actionBar!=null)
        {
            actionBar.hide();
        }
        setContentView(R.layout.web_layout);
        Intent intent=getIntent();
        String url=intent.getStringExtra("destUrl");
        WebView webView=findViewById(R.id.web);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
}
