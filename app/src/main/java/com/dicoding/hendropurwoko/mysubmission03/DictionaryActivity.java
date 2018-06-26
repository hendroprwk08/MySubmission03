package com.dicoding.hendropurwoko.mysubmission03;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;

public class DictionaryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context context;
    RecyclerView rvList;
    DictionaryAdapter dictionaryAdapter;
    DictionaryHelper dictionaryHelper;

    public ArrayList<DictionaryModel> dictionaryModels = new ArrayList<>();

    EditText edtSearch;
    String word;
    Boolean isEng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        context = getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvList = (RecyclerView) findViewById(R.id.rv_list);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Set RecyclerView
        dictionaryAdapter = new DictionaryAdapter(this);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(dictionaryAdapter);

        isEng = true;

        edtSearch = (EditText) findViewById(R.id.edt_search);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                word = s.toString().trim();
                new LoadDictionary().execute(word);
            }
        });

        /*
        // Ambil semua data mahasiswa di database
        dictionaryHelper = new DictionaryHelper(this);
        dictionaryHelper.open();
        ArrayList<DictionaryModel> dictionaryModels = dictionaryHelper.query(true);
        dictionaryHelper.close();

        //test
        DictionaryModel dictionaryModels = new DictionaryModel();
        dictionaryModels.setWord("SASDA");
        dictionaryModels.setDescription("DGDGDFGDGHDFHDFG");
        dictionaryModels.add(dictionaryModels);

        dictionaryAdapter.addItem(dictionaryModels);
        dictionaryAdapter.getListData();
        rvList.setAdapter(dictionaryAdapter);
        */
    }

    private void displayRecyclerView() {

    }

    private class LoadDictionary extends AsyncTask<String, Void, ArrayList<DictionaryModel>> {
        ProgressDialog progressDialog;

        @Override
        protected ArrayList<DictionaryModel> doInBackground(String... strings) {
            DictionaryHelper dictionaryHelper = new DictionaryHelper(context);
            dictionaryHelper.open();
            ArrayList<DictionaryModel> dictionaryModels = dictionaryHelper.getWord(word, true);
            dictionaryHelper.close();

            dictionaryAdapter.addItem(dictionaryModels);
            dictionaryAdapter.getListData();
            rvList.setAdapter(dictionaryAdapter);
            return dictionaryModels;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(getString(R.string.please_wait));//ambil resource string
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<DictionaryModel> aVoid) {
            super.onPostExecute(aVoid);

            if (progressDialog.isShowing())
                progressDialog.dismiss();

            displayRecyclerView();
        }
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
            isEng = true;
            //fragment = new EngInaFragment();
        } else if (itemId == R.id.nav_ina_eng) {
            isEng = false;
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