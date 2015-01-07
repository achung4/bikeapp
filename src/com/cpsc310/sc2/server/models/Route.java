package com.cpsc310.sc2.server.models;

import java.util.ArrayList;
import javax.jdo.annotations.*;

import com.google.gwt.user.client.rpc.IsSerializable;


@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Route implements IsSerializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@PrimaryKey
	private String placeMark;
	@Persistent
	private String name;
	@Persistent
	private String description; 


	@Persistent
	private ArrayList<LineString> lineStrings;

	public Route(){
		lineStrings = new ArrayList<LineString>();
	}

	/**
	 * Route identified by it's placemark
	 * @param placemark Placemark
	 */
	public Route(String placemark){
		this();
		this.setPlaceMark(placemark);
	}
	public void setName(String name){
		this.name = name;
	}

	/**
	 * set the description for the route
	 * @param desc description
	 */
	public void setDescription(String desc){
		description = desc;
	}

	/**
	 * add a LineString to the Route
	 * @param ls
	 */
	public void addLineString(LineString ls){
		lineStrings.add(ls);
	}

	public ArrayList<LineString> getLineStrings(){
		return this.lineStrings;
	}

	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}

	public boolean equals(Route r){
		return r.getName().equals(name);
	}

	public String getPlaceMark() {
		return placeMark;
	}

	public void setPlaceMark(String placeMark) {
		this.placeMark = placeMark;
	}

	public String toString(){
		String s =  "Name: "+ name +
				    "Placemark" +  placeMark +
				    "Description" + description;
		
		for(LineString ls : lineStrings){
			s += ls + "\n";
		}
		return s;
	}
	
	public Route copy(){
		Route r = new Route();
		r.name = new String(name);
		r.placeMark = new String(placeMark);
		r.description = new String(description);
		for(LineString ls: lineStrings){
			r.addLineString(ls.copy());
		}
		
		return r;
	}


}

