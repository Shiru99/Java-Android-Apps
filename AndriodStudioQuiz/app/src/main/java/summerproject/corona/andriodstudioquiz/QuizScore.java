package summerproject.corona.andriodstudioquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class QuizScore extends AppCompatActivity {

    private String[] Questions;
    private String[] Answers;
    private String[] UserAnswers;
    private ImageButton shareMessage;
    private ImageButton shareMail;

    @SuppressLint("QueryPermissionsNeeded")
    public void composeEmail(String[] addresses, String subject, String message) {
        Log.d("AppLogs", "Sharing Via Mail");
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(QuizScore.this, "No Suitable Application Available On This Device", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void composeMmsMessage(String message) {
        Log.d("AppLogs", "Sharing Via MMS message");
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra("sms_body", message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(QuizScore.this, "No Suitable Application Available On This Device", Toast.LENGTH_SHORT).show();
        }
    }

//    @SuppressLint("QueryPermissionsNeeded")
//    public void composeMmsMessage(String message) {
//        Log.d("AppLogs", "Sharing Via MMS message");
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setData(Uri.parse("smsto:")); // This ensures only SMS apps respond
//        intent.putExtra("sms_body", message);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        } else {
//            Toast.makeText(QuizScore.this, "No Suitable Application Available On This Device", Toast.LENGTH_SHORT).show();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_score);
        Log.d("AppLogs", "On ScoreCard Page");

        Intent intent = getIntent();
        String scoreObtained = intent.getStringExtra(QuizPage.EXTRA_QuizScore);
        Questions = intent.getStringExtra(QuizPage.EXTRA_QuizQuestions).replace("[", "").replace("]", "").trim().split(", ");
        Answers = intent.getStringExtra(QuizPage.EXTRA_QuizSolutions).replace("[", "").replace("]", "").trim().split(", ");
        UserAnswers = intent.getStringExtra(QuizPage.EXTRA_QuizUserAnswers).replace("[", "").replace("]", "").trim().split(", ");

        TextView score = findViewById(R.id.score);
        score.setText(scoreObtained);

        shareMail = findViewById(R.id.shareMail);
        shareMessage = findViewById(R.id.shareMessage);

        final StringBuilder Message = new StringBuilder();
        Message.append("Your Score : ").append(scoreObtained).append("\n\n\n");

        for (int i = 0; i < Questions.length; i++) {
            Message.append(Questions[i]).append("\n");
            Message.append("Correct Answer : ").append(Answers[i]).append(" \n");

            if (UserAnswers[i].equals("0")) {
                Message.append("Wrong Answer(0)\n\n");
            } else {
                Message.append("Correct Answer(+1)\n\n");
            }
        }

        // Log.d("AppLogs", Message.toString());

        shareMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AppLogs", "Requesting to share via MMS Applications");
                composeMmsMessage(Message.toString());
            }
        });

        shareMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AppLogs", "Requesting to share via Mail Applications");
                String[] Addresses = { "john.doe@gmail.com" };
                composeEmail(Addresses, "Quiz Analysis", Message.toString());
            }
        });
    }
}