package com.thequietcroc.legendary.activities.info;

import android.content.Intent;
import android.os.Bundle;

import com.thequietcroc.legendary.models.gamecomponents.cards.Hero;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.atomic.AtomicReference;

import static com.thequietcroc.legendary.activities.filters.FilterActivity.COMPONENT_EXTRA;

public class HeroInfoActivity extends InfoActivity {

    final AtomicReference<Hero> heroAtomicReference = new AtomicReference<>(new Hero());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = getIntent();
        if (intent.hasExtra(COMPONENT_EXTRA)) {
            heroAtomicReference.set((Hero) intent.getSerializableExtra(COMPONENT_EXTRA));
        }
    }

    @Override
    protected void setTitle() {
        final Hero hero = heroAtomicReference.get();
        final String title;

        if (StringUtils.isBlank(hero.getName())) {
            title = "New Hero";
        } else {
            title = hero.getName();
        }

        if (null != getActionBar()) {
            getActionBar().setTitle(title);
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(title);
        }
    }
}
