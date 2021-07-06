package com.thequietcroc.legendary.activities.info;

import android.content.Intent;
import android.os.Bundle;

import com.thequietcroc.legendary.models.gamecomponents.cards.Mastermind;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.atomic.AtomicReference;

import static com.thequietcroc.legendary.activities.filters.FilterActivity.COMPONENT_EXTRA;

public class MastermindInfoActivity extends InfoActivity {

    final AtomicReference<Mastermind> MastermindAtomicReference = new AtomicReference<>(new Mastermind());

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = getIntent();
        if (intent.hasExtra(COMPONENT_EXTRA)) {
            MastermindAtomicReference.set((Mastermind) intent.getSerializableExtra(COMPONENT_EXTRA));
        }
    }

    @Override
    protected void setTitle() {
        final Mastermind Mastermind = MastermindAtomicReference.get();
        final String title;

        if (StringUtils.isBlank(Mastermind.getName())) {
            title = "New Mastermind";
        } else {
            title = Mastermind.getName();
        }

        if (null != getActionBar()) {
            getActionBar().setTitle(title);
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(title);
        }
    }
}
