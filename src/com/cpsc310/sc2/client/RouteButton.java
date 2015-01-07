package com.cpsc310.sc2.client;

import java.util.ArrayList;
import java.util.List;

import com.cpsc310.sc2.server.models.Route;
import com.google.gwt.user.client.ui.Button;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.InfoWindow;
import com.google.maps.gwt.client.Polygon;
import com.google.maps.gwt.client.Polyline;

public class RouteButton extends Button{
	private boolean displayed;
	private int index;
	private String placeMark;
	private ArrayList<Polyline> bpList;
	private ArrayList<Polygon> polygons;
	
	public RouteButton(String text){
		super.setText(text);
		this.displayed = false;
	}
	
	public boolean isDisplayed(){
		return displayed;
	}
	
	public void setDisplayed(boolean b){
		this.displayed = b;
	}
	
	public void setIndex(int i){
		this.index = i;
	}
	public int getIndex(){
		return index;
	}
	
	public void setPath(ArrayList<Polyline> bpList){
		this.bpList = bpList;
	}
	
	public ArrayList<Polyline> getPath(){
		return bpList;
	}
	
	public void setPoly(ArrayList<Polygon> p){
		this.polygons = p;
	}
	
	public ArrayList<Polygon> getPoly(){
		return polygons;
	}
	
	public void setPM(String placeMark){
		this.placeMark = placeMark;
	}
	public String getPM(){
		return placeMark;
	}
}
