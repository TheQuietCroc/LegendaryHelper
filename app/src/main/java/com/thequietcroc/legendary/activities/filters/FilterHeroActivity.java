package com.thequietcroc.legendary.activities.filters;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.activities.info.HeroInfoActivity;
import com.thequietcroc.legendary.custom.adapters.GameComponentRecyclerAdapter;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HeroEntity;
import com.thequietcroc.legendary.models.gamecomponents.cards.Hero;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.thequietcroc.legendary.utilities.LiveDataUtil.observeOnce;

public class FilterHeroActivity extends FilterActivity {

    final GameComponentRecyclerAdapter<Hero> gameComponentRecyclerAdapter = new GameComponentRecyclerAdapter<>(new ArrayList<>());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String title = String.format("%s %s", getString(R.string.filter), getString(R.string.heroes));

        if (null != getActionBar()) {
            getActionBar().setTitle(title);
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(title);
        }

        filterRecyclerView.setAdapter(gameComponentRecyclerAdapter);

        gameComponentRecyclerAdapter.setDbUpdateConsumer(hero ->
                new DbAsyncTask(() ->
                        db.heroDao().update(hero.toEntity()))
        );

        gameComponentRecyclerAdapter.setInfoButtonAction(hero -> {
            final Intent intent = new Intent(this, HeroInfoActivity.class);

            intent.putExtra(COMPONENT_EXTRA, hero);

            startActivity(intent);
        });

        observeOnce(this,
                db.heroDao().getAllAsync(),
                this::observerActions);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuActionAddNew: {
                final Intent intent = new Intent(this, HeroInfoActivity.class);

                intent.putExtra(COMPONENT_EXTRA,
                        new Hero(String.format(
                                "%s %s",
                                getString(R.string.custom),
                                getString(R.string.hero))));

                startActivity(intent);
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
