package com.thequietcroc.legendary.activities.filters;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.activities.info.MastermindInfoActivity;
import com.thequietcroc.legendary.custom.adapters.GameComponentRecyclerAdapter;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.MastermindEntity;
import com.thequietcroc.legendary.models.gamecomponents.cards.Henchmen;
import com.thequietcroc.legendary.models.gamecomponents.cards.Mastermind;
import com.thequietcroc.legendary.models.gamecomponents.cards.Villains;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.thequietcroc.legendary.utilities.LiveDataUtil.observeOnce;

public class FilterMastermindActivity extends FilterActivity {

    final GameComponentRecyclerAdapter<Mastermind> gameComponentRecyclerAdapter = new GameComponentRecyclerAdapter<>(new ArrayList<>());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String title = String.format("%s %s", getString(R.string.filter), getString(R.string.masterminds));

        if (null != getActionBar()) {
            getActionBar().setTitle(title);
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(title);
        }

        filterRecyclerView.setAdapter(gameComponentRecyclerAdapter);

        gameComponentRecyclerAdapter.setDbUpdateConsumer(mastermind ->
                new DbAsyncTask(() -> {
                    if (mastermind.isEnabled()) {
                        final Villains alwaysLeadsVillains = mastermind.getAlwaysLeadsVillains();
                        final Henchmen alwaysLeadsHenchmen = mastermind.getAlwaysLeadsHenchmen();

                        if (alwaysLeadsVillains.getId() != 0) {
                            alwaysLeadsVillains.setEnabled(mastermind.isEnabled());

                            db.villainsDao().update(alwaysLeadsVillains.toEntity());
                        }

                        if (alwaysLeadsHenchmen.getId() != 0) {
                            alwaysLeadsHenchmen.setEnabled(mastermind.isEnabled());

                            db.henchmenDao().update(alwaysLeadsHenchmen.toEntity());
                        }
                    }

                    db.mastermindDao().update(mastermind.toEntity());
                })
        );

        gameComponentRecyclerAdapter.setInfoButtonAction(mastermind -> {
            final Intent intent = new Intent(this, MastermindInfoActivity.class);

            intent.putExtra(COMPONENT_EXTRA, mastermind);

            startActivity(intent);
        });

        observeOnce(this,
                db.mastermindDao().getAllAsync(),
                this::observerActions);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuActionAddNew: {
                final Intent intent = new Intent(this, MastermindInfoActivity.class);

                intent.putExtra(COMPONENT_EXTRA,
                        new Mastermind(String.format(
                                "%s %s",
                                getString(R.string.custom),
                                getString(R.string.mastermind))));

                startActivity(intent);
            } break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }

        return true;
    }

    private void observerActions(final List<MastermindEntity> results) {
        gameComponentRecyclerAdapter.getComponentList().addAll(results
                .stream()
                .map(MastermindEntity::toModel)
                .collect(Collectors.toList()));
        gameComponentRecyclerAdapter.notifyDataSetChanged();
    }

}
