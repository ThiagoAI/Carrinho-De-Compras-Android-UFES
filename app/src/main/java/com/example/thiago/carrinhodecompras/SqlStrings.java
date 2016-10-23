package com.example.thiago.carrinhodecompras;

import com.example.thiago.carrinhodecompras.ProductContract;

/**
 * Created by thiago on 22/10/16.
 */
public class SqlStrings {

    public static final String TEXT_TYPE = " TEXT";

    public static final String BOOLEAN_TYPE = " BOOLEAN";

    public static final String COMMA_SEP = ",";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ProductContract.Product.TABLE_NAME + " (" +
                    ProductContract.Product._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    ProductContract.Product.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    ProductContract.Product.COLUMN_PRICE + TEXT_TYPE +
                    " )";

    public static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + ProductContract.Product.TABLE_NAME;
}
