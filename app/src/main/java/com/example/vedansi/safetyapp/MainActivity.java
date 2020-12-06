package com.example.vedansi.safetyapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback {

    GoogleMap map;
    private String lat;
    private String lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Toolbar tools = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tools);
        FloatingActionButton alert = (FloatingActionButton) findViewById(R.id.fab);
        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaPlayer ring = MediaPlayer.create(MainActivity.this, R.raw.alert);
                ring.start();
            }
        });
        DrawerLayout nav = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, nav, tools, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        nav.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView view = (NavigationView) findViewById(R.id.nav_view);
        view.setNavigationItemSelectedListener(this);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout press = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (press.isDrawerOpen(GravityCompat.START)) {
            press.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        final int pic_id = 123;
        final int REQUEST_VIDEO_CAPTURE = 1;

        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera, pic_id);
        } else if (id == R.id.nav_gallery) {
            Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
            gallery.addCategory(Intent.CATEGORY_OPENABLE);
            gallery.setType("image/*");
            startActivity(gallery);
        } else if (id == R.id.nav_record) {
            Intent video = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            if (video.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(video, REQUEST_VIDEO_CAPTURE);
            }
        } else if (id == R.id.nav_share) {
            String uri = "https://www.google.com/maps/?q=" + lat+ "," +lon ;
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(android.content.Intent.EXTRA_TEXT,  uri);
            startActivity(Intent.createChooser(share, " Sharing"));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        LatLng Map=new LatLng(33.753746, -84.38633);
        map.addMarker(new MarkerOptions().position(Map).title("Atlanta"));
        map.moveCamera(CameraUpdateFactory.newLatLng(Map));
    }
}
