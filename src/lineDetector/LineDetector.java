package lineDetector;
import java.util.Arrays;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdOut;

//Programmed by Glen Keane. 20057974.
//THIS LINE DETECTOR RECOGNISES DUPLICATE LINES AND DOESN'T DRAW THEM.
public class LineDetector {

	private Point[] data;//All points are stored in this local variable.
	
	//method for point initialisation
	private LineDetector(String str) {
		In fileIn = new In(str);
		int k = fileIn.readInt();
		data = new Point[k];
		int i = 0;
		while (!fileIn.isEmpty()) {
			Point temp = new Point(fileIn.readInt(), fileIn.readInt());
			data[i++] = temp;
		}
		//set draw scale.
		StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        //draw axes for reference.
		StdDraw.line(0, 0, 0, 32768); 
		StdDraw.line(0, 0, 32768, 0);
	}

	// compute all lines.
	private void computeLines() {
		String print = null;//initialise the very last printout from the console
		Line[] lines = new Line[data.length - 3];//initialise an array of lines, for comparison and drawing.
		//compute all lines for passed in origin point.
		for (int i = 0; i < data.length - 3; i++) {
			lines[i] = getLinesForPoint(i);
		}
		//go through all lines and if they aren't a repeat or null, draw it.
		for (int x = 0; x < lines.length; x++) {
			Line l = lines[x];
			if (l != null) {
				for (int y = 0; y<  l.lastIndex.length; y++) {
					String str = l.linesPoints[y];
					if(print != null){
						if (!print.contains(str)) { //This checks to see if the very final printout contains 
							//the string of the current line. This stops duplicate printouts.
							print += "\n" + str;
							data[l.firstIndex].drawTo(data[l.lastIndex[y]]);
						}
					} else{
						print = str;
						data[l.firstIndex].drawTo(data[l.lastIndex[y]]);
					}
				}
			}
		}
		if(print != null)StdOut.println(print);
		else StdOut.println("No lines detected");
	}

	private Line getLinesForPoint(int i) {
		Line oldLine = null;
		LinePoint[] temp = new LinePoint[data.length - 1 - i];
		// array to save the points based on their slope and index in data array
		for (int j = i + 1; j < data.length; j++) {
			temp[j - i - 1] = new LinePoint(data[i].slopeTo(data[j]), j);
		}
		// sort temp array based on the slopes
		Arrays.sort(temp);
		int c = 1;
		boolean line = false;
		int numPs = 0, numLns = 0;
		
		//COMPARES SLOPES OF J AND J-1 IN TEMP ARRAY, UP TO N-ORIGIN POS.
		//IF 3 OR MORE POINTS ARE DETECTED ON A LINE AND THEN POINT
		//J SLOPE IS NOT THE SAME AS J-1 SLOPE,
		//IT ADDS THE LINE FROM ORIGIN TO J-1. IF J IS THE LENGTH OF THE TEMP ARRAY
		//AND THERE IS A LINE DETECTED, IT ADDS THE LINE FROM ORIGIN TO J
		for (int j = 1; j < temp.length; j++) {
			if (temp[j].compareTo(temp[j - 1]) == 0) {
				c++;
				numPs++;
			} else {
				c = 1;
			}
			if (c == 3) {
				line = true;
			}
			if (line == true && c < 3) {
				String lineStr = data[i].toString();
				for (int k = numPs; k >= 0; k--) {
					lineStr += " -> " + data[temp[j - k - 1].index].toString();
				}
				numLns++;
				Line newLine = new Line(i, numLns, numLns);
				if (oldLine == null) {
					newLine.lastIndex[0] = temp[j - 1].index;
					newLine.linesPoints[0] = lineStr;
				} else {
					for (int x = 0; x < newLine.lastIndex.length; x++) {
						if (x != newLine.lastIndex.length - 1) {
							newLine.lastIndex[x] = oldLine.lastIndex[x];
						} else {
							newLine.lastIndex[x] = temp[j - 1].index;
						}
					}
					for (int x = 0; x < newLine.linesPoints.length; x++) {
						if (x != newLine.linesPoints.length - 1) {
							newLine.linesPoints[x] = oldLine.linesPoints[x];
						} else {
							newLine.linesPoints[x] = lineStr;
						}
					}
				}
				oldLine = newLine;
				line = false;
			}
			if (line == true && j == temp.length-1) {
				String lineStr = data[i].toString();
				for (int k = numPs; k >= 0; k--) {
					lineStr += " -> " + data[temp[j - k].index].toString();
				}
				numLns++;
				Line newLine = new Line(i, numLns, numLns);
				if (oldLine == null) {
					newLine.lastIndex[0] = temp[j].index;
					newLine.linesPoints[0] = lineStr;
				} else {
					for (int x = 0; x < newLine.lastIndex.length; x++) {
						if (x != newLine.lastIndex.length - 1) {
							newLine.lastIndex[x] = oldLine.lastIndex[x];
						} else {
							newLine.lastIndex[x] = temp[j].index;
						}
					}
					for (int x = 0; x < newLine.linesPoints.length; x++) {
						if (x != newLine.linesPoints.length - 1) {
							newLine.linesPoints[x] = oldLine.linesPoints[x];
						} else {
							newLine.linesPoints[x] = lineStr;
						}
					}
				}
				oldLine = newLine;
				line = false;
			}
			if (temp[j].compareTo(temp[j - 1]) != 0) {
				numPs = 0;
			}
		}
		return oldLine;
	}

	// class for creating objects which save a points slope relative to a first
	// point and its index in the main array
	public class LinePoint implements Comparable<LinePoint> {
		Double slope;
		int index;

		public LinePoint(Double slope, int index) {
			this.slope = slope;
			this.index = index;
		}
		// sorts based on slopes. uses Double classes compare method.
		public int compareTo(LinePoint ln) {
			if (this.slope.equals(ln.slope)) return 0;
			return Double.compare(this.slope, ln.slope);
		}
	}

	//class for making all lines relative to its origin point. since an origin point 
	//can be an origin for multiple lines, it saves all of those lines for that origin point.
	public class Line {
		int firstIndex;
		int[] lastIndex;
		String[] linesPoints;

		public Line(int i, int j, int k) {
			this.firstIndex = i;
			this.lastIndex = new int[j];
			this.linesPoints = new String[k];
		}
	}

	public static void main(String[] args) {
		StdOut.println("Reading in points from file...");
		// initialise points
		String fileName;
		try{
			fileName = args[0];
		}catch(Exception e){
			fileName = "TestPoints.txt"; //change this to change the file being read in.
		}
		LineDetector lD = new LineDetector(fileName);;
		
		for (Point d : lD.data) {
			d.draw();
		}
		StdOut.println("Points drawn. Now computing lines for them.");
		lD.computeLines();
		StdOut.println("\ndone!");
	}
}