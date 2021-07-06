package com.thequietcroc.legendary.activities.filters;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.database.LegendaryDatabase;

public abstract class FilterActivity extends AppCompatActivity {

    public static final String COMPONENT_EXTRA = "component";

    final LegendaryDatabase db = LegendaryDatabase.getInstance();
    RecyclerView filterRecyclerView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        filterRecyclerView = findViewById(R.id.filterRecyclerView);
        filterRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_activity_menu_actions, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return true;
    }
}
