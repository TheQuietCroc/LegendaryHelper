package com.thequietcroc.legendary.activities.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ToggleButton;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.models.gamecomponents.cards.Hero;

public class HeroInfoActivity extends CardInfoActivity {

    ToggleButton heroCovertToggleButton;
    ToggleButton heroInstinctToggleButton;
    ToggleButton heroRangedToggleButton;
    ToggleButton heroStrengthToggleButton;
    ToggleButton heroTechToggleButton;
    CheckBox heroGunCheckbox;
    CheckBox heroFlavorCheckbox;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.heroInfo));
        
        final View heroInfoControls = LayoutInflater.from(componentControlsLayout.getContext())
                .inflate(R.layout.card_info_hero_controls, componentControlsLayout, false);

        heroCovertToggleButton = heroInfoControls.findViewById(R.id.cardInfoHeroCovertToggle);
        heroInstinctToggleButton = heroInfoControls.findViewById(R.id.cardInfoHeroInstinctToggle);
        heroRangedToggleButton = heroInfoControls.findViewById(R.id.cardInfoHeroRangedToggle);
        heroStrengthToggleButton = heroInfoControls.findViewById(R.id.cardInfoHeroStrengthToggle);
        heroTechToggleButton = heroInfoControls.findViewById(R.id.cardInfoHeroTechToggle);
        heroGunCheckbox = heroInfoControls.findViewById(R.id.cardInfoHeroGunCheckbox);
        heroFlavorCheckbox = heroInfoControls.findViewById(R.id.cardInfoHeroFlavortextCheckbox);

        componentControlsLayout.addView(heroInfoControls);

        final Hero hero = (Hero) componentAtomicReference.get();

        heroCovertToggleButton.setChecked(hero.hasCovert());
        heroInstinctToggleButton.setChecked(hero.hasInstinct());
        heroRangedToggleButton.setChecked(hero.hasRanged());
        heroStrengthToggleButton.setChecked(hero.hasStrength());
        heroTechToggleButton.setChecked(hero.hasTech());
        heroGunCheckbox.setChecked(hero.hasGun());
        heroFlavorCheckbox.setChecked(hero.hasFlavorText());

        if (hero.isCustom()) {
            heroCovertToggleButton.setOnClickListener(v -> hero.setHasCovert(heroCovertToggleButton.isChecked()));
            heroInstinctToggleButton.setOnClickListener(v -> hero.setHasInstinct(heroInstinctToggleButton.isChecked()));
            heroRangedToggleButton.setOnClickListener(v -> hero.setHasRanged(heroRangedToggleButton.isChecked()));
            heroStrengthToggleButton.setOnClickListener(v -> hero.setHasStrength(heroStrengthToggleButton.isChecked()));
            heroTechToggleButton.setOnClickListener(v -> hero.setHasTech(heroTechToggleButton.isChecked()));
            heroGunCheckbox.setOnClickListener(v -> hero.setHasGun(heroGunCheckbox.isChecked()));
            heroFlavorCheckbox.setOnClickListener(v -> hero.setHasFlavorText(heroFlavorCheckbox.isChecked()));
        } else {
            heroCovertToggleButton.setEnabled(false);
            heroInstinctToggleButton.setEnabled(false);
            heroRangedToggleButton.setEnabled(false);
            heroStrengthToggleButton.setEnabled(false);
            heroTechToggleButton.setEnabled(false);
            heroGunCheckbox.setEnabled(false);
            heroFlavorCheckbox.setEnabled(false);
        }
    }
}
