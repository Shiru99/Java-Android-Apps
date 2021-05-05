package summerproject.corona.andriodstudioquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button Start;
    private Button NotAvailableJava;
    private Button NotAvailablePython;
    private Button NotAvailableDart;
    public static  String EXTRA_QuizPage="summerproject.corona.codingquiz.key.quizpage"; // Unique

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("AppLogs","App Started");

        final Intent intent_QuizPage = new Intent(this,QuizPage.class);

        NotAvailableJava = findViewById(R.id.java_button);
        NotAvailableJava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AppLogs","Request for Java quiz");
                Toast.makeText(MainActivity.this, "All The Best", Toast.LENGTH_SHORT).show();
                intent_QuizPage.putExtra(EXTRA_QuizPage,"Java");
                startActivity(intent_QuizPage);
            }
        });

        Start = findViewById(R.id.android_button);
        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AppLogs","Request for Android quiz");
                Toast.makeText(MainActivity.this, "Quiz not available", Toast.LENGTH_SHORT).show();
            }
        });



        NotAvailablePython = findViewById(R.id.python_button);
        NotAvailablePython.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AppLogs","Request for Python quiz");
                Toast.makeText(MainActivity.this, "Quiz not available", Toast.LENGTH_SHORT).show();
            }
        });

        NotAvailableDart = findViewById(R.id.dart_button);
        NotAvailableDart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AppLogs","Request for Dart quiz");
                Toast.makeText(MainActivity.this, "Quiz not available", Toast.LENGTH_SHORT).show();
            }
        });



    }


}