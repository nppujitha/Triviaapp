package com.example.akipuja.hw3;
/*Group 34
  Names : Naga Poorna Pujitha Perakalapudi, Akshay Karai.
*/

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StatsActivity extends AppCompatActivity {

    ProgressBar pcPbar;
    Button quitButtonN,tryAgainButton;
    TextView tryTV,pcTV;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("state", "onCreate: "+"Stats Activity started");
        super.onCreate(savedInstanceState);
        setContentView(com.example.akipuja.hw3.R.layout.activity_stats);
        setTitle(com.example.akipuja.hw3.R.string.TriviaQuiz);

        pcPbar =(ProgressBar) findViewById(com.example.akipuja.hw3.R.id.pcProgressBar);
        quitButtonN =(Button) findViewById(com.example.akipuja.hw3.R.id.quitButton2);
        tryAgainButton=(Button) findViewById(com.example.akipuja.hw3.R.id.tryAgainButton);
        tryTV=(TextView) findViewById(com.example.akipuja.hw3.R.id.tryTV);
        pcTV=(TextView) findViewById(com.example.akipuja.hw3.R.id.pcTV);
        pcPbar.setProgress(45);

        if(getIntent()!=null && getIntent().getExtras()!=null){
            if(getIntent().getExtras().containsKey("correctCount")){
                Integer count1=(Integer) getIntent().getExtras().getSerializable("correctCount");
                count=(count1.intValue()*100)/16;
                pcPbar.setProgress(count);
                pcTV.setText(String.format("%d%s",count,"%"));
                if(count==100){
                        tryTV.setText("Good Job!");
                }
            }
        }

        quitButtonN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StatsActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        tryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("state", "onDestroy: "+"Stats Activity ended");
    }
}
