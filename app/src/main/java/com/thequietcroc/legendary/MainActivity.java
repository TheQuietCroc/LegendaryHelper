package com.thequietcroc.legendary;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.thequietcroc.legendary.custom.views.CardControl;
import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.enums.CardType;

import java.util.List;

import static com.thequietcroc.legendary.enums.CardType.HENCHMEN;
import static com.thequietcroc.legendary.enums.CardType.HERO;
import static com.thequietcroc.legendary.enums.CardType.MASTERMIND;
import static com.thequietcroc.legendary.enums.CardType.SCHEME;
import static com.thequietcroc.legendary.enums.CardType.VILLAINS;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.thequietcroc.legendary.MESSAGE";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LegendaryDatabase db = LegendaryDatabase.getInstance(this);

        initializeControls(db);
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
}

