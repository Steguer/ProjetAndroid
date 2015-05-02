package com.android.projet.projetandroid.markerAugReality.markers;

import android.graphics.Point;

import com.android.projet.projetandroid.markerAugReality.MarkerActivity;
import com.android.projet.projetandroid.markerAugReality.Projector;
import com.android.projet.projetandroid.markerAugReality.graphics.SimpleBox;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import edu.dhbw.andar.ARObject;
import edu.dhbw.andar.util.GraphicsUtil;

/**
 * Created by Adrien on 01/05/2015.
 */
public abstract class AbstractMarker extends ARObject {
    private MarkerActivity markerActivity;
    private boolean hasDetected;

    public MarkerType getType() {
        return type;
    }

    private MarkerType type;

    public AbstractMarker(String name, String patternName,
                         double markerWidth, double[] markerCenter, MarkerActivity markerActivity, MarkerType type) {
        super(name, patternName, markerWidth, markerCenter);
        float   mat_ambientf[]     = {0f, 1.0f, 0f, 1.0f};
        float   mat_flashf[]       = {0f, 1.0f, 0f, 1.0f};
        float   mat_diffusef[]       = {0f, 1.0f, 0f, 1.0f};
        float   mat_flash_shinyf[] = {50.0f};

        mat_ambient = GraphicsUtil.makeFloatBuffer(mat_ambientf);
        mat_flash = GraphicsUtil.makeFloatBuffer(mat_flashf);
        mat_flash_shiny = GraphicsUtil.makeFloatBuffer(mat_flash_shinyf);
        mat_diffuse = GraphicsUtil.makeFloatBuffer(mat_diffusef);

        this.markerActivity = markerActivity;
        this.hasDetected = false;
        this.type = type;
        System.out.println("c1 " + type);
    }

    public AbstractMarker(String name, String patternName,
                         double markerWidth, double[] markerCenter, float[] customColor, MarkerActivity markerActivity, MarkerType type) {
        super(name, patternName, markerWidth, markerCenter);
        float   mat_flash_shinyf[] = {50.0f};

        mat_ambient = GraphicsUtil.makeFloatBuffer(customColor);
        mat_flash = GraphicsUtil.makeFloatBuffer(customColor);
        mat_flash_shiny = GraphicsUtil.makeFloatBuffer(mat_flash_shinyf);
        mat_diffuse = GraphicsUtil.makeFloatBuffer(customColor);

        this.markerActivity = markerActivity;
        this.hasDetected = false;
        this.type = type;
        System.out.println("c2 " + type);
    }

    private SimpleBox box = new SimpleBox();
    private FloatBuffer mat_flash;
    private FloatBuffer mat_ambient;
    private FloatBuffer mat_flash_shiny;
    private FloatBuffer mat_diffuse;

    @Override
    public final void draw(GL10 gl) {
        super.draw(gl);

        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR,mat_flash);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, mat_flash_shiny);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat_diffuse);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat_ambient);

        gl.glColor4f(1.0f, 0.0f, 0, 1.0f);
        gl.glTranslatef(0.0f, 0.0f, 12.5f);

        Projector projector = new Projector();
        projector.setViewport(gl);
        Point xy = projector.getScreenCoords(getTransMatrix(), gl);
        detected(xy);

        box.draw(gl, 60, 60, 10);
        System.out.println(type);
    }

    @Override
    public void init(GL10 gl) {
        System.out.println("init");
    }

    protected void detected(Point xy){
        if(!this.hasDetected) {
            markerActivity.detected(this, xy);
            this.hasDetected = true;
        }
    }


}
