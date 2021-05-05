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
    public Button ScoreCard;
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

            public void onFinish() {
                countdown.setText("TimeOut");
            }
        }.start();

        question = findViewById(R.id.question);
        question.setMovementMethod(new ScrollingMovementMethod());



        // ScoreCard Button

        final Intent intent_QuizScore = new Intent(this,QuizScore.class);

        ScoreCard = findViewById(R.id.scorecard);
        ScoreCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AppLogs","Request for Score");
                //  Toast.makeText(QuizPage.this, "Score", Toast.LENGTH_SHORT).show();
                intent_QuizScore.putExtra(EXTRA_QuizScore,"0");
                startActivity(intent_QuizScore);
            }
        });




    }
}