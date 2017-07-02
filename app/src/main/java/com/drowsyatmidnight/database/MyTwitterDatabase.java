package com.drowsyatmidnight.database;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = MyTwitterDatabase.NAME, version = MyTwitterDatabase.VERSION)
public class MyTwitterDatabase {

    public static final String NAME = "RestClientDatabase";

    public static final int VERSION = 1;
}
