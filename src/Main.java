import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Main {
	private static final int LEVEL_COUNT = 80;
	private static final String routePath = "levels/";
	public static void main(String[] args) {
		try {
			float[] arr = getHardnesses();
			System.out.println(Arrays.toString(arr));
			//float hardValue = hardness(routePath +"level1.tmx",1);
			//System.out.println("Level hardness : "+hardValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static float[] getHardnesses(){
		float[] arr = new float[LEVEL_COUNT];
		for(int i =0;i<LEVEL_COUNT;i++) {
			try {
				arr[i] = hardness(routePath+String.format("level%d.tmx",i+1),i+1);	
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return arr;
	}
	public static List<Circle> circleList(Node circleNodes) {
		List<Circle> list = new ArrayList<>();
		NodeList circleChildNodes = circleNodes.getChildNodes();
		
		for(int i =0;i<circleChildNodes.getLength();i++) {
			
			Node node = circleChildNodes.item(i);
			if(node.getNodeName().equals("object")) {
				list.add(Circle.createCircle(node));
			}
			
	
		}
		return list;
	}
	public static float hardness(String filePath,int level) throws Exception{
		float val= 0.0f;
		File fXmlFile = new File(filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);

		// optional, but recommended
		// read this -
		// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();
		
		System.out.println("Level :"+level);
		NodeList nList = doc.getElementsByTagName("objectgroup");
		Node circleNodes = nList.item(0);
		Node starNode = nList.item(1);
		Node barrierNode = nList.item(2);
		
		List<Circle> circles = circleList(circleNodes);
		System.out.println("Found circle count :"+circles.size());
		for(int i =0;i<circles.size();i++) {
			System.out.println( (i + 1) +". Circle :"+circles.get(i));
		}
		for(int i =0;i<circles.size();i++) {
			Circle circle = circles.get(i);
			switch(circle.getType()) {
			case 0:
				//bitis cemberini siklememek lazim
				if(circle.getName()==null || !circle.getName().equals("end") || circle.getName().equals("start")) {
					val += circle.getMultiplyer() * circle.getAngle() / circle.getWidth();
					
				}
				break;
			case 1:
				
				break;
			case 2:
				break;
			case 3:
				break;
			}
		}
		Circle startCircle = findCircleByName(circles, "start");
		Circle endCircle = findCircleByName(circles,"end");
		float distanceH = distanceHardness(startCircle, endCircle);
		System.out.println(distanceH);
		val +=distanceH;
		
		return val;
	}
	//mesafe uzakligi hesaplama
	static float distanceHardness(Circle start,Circle end) {
		float startX = start.getX()+ start.getWidth()/2;
		float startY = start.getY()+start.getHeight()/2;
		float endX = end.getX() + end.getWidth()/2;
		float endY = end.getY()+end.getHeight()/2;
		float xx = (float)Math.sqrt((startX - endX)* (startX - endX) + (startY - endY) * (startY - endY));
		return xx / 900;// 900 ekranin yaklasik olarak hipotenus degeri
	}
	private static Circle findCircleByName(List<Circle> circles,String name) {
		for(int i =0;i<circles.size();i++) {
			if(circles.get(i).getName()!=null && circles.get(i).getName().equals(name))
				return circles.get(i);
		}
		return null;
	}
}
