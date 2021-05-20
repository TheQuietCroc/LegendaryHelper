package com.thequietcroc.legendary;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.thequietcroc.legendary.database.LegendaryDatabase;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.thequietcroc.legendary.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LegendaryDatabase db = LegendaryDatabase.getInstance(this);

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
}