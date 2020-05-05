package com.example.mynews;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

        //Retrieving sharedPreferences data for the CheckBox
        mSharedPreferences = getSharedPreferences(MyPref, MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        boolean artsIsChecked = mSharedPreferences.getBoolean("arts", false);
        if(!artsIsChecked){
            mEditor.putBoolean("arts", false);
        }else {
            CheckBox artsCheckBox = findViewById(R.id.checkBoxArt);
            artsCheckBox.setChecked(true);
        }

        boolean politicsIsChecked = mSharedPreferences.getBoolean("politics", false);
        if(!politicsIsChecked){
            mEditor.putBoolean("politics", false);
        }else{
            CheckBox politicsCheckBox = findViewById(R.id.checkBoxPolitic);
            politicsCheckBox.setChecked(true);
        }

        boolean businessIsChecked = mSharedPreferences.getBoolean("business", false);
        if(!businessIsChecked){
            mEditor.putBoolean("business", false);
        }else{
            CheckBox businessCheckBox = findViewById(R.id.checkBoxBusiness);
            businessCheckBox.setChecked(true);
        }

        boolean sportsIsChecked = mSharedPreferences.getBoolean("sports", false);
        if(!sportsIsChecked){
            mEditor.putBoolean("sports", false);
        }else{
            CheckBox sportsCheckBox = findViewById(R.id.checkBoxSport);
            sportsCheckBox.setChecked(true);
        }

        boolean entrepreneursIsChecked = mSharedPreferences.getBoolean("entrepreneurs", false);
        if(!entrepreneursIsChecked){
            mEditor.putBoolean("entrepreneurs", false);
        }else{
            CheckBox entrepreneursCheckBox = findViewById(R.id.checkBoxEntrepreneur);
            entrepreneursCheckBox.setChecked(true);
        }

        boolean travelsIsChecked = mSharedPreferences.getBoolean("travels", false);
        if(!travelsIsChecked){
            mEditor.putBoolean("travels", false);
        }else{
            CheckBox travelsCheckBox = findViewById(R.id.checkBoxTravel);
            travelsCheckBox.setChecked(true);
        }
        mEditor.commit();

        //Search Query will be saved and pulled from here when the user type a search request.
        final EditText searchView = findViewById(R.id.simpleSearchView);
        searchView.setText(mSharedPreferences.getString("searchQuery", ""));
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEditor.putString("searchQuery", s.toString()).commit();
            }

            @Override
            public void afterTextChanged(Editable s) {
                mEditor.putString("searchQuery", s.toString()).commit();
            }
        });

    }



    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // display the date chosen by the user
        final Calendar calendar = Calendar.getInstance(locale);
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        final String myFormat = "dd/MM/yy";
        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        if (getCurrentFocus() != null) {
            switch (getCurrentFocus().getId()) {

                case R.id.pickBeginDate:
                    calendar.set(year, month, dayOfMonth);
                    mBeginDateText.setText(sdf.format(calendar.getTime()));
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
            case R.id.checkBoxBusiness:
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
