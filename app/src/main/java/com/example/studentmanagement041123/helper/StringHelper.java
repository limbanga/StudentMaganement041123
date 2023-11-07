package com.example.studentmanagement041123.helper;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import java.util.UUID;

public class StringHelper {

    public static String generateGuidFileName() {
        // Generate a random UUID
        UUID uuid = UUID.randomUUID();

        // Convert UUID to string and remove dashes
        String uuidString = uuid.toString().replace("-", "");

        // Return the UUID string as the file name
        return uuidString;
    }

    public static String generateGuidFileNameWithExtension(Uri uri, Context context) {
        return generateGuidFileName() + "." + getFileExtensionFromUri(uri, context);
    }

    public static String getFileExtensionFromUri(Uri uri, Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String extension;

        // Check if the URI has a scheme
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
            // Get the file extension from the content resolver
            extension = mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
        } else {
            // Get the file extension from the file path in the URI
            extension = MimeTypeMap.getFileExtensionFromUrl(uri.toString());
        }

        if (extension == null) {
            // Handle the case when no file extension is found
            return "";
        } else {
            // Convert the extension to lowercase and return
            return extension.toLowerCase();
        }
    }
}
