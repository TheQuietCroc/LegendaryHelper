package com.thequietcroc.legendary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.entities.GameSetup;
import com.thequietcroc.legendary.database.entities.Henchmen;
import com.thequietcroc.legendary.database.entities.Hero;
import com.thequietcroc.legendary.database.entities.Mastermind;
import com.thequietcroc.legendary.database.entities.Scheme;
import com.thequietcroc.legendary.database.entities.Villains;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LegendaryDatabase db;
    private GameSetup gameSetup;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = LegendaryDatabase.getInstance(this);

        final ConstraintLayout villainsContainer = findViewById(R.id.containerVillains);
        final ConstraintLayout henchmenContainer = findViewById(R.id.containerHenchmen);
        final ConstraintLayout heroesContainer = findViewById(R.id.containerHeroes);

        final Mastermind noneMastermind = db.mastermindDao().findByIdSync(0);
        final List<Mastermind> mastermindList = db.mastermindDao().getAllFilteredSync();
        mastermindList.remove(noneMastermind);
        mastermindList.add(0, noneMastermind);

        final Scheme noneScheme = db.schemeDao().findByIdSync(0);
        final List<Scheme> schemeList = db.schemeDao().getAllFilteredSync();
        schemeList.remove(noneScheme);
        schemeList.add(0, noneScheme);

        final Villains noneVillain = db.villainsDao().findByIdSync(0);
        final List<Villains> villainsList = db.villainsDao().getAllFilteredSync();
        villainsList.remove(noneVillain);
        villainsList.add(0, noneVillain);

        final Henchmen noneHenchmen = db.henchmenDao().findByIdSync(0);
        final List<Henchmen> henchmenList = db.henchmenDao().getAllFilteredSync();
        henchmenList.remove(noneHenchmen);
        henchmenList.add(0, noneHenchmen);

        final Hero noneHero = db.heroDao().findByIdSync(0);
        final List<Hero> heroList = db.heroDao().getAllFilteredSync();
        heroList.remove(noneHero);
        heroList.add(0, noneHero);

        final MaterialButtonToggleGroup buttonGroupPlayers = findViewById(R.id.buttonGroupPlayers);

        gameSetup = new GameSetup(
                Integer.parseInt((String) ((MaterialButton) findViewById(buttonGroupPlayers.getCheckedButtonId())).getText()),
                buttonGroupPlayers,
                db,
                mastermindList,
                schemeList,
                villainsList,
                henchmenList,
                heroList,
                findViewById(R.id.cardControlMastermind),
                findViewById(R.id.cardControlScheme),
                villainsContainer,
                henchmenContainer,
                heroesContainer);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu_actions, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuActionFilter: {
                openFilterActivity();
            }
            break;
            case R.id.menuActionRandomize: {
                gameSetup.newSetup();
            }
            break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void openFilterActivity() {
        final Intent intent = new Intent(this, FilterMenuActivity.class);
        startActivity(intent);
    }
}

