package com.thequietcroc.legendary.activities.info;

import android.os.Bundle;

import com.thequietcroc.legendary.R;

public class HeroInfoActivity extends CardInfoActivity {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(getString(R.string.heroInfo));
    }
}
