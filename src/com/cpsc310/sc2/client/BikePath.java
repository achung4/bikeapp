package com.cpsc310.sc2.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BikePath  implements Serializable {
	String name;
	private double longt;

	private double latt;

	private double longt2;

	private double latt2;
	
	

	public BikePath(String value) {
		// TODO Auto-generated constructor stub
		
	}

	
	public void setname(String name){
		this.name = name;
		
	}


	public String convertname(String nameString) {
		String n = nameString.substring(0,8);
		return n;
	}

	
	public void setLongt(Double longtt){
		this.longt = longtt;
	}
	public void setLatt(Double lattt){
		this.latt = lattt;
	}
	public void setLong2(Double longt22){
		this.longt2 = longt22;
	}
	public void setLatt2(Double lattt22){
		this.latt2 = lattt22;

	}
	
	
	public double getLatt(){
		return this.latt;
	}
	
	public double getLatt2(){
		return this.latt2;
	}
	public double getLongt(){
		return this.longt;
	}
	public double getLongt2(){
		return this.longt2;
	}

	public List<Double> parsedCoordinatesToLatAndLong(String coordinates) {
		
		List<Double> latandlongs  = new ArrayList<Double>();
		
		String st1 = null;
		Double dob1 = null;
		
		String st2 = null;
		Double dob2 = null;
		
		String st3 = null;
		Double dob3 = null;
		
		String st4 = null;
		Double dob4 = null;
				
		int i,j,k,r;
	
		
		
		
		for( i = 1; i < coordinates.length(); i++){
			
			if(coordinates.charAt(i) == ','){
				st1 = coordinates.substring(1, i);
				dob1 = 0 - Double.parseDouble(st1);
				i ++;
				break;
			}
		}
		latandlongs.add(dob1);
		
		
		for(j = i; j < coordinates.length(); j++){
			if(coordinates.charAt(j) == ','){
				st2 = coordinates.substring(i, j);
				dob2 = Double.parseDouble(st2);
				j += 4;
				break;
			}
		}
		latandlongs.add(dob2);
		
		
		
		for(k = j; k < coordinates.length(); k++){
			if(coordinates.charAt(k) == ','){
				st3 = coordinates.substring(j, k);
				dob3 = Double.parseDouble(st3);
				k++;
				break;
			}
		}
		latandlongs.add(dob3);
		
		
		
		for(r = k; r < coordinates.length(); r++){
			if(coordinates.charAt(r) == ','){
				st4 = coordinates.substring(k, r);
				dob4 = Double.parseDouble(st4);
				r++;
				break;
			}
		}
		latandlongs.add(dob4);
		
		
		return latandlongs;
		
	}
	
	




	
}
