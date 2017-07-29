package xyz.yapapa.rebuses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        WebView webViewHelp= (WebView) findViewById(R.id.webViewHelp);
        WebSettings webSetting = webViewHelp.getSettings();
        webSetting.setBuiltInZoomControls(true);
        webSetting.setJavaScriptEnabled(true);

        webViewHelp.setWebViewClient(new WebViewClient());
        webViewHelp.loadUrl("file:///android_asset/help.html");
    }
}
