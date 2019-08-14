package com.alisasadkovska.czytanka.common;

import android.annotation.SuppressLint;
import android.os.Environment;

public class Common {
    public static final String BOOKS_REFERENCE = "books";
    public static final String RV_POS_INDEX = "position";
    public static final String FOLDER_NAME = "Czytanka";


    //Method to Check If SD Card is mounted or not
    public static boolean isSDCardPresent() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

}
