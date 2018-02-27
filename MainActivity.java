package com.example.akipuja.hw3;

/*Group 34
  Names : Naga Poorna Pujitha Perakalapudi, Akshay Karai.
*/
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetTriviaTask.ITrivia {

    ArrayList<Question> qList=new ArrayList<>();
    ImageView trivImgV;
    TextView loaderTV,readyTV;
    ProgressBar progressBar;
    Button startTrivButtoN,exitButton;
    TriviaActivity t=new TriviaActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("state", "onCreate: "+"Main Activity started");
        super.onCreate(savedInstanceState);
        setContentView(com.example.akipuja.hw3.R.layout.activity_main);
        setTitle(com.example.akipuja.hw3.R.string.TriviaQuiz);

        trivImgV =(ImageView) findViewById(com.example.akipuja.hw3.R.id.trivImgView);
        loaderTV =(TextView) findViewById(com.example.akipuja.hw3.R.id.loadingTV);
        readyTV=(TextView) findViewById(com.example.akipuja.hw3.R.id.triviaReadyTV);
        progressBar=(ProgressBar) findViewById(com.example.akipuja.hw3.R.id.progressBar);
        startTrivButtoN =(Button) findViewById(com.example.akipuja.hw3.R.id.startTriviaButton);
        exitButton=(Button) findViewById(com.example.akipuja.hw3.R.id.exitButton);

        trivImgV.setVisibility(View.INVISIBLE);
        readyTV.setVisibility(View.INVISIBLE);
        startTrivButtoN.setEnabled(false);

        if(isConnected()) {
            new GetTriviaTask(MainActivity.this).execute("http://dev.theappsdr.com/apis/trivia_json/trivia_text.php");
        }else{
            Toast.makeText(this, "Internet Disconnected", Toast.LENGTH_SHORT).show();
        }

        startTrivButtoN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent=new Intent(MainActivity.this,TriviaActivity.class);
                 intent.putExtra("questionsList",qList);
                 startActivity(intent);
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.finishAffinity(MainActivity.this);
            }
        });

    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

    @Override
    public void handleQuestions(ArrayList<Question> qList1) {
        qList=qList1;
        progressBar.setVisibility(View.GONE);
        loaderTV.setVisibility(View.GONE);
        readyTV.setVisibility(View.VISIBLE);
        trivImgV.setVisibility(View.VISIBLE);
        startTrivButtoN.setEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("state", "onDestroy: "+"Main Activity ended");
    }
}
