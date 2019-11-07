package au.edu.swin.sdmd.suncalculatorjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import au.edu.swin.sdmd.suncalculatorjava.calc.AstronomicalCalendar;
import au.edu.swin.sdmd.suncalculatorjava.calc.GeoLocation;
import au.edu.swin.sdmd.suncalculatorjava.model.City;

public class MainActivity extends AppCompatActivity {

    private City city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Sun Timings");
        initializeUI();
    }

    private void initializeUI() {
        DatePicker dp = findViewById(R.id.datePicker);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        dp.init(year,month,day,dateChangeHandler); // setup initial values and reg. handler
        TextView locationTV = findViewById(R.id.locationTV);
        locationTV.setText("Melbourne ,AU");
        city = new City();
        city.setLatitude("-37.50");
        city.setLongitude("145.01");
        city.setName("Melbourne ,AU");
        updateTime(year, month, day);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, CitySelectorActivity.class), 100);
            }
        });
    }

    private void updateTime(int year, int monthOfYear, int dayOfMonth) {
        TimeZone tz = TimeZone.getDefault();
        if(!city.getName().equals("Melbourne ,AU")) {
            tz = TimeZone.getTimeZone(city.getTimezone());
        }
        double latitude = Double.parseDouble(city.getLatitude());
        double longitude = Double.parseDouble(city.getLongitude());
        GeoLocation geolocation = new GeoLocation(city.getName(), latitude, longitude, tz);
        AstronomicalCalendar ac = new AstronomicalCalendar(geolocation);
        ac.getCalendar().set(year, monthOfYear, dayOfMonth);
        Date srise = ac.getSunrise();
        Date sset = ac.getSunset();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        TextView sunriseTV = findViewById(R.id.sunriseTimeTV);
        TextView sunsetTV = findViewById(R.id.sunsetTimeTV);

        sunriseTV.setText(sdf.format(srise));
        sunsetTV.setText(sdf.format(sset));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == 200){
            city = data.getParcelableExtra("city");
            TextView locationTV = findViewById(R.id.locationTV);
            locationTV.setText(city.getName()+" ,AU");
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            updateTime(year, month, day);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.add_city){
            Intent intent = new Intent(MainActivity.this, AddCityActivity.class);
            startActivityForResult(intent, 100);
        }
        return super.onOptionsItemSelected(item);
    }

    DatePicker.OnDateChangedListener dateChangeHandler = new DatePicker.OnDateChangedListener()
    {
        public void onDateChanged(DatePicker dp, int year, int monthOfYear, int dayOfMonth)
        {
            updateTime(year, monthOfYear, dayOfMonth);
        }
    };


}
