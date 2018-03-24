package bupt.tasays.settings;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by root on 18-3-24.
 */

public class Settings {
    private static String filename="Settings.txt";

    public static void writeSetting(Context context,String content) throws Exception{
        FileOutputStream outputStream=context.openFileOutput(filename,Context.MODE_PRIVATE);
        outputStream.write(content.getBytes());
        outputStream.close();
    }

    public static String readSetting(Context context)throws IOException{
        FileInputStream inputStream=context.openFileInput(filename);
        byte[] temp = new byte[1024];
        StringBuilder stringBuilder = new StringBuilder("");
        int len=0;
        while ((len = inputStream.read(temp)) > 0) {
            stringBuilder.append(new String(temp, 0, len));
        }
        inputStream.close();
        return stringBuilder.toString();
    }
}
