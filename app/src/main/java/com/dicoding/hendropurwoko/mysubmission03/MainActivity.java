package com.dicoding.hendropurwoko.mysubmission03;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    DictionaryHelper dictionaryHelper;
    public ArrayList<DictionaryModel> dictionaryList;
    public ProgressDialog progressDialog;

    AppPreference appPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appPreference = new AppPreference(MainActivity.this);
        dictionaryHelper = new DictionaryHelper(MainActivity.this);
        if(appPreference.getFirstRun()) {
            new LoadData().execute();
        }else{
            startActivity(new Intent(MainActivity.this, DictionaryActivity.class));
        }

    }

    private class LoadData extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage(getString(R.string.please_wait));//ambil resource string
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (progressDialog.isShowing())
                progressDialog.dismiss();

            startActivity(new Intent(MainActivity.this, DictionaryActivity.class));
            finish();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            ArrayList<DictionaryModel> engList = loadRaw(R.raw.english_indonesia);
            ArrayList<DictionaryModel> inaList = loadRaw(R.raw.indonesia_english);

            //open
            dictionaryHelper.open();
            dictionaryHelper.beginTransaction();

            try {
                dictionaryHelper.insertTransaction(engList, true);
                dictionaryHelper.insertTransaction(inaList, false);

            } catch (Exception e) {
                e.printStackTrace();
            }

            dictionaryHelper.setTransactionSuccess();
            dictionaryHelper.endTransaction();
            dictionaryHelper.close();

            appPreference.setFirstRun(false);

            return null;
        }
    }

    private ArrayList<DictionaryModel> loadRaw(int data){ //output array list
        ArrayList<DictionaryModel> dictionaryList = new ArrayList<>();
        String line = null;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(data);
            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;

            do {
                line = reader.readLine();
                String[] splitString = line.split("\t");

                DictionaryModel dictionaryModel;
                dictionaryModel = new DictionaryModel(splitString[0], splitString[1]);
                dictionaryList.add(dictionaryModel);
                count ++;
            } while (line != null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dictionaryList;
    }
}
