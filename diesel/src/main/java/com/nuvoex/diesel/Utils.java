package com.nuvoex.diesel;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by Shine on 19/07/16.
 */
public class Utils {

    public static String getJsonFile(Context context, String name, String packageName) {
        InputStream is = context.getResources().openRawResource(context.getResources().getIdentifier("diesel", "raw", packageName));/*R.raw.diesel*/

        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = null;
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return writer.toString();
    }
}
