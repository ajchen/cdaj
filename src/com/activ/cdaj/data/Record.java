package com.activ.cdaj.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class Record {

  public static final SimpleDateFormat dateLabel = new SimpleDateFormat("yyyy-MM-dd");

  //record types used by EHR:
	public static String TYPE_LAB = "lab";
	public static String TYPE_VISIT = "visit";
	//more to come	
	
	public String id;
	public String name;
	public String type;
	public String desc;
	public Date date;

	//data map
	public Map<String, KV> data = new LinkedHashMap<String,KV>(); 
	
	/**check if it's the given type */
	public boolean isType(String t){
		return t.equals(type);
	}
	
}
