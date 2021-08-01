package com.thequietcroc.legendary.activities.filters;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.activities.info.MastermindInfoActivity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.MastermindEntity;
import com.thequietcroc.legendary.models.gamecomponents.cards.Mastermind;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.List;
import java.util.stream.Collectors;

public class FilterMastermindActivity extends FilterActivity<Mastermind> {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.filterMasterminds));

        gameComponentRecyclerAdapter.setInfoButtonOnClickConsumer(vh -> {
            startNewActivity(getGameComponent(vh), MastermindInfoActivity.class);
        });

        new DbAsyncTask(() -> {
            final List<MastermindEntity> results = db.mastermindDao().getAll();

            new Handler(Looper.getMainLooper()).post(() -> {
                gameComponentRecyclerAdapter.getComponentList().addAll(results
                        .stream()
                        .map(MastermindEntity::toModel)
                        .collect(Collectors.toList()));

                gameComponentRecyclerAdapter.notifyDataSetChanged();
            });
        });
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuActionAddNew: {
                startNewActivity(new Mastermind(getString(R.string.customMastermind)),
                        MastermindInfoActivity.class);
            } break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }

        return true;
    }
}
