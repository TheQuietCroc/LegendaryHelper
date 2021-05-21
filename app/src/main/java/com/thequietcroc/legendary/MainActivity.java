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

            } break;
            case MASTERMIND: {
                final Observer<List<String>> observer = list -> {
                    final Spinner spinner = findViewById(R.id.spinnerMasterminds);
                    final ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            getApplicationContext(),
                            R.layout.spinner_layout,
                            list);

                    adapter.setDropDownViewResource(R.layout.spinner_layout);
                    spinner.setAdapter(adapter);
                };
                db.mastermindDao().getAllFilteredNames().observe(this, observer);
            } break;
            case VILLAINS: {

            } break;
            case HENCHMEN: {

            } break;
            case SCHEME: {

            } break;
        }
    }
}