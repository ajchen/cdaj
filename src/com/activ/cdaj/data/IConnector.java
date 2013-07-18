package com.activ.cdaj.data;

import java.util.List;
import java.util.Map;

import com.activ.cdaj.user.User;

public interface IConnector {

	public boolean hasUser(String user_id);
	public User authenticateUser(String user_id, String enc_pwd);

	public List<Record> getRecords();
	public Record getRecord(String id);


	public Map<String, KV> getVitalSigns(String patient_id);

}
