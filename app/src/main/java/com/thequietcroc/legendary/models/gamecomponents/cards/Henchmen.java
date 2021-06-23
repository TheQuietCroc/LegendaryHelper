package com.thequietcroc.legendary.models.gamecomponents.cards;

import android.os.AsyncTask;

import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.daos.gamecomponents.cards.MastermindDao;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HenchmenEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.MastermindEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Henchmen extends BaseCard {

    private final List<Mastermind> mastermindLeaderList = Collections.synchronizedList(new ArrayList<>());

    public Henchmen() {
        super();
    }

    public Henchmen(final HenchmenEntity henchmenEntity) {
        super(henchmenEntity);
    }

    public Henchmen(final String name) {
        super(name);
    }

    public List<Mastermind> getMastermindLeaderList() {
        if (mastermindLeaderList.isEmpty()) {

            final MastermindDao mastermindDao = LegendaryDatabase.getInstance().mastermindDao();

            try {
                mastermindLeaderList.addAll(mastermindDao
                        .findAllByAlwaysLeadsHenchmenId(getId())
                        .stream()
                        .map(MastermindEntity::toModel)
                        .collect(Collectors.toList()));
            } catch (final IllegalStateException e) {
                AsyncTask.execute(() -> {
                    mastermindLeaderList.addAll(mastermindDao
                            .findAllByAlwaysLeadsHenchmenId(getId())
                            .stream()
                            .map(MastermindEntity::toModel)
                            .collect(Collectors.toList()));
                });
            }
        }

        return mastermindLeaderList;
    }

    @Override
    public HenchmenEntity toEntity() {
        return new HenchmenEntity(this);
    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof Henchmen)) {
            return false;
        }

        return super.equals(o);
    }
}
