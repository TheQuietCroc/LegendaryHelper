package com.thequietcroc.legendary.activities.filters;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.activities.info.VillainsInfoActivity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.VillainsEntity;
import com.thequietcroc.legendary.models.gamecomponents.GameSet;
import com.thequietcroc.legendary.models.gamecomponents.cards.Mastermind;
import com.thequietcroc.legendary.models.gamecomponents.cards.Villains;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.List;
import java.util.stream.Collectors;

public class FilterVillainsActivity extends FilterActivity<Villains> {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.filterVillains));

        gameComponentRecyclerAdapter.setCheckboxOnClickCosumer(vh -> {
            final boolean isChecked = vh.getGameComponentEnabledCheckbox().isChecked();
            final Villains villains = gameComponentRecyclerAdapter.getComponentList()
                    .get(vh.getAdapterPosition());

            new DbAsyncTask(() -> {
                villains.setEnabled(isChecked);
                villains.dbSave();

                final GameSet gameSet = villains.getGameSet();

                if (isChecked) {
                    gameSet.setEnabled(true);
                } else {
                    final Mastermind mastermindLeader = villains.getMastermindLeader();

                    if (mastermindLeader.getId() > 0) {
                        mastermindLeader.setEnabled(false);
                        mastermindLeader.dbSave();
                    }

                    gameSet.setEnabled(gameSet.areAllItemsEnabled());
                }

                gameSet.dbSave();
            });
        });
        gameComponentRecyclerAdapter.setInfoButtonOnClickConsumer(vh
                -> startNewActivity(getGameComponent(vh), VillainsInfoActivity.class));

        new DbAsyncTask(() -> {
            final List<VillainsEntity> results = db.villainsDao().getAll();

            new Handler(Looper.getMainLooper()).post(() -> {
                gameComponentRecyclerAdapter.getComponentList().addAll(results
                        .stream()
                        .map(VillainsEntity::toModel)
                        .collect(Collectors.toList()));

                gameComponentRecyclerAdapter.notifyDataSetChanged();
            });
        });
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuActionAddNew: {
              startNewActivity(new Villains(getString(R.string.customVillains)),
                      VillainsInfoActivity.class);
            } break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }

        return true;
    }
}
