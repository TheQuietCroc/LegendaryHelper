package com.thequietcroc.legendary.activities.info;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.models.BaseItem;
import com.thequietcroc.legendary.models.gamecomponents.BaseGameComponent;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.atomic.AtomicReference;

public abstract class InfoActivity extends AppCompatActivity {

    final LegendaryDatabase db = LegendaryDatabase.getInstance();
    final AtomicReference<BaseGameComponent> componentAtomicReference = new AtomicReference<>();

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component_info);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.info_activity_menu_actions, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case R.id.infoMenuDelete: {
                new AlertDialog.Builder(this)
                        .setMessage(String.format("%s \"%s\"?",
                                getString(R.string.confirmDeleteMessage),
                                componentAtomicReference.get().getName()))
                        .setTitle(getString(R.string.confirmDeleteTitle))
                        .setPositiveButton(R.string.yes, (dialog, which) -> deleteComponent())
                        .setNegativeButton(R.string.cancel, (dialog, which) -> {})
                        .create();
            } break;
            case R.id.infoMenuSave: {
                saveComponent();
            } break;
            default:
                new AlertDialog.Builder(this)
                        .setMessage(R.string.confirmSave)
                        .setTitle(R.string.save)
                        .setPositiveButton(R.string.yes, (dialog, which) -> {
                            saveComponent();
                            NavUtils.navigateUpFromSameTask(this);
                        })
                        .setNeutralButton(R.string.cancel, (dialog, which) -> {})
                        .setNegativeButton(R.string.no, (dialog, which) -> NavUtils.navigateUpFromSameTask(this))
                        .create();
            break;
        }

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        final boolean isCustom = componentAtomicReference.get().isCustom();

        menu.findItem(R.id.infoMenuDelete).setVisible(isCustom);
        menu.findItem(R.id.infoMenuSave).setVisible(isCustom);

        return true;
    }

    protected void setTitle(final String placeholder) {
        final BaseGameComponent component = componentAtomicReference.get();
        final String title;

        if (StringUtils.isBlank(component.getName())) {
            title = placeholder;
        } else {
            title = component.getName();
        }

        if (null != getActionBar()) {
            getActionBar().setTitle(title);
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(title);
        }
    }

    private void deleteComponent() {
        final BaseItem component = componentAtomicReference.get();

        component.dbDelete();
        NavUtils.navigateUpFromSameTask(this);

        Toast.makeText(this,
                String.format("\"%s\" %s",
                        component.getName(),
                        getString(R.string.deleted)),
                Toast.LENGTH_SHORT).show();
    }

    private void saveComponent() {
        final BaseItem component = componentAtomicReference.get();

        component.dbSave();

        Toast.makeText(this,
                String.format("\"%s\" %s",
                        component.getName(),
                        getString(R.string.saved)),
                Toast.LENGTH_SHORT).show();
    }
}