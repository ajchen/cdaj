package com.activ.cdaj.data;

import java.util.Date;


public class KV {

	public String key;    //unique key
	public String name;   //test name label
	public Class<?> dataType = String.class;
	public Object value;  //result value
	public String unit;   //result unit
	public Date time;
	
	public KV(){}
	
	public KV(String key, String name, Class<?> type, Object value, String unit, Date time ){
		this.key = key;
		this.name = name;
		this.dataType = type;
		this.value = value;
		this.unit = unit;
		this.time = time;
	}
	

	public String toString(){
		return key+" : "+name+" : "+dataType.getSimpleName();
	}

}
