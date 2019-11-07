package au.edu.swin.sdmd.suncalculatorjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class AddCityActivity extends AppCompatActivity {

    private String filename = "Locations.csv";
    private EditText cityET;
    private EditText latET;
    private EditText longET;
    private EditText timezoneET;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);
        setTitle("Add City");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cityET = findViewById(R.id.nameET);
        latET = findViewById(R.id.latET);
        longET = findViewById(R.id.longET);
        timezoneET = findViewById(R.id.timezoneET);
        setListener();
    }

    private void setListener() {
        Button submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateForm()){
                    writeToFile();
                    Toast.makeText(AddCityActivity.this,"City Added",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void writeToFile() {
        File file = new File(getFilesDir(), filename);
        //GMT-8:00
        String value = cityET.getText().toString()+","+latET.getText().toString()+","+longET.getText().toString()
                +",GMT"+timezoneET.getText().toString()+"\n";
        try{
            //if file doesnt exists, then create it
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWritter = new FileWriter(file,true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(value);
            bufferWritter.close();
            fileWritter.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private boolean validateForm(){
        if(TextUtils.isEmpty(cityET.getText().toString())){
            cityET.setError("Enter City");
            return false;
        }

        if(TextUtils.isEmpty(latET.getText().toString())){
            latET.setError("Enter Latitude");
            return false;
        }

        if(TextUtils.isEmpty(longET.getText().toString())){
            longET.setError("Enter Longitude");
            return false;
        }

        if(TextUtils.isEmpty(timezoneET.getText().toString())){
            timezoneET.setError("Enter Timezone");
            return false;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
