package com.leon.androidplus.utils;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

public class ImageQueryHandler extends AsyncQueryHandler{
    private static final String TAG = "ImageQueryHandler";

    public ImageQueryHandler(ContentResolver cr) {
        super(cr);
    }

    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
        ImageUtils.saveAvatar(path);
        cursor.close();
    }
}
