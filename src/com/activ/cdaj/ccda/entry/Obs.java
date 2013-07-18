package com.activ.cdaj.ccda.entry;

import java.util.Date;


public class Obs {

	public String key;    //unique key
	public String name;   //test name label
	public String value;  //result value
	public String unit;   //result unit
	public String time;
	
	public Obs(String key, String name, String value, String unit, String time ){
		this.key = key;
		this.name = name;
		this.value = value;
		this.unit = unit;
		this.time = time;
	}
	

	public String toString(){
		return key+" : "+name+" : "+value+" "+unit;
	}

}
