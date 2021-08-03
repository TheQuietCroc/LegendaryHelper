package com.thequietcroc.legendary.activities.filters;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.activities.info.GameSetInfoActivity;
import com.thequietcroc.legendary.database.entities.gamecomponents.GameSetEntity;
import com.thequietcroc.legendary.models.gamecomponents.GameSet;
import com.thequietcroc.legendary.models.gamecomponents.cards.Henchmen;
import com.thequietcroc.legendary.models.gamecomponents.cards.Hero;
import com.thequietcroc.legendary.models.gamecomponents.cards.Mastermind;
import com.thequietcroc.legendary.models.gamecomponents.cards.Scheme;
import com.thequietcroc.legendary.models.gamecomponents.cards.Villains;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.List;
import java.util.stream.Collectors;

public class FilterGameSetActivity extends FilterActivity<GameSet> {

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.filterGameSets));

        gameComponentRecyclerAdapter.setCheckboxOnClickCosumer(vh -> {
            final boolean isChecked = vh.getGameComponentEnabledCheckbox().isChecked();
            final GameSet gameSet = gameComponentRecyclerAdapter.getComponentList()
                    .get(vh.getAdapterPosition());

            gameSet.setEnabled(isChecked);
            gameSet.dbSave();

            new DbAsyncTask(() -> {
                gameSet.getMastermindList().stream().forEach(mastermind -> mastermind.setEnabled(isChecked));
                db.mastermindDao().update(gameSet.getMastermindList()
                        .stream()
                        .map(Mastermind::toEntity)
                        .collect(Collectors.toList()));

                gameSet.getSchemeList().stream().forEach(scheme -> scheme.setEnabled(isChecked));
                db.schemeDao().update(gameSet.getSchemeList()
                        .stream()
                        .map(Scheme::toEntity)
                        .collect(Collectors.toList()));

                gameSet.getVillainsList().stream().forEach(villains -> villains.setEnabled(isChecked));
                db.villainsDao().update(gameSet.getVillainsList()
                        .stream()
                        .map(Villains::toEntity)
                        .collect(Collectors.toList()));

                gameSet.getHenchmenList().stream().forEach(henchmen -> henchmen.setEnabled(isChecked));
                db.henchmenDao().update(gameSet.getHenchmenList()
                        .stream()
                        .map(Henchmen::toEntity)
                        .collect(Collectors.toList()));

                gameSet.getHeroList().stream().forEach(hero -> hero.setEnabled(isChecked));
                db.heroDao().update(gameSet.getHeroList()
                        .stream()
                        .map(Hero::toEntity)
                        .collect(Collectors.toList()));
            });

        });
        gameComponentRecyclerAdapter.setInfoButtonOnClickConsumer(vh
                -> startNewActivity(getGameComponent(vh), GameSetInfoActivity.class));

        new DbAsyncTask(() -> {
            final List<GameSetEntity> results = db.gameSetDao().getAll();

            new Handler(Looper.getMainLooper()).post(() -> {
                gameComponentRecyclerAdapter.getComponentList().addAll(results
                        .stream()
                        .map(GameSetEntity::toModel)
                        .collect(Collectors.toList()));

                gameComponentRecyclerAdapter.notifyDataSetChanged();
            });
        });
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuActionAddNew: {
                startNewActivity(new GameSet(getString(R.string.customGameSet)),
                        GameSetInfoActivity.class);
            } break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }

        return true;
    }

}
