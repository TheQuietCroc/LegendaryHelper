package com.thequietcroc.legendary.activities.info;

import android.content.Intent;
import android.os.Bundle;

import com.thequietcroc.legendary.models.gamecomponents.cards.Villains;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.atomic.AtomicReference;

import static com.thequietcroc.legendary.activities.filters.FilterActivity.COMPONENT_EXTRA;

public class VillainsInfoActivity extends InfoActivity {

    final AtomicReference<Villains> VillainsAtomicReference = new AtomicReference<>(new Villains());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = getIntent();
        if (intent.hasExtra(COMPONENT_EXTRA)) {
            VillainsAtomicReference.set((Villains) intent.getSerializableExtra(COMPONENT_EXTRA));
        }
    }

    @Override
    protected void setTitle() {
        final Villains Villains = VillainsAtomicReference.get();
        final String title;

        if (StringUtils.isBlank(Villains.getName())) {
            title = "New Villains";
        } else {
            title = Villains.getName();
        }

        if (null != getActionBar()) {
            getActionBar().setTitle(title);
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(title);
        }
    }
}
