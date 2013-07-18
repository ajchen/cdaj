package com.activ.cdaj;

import java.util.List;

import com.activ.cdaj.ccda.Ccda;
import com.activ.cdaj.ccda.CcdaBuilder;
import com.activ.cdaj.data.DataMgr;
import com.activ.cdaj.data.Record;
import com.activ.cdaj.db.MongoMgr;
import com.activ.cdaj.user.User;
import com.activ.cdaj.util.Utils;

/**
 * Service providing access to backend data. 
 * User should use this service to access EHR data and local data. 
 * There can be many different EHR data connectors. DataManager hides the  
 * the different implementation from user of this service.
 * 
 * Services include:
 *  - authenticate EHR user.
 *  - search patient records.
 *  - get CCD summary in C-CDA.
 *  - more to come
 * 
 * @author aj
 *
 */
public class EhrDataService {

	private static EhrDataService _instance;
	
	private EhrDataService() throws Exception {
		MongoMgr.init();
		DataMgr.init();
	}

	/**init the service. must call before using it. */ 
	public static void init() throws Exception {
		if(_instance == null){
			_instance = new EhrDataService();
		}
	}

	public EhrDataService get(){return _instance;}
	
	/**authenticate user in database, expecting the same user credentials in patient portal. */
	public static User authenticateUser(String user_id, String password){
		return DataMgr.get().authenticateUser(user_id, password);
	}
	
	/**
	 * Get patient data within the scope.
	 * @param patient_id
	 * @param scope  search, list, summary, etc
	 * @param record_id
	 * @param type  content type (xml, json, etc)
	 * @return data in the given format for API response.
	 */
	public static String getDataForApi(String patient_id, String scope, String record_id, String content_type){
		if(Utils.DataScope.LIST.equals(scope)){
			return listRecords(patient_id, content_type);
		}else if(Utils.DataScope.SUMMARY.equals(scope)){
			return getSummary(patient_id, record_id, content_type);
		}else if(Utils.DataScope.SEARCH.equals(scope)){
			return listRecords(patient_id, content_type);  
			//return search(patient_id, type);
		}
		return "";
	}

	/**
	 * list or search patient records. return results in the given format (in json for now). 
	 */
	public static String listRecords(String patient_id, String format){
		List<Record> records = DataMgr.get().getRecords();
		Generator gen = new Generator();
		return gen.generateRecords(records, format);		
	}

	/** 
	 * get CCD summary for the patient's record.
	 * @param patient_id	patient id
	 * @param record_id  record id
	 * @param format  C-CDA for now
	 * @return CCD summary in C-CDA 
	 */
	public static String getSummary(String patient_id, String record_id, String format){
		Ccda ccda = CcdaBuilder.createSummaryCcda(patient_id, record_id);
		Generator gen = new Generator();
		return gen.generateCcda(ccda, format);		
	}
	
/*
	public static void main(String[] args){
    String usage = "usage: ApiMgr ";
    try{
    	//boolean update = false;
      for(int i=0; i<args.length; i++){
      	//if(args[i].equals("-update")){
      		//update = true;
      	//}
      }
      
      String conf = System.getProperty("bbpi.conf");
      Config.init(new File(conf));
      ApiMgr mgr = new ApiMgr();
      String xml = mgr.getSummary("123", Utils.ContentType.XML);
      System.out.println(xml);
      Utils.saveStringToFile(xml, new File(Config.get().appDir(), "res/ccda.xml"));
      
      System.exit(0);
    }catch(Exception e){
    	LOG.error(e,e);
      System.err.println(usage);
      System.exit(1);
    }
  }
*/
}
