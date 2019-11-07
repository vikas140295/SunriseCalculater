package au.edu.swin.sdmd.suncalculatorjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import au.edu.swin.sdmd.suncalculatorjava.adapter.CityAdapter;
import au.edu.swin.sdmd.suncalculatorjava.listener.OnItemClickListener;
import au.edu.swin.sdmd.suncalculatorjava.model.City;

public class CitySelectorActivity extends AppCompatActivity {

    private String filename = "Locations.csv";
    private ArrayList<City> cities;
    private CityAdapter cityAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selector);
        setTitle("Select City");
        setUpView();
    }

    private void setUpView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        readAssetsFile();
        cities.addAll(readInternalFile());
        cityAdapter = new CityAdapter(cities, new OnItemClickListener() {
            @Override
            public void onItemClicked(City city) {
                Intent intent = new Intent();
                intent.putExtra("city",city);
                setResult(200,intent);
                finish();
            }
        });
        recyclerView.setAdapter(cityAdapter);
    }

    private void readAssetsFile() {
        cities = new ArrayList<>();
        try {
            InputStream is = getAssets().open("au_location");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            String csvSplitBy = ",";

            while ((line = br.readLine()) != null) {
                String[] row = line.split(csvSplitBy);
                if(row.length == 4) {
                    City city = new City();
                    city.setName(row[0]);
                    city.setLatitude(row[1]);
                    city.setLongitude(row[2]);
                    city.setTimezone(row[3]);
                    cities.add(city);
                }
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    //Locations added by user are saved in internal file
    private List<City> readInternalFile() {
        ArrayList<City> cities = new ArrayList<>();
        File file = new File(getFilesDir(), filename);
        if(!file.exists()) {
            return cities;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();
            while (line != null) {
                String[] cityData = line.split(",");
                City city = new City();
                city.setName(cityData[0]);
                city.setLatitude(cityData[1]);
                city.setLongitude(cityData[2]);
                city.setTimezone(cityData[3]);
                cities.add(city);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cities;
    }
}
