package com.thequietcroc.legendary.custom.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ToggleButton;

import com.thequietcroc.legendary.R;

public class CardControl extends LinearLayout {

    private final Spinner spinner;
    private final ToggleButton toggleLock;

    public CardControl(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        final LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.card_control, this, true);

        spinner = (Spinner) getChildAt(0);
        spinner.setTag(0);

        toggleLock = (ToggleButton) getChildAt(1);
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
}
