package com.cpsc310.sc2.server.models;

import java.util.ArrayList;

import javax.jdo.annotations.*;
//import com.google.appengine.api.datastore.Key;

import com.google.gwt.user.client.rpc.IsSerializable;



@SuppressWarnings("unused")
@PersistenceCapable(identityType = IdentityType.DATASTORE)
public class LineString implements IsSerializable{
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Extension(vendorName="datanucleus", key="gae.encoded-pk", value="true")
	private String key;
	
	@Persistent
	private ArrayList<Coordinate> coordinates;
	
	
	public LineString(){
		init();
	}
	
	public LineString(ArrayList<Coordinate> coords){
		init();
	}
	
	private void init(){
		coordinates = new ArrayList<Coordinate>();
	}
	
	public String getKey(){
		return key;
	}
	
	public void addCoordinate(Coordinate coord){
		coordinates.add(coord);
	}
	
	public ArrayList<Coordinate> getCoordinates(){
		return coordinates;
	}

	public LineString copy(){
		LineString ls = new LineString();
		ls.key = new String(key);
		
		for(Coordinate c: coordinates){
			ls.addCoordinate(c.copy());
		}
		
		return ls;
	}
	public String toString(){
		String s = super.toString();
		s += "    Coordinates" ;
		for(Coordinate c: coordinates){
			s += "\n         " + c;
		}
		
		return s;
	}
	
}
