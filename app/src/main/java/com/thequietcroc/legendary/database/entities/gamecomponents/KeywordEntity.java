package com.thequietcroc.legendary.database.entities.gamecomponents;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

import com.thequietcroc.legendary.models.gamecomponents.Keyword;

@Entity(
        tableName = "tblKeywords",
        indices = {
                @Index(
                        name = "keywordIndex",
                        value = "name"
                )
        }
)
public class KeywordEntity extends BaseGameComponentEntity {

    @ColumnInfo
    private String definition;

    public KeywordEntity() {
        super();
    }

    public KeywordEntity(final Keyword keyword) {
        super(keyword);

        setDefinition(keyword.getDefinition());
    }

    public void setDefinition(final String definition) {
        this.definition = definition;
    }

    public String getDefinition() {
        return definition;
    }

    @Override
    public Keyword toModel() {
        return new Keyword(this);
    }

    @Override
    public boolean equals(final Object o) {

        if (!(o instanceof KeywordEntity)) {
            return false;
        }

        return super.equals(o);
    }
}
