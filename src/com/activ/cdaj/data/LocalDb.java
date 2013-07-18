package com.activ.cdaj.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.activ.cdaj.user.User;
import com.activ.cdaj.util.Config;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LocalDb implements IConnector {
	private static final Logger LOG = Logger.getLogger(LocalDb.class);
	
	//user id => acc
	private Map<String,User> users = new HashMap<String,User>();
	
	private Map<String,Record> records = new HashMap<String,Record>();
	
	public LocalDb() throws Exception {		
		File user_file = new File(Config.get().appDir(), Config.get().getValue("db.user.file"));
		loadUsersFile(user_file);
		File data_file = new File(Config.get().appDir(), Config.get().getValue("db.data.file"));
		loadDataFile(data_file);
	}
	
	private void loadUsersFile(File f) throws Exception {
		ObjectMapper m = new ObjectMapper();
		JsonNode root = m.readTree(f);
	  for (JsonNode node : root.path("users")) {
	  	String user_id = node.get("user_id").textValue();
	  	String password = node.get("password").textValue();
	  	User user = new User(user_id, password);
	  	users.put(user_id, user);
	  }
	  LOG.info("loaded users: "+users.size());
	}
	
	private void loadDataFile(File f) throws Exception {
		ObjectMapper m = new ObjectMapper();
		JsonNode root = m.readTree(f);
	  for (JsonNode node : root.path("data")) {
	  	String id = node.get("id").textValue();
	  	String type = node.get("type").textValue();
	  	String name = node.get("name").textValue();
	  	String d = node.get("date").textValue();
	  	Date date = Record.dateLabel.parse(d);
	  	Record r = new Record();
	  	r.id = id;
	  	r.type = type;
	  	r.name = name;
	  	r.date = date;
	  	records.put(id, r);

	  	if(node.get("vitals") != null){
	  		parseData(node.path("vitals"), r);
	  	}else if(node.get("tests") != null){
	  		parseData(node.path("tests"), r);	  		
	  	}
	  }
	  LOG.info("loaded records: "+records.size());
	}

	private void parseData(JsonNode list, Record r){
		for(JsonNode n:list){
			KV kv = new KV();
	  	String code = n.get("code").textValue();
	  	String name = n.get("name").textValue();
	  	String type = n.get("dataType").textValue();
	  	String unit = n.get("unit").textValue();
	  	if(n.get("value").isInt()){
	  		kv.value = n.get("value").intValue();
	  	}else if(n.get("value").isDouble()){
	  		kv.value = n.get("value").doubleValue();
	  	}
	  	kv.key = code;
	  	kv.name = name;
	  	kv.unit = unit;
	  	if("integer".equalsIgnoreCase(type)){
	  		kv.dataType = Integer.class;
	  	}else if("double".equalsIgnoreCase(type)){
	  		kv.dataType = Double.class;
	  	}
	  	kv.time = r.date;
	  	r.data.put(code, kv);

		}
	}
	
	public boolean hasUser(String user_id){
		return users.containsKey(user_id);
	}
	
	public User authenticateUser(String user_id, String enc_pwd){
		User user = users.get(user_id);
		if(user != null && enc_pwd != null){
			if(enc_pwd.equals(user.pwdEncoded)){
				return user;
			}
		}
		return null;
	}

	public List<Record> getRecords(){
		return new ArrayList<Record>(records.values());
	}
	
	public Record getRecord(String id){
		return records.get(id);
	}
	
	public Map<String, KV> getVitalSigns(String patient_id){
		//todo: retrieve patient data from local data store

		//for testing only
		return getTestVitalSigns(patient_id);
	}

	//for testing only
	private Map<String, KV> getTestVitalSigns(String patient_id){
		Map<String, KV> map = new LinkedHashMap<String,KV>(); 
		Date time = Calendar.getInstance().getTime();
		map.put("8302-2", new KV("8302-2", "height", Double.class, 66.0, "in", time));
		map.put("3141-9", new KV("3141-9", "weight", Double.class, 222.0, "lb", time));
		map.put("8462-4", new KV("8462-4", "dbp", Integer.class, 95, "mmHg", time));
		map.put("8480-6", new KV("8480-6", "sbp", Integer.class, 150, "mmHg", time));
		
		map.put("2093-3", new KV("2093-3", "cholesterol", Integer.class, 300, "", time));
		map.put("2085-9", new KV("2085-9", "hdl", Integer.class, 30, "", time));
		map.put("2089-1", new KV("2089-1", "ldl", Integer.class, 200, "", time));
		map.put("2571-8", new KV("2571-8", "triglyceride", Integer.class, 200, "", time));
		map.put("4548-4", new KV("4548-4", "hba1c", Double.class, 7.7, "", time));
		return map;
	}

}
