package summerproject.corona.temperatureconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    private TextView welcomeMessage;
    private Button convert;
    private EditText tempInF;
    private  EditText tempInC;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Toast
        Toast.makeText(this, "You never get a second chance\n\t\t\tto make a first impression", Toast.LENGTH_SHORT).show();

        welcomeMessage = findViewById(R.id.welcomeMessage);
        convert = findViewById(R.id.convert);
        tempInC = findViewById(R.id.tempInC);
        tempInF = findViewById(R.id.tempInF);

        Calendar calendar = GregorianCalendar.getInstance();
        int timeHours = calendar.get(Calendar.HOUR_OF_DAY);
        if(timeHours<12){
            welcomeMessage.setText("Good Morning \uD83C\uDF04\uD83D\uDECC☕");
        }else if(timeHours<18){
            welcomeMessage.setText("Good Afternoon \u200B\uD83C\uDF1E\u200B\uD83D\uDC53\u200B\uD83D\uDCBC");
        }else{
            welcomeMessage.setText("Good Evening \uD83C\uDF07\u200B\uD83C\uDF77\u200B\uD83C\uDF03\u200B");
        }

        convert.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                // Toggle KeyBoard
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                // Toast
//                Toast.makeText(MainActivity.this, "Done!", Toast.LENGTH_SHORT).show();

                String tempInputF = tempInF.getText().toString();
                String tempInputC = tempInC.getText().toString();

                if(tempInputC.isEmpty() && !tempInputF.isEmpty()){
                    try {
                        double tempF = Double.parseDouble(tempInputF);
                        double tempC = (tempF-32)*5/9;
                        tempInC.setText(Double.toString(Math.round(tempC * 100.0) / 100.0));

                        Toast.makeText(MainActivity.this, "Done!", Toast.LENGTH_SHORT).show();
                    }
                    catch(Exception e) {
                        tempInF.setText("");
                        Toast.makeText(MainActivity.this, "Please Enter Correct Input", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(tempInputF.isEmpty() && !tempInputC.isEmpty())
                {
                    try {
                        double tempC = Double.parseDouble(tempInputC);
                        double tempF = (tempC*9/5) + 32;
                        tempInF.setText(Double.toString(tempF));
                        Toast.makeText(MainActivity.this, "Done!", Toast.LENGTH_SHORT).show();
                    }
                    catch(Exception e) {
                        tempInF.setText("");
                        Toast.makeText(MainActivity.this, "Please Enter Correct Input", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    tempInF.setText("");
                    tempInC.setText("");
                    Toast.makeText(MainActivity.this, "Please Enter One Input", Toast.LENGTH_SHORT).show();
                }




            }
        });



    }
}