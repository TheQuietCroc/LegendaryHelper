package com.thequietcroc.legendary.database.entities;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import com.thequietcroc.legendary.models.BaseItem;

public abstract class BaseEntity {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Integer id;

    @NonNull
    private String name = "";

    public BaseEntity() {
        this.id = 0;
        this.name = "None";
    }

    public BaseEntity(final BaseItem baseItem) {
        this.id = baseItem.getId();
        this.name = baseItem.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull final String name) {
        this.name = name;
    }

    public abstract BaseItem toModel();

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof BaseEntity)) {
            return false;
        }

        final BaseEntity b = (BaseEntity) o;

        return id.equals(b.id);
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
