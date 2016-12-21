package org.esiea.jachimski.mybeers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;



public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.rev_biere);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        mAdapter = new EltsAdapter(getBiersFromFile());

        mRecyclerView.setAdapter(mAdapter);

    }
    private class EltsAdapter extends RecyclerView.Adapter<EltHolder> {

        private JSONArray elts = null;

        public EltsAdapter(JSONArray autrechose) {
            this.elts = autrechose;
        }

        @Override
        public EltHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_custom_display, parent, false);

            EltHolder mHolder = new EltHolder(v);
            return mHolder;
        }

        @Override
        public void onBindViewHolder(EltHolder holder, int position) {
            JSONObject element;
            String nom;
            try {
                element = elts.getJSONObject(position);
                nom = element.getString("name");
                holder.name.setText(nom);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void setNewElts(JSONArray jsonarray) {
            this.elts = jsonarray;
            mAdapter.notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            int number = elts.length();
            Log.d("TAG", "Le nombre d'items est :" + number);
            return number;
        }
    }

    public JSONArray getBiersFromFile(){
        try {
            InputStream is = new FileInputStream(getCacheDir() + "/" + "bieres.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONArray(new String(buffer, "UTF-8"));
        }catch (IOException e) {
            e.printStackTrace();
            return new JSONArray();
        }catch (JSONException e){
            e.printStackTrace();
            return new JSONArray();
        }
    }


}