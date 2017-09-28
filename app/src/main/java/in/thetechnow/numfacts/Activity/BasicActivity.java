package in.thetechnow.numfacts.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import in.thetechnow.numfacts.R;

public class BasicActivity extends AppCompatActivity {
    Button trivia, math, date, year;
    Spinner spinner;
    String selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        spinner = (Spinner) findViewById(R.id.sp_functionality);
        trivia = (Button) findViewById(R.id.basic_trivia);
        math = (Button) findViewById(R.id.basic_math);
        date = (Button) findViewById(R.id.basic_date);
        year = (Button) findViewById(R.id.basic_year);


        trivia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selection = spinner.getSelectedItem().toString();
                Intent myintent;
                if (selection.equals("Advance")) {
                     myintent = new Intent(BasicActivity.this, AdvanceWork.class).putExtra("category", "trivia");

                } else {
                     myintent = new Intent(BasicActivity.this, BasicWork.class).putExtra("category", "trivia");

                }
                startActivity(myintent);
            }
        });

        math.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selection = spinner.getSelectedItem().toString();
                Intent myintent;
                if (selection.equals("Advance")) {
                    myintent = new Intent(BasicActivity.this, AdvanceWork.class).putExtra("category", "math");

                } else {
                    myintent = new Intent(BasicActivity.this, BasicWork.class).putExtra("category", "math");

                }
                startActivity(myintent);
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selection = spinner.getSelectedItem().toString();
                Intent myintent;
                if (selection.equals("Advance")) {
                    myintent = new Intent(BasicActivity.this, AdvanceWork.class).putExtra("category", "date");

                } else {
                    myintent = new Intent(BasicActivity.this, BasicWork.class).putExtra("category", "date");

                }
                startActivity(myintent);
            }
        });

        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selection = spinner.getSelectedItem().toString();
                Intent myintent;
                if (selection.equals("Advance")) {
                    myintent = new Intent(BasicActivity.this, AdvanceWork.class).putExtra("category", "year");

                } else {
                    myintent = new Intent(BasicActivity.this, BasicWork.class).putExtra("category", "year");

                }
                startActivity(myintent);
            }
        });
    }
}
