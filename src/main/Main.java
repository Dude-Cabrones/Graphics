package main;

import impl.MyPoint3D;

import java.awt.Color;

import shapes.MySphere;
import world.World;

public class Main {

	public static void main(String[] args) {
		
		// set up viewer and view plane
		MyPoint3D viewer = new MyPoint3D(0, 0, -10);
		World world = new World(viewer, 1, -200, 200, -200, 200, 300, 300, Color.WHITE);
	
		// set up quadratic test shape
		//MyPoint3D ta = new MyPoint3D(12, 384, 600);
		//MyPoint3D tb = new MyPoint3D(530, -384, 600);
		//MyPoint3D tc = new MyPoint3D(188, -100, 400);
		//MyPoint3D td = new MyPoint3D(-512, 384, 400);
		//world.addShape(new MyQuadSurface(ta,tb,tc,td,100,100,100));
	
		// add test sphere
		MyPoint3D center = new MyPoint3D(100, 20, 40);
		MySphere sphereA = new MySphere(center, 30, Color.red);
		world.addShape(sphereA);
		
		// add test sphere
		world.addShape(new MySphere(new MyPoint3D(-45, -45, 25), 30, Color.blue));
		
		// render test shapes
		world.display();
	}
}
