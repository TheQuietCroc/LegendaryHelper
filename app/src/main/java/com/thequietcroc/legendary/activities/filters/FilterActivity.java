package com.thequietcroc.legendary.activities.filters;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.custom.adapters.GameComponentRecyclerAdapter;
import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.models.gamecomponents.BaseGameComponent;

import java.util.ArrayList;

public abstract class FilterActivity<T extends BaseGameComponent> extends AppCompatActivity {

    public static final String COMPONENT_EXTRA = "component";

    final LegendaryDatabase db = LegendaryDatabase.getInstance();
    final GameComponentRecyclerAdapter<T> gameComponentRecyclerAdapter = new GameComponentRecyclerAdapter<>(new ArrayList<>());
    RecyclerView filterRecyclerView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        filterRecyclerView = findViewById(R.id.filterRecyclerView);
        filterRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        filterRecyclerView.setAdapter(gameComponentRecyclerAdapter);

        gameComponentRecyclerAdapter.setCheckboxOnClickCosumer(vh -> {
            final T component = getGameComponent(vh);

            component.setEnabled(vh.getGameComponentEnabledCheckbox().isChecked());
            component.dbSave();
        });
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

    protected void startNewActivity(final T component, final Class<?> activityClass) {
        final Intent intent = new Intent(this, activityClass);

        intent.putExtra(COMPONENT_EXTRA, component);

        startActivity(intent);
    }

    protected void setTitle(final String title) {
        if (null != getActionBar()) {
            getActionBar().setTitle(title);
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(title);
        }
    }

    protected T getGameComponent(final GameComponentRecyclerAdapter.ViewHolder vh) {
        return gameComponentRecyclerAdapter.getComponentList().get(vh.getAdapterPosition());
    }
}
