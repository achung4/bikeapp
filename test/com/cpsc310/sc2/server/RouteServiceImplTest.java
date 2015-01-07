///**
// * 
// */
//package com.cpsc310.sc2.server;
//
//import static org.junit.Assert.*;
//
//import java.util.List;
//
//import javax.jdo.JDOHelper;
//import javax.jdo.PersistenceManager;
//import javax.jdo.Query;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.cpsc310.sc2.client.RouteService;
//import com.cpsc310.sc2.client.RouteServiceAsync;
//import com.cpsc310.sc2.models.Route;
//import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
//import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
//import com.google.gwt.core.shared.GWT;
//import com.google.gwt.user.client.rpc.AsyncCallback;
//
///**
// * @author peter9207
// *
// */
//public class RouteServiceImplTest {
//
//	private RouteServiceAsync routeService;
//	private LocalServiceTestHelper helper  = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
//	private PersistenceManager pm ;
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@Before
//	public void setUp() throws Exception {
//		helper.setUp();
//		routeService = GWT.create(RouteService.class);
//		pm = JDOHelper.getPersistenceManager("transactions-optional");
//	}
//	@After
//	public void tearDown(){
//		helper.tearDown();
//		routeService = null;
//		pm = null;
//	}
//
//	@Test
//	public void testAddRoute() {
//		Route r1 = new Route("route 1");
//		Route r2 = new Route("route 2");
//		
//		AsyncCallback<Void> callback = new AsyncCallback<Void>(){
//			@Override
//			public void onFailure(Throwable caught) {
//				// TODO Auto-generated method stub
//				fail("Add route callback failed");
//			}
//			@Override
//			public void onSuccess(Void result) {
//				//sucess
//			}
//		};
//		
//		routeService.addRoute(r1, callback);
//		routeService.addRoute(r2, callback);
//		
//		Query q = pm.newQuery(Route.class);
//		@SuppressWarnings("unchecked")
//		List<Route> result = (List<Route>) q.execute();
//		assertEquals(result.size(), 2);
//	}
//	
//	@Test
//	public void testGetRoute(){
//		final Route r1 = new Route("route 1");
//		pm.makePersistent(r1);
//		
//		routeService.getRoute(r1.getName(),	new AsyncCallback<Route>(){
//
//			@Override
//			public void onFailure(Throwable caught) {
//				// TODO Auto-generated method stub
//				fail("get route callback failed");
//			}
//
//			@Override
//			public void onSuccess(Route result) {
//				// TODO Auto-generated method stub
//				assertTrue(r1.getName().equals(result.getName()));
//			}
//			
//		});
//		
//	}
//
//}
