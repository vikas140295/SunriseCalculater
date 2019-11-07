package au.edu.swin.sdmd.suncalculatorjava.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import au.edu.swin.sdmd.suncalculatorjava.R;
import au.edu.swin.sdmd.suncalculatorjava.listener.OnItemClickListener;
import au.edu.swin.sdmd.suncalculatorjava.model.City;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private List<City> cities;
    private OnItemClickListener clickListener;

    public CityAdapter(List<City> cities, OnItemClickListener clickListener){
        this.cities = cities;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.layout_city_item,viewGroup,false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder cityViewHolder, int i) {
        final City city = cities.get(i);
        cityViewHolder.cityTV.setText(city.getName());
        cityViewHolder.cityTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClicked(city);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    class CityViewHolder extends RecyclerView.ViewHolder {

        final TextView cityTV;

        CityViewHolder(@NonNull View itemView) {
            super(itemView);
            cityTV = itemView.findViewById(R.id.cityTV);
        }
    }

}
