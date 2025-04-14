package app.webview.errorpage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    private DrawerLayout drawerLayout;
    private ListView listView;
    private TextView emptyText;
    private List<ExcelTopic> currentTopics;
    private TopicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Set up NavigationView
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Set up ListView
        listView = findViewById(R.id.list_view);
        emptyText = findViewById(R.id.empty_text);
        listView.setEmptyView(emptyText);

        // Load all topics initially
        loadTopics("all");

        // Set ListView item click listener
        listView.setOnItemClickListener((adapterView, view, i, l) -> TopicAdapter.flipCard(MainActivity.this, view, view, () -> {
            Intent intent = new Intent(MainActivity.this, TopicDetailActivity.class);
            intent.putExtra("url", currentTopics.get(i).getDetailUrl());
            startActivity(intent);
        }));
    }

    private void loadTopics(String category) {
        if (category.equals("all")) {
            loadTopicsFromJson("all");
            getSupportActionBar().setTitle("All Excel Topics");
        } else {
            loadTopicsFromJson(category);
            getSupportActionBar().setTitle(category + " Topics");
        }

        if (adapter == null) {
            adapter = new TopicAdapter(this, currentTopics);
            listView.setAdapter(adapter);
        } else {
            adapter.clear();
            adapter.addAll(currentTopics);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        // Handle navigation view item clicks
        if (id == R.id.nav_all) {
            loadTopics("all");
        } else if (id == R.id.nav_basics) {
            loadTopics("Basics");
        } else if (id == R.id.nav_functions) {
            loadTopics("Functions");
        } else if (id == R.id.nav_data_analysis) {
            loadTopics("Data Analysis");
        } else if (id == R.id.nav_vba) {
            loadTopics("VBA");
        } else if (id == R.id.nav_examples) {
            loadTopics("Examples");
        }

        // Close drawer after item click
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void loadTopicsFromJson(String category) {
        try {
            currentTopics = new ArrayList<>();
            String jsonString = readJsonFromAsset("excel_topics.json");
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String category1 = jsonObject.getString("category");
                if (category.equals("all") || category.equals(category1)) {
                    ExcelTopic excelTopic = new ExcelTopic();
                    excelTopic.setId(jsonObject.getInt("id"));
                    excelTopic.setTitle(jsonObject.getString("title"));
                    excelTopic.setSubtitle(jsonObject.getString("subtitle"));
                    excelTopic.setDescription(jsonObject.getString("description"));
                    excelTopic.setCategory(category1);
                    excelTopic.setDetailUrl(jsonObject.getString("detailUrl"));
                    excelTopic.setColor(jsonObject.getString("color"));
                    currentTopics.add(excelTopic);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing JSON data", e);
        }
    }

    private String readJsonFromAsset(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream is = getAssets().open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            Log.e(TAG, "Error reading JSON file from assets", e);
        }
        return stringBuilder.toString();
    }
}