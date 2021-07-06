package com.thequietcroc.legendary.activities.info;

import android.content.Intent;
import android.os.Bundle;

import com.thequietcroc.legendary.models.gamecomponents.cards.Henchmen;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.atomic.AtomicReference;

import static com.thequietcroc.legendary.activities.filters.FilterActivity.COMPONENT_EXTRA;

public class HenchmenInfoActivity extends InfoActivity {

    final AtomicReference<Henchmen> HenchmenAtomicReference = new AtomicReference<>(new Henchmen());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = getIntent();
        if (intent.hasExtra(COMPONENT_EXTRA)) {
            HenchmenAtomicReference.set((Henchmen) intent.getSerializableExtra(COMPONENT_EXTRA));
        }
    }

    @Override
    protected void setTitle() {
        final Henchmen Henchmen = HenchmenAtomicReference.get();
        final String title;

        if (StringUtils.isBlank(Henchmen.getName())) {
            title = "New Henchmen";
        } else {
            title = Henchmen.getName();
        }

        if (null != getActionBar()) {
            getActionBar().setTitle(title);
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(title);
        }
    }
}
