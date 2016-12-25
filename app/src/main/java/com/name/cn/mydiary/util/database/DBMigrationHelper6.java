package com.name.cn.mydiary.util.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * updateHelper
 * Created by Administrator on 2016-12-25.
 */

public class DBMigrationHelper6 extends AbstractMigrationHelper {

/* Upgrade from DB schema 6 to schema 7 , version numbers are just examples*/

    public void onUpgrade(SQLiteDatabase db) {

    /* Create a temporal table where you will copy all the data from the previous table that you need to modify with a non supported sqlite operation */
        db.execSQL("CREATE TABLE " + "'post2' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'POST_ID' INTEGER UNIQUE ," + // 1: postId
                "'USER_ID' INTEGER," + // 2: userId
                "'VERSION' INTEGER," + // 3: version
                "'TYPE' TEXT," + // 4: type
                "'MAGAZINE_ID' TEXT NOT NULL ," + // 5: magazineId
                "'SERVER_TIMESTAMP' INTEGER," + // 6: serverTimestamp
                "'CLIENT_TIMESTAMP' INTEGER," + // 7: clientTimestamp
                "'MAGAZINE_REFERENCE' TEXT NOT NULL ," + // 8: magazineReference
                "'POST_CONTENT' TEXT);"); // 9: postContent

    /* Copy the data from one table to the new one */
        db.execSQL("INSERT INTO post2 (_id, POST_ID, USER_ID, VERSION, TYPE,  MAGAZINE_ID, SERVER_TIMESTAMP, CLIENT_TIMESTAMP, MAGAZINE_REFERENCE, POST_CONTENT)" +
                "   SELECT _id, POST_ID, USER_ID, VERSION, TYPE,  MAGAZINE_ID, SERVER_TIMESTAMP, CLIENT_TIMESTAMP, MAGAZINE_REFERENCE, POST_CONTENT FROM post;");

    /* Delete the previous table */
        db.execSQL("DROP TABLE post");
    /* Rename the just created table to the one that I have just deleted */
        db.execSQL("ALTER TABLE post2 RENAME TO post");

    /* Add Index/es if you want them */
        db.execSQL("CREATE INDEX " + "IDX_post_USER_ID ON post" +
                " (USER_ID);");
        //Example sql statement
        db.execSQL("ALTER TABLE user ADD COLUMN USERNAME TEXT");
    }
}