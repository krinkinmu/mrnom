package com.example.mrnom.framework.handlers;

import com.example.mrnom.framework.handlers.impl.BaseHandler;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MagneticFieldHandler extends BaseHandler implements SensorEventListener {
	private float[] magnetic;

	public MagneticFieldHandler(Context context) {
		SensorManager manager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
		if (manager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD).size() > 0) {
			Sensor accelerometer = manager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD).get(0);
			manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
		}
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// nothing to do
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		magnetic = event.values.clone();
		notifyListeners();
	}
	
	float[] getMagneticFields() {
		return magnetic;
	}

}
