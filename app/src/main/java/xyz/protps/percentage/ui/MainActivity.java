package xyz.protps.percentage.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;
import butterknife.OnTextChanged;
import xyz.protps.percentage.R;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @BindView(R.id.edit_value)
    EditText editValue;

    @BindView(R.id.edit_settings_percentage)
    EditText editSettingsPercentage;

    @BindView(R.id.text_percentage)
    TextView textPercentage;

    @BindView(R.id.text_value_minus_percentage)
    TextView textValueMinusPercentage;

    @BindView(R.id.text_value_plus_percentage)
    TextView textValuePlusPercentage;

    String y_m_percentage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        y_m_percentage =
                sharedPreferences.getString(getString(R.string.settings_yemen_mobile_percentage_value_key),
                getString(R.string.settings_yemen_mobile_percentage_default));


        editSettingsPercentage.setText(y_m_percentage + " %");
    }

    @OnLongClick(R.id.edit_settings_percentage)
    public boolean updateSettings(){
        // Get a reference to the LayoutInflater
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);

        // Create a view from layout using inflation
        View promptView = layoutInflater.inflate(R.layout.dialog_for_settings, null);

        // Create an AlertDialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

        // Set prompts.xml to be the layout file of the alert dialog builder
        alertDialogBuilder.setView(promptView);

        // Make this dialog unCancelable except from negative and positive button.
        alertDialogBuilder.setCancelable(false);

        // Views declaration
        final EditText percentageValue = promptView.findViewById(R.id.edit_text_percentage_value);
        ImageButton clearText = promptView.findViewById(R.id.image_button_clear_text);

        // Get the value of searched text from preferences
        String y_m_percentage =
                sharedPreferences.getString(getString(R.string.settings_yemen_mobile_percentage_value_key),
                        getString(R.string.settings_yemen_mobile_percentage_default));

        percentageValue.setText(y_m_percentage);

        clearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear the edit text
                percentageValue.setText("");
            }
        });
        // Add a positive button to this dialog
        alertDialogBuilder.setPositiveButton(R.string.update,
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // Add a negative button to this dialog to cancel it.
        alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cancel this dialog
                dialog.dismiss();
            }
        });

        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();

        // Show the dialog
        alertDialog.show();

        alertDialog.getButton(Dialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text from the edit text
                String value = percentageValue.getText().toString().trim();

                // Check it if not empty
                if (!TextUtils.isEmpty(value)) {
                    // Set values to activity variables

                        // Open the shared preference in edit mode
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        // Put all the data to be saved
                        editor.putString(getString(R.string.settings_yemen_mobile_percentage_value_key), value);
                        // Save the values to the preferences
                        editor.apply();

                    String y_m_percentage =
                            sharedPreferences.getString(getString(R.string.settings_yemen_mobile_percentage_value_key),
                                    getString(R.string.settings_yemen_mobile_percentage_default));


                    editSettingsPercentage.setText(y_m_percentage + " %");

                    alertDialog.dismiss();

                } else {
                    // Show message to tell the user to write something to search
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.write_any_thing),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        return true;
    }

    @OnTextChanged(R.id.edit_value)
    public void calculate(){

        y_m_percentage =
                sharedPreferences.getString(getString(R.string.settings_yemen_mobile_percentage_value_key),
                        getString(R.string.settings_yemen_mobile_percentage_default));
        //String percentageValue = editSettingsPercentage.getText().toString();
        double pv = 0;
        if(!y_m_percentage.isEmpty()){
            pv = Double.valueOf(y_m_percentage);
        }

        String enteredValue = editValue.getText().toString();
        if (!enteredValue.isEmpty()) {
            double value = Double.valueOf(enteredValue);
            double percentage = 0;
            double percentagePlusValue = 0;
            double percentageMinusValue = 0;

            if(pv > 0){
                percentage = value * (pv / 100);
                percentagePlusValue = value + percentage;
                percentageMinusValue = value - percentage;
            }

            textPercentage.setText(String.valueOf(percentage));

            textValuePlusPercentage.setText(String.valueOf(percentagePlusValue));
            textValueMinusPercentage.setText(String.valueOf(percentageMinusValue));

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.settings) {
            startActivity(new Intent(MainActivity.this,SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        y_m_percentage =
                sharedPreferences.getString(getString(R.string.settings_yemen_mobile_percentage_value_key),
                        getString(R.string.settings_yemen_mobile_percentage_default));

        editSettingsPercentage.setText(y_m_percentage + " %");
    }
}