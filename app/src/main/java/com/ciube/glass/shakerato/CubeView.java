package com.ciube.glass.shakerato;

import android.view.View;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.content.Context;

public class CubeView extends View {

    // -----------------------------------------------------------------------
    // Geometry
    // -----------------------------------------------------------------------

    private final float[][] mVertices;

    private static final int[][] FACES = {
        { 4, 7, 6, 5 }, // top
        { 2, 6, 7, 3 }, // front
        { 1, 5, 6, 2 }, // right
        { 0, 4, 5, 1 }, // back
        { 3, 7, 4, 0 }, // left
        { 3, 0, 1, 2 }, // bottom
    };

    private final int[] mFaceColors = {
        Params.COLOR_FACE_TOP,
        Params.COLOR_FACE_FRONT,
        Params.COLOR_FACE_RIGHT,
        Params.COLOR_FACE_BACK,
        Params.COLOR_FACE_LEFT,
        Params.COLOR_FACE_BOTTOM,
    };

    // -----------------------------------------------------------------------
    // Animation
    // -----------------------------------------------------------------------

    private long mStartTime = -1;
    private final double mElevationRad = Math.toRadians(Params.ELEVATION_DEGREES);

    // Pre-compute the fixed angle once when SPINNING = false
    private final float mStaticCosT;
    private final float mStaticSinT;

    // -----------------------------------------------------------------------
    // Paint / path
    // -----------------------------------------------------------------------

    private final Paint mFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint mEdgePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path  mPath      = new Path();

    private final float[] mProjX = new float[8];
    private final float[] mProjY = new float[8];

    // -----------------------------------------------------------------------

    public CubeView(Context context) {
        super(context);

        float s = Params.CUBE_HALF_SIZE;
        mVertices = new float[][] {
            { -s, -s, -s }, // 0 bottom-back-left
            {  s, -s, -s }, // 1 bottom-back-right
            {  s, -s,  s }, // 2 bottom-front-right
            { -s, -s,  s }, // 3 bottom-front-left
            { -s,  s, -s }, // 4 top-back-left
            {  s,  s, -s }, // 5 top-back-right
            {  s,  s,  s }, // 6 top-front-right
            { -s,  s,  s }, // 7 top-front-left
        };

        // Pre-compute trig for the static angle so onDraw does zero extra work
        double staticRad = Math.toRadians(Params.STATIC_ANGLE_DEGREES);
        mStaticCosT = (float) Math.cos(staticRad);
        mStaticSinT = (float) Math.sin(staticRad);

        mEdgePaint.setStyle(Paint.Style.STROKE);
        mEdgePaint.setStrokeWidth(Params.EDGE_STROKE_WIDTH);
        mEdgePaint.setColor(Params.COLOR_EDGE);

        mFillPaint.setStyle(Paint.Style.FILL);

        setBackgroundColor(Params.COLOR_BACKGROUND);
        setWillNotDraw(false);
    }

    // -----------------------------------------------------------------------
    // Drawing
    // -----------------------------------------------------------------------

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        final float cosT;
        final float sinT;

        if (Params.SPINNING) {
            // Animate: derive angle from elapsed time
            long now = android.os.SystemClock.elapsedRealtime();
            if (mStartTime < 0) mStartTime = now;
            float theta = (now - mStartTime) * Params.ROTATION_SPEED_RAD_PER_MS;
            cosT = (float) Math.cos(theta);
            sinT = (float) Math.sin(theta);
        } else {
            // Static: use the pre-computed fixed angle — draw once, never again
            cosT = mStaticCosT;
            sinT = mStaticSinT;
        }

        float cosE = (float) Math.cos(mElevationRad);
        float sinE = (float) Math.sin(mElevationRad);

        float cx = getWidth()  / 2f;
        float cy = getHeight() / 2f;

        // --- project each vertex ---
        for (int i = 0; i < 8; i++) {
            float x = mVertices[i][0];
            float y = mVertices[i][1];
            float z = mVertices[i][2];

            float xw =  x * cosT + z * sinT;
            float zw = -x * sinT + z * cosT;

            mProjX[i] = cx + xw;
            mProjY[i] = cy - y * cosE + zw * sinE;
        }

        // --- painter's algorithm ---
        float[] faceDepth = new float[6];
        for (int f = 0; f < 6; f++) {
            float sum = 0f;
            for (int vi : FACES[f]) {
                sum += -mVertices[vi][0] * sinT + mVertices[vi][2] * cosT;
            }
            faceDepth[f] = sum / 4f;
        }

        int[] order = { 0, 1, 2, 3, 4, 5 };
        for (int i = 1; i < 6; i++) {
            int key  = order[i];
            float kd = faceDepth[key];
            int j = i - 1;
            while (j >= 0 && faceDepth[order[j]] < kd) {
                order[j + 1] = order[j];
                j--;
            }
            order[j + 1] = key;
        }

        // --- draw faces ---
        for (int fi = 0; fi < 6; fi++) {
            int f      = order[fi];
            int[] face = FACES[f];

            float ax = mProjX[face[1]] - mProjX[face[0]];
            float ay = mProjY[face[1]] - mProjY[face[0]];
            float bx = mProjX[face[2]] - mProjX[face[0]];
            float by = mProjY[face[2]] - mProjY[face[0]];
            if (ax * by - ay * bx > 0) continue;

            mPath.reset();
            mPath.moveTo(mProjX[face[0]], mProjY[face[0]]);
            for (int k = 1; k < 4; k++) {
                mPath.lineTo(mProjX[face[k]], mProjY[face[k]]);
            }
            mPath.close();

            mFillPaint.setColor(mFaceColors[f]);
            canvas.drawPath(mPath, mFillPaint);
            canvas.drawPath(mPath, mEdgePaint);
        }

        // Only schedule the next frame when actually animating
        if (Params.SPINNING) {
            postInvalidateOnAnimation();
        }
    }
}