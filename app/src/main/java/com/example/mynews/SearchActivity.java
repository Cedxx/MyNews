package com.example.mynews;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

        //Retrieving sharedPreferences data
        mSharedPreferences = getSharedPreferences(MyPref, MODE_PRIVATE);


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
                    mEditor.putString("beginDate", String.valueOf(mBeginDateText));
                    break;
                case R.id.pickEndDate:
                    calendar.set(year, month, dayOfMonth);
                    mEndDateText.setText(dateFormat.format(calendar.getTime()));
                    mEditor.putString("endDate", String.valueOf(mEndDateText));
            }mEditor.commit();
        }
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBoxArt:
                if (checked)
                mEditor.putBoolean("arts", true);
            else

                mEditor.putBoolean("arts", false);
                break;
            case R.id.checkBoxPolitic:
                if (checked)
                    mEditor.putBoolean("politics", true);
            else
                    mEditor.putBoolean("politics", false);
                break;
            case R.id.checkBoxBusines:
                if (checked)
                    mEditor.putBoolean("business", true);
                else
                    mEditor.putBoolean("business", false);
                break;
            case R.id.checkBoxSport:
                if (checked)
                    mEditor.putBoolean("sports", true);
                else
                    mEditor.putBoolean("sports", false);
                break;
            case R.id.checkBoxEntrepreneur:
                if (checked)
                    mEditor.putBoolean("entrepreneurs", true);
                else
                    mEditor.putBoolean("entrepreneurs", false);
                break;
            case R.id.checkBoxTravel:
                if (checked)
                    mEditor.putBoolean("travels", true);
                else
                    mEditor.putBoolean("travels", false);
                break;
        }mEditor.commit();
    }


}
