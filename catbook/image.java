package sarayutwiangchai.catbook;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Sarayut Wiangchai on 1/7/2558.
 */
public class image {
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }


}
