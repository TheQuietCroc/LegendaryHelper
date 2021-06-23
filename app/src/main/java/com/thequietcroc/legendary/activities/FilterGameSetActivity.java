package com.thequietcroc.legendary.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.custom.adapters.GameComponentRecyclerAdapter;
import com.thequietcroc.legendary.database.entities.gamecomponents.GameSetEntity;
import com.thequietcroc.legendary.models.gamecomponents.GameSet;
import com.thequietcroc.legendary.models.gamecomponents.cards.Henchmen;
import com.thequietcroc.legendary.models.gamecomponents.cards.Hero;
import com.thequietcroc.legendary.models.gamecomponents.cards.Mastermind;
import com.thequietcroc.legendary.models.gamecomponents.cards.Scheme;
import com.thequietcroc.legendary.models.gamecomponents.cards.Villains;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.thequietcroc.legendary.utilities.LiveDataUtil.observeOnce;

public class FilterGameSetActivity extends FilterActivity {

    final GameComponentRecyclerAdapter<GameSet> gameComponentRecyclerAdapter = new GameComponentRecyclerAdapter<>(new ArrayList<>());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != getActionBar()) {
            getActionBar().setTitle(R.string.activityFilterGameSet);
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(R.string.activityFilterGameSet);
        }

        filterRecyclerView.setAdapter(gameComponentRecyclerAdapter);

        gameComponentRecyclerAdapter.setDbUpdateConsumer(gameSet ->
                AsyncTask.execute(() -> {
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

        gameComponentRecyclerAdapter.setDbDeleteConsumer(gameSet ->
                AsyncTask.execute(() -> {
                    // TODO: confirm delete all in set before doing it
//                    final List<MastermindEntity> mastermindsInSet = db.gameSetDao().getAllMastermindsInSet(gameSet.getId());
//                    final List<SchemeEntity> schemesInSet = db.gameSetDao().getAllSchemesInSet(gameSet.getId());
//                    final List<VillainsEntity> villainsInSet = db.gameSetDao().getAllVillainsInSet(gameSet.getId());
//                    final List<HenchmenEntity> henchmenInSet = db.gameSetDao().getAllHenchmenInSet(gameSet.getId());
//                    final List<HeroEntity> heroesInSet = db.gameSetDao().getAllHeroesInSet(gameSet.getId());

                    db.gameSetDao().delete(gameSet.toEntity());
//                    db.mastermindDao().delete(mastermindsInSet);
//                    db.schemeDao().delete(schemesInSet);
//                    db.villainsDao().delete(villainsInSet);
//                    db.henchmenDao().delete(henchmenInSet);
//                    db.heroDao().delete(heroesInSet);
                })
        );

        observeOnce(this,
                db.gameSetDao().getAllAsync(),
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

    private void observerActions(final List<GameSetEntity> results) {
        gameComponentRecyclerAdapter.getComponentEntityList().addAll(results
                .stream()
                .map(GameSetEntity::toModel)
                .collect(Collectors.toList()));
        gameComponentRecyclerAdapter.notifyDataSetChanged();
        List<Mastermind> test = gameComponentRecyclerAdapter.getComponentEntityList().get(0).getMastermindList();
    }

}
