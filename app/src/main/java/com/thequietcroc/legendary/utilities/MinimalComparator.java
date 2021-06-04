package com.thequietcroc.legendary.utilities;

import com.thequietcroc.legendary.database.entities.BaseEntity;

import java.util.Comparator;

public class MinimalComparator<T extends BaseEntity.Minimal> implements Comparator<T> {
    @Override
    public int compare(T m1, T m2) {
        return m1.getName().compareTo(m2.getName());
    }
}
