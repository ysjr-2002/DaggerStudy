package com.visitor.obria.yourapplication.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.util.Log;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.ByteArrayOutputStream;

/**
 * created by yangshaojie  on 2018/9/8
 * email: ysjr-2002@163.com
 */
public class YuvUtils {

    public static Bitmap NV21ToBitmap(CameraPreviewData data) {

        YuvImage yuvImage = new YuvImage(data.nv21Data, ImageFormat.NV21, data.width, data.height, null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(data.nv21Data.length);
        yuvImage.compressToJpeg(new Rect(0, 0, data.width, data.height), 100, byteArrayOutputStream);
        byte[] temp = byteArrayOutputStream.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
        return bitmap;
    }

    public static String recognzieQR(Bitmap obmp) {
        int width = obmp.getWidth();
        int height = obmp.getHeight();
        int[] data = new int[width * height];
        obmp.getPixels(data, 0, width, 0, 0, width, height);
        RGBLuminanceSource source = new RGBLuminanceSource(width, height, data);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        Result re = null;
        String content = "";
        try {
            re = reader.decode(bitmap1);
            if (re != null) {
                content = re.getText();
            }
        } catch (NotFoundException e) {
            Log.d("ysj", "没有发现二维码");
            e.printStackTrace();
        } catch (ChecksumException e) {
            Log.d("ysj", "校验错误");
            e.printStackTrace();
        } catch (FormatException e) {
            Log.d("ysj", "格式错误");
            e.printStackTrace();
        }
        finally {
        }
        return content;
    }
}
