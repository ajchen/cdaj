package com.activ.cdaj.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.activ.cdaj.user.User;
import com.activ.cdaj.user.UserDao;

/** managing users in local db */
public class UserMgr {
	private static final Logger LOG = Logger.getLogger(UserMgr.class);

	//invalid char for user id: any char except word char and -
	public static Pattern ptn_bad_user_char = Pattern.compile("[^\\w\\-]");

	public UserMgr(){
	}

  /**
	 * authenticate user by id and password.
	 * @param user_id  user id
	 * @param password  password
	 * @return user profile matching user id and password
	 */
	public User authenticateUser(String user_id, String password){
		if(user_id != null && password != null)
			return authenticateUserEnc(user_id, encodePassword(password));
		return null;
	}
	
	/**
	 * authenticate user by id and encrypted password.
	 * @param user_id  user id
	 * @param enc_password  password, encoded
	 * @return user profile matching user id and password
	 */
	public User authenticateUserEnc(String user_id, String enc_password){
		if(user_id != null && enc_password != null){
			UserDao dao = new UserDao();
			User user = dao.getUserByUserId(user_id);
			if(user != null && user.matchEncodedPassword(enc_password)){
				LOG.info("user authenticated: "+user_id);                
				return user;
			}
		}
		LOG.info("user not authenticated: "+user_id);                
		return null;
	}


	
	/** 
	 * check if the given user id is valid or not.
	 * valid characters: a-Z 0-9 - _
	 */
	public static boolean isValidUserId(String user_id){
		if(user_id == null || user_id.trim().length() == 0){
			return false;
	  }
		Matcher m = ptn_bad_user_char.matcher(user_id);
		if(m.find()){  //has invalid char
			return false;
		}
		return true;
	}
	
	/**encode password by base64*/
	public static String encodePassword(String password)  {
	    try{
	      return new String((new Base64()).encode(password.getBytes()));
	    }catch(Exception e){
	      LOG.error(e);
	    }
	    return password; 
	}
	  
	/**decode password by base64*/
	public static String decodePassword(String enc_password){
	    try{
	      return new String((new Base64()).decode(enc_password.getBytes()));
	    }catch(Exception e){
	      LOG.error(e,e);
	    }
	    return enc_password;
	}



}
