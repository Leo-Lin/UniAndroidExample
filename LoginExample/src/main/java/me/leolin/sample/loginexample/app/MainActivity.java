package me.leolin.sample.loginexample.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //share storage
        sharedPreferences = getSharedPreferences("APIKey", MODE_PRIVATE);

        String apiKey = sharedPreferences.getString("APIKey", null);
        if (apiKey == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }


        Toast.makeText(MainActivity.this, "Login ok, ApiKey is " + apiKey, Toast.LENGTH_SHORT).show();

        findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().clear().commit();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
    }


}
