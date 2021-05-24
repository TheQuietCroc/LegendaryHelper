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
import com.thequietcroc.legendary.enums.ItemType;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static com.thequietcroc.legendary.enums.ItemType.HENCHMEN;
import static com.thequietcroc.legendary.enums.ItemType.HERO;
import static com.thequietcroc.legendary.enums.ItemType.MASTERMIND;
import static com.thequietcroc.legendary.enums.ItemType.SCHEME;
import static com.thequietcroc.legendary.enums.ItemType.VILLAINS;

public class MainActivity extends AppCompatActivity {

    private LegendaryDatabase db;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = LegendaryDatabase.getInstance(this);

        initializeControls();
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
                randomizeSelections();
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
                db.heroDao().getAllFilteredNames()
                        .observe(this, generateObserver(findViewById(R.id.cardControlHeroes1),
                                findViewById(R.id.cardControlHeroes2),
                                findViewById(R.id.cardControlHeroes3),
                                findViewById(R.id.cardControlHeroes4),
                                findViewById(R.id.cardControlHeroes5),
                                findViewById(R.id.cardControlHeroes6)));
            }
            break;
            case MASTERMIND: {
                db.mastermindDao().getAllFilteredNames()
                        .observe(this, generateObserver(findViewById(R.id.cardControlMastermind)));
            }
            break;
            case VILLAINS: {
                db.villainsDao().getAllFilteredNames()
                        .observe(this, generateObserver(findViewById(R.id.cardControlVillains1),
                                findViewById(R.id.cardControlVillains2),
                                findViewById(R.id.cardControlVillains3),
                                findViewById(R.id.cardControlVillains4)));
            }
            break;
            case HENCHMEN: {
                db.henchmenDao().getAllFilteredNames()
                        .observe(this, generateObserver(findViewById(R.id.cardControlHenchmen1),
                                findViewById(R.id.cardControlHenchmen2)));
            }
            break;
            case SCHEME: {
                db.schemeDao().getAllFilteredNames()
                        .observe(this, generateObserver(findViewById(R.id.cardControlScheme)));
            }
            break;
        }
    }

    private Observer<List<String>> generateObserver(final CardControl... cardControls) {
        return list -> {
            list.add(0, "None");

            for (final CardControl cardControl : cardControls) {
                final Spinner spinner = cardControl.getSpinner();
                final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getApplicationContext(),
                        R.layout.spinner_layout,
                        list);

                adapter.setDropDownViewResource(R.layout.spinner_layout);
                spinner.setAdapter(adapter);
            }
        };
    }

    private void randomizeSelections() {

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

        setupMastermind(findViewById(R.id.cardControlMastermind), villainsControls[0], henchmenControls[0]);
        setupScheme(findViewById(R.id.cardControlScheme));
        selectRandomly(villainsControls);
        selectRandomly(henchmenControls);
        selectRandomly(heroControls);
    }

    private void setupMastermind(final CardControl mastermindControl,
                                 final CardControl villainsControl,
                                 final CardControl henchmenControls) {
        selectRandomly(mastermindControl);
    }

    private void setupScheme(final CardControl schemeControl) {
        selectRandomly(schemeControl);
    }

    private void selectRandomly(final CardControl... cardControls) {
        final Set<Integer> results = new HashSet<>(cardControls.length);

        for (final CardControl cardControl : cardControls) {
            if (!cardControl.getToggleLock().isChecked()
                    && !cardControl.isSetBySetup()) {

                final Spinner spinner = cardControl.getSpinner();
                final int numCards = spinner.getCount();
                int selection;

                do {
                    selection = new Random().nextInt(numCards - 1) + 1;
                } while (!results.add(selection));

                spinner.setSelection(selection);
            } else {
                results.add(cardControl.getSpinner().getSelectedItemPosition());
            }
        }
    }

    private void openFilterActivity() {
        final Intent intent = new Intent(this, FilterMenuActivity.class);
        startActivity(intent);
    }
}

