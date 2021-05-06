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

import java.util.Arrays;
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
    private byte[] UserAnswers;
    private int score;
    private int index = 0;
    private String scoreText = "0/0";
    public static String EXTRA_QuizScore = "summerproject.corona.codingquiz.key.quizscore"; // Unique
    public static String EXTRA_QuizQuestions = "summerproject.corona.codingquiz.key.quizquestions"; // Unique
    public static String EXTRA_QuizSolutions = "summerproject.corona.codingquiz.key.quizsolutions"; // Unique
    public static String EXTRA_QuizUserAnswers = "summerproject.corona.codingquiz.key.quizuseranswers"; // Unique

    // public static void visitScorecard(){
    // return;
    // }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);

        Log.d("AppLogs", "Entered in Quiz Page");

        Intent intent = getIntent();
        String subject = intent.getStringExtra(MainActivity.EXTRA_QuizPage);

        title = findViewById(R.id.title);
        title.setText(subject);
        score = 0;
        // Questions for JAVA :
        if (subject.toLowerCase().equals("java")) {
            Questions = new String[] {
                    "1. In an instance method or a constructor \"this\" is a reference to the current object.",
                    "2. Garbage Collection is manual process.",
                    "3. The JRE deletes objects when it determines that they are no longer being used. This process is called Garbage Collection",
                    "4. Assignment operator is evaluated Left to Right",
                    "5. All binary operators except for the assignment operators are evaluated from Left to Right",
                    "6. Java programming is not statically-typed means all variables should not first be declared before they can be used." };

            Answers = new boolean[] { true, false, true, false, true, false };
            UserAnswers = new byte[Questions.length];

        } else if (subject.toLowerCase().equals("python")) {
            Questions = new String[] { "1. The condition x <= y <= z is allowed in Python",
                    "2. Python does not have a Switch or Case statement", "3. Python strings are indeed mutable",
                    "4. In call-by-value the argument whether an expression or a value gets bound to the respective variable in the function.",
                    "5. Built-in functions in Python - id() accepts one parameter and returns a unique identifier associated with the input object.",
                    "6. pass and continue are same in Python",
                    "7. 'chr(int)' returns the string denoting a character whose Unicode code point is an integer And 'ord(char)' takes a string of size one and returns an integer denoting the Unicode code." };

            Answers = new boolean[] { true, true, false, true, true, false, true };
            UserAnswers = new byte[Questions.length];
        }

        final Intent intent_QuizScore = new Intent(this, QuizScore.class);

        // ScoreCard Button
        scorecard = (Button) findViewById(R.id.scorecard);
        scorecard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreText = score + "/" + Questions.length;
                Log.d("AppLogs", "Request for Score");
                intent_QuizScore.putExtra(EXTRA_QuizScore, scoreText);
                intent_QuizScore.putExtra(EXTRA_QuizQuestions, Arrays.toString(Questions));
                intent_QuizScore.putExtra(EXTRA_QuizSolutions, Arrays.toString(Answers));
                intent_QuizScore.putExtra(EXTRA_QuizUserAnswers, Arrays.toString(UserAnswers));
                startActivity(intent_QuizScore);
            }
        });

        question = findViewById(R.id.question);
        question.setMovementMethod(new ScrollingMovementMethod()); // In case Question is long => need to scroll
        question.setText(Questions[index]);
        buttonTrue = findViewById(R.id.buttonTrue);
        buttonFalse = findViewById(R.id.buttonFalse);

        buttonTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuizPage.this, "Answer Recorded : True", Toast.LENGTH_SHORT).show();
                if (Answers[index]) {
                    score++;
                    UserAnswers[index] = 1;
                } else {
                    UserAnswers[index] = 0;
                }

                if (++index == Questions.length) {
                    Log.d("AppLogs", "Quiz Over! redirect to scorecard");
                    scorecard.performClick();
                } else {
                    question.setText(Questions[index]);
                }

            }
        });

        buttonFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuizPage.this, "Answer Recorded : False", Toast.LENGTH_SHORT).show();

                if (!Answers[index]) {
                    score++;
                    UserAnswers[index] = 1;
                } else {
                    UserAnswers[index] = 0;
                }
                if (++index == Questions.length) {
                    Log.d("AppLogs", "Quiz Over! redirect to scorecard");
                    scorecard.performClick();
                } else {
                    question.setText(Questions[index]);
                }
            }
        });

        // CountDown
        countdown = (TextView) findViewById(R.id.countdown);
        new CountDownTimer(30000 * (Questions.length), 1000) // milli seconds
        {
            @SuppressLint({ "DefaultLocale", "SetTextI18n" })
            public void onTick(long millisUntilFinished) {
                countdown.setText(String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            @SuppressLint("SetTextI18n")
            public void onFinish() {
                countdown.setText("TimeOut");
                scorecard.performClick();
            }
        }.start();
    }
}