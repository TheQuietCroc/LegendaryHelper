package com.thequietcroc.legendary;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.thequietcroc.legendary.custom.views.CardControl;
import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.enums.CardType;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static com.thequietcroc.legendary.enums.CardType.HENCHMEN;
import static com.thequietcroc.legendary.enums.CardType.HERO;
import static com.thequietcroc.legendary.enums.CardType.MASTERMIND;
import static com.thequietcroc.legendary.enums.CardType.SCHEME;
import static com.thequietcroc.legendary.enums.CardType.VILLAINS;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LegendaryDatabase db = LegendaryDatabase.getInstance(this);

        initializeControls(db);
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
            case R.id.menuActionRandomize: {

                final List<CardControl> cardControls = Arrays.asList(new CardControl[]{
                        findViewById(R.id.cardControlMastermind),
                        findViewById(R.id.cardControlScheme),
                        findViewById(R.id.cardControlVillains1),
                        findViewById(R.id.cardControlVillains2),
                        findViewById(R.id.cardControlVillains3),
                        findViewById(R.id.cardControlVillains4),
                        findViewById(R.id.cardControlHenchmen1),
                        findViewById(R.id.cardControlHenchmen2),
                        findViewById(R.id.cardControlHeroes1),
                        findViewById(R.id.cardControlHeroes2),
                        findViewById(R.id.cardControlHeroes3),
                        findViewById(R.id.cardControlHeroes4),
                        findViewById(R.id.cardControlHeroes5),
                        findViewById(R.id.cardControlHeroes6)
                });

                randomizeSelections(Collections.singletonList(cardControls.get(0)));
                randomizeSelections(Collections.singletonList(cardControls.get(1)));
                randomizeSelections(cardControls.subList(2, 6));
                randomizeSelections(cardControls.subList(6, 8));
                randomizeSelections(cardControls.subList(8, 14));
            }
            break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void initializeControls(final LegendaryDatabase db) {
        populateCardSpinner(db, HERO);
        populateCardSpinner(db, MASTERMIND);
        populateCardSpinner(db, VILLAINS);
        populateCardSpinner(db, HENCHMEN);
        populateCardSpinner(db, SCHEME);
    }

    private void populateCardSpinner(final LegendaryDatabase db, final CardType cardType) {

        switch (cardType) {
            case HERO: {
                db.heroDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.cardControlHeroes1));
                db.heroDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.cardControlHeroes2));
                db.heroDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.cardControlHeroes3));
                db.heroDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.cardControlHeroes4));
                db.heroDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.cardControlHeroes5));
                db.heroDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.cardControlHeroes6));
            }
            break;
            case MASTERMIND: {
                db.mastermindDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.cardControlMastermind));
            }
            break;
            case VILLAINS: {
                db.villainsDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.cardControlVillains1));
                db.villainsDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.cardControlVillains2));
                db.villainsDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.cardControlVillains3));
                db.villainsDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.cardControlVillains4));
            }
            break;
            case HENCHMEN: {
                db.henchmenDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.cardControlHenchmen1));
                db.henchmenDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.cardControlHenchmen2));
            }
            break;
            case SCHEME: {
                db.schemeDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.cardControlScheme));
            }
            break;
        }
    }

    private Observer<List<String>> generateObserver(final int viewId) {
        return list -> {
            list.add(0, "None");

            final Spinner spinner = ((CardControl) findViewById(viewId)).getSpinner();
            final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getApplicationContext(),
                    R.layout.spinner_layout,
                    list);

            adapter.setDropDownViewResource(R.layout.spinner_layout);
            spinner.setAdapter(adapter);
        };
    }

    private void randomizeSelections(final List<CardControl> cardControls) {
        final Set<Integer> results = new HashSet<>(cardControls.size());

        for (final CardControl cardControl : cardControls) {
            if (!cardControl.getToggleLock().isChecked()) {

                final Spinner spinner = cardControl.getSpinner();
                final int numCards = spinner.getCount();
                int selection;

                do {
                    selection = new Random().nextInt(numCards - 1) + 1;
                } while (!results.add(selection));

                spinner.setSelection(selection);
            }
        }
    }
}

