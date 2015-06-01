package me.leolin.sample.parcelable.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.util.Map;


public class SecondActivity extends ActionBarActivity {

    private static final String LOG_TAG = SecondActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToBeTransferred toBeTransferred = getIntent().getParcelableExtra("data");

        for (Map.Entry<String, Integer> entry : toBeTransferred.getMap().entrySet()) {
            Log.d(LOG_TAG, "key=" + entry.getKey() + ",value=" + entry.getValue());
        }
    }

}
