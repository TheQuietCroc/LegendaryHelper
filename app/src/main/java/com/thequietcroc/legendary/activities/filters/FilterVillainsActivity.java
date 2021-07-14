package com.thequietcroc.legendary.activities.filters;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.activities.info.VillainsInfoActivity;
import com.thequietcroc.legendary.custom.adapters.GameComponentRecyclerAdapter;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.VillainsEntity;
import com.thequietcroc.legendary.models.gamecomponents.cards.Villains;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilterVillainsActivity extends FilterActivity {

    final GameComponentRecyclerAdapter<Villains> gameComponentRecyclerAdapter = new GameComponentRecyclerAdapter<>(new ArrayList<>());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.filterVillains));

        filterRecyclerView.setAdapter(gameComponentRecyclerAdapter);
        gameComponentRecyclerAdapter.setCheckboxOnClickConsumer(Villains::dbSave);
        gameComponentRecyclerAdapter.setInfoButtonAction(villains ->
                startNewActivity(villains, VillainsInfoActivity.class));

        new DbAsyncTask(() -> {
            final List<VillainsEntity> results = db.villainsDao().getAll();

            new Handler(Looper.getMainLooper()).post(() -> {
                gameComponentRecyclerAdapter.getComponentList().addAll(results
                        .stream()
                        .map(VillainsEntity::toModel)
                        .collect(Collectors.toList()));

                filterRecyclerView.setAdapter(gameComponentRecyclerAdapter);
                gameComponentRecyclerAdapter.setInfoButtonAction(villains ->
                        startNewActivity(villains, VillainsInfoActivity.class));
            });
        });
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuActionAddNew: {
              startNewActivity(new Villains(getString(R.string.customVillains)),
                      VillainsInfoActivity.class);
            } break;
            default:
                super.onOptionsItemSelected(item);
                break;
        }

        return true;
    }
}
