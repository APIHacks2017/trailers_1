package com.trailit.trailit;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormActivity extends AppCompatActivity {

    Boolean waypoints;
    public String APP_NAME="trails.it";
    String startTime,endTime;
    EditText startTimeED,endTimeED;
    Button pickBtn,createPlan;
    LinearLayout placeView;
    TextView placeText;
    Place pickedPlace=null;
    String[] pref;
    CheckBox prefFood;
    CheckBox prefExplore;
    ScrollView formView;
    RecyclerView pointsRecycler;
    pointsAdapter pAdapter;
    List<point> pList;
    ProgressDialog prog_loading;


    int PLACE_AUTOCOMPLETE_REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        getSupportActionBar().setTitle("Enter choice");
        waypoints=getIntent().getExtras().getBoolean("wayponts");
        pList=new ArrayList<>();
       // pList.add(new point("akr","akr",3.14,3.45));

        prog_loading=new ProgressDialog(this);
        prog_loading.setMessage("Creating...");
        prog_loading.setCancelable(false);


        startTimeED=(EditText) findViewById(R.id.starting_time);
        endTimeED=(EditText) findViewById(R.id.ending_time);
        pickBtn=(Button) findViewById(R.id.pick_place_btn);
        placeView=(LinearLayout) findViewById(R.id.place_view);
        placeText=new TextView(this);
        createPlan=(Button) findViewById(R.id.create_plan_btn);
        prefFood=(CheckBox) findViewById(R.id.check_food);
        prefExplore=(CheckBox) findViewById(R.id.check_explore);
        formView=(ScrollView) findViewById(R.id.form_view);
        pointsRecycler = (RecyclerView) findViewById(R.id.points_recycler);
        pAdapter = new pointsAdapter(pList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        pointsRecycler.setLayoutManager(mLayoutManager);
        pointsRecycler.setItemAnimator(new DefaultItemAnimator());
        pointsRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        pointsRecycler.setAdapter(pAdapter);

        startTimeED.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(FormActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startTimeED.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        endTimeED.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(FormActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        endTimeED.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        pickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   findPlace(view);
            }
        });

        createPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateAndRequest();
            }
        });

        Log.i(APP_NAME,"waypoints : "+waypoints);

    }

    void validateAndRequest(){

        request req=new request();
        req.setStTime(startTimeED.getText().toString());
        req.setEdTime(endTimeED.getText().toString());

        if(prefFood.isChecked()&&prefExplore.isChecked()){
            pref=new String[]{"food","explore"};
        }else if(prefExplore.isChecked()){
            pref=new String[]{"explore"};
        }else{
            pref=new String[]{"food"};
        }
        req.setPointType(pref);

        req.setLat(pickedPlace.getLatLng().latitude);
        req.setLon(pickedPlace.getLatLng().longitude);

        prog_loading.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<point>> call = apiService.getResult(req);
        try{
            call.enqueue(new Callback<List<point>>() {

                @Override
                public void onResponse(Call<List<point>> call, Response<List<point>> response) {
                    int statusCode = response.code();
                    List<point> res = response.body();
                    prog_loading.dismiss();
                    Log.i(APP_NAME,res.get(0).getpName());
                    pAdapter.updateList(res);
                    getSupportActionBar().setTitle("Explorations");
                    formView.setVisibility(View.GONE);
                    pointsRecycler.setVisibility(View.VISIBLE);

                }

                @Override
                public void onFailure(Call<List<point>> call, Throwable t) {

                    prog_loading.dismiss();
                    Toast.makeText(FormActivity.this, "Error in registering..Retry", Toast.LENGTH_LONG).show();

                }
            });
        }
        catch (Exception e){
           Log.i(APP_NAME,e.getStackTrace().toString());
        }

    }

    public void findPlace(View view) {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    // A place has been received; use requestCode to track the request.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(APP_NAME, "Place: " + place.getName());
                pickedPlace=place;
                placeText.setText(place.getName());
                placeView.addView(placeText);
                pickBtn.setVisibility(View.GONE);

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(APP_NAME, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

}
