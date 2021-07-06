package com.thequietcroc.legendary.activities.info;

import android.content.Intent;
import android.os.Bundle;

import com.thequietcroc.legendary.models.gamecomponents.cards.Scheme;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.atomic.AtomicReference;

import static com.thequietcroc.legendary.activities.filters.FilterActivity.COMPONENT_EXTRA;

public class SchemeInfoActivity extends InfoActivity {

    final AtomicReference<Scheme> SchemeAtomicReference = new AtomicReference<>(new Scheme());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = getIntent();
        if (intent.hasExtra(COMPONENT_EXTRA)) {
            SchemeAtomicReference.set((Scheme) intent.getSerializableExtra(COMPONENT_EXTRA));
        }
    }

    @Override
    protected void setTitle() {
        final Scheme Scheme = SchemeAtomicReference.get();
        final String title;

        if (StringUtils.isBlank(Scheme.getName())) {
            title = "New Scheme";
        } else {
            title = Scheme.getName();
        }

        if (null != getActionBar()) {
            getActionBar().setTitle(title);
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(title);
        }
    }
}
