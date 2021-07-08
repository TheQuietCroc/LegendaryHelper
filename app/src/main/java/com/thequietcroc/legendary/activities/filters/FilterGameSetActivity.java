package com.thequietcroc.legendary.activities.filters;

import android.os.Bundle;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.activities.info.GameSetInfoActivity;
import com.thequietcroc.legendary.custom.adapters.GameComponentRecyclerAdapter;
import com.thequietcroc.legendary.database.entities.gamecomponents.GameSetEntity;
import com.thequietcroc.legendary.models.gamecomponents.GameSet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.thequietcroc.legendary.utilities.LiveDataUtil.observeOnce;

public class FilterGameSetActivity extends FilterActivity {

    final GameComponentRecyclerAdapter<GameSet> gameComponentRecyclerAdapter = new GameComponentRecyclerAdapter<>(new ArrayList<>());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.filterGameSets));

        filterRecyclerView.setAdapter(gameComponentRecyclerAdapter);
        gameComponentRecyclerAdapter.setDbUpdateConsumer(GameSet::dbSave);
        gameComponentRecyclerAdapter.setInfoButtonAction(gameSet ->
                startNewActivity(gameSet, GameSetInfoActivity.class));

        observeOnce(this,
                db.gameSetDao().getAllAsync(),
                this::observerActions);
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

    private void observerActions(final List<GameSetEntity> results) {
        gameComponentRecyclerAdapter.getComponentList().addAll(results
                .stream()
                .map(GameSetEntity::toModel)
                .collect(Collectors.toList()));
        gameComponentRecyclerAdapter.notifyDataSetChanged();
    }

}
