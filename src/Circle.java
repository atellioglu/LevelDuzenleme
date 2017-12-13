import java.util.HashMap;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Circle {
	private float angle = 5;
	private int width, height;
	private String name;
	private int type = 0;
	private int x,y;
	private HashMap<String, Object> properties;
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public HashMap<String, Object> getProperties() {
		return properties;
	}

	public float getAngle() {
		return angle;
	}

	public int getWidth() {
		return width;
	}
	public float getMultiplyer() {
		switch(type) {
		case 0:
			return 1;
		case 1:
			return 5;
		case 2:
			return 10;
		case 3:
			return 30;
		}
		return 1;
	}

	public int getHeight() {
		return height;
	}

	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}

	public static Circle createCircle(Node node) {
		if (!node.getNodeName().equals("object")) {
			return null;
		}
		Circle circle = new Circle();
		circle.properties = new HashMap<>();
		
		if(node.getAttributes().getNamedItem("name")!=null) {
			circle.name = node.getAttributes().getNamedItem("name").getNodeValue();
		}
		circle.width = Integer.parseInt(node.getAttributes().getNamedItem("width").getNodeValue());
		circle.height = Integer.parseInt(node.getAttributes().getNamedItem("height").getNodeValue());
		circle.x =(int) Float.parseFloat(node.getAttributes().getNamedItem("x").getNodeValue());
		circle.y =(int) Float.parseFloat(node.getAttributes().getNamedItem("y").getNodeValue());
		NodeList nodeList = node.getChildNodes();
		for(int i =0;i<nodeList.getLength();i++) {
			if(nodeList.item(i).getNodeName().equals("properties")) {
				Node properties = nodeList.item(i);
				for(int j =0;j<properties.getChildNodes().getLength();j++) {
					Node propertyNode = properties.getChildNodes().item(j);
					
					if(propertyNode.getNodeName().equals("property")) {
						Node prop = propertyNode.getAttributes().getNamedItem("name");
						if(prop.getNodeValue().equals("angle")) {
							Node angleValue = propertyNode.getAttributes().getNamedItem("value");
							circle.angle =  Float.parseFloat(angleValue.getNodeValue());
						}else if (prop.getNodeValue().equals("type")) {
							Node typeValue = propertyNode.getAttributes().getNamedItem("value");
							circle.type = Integer.parseInt(typeValue.getNodeValue());
							if(circle.type == 1) {
								circle.properties.put("timeOut", 5);
							}
						}else if(prop.getNodeValue().equals("timeOut")) {
							circle.properties.put("timeOut",Float.parseFloat(propertyNode.getAttributes().getNamedItem("value").getNodeValue()));
						}else if(prop.getNodeValue().equals("fadeTime")) {
							circle.properties.put("fadeTime",Float.parseFloat(propertyNode.getAttributes().getNamedItem("value").getNodeValue()));
						}else if(prop.getNodeValue().equals("invisibleTime")) {
							circle.properties.put("invisibleTime",Float.parseFloat(propertyNode.getAttributes().getNamedItem("value").getNodeValue()));
						}else if(prop.getNodeValue().equals("visibleTime")) {
							circle.properties.put("visibleTime",Float.parseFloat(propertyNode.getAttributes().getNamedItem("value").getNodeValue()));
						}
						
					}	
				}
				
				
			}
		}
		
		
		return circle;
	}

	@Override
	public String toString() {
		return "Circle [angle=" + angle + ", width=" + width + ", height=" + height + ", name=" + name + ", type="
				+ type + ", x=" + x + ", y=" + y + ", properties=" + properties + "]";
	}

	
	
}
