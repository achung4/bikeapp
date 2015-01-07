package com.cpsc310.sc2.server.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.cpsc310.sc2.server.models.Coordinate;
import com.cpsc310.sc2.server.models.LineString;
import com.cpsc310.sc2.server.models.Route;
/**
 * BikeRoute Parser to parse kml data for bike routes
 * 
 * @author peter9207
 *
 */
public class BikeRouteParserHandler extends DefaultHandler {
	
	
	
	private ArrayList<Route> routes;
	
	private String currVal;
	private Route currRoute;
	private LineString currLS;
	private Coordinate currCoord;
	
	public BikeRouteParserHandler (){
		routes = new ArrayList<Route>();
	}
	
	@Override
	public void startElement(String s, String s1, String elementName, Attributes attributes) throws SAXException{
		if(elementName.equalsIgnoreCase("placemark")){
			currRoute = new Route(attributes.getValue("id"));
			//System.out.println("Route: "+ attributes.getValue("id"));
			
		}else
		if(elementName.equalsIgnoreCase("LineString")){
			//System.out.println("new LS");
			currLS = new LineString();
		}
	}

	@Override
	public void endElement(String s, String s1, String element) throws SAXException {
		if (currRoute != null) {
			if (element.equalsIgnoreCase("name")) {
				currRoute.setName(currVal);
				
			} else if (element.equalsIgnoreCase("description")) {
				currRoute.setDescription(currVal);
				
			} else if (element.equalsIgnoreCase("linestring")) {
				currRoute.addLineString(currLS);
			} else if (element.equalsIgnoreCase("placemark")) {
				routes.add(currRoute);
			} else if (element.equalsIgnoreCase("coordinates")) {
				ArrayList<Coordinate> coords = parseCoordinates(currVal);
				for(Coordinate c: coords){
					//System.out.println("coordinates added"+ c);
					currLS.addCoordinate(c);
				}
				
			}
		}
	}
	
	@Override
	public void characters(char[] ac, int i, int j) throws SAXException {
		currVal = new String(ac, i , j);
	}
	
	public ArrayList<Route> getRoutes(){
		return routes;
	}
	
	private ArrayList<Coordinate> parseCoordinates(String coords){
		ArrayList<Coordinate> results = new ArrayList<Coordinate>();
		String[] splitCoords = coords.split(" ");
		
		for(String c : splitCoords){
			String[] vals = c.split(",");
			String xval = vals[0];
			String yval = vals[1];
			String zval = vals[2];
			
			Coordinate coord = new Coordinate();
			coord.setLat(Double.parseDouble(yval));
			coord.setLang(Double.parseDouble(xval));
			coord.setElev(Double.parseDouble(zval));
			results.add(coord);
			
			
		}
		
		return results;
	}
	
	
	
}
