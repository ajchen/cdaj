package com.activ.cdaj.user;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import com.activ.cdaj.db.MongoMgr;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

/**
 * model: user
 *   _id, acc_type, role, user_id, encoded_pwd, created_time
 */
public class UserDao {

	public static final Logger LOG = Logger.getLogger(UserDao.class);	
	public static final String C_USER = "user";

	public static final String USER_ID = "userid";
	public static final String ENC_PWD = "encpwd";
	public static final String ACC_TYPE = "acctype";
	public static final String CREATE_TIME = "createtime";
	public static final String UPDATE_TIME = "updatetime";

	protected DBCollection coll; 

	public UserDao(){
		coll = MongoMgr.get().getDb().getCollection(C_USER);
	}
	
	public User createUser(User acc){
		if(acc != null && acc.isValid()){
			BasicDBObject doc = new BasicDBObject();
			doc.put(USER_ID, acc.userId);
			doc.put(ENC_PWD, acc.pwdEncoded);
			doc.put(CREATE_TIME, Calendar.getInstance().getTime());
        
			WriteResult wr = coll.insert(doc);
			if(wr.getError() == null){
      	String _id = ((ObjectId)doc.get("_id")).toString();
      	acc.accId = _id;
  			LOG.info("created acc: "+acc);
  			return acc;
			}
		}
		return null;
	}
/*	
	public boolean updateAccount(Acc acc){
		BasicDBObject olddoc = new BasicDBObject().append("_id", new ObjectId(acc.accId));
		BasicDBObject newdoc = new BasicDBObject();
		if(acc.getAccountType() != null){
			newdoc.put(ACC_TYPE, acc.getAccountType());
		}
		//doc.put(USER_ROLE, acc.getRole());
		if(acc.getEncodedPassword() != null){
			newdoc.put(ENC_PWD, acc.getEncodedPassword());
		}
		newdoc.put(UPDATE_TIME, Calendar.getInstance().getTime());
    newdoc.put(FIRSTNAME, acc.firstname);
    newdoc.put(LASTNAME, acc.lastname);
		newdoc.put(EMAIL, acc.email);

    WriteResult wr = coll.update(olddoc, new BasicDBObject().append("$set", newdoc));
    if(wr.getError() == null){
    	LOG.info(acc.userId+" updated account");
    	return true;
    }
		return false;
	}
*/	
	
	public User getUserById(String _id){
		BasicDBObject query = new BasicDBObject().append("_id", new ObjectId(_id));
		DBObject o = coll.findOne(query);
		if(o != null){
			//System.out.println(o);
			User acc = new User();
			toAcc(o, acc); 
			return acc;
		}
		return null;
	}
	
	public User getUserByUserId(String user_id){
		BasicDBObject query = new BasicDBObject().append(USER_ID, user_id);
		DBCursor cur = coll.find(query);
		while(cur.hasNext()) {
			DBObject o = cur.next();
			//System.out.println(o);
			User acc = new User();
			toAcc(o, acc);  //only the first one
			return acc;
		}
		return null;
	}

	public boolean hasUserId(String user_id){
		BasicDBObject query = new BasicDBObject();
		query.put(USER_ID, user_id);
		DBCursor cur = coll.find(query);
		if(cur.count() > 0){
			return true;
		}else{
			return false;
		}
	}
/*	
	public List<Acc> getUserAccounts(int start, int max){
		List<Acc> l = new LinkedList<Acc>();
		DBCursor cur = coll.find(new BasicDBObject().append(ACC_TYPE, Acc.TYPE_USER)).skip(start).limit(max)
			.sort(new BasicDBObject(USER_ID, 1));
		while(cur.hasNext()) {
			DBObject o = cur.next();
			Acc acc = new Acc();
			toAcc(o, acc);
			l.add(acc);
		}
		return l;
	}
*/	
	
	public boolean removeAccount(String acc_id){
		WriteResult r = coll.remove(new BasicDBObject().append("_id", new ObjectId(acc_id)));
		if(r.getError() == null){
			LOG.info("removed account: "+acc_id);
			return true;
		}else{
			LOG.error(r.getError());
			return false;
		}
	}
	
	protected void toAcc(DBObject o, User acc){
		acc.accId = ((ObjectId)o.get("_id")).toString();
    acc.userId = (String)o.get(USER_ID);
    acc.pwdEncoded = (String)o.get(ENC_PWD);
    if(o.get(CREATE_TIME) != null)
    	acc.createdTime = (Date)o.get(CREATE_TIME);
    if(o.get(UPDATE_TIME) != null)
    	acc.updatedTime = (Date)o.get(UPDATE_TIME);
	}

}
