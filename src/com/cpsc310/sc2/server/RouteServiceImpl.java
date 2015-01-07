package com.cpsc310.sc2.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.cpsc310.sc2.client.RouteService;
import com.cpsc310.sc2.server.models.Coordinate;
import com.cpsc310.sc2.server.models.Route;
import com.cpsc310.sc2.server.parser.BikeRouteParser;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class RouteServiceImpl extends RemoteServiceServlet implements RouteService {

	private static final Logger LOG = Logger.getLogger(RouteServiceImpl.class.getName());
	private static final PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	/*
	 * explained in RouteService.java
	 */
	@Override
	public Route getRoute(int i) { //returns coordinates correctly
		PersistenceManager pm = PMF.getPersistenceManager();
		Route result = null;
		
		try{
			Query q = pm.newQuery(Route.class);

			q.getFetchPlan().setFetchSize(500);
			q.getFetchPlan().setMaxFetchDepth(10);
			List<Route> objs = ((List<Route>) q.execute());
			
			result = objs.get(i).copy();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			pm.close();
		}
		
		//System.out.println("RouteServiceImpl.getRoute returns: "+result);
		return result;
	}
	
	
	public Route[] getRouteTest() { //colin: this will become a getRoutes function
		Route[] results = null;
		
		PersistenceManager pm = PMF.getPersistenceManager();
		Route result = null;
		
		try{
			Query q = pm.newQuery(Route.class);

			q.getFetchPlan().setFetchSize(500);
			q.getFetchPlan().setMaxFetchDepth(10);
			List<Route> objs = ((List<Route>) q.execute());			
			
			System.out.println("RouteServiceImpl: objs size: " + objs.size());
			results = new Route[objs.size()];
		for (int i=0; i<objs.size(); i++)
			{
				System.out.println("RouteServiceImpl: copying route: " + i);
				
				results[i]=objs.get(i).copy();
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			pm.close();
		}
		
		//System.out.println("RouteServiceImpl.getRoute returns: "+result);
		return results;
	}
	
	/*
	 * explained in RouteService.java
	 */
	@Override
	public Route getRoutePM(String name) { //this does not return the coordinates
		PersistenceManager pm = PMF.getPersistenceManager();
		Route result = null;
	
		try{
			Query q = pm.newQuery(Route.class);

			q.setFilter("placeMark == name");
			q.declareParameters("String name");

			List<Route> objs = ((List<Route>) q.execute(name));
			if (!objs.isEmpty()) {
				result = objs.get(0).copy();
			} else {
				Window.alert("Place Mark not found!");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("error getting place mark!");
		}finally {
			pm.close();
		}
		
		System.out.println("RouteServiceImpl.getRoute returns: "+result);
		return result;
	}

	//
	@Override
	public void addRoute(Route route) {
		// TODO Auto-generated method stub
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			pm.makePersistent(route);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			pm.close();
		}
		
	}

	@Override
	public Route[] getRoutes() { //this also does not return the coordinates
		PersistenceManager pm = PMF.getPersistenceManager();
		List<Route> result;
		ArrayList<Route> al = new ArrayList<Route>();
		try{
			Query q = pm.newQuery(Route.class);
			result = (List<Route>) q.execute();
			
			for(Route r : result){
				al.add(r);
			}
		}finally{
			pm.close();
		}
		return al.toArray(new Route[al.size()]);
	}

	@Override
	public void storeParsedData() {
		List<Route> routes = BikeRouteParser.parseRoutes("./bikeways.kml");
		for(Route r: routes){
			addRoute(r);
		}
		
	}
	
	public Coordinate[] getCoordinates() {
		PersistenceManager pm = PMF.getPersistenceManager();
		List<Coordinate> result;
		ArrayList<Coordinate> al = new ArrayList<Coordinate>();
		try{
			Query q = pm.newQuery(Coordinate.class);
			result = (List<Coordinate>) q.execute();
			
			for(Coordinate r : result){
				al.add(r);
			}
		}finally{
			pm.close();
		}
		return al.toArray(new Coordinate[0]);
	}
	
	

}
