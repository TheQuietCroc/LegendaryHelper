package com.thequietcroc.legendary.enums;

public enum CardType {
    HERO("Hero", "tblHeroes"),
    MASTERMIND("Mastermind", "tblMasterminds"),
    VILLAINS("Villains", "tblVillains"),
    HENCHMEN("Henchmen", "tblHenchmen"),
    SCHEME("Scheme", "tblSchemes");

    public final String name;
    public final String tableName;

    CardType(final String name, final String tableName) {
        this.name = name;
        this.tableName = tableName;
    }
}
