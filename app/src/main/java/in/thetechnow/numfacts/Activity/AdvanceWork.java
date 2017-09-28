package in.thetechnow.numfacts.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.easing.linear.Linear;

import org.w3c.dom.Text;

import java.io.IOException;

import in.thetechnow.numfacts.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AdvanceWork extends AppCompatActivity {
    Intent intent;
    String category;
    EditText date, etNumAdvance;
    DatePickerDialog datePickerDialog;
    Button btnSubmit;
    private Handler mHandler;
    TextView tvAdvanceView;
    LinearLayout layout;
    ProgressBar progressBar;
    String responses;
    String url;
    int number,dateUrl,yearUrl,monthUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_work);


        intent = getIntent();
        category = intent.getStringExtra("category");
        date = (EditText) findViewById(R.id.et_date_view);
        etNumAdvance = (EditText) findViewById(R.id.et_num_advance);
        layout = (LinearLayout) findViewById(R.id.tv_advance_work_layout);
        progressBar = (ProgressBar) findViewById(R.id.pb_advance_work);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        progressBar.setVisibility(View.INVISIBLE);
        layout.setVisibility(View.INVISIBLE);
        tvAdvanceView = (TextView) findViewById(R.id.tv_advance_work);
        switch (category) {
            case "math":
                date.setVisibility(View.INVISIBLE);
                etNumAdvance.setVisibility(View.VISIBLE);
                break;
            case "trivia":
                date.setVisibility(View.INVISIBLE);
                etNumAdvance.setVisibility(View.VISIBLE);
                break;
            case "date":
                date.setVisibility(View.VISIBLE);
                etNumAdvance.setVisibility(View.INVISIBLE);
                break;
            case "year":
                date.setVisibility(View.VISIBLE);
                etNumAdvance.setVisibility(View.INVISIBLE);
                break;
        }


        // perform click event on edit text
        date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AdvanceWork.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                dateUrl = dayOfMonth;
                                yearUrl = year;
                                monthUrl = monthOfYear+1;
                                if(category.equals("year")){
                                    date.setText(String.valueOf(yearUrl));
                                }else{
                                    date.setText(monthUrl+"/"+dateUrl);
                                }

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (category) {
                    case "math":
                        progressBar.setVisibility(View.VISIBLE);
                        try {
                            number = Integer.parseInt(etNumAdvance.getText().toString());
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),"Enter Digit Only",Toast.LENGTH_LONG).show();
                        }
                        url = "http://numbersapi.com/" + number + "/math";
                        setText(url);
                        break;
                    case "trivia":
                        progressBar.setVisibility(View.VISIBLE);
                        try {
                            number = Integer.parseInt(etNumAdvance.getText().toString());
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),"Enter Digit Only",Toast.LENGTH_LONG).show();
                        }
                        url = "http://numbersapi.com/" + number + "/trivia";
                        setText(url);
                        break;
                    case "date":
                        progressBar.setVisibility(View.VISIBLE);
                        url = "http://numbersapi.com/" + monthUrl + "/"+dateUrl+"/date";
                        setText(url);
                        break;
                    case "year":
                        progressBar.setVisibility(View.VISIBLE);
                        url = "http://numbersapi.com/" + yearUrl + "/year";
                        setText(url);
                        break;
                }
            }
        });
    }


    void setText(String url) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        mHandler = new Handler(Looper.getMainLooper());

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("GetData", e.toString());
                responses = e.toString();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.INVISIBLE);
                        layout.setVisibility(View.VISIBLE);
                        tvAdvanceView.setText("There Is Some Error");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String message = response.body().string();
                Log.i("tag", message);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.INVISIBLE);
                        layout.setVisibility(View.VISIBLE);
                        tvAdvanceView.setText(message);
                    }
                });
            }
        });


    }
}
