package com.thequietcroc.legendary.activities.filters;

import android.os.Bundle;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.activities.info.MastermindInfoActivity;
import com.thequietcroc.legendary.custom.adapters.GameComponentRecyclerAdapter;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.MastermindEntity;
import com.thequietcroc.legendary.models.gamecomponents.cards.Mastermind;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.thequietcroc.legendary.utilities.LiveDataUtil.observeOnce;

public class FilterMastermindActivity extends FilterActivity {

    final GameComponentRecyclerAdapter<Mastermind> gameComponentRecyclerAdapter = new GameComponentRecyclerAdapter<>(new ArrayList<>());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.filterMasterminds));

        filterRecyclerView.setAdapter(gameComponentRecyclerAdapter);
        gameComponentRecyclerAdapter.setDbUpdateConsumer(Mastermind::dbSave);
        gameComponentRecyclerAdapter.setInfoButtonAction(mastermind ->
                startNewActivity(mastermind, MastermindInfoActivity.class));

        observeOnce(this,
                db.mastermindDao().getAllAsync(),
                this::observerActions);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuActionAddNew: {
                startNewActivity(new Mastermind(getString(R.string.customMastermind)),
                        MastermindInfoActivity.class);
            } break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }

        return true;
    }

    private void observerActions(final List<MastermindEntity> results) {
        gameComponentRecyclerAdapter.getComponentList().addAll(results
                .stream()
                .map(MastermindEntity::toModel)
                .collect(Collectors.toList()));
        gameComponentRecyclerAdapter.notifyDataSetChanged();
    }

}
