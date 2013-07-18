package com.activ.cdaj.ccda.entry;

import java.util.LinkedHashMap;
import java.util.Map;

import com.activ.cdaj.ccda.Ccda;
import com.activ.cdaj.data.KV;
import com.activ.cdaj.util.Utils;

public class Entry {

	//key => value
	public Map<String,Obs> obsMap = new LinkedHashMap<String,Obs>();

	public void setData(Map<String, KV> data){
		for(String key : data.keySet()){
			KV kv = data.get(key);
			String v = String.valueOf(kv.value);
			Obs obs = new Obs(kv.key, kv.name, v, kv.unit, Ccda.EFFECTIVE_TIME.format(kv.time));
			obsMap.put(key, obs);
		}
	}
	
	public String space(int n){
		return Utils.space(n);
	}

}
