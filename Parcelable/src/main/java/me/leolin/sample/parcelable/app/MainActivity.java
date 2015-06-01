package me.leolin.sample.parcelable.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ToBeTransferred toBeTransferred = new ToBeTransferred();
        toBeTransferred.getMap().put("a",1);
        toBeTransferred.getMap().put("b",2);
        toBeTransferred.getMap().put("c",3);

        Intent intent = new Intent(this,SecondActivity.class);
        intent.putExtra("data",toBeTransferred);

        startActivity(intent);
    }




}
