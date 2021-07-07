package com.thequietcroc.legendary.activities.info;

import android.content.Intent;
import android.os.Bundle;

import com.thequietcroc.legendary.models.gamecomponents.cards.Henchmen;

import static com.thequietcroc.legendary.activities.filters.FilterActivity.COMPONENT_EXTRA;

public class HenchmenInfoActivity extends InfoActivity {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = getIntent();

        if (intent.hasExtra(COMPONENT_EXTRA)) {
            componentAtomicReference.set((Henchmen) intent.getSerializableExtra(COMPONENT_EXTRA));
        }

        setTitle();
    }
}
