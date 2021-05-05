package summerproject.corona.andriodstudioquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class QuizScore extends AppCompatActivity {

    private TextView Score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_score);

        Intent intent = getIntent();
        String scoreObtained = intent.getStringExtra(QuizPage.EXTRA_QuizScore);

        Score = findViewById(R.id.score);
        Score.setText(scoreObtained);
    }
}