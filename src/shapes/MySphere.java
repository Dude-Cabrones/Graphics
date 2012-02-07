package shapes;

import impl.Calc;
import impl.CalcTuple;
import impl.MyPoint3D;
import impl.MyRay;

import java.awt.Color;

public class MySphere implements MyShape{

	private MyPoint3D center;	// sphere center
	private double radius;	// radius
	private Color color;	// color
	
	public MySphere(MyPoint3D center, double radius, Color color) {
		super();
		this.center = center;
		this.radius = radius;
		this.color = color;
	}
	
	public double rayIntersect(MyRay ray) {
		// TODO
		double boundingSquare = radius*radius;
		MyPoint3D origin = ray.getOrigin().sub(center);
		double a, b, c;
		a = ray.getDirection().dotProduct(ray.getDirection());
		b = 2*(origin.dotProduct(ray.getDirection()));
		c = origin.dotProduct(origin)-boundingSquare;
		
		// solve quad. equation
		CalcTuple tup = Calc.CalcQuadRoot(a, b, c);
		double roots = tup.getRoot();
		
		if(roots > 0) {
			return tup.getIntersectionA() >= 0 
				? tup.getIntersectionA() : tup.getIntersectionB();
		} else {
			return -1;
		}
	}
	
	public MyPoint3D getCenter() {
		return center;
	}

	public double getRadius() {
		return radius;
	}

	public Color getColor() {
		return color;
	}
}
