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
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HenchmenEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HeroEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.MastermindEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.SchemeEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.VillainsEntity;
import com.thequietcroc.legendary.utilities.GameSetup;

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

        final MastermindEntity noneMastermind = new MastermindEntity();
        noneMastermind.setId(0);
        noneMastermind.setName("None");
        final List<MastermindEntity> mastermindList = db.mastermindDao().getAllEnabledSync();
        mastermindList.remove(noneMastermind);
        mastermindList.add(0, noneMastermind);

        final SchemeEntity noneScheme = new SchemeEntity();
        noneScheme.setId(0);
        noneScheme.setName("None");
        final List<SchemeEntity> schemeList = db.schemeDao().getAllEnabledSync();
        schemeList.remove(noneScheme);
        schemeList.add(0, noneScheme);

        final VillainsEntity noneVillains = new VillainsEntity();
        noneVillains.setId(0);
        noneVillains.setName("None");
        final List<VillainsEntity> villainsList = db.villainsDao().getAllEnabledSync();
        villainsList.remove(noneVillains);
        villainsList.add(0, noneVillains);

        final HenchmenEntity noneHenchmen = new HenchmenEntity();
        noneHenchmen.setId(0);
        noneHenchmen.setName("None");
        final List<HenchmenEntity> henchmenList = db.henchmenDao().getAllEnabledSync();
        henchmenList.remove(noneHenchmen);
        henchmenList.add(0, noneHenchmen);

        final HeroEntity noneHero = new HeroEntity();
        noneHero.setId(0);
        noneHero.setName("None");
        final List<HeroEntity> heroList = db.heroDao().getAllEnabledSync();
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

