
/*
 *
 *  * Copyright (C) 2015 yelo.red
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 *
 */package red.yelo.data;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;

import java.util.Locale;

import red.yelo.utils.AppConstants.ChatStatus;
import red.yelo.utils.Logger;

/**
 * @author Anshul Kamboj Table representing a list of chat messages
 */
public class TableChatMessages {

    private static final String TAG = "TableChatMessages";

    public static final String NAME = "CHAT_MESSAGES";

    public static void create(final SQLiteDatabase db) {

        final String columnDef = TextUtils
                .join(SQLConstants.COMMA, new String[]{
                        String.format(Locale.US, SQLConstants.DATA_INTEGER_PK, BaseColumns._ID),
                        String.format(Locale.US, SQLConstants.DATA_TEXT, DatabaseColumns.CHAT_ID, ""),
                        String.format(Locale.US, SQLConstants.DATA_TEXT, DatabaseColumns.SERVER_CHAT_ID, ""),
                        String.format(Locale.US, SQLConstants.DATA_TEXT, DatabaseColumns.SENDER_ID, ""),
                        String.format(Locale.US, SQLConstants.DATA_TEXT, DatabaseColumns.SENDER_NAME, ""),
                        String.format(Locale.US, SQLConstants.DATA_TEXT, DatabaseColumns.SENDER_IMAGE, ""),
                        String.format(Locale.US, SQLConstants.DATA_TEXT, DatabaseColumns.RECEIVER_ID, ""),
                        String.format(Locale.US, SQLConstants.DATA_TEXT, DatabaseColumns.RECEIVER_NAME, ""),
                        String.format(Locale.US, SQLConstants.DATA_TEXT, DatabaseColumns.RECEIVER_IMAGE, ""),
                        String.format(Locale.US, SQLConstants.DATA_TEXT, DatabaseColumns.SENT_AT, ""),
                        String.format(Locale.US, SQLConstants.DATA_TEXT, DatabaseColumns.MESSAGE, ""),
                        String.format(Locale.US, SQLConstants.DATA_TEXT, DatabaseColumns.TIMESTAMP, ""),
                        String.format(Locale.US, SQLConstants.DATA_TEXT, DatabaseColumns.TIMESTAMP_HUMAN, ""),
                        String.format(Locale.US, SQLConstants.DATA_INTEGER, DatabaseColumns.TIMESTAMP_EPOCH, 0),
                        String.format(Locale.US, SQLConstants.DATA_INTEGER, DatabaseColumns.CHAT_STATUS, ChatStatus.FAILED)
                });

        Logger.d(TAG, "Column Def: %s", columnDef);
        db.execSQL(String
                .format(Locale.US, SQLConstants.CREATE_TABLE, NAME, columnDef));

    }

    @SuppressLint("UseSparseArrays")
    public static void upgrade(final SQLiteDatabase db, final int oldVersion,
                               final int newVersion) {


        //Add any data migration code here. Default is to drop and rebuild the table
    }
}
