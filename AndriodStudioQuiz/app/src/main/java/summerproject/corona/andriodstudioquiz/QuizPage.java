package summerproject.corona.andriodstudioquiz;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class QuizPage extends AppCompatActivity {

    private TextView title;
    private TextView countdown;
    private TextView question;

    public Button buttonFalse;
    public Button buttonTrue;
    public Button scorecard;
    private String[] Questions;
    private boolean[] Answers;
    private int score;
    private String scoreText="0/0";
    public static  String EXTRA_QuizScore="summerproject.corona.codingquiz.key.quizscore"; // Unique

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);

        Log.d("AppLogs","Entered in Quiz Page");

        Intent intent = getIntent();
        String subject = intent.getStringExtra(MainActivity.EXTRA_QuizPage);

        title = findViewById(R.id.title);
        title.setText(subject);

        if(title.toString().toLowerCase().equals("java")){
            Questions = new String[]{"Q.1", "Q.2", "Q.3", "Q.4", "Q.5", "Q.6"};
            Answers = new boolean[]{true,false,true,false,true,true};
            score = 0;
        }


        buttonFalse = findViewById(R.id.buttonFalse);
        buttonTrue = findViewById(R.id.buttonTrue);

        buttonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuizPage.this, "Answer Recorded : True", Toast.LENGTH_SHORT).show();
            }
        });

        buttonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuizPage.this, "Answer Recorded : False", Toast.LENGTH_SHORT).show();
            }
        });



        countdown = (TextView) findViewById( R.id.countdown );
        new CountDownTimer(600000, 1000)  // milli seconds
        {
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            public void onTick(long millisUntilFinished) {
                countdown.setText(
                        String.format("%d min, %d sec",
                                TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                        )
                );
            }

            @SuppressLint("SetTextI18n")
            public void onFinish() {
                countdown.setText("TimeOut");
            }
        }.start();

        question = findViewById(R.id.question);
        question.setMovementMethod(new ScrollingMovementMethod());



        // ScoreCard Button

        final Intent intent_QuizScore = new Intent(this,QuizScore.class);

        scorecard = findViewById(R.id.scorecard);
        scorecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AppLogs","Request for Score");
                //  Toast.makeText(QuizPage.this, "Score", Toast.LENGTH_SHORT).show();
                intent_QuizScore.putExtra(EXTRA_QuizScore,scoreText);
                startActivity(intent_QuizScore);
            }
        });




    }
}