package com.cpsc310.sc2.client;

import java.util.ArrayList;
import java.util.List;

import com.cpsc310.sc2.server.models.*;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.Color;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.ColumnChart;
import com.google.gwt.visualization.client.visualizations.PieChart;
import com.google.gwt.visualization.client.visualizations.PieChart.Options;

import com.google.gwt.dom.client.Document;
import com.google.maps.gwt.client.Geocoder;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.InfoWindow;
import com.google.maps.gwt.client.InfoWindowOptions;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MVCArray;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.MouseEvent;
import com.google.maps.gwt.client.Polyline;
import com.google.maps.gwt.client.PolylineOptions;
import com.google.maps.gwt.client.Polygon;
import com.google.maps.gwt.client.PolygonOptions;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BikeApp implements EntryPoint {

	public static boolean DEBUG = true;

	private GoogleMap map;
	private Geocoder geocoder;
	private List<Polyline> polylines = new ArrayList<Polyline>();

	// creates a table
	private FlexTable routeFlexTable = new FlexTable();
	// create vertical panel
	private VerticalPanel mainPanel = new VerticalPanel();

	private HorizontalPanel searchPanel = new HorizontalPanel();

	private boolean displayed = false;

	private Button displayfavButton = new Button("Display My Favourite Routes");
	private Button displayButton = new Button("Display All Routes");
	private HorizontalPanel functionalityPanel = new HorizontalPanel();
	private Button hidefavButton = new Button("Hide My Favourite Routes");

	private CheckBox check = new CheckBox();

	private TextBox newSearchTextBox = new TextBox();
	private Button searchButton = new Button("Search");
	private ListBox dropDownList = new ListBox();
	// added for displayallroute button
	private Route[] routetorpint;

	final FlexTable routeTable = new FlexTable();

	private List<InfoWindow> windows = new ArrayList<InfoWindow>();

	private boolean parsed = false;

	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access this bike app application.");
	private Anchor signInLink = new Anchor("Sign In");
	private Anchor signOutLink = new Anchor("Sign Out");

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// Check login status using login service.
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(),
				new AsyncCallback<LoginInfo>() {
			public void onFailure(Throwable error) {
				Window.alert("Login service ERROR!!!");
			}

			public void onSuccess(LoginInfo result) {
				loginInfo = result;
				System.out.println("BikeApp: nickname: " + loginInfo.getNickname());
				System.out.println("BikeAPP: email address: " + loginInfo.getEmailAddress());
				//						Window.alert("BikeApp: nickname: " + loginInfo.getNickname());
				//						Window.alert("BikeAPP: email address: " + loginInfo.getEmailAddress());


				if (loginInfo.isLoggedIn()) {
					System.out.println("logged in");
					load();
				} else {
					loadLogin();
					System.out.println("not logged in");
				}
			}
		});
	}


	private void display(Route rt) {
		drawRouteElevation(rt);
		displayRoutePath(rt);
	}

	private void displayRoutePath(Route rt) {
		final String name = rt.getName();
		final String desc = rt.getDescription();

		System.out.println( rt.getLineStrings());

		for (LineString ls : rt.getLineStrings()) {

			MVCArray<LatLng> bikePathCoordinates = MVCArray.create();
			for (Coordinate cd : ls.getCoordinates()) {
				bikePathCoordinates.push(LatLng.create(cd.getLat(),
						cd.getLang()));
			}

			PolylineOptions polyOpts = PolylineOptions.create();
			polyOpts.setPath(bikePathCoordinates);
			polyOpts.setStrokeColor("#FF0000");
			polyOpts.setStrokeOpacity(1.0);
			polyOpts.setStrokeWeight(2);

			Polyline bikepath = Polyline.create(polyOpts);
			bikepath.setMap(map);

			// start of code to draw elevation data (as polygons) on the map


			// end of code for drawing elevation data

			// added to list of polylines for clearmap
			polylines.add(bikepath);

			// pop up infowindow to show information of chosen bikepath

			InfoWindowOptions infowindowOps = InfoWindowOptions.create();
			infowindowOps.setContent("<div align='center'><b>You have chosen bikepath on : "+name+"/For more info please call "+desc+" </b></div>"
					+"<iframe allowtransparency='true' frameborder='0' scrolling='no'src='https://platform.twitter.com/widgets/tweet_button.html' style='width:130px; height:20px;'></iframe>" 
					+ "<iframe src='http://www.facebook.com/plugins/comments.php?href=http://joeyxfy.appspot.com&permalink=1' scrolling='no' frameborder='0' style='border:none; overflow:hidden; width:130px; height:16px;' allowTransparency='true'></iframe>"
					+ "<iframe src='https://www.facebook.com/plugins/like.php?href=YOUR_URL' scrolling='no' frameborder='0'style='border:none; width:450px; height:80px'></iframe>"
					);

			final InfoWindow infowd = InfoWindow.create(infowindowOps);
			bikepath.addClickListener(new Polyline.ClickHandler() {
				public void handle(MouseEvent event) {
					for (InfoWindow iw : windows)
						iw.close();
					infowd.open(map);
					infowd.setPosition(event.getLatLng());
					windows.add(infowd);
				}
			});
			displayed = true;

		}

		/*
		 * polylines.add(bikepath); final Polyline routepath =
		 * Polyline.create(polyOpts); routepath.setMap(map);
		 * polylines.add(routepath);
		 */
	}
	ArrayList<Polygon> polygons = new ArrayList<Polygon>();
	private void drawRouteElevation(Route rt)
	{
		/**@param a bike route
		 * draws the elevation data for a Route on the GoogleMap in the form of coloured squares (polygons)
		 */
		for (LineString ls : rt.getLineStrings()) {


			PolygonOptions po = PolygonOptions.create();
			po.setClickable(false);
			po.setFillColor("#0000FF"); // draw the polygon in green
			po.setStrokeColor("#999999");
			po.setStrokeOpacity(1.0);
			po.setStrokeWeight(0.1);
			po.setFillOpacity(1);

			double size = 0.0001; // the size of the elevation marking squares
			double curLat = 0;
			double curLng = 0;
			double curElev = 0;
			MVCArray<MVCArray<LatLng>> containingArray = MVCArray.create();

			for (Coordinate cd : ls.getCoordinates()) {
				curLat = cd.getLat();
				curLng = cd.getLang();
				curElev = cd.getElev();

				LatLng l1 = LatLng.create(curLat - size, curLng + size); // these
				// will
				// form
				// the
				// boundaries
				// of
				// the
				// square
				LatLng l2 = LatLng.create(curLat - size, curLng - size);
				LatLng l3 = LatLng.create(curLat + size, curLng - size);
				LatLng l4 = LatLng.create(curLat + size, curLng + size);

				MVCArray<LatLng> testPolygonCoords = MVCArray.create();
				testPolygonCoords.push(l1);
				testPolygonCoords.push(l2);
				testPolygonCoords.push(l3);
				testPolygonCoords.push(l4);
				testPolygonCoords.push(l1);

				long elevRatio = (long) curElev * 256 / 100; // elev: is between
				// 0 and 256
				String hs2 = Long.toHexString(elevRatio);

				String hs = "#" + "00" + hs2 + hs2; // concatate strings to
				// generate a valid color
				// hex string



				po.setFillColor(hs); // the purpose of this block was to
				// generate the color hs (a shade of
				// green/black, depending on the
				// elevation of the cooridnate)

				containingArray = MVCArray.create(); // clear the array
				containingArray.push(testPolygonCoords);

				po.setPaths(containingArray);
				Polygon poly = Polygon.create(po);

				polygons.add(poly);

				poly.setMap(map); // places the polygon on to the map
			}
		}
	}


	private void clearmap() {

		if (polylines != null) {
			for (Polyline plines : polylines)
				plines.setVisible(false);
				//plines.setMap((GoogleMap) null);
			//polylines.clear();
		}
		if (polygons != null) {
			for (Polygon poly : polygons) {
				poly.setVisible(false);
				//poly.setMap((GoogleMap) null);
			}
		}

		if (windows != null) {
			for (InfoWindow infow : windows)
				infow.close();
			windows.clear();
		}

	}

	private void loadLogin() {
		// Assemble login panel.
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("routeList").add(loginPanel);
	}

	private void load() {
		// map
		// /geocoder = Geocoder.create();
		MapOptions myOptions = MapOptions.create();
		myOptions.setZoom(12.0);
		myOptions.setCenter(LatLng.create(49.2634, -123.1383));
		myOptions.setMapTypeId(MapTypeId.ROADMAP);
		map = GoogleMap.create(Document.get().getElementById("map_canvas"),
				myOptions);

		// suppose to get routes from data base
		// Route rt = .getroutes()
		// display(rt);

		// Set up sign out hyperlink.
		signOutLink.setHref(loginInfo.getLogoutUrl());
		mainPanel.add(signOutLink);

		// configureUI_Default();

		configureUI();

		// RouteServiceAsync routeService = GWT.create(RouteService.class);
		routeFlexTable.setText(0, 0, "Name");

		searchPanel.add(newSearchTextBox);
		searchPanel.add(searchButton);
		searchPanel.add(dropDownList);
		searchPanel.setSpacing(5);
		searchPanel.setStyleName("applystyle1");

		functionalityPanel.add(displayButton);
		functionalityPanel.add(displayfavButton);
		functionalityPanel.add(hidefavButton);
		functionalityPanel.setSpacing(5);
		functionalityPanel.setStyleName("applystyle2");

		// Assemble main panel
		mainPanel.add(routeFlexTable);
		mainPanel.add(searchPanel);
		mainPanel.add(functionalityPanel);
		mainPanel.setSpacing(8);

		// Move cursor focus to the input box.
		newSearchTextBox.setFocus(true);

		// Listen for mouse events on the search button.
		searchButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				search();
			}
		});

		// Listen for keyboard events in the input box.
		newSearchTextBox.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER) {

					search();
				}
			}
		});

		// Listen for mouse events on the display button.
		// and display all route
		displayButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				// check if dispalyed or not, if already displayed,
				// clearoverlay()
				if (displayed == true) {
					clearmap();
					map.setZoom(12.0);
					map.setCenter(LatLng.create(49.2634, -123.1383));

					// change panel txt back to
					displayButton.setText("Display All Routes");
					// set it to undisplay
					displayed = false;
				} else {

					final RouteServiceAsync routeService = GWT
							.create(RouteService.class);
					routeService.getRoutes(new AsyncCallback<Route[]>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onSuccess(Route[] result) {
							// System.out.println(result.length);
							for (int i = 0; i < result.length; i++) {
								routeService.getRoute(i,
										new AsyncCallback<Route>() {

									@Override
									public void onFailure(
											Throwable caught) {
										// TODO Auto-generated method
										// stub

										Window.alert(caught
												.getMessage());
									}

									@Override
									public void onSuccess(Route result2) {
										// System.out.println(result);
										display(result2);

										// finished display, set
										// displaybutton to activate
										// hide all path,
										displayButton
										.setText("Hide All Routes");

									}
								});
							}
						}
					});

				}

			}
		});

		displayfavButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				//Window.alert("favorite display button pressed");
				FavoriteServiceAsync favoriteService = GWT.create(FavoriteService.class);

				favoriteService.getFavoriteRoutes(new AsyncCallback<Route[]>(){

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("getFavoriteRoutes failed");
						System.out.println("getFavoriteRoutes failed");
					}

					@Override
					public void onSuccess(Route[] result) {
						// display all the favorite routes
						//Window.alert("getFavoriteRoutes");
						for(Route r: result){
							RouteServiceAsync routeService = GWT.create(RouteService.class);
							routeService.getRoutePM(r.getPlaceMark(), new AsyncCallback<Route>(){

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onSuccess(Route result) {

									// TODO Auto-generated method stub
									//									if (displayed == true) {
									//										clearmap();
									//										map.setZoom(12.0);
									//										map.setCenter(LatLng.create(49.2634, -123.1383));
									//
									//										// change panel txt back to
									//										displayfavButton.setText("Display My Favourite Routes");
									//										// set it to undisplay
									//										displayed = false;
									//										return;
									//									}
									//display(result);
									//									else{
									//									display(result);
									//									displayfavButton.setText("Hide My Favourite Routes");
									//									}


									//displayRoutePath(result);


								}

							});
						}

					}

				});


			}




		});

		//some how same logic doesnt work with hide fav, thus creating a new button

		hidefavButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				clearmap();
			}


		});

		// Associate the Main panel with the HTML host page
		RootPanel.get("routeList").add(mainPanel);

		loadTables("__FPCCMZ");

	} // end of load() function

	/*
	 * assumes its search by route name;
	 */
	private void search() {
		final String word = newSearchTextBox.getText();

		RouteServiceAsync routeService = GWT.create(RouteService.class);
		routeService.getRoutes(new AsyncCallback<Route[]>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Route[] result) {
				ArrayList<Route> routes = new ArrayList<Route>();
				for (Route r : result) {
					if (r.getName().contains(word)) {
						routes.add(r);
					}
				}

				fillRouteTable(routes);

			}

		});

	}
	//private ArrayList<Polyline> bikepathList = new ArrayList<Polyline>();
	private void configureUI() {

		final Button addButton = new Button("Add");
		final TextBox nameField = new TextBox();
		final Label errorLabel = new Label();
		final Button otherButton = new Button("Count");
		final Button parseButton = new Button("Parse");

		// We can add style names to widgets
		addButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("sendButtonContainer").add(addButton);
		RootPanel.get("anotherButtonContainer").add(otherButton);
		RootPanel.get("parseButtonContainer").add(parseButton);
		RootPanel.get("parseData").add(routeTable);

		RouteServiceAsync routes = GWT.create(RouteService.class);
		routes.getRoutes(new AsyncCallback<Route[]>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Route[] result) {
				ArrayList<Route> routes = new ArrayList<Route>();
				for (Route r : result) {
					routes.add(r);
				}
				routeTable.clear();
				fillRouteTable(routes);
			}

		});

		parseButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub

				RouteServiceAsync routeService = GWT.create(RouteService.class);

				routeService.storeParsedData(new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Window.alert("Parse Failed");
					}

					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						Window.alert("Parse Sucess");
						parsed = true;
					}

				});
			}

		});

		routes.getRouteTest(new AsyncCallback<Route[]>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Route result[]) {
				System.out.println("Bikeapp: result length:" + result.length);
				System.out.println("Bikeapp: getRoutesTestSucess!");

			}

		});

		addButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				RouteServiceAsync routeService = GWT.create(RouteService.class);
				routeService.addRoute(new Route(nameField.getText()),
						new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Window.alert("Failed");
					}

					@Override
					public void onSuccess(Void result) {
						Window.alert("Sucess");

					}

				});

			}

		});

		otherButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				RouteServiceAsync routeService = GWT.create(RouteService.class);

				routeService.getRoutes(new AsyncCallback<Route[]>() {

					public void onFailure(Throwable t) {

					}

					@Override
					public void onSuccess(Route[] result) {
						// TODO Auto-generated method stub

						Window.alert("count = " + result.length);

					}
				});
			}
		});

	}

	private void fillRouteTable(final List<Route> routes) {
		int index = 0; // index 0 is the column headings

		routeTable.removeAllRows();
		
		
		for (final Route r : routes) {
			final RouteButton selectButton = new RouteButton("Show Route");
			Button addtofav = new Button("Add to Favourite");
			routeTable.setText(index, 0, r.getName());
			routeTable.setText(index, 1, r.getDescription());
			routeTable.setWidget(index, 2, selectButton);
			routeTable.setWidget(index, 3, addtofav);
			selectButton.setIndex(index);
			
			RouteServiceAsync routeService = GWT.create(RouteService.class);
			routeService.getRoutePM(r.getPlaceMark(),new AsyncCallback<Route>() {
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onSuccess(Route result) {
					//display(result);
					selectButton.setText("Show Route");
					final String name = result.getName();
					final String desc = result.getDescription();
					final String pm = result.getPlaceMark();
					ArrayList<Polyline> bikepathList = new ArrayList<Polyline>();
					ArrayList<Polygon> polygons = new ArrayList<Polygon>();
					selectButton.setPM(pm);
					for (LineString ls : result.getLineStrings()) {
					
						MVCArray<LatLng> bikePathCoordinates = MVCArray.create();
						for (Coordinate cd : ls.getCoordinates()) {
							bikePathCoordinates.push(LatLng.create(cd.getLat(),
									cd.getLang()));
						}

						PolylineOptions polyOpts = PolylineOptions.create();
						polyOpts.setPath(bikePathCoordinates);
						polyOpts.setStrokeColor("#FF0000");
						polyOpts.setStrokeOpacity(1.0);
						polyOpts.setStrokeWeight(2);

						Polyline bikepath = Polyline.create(polyOpts);
						bikepath.setMap(map);
						bikepath.setVisible(false);
						// start of code to draw elevation data (as polygons) on the map
						

						// end of code for drawing elevation data

						// added to list of polylines for clearmap
						polylines.add(bikepath);
						bikepathList.add(bikepath);
						
						// pop up infowindow to show information of chosen bikepath

						InfoWindowOptions infowindowOps = InfoWindowOptions.create();
						infowindowOps
								.setContent("<div align='center'><b>You have chosen bikepath on : "
										+ name
										+ " /For more info please call "
										+ desc
										+ " </b></div>");
						final InfoWindow infowd = InfoWindow.create(infowindowOps);
						bikepath.addClickListener(new Polyline.ClickHandler() {
							public void handle(MouseEvent event) {
								for (InfoWindow iw : windows)
									iw.close();
								infowd.open(map);
								infowd.setPosition(event.getLatLng());
								windows.add(infowd);
							}
						});
					}
					selectButton.setPath(bikepathList);
					////////////////////////ELEVATION
					/**@param a bike route
					 * draws the elevation data for a Route on the GoogleMap in the form of coloured squares (polygons)
					 */
					for (LineString ls : result.getLineStrings()) {


						PolygonOptions po = PolygonOptions.create();
						po.setClickable(false);
						po.setFillColor("#0000FF"); // draw the polygon in green
						po.setStrokeColor("#999999");
						po.setStrokeOpacity(1.0);
						po.setStrokeWeight(0.1);
						po.setFillOpacity(1);

						double size = 0.0001; // the size of the elevation marking squares
						double curLat = 0;
						double curLng = 0;
						double curElev = 0;
						MVCArray<MVCArray<LatLng>> containingArray = MVCArray.create();

						for (Coordinate cd : ls.getCoordinates()) {
							curLat = cd.getLat();
							curLng = cd.getLang();
							curElev = cd.getElev();

							LatLng l1 = LatLng.create(curLat - size, curLng + size); // these will form the boundaries of the square
							LatLng l2 = LatLng.create(curLat - size, curLng - size);
							LatLng l3 = LatLng.create(curLat + size, curLng - size);
							LatLng l4 = LatLng.create(curLat + size, curLng + size);

							MVCArray<LatLng> testPolygonCoords = MVCArray.create();
							testPolygonCoords.push(l1);
							testPolygonCoords.push(l2);
							testPolygonCoords.push(l3);
							testPolygonCoords.push(l4);
							testPolygonCoords.push(l1);

							long elevRatio = (long) curElev * 256 / 100; // elev: is between
							// 0 and 256
							String hs2 = Long.toHexString(elevRatio);

							String hs = "#" + "00" + hs2 + hs2; // concatate strings to
							// generate a valid color
							// hex string

							po.setFillColor(hs); // the purpose of this block was to
							// generate the color hs (a shade of
							// green/black, depending on the
							// elevation of the cooridnate)

							containingArray = MVCArray.create(); // clear the array
							containingArray.push(testPolygonCoords);

							po.setPaths(containingArray);
							Polygon poly = Polygon.create(po);

							polygons.add(poly);

							poly.setMap(map); // places the polygon on to the map
							poly.setVisible(false);
						}
					}
					selectButton.setPoly(polygons);
				}
				
			});
			
			addtofav.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					// TODO Auto-generated method stub
					FavoriteServiceAsync favService = GWT.create(FavoriteService.class);
					favService.addFavoriteRoute(r, new AsyncCallback<Void>(){

						@Override
						public void onFailure(Throwable caught) {
							// error handler
							System.out.println("Failed to add route "+ r.getPlaceMark()+" to favorites0");

						}

						@Override
						public void onSuccess(Void result) {
							//sucess

						}

					});
				}



			});

			selectButton.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					// display selected route
					
					// System.out.println("clicked");
					if (selectButton.isDisplayed()) {
						//bikepathList.get(selectButton.getIndex()).setVisible(false);
						for(Polyline bpl: selectButton.getPath()){
							bpl.setVisible(false);
						}
						for(Polygon p: selectButton.getPoly()){
							p.setVisible(false);
						}
						selectButton.setText("Show Route");
						selectButton.setDisplayed(false);
						/*clearmap();
						map.setZoom(12.0);
						map.setCenter(LatLng.create(49.2634, -123.1383));

						// change panel txt back to
						selectButton.setText("Show Route");
						// set it to undisplay
						displayed = false;
						return;*/
						//displayed = false;
					}

					else {
						//loadTables(selectButton.getPM());
						//bikepathList.get(selectButton.getIndex()).setVisible(true);
						for(Polyline bpl: selectButton.getPath()){
							bpl.setVisible(true);
						}
						for(Polygon p: selectButton.getPoly()){
							p.setVisible(true);
						}
						//displayed = true;
						selectButton.setText("Hide Selected Route");
						selectButton.setDisplayed(true);
						/*final RouteServiceAsync routeService = GWT.create(RouteService.class);
						routeService.getRoutePM(r.getPlaceMark(),new AsyncCallback<Route>() {
							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(Route result) {
								display(result);
								selectButton.setText("Hide Selected Route");

							}

						});*/

					}
				}

			});
			index++;
		}
	}

	private void loadTables(String placeMark) {
		Runnable onLoadCallback = new Runnable() {
			@Override
			public void run() {
				//donothing	  
			};

		};

		// Load the visualization api, passing the onLoadCallback to be called
		// when loading is done.
		// VisualizationUtils.loadVisualizationApi(onLoadCallback,
		// ColumnChart.PACKAGE);

		VisualizationUtils.loadVisualizationApi(onLoadCallback,PieChart.PACKAGE);

		final RouteServiceAsync routeService = GWT.create(RouteService.class);

		routeService.getRoutePM(placeMark, new AsyncCallback<Route>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("call to get routes[] failed -- from BikeApp.java");
				System.out.println(caught.getMessage());

			}

			@Override
			public void onSuccess(Route result) {
				System.out.println("Routes Array succesfully recieved -- from BikeApp.java");

				//Panel panel = RootPanel.get();

				// Create a pie chart visualization.
				//PieChart pie = new PieChart(createTable(), createOptions());
				AbstractDataTable data = createInitialElevationTableDataFromSingleRoute(result);// DataTable.create();

				ColumnChart.Options ccOptions = ColumnChart.Options.create(); // the package name is longer because there is a collision
				ccOptions.set3D(false);
				ccOptions.setHeight(400);
				ccOptions.setWidth(900);

				Color c = Color.create(); // set the color of the table
				// (hopefully)
				c.setFill("red");
				ccOptions.setBackgroundColor(c);
				ColumnChart column = new ColumnChart(data, ccOptions); // make
				// the
				// column
				// chart

				RootPanel.get("graphList").add(column); // add the column chart to the panel
				//panel.add(pie);

			}
		});

	}



	private AbstractDataTable createTable() { // creates the data for the both
		// tables
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, "Task");
		data.addColumn(ColumnType.NUMBER, "Hours per Day");
		data.addRows(2);
		data.setValue(0, 0, "Work");
		data.setValue(0, 1, 14);
		data.setValue(1, 0, "Sleep");
		data.setValue(1, 1, 10);
		return data;
	}


	private Options createOptions() { // creates options for just the pie table
		Options options = Options.create();
		options.setWidth(400);
		options.setHeight(240);
		options.set3D(true);
		options.setTitle("My Daily Activities");
		return options;
	}

	private AbstractDataTable createInitialElevationTableDataFromSingleRoute(Route route)
	{
		System.out.println("creating elv table from single route");
		//System.out.println("findme1");
		DataTable data = DataTable.create();
		//System.out.println("findme1");
		data.addColumn(ColumnType.STRING, "");
		//System.out.println("findme1");
		data.addColumn(ColumnType.NUMBER, "Elevation (m)");
		//System.out.println("findme1");



		//System.out.println("findme2");


		//System.out.println("findm3");
		int curLength = 0;
		//System.out.println("findme4");
		for (LineString ls: route.getLineStrings())
		{
			//System.out.println("findme5");

			for (Coordinate c: ls.getCoordinates())
			{
				//System.out.println("Innerloop: " + curLength);
				data.addRows(1);
				data.setValue(curLength, 0, "c" + Integer.toString(curLength)); //"row one" data-wise
				data.setValue(curLength, 1, c.getElev());
				curLength+=1;

			}

		}
		System.out.println("curLength: " + curLength);

		return data;
	}
}




