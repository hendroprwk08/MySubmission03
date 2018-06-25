package com.dicoding.hendropurwoko.mysubmission03;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class DictionaryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView rvList;
    DictionaryAdapter dictionaryAdapter;
    DictionaryHelper dictionaryHelper;
    public ArrayList<DictionaryModel> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvList = (RecyclerView) findViewById(R.id.rv_list);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                startActivity(new Intent(DictionaryActivity.this, SearchActivity.class));

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //load recyclerview
        //set Adapter
        dictionaryAdapter = new DictionaryAdapter(this);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(dictionaryAdapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Ambil semua data mahasiswa di database
        dictionaryHelper = new DictionaryHelper(this);
        dictionaryHelper.open();
        ArrayList<DictionaryModel> dictionaryModels = dictionaryHelper.query(true);
        dictionaryHelper.close();

        /*
        //test
        DictionaryModel dictionaryModels = new DictionaryModel();
        dictionaryModels.setWord("SASDA");
        dictionaryModels.setDescription("DGDGDFGDGHDFHDFG");
        dictionaryModels.add(dictionaryModels);
        */

        dictionaryAdapter.addItem(dictionaryModels);
        dictionaryAdapter.getListData();
        //dictionaryAdapter.addItem(dictionaryModels);

        //getRecyclerViewData("",true);
        //dictionaryAdapter.getListData();
        rvList.setAdapter(dictionaryAdapter);

        //getRecyclerViewData("",true);

        //default fragment
        //displaySelectedScreen(R.id.nav_eng_ina);
    }

    private void getRecyclerViewData(String find, Boolean isEng) {
        dictionaryHelper = new DictionaryHelper(this);
        dictionaryHelper.open();
        try {
            if (isEng) {
                if (find.isEmpty()){
                    list = dictionaryHelper.query(true);
                }else{
                    list = dictionaryHelper.query(true);
                }
            } else {
                if (find.isEmpty()){
                    list = dictionaryHelper.query(false);
                }else{
                    list = dictionaryHelper.query(false);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dictionaryHelper.close();
        dictionaryAdapter.addItem(list);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        /*getMenuInflater().inflate(R.menu.dictionary, menu);
        return true;*/
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        //make this method blank
        return true;
    }

    private void displaySelectedScreen(int itemId) {

        Fragment fragment = null;

        if (itemId == R.id.nav_eng_ina) {
            //fragment = new EngInaFragment();
        } else if (itemId == R.id.nav_ina_eng) {
            //fragment = new InaEngFragment();
        }

        /*
        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_content, fragment);
            ft.commit();
        }
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
}
