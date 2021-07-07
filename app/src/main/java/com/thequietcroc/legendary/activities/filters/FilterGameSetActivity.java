package com.thequietcroc.legendary.activities.filters;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.activities.info.GameSetInfoActivity;
import com.thequietcroc.legendary.custom.adapters.GameComponentRecyclerAdapter;
import com.thequietcroc.legendary.database.entities.gamecomponents.GameSetEntity;
import com.thequietcroc.legendary.models.gamecomponents.GameSet;
import com.thequietcroc.legendary.models.gamecomponents.cards.Henchmen;
import com.thequietcroc.legendary.models.gamecomponents.cards.Hero;
import com.thequietcroc.legendary.models.gamecomponents.cards.Mastermind;
import com.thequietcroc.legendary.models.gamecomponents.cards.Scheme;
import com.thequietcroc.legendary.models.gamecomponents.cards.Villains;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.thequietcroc.legendary.utilities.LiveDataUtil.observeOnce;

public class FilterGameSetActivity extends FilterActivity {

    final GameComponentRecyclerAdapter<GameSet> gameComponentRecyclerAdapter = new GameComponentRecyclerAdapter<>(new ArrayList<>());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String title = String.format("%s %s", getString(R.string.filter), getString(R.string.gameSets));

        if (null != getActionBar()) {
            getActionBar().setTitle(title);
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(title);
        }

        filterRecyclerView.setAdapter(gameComponentRecyclerAdapter);

        gameComponentRecyclerAdapter.setDbUpdateConsumer(gameSet ->
                new DbAsyncTask(() -> {
                    final List<Mastermind> mastermindsInSet = gameSet.getMastermindList();
                    final List<Scheme> schemesInSet = gameSet.getSchemeList();
                    final List<Villains> villainsInSet = gameSet.getVillainsList();
                    final List<Henchmen> henchmenInSet = gameSet.getHenchmenList();
                    final List<Hero> heroesInSet = gameSet.getHeroList();

                    mastermindsInSet.stream().forEach(mastermind -> mastermind.setEnabled(gameSet.isEnabled()));
                    schemesInSet.stream().forEach(scheme -> scheme.setEnabled(gameSet.isEnabled()));
                    villainsInSet.stream().forEach(villains -> villains.setEnabled(gameSet.isEnabled()));
                    henchmenInSet.stream().forEach(henchmen -> henchmen.setEnabled(gameSet.isEnabled()));
                    heroesInSet.stream().forEach(heroes -> heroes.setEnabled(gameSet.isEnabled()));

                    db.gameSetDao().update(gameSet.toEntity());
                    db.mastermindDao().update(mastermindsInSet
                            .stream()
                            .map(Mastermind::toEntity)
                            .collect(Collectors.toList()));
                    db.schemeDao().update(schemesInSet
                            .stream()
                            .map(Scheme::toEntity)
                            .collect(Collectors.toList()));
                    db.villainsDao().update(villainsInSet
                            .stream()
                            .map(Villains::toEntity)
                            .collect(Collectors.toList()));
                    db.henchmenDao().update(henchmenInSet
                            .stream()
                            .map(Henchmen::toEntity)
                            .collect(Collectors.toList()));
                    db.heroDao().update(heroesInSet
                            .stream()
                            .map(Hero::toEntity)
                            .collect(Collectors.toList()));
                })
        );

        gameComponentRecyclerAdapter.setInfoButtonAction(gameSet -> {
            final Intent intent = new Intent(this, GameSetInfoActivity.class);

            intent.putExtra(COMPONENT_EXTRA, gameSet);

            startActivity(intent);
        });

        observeOnce(this,
                db.gameSetDao().getAllAsync(),
                this::observerActions);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuActionAddNew: {
                final Intent intent = new Intent(this, GameSetInfoActivity.class);

                intent.putExtra(COMPONENT_EXTRA,
                        new GameSet(String.format(
                                "%s %s",
                                getString(R.string.custom),
                                getString(R.string.gameSet))));

                startActivity(intent);
            } break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }

        return true;
    }

    private void observerActions(final List<GameSetEntity> results) {
        gameComponentRecyclerAdapter.getComponentList().addAll(results
                .stream()
                .map(GameSetEntity::toModel)
                .collect(Collectors.toList()));
        gameComponentRecyclerAdapter.notifyDataSetChanged();
    }

}
