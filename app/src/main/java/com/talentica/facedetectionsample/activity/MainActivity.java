package com.talentica.facedetectionsample.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.talentica.facedetectionsample.R;
import com.talentica.facedetectionsample.view.FaceDetectorView;

public class MainActivity extends AppCompatActivity {
    private SparseArray<Face> mFaces;
    private FaceDetectorView mDetectorView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDetectorView = (FaceDetectorView)findViewById(R.id.view_facedetector);

        Bitmap bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.raw.family));

        FaceDetector detector = new FaceDetector.Builder(this)
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.FAST_MODE)
                .build();

        if (!detector.isOperational()) {

        } else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            mFaces = detector.detect(frame);
            mDetectorView.setData(bitmap,mFaces);
            detector.release();
        }

    }
}
