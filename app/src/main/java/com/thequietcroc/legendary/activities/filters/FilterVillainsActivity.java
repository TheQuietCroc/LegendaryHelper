package com.thequietcroc.legendary.activities.filters;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.activities.info.VillainsInfoActivity;
import com.thequietcroc.legendary.custom.adapters.GameComponentRecyclerAdapter;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.VillainsEntity;
import com.thequietcroc.legendary.models.gamecomponents.cards.Mastermind;
import com.thequietcroc.legendary.models.gamecomponents.cards.Villains;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.thequietcroc.legendary.utilities.LiveDataUtil.observeOnce;

public class FilterVillainsActivity extends FilterActivity {

    final GameComponentRecyclerAdapter<Villains> gameComponentRecyclerAdapter = new GameComponentRecyclerAdapter<>(new ArrayList<>());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String title = String.format("%s %s", getString(R.string.filter), getString(R.string.villains));

        if (null != getActionBar()) {
            getActionBar().setTitle(title);
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(title);
        }

        filterRecyclerView.setAdapter(gameComponentRecyclerAdapter);

        gameComponentRecyclerAdapter.setDbUpdateConsumer(villains ->
                new DbAsyncTask(() -> {
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

        gameComponentRecyclerAdapter.setInfoButtonAction(villains -> {
            final Intent intent = new Intent(this, VillainsInfoActivity.class);

            intent.putExtra(COMPONENT_EXTRA, villains);

            startActivity(intent);
        });

        observeOnce(this,
                db.villainsDao().getAllAsync(),
                this::observerActions);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuActionAddNew: {
                final Intent intent = new Intent(this, VillainsInfoActivity.class);

                intent.putExtra(COMPONENT_EXTRA,
                        new Villains(String.format(
                                "%s %s",
                                getString(R.string.custom),
                                getString(R.string.villains))));

              startActivity(intent);
            } break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }

        return true;
    }

    private void observerActions(final List<VillainsEntity> results) {
        gameComponentRecyclerAdapter.getComponentList().addAll(results
                .stream()
                .map(VillainsEntity::toModel)
                .collect(Collectors.toList()));
        gameComponentRecyclerAdapter.notifyDataSetChanged();
    }

}
