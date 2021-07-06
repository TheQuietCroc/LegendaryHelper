package com.thequietcroc.legendary.activities.filters;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.activities.info.HenchmenInfoActivity;
import com.thequietcroc.legendary.custom.adapters.GameComponentRecyclerAdapter;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HenchmenEntity;
import com.thequietcroc.legendary.models.gamecomponents.cards.Henchmen;
import com.thequietcroc.legendary.models.gamecomponents.cards.Mastermind;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.thequietcroc.legendary.utilities.LiveDataUtil.observeOnce;

public class FilterHenchmenActivity extends FilterActivity {

    final GameComponentRecyclerAdapter<Henchmen> gameComponentRecyclerAdapter = new GameComponentRecyclerAdapter<>(new ArrayList<>());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String title = String.format("%s %s", getString(R.string.filter), getString(R.string.henchmen));

        if (null != getActionBar()) {
            getActionBar().setTitle(title);
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(title);
        }

        filterRecyclerView.setAdapter(gameComponentRecyclerAdapter);

        gameComponentRecyclerAdapter.setDbUpdateConsumer(henchmen ->
                new DbAsyncTask(() -> {
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

        gameComponentRecyclerAdapter.setInfoButtonAction(henchmen -> {
            final Intent intent = new Intent(this, HenchmenInfoActivity.class);

            intent.putExtra(COMPONENT_EXTRA, henchmen);

            startActivity(intent);
        });

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
