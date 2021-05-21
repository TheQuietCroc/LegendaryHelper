package com.thequietcroc.legendary;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LegendaryDatabase db = LegendaryDatabase.getInstance(this);

        populateControls(db);

//        final LegendaryDatabase db = LegendaryDatabase.getInstance(this);
//
//        final Observer<Henchmen> nameObserver = h -> dbText.setText(h.name);
//
//        db.henchmenDao().findByName("Hand Ninjas").observe(this, nameObserver);
    }

//    private void fillCardListDisplay(final List<? extends BaseCard> cards) {
//        final RecyclerView cardListDisplay = findViewById(R.id.cardListDisplay);
//        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//
//        cardListDisplay.setLayoutManager(layoutManager);
//        cardListDisplay.setAdapter(new CardsAdapter(this, cards));
//    }

    private void populateControls(final LegendaryDatabase db) {
        populateCardSpinner(db, HERO);
        populateCardSpinner(db, MASTERMIND);
        populateCardSpinner(db, VILLAINS);
        populateCardSpinner(db, HENCHMEN);
        populateCardSpinner(db, SCHEME);
    }

    private void populateCardSpinner(final LegendaryDatabase db, final CardType cardType) {

        switch(cardType) {
            case HERO: {
                db.heroDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.spinnerHeroes1));
                db.heroDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.spinnerHeroes2));
                db.heroDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.spinnerHeroes3));
                db.heroDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.spinnerHeroes4));
                db.heroDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.spinnerHeroes5));
                db.heroDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.spinnerHeroes6));
            } break;
            case MASTERMIND: {
                db.mastermindDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.spinnerMasterminds));
            } break;
            case VILLAINS: {
                db.villainsDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.spinnerVillains1));
                db.villainsDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.spinnerVillains2));
                db.villainsDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.spinnerVillains3));
                db.villainsDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.spinnerVillains4));
            } break;
            case HENCHMEN: {
                db.henchmenDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.spinnerHenchmen1));
                db.henchmenDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.spinnerHenchmen2));
            } break;
            case SCHEME: {
                db.schemeDao().getAllFilteredNames()
                        .observe(this, generateObserver(R.id.spinnerScheme));
            } break;
        }
    }

    private Observer<List<String>> generateObserver(final int viewId) {
        return list -> {
            final Spinner spinner = findViewById(viewId);
            final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getApplicationContext(),
                    R.layout.spinner_layout,
                    list);

            adapter.setDropDownViewResource(R.layout.spinner_layout);
            spinner.setAdapter(adapter);
        };
    }
}