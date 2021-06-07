package com.thequietcroc.legendary.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.custom.adapters.GameComponentRecyclerAdapter;
import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.entities.gamecomponents.BaseGameComponentEntity;
import com.thequietcroc.legendary.enums.ItemType;

import java.util.List;

import static com.thequietcroc.legendary.utilities.LiveDataUtil.observeOnce;

public class FilterActivity extends AppCompatActivity {

    RecyclerView filterRecyclerView;
    GameComponentRecyclerAdapter gameComponentRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        final Intent intent = getIntent();
        final ItemType type = (ItemType) intent.getSerializableExtra(FilterSelectMenuActivity.EXTRA_TYPE);
        final LegendaryDatabase db = LegendaryDatabase.getInstance(this);
        filterRecyclerView = findViewById(R.id.filterRecyclerView);

        switch (type) {
            case GAMESET: {
                observeOnce(this,
                        db.gameSetDao().getAllAsync(),
                        this::observerActions);
            } break;
            case HERO: {
                observeOnce(this,
                        db.heroDao().getAllAsync(),
                        this::observerActions);
            } break;
            case MASTERMIND: {
                observeOnce(this,
                        db.mastermindDao().getAllAsync(),
                        this::observerActions);
            } break;
            case VILLAINS: {
                observeOnce(this,
                        db.villainsDao().getAllAsync(),
                        this::observerActions);
            } break;
            case HENCHMEN: {
                observeOnce(this,
                        db.henchmenDao().getAllAsync(),
                        this::observerActions);
            } break;
            case SCHEME: {
                observeOnce(this,
                        db.schemeDao().getAllAsync(),
                        this::observerActions);
            } break;
        }
    }

    private <T extends BaseGameComponentEntity> void observerActions(final List<T> results) {
        this.gameComponentRecyclerAdapter = new GameComponentRecyclerAdapter(results);

        filterRecyclerView.setAdapter(gameComponentRecyclerAdapter);
        filterRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
