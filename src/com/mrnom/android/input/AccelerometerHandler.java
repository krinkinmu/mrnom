package com.mrnom.android.input;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AccelerometerHandler extends BaseHandler implements SensorEventListener {
	private float[] gravity;
	
	public AccelerometerHandler(Context context) {
		SensorManager manager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
		if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() > 0) {
			Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
			manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// nothing to do
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		gravity = event.values.clone();
		notifyListeners();
	}
	
	public float getAccelX() {
		return gravity[0];
	}
	
	public float getAccelY() {
		return gravity[1];
	}
	
	public float getAccelZ() {
		return gravity[2];
	}
	
	float[] getGravity() {
		return gravity;
	}

}
