package com.mrnom.android.input;


import android.content.Context;
import android.hardware.SensorManager;

public class CompasHandler extends BaseHandler implements HandlerListener {
	private final AccelerometerHandler accelerometer;
	private final MagneticFieldHandler magnetic;
	private final float[] I = new float[9];
	private final float[] R = new float[9];
	private final float[] orientation = new float[3];
	private float[] gravity;
	private float[] geomagnetic;
	
	public CompasHandler(Context context) {
		accelerometer = new AccelerometerHandler(context);
		accelerometer.registerListener(this);
		magnetic = new MagneticFieldHandler(context);
		magnetic.registerListener(this);
	}
	
	public float getAthimuth() {
		return orientation[0];
	}
	
	public float getPitch() {
		return orientation[1];
	}
	
	public float getRoll() {
		return orientation[2];
	}

	@Override
	public void dataChanged(BaseHandler handler) {
		if (handler == accelerometer) {
			gravity = accelerometer.getGravity();
		} else if (handler == magnetic) {
			geomagnetic = magnetic.getMagneticFields();
		}
		if (gravity != null && geomagnetic != null && SensorManager.getRotationMatrix(R, I, gravity, geomagnetic)) {
			SensorManager.getOrientation(R, orientation);
			notifyListeners();
		}
	}

}
