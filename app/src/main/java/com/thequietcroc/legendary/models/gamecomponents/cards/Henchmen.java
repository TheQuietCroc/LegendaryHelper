package com.thequietcroc.legendary.models.gamecomponents.cards;

import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.daos.gamecomponents.cards.HenchmenDao;
import com.thequietcroc.legendary.database.daos.gamecomponents.cards.MastermindDao;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.HenchmenEntity;
import com.thequietcroc.legendary.database.entities.gamecomponents.cards.MastermindEntity;
import com.thequietcroc.legendary.utilities.DbAsyncTask;

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

            new DbAsyncTask(() -> {
                mastermindLeaderList.addAll(mastermindDao
                        .findAllByAlwaysLeadsHenchmenId(getId())
                        .stream()
                        .map(MastermindEntity::toModel)
                        .collect(Collectors.toList()));
            });
        }

        return mastermindLeaderList;
    }

    @Override
    public HenchmenEntity toEntity() {
        return new HenchmenEntity(this);
    }

    @Override
    public void dbSave() {
        final HenchmenDao henchmenDao = LegendaryDatabase.getInstance().henchmenDao();

        if (0 == getId()) {
            henchmenDao.insert(toEntity());
        } else {
            henchmenDao.update(toEntity());
        }
    }

    @Override
    public void dbDelete() {
        final HenchmenDao henchmenDao = LegendaryDatabase.getInstance().henchmenDao();

        henchmenDao.delete(toEntity());
    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof Henchmen)) {
            return false;
        }

        return super.equals(o);
    }
}
