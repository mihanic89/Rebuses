package xyz.yapapa.rebuses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        TextView textViewHelp= (TextView) findViewById(R.id.textViewHelp);
        textViewHelp.setText("111");
    }
}
