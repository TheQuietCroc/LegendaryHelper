package com.thequietcroc.legendary.models.gamecomponents.cards;

import android.os.AsyncTask;

import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.daos.gamecomponents.cards.MastermindDao;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.MastermindEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.VillainsEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Villains extends BaseCard {

    private final List<Mastermind> mastermindLeaderList = Collections.synchronizedList(new ArrayList<>());

    public Villains() {
        super();
    }

    public Villains(final VillainsEntity villainsEntity) {
        super(villainsEntity);
    }

    public Villains(final String name) {
        super(name);
    }

    public List<Mastermind> getMastermindLeaderList() {
        if (mastermindLeaderList.isEmpty()) {

            final MastermindDao mastermindDao = LegendaryDatabase.getInstance().mastermindDao();

            try {
                mastermindLeaderList.addAll(mastermindDao
                        .findAllByAlwaysLeadsVillainsId(getId())
                        .stream()
                        .map(MastermindEntity::toModel)
                        .collect(Collectors.toList()));
            } catch (final IllegalStateException e) {
                AsyncTask.execute(() -> {
                    mastermindLeaderList.addAll(mastermindDao
                            .findAllByAlwaysLeadsVillainsId(getId())
                            .stream()
                            .map(MastermindEntity::toModel)
                            .collect(Collectors.toList()));
                });
            }
        }

        return mastermindLeaderList;
    }

    @Override
    public VillainsEntity toEntity() {
        return new VillainsEntity(this);
    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof Villains)) {
            return false;
        }

        return super.equals(o);
    }
}
