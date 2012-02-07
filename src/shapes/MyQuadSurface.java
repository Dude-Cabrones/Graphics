package shapes;

import impl.MyPoint3D;
import impl.MyRay;

import java.awt.Color;

public class MyQuadSurface implements MyShape{

	// Point A is connected with point B is connected with point C... A-B-C-D-A
	private MyPoint3D a;
	private MyPoint3D b;
	private MyPoint3D c;
	private MyPoint3D d;
	
	// color
	private Color color;
	
	public MyQuadSurface(MyPoint3D a, MyPoint3D b, 
			MyPoint3D c, MyPoint3D d, Color color) {
		super();
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.color = color;
	}
	
	public double rayIntersect(MyRay ray) {
		// TODO
		return 0;
	}

	public MyPoint3D getA() {
		return a;
	}

	public MyPoint3D getB() {
		return b;
	}

	public MyPoint3D getC() {
		return c;
	}

	public MyPoint3D getD() {
		return d;
	}

	public Color getColor() {
		return color;
	}
}
