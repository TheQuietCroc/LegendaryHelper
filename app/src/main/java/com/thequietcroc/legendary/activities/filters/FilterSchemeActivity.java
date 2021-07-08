package com.thequietcroc.legendary.activities.filters;

import android.os.Bundle;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.activities.info.SchemeInfoActivity;
import com.thequietcroc.legendary.custom.adapters.GameComponentRecyclerAdapter;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.SchemeEntity;
import com.thequietcroc.legendary.models.gamecomponents.cards.Scheme;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.thequietcroc.legendary.utilities.LiveDataUtil.observeOnce;

public class FilterSchemeActivity extends FilterActivity {

    final GameComponentRecyclerAdapter<Scheme> gameComponentRecyclerAdapter = new GameComponentRecyclerAdapter<>(new ArrayList<>());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.filterSchemes));

        filterRecyclerView.setAdapter(gameComponentRecyclerAdapter);
        gameComponentRecyclerAdapter.setDbUpdateConsumer(Scheme::dbSave);
        gameComponentRecyclerAdapter.setInfoButtonAction(scheme ->
                startNewActivity(scheme, SchemeInfoActivity.class));

        observeOnce(this,
                db.schemeDao().getAllAsync(),
                this::observerActions);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuActionAddNew: {
                startNewActivity(new Scheme(getString(R.string.customScheme)),
                        SchemeInfoActivity.class);
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
