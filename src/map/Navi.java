package map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Navi {

	Landscape map;

	public Navi(Landscape map) {
		this.map = map;
	}

	class Point {
		public int x;
		public int y;
		public String path = "";

		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return x + "," + y + ":" + path;
		}

		@Override
		public boolean equals(Object obj) {
			Point o = (Point) obj;
			return x == o.x && y == o.y;
		}
	}

	List<Point> visited = new ArrayList<Point>();
	List<Point> todo = new ArrayList<Point>();
	String path = "";

	void add(Point add, String actualPath, String dir, final Point target) {

		if (todo.contains(add) || visited.contains(add))
			return;

		if (add.x >= 0 && add.x < map.maze.length && add.y >= 0
				&& add.y < map.maze[0].length) {

			if (map.maze[add.x][add.y].type == 0) {
				todo.add(add);
				add.path = actualPath + dir;
			}
		}

		Comparator<Point> distanceCompo = new Comparator<Navi.Point>() {
			@Override
			public int compare(Point o1, Point o2) {
				double d1 = Math.sqrt(Math.pow(o1.x - target.x, 2)
						- Math.pow(o1.y - target.y, 2));
				
				double d2 = Math.sqrt(Math.pow(o2.x - target.x, 2)
						- Math.pow(o2.y - target.y, 2));
				return (int) ((d1-d2) * 1000);
			}
		};
		Collections.sort(todo, distanceCompo);
	}

	void expand(Point target) {
		Point here = todo.get(0);

		todo.remove(here);
		visited.add(here);

		if (here.equals(target)) {
			path = here.path;
			return;
		}

		add(new Point(here.x - 1, here.y), here.path, "w", target);
		add(new Point(here.x + 1, here.y), here.path, "e", target);
		add(new Point(here.x, here.y - 1), here.path, "n", target);
		add(new Point(here.x, here.y + 1), here.path, "s", target);

		if (todo.size() > 0) {
			expand(target);
		}

	}

	public String findPath(int x, int y, int tx, int ty) {
		todo.add(new Point(x, y));
		expand(new Point(tx, ty));
		return path;
	}
}
