package com.cpsc310.sc2.server.models;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.gwt.user.client.rpc.IsSerializable;

//import com.google.appengine.api.datastore.Key;

//import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Coordinate implements IsSerializable{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String key;
	
	@Persistent
	private double lat;
	@Persistent
	private double lang;
	@Persistent
	private double elev;
	
	public Coordinate(){
	}
	
	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLang() {
		return lang;
	}

	public void setLang(double lang) {
		this.lang = lang;
	}

	public double getElev() {
		return elev;
	}

	public void setElev(double elev) {
		this.elev = elev;
	}

	public String toString(){
		return "lang: "+lang+" lat: "+lat + " elev "+elev;
	}
	
	public String getKey(){
		return key;
	}
	public Coordinate copy(){
		Coordinate c = new Coordinate();
		c.key = new String(key);
		c.setElev(elev);
		c.setLang(lang);
		c.setLat(lat);
		return c;
	}
	

}
