package com.leon.androidplus.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.leon.androidplus.data.model.User;

import java.io.FileNotFoundException;

public class ImageUtils {

    private static final String TAG = "ImageUtils";

    @SuppressLint("NewApi")
    public static void saveAvatar(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            final String docId = DocumentsContract.getDocumentId(uri);
            final String[] split = docId.split(":");
            Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            final String selection = "_id=?";
            final String[] selectionArgs = new String[]{split[1]};
            startQuery(context, contentUri, selection, selectionArgs);
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            startQuery(context, uri, null, null);
        }
    }

    public static void startQuery(Context context, Uri uri, String selection, String[] selectionArgs) {
        ImageQueryHandler imageQueryHandler = new ImageQueryHandler(context.getContentResolver());
        String[] projections = {MediaStore.MediaColumns.DATA};
        imageQueryHandler.startQuery(0, null, uri, projections, selection, selectionArgs, null);
    }

    public static void saveAvatar(String path) {
        String fileName = System.currentTimeMillis() + ".png";
        saveAvatar(fileName, path);
    }

    public static void saveAvatar(String fileName, String filePath) {
        try {
            final AVFile file = AVFile.withAbsoluteLocalPath(fileName, filePath);
            file.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        User user = AVUser.getCurrentUser(User.class);
                        user.setAvatar(file.getUrl());
                        user.saveInBackground();
                    } else {
                        Log.d(TAG, "done: " + e.getLocalizedMessage());
                    }
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
