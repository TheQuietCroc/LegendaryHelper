package com.thequietcroc.legendary.activities.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.NavUtils;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.models.gamecomponents.BaseGameComponent;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.concurrent.atomic.AtomicReference;

import static com.thequietcroc.legendary.activities.filters.FilterActivity.COMPONENT_EXTRA;

public abstract class InfoActivity extends AppCompatActivity {

    final AtomicReference<BaseGameComponent> componentAtomicReference = new AtomicReference<>();

    EditText infoNameEditText;
    LinearLayout componentControlsLayout;
    SwitchCompat enabledSwitch;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component_info);

        infoNameEditText = findViewById(R.id.infoName);
        componentControlsLayout = findViewById(R.id.componentControlsLayout);

        final Intent intent = getIntent();

        componentAtomicReference.set((BaseGameComponent) intent.getSerializableExtra(COMPONENT_EXTRA));

        infoNameEditText.setText(componentAtomicReference.get().getName());

        if (!componentAtomicReference.get().isCustom()) {
            infoNameEditText.setEnabled(false);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        final BaseGameComponent component = componentAtomicReference.get();
        final boolean isCustom = component.isCustom();

        menu.findItem(R.id.infoMenuDelete).setVisible(isCustom);
        menu.findItem(R.id.infoMenuSave).setVisible(isCustom);

        enabledSwitch = menu.findItem(R.id.infoMenuEnabled)
                .getActionView().findViewById(R.id.menuItemSwitch);

        enabledSwitch.setChecked(component.isEnabled());

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
                                saveAndExit();
                            })
                            .setNeutralButton(R.string.cancel, (dialog, which) -> {})
                            .setNegativeButton(R.string.no,
                                    (dialog, which) -> NavUtils.navigateUpFromSameTask(this))
                            .create()
                            .show();
                } else {
                    saveAndExit();
                }
            break;
        }

        return true;
    }

    private void saveAndExit() {
        new DbAsyncTask(() -> {
            saveComponent();
            NavUtils.navigateUpFromSameTask(this);
        });
    }

    protected void setTitle(final String title) {

        if (null != getActionBar()) {
            getActionBar().setTitle(title);
        } else if (null != getSupportActionBar()) {
            getSupportActionBar().setTitle(title);
        }
    }

    private void deleteComponent() {
        new DbAsyncTask(() -> {
            final BaseGameComponent component = componentAtomicReference.get();

            component.dbDelete();
            NavUtils.navigateUpFromSameTask(this);

            Toast.makeText(this,
                    String.format("\"%s\" %s",
                            component.getName(),
                            getString(R.string.deleted)),
                    Toast.LENGTH_SHORT).show();
        });
    }

    protected void saveComponent() {
        final BaseGameComponent component = componentAtomicReference.get();

        component.setName(infoNameEditText.getText().toString());
        component.setEnabled(enabledSwitch.isChecked());

        component.dbSave();

        Toast.makeText(this,
                String.format("\"%s\" %s",
                        component.getName(),
                        getString(R.string.saved)),
                Toast.LENGTH_SHORT).show();
    }
}