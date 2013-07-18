package com.activ.cdaj.data;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.activ.cdaj.user.User;
import com.activ.cdaj.user.UserMgr;
import com.activ.cdaj.util.Config;

/**
 * Managing data access using one connector interface. 
 * There can be many different connectors for different use cases. 
 * But, user of DataManager does not need to know the actual implementation.
 * 
 * @author aj
 *
 */
public class DataMgr {
	private static final Logger LOG = Logger.getLogger(DataMgr.class);
	
	private IConnector connector;  //data connector
	
	private static DataMgr _instance;
	
	private DataMgr() throws Exception {
		try{
			String data_src = Config.get().getValue("data.src");
			LOG.debug("data source: "+data_src);
			if("local".equals(data_src)){
				connector = new LocalDb();
			}else if("remote".equals(data_src)){
				//db = new RemoteDb();
			}
			if(connector == null){
				throw new Exception("missing data source");
			}
		}catch(Exception e){
			LOG.error(e,e);
			throw new Exception("missing data source");
		}
	}
	
	public static void init() throws Exception {
		if(_instance == null){
			_instance = new DataMgr();
		}
	}
	
	public static DataMgr get(){
		return _instance;
	}
	
	/**has user in database? */
	public boolean hasUser(String user_id){
		return connector.hasUser(user_id);
	}

	/**authenticate user in database, expecting the same user credentials in patient portal. */
	public User authenticateUser(String user_id, String pwd){
		return connector.authenticateUser(user_id, UserMgr.encodePassword(pwd));
	}

	/**get patient's EHR records; all records for now. */
	public List<Record> getRecords(){
		return connector.getRecords();
	}

	/**get patient's EHR record by the given record id */
	public Record getRecord(String record_id){
		if(record_id == null){
			//get latest record
			return null;
		}else{
			return connector.getRecord(record_id);
		}
	}

	
	public Map<String, KV> getVitalSigns(String patient_id){
		return connector.getVitalSigns(patient_id);
	}
	

}
