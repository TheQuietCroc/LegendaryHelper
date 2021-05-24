package com.thequietcroc.legendary.custom.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.thequietcroc.legendary.R;

public class CardControl extends LinearLayout {

    private final Spinner spinner;
    private final ToggleButton toggleLock;
    private boolean isSetByMastermind = false;

    public CardControl(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        final LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.card_control, this, true);

        spinner = (Spinner) getChildAt(0);
        toggleLock = (ToggleButton) getChildAt(1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toggleLock.setEnabled(position > 0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        toggleLock.setOnCheckedChangeListener((buttonView, isChecked) ->
                spinner.setEnabled(!isChecked));

    }

    public CardControl(final Context context) {
        this(context, null);
    }

    public Spinner getSpinner() {
        return spinner;
    }

    public ToggleButton getToggleLock() {
        return toggleLock;
    }

    public boolean isSetBySetup() {
        return isSetByMastermind;
    }

    public void setSetBySetup(final boolean isSetByMastermind) {
        this.isSetByMastermind = isSetByMastermind;
    }
}
