package me.leolin.sample.loginexample.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


public class LoginActivity extends ActionBarActivity {

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    private Button buttonLogin;
    private EditText editTextAccount;
    private EditText editTextPassword;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

    private void doLogin() {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(Boolean loginSuccess) {
                progressBar.setVisibility(View.GONE);

                if (loginSuccess) {
                    goToNextActivity();
                } else {
                    Toast.makeText(LoginActivity.this, "Login fail.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                Boolean result = false;
                try {
                    HttpClient client = new DefaultHttpClient();

                    HttpPost post = new HttpPost("http://jasonchi.ddns.net:8080/api/Authenticate");
                    post.addHeader("content-type", "application/json");

                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("Account", editTextAccount.getText());
                    jsonBody.put("Password", editTextPassword.getText());

                    post.setEntity(new StringEntity(jsonBody.toString(), "UTF-8"));

                    HttpResponse response = client.execute(post);
                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == 200) {
                        String responseBody = EntityUtils.toString(response.getEntity(), "UTF-8");
                        JSONObject responseJson = new JSONObject(responseBody);
                        if (responseJson.optBoolean("success")) {
                            result = responseJson.getBoolean("success");
                        }
                    } else {
                        Log.d(LOG_TAG, "Login fail with status code=" + statusCode);
                    }
                } catch (Exception e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                }

                return result;
            }
        }.execute();
    }

    private void goToNextActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
