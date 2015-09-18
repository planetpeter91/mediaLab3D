package com.example.samuel.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;

import org.rajawali3d.Object3D;
import org.rajawali3d.animation.Animation;
import org.rajawali3d.animation.Animation3D;
import org.rajawali3d.animation.EllipticalOrbitAnimation3D;
import org.rajawali3d.animation.RotateOnAxisAnimation;
import org.rajawali3d.animation.TranslateAnimation3D;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.lights.PointLight;
import org.rajawali3d.loader.LoaderAWD;
import org.rajawali3d.loader.LoaderOBJ;
import org.rajawali3d.loader.ParsingException;
import org.rajawali3d.loader.fbx.LoaderFBX;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.materials.methods.SpecularMethod;
import org.rajawali3d.materials.plugins.FogMaterialPlugin;
import org.rajawali3d.materials.textures.CubeMapTexture;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.math.vector.Vector3;
import org.rajawali3d.primitives.Sphere;
import org.rajawali3d.renderer.RajawaliRenderer;

import java.text.ParseException;

/**
 * Created by samuel on 8/18/2015.
 */

public class Renderer extends RajawaliRenderer implements SensorEventListener{
    private DirectionalLight mLight;
    private Object3D mObjectGroup;
    private Animation3D mCameraAnim, mLightAnim;
    private Sphere mySphere;
    public static int sensitivity = 25 ;
    float rotationVec[]={0,0,0};
    Material faceMaterial;
    private LoaderOBJ objParser = null;
    private LoaderOBJ objParser2 = null;

    public Renderer(Context context ) {
        super(context);
        this.mContext = context;
        setFrameRate(60);

    }

    @Override
    protected void initScene() {

        mLight = new DirectionalLight(1.0f, 0.2f, -1.0f);
        mLight.setPosition(1, 2, 0);
        mLight.setColor(1.0f, 1.0f, 1.0f);
        mLight.setPower(2);

        faceMaterial = new Material();
        faceMaterial.enableLighting(true);
        faceMaterial.setColor(Color.WHITE);
        faceMaterial.setDiffuseMethod(new DiffuseMethod.Lambert());


        //mySphere = new Sphere(1,24,24);
        //mySphere.setMaterial(faceMaterial);

        //getCurrentScene().addLight(mLight);
        getCurrentCamera().enableLookAt();
        getCurrentCamera().setFarPlane(10000);
        getCurrentCamera().setNearPlane(0.2);
        // getCurrentCamera().setFieldOfView(60);
        getCurrentCamera().setZ(5.0);
        getCurrentCamera().setX(0.0);
        getCurrentCamera().setY(0.0);
        getCurrentCamera().setUpAxis(-1.0f, 0.0f, 0.0f);
        //face 1 up is y , z was ,uch less


        getCurrentCamera().setLookAt(0.0, 0.0, 0.0);

//Change R.raw.XXX to R.raw.name_of_new_obj
        objParser = new LoaderOBJ(mContext.getResources(),
                mTextureManager, R.raw.facesmall_obj);

        try {
            objParser.parse();
            mObjectGroup = objParser.getParsedObject();

            getCurrentScene().addChild(mObjectGroup);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onOffsetsChanged(float v, float v1, float v2, float v3, int i, int i1) {

    }

    @Override
    public void onTouchEvent(MotionEvent motionEvent) {


    }

    public void onRender(final long elapsedTime, final double deltaTime) {

        super.onRender(elapsedTime, deltaTime);
       
        mObjectGroup.setRotation(0,-rotationVec[0], rotationVec[0]-rotationVec[1]); //2nd face

    }


    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void setRotation(float[] rotationAngles){


        rotationVec[0] = sensitivity*rotationAngles[0];
        rotationVec[1] = sensitivity*rotationAngles[1];
        rotationVec[2] = sensitivity*rotationAngles[2];
    }


}
