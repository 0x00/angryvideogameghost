package map;

import java.util.LinkedList;
import java.util.List;


public class Point {
	public int x;
	public int y;
	public int allcosts;
	List<Point> history = new LinkedList<Point>();

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return x + "," + y;
	}

	@Override
	public boolean equals(Object obj) {
		Point o = (Point) obj;
		return x == o.x && y == o.y;
	}

	public double distance(Point other) {
		return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
	}
}
