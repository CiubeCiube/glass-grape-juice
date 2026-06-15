package com.ciube.glass.shakerato;

import android.graphics.Color;

public final class Params {

    private Params() {}

    // -------------------------------------------------------------------------
    // Geometry
    // -------------------------------------------------------------------------

    /** Half-side length of the cube in world units.
     * Glass display is 640×360; 120 fills it nicely; max sensible ~160. */
    public static final float CUBE_HALF_SIZE = 108f;

    // -------------------------------------------------------------------------
    // Animation
    // -------------------------------------------------------------------------

    /** When true  → cube spins continuously.
     *  When false → cube is rendered once at a fixed angle and stays still. */
    public static boolean SPINNING = false;

    /** Rotation speed in radians per millisecond.
     * π/4000  ≈ one full turn every 8 s  (comfortable on Glass). */
    public static final float ROTATION_SPEED_RAD_PER_MS = (float) (Math.PI / 4000.0);

    /** Camera elevation in degrees (how far above the horizon we look down).
     * 35.264° gives a perfect true isometric projection. */
    public static final double ELEVATION_DEGREES = 35.264;

    public static final double STATIC_ANGLE_DEGREES = 45.0;

    // -------------------------------------------------------------------------
    // Colours
    // -------------------------------------------------------------------------

    public static final int COLOR_FACE_TOP    = Color.parseColor("#00FF00");
    public static final int COLOR_FACE_FRONT  = Color.parseColor("#00FF00");
    public static final int COLOR_FACE_RIGHT  = Color.parseColor("#00FF00");
    public static final int COLOR_FACE_BACK   = Color.parseColor("#00FF00");
    public static final int COLOR_FACE_LEFT   = Color.parseColor("#00FF00");
    public static final int COLOR_FACE_BOTTOM = Color.parseColor("#00FF00");

    public static final int COLOR_EDGE        = Color.argb(180, 0, 0, 0);
    public static final int COLOR_BACKGROUND  = Color.BLACK;

    public static final float EDGE_STROKE_WIDTH = 3f;
}