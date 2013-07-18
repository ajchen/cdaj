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

public class OAuthUserDao  {
	private static final Logger LOG = Logger.getLogger(OAuthUserDao.class);

	public static final String C_USER = "oauthuser";

	//fields
  public static final String ACCESS_TOKEN = "access_token";
  public static final String EXPIRES_IN = "expires_in";
  public static final String REFRESH_TOKEN = "refresh_token";
  public static final String SCOPES = "scopes";
  public static final String USER_ID = "user_id";
  public static final String CLIENT_ID = "client_id";
  public static final String REDIRECT_URI = "redirect_uri";

  public static final String CREATE_TIME = "createtime"; //create time
  public static final String UPDATE_TIME = "updatetime"; //last update time


	protected DBCollection coll; 

	public OAuthUserDao(){ 
		coll = MongoMgr.get().getCollection(C_USER);
	}
	
	public boolean createUserOAuth(OAuthUser token){
		BasicDBObject doc = new BasicDBObject();
		//Note: _id is used for access token
		doc.put(EXPIRES_IN, token.expiresIn);
		doc.put(USER_ID, token.userId);
		doc.put(CLIENT_ID, token.clientId);
		doc.put(REDIRECT_URI, token.redirectUri);
		doc.put(SCOPES, token.scopes);		
		doc.put(CREATE_TIME, Calendar.getInstance().getTime());
    
    WriteResult wr = coll.insert(doc);
    if(wr.getError() == null){
    	String _id = ((ObjectId)doc.get("_id")).toString();
    	token.accessToken = _id;
    	LOG.debug("created user oauth: "+token);
    	return true;
    }
    LOG.warn("failed to create user oauth: "+token);
    return false;
	}

	public boolean updateUserOAuth(OAuthUser token){
		BasicDBObject olddoc = new BasicDBObject().append("_id", new ObjectId(token.accessToken));
		BasicDBObject newdoc = new BasicDBObject();
		newdoc.put(EXPIRES_IN, token.expiresIn);
		newdoc.put(USER_ID, token.userId);
		newdoc.put(CLIENT_ID, token.clientId);
		newdoc.put(REDIRECT_URI, token.redirectUri);
		newdoc.put(SCOPES, token.scopes);		
		newdoc.put(UPDATE_TIME, Calendar.getInstance().getTime());

    WriteResult wr = coll.update(olddoc, new BasicDBObject().append("$set", newdoc));
    if(wr.getError() == null){
    	LOG.info("updated access token");
    	return true;
    }
		return false;
	}

	public OAuthUser getUserOAuthById(String _id){
		BasicDBObject query = new BasicDBObject().append("_id", new ObjectId(_id));
		DBObject o = coll.findOne(query);
		if(o != null){
			//System.out.println(o);
			OAuthUser reg = new OAuthUser();
			toToken(o, reg); 
			return reg;
		}
		return null;
	}
	
	public boolean hasUser(String user_id){
		BasicDBObject query = new BasicDBObject().append(USER_ID, user_id);
		DBCursor cur = coll.find(query);
		if(cur.count() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	protected void toToken(DBObject o, OAuthUser token){
		token.accessToken = ((ObjectId)o.get("_id")).toString();  //_id is used for client id
		token.clientId = (String)o.get(CLIENT_ID);
		token.expiresIn = (Long)o.get(EXPIRES_IN);
		token.redirectUri = (String)o.get(REDIRECT_URI);
		token.userId = (String)o.get(USER_ID);
		if(o.get(SCOPES) != null){
			BasicDBList cons = (BasicDBList)o.get(SCOPES);
			for(Object c : cons.toArray()){
				token.scopes.add((String)c);
			}
		}
    
    if(o.get(CREATE_TIME) != null)
    	token._create_time = (Date)o.get(CREATE_TIME);
    if(o.get(UPDATE_TIME) != null)
    	token._update_time = (Date)o.get(UPDATE_TIME);

	}
	
	
}
