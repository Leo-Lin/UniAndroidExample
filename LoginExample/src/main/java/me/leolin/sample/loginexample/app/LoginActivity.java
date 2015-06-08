package me.leolin.sample.loginexample.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import me.leolin.sample.loginexample.app.volley.VolleyQueue;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    private Button buttonLogin;
    private EditText editTextAccount;
    private EditText editTextPassword;
    private ProgressBar progressBar;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //share storage
        sharedPreferences = getSharedPreferences("APIKey", MODE_PRIVATE);

        //means login
        if (sharedPreferences.getString("APIKey", null) != null) {
            goToNextActivity();
        }

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        editTextAccount = (EditText) findViewById(R.id.editTextAccount);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VolleyQueue.getInstance(this).getRequestQueue().cancelAll("login");
    }

    private void doLogin() {
        try {
            progressBar.setVisibility(View.VISIBLE);

            JSONObject loginBody = new JSONObject();
            loginBody.put("Account", editTextAccount.getText().toString());
            loginBody.put("Password", editTextPassword.getText().toString());
            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "http://jasonchi.ddns.net:8080/api/Authenticate", loginBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressBar.setVisibility(View.INVISIBLE);
                            if (!TextUtils.isEmpty(response.optString("Failure"))) {
                                Toast.makeText(LoginActivity.this, "Login fail", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            String apiKey = response.optString("APIKey");
                            if (TextUtils.isEmpty(apiKey)) {
                                Toast.makeText(LoginActivity.this, "Login fail,cannot get APIKey", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            sharedPreferences.edit().putString("APIKey", apiKey).commit();
                            goToNextActivity();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, "Login fail:" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
            );

            VolleyQueue.getInstance(this).getRequestQueue().add(request);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void goToNextActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
