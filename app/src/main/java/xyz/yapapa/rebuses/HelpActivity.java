package xyz.yapapa.rebuses;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);

        WebView webView = findViewById(R.id.webViewHelp);
        WebSettings settings = webView.getSettings();
        // The page is static bundled HTML: no scripts, no network, no file system access.
        // file:///android_asset/ stays readable regardless of setAllowFileAccess.
        settings.setJavaScriptEnabled(false);
        settings.setAllowFileAccess(false);
        settings.setAllowContentAccess(false);
        settings.setBlockNetworkLoads(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        webView.loadUrl("file:///android_asset/help.html");
    }
}
