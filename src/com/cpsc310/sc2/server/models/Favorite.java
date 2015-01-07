package com.cpsc310.sc2.server.models;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.users.User;
import com.google.appengine.datanucleus.annotations.Unowned;
import com.google.gwt.user.client.rpc.IsSerializable;

@PersistenceCapable
public class Favorite implements IsSerializable{
	@Persistent
	private User user;
	@Persistent
	@Unowned
	private Route route;
	
	@PrimaryKey
	@Persistent
	private String key;
	
//	public Favorite(){
//		
//	}
	
	public Favorite(User user, Route route){
		key = user.getEmail()+route.getPlaceMark();
		this.user = user;
		this.route = route;
	}
	
	public void setUser(User user){
		this.user = user;
	}
	
	public void setRoute(Route route){
		this.route = route;
	}
	public User getUser(){
		return user;
	}
	public Route getRoute(){
		return route;
	}

}
