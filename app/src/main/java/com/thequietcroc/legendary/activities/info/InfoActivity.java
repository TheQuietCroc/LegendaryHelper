package com.thequietcroc.legendary.activities.info;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.database.LegendaryDatabase;

public abstract class InfoActivity extends AppCompatActivity {

    final LegendaryDatabase db = LegendaryDatabase.getInstance();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component_info);

        if (null != getActionBar()) {
            setTitle();
        } else if (null != getSupportActionBar()) {
            setTitle();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.info_activity_menu_actions, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuActionDelete: {
                // TODO: Implement in FILTER-9
            } break;
            default:
                NavUtils.navigateUpFromSameTask(this);
            break;
        }

        return true;
    }

    protected abstract void setTitle();
}