package com.thequietcroc.legendary.activities.info;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.NavUtils;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.models.BaseItem;
import com.thequietcroc.legendary.models.gamecomponents.BaseGameComponent;

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
    public boolean onPrepareOptionsMenu(final Menu menu) {
        final BaseGameComponent component = componentAtomicReference.get();
        final boolean isCustom = component.isCustom();

        menu.findItem(R.id.infoMenuDelete).setVisible(isCustom);
        menu.findItem(R.id.infoMenuSave).setVisible(isCustom);

        final SwitchCompat enabledSwitch = menu.findItem(R.id.infoMenuEnabled)
                .getActionView().findViewById(R.id.menuItemSwitch);

        enabledSwitch.setChecked(component.isEnabled());
        enabledSwitch.setOnClickListener(v ->
                component.setEnabled(((SwitchCompat) v).isChecked()));

        return true;
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
                        .create()
                        .show();
            } break;
            case R.id.infoMenuSave: {
                saveComponent();
            } break;
            default:
                if (componentAtomicReference.get().isCustom()) {
                    new AlertDialog.Builder(this)
                            .setMessage(R.string.confirmSave)
                            .setTitle(R.string.save)
                            .setPositiveButton(R.string.yes, (dialog, which) -> {
                                saveComponent();
                                NavUtils.navigateUpFromSameTask(this);
                            })
                            .setNeutralButton(R.string.cancel, (dialog, which) -> {})
                            .setNegativeButton(R.string.no,
                                    (dialog, which) -> NavUtils.navigateUpFromSameTask(this))
                            .create()
                            .show();
                } else {
                    NavUtils.navigateUpFromSameTask(this);
                }
            break;
        }

        return true;
    }

    protected void setTitle() {
        final BaseGameComponent component = componentAtomicReference.get();

        if (null != getActionBar()) {
            getActionBar().setTitle(component.getName());
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(component.getName());
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

    protected void saveComponent() {
        final BaseItem component = componentAtomicReference.get();

        component.dbSave();

        Toast.makeText(this,
                String.format("\"%s\" %s",
                        component.getName(),
                        getString(R.string.saved)),
                Toast.LENGTH_SHORT).show();
    }
}