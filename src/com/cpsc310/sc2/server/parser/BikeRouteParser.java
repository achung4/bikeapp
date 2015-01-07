package com.cpsc310.sc2.server.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;



import com.cpsc310.sc2.server.models.Route;
import java.net.URL;
import java.net.URLConnection;


public class BikeRouteParser {
	
	public static ArrayList<Route> parseRoutes(String url){
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		
		BikeRouteParserHandler handler = null;
		try {
			//URL data = new URL(url);
			
			SAXParser parser = factory.newSAXParser();
			handler = new BikeRouteParserHandler();
			//parser.parse(data.openConnection().getInputStream(), handler);
			parser.parse(new File(url), handler);
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return handler.getRoutes();
	}

}
