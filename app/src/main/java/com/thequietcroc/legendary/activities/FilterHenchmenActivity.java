package com.thequietcroc.legendary.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.custom.adapters.GameComponentRecyclerAdapter;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HenchmenEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.MastermindEntity;

import java.util.ArrayList;
import java.util.List;

import static com.thequietcroc.legendary.utilities.LiveDataUtil.observeOnce;

public class FilterHenchmenActivity extends FilterActivity {

    GameComponentRecyclerAdapter<HenchmenEntity> gameComponentRecyclerAdapter = new GameComponentRecyclerAdapter<>(new ArrayList<>());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != getActionBar()) {
            getActionBar().setTitle(R.string.activityFilterHenchmen);
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(R.string.activityFilterHenchmen);
        }

        filterRecyclerView.setAdapter(gameComponentRecyclerAdapter);

        gameComponentRecyclerAdapter.setDbUpdateConsumer(henchmenEntity ->
                AsyncTask.execute(() -> {
                    if (!henchmenEntity.isEnabled()) {
                        final List<MastermindEntity> mastermindEntityList = db.mastermindDao().findAllByAlwaysLeadsHenchmenId(henchmenEntity.getId());
                        mastermindEntityList.stream().forEach(mastermindEntity -> mastermindEntity.setEnabled(henchmenEntity.isEnabled()));

                        db.mastermindDao().update(mastermindEntityList);
                    }

                    db.henchmenDao().update(henchmenEntity);
                })
        );

        // TODO: figure out what to do when an always leads henchmen gets deleted
        gameComponentRecyclerAdapter.setDbDeleteConsumer(henchmenEntity ->
                AsyncTask.execute(() ->
                        db.henchmenDao().delete(henchmenEntity))
        );

        observeOnce(this,
                db.henchmenDao().getAllAsync(),
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

    private void observerActions(final List<HenchmenEntity> results) {
        gameComponentRecyclerAdapter.getComponentEntityList().addAll(results);
        gameComponentRecyclerAdapter.notifyDataSetChanged();
    }

}
