package com.thequietcroc.legendary.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.custom.adapters.GameComponentRecyclerAdapter;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HenchmenEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.MastermindEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.VillainsEntity;

import java.util.ArrayList;
import java.util.List;

import static com.thequietcroc.legendary.utilities.LiveDataUtil.observeOnce;

public class FilterMastermindActivity extends FilterActivity {

    final GameComponentRecyclerAdapter<MastermindEntity> gameComponentRecyclerAdapter = new GameComponentRecyclerAdapter<>(new ArrayList<>());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != getActionBar()) {
            getActionBar().setTitle(R.string.activityFilterMastermind);
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(R.string.activityFilterMastermind);
        }

        filterRecyclerView.setAdapter(gameComponentRecyclerAdapter);

        gameComponentRecyclerAdapter.setDbUpdateConsumer(mastermindEntity ->
                AsyncTask.execute(() -> {
                    if (mastermindEntity.isEnabled()) {
                        final VillainsEntity alwaysLeadsVillains = db.villainsDao()
                                .findByIdSync(mastermindEntity.getVillainId());
                        final HenchmenEntity alwaysLeadsHenchmen = db.henchmenDao()
                                .findByIdSync(mastermindEntity.getHenchmenId());

                        if (alwaysLeadsVillains.getId() != 0) {
                            alwaysLeadsVillains.setEnabled(mastermindEntity.isEnabled());

                            db.villainsDao().update(alwaysLeadsVillains);
                        }

                        if (alwaysLeadsHenchmen.getId() != 0) {
                            alwaysLeadsHenchmen.setEnabled(mastermindEntity.isEnabled());

                            db.henchmenDao().update(alwaysLeadsHenchmen);
                        }
                    }

                    db.mastermindDao().update(mastermindEntity);
                })
        );

        gameComponentRecyclerAdapter.setDbDeleteConsumer(mastermindEntity ->
                AsyncTask.execute(() ->
                        db.mastermindDao().delete(mastermindEntity))
        );

        observeOnce(this,
                db.mastermindDao().getAllAsync(),
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

    private void observerActions(final List<MastermindEntity> results) {
        gameComponentRecyclerAdapter.getComponentEntityList().addAll(results);
        gameComponentRecyclerAdapter.notifyDataSetChanged();
    }

}
