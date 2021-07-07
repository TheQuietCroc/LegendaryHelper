package com.thequietcroc.legendary.activities.info;

import android.content.Intent;
import android.os.Bundle;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.models.gamecomponents.cards.Hero;

import static com.thequietcroc.legendary.activities.filters.FilterActivity.COMPONENT_EXTRA;

public class HeroInfoActivity extends InfoActivity {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = getIntent();
        if (intent.hasExtra(COMPONENT_EXTRA)) {
            componentAtomicReference.set((Hero) intent.getSerializableExtra(COMPONENT_EXTRA));
        }

        setTitle(String.format("%s %s", getString(R.string.custom), getString(R.string.hero)));
    }
}
