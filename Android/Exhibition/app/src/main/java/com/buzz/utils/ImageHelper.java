package com.buzz.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by NickChung on 3/17/15.
 */
public class ImageHelper {

    public static Bitmap getLocalBitmap(String url) {
        try {
            FileInputStream fs = new FileInputStream(url);
            Bitmap bitmap = BitmapFactory.decodeStream(fs);
            try {
                fs.close();
            } catch (IOException e) {

            }
            return bitmap;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.print(e.toString());
            return null;
        }
    }

    /**
     * 以最省内存的方式读取本地资源的图片
     *
     * @param context
     * @param resId
     * @return
     */
    public static Bitmap readBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        //获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }
}
