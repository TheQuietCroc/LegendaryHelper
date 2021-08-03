package com.thequietcroc.legendary.activities.filters;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.activities.info.SchemeInfoActivity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.SchemeEntity;
import com.thequietcroc.legendary.models.gamecomponents.GameSet;
import com.thequietcroc.legendary.models.gamecomponents.cards.Scheme;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.List;
import java.util.stream.Collectors;

public class FilterSchemeActivity extends FilterActivity<Scheme> {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.filterSchemes));

        gameComponentRecyclerAdapter.setCheckboxOnClickCosumer(vh -> {
            final boolean isChecked = vh.getGameComponentEnabledCheckbox().isChecked();
            final Scheme scheme = gameComponentRecyclerAdapter.getComponentList()
                    .get(vh.getAdapterPosition());

            new DbAsyncTask(() -> {
                scheme.setEnabled(isChecked);
                scheme.dbSave();

                final GameSet gameSet = scheme.getGameSet();
                    if (isChecked) {
                        gameSet.setEnabled(true);
                    } else {
                        gameSet.setEnabled(gameSet.areAllItemsEnabled());
                    }

                    gameSet.dbSave();
            });
        });
        gameComponentRecyclerAdapter.setInfoButtonOnClickConsumer(vh
                -> startNewActivity(getGameComponent(vh), SchemeInfoActivity.class));

        new DbAsyncTask(() -> {
            final List<SchemeEntity> results = db.schemeDao().getAll();

            new Handler(Looper.getMainLooper()).post(() -> {
                gameComponentRecyclerAdapter.getComponentList().addAll(results
                        .stream()
                        .map(SchemeEntity::toModel)
                        .collect(Collectors.toList()));

                gameComponentRecyclerAdapter.notifyDataSetChanged();
            });
        });
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuActionAddNew: {
                startNewActivity(new Scheme(getString(R.string.customScheme)),
                        SchemeInfoActivity.class);
            } break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }

        return true;
    }
}
