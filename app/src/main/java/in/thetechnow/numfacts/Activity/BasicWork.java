package in.thetechnow.numfacts.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import in.thetechnow.numfacts.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BasicWork extends AppCompatActivity {
    String category;
    Intent intent;
    String responses;
    String response = "result";
    TextView tvBasicWork;
    private Handler mHandler;
    LinearLayout tvBasicWorkLayout;
    ProgressBar pbBasicWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_work);
        intent = getIntent();
        category = intent.getStringExtra("category");
        Toast.makeText(getApplicationContext(), category, Toast.LENGTH_LONG).show();
        tvBasicWork = (TextView)findViewById(R.id.tv_basic_work);
        tvBasicWorkLayout = (LinearLayout)findViewById(R.id.tv_basic_work_layout);
        pbBasicWork = (ProgressBar)findViewById(R.id.pb_basic_work);
        tvBasicWorkLayout.setVisibility(View.INVISIBLE);
        pbBasicWork.setVisibility(View.VISIBLE);
        setText();

    }

    void setText(){
        OkHttpClient client = new OkHttpClient();
        String url = "http://numbersapi.com/"+"random/"+category;
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
                        pbBasicWork.setVisibility(View.INVISIBLE);
                        tvBasicWorkLayout.setVisibility(View.VISIBLE);
                        tvBasicWork.setText("There Is Some Error");
                    }
                });
            }

            @Override
            public void onResponse(Call call,Response response) throws IOException {
                final String message = response.body().string();
                Log.i("tag", message);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        pbBasicWork.setVisibility(View.INVISIBLE);
                        tvBasicWorkLayout.setVisibility(View.VISIBLE);
                        tvBasicWork.setText(message);
                    }
                });
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.refresh_menu,menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.refresh_menu:
                pbBasicWork.setVisibility(View.VISIBLE);
                setText();
                return true;
        }
        return false;
    }
}
