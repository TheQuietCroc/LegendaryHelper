package com.thequietcroc.legendary.activities.filters;

import android.os.Bundle;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.activities.info.HenchmenInfoActivity;
import com.thequietcroc.legendary.custom.adapters.GameComponentRecyclerAdapter;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HenchmenEntity;
import com.thequietcroc.legendary.models.gamecomponents.cards.Henchmen;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.thequietcroc.legendary.utilities.LiveDataUtil.observeOnce;

public class FilterHenchmenActivity extends FilterActivity {

    final GameComponentRecyclerAdapter<Henchmen> gameComponentRecyclerAdapter = new GameComponentRecyclerAdapter<>(new ArrayList<>());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.filterHenchmen));

        filterRecyclerView.setAdapter(gameComponentRecyclerAdapter);
        gameComponentRecyclerAdapter.setDbUpdateConsumer(Henchmen::dbSave);
        gameComponentRecyclerAdapter.setInfoButtonAction(henchmen ->
                startNewActivity(henchmen, HenchmenInfoActivity.class));

        observeOnce(this,
                db.henchmenDao().getAllAsync(),
                this::observerActions);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuActionAddNew: {
                startNewActivity(new Henchmen(getString(R.string.customHenchmen)),
                        HenchmenInfoActivity.class);
            } break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }

        return true;
    }

    private void observerActions(final List<HenchmenEntity> results) {
        gameComponentRecyclerAdapter.getComponentList().addAll(results
                .stream()
                .map(HenchmenEntity::toModel)
                .collect(Collectors.toList()));
        gameComponentRecyclerAdapter.notifyDataSetChanged();
    }

}
