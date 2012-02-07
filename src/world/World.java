package world;

import impl.MyPoint3D;
import impl.MyRay;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import shapes.MyShape;

public class World extends JPanel{

	/**
	 * dummy serialization to suppress warnings
	 */
	private static final long serialVersionUID = 1L;
	
	// screen resolution parameters
	private int widthPix;
	private int heightPix;
	
	// background color
	private Color bgColor;
	
	// viewer
	private MyPoint3D viewer;
	
	// view plane coordinates
	private double zViewPlane;
	private double leftViewPlane;
	private double rightViewPlane;
	private double bottomViewPlane;
	private double topViewPlane;
	
	// objects in our world
	private LinkedList<MyShape> objects;
	
	// pixel memory map
	int[] memory;
	
	// other
	// TODO: maybe in constructor
	private int near = 1;
	private int far = 50;
	
	public World(MyPoint3D viewer, double zViewPlane, double leftViewPlane, 
			double rightViewPlane, double bottomViewPlane, double topViewPlane, 
			int widthPix, int heightPix, Color bgColor) {
		this.setSize(widthPix, heightPix);
		this.viewer = viewer;
		this.zViewPlane = zViewPlane;
		this.leftViewPlane = leftViewPlane;
		this.rightViewPlane = rightViewPlane;
		this.bottomViewPlane = bottomViewPlane;
		this.topViewPlane = topViewPlane;
		this.widthPix = widthPix;
		this.heightPix = heightPix;
		this.bgColor = bgColor;
		objects = new LinkedList<MyShape>();
		memory = new int[widthPix*heightPix];
	}
	
	public void addShape(MyShape shape) {
		objects.add(shape);
	}
	
	private MyRay shootRay(int x, int y) {
		MyRay out = new MyRay();
		out.setOrigin(viewer);
		
		double xPart, yPart;
		xPart = ((double)x)/(double)widthPix;
		yPart = ((double)y)/(double)heightPix;
		
		xPart = leftViewPlane + xPart * ( rightViewPlane - leftViewPlane ) ;
	    yPart = topViewPlane + yPart * ( bottomViewPlane - topViewPlane ) ;
		
		// get ray direction
		MyPoint3D dir = new MyPoint3D(xPart - viewer.getX(), yPart - viewer.getY(), zViewPlane - viewer.getZ());
		
		// TODO
		/* if ( Transformation != null )
	    {
	        result.Direction =
	        Transformation.Apply( result.Direction );
	    } */
		
		// normalize
		dir = dir.div(dir.dotProduct(dir));
		out.setDirection(dir);
		return out;
	}
	
	private void raytraceImage(int width, int height) {
		// color values
		Color c = Color.black;
		// shoot ray through each pixel
		for(int x=0; x<width; x++) {
			for(int y=0; y<height; y++) {
				MyRay ray = shootRay(x, y);
				double maxDist = Double.POSITIVE_INFINITY;
				
				// check each object for intersections 
				// --> get the one on top
				for(MyShape shape : objects) {
					double hitDist = shape.rayIntersect(ray);
					if(hitDist > 0 && maxDist > hitDist) {
						maxDist = hitDist;
						c = shape.getColor();
					}
				}
				
				// set pixel in buffer
				if(maxDist < Double.POSITIVE_INFINITY) {
					// we got an intersection
					memory[y+x*height] = c.getRGB() - (int)Math.min(Math.max(0,maxDist - near) / ( near - far ),1.0); // c.getRGB();
					int pix = y+x*height;
					System.out.println("At Pos "+pix+" : "+memory[y+x*height]);
				} else {
					// no intersection -> use background color
					memory[y+x*height] = bgColor.getRGB();
					int pix = y+x*height;
					System.out.println("At Pos "+pix+" : "+memory[y+x*height]);
				}
			}
		}
		System.out.println("Finished Raytracing!!!");
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// raytrace
		System.out.println("START RAYTRACING");
		raytraceImage(widthPix, heightPix);
		// display map
		System.out.println("CREATING IMAGE");
		Image img = createImage(new MemoryImageSource(widthPix, heightPix, memory, 0, widthPix));
		//BufferedImage bi = new BufferedImage(widthPix, heightPix, BufferedImage.TYPE_INT_ARGB);
		//g2 = bi.createGraphics();
		g2.drawImage(img, 0, 0, null);
		System.out.println("DRAW IMAGE");
	}
	
	public void display() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(widthPix, heightPix);
		frame.getContentPane().add(this);
		frame.setVisible(true);
	}
}