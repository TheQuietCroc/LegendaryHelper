package com.thequietcroc.legendary.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.custom.adapters.GameComponentRecyclerAdapter;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.MastermindEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.VillainsEntity;

import java.util.ArrayList;
import java.util.List;

import static com.thequietcroc.legendary.utilities.LiveDataUtil.observeOnce;

public class FilterVillainsActivity extends FilterActivity {

    final GameComponentRecyclerAdapter<VillainsEntity> gameComponentRecyclerAdapter = new GameComponentRecyclerAdapter<>(new ArrayList<>());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != getActionBar()) {
            getActionBar().setTitle(R.string.activityFilterVillains);
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(R.string.activityFilterVillains);
        }

        filterRecyclerView.setAdapter(gameComponentRecyclerAdapter);

        gameComponentRecyclerAdapter.setDbUpdateConsumer(villainsEntity ->
                AsyncTask.execute(() -> {
                    if (!villainsEntity.isEnabled()) {
                        final List<MastermindEntity> mastermindEntityList = db.mastermindDao().findAllByAlwaysLeadsVillainId(villainsEntity.getId());
                        mastermindEntityList.stream().forEach(mastermindEntity -> mastermindEntity.setEnabled(villainsEntity.isEnabled()));

                        db.mastermindDao().update(mastermindEntityList);
                    }

                    db.villainsDao().update(villainsEntity);
                })
        );

        // TODO: figure out what to do when an always leads villain gets deleted
        gameComponentRecyclerAdapter.setDbDeleteConsumer(villainsEntity ->
                AsyncTask.execute(() ->
                        db.villainsDao().delete(villainsEntity))
        );

        observeOnce(this,
                db.villainsDao().getAllAsync(),
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

    private void observerActions(final List<VillainsEntity> results) {
        gameComponentRecyclerAdapter.getComponentEntityList().addAll(results);
        gameComponentRecyclerAdapter.notifyDataSetChanged();
    }

}
