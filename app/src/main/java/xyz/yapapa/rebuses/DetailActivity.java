package xyz.yapapa.rebuses;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Misha on 25.07.2017.
 */

public class DetailActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        String answer= new String();
        Bundle bundle = getIntent().getExtras();
        int image = bundle.getInt("image");
        answer = bundle.getString("answer");

        ImageView rebus = (ImageView) findViewById(R.id.imageView);


        rebus.setImageResource(image);

       final TextView answerTextView = (TextView) findViewById(R.id.textView);
        answerTextView.setVisibility(View.INVISIBLE);
       final Button btnAnswer = (Button) findViewById(R.id.buttonAnswer);


        View.OnClickListener oclbtnAnswer = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                answerTextView.setVisibility(View.VISIBLE);
                btnAnswer.setVisibility(View.INVISIBLE);
            }
        };

        btnAnswer.setOnClickListener(oclbtnAnswer);

        answerTextView.setText(answer);
    }
}
