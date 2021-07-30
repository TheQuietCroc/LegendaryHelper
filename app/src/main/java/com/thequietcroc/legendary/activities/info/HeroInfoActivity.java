package com.thequietcroc.legendary.activities.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ToggleButton;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.models.gamecomponents.cards.Hero;

public class HeroInfoActivity extends CardInfoActivity<Hero> {

    Hero hero;
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

        hero = componentAtomicReference.get();

        final View heroInfoControls = LayoutInflater.from(componentControlsLayout.getContext())
                .inflate(R.layout.card_info_hero_controls, componentControlsLayout, false);

        componentControlsLayout.addView(heroInfoControls);

        heroCovertToggleButton = heroInfoControls.findViewById(R.id.cardInfoHeroCovertToggle);
        heroInstinctToggleButton = heroInfoControls.findViewById(R.id.cardInfoHeroInstinctToggle);
        heroRangedToggleButton = heroInfoControls.findViewById(R.id.cardInfoHeroRangedToggle);
        heroStrengthToggleButton = heroInfoControls.findViewById(R.id.cardInfoHeroStrengthToggle);
        heroTechToggleButton = heroInfoControls.findViewById(R.id.cardInfoHeroTechToggle);
        heroGunCheckbox = heroInfoControls.findViewById(R.id.cardInfoHeroGunCheckbox);
        heroFlavorCheckbox = heroInfoControls.findViewById(R.id.cardInfoHeroFlavortextCheckbox);

        heroCovertToggleButton.setChecked(hero.hasCovert());
        heroInstinctToggleButton.setChecked(hero.hasInstinct());
        heroRangedToggleButton.setChecked(hero.hasRanged());
        heroStrengthToggleButton.setChecked(hero.hasStrength());
        heroTechToggleButton.setChecked(hero.hasTech());
        heroGunCheckbox.setChecked(hero.hasGun());
        heroFlavorCheckbox.setChecked(hero.hasFlavorText());

        if (!hero.isCustom()) {
            heroCovertToggleButton.setEnabled(false);
            heroInstinctToggleButton.setEnabled(false);
            heroRangedToggleButton.setEnabled(false);
            heroStrengthToggleButton.setEnabled(false);
            heroTechToggleButton.setEnabled(false);
            heroGunCheckbox.setEnabled(false);
            heroFlavorCheckbox.setEnabled(false);
        }
    }

    @Override
    protected void saveComponent() {

        hero.setHasCovert(heroCovertToggleButton.isChecked());
        hero.setHasInstinct(heroInstinctToggleButton.isChecked());
        hero.setHasRanged(heroRangedToggleButton.isChecked());
        hero.setHasStrength(heroStrengthToggleButton.isChecked());
        hero.setHasTech(heroTechToggleButton.isChecked());
        hero.setHasGun(heroGunCheckbox.isChecked());
        hero.setHasFlavorText(heroFlavorCheckbox.isChecked());

        super.saveComponent();
    }

    @Override
    protected void onGameSetChanged() {

    }
}
