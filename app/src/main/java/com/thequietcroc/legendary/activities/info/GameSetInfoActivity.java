package com.thequietcroc.legendary.activities.info;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thequietcroc.legendary.R;
import com.thequietcroc.legendary.custom.adapters.GameComponentRecyclerAdapter;
import com.thequietcroc.legendary.models.gamecomponents.GameSet;
import com.thequietcroc.legendary.models.gamecomponents.cards.Henchmen;
import com.thequietcroc.legendary.models.gamecomponents.cards.Hero;
import com.thequietcroc.legendary.models.gamecomponents.cards.Mastermind;
import com.thequietcroc.legendary.models.gamecomponents.cards.Scheme;
import com.thequietcroc.legendary.models.gamecomponents.cards.Villains;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

import java.util.List;

public class GameSetInfoActivity extends InfoActivity<GameSet> {

    RecyclerView mastermindsRecyclerView;
    RecyclerView schemesRecyclerView;
    RecyclerView villainsRecyclerView;
    RecyclerView henchmenRecyclerView;
    RecyclerView heroesRecyclerView;

    GameComponentRecyclerAdapter<Mastermind> mastermindsAdapter;
    GameComponentRecyclerAdapter<Scheme> schemesAdapter;
    GameComponentRecyclerAdapter<Villains> villainsAdapter;
    GameComponentRecyclerAdapter<Henchmen> henchmenAdapter;
    GameComponentRecyclerAdapter<Hero> heroesAdapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.gameSetInfo));

        final View gameSetInfoControls = LayoutInflater.from(componentControlsLayout.getContext())
                .inflate(R.layout.card_info_game_set_controls, componentControlsLayout, false);

        mastermindsRecyclerView = gameSetInfoControls.findViewById(R.id.gameSetInfoMastermindsRecycler);
        schemesRecyclerView = gameSetInfoControls.findViewById(R.id.gameSetInfoSchemesRecycler);
        villainsRecyclerView = gameSetInfoControls.findViewById(R.id.gameSetInfoVillainsRecycler);
        henchmenRecyclerView = gameSetInfoControls.findViewById(R.id.gameSetInfoHenchmenRecycler);
        heroesRecyclerView = gameSetInfoControls.findViewById(R.id.gameSetInfoHeroesRecycler);

        mastermindsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        schemesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        villainsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        henchmenRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        heroesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        componentControlsLayout.addView(gameSetInfoControls);

        new DbAsyncTask(() -> {

            final GameSet gameset = componentAtomicReference.get();

            final List<Mastermind> mastermindList = gameset.getMastermindList();
            final List<Scheme> schemeList = gameset.getSchemeList();
            final List<Villains> villainsList = gameset.getVillainsList();
            final List<Henchmen> henchmenList = gameset.getHenchmenList();
            final List<Hero> heroList = gameset.getHeroList();
            
            mastermindList.stream().forEach(mastermind -> {
                final Henchmen alwaysLeadsHenchmen = mastermind.getAlwaysLeadsHenchmen();

                if (alwaysLeadsHenchmen.getId() > 0) {
                    mastermind.setAlwaysLeadsHenchmen(henchmenList.get(henchmenList.indexOf(alwaysLeadsHenchmen)));
                }
                
                final Villains alwaysLeadsVillains = mastermind.getAlwaysLeadsVillains();

                if (alwaysLeadsVillains.getId() > 0) {
                    mastermind.setAlwaysLeadsVillains(villainsList.get(villainsList.indexOf(alwaysLeadsVillains)));
                }
            });

            villainsList.stream().forEach(villains -> {
                final Mastermind mastermindLeader = villains.getMastermindLeader();

                if (mastermindLeader.getId() > 0) {
                    villains.setMastermindLeader(mastermindList.get(mastermindList.indexOf(mastermindLeader)));
                }
            });

            henchmenList.stream().forEach(henchmen -> {
                final Mastermind mastermindLeader = henchmen.getMastermindLeader();

                if (mastermindLeader.getId() > 0) {
                    henchmen.setMastermindLeader(mastermindList.get(mastermindList.indexOf(mastermindLeader)));
                }
            });

            mastermindsAdapter = new GameComponentRecyclerAdapter<>(mastermindList, false);
            schemesAdapter = new GameComponentRecyclerAdapter<>(schemeList, false);
            villainsAdapter = new GameComponentRecyclerAdapter<>(villainsList, false);
            henchmenAdapter = new GameComponentRecyclerAdapter<>(henchmenList, false);
            heroesAdapter = new GameComponentRecyclerAdapter<>(heroList, false);

            new Handler(Looper.getMainLooper()).post(() -> {
                mastermindsRecyclerView.setAdapter(mastermindsAdapter);
                schemesRecyclerView.setAdapter(schemesAdapter);
                villainsRecyclerView.setAdapter(villainsAdapter);
                henchmenRecyclerView.setAdapter(henchmenAdapter);
                heroesRecyclerView.setAdapter(heroesAdapter);
            });
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        super.onPrepareOptionsMenu(menu);

        enabledSwitch.setOnClickListener(v -> {
            final boolean isEnabled = ((SwitchCompat) v).isChecked();

            new DbAsyncTask(() -> {
                componentAtomicReference.get().setEnabled(isEnabled);

                new Handler(Looper.getMainLooper()).post(() -> {

                    mastermindsAdapter.notifyDataSetChanged();
                    schemesAdapter.notifyDataSetChanged();
                    villainsAdapter.notifyDataSetChanged();
                    henchmenAdapter.notifyDataSetChanged();
                    heroesAdapter.notifyDataSetChanged();
                });
            });
        });

        return true;
    }
}
