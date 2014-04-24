package com.example.solarvehicledriverdisplay;

/*********************************
 * 
 * 
 * @author suzhaolun
 * 
 * Usage:
 * 1.create a DataObject object using the constructor with no parameters
 * 2.use the random() function to generate new DataObject that changes 
 *   consistently based on previous random values
 *
 */

public class DataObject{
	int speed;
	int batteryCharge;
	int arrayPower;
	int motorCurrent;
	int batteryCurrent;

	public DataObject(){
		speed = 30;
		batteryCharge = 50;
		arrayPower = 500;
		motorCurrent = 50;
		batteryCurrent = 50;
		
	}

	public DataObject(int SPEED,int BATTERYCHARGE, int ARRAYPOWER, int MOTORCURRENT, int BATTERYCURRENT){
		speed = SPEED;
		batteryCharge = BATTERYCHARGE;
		arrayPower = ARRAYPOWER;
		motorCurrent = MOTORCURRENT;
		batteryCurrent = BATTERYCURRENT;
		
	}

	public DataObject(DataObject old){
		speed = old.speed;
		batteryCharge = old.batteryCharge;
		arrayPower = old.arrayPower;
		motorCurrent = old.motorCurrent;
		batteryCurrent = old.batteryCurrent;
		
	}
	
	public DataObject random(){
		DataObject random = new DataObject(this);
		random.speed = (int) (random.speed + (Math.random()-0.5)*5);
		this.speed = random.speed;
		random.batteryCharge = (int) (random.batteryCurrent + (Math.random()-0.5)*5);
		this.batteryCharge = random.batteryCharge;
		random.arrayPower = (int) (random.arrayPower + (Math.random()-0.5)*10);
		this.arrayPower = random.arrayPower;
		random.motorCurrent = (int) (random.motorCurrent + (Math.random()-0.5)*5);
		this.motorCurrent = random.motorCurrent;
		random.batteryCurrent = (int) (batteryCurrent + (Math.random()-0.5)*5);
		this.batteryCurrent = random.batteryCurrent;

		if (random.speed > 60) {random.speed = 60; }
		if (random.batteryCharge > 100) {random.batteryCharge = 100; }
		if (random.arrayPower > 1000) {random.arrayPower = 1000; }
		if (random.motorCurrent > 100) {random.motorCurrent = 100; }
		if (random.batteryCurrent > 100) {random.batteryCurrent = 100; }

		if (random.speed < 0) {random.speed = 0; }
		if (random.batteryCharge < 0) {random.batteryCharge = 0; }
		if (random.arrayPower < 0) {random.arrayPower = 0; }
		if (random.motorCurrent < 0) {random.motorCurrent = 0; }
		if (random.batteryCurrent < 0) {random.batteryCurrent = 0; }

		return random;

	}


	

}