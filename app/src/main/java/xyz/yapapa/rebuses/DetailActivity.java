package xyz.yapapa.rebuses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by Misha on 25.07.2017.
 */

public class DetailActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    private AdView mAdView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        String answer= new String();
        Bundle bundle = getIntent().getExtras();
        int image = bundle.getInt("image");
        answer = bundle.getString("answer");

        ImageView rebus = (ImageView) findViewById(R.id.imageView);


        rebus.setImageResource(image);

       final TextView answerTextView = (TextView) findViewById(R.id.textView);
        answerTextView.setVisibility(View.INVISIBLE);
       final Button btnAnswer = (Button) findViewById(R.id.buttonAnswer);
        final ImageView btHelp = (ImageView) findViewById(R.id.buttonHelp);


        View.OnClickListener oclbtnAnswer = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                answerTextView.setVisibility(View.VISIBLE);
                btnAnswer.setVisibility(View.INVISIBLE);
            }
        };

        View.OnClickListener oclbtnHelp = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(DetailActivity.this, HelpActivity.class);

                startActivity(intent);
            }
        };

        btnAnswer.setOnClickListener(oclbtnAnswer);
        btHelp.setOnClickListener(oclbtnHelp);
        answerTextView.setText(answer);

        mAdView = (AdView) findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("09D7B5315C60A80D280B8CDF618FD3DE")
                .build();
        mAdView.loadAd(adRequest);
    }


    @Override
    public void onResume() {
        super.onResume();
        // Resume the AdView.
        mAdView.resume();
    }

    @Override
    public void onPause() {
        // Pause the AdView.
        mAdView.pause();

        super.onPause();
    }

    @Override
    public void onDestroy() {
        // Destroy the AdView.

        mAdView.destroy();
        super.onDestroy();
    }

}