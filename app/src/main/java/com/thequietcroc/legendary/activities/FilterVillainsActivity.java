package com.thequietcroc.legendary.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.custom.adapters.GameComponentRecyclerAdapter;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.VillainsEntity;
import com.thequietcroc.legendary.models.gamecomponents.cards.Mastermind;
import com.thequietcroc.legendary.models.gamecomponents.cards.Villains;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.thequietcroc.legendary.utilities.LiveDataUtil.observeOnce;

public class FilterVillainsActivity extends FilterActivity {

    final GameComponentRecyclerAdapter<Villains> gameComponentRecyclerAdapter = new GameComponentRecyclerAdapter<>(new ArrayList<>());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != getActionBar()) {
            getActionBar().setTitle(R.string.activityFilterVillains);
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(R.string.activityFilterVillains);
        }

        filterRecyclerView.setAdapter(gameComponentRecyclerAdapter);

        gameComponentRecyclerAdapter.setDbUpdateConsumer(villains ->
                AsyncTask.execute(() -> {
                    if (!villains.isEnabled()) {
                        final List<Mastermind> mastermindList = villains.getMastermindLeaderList();
                        mastermindList.stream().forEach(mastermind -> mastermind.setEnabled(villains.isEnabled()));

                        db.mastermindDao().update(mastermindList
                                .stream()
                                .map(Mastermind::toEntity)
                                .collect(Collectors.toList()));
                    }

                    db.villainsDao().update(villains.toEntity());
                })
        );

        // TODO: figure out what to do when an always leads villain gets deleted
        gameComponentRecyclerAdapter.setDbDeleteConsumer(villains ->
                AsyncTask.execute(() ->
                        db.villainsDao().delete(villains.toEntity()))
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
        gameComponentRecyclerAdapter.getComponentEntityList().addAll(results
                .stream()
                .map(VillainsEntity::toModel)
                .collect(Collectors.toList()));
        gameComponentRecyclerAdapter.notifyDataSetChanged();
    }

}
