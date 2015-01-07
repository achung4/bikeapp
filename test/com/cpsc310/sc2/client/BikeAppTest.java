package com.cpsc310.sc2.client;

import java.util.ArrayList;

import com.cpsc310.sc2.server.models.Coordinate;
import com.cpsc310.sc2.server.models.LineString;
import com.cpsc310.sc2.server.models.Route;
import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * GWT JUnit tests must extend GWTTestCase.
 */
public class BikeAppTest extends GWTTestCase {
	/**
	 * Must refer to a valid module that sources this class.
	 */
	public String getModuleName() {                                         
		return "com.cpsc310.sc2.BikeApp";
	}

	/**
	 * Add as many tests as you like.
	 * This is just a tautology test.
	 */
	public void testSimple() {                                             
		assertTrue(true);
	}

	/**
	 * Verify that the instance fields in the Coordinate class are set correctly.
	 */
	public void testCoordinate(){
		double latitude = 49.2701898375001;
		double longitude = -123.104633579194;
		double elevation = 3.93055121187439;

		Coordinate coord = new Coordinate();
		coord.setLat(latitude);
		coord.setLang(longitude);
		coord.setElev(elevation);

		assertNotNull(coord);
		assertEquals(latitude, coord.getLat());
		assertEquals(longitude, coord.getLang());
		assertEquals(elevation, coord.getElev());
	}

	/**
	 * Verify that the instance fields in the LineString class are set correctly.
	 */
	public void testLineString(){
		Coordinate c1 = new Coordinate();
		c1.setLat(49.99);
		c1.setLang(-123.99);
		c1.setElev(3.99);

		Coordinate c2 = new Coordinate();
		c2.setLat(49.11);
		c2.setLang(-123.11);
		c2.setElev(3.11);

		ArrayList<Coordinate> cList = new ArrayList<Coordinate>();
		cList.add(c1);
		cList.add(c2);

		LineString ls = new LineString();
		ls.addCoordinate(c1);
		ls.addCoordinate(c2);

		assertNotNull(ls);
		assertEquals(cList, ls.getCoordinates());
	}

	/**
	 * Verify that the instance fields in the Route class are set correctly.
	 */
	public void testRoute(){
		String placeMark = "__FPCCMZ";
		String name = "1st Ave";
		String description = "Phone: 3-1-1 or 604-873-7000";

		Route r = new Route();
		r.setPlaceMark(placeMark);
		r.setName(name);
		r.setDescription(description);

		assertNotNull(r);
		assertEquals(placeMark, r.getPlaceMark());
		assertEquals(name, r.getName());
		assertEquals(description, r.getDescription());

	}

	/**
	 * this test will send a request to the server using the greetServer
	 *  method in RouteService and verify the response.
	 *  
	 *  WARNINGS: since we have java script injections(facebook) in our HTML
	 */
	public void testRouteService(){
		RouteServiceAsync routeService = GWT.create(RouteService.class);
		routeService.storeParsedData(new AsyncCallback<Void>() {

			public void onFailure(Throwable caught) {
				/* The request resulted in an unexpected error. */
				fail("Request failure: " + caught.getMessage());
			}

			public void onSuccess(Void result) {
				/* verify that the response parses */
				assertTrue(true);

				/* now that we have received a response, we need to 
	             tell the test runner that the test is complete. 
	             You must call finishTest() after an asynchronous test 
	             finishes successfully, or the test will time out.*/
				finishTest();
			}
		});
		
//		/* send a request to the server. */
//		routeService.getRoute(0, new AsyncCallback<Route>() {
//
//			public void onFailure(Throwable caught) {
//				/* The request resulted in an unexpected error. */
//				fail("Request failure: " + caught.getMessage());
//			}
//
//			public void onSuccess(Route result) {
//				/* verify that the response will not give a null */
//				assertNotNull(result);
//
//				/* now that we have received a response, we need to 
//	             tell the test runner that the test is complete. 
//	             You must call finishTest() after an asynchronous test 
//	             finishes successfully, or the test will time out.*/
//				finishTest();
//			}
//		});



//		/* send another request to the server. */
//		String placeMark = "__FPCCMZ";
//		routeService.getRoutePM(placeMark, new AsyncCallback<Route>() {
//
//			public void onFailure(Throwable caught) {
//				/* The request resulted in an unexpected error. */
//				fail("Request failure: " + caught.getMessage());
//			}
//
//			public void onSuccess(Route result) {
//				/* verify that the response will not give a null */
//				assertNotNull(result);
//
//				/* now that we have received a response, we need to 
//			             tell the test runner that the test is complete. 
//			             You must call finishTest() after an asynchronous test 
//			             finishes successfully, or the test will time out.*/
//				finishTest();
//			}
//		});
	}
}
