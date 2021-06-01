package com.thequietcroc.legendary.database.entities;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

public abstract class BaseEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof BaseEntity)) {
            return false;
        }

        final BaseEntity b = (BaseEntity) o;

        return id == b.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
