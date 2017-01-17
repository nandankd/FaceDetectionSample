package com.talentica.facedetectionsample.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;

import com.google.android.gms.vision.face.Face;

public class FaceDetectorView extends View {

    private Bitmap mBitmap;
    private SparseArray<Face> mFaces;

    public FaceDetectorView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }

    public void setData(Bitmap bitmap, SparseArray<Face> faces) {
        mBitmap = bitmap;
        mFaces = faces;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBitmap != null && mFaces != null) {
            double deviceScale = drawBitmapToDeviceSize(canvas);
            drawFaceDetectionBox(canvas, deviceScale);
        }
    }

    private double drawBitmapToDeviceSize(Canvas canvas) {
        double viewWidth = canvas.getWidth();
        double viewHeight = canvas.getHeight();
        double imageWidth = mBitmap.getWidth();
        double imageHeight = mBitmap.getHeight();
        double scale = Math.min(viewWidth / imageWidth, viewHeight / imageHeight);

        Rect bitmapBounds = new Rect(0, 0, (int) (imageWidth * scale), (int) (imageHeight * scale));
        canvas.drawBitmap(mBitmap, null, bitmapBounds, null);
        return scale;
    }

    private void drawFaceDetectionBox(Canvas canvas, double deviceScale) {
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);

        for (int i = 0; i < mFaces.size(); ++i) {
            Face face = mFaces.valueAt(i);
            float x1 = (float) (face.getPosition().x * deviceScale);
            float y1 = (float) (face.getPosition().y * deviceScale);
            float x2 = (float) (x1 + face.getWidth() * deviceScale);
            float y2 = (float) (y1 + face.getHeight() * deviceScale);

            canvas.drawRect(x1, y1, x2, y2,
                    paint);
        }
    }
}
