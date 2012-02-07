package shapes;

import impl.MyRay;

import java.awt.Color;


public interface MyShape {

	public double rayIntersect(MyRay ray);
	public Color getColor();
	
}
