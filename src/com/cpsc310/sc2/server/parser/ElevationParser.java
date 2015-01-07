
//package com.cpsc310.sc2.server.parser;
//
//
//import java.io.IOException;
//import java.io.FileReader;
//import java.io.BufferedReader;
//import java.util.LinkedList;
//
//import uk.me.jstott.jcoord.UTMRef;
//import uk.me.jstott.jcoord.LatLng;
//
//
//
//public class ElevationParser {
//	public static void main(String[] args) throws IOException{
//	String testPath = "\\src\\textfiles\\DEM.dxf";
//	// REPLACE WITH WHEREEVER YOUR DXF File is. This uses absolute path.
//	
//
//	LinkedList<Triple<Double, Double, Double>> fileContent = OpenFile(testPath);
////	for (Triple t:fileContent)
////	{
//////	System.out.println(t.a);
//////	System.out.print(t.b);
//////	System.out.print(t.c);
////	
////	}
//	LatLng ll;
//	UTMRef m;
//	for(Triple<Double, Double, Double> point : fileContent){
//		 
//	m = new UTMRef(point.x, point.y, 'N', 10);  //after these steps the linked list now holds lat long coordinates + z, which is elevation in meters
//	ll = m.toLatLng();
//	point.x=ll.getLat();
//	point.y=ll.getLng();
//	
//	
//	}
//	System.out.println("Done!" + fileContent.size());
//}
//	
//	public static LinkedList<Triple<Double, Double, Double>> OpenFile(String path) throws IOException{
//		
//		LinkedList<Triple<Double, Double, Double>> textData = new LinkedList<Triple<Double, Double, Double>>();
//		FileReader fr = new FileReader(path);
//		BufferedReader textReader = new BufferedReader(fr);
//		String sx;
//		String sy;
//		String sz;
//		try{
//		String currentLineData = "";
//		Triple<Double,Double,Double> triplexyz;
//		int counter =0;
//		while ((currentLineData=textReader.readLine())!=null) //get the next line, while this is not null
//		{
//			if (currentLineData.equals("AcDbPoint"))
//			{
//				textReader.readLine();
//				sx=textReader.readLine();
//				textReader.readLine();
//				sy=textReader.readLine();
//				textReader.readLine();
//				sz=textReader.readLine();
//				triplexyz=new Triple<Double, Double, Double>(Double.valueOf(sx), Double.valueOf(sy), Double.valueOf(sz));
//				//System.out.println(triplexyz.x + ", " + triplexyz.y + ", " + triplexyz.z);
//			}
//			counter+=1;
//			//if (counter>100000)
//			//{System.out.println("broke out at 100000 points");
//			//	break;}
//			//System.out.println(counter);
//			//textData.add(currentLineData); //add the parsed line to the linked list
//			//System.out.println("hi" + textData.getLast());
//		}
//		textReader.close();
//		}
//		catch(IOException e)
//		{
//		System.out.println("Something went wrong: " + e.getMessage());	
//		}
//		return textData;
//		
//	}
//
//}
//
//
