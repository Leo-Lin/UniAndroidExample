package me.leolin.datepickerexample.app;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editTextStartDate = (EditText) findViewById(R.id.startDate);
        final EditText editTextEndDate = (EditText) findViewById(R.id.endDate);

        editTextStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, monthOfYear);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String dateString = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());

                        editTextStartDate.setText(dateString);
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        editTextEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, monthOfYear);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String dateString = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());

                        editTextEndDate.setText(dateString);
                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                ).show();
            }
        });
    }

}
