package com.example.mynews;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity implements DatePickerFragment.OnDateSetListener {

    private EditText mBeginDateText;
    private EditText mEndDateText;
    private Locale locale;

    private DialogFragment datePicker;

    // SharedPreferences variable
    public static final String MyPref = "MyPrefsFile";
    protected SharedPreferences.Editor mEditor;
    protected SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Linking the Search Main element to the view
        setContentView(R.layout.search_main);

        locale = getResources().getConfiguration().locale;
        datePicker = new DatePickerFragment();

        //Linking the Begin Date text area to the view
        mBeginDateText = findViewById(R.id.pickBeginDate);
        //disable the keyboard input
        mBeginDateText.setInputType(InputType.TYPE_NULL);
        //Set the OnClickListener
        mBeginDateText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    datePicker.show(getSupportFragmentManager(), "datePickerBegin");
                }else{
                    datePicker.dismiss();
                }
            }
        });

        //Linking the End Date text area to the view
        mEndDateText = findViewById(R.id.pickEndDate);
        //disable the keyboard input
        mEndDateText.setInputType(InputType.TYPE_NULL);
        //Set the OnClickListener
        mEndDateText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    datePicker.show(getSupportFragmentManager(), "datePickerEnd");
                }else {
                    datePicker.dismiss();
                }

            }
        });

    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // display the date chosen by the user
        final Calendar calendar = Calendar.getInstance(locale);
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);

        if (getCurrentFocus() != null) {
            switch (getCurrentFocus().getId()) {

                case R.id.pickBeginDate:
                    calendar.set(year, month, dayOfMonth);
                    mBeginDateText.setText(dateFormat.format(calendar.getTime()));
                    break;
                case R.id.pickEndDate:
                    calendar.set(year, month, dayOfMonth);
                    mEndDateText.setText(dateFormat.format(calendar.getTime()));
            }
        }
    }
}
