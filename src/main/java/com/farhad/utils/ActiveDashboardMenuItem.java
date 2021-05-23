package com.farhad.utils;

public enum ActiveDashboardMenuItem {
    OVERVIEW,
    ACCOUNTS,
    TRANSACTIONS,
    ADD_ACCOUNT,
    USER_SETTINGS;

    public static ActiveDashboardMenuItem current = OVERVIEW;
}
