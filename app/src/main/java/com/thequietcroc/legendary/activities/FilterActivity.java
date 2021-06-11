package com.thequietcroc.legendary.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.custom.adapters.GameComponentRecyclerAdapter;
import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.entities.gamecomponents.BaseGameComponentEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.GameSetEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HenchmenEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HeroEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.MastermindEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.SchemeEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.VillainsEntity;
import com.thequietcroc.legendary.enums.ItemType;

import java.util.ArrayList;
import java.util.List;

import static com.thequietcroc.legendary.utilities.LiveDataUtil.observeOnce;

public class FilterActivity extends AppCompatActivity {

    final List componentEntityList = new ArrayList();
    ItemType type;
    RecyclerView filterRecyclerView;
    GameComponentRecyclerAdapter gameComponentRecyclerAdapter = new GameComponentRecyclerAdapter(componentEntityList);
    LegendaryDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        final Intent intent = getIntent();
        type = (ItemType) intent.getSerializableExtra(FilterSelectMenuActivity.EXTRA_TYPE);
        filterRecyclerView = findViewById(R.id.filterRecyclerView);
        db = LegendaryDatabase.getInstance(this);

        filterRecyclerView.setAdapter(gameComponentRecyclerAdapter);
        filterRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_activity_menu_actions, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuActionSave: {
                saveComponents();
            }
            break;
            case R.id.menuActionAddNew: {

            }
            break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private <T extends BaseGameComponentEntity> void observerActions(final List<T> results) {
        componentEntityList.addAll(results);
        gameComponentRecyclerAdapter.notifyDataSetChanged();
    }

    private void saveComponents() {


        switch (type) {
            case GAMESET:{
                final List<GameSetEntity> componentEntityList = gameComponentRecyclerAdapter.getComponentEntityList();
                final List<GameSetEntity> componentsToDeleteList = gameComponentRecyclerAdapter.getComponentsToDeleteList();

                AsyncTask.execute(() -> {
                    db.gameSetDao().insert(componentEntityList);
                    db.gameSetDao().delete(componentsToDeleteList);
                });
            } break;
            case HERO: {
                final List<HeroEntity> componentEntityList = gameComponentRecyclerAdapter.getComponentEntityList();
                final List<HeroEntity> componentsToDeleteList = gameComponentRecyclerAdapter.getComponentsToDeleteList();

                AsyncTask.execute(() -> {
                    db.heroDao().insert(componentEntityList);
                    db.heroDao().delete(componentsToDeleteList);
                });
            } break;
            case MASTERMIND: {
                final List<MastermindEntity> componentEntityList = gameComponentRecyclerAdapter.getComponentEntityList();
                final List<MastermindEntity> componentsToDeleteList = gameComponentRecyclerAdapter.getComponentsToDeleteList();

                AsyncTask.execute(() -> {
                    db.mastermindDao().insert(componentEntityList);
                    db.mastermindDao().delete(componentsToDeleteList);
                });
            } break;
            case VILLAINS: {
                final List<VillainsEntity> componentEntityList = gameComponentRecyclerAdapter.getComponentEntityList();
                final List<VillainsEntity> componentsToDeleteList = gameComponentRecyclerAdapter.getComponentsToDeleteList();

                AsyncTask.execute(() -> {
                    db.villainsDao().insert(componentEntityList);
                    db.villainsDao().delete(componentsToDeleteList);
                });
            } break;
            case HENCHMEN: {
                final List<HenchmenEntity> componentEntityList = gameComponentRecyclerAdapter.getComponentEntityList();
                final List<HenchmenEntity> componentsToDeleteList = gameComponentRecyclerAdapter.getComponentsToDeleteList();

                AsyncTask.execute(() -> {
                    db.henchmenDao().insert(componentEntityList);
                    db.henchmenDao().delete(componentsToDeleteList);
                });
            } break;
            case SCHEME: {
                final List<SchemeEntity> componentEntityList = gameComponentRecyclerAdapter.getComponentEntityList();
                final List<SchemeEntity> componentsToDeleteList = gameComponentRecyclerAdapter.getComponentsToDeleteList();

                AsyncTask.execute(() -> {
                    db.schemeDao().insert(componentEntityList);
                    db.schemeDao().delete(componentsToDeleteList);
                });
            } break;
        }

        Toast.makeText(this.getApplicationContext(), "Changes Saved", Toast.LENGTH_SHORT).show();

    }
}
