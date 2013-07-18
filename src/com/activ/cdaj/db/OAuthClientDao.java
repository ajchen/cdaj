package com.activ.cdaj.db;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import com.activ.cdaj.db.MongoMgr;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public class OAuthClientDao  {
	private static final Logger LOG = Logger.getLogger(OAuthClientDao.class);

	public static final String C_CLIENT = "oauthclient";

	//fields
  public static final String CLIENT_NAME = "client_name";
  public static final String CLIENT_URI = "client_uri";
  public static final String LOGO_URI = "logo_uri";
  public static final String REDIRECT_URIS = "redirect_uris";
  public static final String RESPONSE_TYPES = "response_types";
  public static final String GRANT_TYPES = "grant_types";
  public static final String TOKEN_METHOD = "token_endpoint_auth_method";
  public static final String SCOPES = "scopes";

  //public static final String CLIENT_ID = "client_id";
  public static final String CLIENT_SECRET = "client_secret";
  public static final String ISSUED_AT = "client_id_issued_at";
  public static final String EXPIRES_AT = "client_secret_expires_at";

  public static final String CREATE_TIME = "createtime"; //create time
  public static final String UPDATE_TIME = "updatetime"; //last update time


	protected DBCollection coll; 

	public OAuthClientDao(){ 
		coll = MongoMgr.get().getCollection(C_CLIENT);
	}
	
	public boolean createClient(OAuthClient reg){
		BasicDBObject doc = new BasicDBObject();
		doc.put(CLIENT_NAME, reg.client_name);
		doc.put(CLIENT_URI, reg.client_uri);
		doc.put(REDIRECT_URIS, reg.redirect_uris);
		doc.put(RESPONSE_TYPES, reg.response_types);
		doc.put(GRANT_TYPES, reg.grant_types);
		doc.put(TOKEN_METHOD, reg.token_endpoint_auth_method);
		doc.put(SCOPES, reg.scopes);
		
		//Note: _id is used for client id
		doc.put(CLIENT_SECRET, reg.client_secret);
		doc.put(ISSUED_AT, reg.issued_at);
		doc.put(EXPIRES_AT, reg.expires_at);
		doc.put(CREATE_TIME, Calendar.getInstance().getTime());
    
    WriteResult wr = coll.insert(doc);
    if(wr.getError() == null){
    	String _id = ((ObjectId)doc.get("_id")).toString();
    	reg.client_id = _id;
    	LOG.debug("created client reg: "+reg);
    	return true;
    }
    LOG.warn("failed to create client reg: "+reg);
    return false;
	}

	public boolean updateClient(OAuthClient reg){
		BasicDBObject olddoc = new BasicDBObject().append("_id", new ObjectId(reg.client_id));
		BasicDBObject newdoc = new BasicDBObject();
		newdoc.put(REDIRECT_URIS, reg.redirect_uris);
		newdoc.put(RESPONSE_TYPES, reg.response_types);
		newdoc.put(GRANT_TYPES, reg.grant_types);
		newdoc.put(TOKEN_METHOD, reg.token_endpoint_auth_method);
		newdoc.put(SCOPES, reg.scopes);
		
		//Note: _id is used for client id
		newdoc.put(CLIENT_SECRET, reg.client_secret);
		newdoc.put(ISSUED_AT, reg.issued_at);
		newdoc.put(EXPIRES_AT, reg.expires_at);
		newdoc.put(UPDATE_TIME, Calendar.getInstance().getTime());

    WriteResult wr = coll.update(olddoc, new BasicDBObject().append("$set", newdoc));
    if(wr.getError() == null){
    	LOG.info("updated client registration");
    	return true;
    }
		return false;
	}

	public OAuthClient getClientRegById(String _id){
		BasicDBObject query = new BasicDBObject().append("_id", new ObjectId(_id));
		DBObject o = coll.findOne(query);
		if(o != null){
			//System.out.println(o);
			OAuthClient reg = new OAuthClient();
			toReg(o, reg); 
			return reg;
		}
		return null;
	}
	
	public OAuthClient getClientRegByName(String client_name, String client_uri){
		BasicDBObject query = new BasicDBObject().append(CLIENT_NAME, client_name)
			.append(CLIENT_URI, client_uri);
		DBCursor cur = coll.find(query);
		while(cur.hasNext()) {
			DBObject o = cur.next();
			//System.out.println(o);
			OAuthClient reg = new OAuthClient();
			toReg(o, reg);  //only the first one
			return reg;
		}
		return null;
	}

	public boolean hasClientReg(String client_name, String client_uri){
		BasicDBObject query = new BasicDBObject().append(CLIENT_NAME, client_name)
			.append(CLIENT_URI, client_uri);
		DBCursor cur = coll.find(query);
		if(cur.count() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	protected void toReg(DBObject o, OAuthClient reg){
		reg.client_id = ((ObjectId)o.get("_id")).toString();  //_id is used for client id
		//reg.client_id = (String)o.get(CLIENT_ID);
		reg.client_secret = (String)o.get(CLIENT_SECRET);
		reg.issued_at = (Long)o.get(ISSUED_AT);
		reg.expires_at = (Long)o.get(EXPIRES_AT);
		//reg.reg_token = (String)o.get();

		reg.client_name = (String)o.get(CLIENT_NAME);
		reg.client_uri = (String)o.get(CLIENT_URI);    
		if(o.get(TOKEN_METHOD) != null)
    	reg.token_endpoint_auth_method = (String)o.get(TOKEN_METHOD);
    if(o.get(REDIRECT_URIS) != null){
			BasicDBList cons = (BasicDBList)o.get(REDIRECT_URIS);
			for(Object c : cons.toArray()){
				reg.redirect_uris.add((String)c);
			}
    }
    if(o.get(RESPONSE_TYPES) != null){
			BasicDBList cons = (BasicDBList)o.get(RESPONSE_TYPES);
			for(Object c : cons.toArray()){
				reg.response_types.add((String)c);
			}
    }
    if(o.get(GRANT_TYPES) != null){
			BasicDBList cons = (BasicDBList)o.get(GRANT_TYPES);
			for(Object c : cons.toArray()){
				reg.grant_types.add((String)c);
			}
    }
		if(o.get(SCOPES) != null){
			BasicDBList cons = (BasicDBList)o.get(SCOPES);
			for(Object c : cons.toArray()){
				reg.scopes.add((String)c);
			}
		}
    
    if(o.get(CREATE_TIME) != null)
    	reg._create_time = (Date)o.get(CREATE_TIME);
    if(o.get(UPDATE_TIME) != null)
    	reg._update_time = (Date)o.get(UPDATE_TIME);

	}
	
	
}
