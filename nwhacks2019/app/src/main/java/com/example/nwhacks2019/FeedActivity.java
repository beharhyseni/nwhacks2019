package com.example.nwhacks2019;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import org.json.JSONObject;

public class FeedActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public JSONObject CallServer() {
        JSONObject results = new JSONObject();
        try {
            ScrapeTask task = new ScrapeTask();
            task.execute();
            results = task.get();  //Add this
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return results;//Need to get back the result

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        JSONObject results = CallServer();
        mAdapter = new PostsAdapter(results, this);
        mRecyclerView.setAdapter(mAdapter);
    }
}

class ScrapeTask extends AsyncTask<Void, Void, JSONObject> {

    @Override
    protected JSONObject doInBackground(Void... params) {
        JSONObject results = new JSONObject();
        try {
            Data d = Data.getInstance();
            String query = d.getTextView();
            Scraper scraper = new Scraper();
            query = "lysol wipes";
            results = scraper.runScraper(query, 3);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}