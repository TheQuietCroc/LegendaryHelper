package com.thequietcroc.legendary.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.custom.adapters.GameComponentRecyclerAdapter;
import com.thequietcroc.legendary.database.entities.gamecomponents.GameSetEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HenchmenEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HeroEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.MastermindEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.SchemeEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.VillainsEntity;

import java.util.ArrayList;
import java.util.List;

import static com.thequietcroc.legendary.utilities.LiveDataUtil.observeOnce;

public class FilterGameSetActivity extends FilterActivity {

    final GameComponentRecyclerAdapter<GameSetEntity> gameComponentRecyclerAdapter = new GameComponentRecyclerAdapter<>(new ArrayList<>());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != getActionBar()) {
            getActionBar().setTitle(R.string.activityFilterGameSet);
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(R.string.activityFilterGameSet);
        }

        filterRecyclerView.setAdapter(gameComponentRecyclerAdapter);

        gameComponentRecyclerAdapter.setDbUpdateConsumer(gameSetEntity ->
                AsyncTask.execute(() -> {
                    final List<MastermindEntity> mastermindsInSet = db.mastermindDao().getAllBySetIdSync(gameSetEntity.getId());
                    final List<SchemeEntity> schemesInSet = db.schemeDao().getAllBySetIdSync(gameSetEntity.getId());
                    final List<VillainsEntity> villainsInSet = db.villainsDao().getAllBySetIdSync(gameSetEntity.getId());
                    final List<HenchmenEntity> henchmenInSet = db.henchmenDao().getAllBySetIdSync(gameSetEntity.getId());
                    final List<HeroEntity> heroesInSet = db.heroDao().getAllBySetIdSync(gameSetEntity.getId());

                    mastermindsInSet.stream().forEach(mastermindEntity -> mastermindEntity.setEnabled(gameSetEntity.isEnabled()));
                    schemesInSet.stream().forEach(schemeEntity -> schemeEntity.setEnabled(gameSetEntity.isEnabled()));
                    villainsInSet.stream().forEach(villainsEntity -> villainsEntity.setEnabled(gameSetEntity.isEnabled()));
                    henchmenInSet.stream().forEach(henchmenEntity -> henchmenEntity.setEnabled(gameSetEntity.isEnabled()));
                    heroesInSet.stream().forEach(heroesEntity -> heroesEntity.setEnabled(gameSetEntity.isEnabled()));

                    db.gameSetDao().update(gameSetEntity);
                    db.mastermindDao().update(mastermindsInSet);
                    db.schemeDao().update(schemesInSet);
                    db.villainsDao().update(villainsInSet);
                    db.henchmenDao().update(henchmenInSet);
                    db.heroDao().update(heroesInSet);
                })
        );

        gameComponentRecyclerAdapter.setDbDeleteConsumer(gameSetEntity ->
                AsyncTask.execute(() -> {
                    // TODO: confirm delete all in set before doing it
//                    final List<MastermindEntity> mastermindsInSet = db.gameSetDao().getAllMastermindsInSet(gameSetEntity.getId());
//                    final List<SchemeEntity> schemesInSet = db.gameSetDao().getAllSchemesInSet(gameSetEntity.getId());
//                    final List<VillainsEntity> villainsInSet = db.gameSetDao().getAllVillainsInSet(gameSetEntity.getId());
//                    final List<HenchmenEntity> henchmenInSet = db.gameSetDao().getAllHenchmenInSet(gameSetEntity.getId());
//                    final List<HeroEntity> heroesInSet = db.gameSetDao().getAllHeroesInSet(gameSetEntity.getId());

                    db.gameSetDao().delete(gameSetEntity);
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
        gameComponentRecyclerAdapter.getComponentEntityList().addAll(results);
        gameComponentRecyclerAdapter.notifyDataSetChanged();
    }

}
