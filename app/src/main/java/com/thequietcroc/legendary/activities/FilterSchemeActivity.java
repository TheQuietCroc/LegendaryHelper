package com.thequietcroc.legendary.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.custom.adapters.GameComponentRecyclerAdapter;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.SchemeEntity;

import java.util.ArrayList;
import java.util.List;

import static com.thequietcroc.legendary.utilities.LiveDataUtil.observeOnce;

public class FilterSchemeActivity extends FilterActivity {

    GameComponentRecyclerAdapter<SchemeEntity> gameComponentRecyclerAdapter = new GameComponentRecyclerAdapter<>(new ArrayList<>());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != getActionBar()) {
            getActionBar().setTitle(R.string.activityFilterScheme);
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(R.string.activityFilterScheme);
        }

        filterRecyclerView.setAdapter(gameComponentRecyclerAdapter);

        gameComponentRecyclerAdapter.setDbUpdateConsumer(schemeEntity ->
                AsyncTask.execute(() ->
                        db.schemeDao().update(schemeEntity))
        );

        gameComponentRecyclerAdapter.setDbDeleteConsumer(schemeEntity ->
                AsyncTask.execute(() ->
                        db.schemeDao().delete(schemeEntity))
        );

        observeOnce(this,
                db.schemeDao().getAllAsync(),
                this::observerActions);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuActionAddNew: {

            }
            break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }

        return true;
    }

    private void observerActions(final List<SchemeEntity> results) {
        gameComponentRecyclerAdapter.getComponentEntityList().addAll(results);
        gameComponentRecyclerAdapter.notifyDataSetChanged();
    }

}
