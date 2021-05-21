package com.farhad.utils;

public enum ActiveDashboardMenuItem {
    OVERVIEW,
    ACCOUNTS,
    TRANSACTIONS,
    PRODUCTS,
    ADD_ACCOUNT,
    BE_MERCHANT,
    USER_SETTINGS;

    public static ActiveDashboardMenuItem current = OVERVIEW;
}
