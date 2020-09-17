package com.example.mynews.Controllers.Activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.mynews.Controllers.Fragments.DatePickerFragment;
import com.example.mynews.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity implements DatePickerFragment.OnDateSetListener {

    private EditText mBeginDateText;
    private EditText mEndDateText;
    private Locale locale;
    private EditText mSearchView;
    private ArrayList<String> categoriesFields;
    private DialogFragment datePicker;

    //CheckBox Variable
    private CheckBox mCheckBoxArts;
    private CheckBox mCheckBoxBusiness;
    private CheckBox mCheckBoxEntrepreneurs;
    private CheckBox mCheckBoxPolitics;
    private CheckBox mCheckBoxSports;
    private CheckBox mCheckBoxTravel;

    // SharedPreferences variable
    public static final String MyPref = "MyPrefsFile";
    protected SharedPreferences.Editor mEditor;
    protected SharedPreferences mSharedPreferences;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Retrieving sharedPreferences data for the CheckBox
        mSharedPreferences = getSharedPreferences(MyPref, MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        // Linking the Search Main element to the view
        setContentView(R.layout.search_main);

        // Creating the list in a array for the selected categories
        categoriesFields = new ArrayList<>();

        //initializing the CHeckBox view
        mCheckBoxArts = findViewById(R.id.checkBoxArt);
        mCheckBoxBusiness = findViewById(R.id.checkBoxBusiness);
        mCheckBoxEntrepreneurs = findViewById(R.id.checkBoxEntrepreneur);
        mCheckBoxPolitics = findViewById(R.id.checkBoxPolitic);
        mCheckBoxSports = findViewById(R.id.checkBoxSport);
        mCheckBoxTravel = findViewById(R.id.checkBoxTravel);

        locale = getResources().getConfiguration().locale;
        datePicker = new DatePickerFragment();

        //Linking the Begin Date text area to the view
        mBeginDateText = findViewById(R.id.pickBeginDate);
        //disable the keyboard input
        mBeginDateText.setInputType(InputType.TYPE_NULL);

        //Retrieve the begin date from the sharedPrefs
        mBeginDateText.setText(mSharedPreferences.getString("beginDate","Begin Date"));
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
        //Retrieve the end date from the sharedPrefs
        mEndDateText.setText(mSharedPreferences.getString("endDate", "End Date"));
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

        //Set the correct value in the sharedPreferences for the CheckBox
        setSharedPreferencesForCheckBox();


        //Search Query will be saved and pulled from here when the user type a search request.
        mSearchView = findViewById(R.id.simpleSearchView);
        //retrieving the default save data for the search Query
        mSearchView.setText(mSharedPreferences.getString("searchQuery", ""));

        //Set an OnEditorActionListener to listen to specific key press
        mSearchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    mSearchView.setText(mSearchView.getText().toString());
                    mEditor.putString("searchQuery", mSearchView.getText().toString()).commit();
                    handled = true;
                }hideKeyboard(v);
                return handled;
            }
        });


    }

    public void setSharedPreferencesForCheckBox(){
        boolean artsIsChecked = mSharedPreferences.getBoolean("arts", false);
        if(!artsIsChecked){
            mEditor.putBoolean("arts", false);
        }else {
            CheckBox artsCheckBox = findViewById(R.id.checkBoxArt);
            artsCheckBox.setChecked(true);
            categoriesFields.add("arts");
        }

        boolean politicsIsChecked = mSharedPreferences.getBoolean("politics", false);
        if(!politicsIsChecked){
            mEditor.putBoolean("politics", false);
        }else{
            CheckBox politicsCheckBox = findViewById(R.id.checkBoxPolitic);
            politicsCheckBox.setChecked(true);
            categoriesFields.add("politics");
        }

        boolean businessIsChecked = mSharedPreferences.getBoolean("business", false);
        if(!businessIsChecked){
            mEditor.putBoolean("business", false);
        }else{
            CheckBox businessCheckBox = findViewById(R.id.checkBoxBusiness);
            businessCheckBox.setChecked(true);
            categoriesFields.add("business");
        }

        boolean sportsIsChecked = mSharedPreferences.getBoolean("sports", false);
        if(!sportsIsChecked){
            mEditor.putBoolean("sports", false);
        }else{
            CheckBox sportsCheckBox = findViewById(R.id.checkBoxSport);
            sportsCheckBox.setChecked(true);
            categoriesFields.add("sports");
        }

        boolean entrepreneursIsChecked = mSharedPreferences.getBoolean("entrepreneurs", false);
        if(!entrepreneursIsChecked){
            mEditor.putBoolean("entrepreneurs", false);
        }else{
            CheckBox entrepreneursCheckBox = findViewById(R.id.checkBoxEntrepreneur);
            entrepreneursCheckBox.setChecked(true);
            categoriesFields.add("entrepreneurs");
        }

        boolean travelsIsChecked = mSharedPreferences.getBoolean("travels", false);
        if(!travelsIsChecked){
            mEditor.putBoolean("travels", false);
        }else{
            CheckBox travelsCheckBox = findViewById(R.id.checkBoxTravel);
            travelsCheckBox.setChecked(true);
            categoriesFields.add("travels");
        }
        mEditor.commit();
    }

    //Method that will force to close the keyboard once called
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // display the date chosen by the user
        final Calendar calendar = Calendar.getInstance(locale);
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        final String myFormat = "dd/MM/yy";
        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if (getCurrentFocus() != null) {
            switch (getCurrentFocus().getId()) {

                case R.id.pickBeginDate:
                    calendar.set(year, month, dayOfMonth);
                    mBeginDateText.setText(sdf.format(calendar.getTime()));
                    mEditor.putString("beginDate", sdf.format(calendar.getTime()));
                    break;
                case R.id.pickEndDate:
                    calendar.set(year, month, dayOfMonth);
                    mEndDateText.setText(dateFormat.format(calendar.getTime()));
                    mEditor.putString("endDate", sdf.format(calendar.getTime()));
            }mEditor.commit();
        }
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBoxArt:
                if (checked) {
                    mEditor.putBoolean("arts", true);
                    categoriesFields.add("arts");
                }
            else {
                    mEditor.putBoolean("arts", false);
                    categoriesFields.remove("arts");
                }

                break;
            case R.id.checkBoxPolitic:
                if (checked) {
                    mEditor.putBoolean("politics", true);
                   categoriesFields.add("politics");
                }
            else {
                    mEditor.putBoolean("politics", false);
                    categoriesFields.remove("politics");
                }
                break;
            case R.id.checkBoxBusiness:
                if (checked) {
                    mEditor.putBoolean("business", true);
                    categoriesFields.add("business");
                }
                else {
                    mEditor.putBoolean("business", false);
                    categoriesFields.remove("business");
                }
                break;
            case R.id.checkBoxSport:
                if (checked) {
                    mEditor.putBoolean("sports", true);
                    categoriesFields.add("sports");
                }
                else {
                    mEditor.putBoolean("sports", false);
                    categoriesFields.remove("sports");
                }
                break;
            case R.id.checkBoxEntrepreneur:
                if (checked) {
                    mEditor.putBoolean("entrepreneurs", true);
                     categoriesFields.add("entrepreneurs");
                }
                else {
                    mEditor.putBoolean("entrepreneurs", false);
                    categoriesFields.remove("entrepreneurs");
                }
                break;
            case R.id.checkBoxTravel:
                if (checked) {
                    mEditor.putBoolean("travels", true);
                    categoriesFields.add("travels");
                }
                else {
                    mEditor.putBoolean("travels", false);
                    categoriesFields.remove("travels");
                }
                break;
        }mEditor.commit();
    }

    //Method that will check if at least 1 checkBox is checked and if the Search Query is not empty
    private boolean validateCriteria(){
        final Context context = getApplicationContext();
        final CharSequence text = "Required data : Search Query and at least one category";
        final int duration = Toast.LENGTH_SHORT;

        if (mSearchView.getText().toString().equals("") || (!mCheckBoxArts.isChecked() &&
                !mCheckBoxBusiness.isChecked() &&
                !mCheckBoxEntrepreneurs.isChecked() &&
                !mCheckBoxPolitics.isChecked() &&
                !mCheckBoxSports.isChecked() &&
                !mCheckBoxTravel.isChecked())
        ){
            //when false, return a toast message to let the user know that one field is missing
            Toast.makeText(context, text,duration).show();
            return false;
        }else return true;

    }


    //On click action when pressing the Search button.
      @RequiresApi(api = Build.VERSION_CODES.O)
      public void onSearchButtonClick(View view) {
        final Context context = getApplicationContext();
        final CharSequence text = "Required data : Search Query and at least one category";
        final int duration = Toast.LENGTH_SHORT;
        if(!validateCriteria()){
            //Toast.makeText(context, text,duration).show();
        getCurrentFocus();
        }else{
            String fields = String.join("/", categoriesFields);
              mEditor.putString("categoriesQuery", fields).commit();
              Intent resultIntent = new Intent();
              resultIntent.putExtra("TAB_RESULT_TITLE", fields);
              setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    }
}
