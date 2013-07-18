package com.activ.cdaj.user;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * User such as patient of a provider. Password is encoded. 
 */
public class User implements Serializable{
  private static final long serialVersionUID = 1L;
  
  public String accId;               //internal user account id  
  //protected String accType = TYPE_USER; //acc type
  public String userId;              //user id, unique, lowercase
  public String pwdEncoded;          //encoded password, for exposing to outside
  public Date createdTime;           //creation time 
  public Date updatedTime;           //last update time 

  public Set<String> acceptedAppIds = new HashSet<String>();
  	
  public User(String uid, String encpwd){
    this.userId = uid;
    this.pwdEncoded = encpwd;
  }

  public User(){}
  
  public boolean isValid(){
  	return userId != null && pwdEncoded != null;
  }

  /**check if encoded password matches*/
  public boolean matchEncodedPassword(String enc_pwd){
    return pwdEncoded.equals(enc_pwd)? true : false;
  }
    
  public boolean clientIdAccepted(String client_id){
  	return acceptedAppIds.contains(client_id);
  }
  
  public String toString(){
    return accId+"|"+userId;
  }
}
