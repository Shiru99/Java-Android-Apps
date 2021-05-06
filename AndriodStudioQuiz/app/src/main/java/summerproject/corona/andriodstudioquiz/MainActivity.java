package summerproject.corona.andriodstudioquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button AvailableJava;
    private Button NotAvailableAndroid;
    private Button AvailablePython;
    private Button NotAvailableDart;
    public static  String EXTRA_QuizPage="summerproject.corona.codingquiz.key.quizpage"; // Unique

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("AppLogs","App Started");

        final Intent intent_QuizPage = new Intent(this,QuizPage.class);

        AvailableJava = findViewById(R.id.java_button);
        AvailableJava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AppLogs","Request for Java quiz page");
                Toast.makeText(MainActivity.this, "All The Best", Toast.LENGTH_SHORT).show();
                intent_QuizPage.putExtra(EXTRA_QuizPage,"Java");
                startActivity(intent_QuizPage);
            }
        });

        AvailablePython = findViewById(R.id.python_button);
        AvailablePython.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AppLogs","Request for Python quiz");
                Toast.makeText(MainActivity.this, "All The Best", Toast.LENGTH_SHORT).show();
                intent_QuizPage.putExtra(EXTRA_QuizPage,"Python");
                startActivity(intent_QuizPage);
            }
        });

        NotAvailableAndroid = findViewById(R.id.android_button);
        NotAvailableAndroid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AppLogs","Request for Android quiz");
                Toast.makeText(MainActivity.this, "Quiz not available", Toast.LENGTH_SHORT).show();
                // intent_QuizPage.putExtra(EXTRA_QuizPage,"Android");
                // startActivity(intent_QuizPage);
            }
        });


        NotAvailableDart = findViewById(R.id.dart_button);
        NotAvailableDart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AppLogs","Request for Dart quiz");
                Toast.makeText(MainActivity.this, "Quiz not available", Toast.LENGTH_SHORT).show();
                // intent_QuizPage.putExtra(EXTRA_QuizPage,"Dart");
                // startActivity(intent_QuizPage);
            }
        });



    }


}