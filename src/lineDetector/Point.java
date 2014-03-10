package lineDetector;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;

//Programmed by Glen Keane. 20057974.
public class Point implements Comparable<Point> {

	private final int x; // x coordinate
	private final int y; // y coordinate

	// create the point (x, y)
	public Point(int x, int y) {
		/* DO NOT MODIFY */
		this.x = x;
		this.y = y;
	}

	// plot this point to standard drawing
	public void draw() {
		/* DO NOT MODIFY */
		StdDraw.point(x, y);
	}

	// draw line between this point and that point to standard drawing
	public void drawTo(Point that) {
		/* DO NOT MODIFY */
		StdDraw.line(this.x, this.y, that.x, that.y);
	}

	// slope between this point and that point
	public Double slopeTo(Point that) {
		if(this.y == that.y){
			return 0.0; // 0 over anything = 0
		}
		if(this.x == that.x) return Double.POSITIVE_INFINITY; //anything over 0, pos infinity.
		return (((double)(that.y - this.y))/ ((double)(that.x - this.x)));
	}

	// is this point lexicographically smaller than that one?
	// comparing y-coordinates and breaking ties by x-coordinates
	public int compareTo(Point that) {
		 if (this.y == that.y) {
             return this.x - that.x;
     }
     return this.y - that.y;
	}

	// return string representation of this point
	public String toString() {
		/* DO NOT MODIFY */
		return "(" + x + ", " + y + ")";
	}

	// unit test
	public static void main(String[] args) {
		StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        Point p1 = new Point(10000, 10000);
        Point p2 = new Point(10000, 20000);
        p1.drawTo(p2);
        StdOut.print("slope for line drawn(should be infinity): "+ p1.slopeTo(p2));
        StdDraw.show(0);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}