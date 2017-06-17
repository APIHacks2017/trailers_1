package com.trailit.trailit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout waypointView;
    LinearLayout exploreView;
    ImageButton waypointBtn;
    ImageButton exploreBtn;
    public String APP_NAME="trails.it";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        waypointView= (LinearLayout) findViewById(R.id.waypoint_view);
        exploreView=(LinearLayout) findViewById(R.id.explore_view);
        waypointBtn=(ImageButton) findViewById(R.id.waypoint_btn);
        exploreBtn=(ImageButton) findViewById(R.id.explore_btn);

        waypointView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoWayform();
            }
        });

        exploreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               gotoExploreform();
            }
        });

        waypointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoWayform();
            }
        });

        exploreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoExploreform();
            }
        });

    }

    void gotoWayform(){
        Log.i(APP_NAME,"goto waypoints form");
        Intent intent = new Intent(MainActivity.this,FormActivity.class);
        intent.putExtra("waypoints",true);
        startActivity(intent);
    }

    void gotoExploreform(){
        Log.i(APP_NAME,"goto explore form");
        Intent intent = new Intent(MainActivity.this,FormActivity.class);
        intent.putExtra("waypoints",false);
        startActivity(intent);
    }
}
