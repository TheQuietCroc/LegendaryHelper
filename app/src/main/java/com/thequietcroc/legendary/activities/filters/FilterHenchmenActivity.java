package com.thequietcroc.legendary.activities.filters;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.activities.info.HenchmenInfoActivity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HenchmenEntity;
import com.thequietcroc.legendary.models.gamecomponents.GameSet;
import com.thequietcroc.legendary.models.gamecomponents.cards.Henchmen;
import com.thequietcroc.legendary.models.gamecomponents.cards.Mastermind;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.List;
import java.util.stream.Collectors;

public class FilterHenchmenActivity extends FilterActivity<Henchmen> {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.filterHenchmen));

        gameComponentRecyclerAdapter.setCheckboxOnClickCosumer(vh -> {
            final boolean isChecked = vh.getGameComponentEnabledCheckbox().isChecked();
            final Henchmen henchmen = gameComponentRecyclerAdapter.getComponentList()
                    .get(vh.getAdapterPosition());

            new DbAsyncTask(() -> {
                henchmen.setEnabled(isChecked);
                henchmen.dbSave();

                final GameSet gameSet = henchmen.getGameSet();

                if (isChecked)  {
                    gameSet.setEnabled(true);
                } else {
                    final Mastermind mastermindLeader = henchmen.getMastermindLeader();

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
                -> startNewActivity(getGameComponent(vh), HenchmenInfoActivity.class));

        new DbAsyncTask(() -> {
            final List<HenchmenEntity> results = db.henchmenDao().getAll();

            new Handler(Looper.getMainLooper()).post(() -> {
                gameComponentRecyclerAdapter.getComponentList().addAll(results
                        .stream()
                        .map(HenchmenEntity::toModel)
                        .collect(Collectors.toList()));

                gameComponentRecyclerAdapter.notifyDataSetChanged();
            });
        });
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuActionAddNew: {
                startNewActivity(new Henchmen(getString(R.string.customHenchmen)),
                        HenchmenInfoActivity.class);
            } break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }

        return true;
    }

}
