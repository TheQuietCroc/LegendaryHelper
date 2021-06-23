package com.thequietcroc.legendary.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.custom.adapters.GameComponentRecyclerAdapter;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HeroEntity;
import com.thequietcroc.legendary.models.gamecomponents.cards.Hero;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.thequietcroc.legendary.utilities.LiveDataUtil.observeOnce;

public class FilterHeroActivity extends FilterActivity {

    final GameComponentRecyclerAdapter<Hero> gameComponentRecyclerAdapter = new GameComponentRecyclerAdapter<>(new ArrayList<>());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null != getActionBar()) {
            getActionBar().setTitle(R.string.activityFilterHero);
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(R.string.activityFilterHero);
        }

        filterRecyclerView.setAdapter(gameComponentRecyclerAdapter);

        gameComponentRecyclerAdapter.setDbUpdateConsumer(hero ->
                AsyncTask.execute(() ->
                        db.heroDao().update(hero.toEntity()))
        );

        gameComponentRecyclerAdapter.setDbDeleteConsumer(hero ->
                AsyncTask.execute(() ->
                        db.heroDao().delete(hero.toEntity()))
        );

        observeOnce(this,
                db.heroDao().getAllAsync(),
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

    private void observerActions(final List<HeroEntity> results) {
        gameComponentRecyclerAdapter.getComponentEntityList().addAll(results
                .stream()
                .map(HeroEntity::toModel)
                .collect(Collectors.toList()));
        gameComponentRecyclerAdapter.notifyDataSetChanged();
    }

}
