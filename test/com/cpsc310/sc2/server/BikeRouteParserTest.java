/**
 * 
 */
package com.cpsc310.sc2.server;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.cpsc310.sc2.server.models.LineString;
import com.cpsc310.sc2.server.models.Route;
import com.cpsc310.sc2.server.parser.BikeRouteParser;

/**
 * @author peter9207
 * test for the bike route parser class
 */
public class BikeRouteParserTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testParseRoutes() {
		
		ArrayList<Route> routes = BikeRouteParser.parseRoutes("./war/bikeways.kml");
		
		//should be a total of 66 routes in the kml file
		assertEquals(routes.size(), 66);
		
		for(Route r : routes){
			//look for 1 perticular route to test
			if(r.getPlaceMark().equals("__FPCCMZ")){
				
				assertTrue(r.getName().equalsIgnoreCase("1st ave"));
				// there are a large amount of lineStrings
				assertTrue(r.getLineStrings().size()==38);
				
				// 21 sets of linestrings with 2 sets of coordinates inside
				int count = 0;
				for(LineString ls: r.getLineStrings()){
					if(ls.getCoordinates().size() == 2){
						count++;
					}
				}
				
				assertTrue(count == 21);
				
				
			}
		}
		
		
		
	}

}
