package com.thequietcroc.legendary.models;

import com.thequietcroc.legendary.database.entities.BaseEntity;

import java.util.Comparator;

public abstract class BaseItem {

    private int id = 0;
    private String name = "None";

    public BaseItem() {}

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

    public static class BaseItemComparator<T extends BaseItem> implements Comparator<T> {
        @Override
        public int compare(final T t1, final T t2) {
            return t1.getName().compareTo(t2.getName());
        }
    }
}
