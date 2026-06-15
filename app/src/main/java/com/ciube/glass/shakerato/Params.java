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

    /** Rotation speed in radians per millisecond.
     * π/4000  ≈ one full turn every 8 s  (comfortable on Glass). */
    public static final float ROTATION_SPEED_RAD_PER_MS = (float) (Math.PI / 4000.0);

    /** Camera elevation in degrees (how far above the horizon we look down).
     * Impostato a 35.264° per ottenere una proiezione isometrica perfetta e reale. */
    public static final double ELEVATION_DEGREES = 35.264;

    // -------------------------------------------------------------------------
    // Colours
    //    Sfalsati per creare un effetto d'ombra direzionale (shading) 
    //    fondamentale per percepire la rotazione 3D delle facce.
    // -------------------------------------------------------------------------

    /** Top face — più chiara per simulare la luce dall'alto. */
    public static final int COLOR_FACE_TOP    = Color.parseColor("#00FF00");

    /** Front face — colore base. */
    public static final int COLOR_FACE_FRONT  = Color.parseColor("#00FF00");

    /** Right face — in ombra accentuata. */
    public static final int COLOR_FACE_RIGHT  = Color.parseColor("#00FF00");

    /** Back face (nascondibile dal culling). */
    public static final int COLOR_FACE_BACK   = Color.parseColor("#00FF00");

    /** Left face — in ombra leggera. */
    public static final int COLOR_FACE_LEFT   = Color.parseColor("#00FF00");

    /** Bottom face (nascondibile dal culling). */
    public static final int COLOR_FACE_BOTTOM = Color.parseColor("#00FF00");

    /** Edge stroke colour drawn on top of each face. */
    public static final int COLOR_EDGE        = Color.argb(180, 0, 0, 0);

    /** Background fill (keep black for Glass). */
    public static final int COLOR_BACKGROUND  = Color.BLACK;

    /** Edge line width in pixels. 3 is crisp on the Glass 640×360 display. */
    public static final float EDGE_STROKE_WIDTH = 3f;
}