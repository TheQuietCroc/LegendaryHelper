package com.thequietcroc.legendary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.custom.views.CardControl;
import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.entities.BaseCard;
import com.thequietcroc.legendary.database.entities.GameSetup;
import com.thequietcroc.legendary.database.entities.Henchmen;
import com.thequietcroc.legendary.database.entities.Hero;
import com.thequietcroc.legendary.database.entities.Mastermind;
import com.thequietcroc.legendary.database.entities.Scheme;
import com.thequietcroc.legendary.database.entities.Villains;
import com.thequietcroc.legendary.enums.ItemType;

import java.util.ArrayList;
import java.util.List;

import static com.thequietcroc.legendary.enums.ItemType.HENCHMEN;
import static com.thequietcroc.legendary.enums.ItemType.HERO;
import static com.thequietcroc.legendary.enums.ItemType.MASTERMIND;
import static com.thequietcroc.legendary.enums.ItemType.SCHEME;
import static com.thequietcroc.legendary.enums.ItemType.VILLAINS;

public class MainActivity extends AppCompatActivity {

    private LegendaryDatabase db;
    private GameSetup gameSetup;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = LegendaryDatabase.getInstance(this);

        initializeControls();

        final CardControl[] villainsControls = {
                findViewById(R.id.cardControlVillains1),
                findViewById(R.id.cardControlVillains2),
                findViewById(R.id.cardControlVillains3),
                findViewById(R.id.cardControlVillains4)
        };

        final CardControl[] henchmenControls = {
                findViewById(R.id.cardControlHenchmen1),
                findViewById(R.id.cardControlHenchmen2)
        };

        final CardControl[] heroControls = {
                findViewById(R.id.cardControlHeroes1),
                findViewById(R.id.cardControlHeroes2),
                findViewById(R.id.cardControlHeroes3),
                findViewById(R.id.cardControlHeroes4),
                findViewById(R.id.cardControlHeroes5),
                findViewById(R.id.cardControlHeroes6)
        };

        final List<Mastermind> mastermindList = new ArrayList<>();
        final List<Scheme> schemeList = new ArrayList<>();
        final List<Villains> villainsList = new ArrayList<>();
        final List<Henchmen> henchmenList = new ArrayList<>();
        final List<Hero> heroList = new ArrayList<>();

        db.mastermindDao().getAllFilteredAsync().observe(this, mastermindList::addAll);
        db.schemeDao().getAllFilteredAsync().observe(this, schemeList::addAll);
        db.villainsDao().getAllFilteredAsync().observe(this, villainsList::addAll);
        db.henchmenDao().getAllFilteredAsync().observe(this, henchmenList::addAll);
        db.heroDao().getAllFilteredAsync().observe(this, heroList::addAll);

        gameSetup = new GameSetup(db,
                mastermindList,
                schemeList,
                villainsList,
                henchmenList,
                heroList,
                findViewById(R.id.cardControlMastermind),
                findViewById(R.id.cardControlScheme),
                villainsControls,
                henchmenControls,
                heroControls);
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
            } break;
            case R.id.menuActionRandomize: {
                gameSetup.newSetup();
            } break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void initializeControls() {
        populateCardSpinner(HERO);
        populateCardSpinner(MASTERMIND);
        populateCardSpinner(VILLAINS);
        populateCardSpinner(HENCHMEN);
        populateCardSpinner(SCHEME);
    }

    private void populateCardSpinner(final ItemType ItemType) {

        switch (ItemType) {
            case HERO: {
                db.heroDao().getAllFilteredAsync()
                        .observe(this, generateObserver(findViewById(R.id.cardControlHeroes1),
                                findViewById(R.id.cardControlHeroes2),
                                findViewById(R.id.cardControlHeroes3),
                                findViewById(R.id.cardControlHeroes4),
                                findViewById(R.id.cardControlHeroes5),
                                findViewById(R.id.cardControlHeroes6)));
            }
            break;
            case MASTERMIND: {
                db.mastermindDao().getAllFilteredAsync()
                        .observe(this, generateObserver(findViewById(R.id.cardControlMastermind)));
            }
            break;
            case VILLAINS: {
                db.villainsDao().getAllFilteredAsync()
                        .observe(this, generateObserver(findViewById(R.id.cardControlVillains1),
                                findViewById(R.id.cardControlVillains2),
                                findViewById(R.id.cardControlVillains3),
                                findViewById(R.id.cardControlVillains4)));
            }
            break;
            case HENCHMEN: {
                db.henchmenDao().getAllFilteredAsync()
                        .observe(this, generateObserver(findViewById(R.id.cardControlHenchmen1),
                                findViewById(R.id.cardControlHenchmen2)));
            }
            break;
            case SCHEME: {
                db.schemeDao().getAllFilteredAsync()
                        .observe(this, generateObserver(findViewById(R.id.cardControlScheme)));
            }
            break;
        }
    }

    private Observer<List<? extends BaseCard>> generateObserver(final CardControl... cardControls) {
        return list -> {
            for (final CardControl cardControl : cardControls) {
                final Spinner spinner = cardControl.getSpinner();

                final ArrayAdapter<? extends BaseCard> adapter = new ArrayAdapter<>(
                        getApplicationContext(),
                        R.layout.spinner_layout,
                        list);

                adapter.setDropDownViewResource(R.layout.spinner_layout);
                spinner.setAdapter(adapter);
            }
        };
    }

    private void openFilterActivity() {
        final Intent intent = new Intent(this, FilterMenuActivity.class);
        startActivity(intent);
    }
}

