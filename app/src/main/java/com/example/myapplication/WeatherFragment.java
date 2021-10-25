package com.example.myapplication;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.utilities.NetworkUtils;
import com.example.myapplication.utilities.OpenWeatherJsonUtils;

import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment {

    private static final int TEMPERATURE = 0;
    private static final int CONDITION = 1;

    private TextView mWeatherTextView;
    private TextView townText;

    private ImageView condIcon;

    private Button weatherNow;
    private Button weatherFrom;
    private Button weatherWant;
    private Button weatherFamily;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WeatherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeatherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeatherFragment newInstance(String param1, String param2) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        mWeatherTextView = (TextView) view.findViewById(R.id.weather_test);
        townText = (TextView) view.findViewById((R.id.town_name));
        condIcon = (ImageView) view.findViewById(R.id.weather_icon);



        //Northborough Button
        weatherNow = (Button) view.findViewById(R.id.NorthboroughButton);
        weatherNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadWeatherData("Northborough");
                townText.setText("Where I am Now. (Northborough, MA)");
            }
        });

        //Marlborough Button
        weatherFrom = (Button) view.findViewById(R.id.MarlboroughButton);
        weatherFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadWeatherData("Marlborough");
                townText.setText("Where I am From. (Marlborough, MA)");
            }
        });

        //Los Angeles Button
        weatherWant = (Button) view.findViewById(R.id.LosAngelasButton);
        weatherWant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadWeatherData("Los Angeles");
                townText.setText("Where I want to be. (Los Angelas, CA)");
            }
        });

        //Shanghai Button
        weatherFamily = (Button) view.findViewById(R.id.ShanghaiButton);
        weatherFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadWeatherData("Shanghai");
                townText.setText("Where my Family is from. (Shanghai, CN)");
            }
        });

        loadWeatherData("Northborough");
        //loadWeatherData("Some other City");
        return view;
    }

    private void loadWeatherData(String city) {
        // String location = SunshinePreferences.getPreferredWeatherLocation(this);

        new FetchWeatherTask().execute(city);
    }

    public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {

        // COMPLETED (6) Override the doInBackground method to perform your network requests
        @Override
        protected String[] doInBackground(String... params) {

            /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            String location = params[0];
            URL weatherRequestUrl = NetworkUtils.buildUrl(location);

            try {
                String jsonWeatherResponse = NetworkUtils
                        .getResponseFromHttpUrl(weatherRequestUrl);

                String[] simpleJsonWeatherData = OpenWeatherJsonUtils
                        .getSimpleWeatherStringsFromJson(getActivity().getApplicationContext(), jsonWeatherResponse);

                return simpleJsonWeatherData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        // COMPLETED (7) Override the onPostExecute method to display the results of the network request
        @Override
        protected void onPostExecute(String[] weatherData) {

            if (weatherData != null) {
                /*
                 * Iterate through the array and append the Strings to the TextView. The reason why we add
                 * the "\n\n\n" after the String is to give visual separation between each String in the
                 * TextView. Later, we'll learn about a better way to display lists of data.
                 */
                mWeatherTextView.setText("Temperature: " + weatherData[TEMPERATURE] + "C" + "\n");
                mWeatherTextView.append("Condition: " + weatherData[CONDITION]+ "\n");

                switch (weatherData[CONDITION]){
                    case "Thunderstorm":
                        condIcon.setImageResource(R.drawable.art_storm);
                        break;

                    case "Partially cloudy":
                        condIcon.setImageResource(R.drawable.art_light_clouds);
                        break;

                    case "Clouds":
                        condIcon.setImageResource(R.drawable.art_clouds);
                        break;

                    case "Clear":
                        condIcon.setImageResource(R.drawable.art_clear);
                        break;

                    case "Rain":
                        condIcon.setImageResource(R.drawable.art_rain);
                        break;

                    case "Drizzle":
                        condIcon.setImageResource(R.drawable.art_light_rain);
                        break;

                    default:
                        condIcon.setImageResource(R.drawable.art_fog);
                }


                /* ADD SWTICH CASE FOR WEATHER ICON
                * Make image view in xml, get the switch case here to change it
                *
                * switch */

            }
        }
    }

}