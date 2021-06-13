package com.thequietcroc.legendary.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.enums.ItemType;


public class FilterSelectMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_select_menu);

        if (null != getActionBar()) {
            getActionBar().setTitle(R.string.activityFilter);
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(R.string.activityFilter);
        }

        findViewById(R.id.buttonFilterGameSets).setOnClickListener(v -> openFilterActivity(ItemType.GAMESET));

        findViewById(R.id.buttonFilterMasterminds).setOnClickListener(v -> openFilterActivity(ItemType.MASTERMIND));

        findViewById(R.id.buttonFilterSchemes).setOnClickListener(v -> openFilterActivity(ItemType.SCHEME));

        findViewById(R.id.buttonFilterVillains).setOnClickListener(v -> openFilterActivity(ItemType.VILLAINS));

        findViewById(R.id.buttonFilterHenchmen).setOnClickListener(v -> openFilterActivity(ItemType.HENCHMEN));

        findViewById(R.id.buttonFilterHeroes).setOnClickListener(v -> openFilterActivity(ItemType.HERO));
    }

    private void openFilterActivity(final ItemType type) {
        final Intent intent;

        switch (type) {
            case GAMESET:
                intent = new Intent(this, FilterGameSetActivity.class);
                break;
            case HERO:
                intent = new Intent(this, FilterHeroActivity.class);
                break;
            case MASTERMIND:
                intent = new Intent(this, FilterMastermindActivity.class);
                break;
            case VILLAINS:
                intent = new Intent(this, FilterVillainsActivity.class);
                break;
            case HENCHMEN:
                intent = new Intent(this, FilterHenchmenActivity.class);
                break;
            case SCHEME:
                intent = new Intent(this, FilterSchemeActivity.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        startActivity(intent);
    }
}