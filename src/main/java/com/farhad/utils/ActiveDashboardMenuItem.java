package com.farhad.utils;

public enum ActiveDashboardMenu {
    OVERVIEW,
    ACCOUNTS,
    TRANSACTIONS,
    PRODUCTS,
    ADD_ACCOUNT,
    BE_MERCHANT,
    USER_SETTINGS;

    public static ActiveDashboardMenu current = OVERVIEW;
}
