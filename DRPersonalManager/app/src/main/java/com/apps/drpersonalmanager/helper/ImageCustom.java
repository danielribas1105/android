package com.apps.drpersonalmanager.helper;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.exifinterface.media.ExifInterface;

import java.io.IOException;

public class ImageCustom {

    /*
    public String getImagePath(Uri contentUri) {
        String[] campos = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri,campos,null,null,null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }

     */

    public static Bitmap imgRotate(Bitmap imagem, String path){

        ExifInterface exif = null;
        try {
            //data carrega imagem, mas está retornando zero sempre! Mas não dá erro.
            exif = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String orientacao = exif.getAttribute(ExifInterface.TAG_ORIENTATION);

        int codigoOrientacao = Integer.parseInt(orientacao);
        int i=0;
        switch (codigoOrientacao) {
            case ExifInterface.ORIENTATION_NORMAL:
                i = 0;
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                i = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                i = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                i = 270;
                break;
        }

        Matrix matrix = new Matrix();
        matrix.setRotate(i);
        Bitmap bitmap = Bitmap
                .createBitmap(imagem,0,0,imagem.getWidth(),imagem.getHeight(),matrix,true);
        return bitmap;
    }
}
