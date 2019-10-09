package com.test.lab10;

import android.provider.BaseColumns;

public final class ReaderContract {

    private ReaderContract() {
    }

    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME_USER = "User";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_PASSWORD = "Password";
        public static final String COLUMN_NAME_TYPE = "Type";
    }

    public static class MessageEntry implements BaseColumns {
        public static final String TABLE_NAME_MESSAGE = "Message";
        public static final String COLUMN_NAME_USER = "User";
        public static final String COLUMN_NAME_SUBJECT = "Subject";
        public static final String COLUMN_NAME_MESSAGE = "Message";
    }

}
