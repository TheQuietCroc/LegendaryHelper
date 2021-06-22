package com.thequietcroc.legendary.models;

import com.thequietcroc.legendary.database.entities.BaseEntity;

public abstract class BaseItem {

    private int id = -1;
    private String name;

    public BaseItem(final BaseEntity baseEntity) {
        setId(baseEntity.getId());
        setName(baseEntity.getName());
    }

    public BaseItem(final String name) {
        setName(name);
    }

    public int getId() {
        return id;
    }

    private void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public abstract BaseEntity toEntity();

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof BaseItem)) {
            return false;
        }

        final BaseItem b = (BaseItem) o;

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
