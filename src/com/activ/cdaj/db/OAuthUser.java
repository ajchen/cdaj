package com.activ.cdaj.db;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**representing user authorization for a client application to access certain data */
public class OAuthUser {

	//access token
  public String accessToken;  //used as internal id
  public Long expiresIn;
  public String refreshToken;

  //access scope
  //public String scope;  //scope string, eg: "summary search"
	public Set<String> scopes = new HashSet<String>(); 

  //for user and client aaplication
	public String userId;
	public String clientId;
	public String redirectUri;

  //internal fields
  public Date _create_time;
  public Date _update_time;
  
  public String toString(){
  	return userId+" | "+clientId+" | "+scopes+" | "+accessToken+" | "+expiresIn;
  }
}
