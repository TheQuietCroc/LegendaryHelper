package com.thequietcroc.legendary.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.custom.adapters.GameComponentRecyclerAdapter;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HenchmenEntity;
import com.thequietcroc.legendary.models.gamecomponents.cards.Henchmen;
import com.thequietcroc.legendary.models.gamecomponents.cards.Mastermind;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.thequietcroc.legendary.utilities.LiveDataUtil.observeOnce;

public class FilterHenchmenActivity extends FilterActivity {

    final GameComponentRecyclerAdapter<Henchmen> gameComponentRecyclerAdapter = new GameComponentRecyclerAdapter<>(new ArrayList<>());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != getActionBar()) {
            getActionBar().setTitle(R.string.activityFilterHenchmen);
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(R.string.activityFilterHenchmen);
        }

        filterRecyclerView.setAdapter(gameComponentRecyclerAdapter);

        gameComponentRecyclerAdapter.setDbUpdateConsumer(henchmen ->
                AsyncTask.execute(() -> {
                    if (!henchmen.isEnabled()) {
                        final List<Mastermind> mastermindList = henchmen.getMastermindLeaderList();
                        mastermindList.stream().forEach(mastermind -> mastermind.setEnabled(henchmen.isEnabled()));

                        db.mastermindDao().update(mastermindList
                                .stream()
                                .map(Mastermind::toEntity)
                                .collect(Collectors.toList()));
                    }

                    db.henchmenDao().update(henchmen.toEntity());
                })
        );

        // TODO: figure out what to do when an always leads henchmen gets deleted
        gameComponentRecyclerAdapter.setDbDeleteConsumer(henchmen ->
                AsyncTask.execute(() ->
                        db.henchmenDao().delete(henchmen.toEntity()))
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
        gameComponentRecyclerAdapter.getComponentList().addAll(results
                .stream()
                .map(HenchmenEntity::toModel)
                .collect(Collectors.toList()));
        gameComponentRecyclerAdapter.notifyDataSetChanged();
    }

}
