package net.glm.googlemapsinstatest2.Utility;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.Log;

import net.glm.googlemapsinstatest2.R;

/**
 * Created by Michael on 18/02/2018.
 */

public class Utility {
    private static final String LOG_TAG = "MapsTest";

    public static Bitmap getCircledBitmap (Bitmap bitmap){
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static Bitmap getResizebleCircleBitmap (Bitmap inputBitmap, int circleRadiusInPx) {

        float minimumSideSizeOfBitmap = (float) Math.min(inputBitmap.getHeight(), inputBitmap.getWidth());
        float multiplyCoefficient = ((circleRadiusInPx * 2) / minimumSideSizeOfBitmap);

        Bitmap resizedInputBitmap = Bitmap.createScaledBitmap(inputBitmap, (int) (inputBitmap.getWidth() * multiplyCoefficient),
                (int) (inputBitmap.getHeight() * multiplyCoefficient), true);

        Bitmap outputBitmap = Bitmap.createBitmap(circleRadiusInPx*2,
                circleRadiusInPx*2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(outputBitmap);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, circleRadiusInPx*2, circleRadiusInPx*2);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        canvas.drawCircle(circleRadiusInPx,circleRadiusInPx,circleRadiusInPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(resizedInputBitmap, rect, rect, paint);

        return outputBitmap;
    }



    public static Bitmap getCircle(int radius) {

        Bitmap output = Bitmap.createBitmap(radius*2,
                radius*2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        int color = 0x40EF5350;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        canvas.drawCircle(radius,radius,radius, paint);
        return output;

    }

    public static Bitmap getResizebleCircleBitmap(Bitmap inputBitmap){
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(inputBitmap, (int) (inputBitmap.getWidth() * 0.3), (int) (inputBitmap.getHeight() * 0.3), true);
        Bitmap circleBitmap = getCircledBitmap(resizedBitmap);
        return circleBitmap;

    }


}
