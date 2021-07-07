package com.thequietcroc.legendary.activities.filters;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.activities.info.SchemeInfoActivity;
import com.thequietcroc.legendary.custom.adapters.GameComponentRecyclerAdapter;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.SchemeEntity;
import com.thequietcroc.legendary.models.gamecomponents.cards.Scheme;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.thequietcroc.legendary.utilities.LiveDataUtil.observeOnce;

public class FilterSchemeActivity extends FilterActivity {

    final GameComponentRecyclerAdapter<Scheme> gameComponentRecyclerAdapter = new GameComponentRecyclerAdapter<>(new ArrayList<>());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String title = String.format("%s %s", getString(R.string.filter), getString(R.string.schemes));

        if (null != getActionBar()) {
            getActionBar().setTitle(title);
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(title);
        }

        filterRecyclerView.setAdapter(gameComponentRecyclerAdapter);

        gameComponentRecyclerAdapter.setDbUpdateConsumer(scheme ->
                new DbAsyncTask(() ->
                        db.schemeDao().update(scheme.toEntity()))
        );

        gameComponentRecyclerAdapter.setInfoButtonAction(scheme -> {
            final Intent intent = new Intent(this, SchemeInfoActivity.class);

            intent.putExtra(COMPONENT_EXTRA, scheme);

            startActivity(intent);
        });

        observeOnce(this,
                db.schemeDao().getAllAsync(),
                this::observerActions);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuActionAddNew: {
                final Intent intent = new Intent(this, SchemeInfoActivity.class);

                intent.putExtra(COMPONENT_EXTRA,
                        new Scheme(String.format(
                                "%s %s",
                                getString(R.string.custom),
                                getString(R.string.scheme))));

                startActivity(intent);
            } break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }

        return true;
    }

    private void observerActions(final List<SchemeEntity> results) {
        gameComponentRecyclerAdapter.getComponentList().addAll(results
                .stream()
                .map(SchemeEntity::toModel)
                .collect(Collectors.toList()));
        gameComponentRecyclerAdapter.notifyDataSetChanged();
    }

}
