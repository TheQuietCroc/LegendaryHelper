package com.thequietcroc.legendary.activities.filters;

import android.os.Bundle;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.activities.info.HeroInfoActivity;
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
        setTitle(getString(R.string.filterHeroes));

        filterRecyclerView.setAdapter(gameComponentRecyclerAdapter);
        gameComponentRecyclerAdapter.setDbUpdateConsumer(Hero::dbSave);
        gameComponentRecyclerAdapter.setInfoButtonAction(hero ->
                startNewActivity(hero, HeroInfoActivity.class));

        observeOnce(this,
                db.heroDao().getAllAsync(),
                this::observerActions);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuActionAddNew: {
                startNewActivity(new Hero(getString(R.string.customHero)),
                        HeroInfoActivity.class);
            } break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }

        return true;
    }

    private void observerActions(final List<HeroEntity> results) {
        gameComponentRecyclerAdapter.getComponentList().addAll(results
                .stream()
                .map(HeroEntity::toModel)
                .collect(Collectors.toList()));
        gameComponentRecyclerAdapter.notifyDataSetChanged();
    }

}
