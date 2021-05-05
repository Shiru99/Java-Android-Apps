package summerproject.corona.andriodstudioquiz;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class QuizPage extends AppCompatActivity {

    private TextView title;
    private TextView countdown;
    private TextView question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);

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



    }
}