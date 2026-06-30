package com.example.pr19_rmp_gudochkina;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CustomDialogFragment.DialogFragmentListener {

    private TextView timePick;
    private Button btnDate, btnTime, btnDialog;
    private Calendar dateAndTime;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateAndTime = Calendar.getInstance();

        timePick = findViewById(R.id.time_pick);
        btnDate = findViewById(R.id.btn_date);
        btnTime = findViewById(R.id.btn_time);
        btnDialog = findViewById(R.id.btn_dialog);

        btnDate.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        btnDialog.setOnClickListener(this);

        setInitialDateTime();
    }

    private void setInitialDateTime() {
        timePick.setText(dateFormat.format(dateAndTime.getTime()));
    }

    // Установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    // Установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btn_date) {
            new DatePickerDialog(MainActivity.this, d,
                    dateAndTime.get(Calendar.YEAR),
                    dateAndTime.get(Calendar.MONTH),
                    dateAndTime.get(Calendar.DAY_OF_MONTH))
                    .show();
        } else if (id == R.id.btn_time) {
            new TimePickerDialog(MainActivity.this, t,
                    dateAndTime.get(Calendar.HOUR_OF_DAY),
                    dateAndTime.get(Calendar.MINUTE), true)
                    .show();
        } else if (id == R.id.btn_dialog) {
            CustomDialogFragment dialog = new CustomDialogFragment();
            String currentDateTime = timePick.getText().toString();
            dialog.setReceivedData(currentDateTime);
            dialog.show(getSupportFragmentManager(), "CUSTOM_DIALOG");
        }
    }
    public void onDialogResult(String result) {
        try {
            Date newDate = dateFormat.parse(result);

            dateAndTime.setTime(newDate);
            timePick.setText(result);

            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();

        } catch (ParseException e) {
            Toast.makeText(this, "Неверный формат! Ожидается дд.мм.гггг чч:мм", Toast.LENGTH_LONG).show();
        }
    }

}