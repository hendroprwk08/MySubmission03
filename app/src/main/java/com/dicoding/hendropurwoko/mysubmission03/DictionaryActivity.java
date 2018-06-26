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
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class DictionaryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context context;
    RecyclerView rvList;
    DictionaryAdapter dictionaryAdapter;
    DictionaryHelper dictionaryHelper;
    ProgressDialog progressDialog;

    public ArrayList<DictionaryModel> dictionaryModels = new ArrayList<>();

    EditText edtSearch;
    NavigationView nView;
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

        //defauit check navigation
        nView = (NavigationView) findViewById(R.id.nav_view);
        nView.getMenu().getItem(0).setChecked(true);

        //isEng = true;

        edtSearch = (EditText) findViewById(R.id.edt_search);
        edtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                word = edtSearch.getText().toString().trim();

                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if (TextUtils.isEmpty(word)) {
                        dictionaryAdapter.clear();
                        rvList.setAdapter(dictionaryAdapter);
                        Toast.makeText(DictionaryActivity.this, "Tak ada pencarian", Toast.LENGTH_SHORT).show();
                    } else {
                        new LoadDictionary().execute(word);
                    }
                }
                return true;
            }
        });
        /*
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    dictionaryAdapter.clear();
                    rvList.setAdapter(dictionaryAdapter);
                    Toast.makeText(DictionaryActivity.this, "Tak ada pencarian", Toast.LENGTH_SHORT).show();
                } else {
                    word = s.toString().trim();
                    new LoadDictionary().execute(s.toString().trim());
                }
            }
        });
        */

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

    private class LoadDictionary extends AsyncTask<String, Void, ArrayList<DictionaryModel>> {
        ArrayList<DictionaryModel> dictionaryModels = new ArrayList<>();
        ProgressDialog progressDialog;

        @Override
        protected ArrayList<DictionaryModel> doInBackground(String... strings) {
            DictionaryHelper dictionaryHelper = new DictionaryHelper(context);
            dictionaryHelper.open();
            dictionaryModels = dictionaryHelper.getWord(strings[0], true);
            dictionaryHelper.close();

            return dictionaryModels;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(DictionaryActivity.this);
            progressDialog.setMessage(getString(R.string.please_wait));//ambil resource string
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(ArrayList<DictionaryModel> aVoid) {
            super.onPostExecute(aVoid);

            dictionaryAdapter.addItem(dictionaryModels);
            dictionaryAdapter.getListData();
            rvList.setAdapter(dictionaryAdapter);

            if (progressDialog.isShowing())
                progressDialog.dismiss();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        //make this method blank
        if (item.getItemId() == R.id.nav_eng_ina) {
            isEng = true;
        }

        if (item.getItemId() == R.id.nav_ina_eng) {
            isEng = false;
        }

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