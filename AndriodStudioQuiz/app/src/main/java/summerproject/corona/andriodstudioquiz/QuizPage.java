package summerproject.corona.andriodstudioquiz;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class QuizPage extends AppCompatActivity {

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);

        Intent intent = getIntent();
        String subject = intent.getStringExtra(MainActivity.EXTRA_QuizPage);

        title = findViewById(R.id.title);
        title.setText(subject);



    }
}