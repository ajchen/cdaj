package com.activ.cdaj.db;

import java.util.Set;

import org.apache.log4j.Logger;

import com.activ.cdaj.util.Config;
import com.mongodb.*;

public class MongoMgr {
	private static final Logger LOG = Logger.getLogger(MongoMgr.class);
	
	private static MongoMgr _instance;
	private static MongoClient mongo;  //db pool

	private String dbName;
	private String host = "localhost";
	private int port = 27017;
	private String user;
	private String pwd;
	
	private MongoMgr() throws Exception {
		host = Config.get().getValue("mongo.host");
		port = Config.get().getIntValue("mongo.port", port);
		dbName = Config.get().getValue("mongo.db.name");
		user = Config.get().getValue("mongo.user");
		pwd = Config.get().getValue("mongo.pwd");
		mongo = new MongoClient( host , port );
		
		DB db = mongo.getDB( dbName );
		Set<String> colls = db.getCollectionNames();
		LOG.info(dbName+" mongodb collections: "+colls);

		//check db and authentication if needed
		if(user != null && pwd != null){
			boolean auth = db.authenticate(user, pwd.toCharArray());
			if(!auth){
				throw new Exception(user+" not authenticated for mongodb "+dbName);
			}
		}
	}
	
	public static void init() throws Exception {
		if(_instance == null){
			_instance = new MongoMgr();
		}
	}
	
	public DB getDb(){
		return mongo.getDB( dbName );
	}

	public static MongoMgr get(){ return _instance;}
	
	public DBCollection getCollection(String c_name){
		return mongo.getDB(dbName).getCollection(c_name);
	}
}
