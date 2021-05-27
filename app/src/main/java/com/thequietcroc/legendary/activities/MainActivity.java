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
import com.thequietcroc.legendary.database.entities.GameSetup;
import com.thequietcroc.legendary.database.entities.Henchmen;
import com.thequietcroc.legendary.database.entities.Hero;
import com.thequietcroc.legendary.database.entities.Mastermind;
import com.thequietcroc.legendary.database.entities.Scheme;
import com.thequietcroc.legendary.database.entities.Villains;
import com.thequietcroc.legendary.enums.ItemType;

import java.util.ArrayList;
import java.util.Arrays;
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

        final List<CardControl> villainsControls = new ArrayList<>(Arrays.asList(
                findViewById(R.id.cardControlVillains1),
                findViewById(R.id.cardControlVillains2),
                findViewById(R.id.cardControlVillains3),
                findViewById(R.id.cardControlVillains4)
        ));

        final List<CardControl> henchmenControls = new ArrayList<>(Arrays.asList(
                findViewById(R.id.cardControlHenchmen1),
                findViewById(R.id.cardControlHenchmen2)
        ));

        final List<CardControl> heroControls = new ArrayList<>(Arrays.asList(
                findViewById(R.id.cardControlHeroes1),
                findViewById(R.id.cardControlHeroes2),
                findViewById(R.id.cardControlHeroes3),
                findViewById(R.id.cardControlHeroes4),
                findViewById(R.id.cardControlHeroes5),
                findViewById(R.id.cardControlHeroes6)
        ));

        final List<Mastermind> mastermindList = new ArrayList<>();
        final List<Scheme> schemeList = new ArrayList<>();
        final List<Villains> villainsList = new ArrayList<>();
        final List<Henchmen> henchmenList = new ArrayList<>();
        final List<Hero> heroList = new ArrayList<>();

        db.mastermindDao().getAllFilteredAsync().observe(this, list -> {
            final Mastermind noneEntry = db.mastermindDao().findByIdSync(0);

            list.remove(noneEntry);
            list.add(0, noneEntry);

            mastermindList.addAll(list);

        });
        db.schemeDao().getAllFilteredAsync().observe(this, list -> {
            final Scheme noneEntry = db.schemeDao().findByIdSync(0);

            list.remove(noneEntry);
            list.add(0, noneEntry);

            schemeList.addAll(list);

        });
        db.villainsDao().getAllFilteredAsync().observe(this, list -> {
            final Villains noneEntry = db.villainsDao().findByIdSync(0);

            list.remove(noneEntry);
            list.add(0, noneEntry);

            villainsList.addAll(list);

        });
        db.henchmenDao().getAllFilteredAsync().observe(this, list -> {
            final Henchmen noneEntry = db.henchmenDao().findByIdSync(0);

            list.remove(noneEntry);
            list.add(0, noneEntry);

            henchmenList.addAll(list);

        });
        db.heroDao().getAllFilteredAsync().observe(this, list -> {
            final Hero noneEntry = db.heroDao().findByIdSync(0);

            list.remove(noneEntry);
            list.add(0, noneEntry);

            heroList.addAll(list);

        });

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
                final Hero noneEntry = db.heroDao().findByIdSync(0);

                db.heroDao().getAllFilteredAsync()
                        .observe(this, generateObserver(noneEntry,
                                findViewById(R.id.cardControlHeroes1),
                                findViewById(R.id.cardControlHeroes2),
                                findViewById(R.id.cardControlHeroes3),
                                findViewById(R.id.cardControlHeroes4),
                                findViewById(R.id.cardControlHeroes5),
                                findViewById(R.id.cardControlHeroes6)));
            }
            break;
            case MASTERMIND: {
                final Mastermind noneEntry = db.mastermindDao().findByIdSync(0);

                db.mastermindDao().getAllFilteredAsync()
                        .observe(this, generateObserver(noneEntry,
                                findViewById(R.id.cardControlMastermind)));
            }
            break;
            case VILLAINS: {
                final Villains noneEntry = db.villainsDao().findByIdSync(0);

                db.villainsDao().getAllFilteredAsync()
                        .observe(this, generateObserver(noneEntry,
                                findViewById(R.id.cardControlVillains1),
                                findViewById(R.id.cardControlVillains2),
                                findViewById(R.id.cardControlVillains3),
                                findViewById(R.id.cardControlVillains4)));
            }
            break;
            case HENCHMEN: {
                final Henchmen noneEntry = db.henchmenDao().findByIdSync(0);

                db.henchmenDao().getAllFilteredAsync()
                        .observe(this, generateObserver(noneEntry,
                                findViewById(R.id.cardControlHenchmen1),
                                findViewById(R.id.cardControlHenchmen2)));
            }
            break;
            case SCHEME: {
                final Scheme noneEntry = db.schemeDao().findByIdSync(0);

                db.schemeDao().getAllFilteredAsync()
                        .observe(this, generateObserver(noneEntry,
                                findViewById(R.id.cardControlScheme)));
            }
            break;
        }
    }

    private <T> Observer<List<T>> generateObserver(final T noneEntry,
                                                   final CardControl... cardControls) {
        return list -> {
            list.remove(noneEntry);
            list.add(0, noneEntry);

            for (final CardControl cardControl : cardControls) {
                final Spinner spinner = cardControl.getSpinner();

                final ArrayAdapter<T> adapter = new ArrayAdapter<>(
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

