package com.thequietcroc.legendary.models.gamecomponents;

import com.thequietcroc.legendary.database.LegendaryDatabase;
import com.thequietcroc.legendary.database.entities.gamecomponents.KeywordEntity;

public class Keyword extends BaseGameComponent {

    private String definition = "";

    public Keyword(final KeywordEntity keywordEntity) {
        super(keywordEntity);
    }

    public Keyword(final String name) {
        super(name);
    }

    public void setDefinition(final String definition) {
        this.definition = definition;
    }

    public String getDefinition() {
        return definition;
    }

    @Override
    public KeywordEntity toEntity() {
        return new KeywordEntity(this);
    }

    @Override
    public void dbSave() {
        dbSave(LegendaryDatabase.getInstance().keywordDao(), toEntity());
    }

    @Override
    public void dbDelete() {

    }
}
