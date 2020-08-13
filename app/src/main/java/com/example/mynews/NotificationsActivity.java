package com.example.mynews;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NotificationsActivity extends AppCompatActivity implements DatePickerFragment.OnDateSetListener{

    private EditText mBeginDateText;
    private EditText mEndDateText;
    private Locale locale;
    private EditText mSearchView;
    private Button mSearchButton;

    private static final String NOTIFICATION_ID_STRING = "1";
    private static final int NOTIFICATION_ID = 1;

    private DialogFragment datePicker;

    // SharedPreferences variable
    public static final String MyPref = "MyPrefsFile";
    protected SharedPreferences.Editor mEditor;
    protected SharedPreferences mSharedPreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
        locale = getResources().getConfiguration().locale;
        datePicker = new DatePickerFragment();

        //start notification channel
        createNotificationChannel();


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
        //start the ifCheckBoxIsCheck method
        ifCheckBoxIsCheck();


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

    // This method will retrieve information from the SharedPreferences if the checkBox are set to True or False and set the proper value
    private void ifCheckBoxIsCheck(){
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
    }


    //Notification builder
    private void addNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_ID_STRING)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("test")
                .setContentText("this a test text")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //Notify the app
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_ID_STRING, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
    }


    //Method that will force to close the keyboard once called
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    // this method will handle the date variable from the user
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
                    mEditor.putString("beginDate", String.valueOf(sdf));
                    break;
                case R.id.pickEndDate:
                    calendar.set(year, month, dayOfMonth);
                    mEndDateText.setText(dateFormat.format(calendar.getTime()));
                    mEditor.putString("endDate", String.valueOf(sdf));
            }mEditor.commit();
        }
    }

    // this method will save the current state of the checkBox for each value in the SharedPreference
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


    //On click action when pressing the Search button.
    // if the searchQuery is empty, display a error message
    // if all the data are properly set, start the notification process
    public void onTest(View view) {
        final Context context = getApplicationContext();
        final CharSequence text = "Search Query can't be empty!";
        final int duration = Toast.LENGTH_SHORT;
        if( mSearchView.getText().toString().length() == 0 ){
            Toast.makeText(context, text,duration).show();
            getCurrentFocus();
        }else{
            addNotification();
            finish();
        }
    }
}
