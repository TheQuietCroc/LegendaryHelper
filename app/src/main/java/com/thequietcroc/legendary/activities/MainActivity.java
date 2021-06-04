package com.thequietcroc.legendary.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.utilities.GameSetup;

public class MainActivity extends AppCompatActivity {

    private LegendaryDatabase db;
    private GameSetup gameSetup;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = LegendaryDatabase.getInstance(this);

        final ConstraintLayout villainsContainer = findViewById(R.id.containerVillains);
        final ConstraintLayout henchmenContainer = findViewById(R.id.containerHenchmen);
        final ConstraintLayout heroesContainer = findViewById(R.id.containerHeroes);

        final MaterialButtonToggleGroup buttonGroupPlayers = findViewById(R.id.buttonGroupPlayers);

        gameSetup = new GameSetup(this,
                db,
                buttonGroupPlayers,
                findViewById(R.id.cardControlMastermind),
                findViewById(R.id.cardControlScheme),
                villainsContainer,
                henchmenContainer,
                heroesContainer);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu_actions, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuActionFilter: {
                openFilterActivity();
            }
            break;
            case R.id.menuActionRandomize: {
                gameSetup.newSetup();
            }
            break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void openFilterActivity() {
        final Intent intent = new Intent(this, FilterMenuActivity.class);
        startActivity(intent);
    }
}

