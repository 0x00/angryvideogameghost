package map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Navi {

	Landscape map;
	Point avoid;
	public double avoider = 190;

	public Navi(Landscape map) {
		this.map = map;
	}

	public void dumpPath() {
		for (int y = 0; y < map.maze[0].length; y++) {

			for (int x = 0; x < map.maze.length; x++) {

				boolean painted = false;
				for (Point p : path) {
					if (p.x == x && p.y == y && !painted) {
						System.out.print("@");
						painted = true;
					}
				}

				if (!painted) {
					if (avoid != null && x == avoid.x && y == avoid.y) {
						System.out.print("X");
					} else {
						String item = map.maze[x][y].type == 0 ? "_" : "Q";
						System.out.print(item);
					}
				}
			}
			System.out.println();
		}
	}

	List<Point> visited;
	List<Point> todo;
	public List<Point> path;

	double distance(Point a, Point b) {
		return Math.sqrt(Math.pow(a.x - b.x, 2) - Math.pow(a.y - b.y, 2));
	}

	void add(Point add, Point actual) {

		if (todo.contains(add) || visited.contains(add))
			return;

		if (avoid != null && avoid.x == add.x && avoid.y == add.y)
			return;

		if (add.x >= 0 && add.x < map.maze.length && add.y >= 0
				&& add.y < map.maze[0].length) {

			if (map.maze[add.x][add.y].type == 0) {
				todo.add(add);

				Point p = new Point(add.x, add.y);

				if (actual.history.size() == 0) {
					actual.history.add(actual);
				}

				add.history = new ArrayList<Point>(actual.history);
				add.history.add(p);
				add.allcosts = actual.allcosts + 1;
			}
		}

		Collections.sort(todo, distanceCompo);
	}

	Comparator<Point> distanceCompo = new Comparator<Point>() {
		@Override
		public int compare(Point o1, Point o2) {

			double d1 = o1.distance(target);
			double d2 = o2.distance(target);

			if (avoid != null) {
				d1 += o1.allcosts + avoider / (o1.distance(avoid) + 1);
				d2 += o2.allcosts + avoider / (o2.distance(avoid) + 1);
			}

			return (int) ((d1 - d2) * 10000);
		}
	};

	boolean found = false;
	private Point target;

	void expand() {
		Point here = todo.get(0);

		todo.remove(here);
		visited.add(here);

		if (here.equals(target)) {
			path = here.history;
			found = true;
			return;
		}

		add(new Point(here.x - 1, here.y), here);
		add(new Point(here.x + 1, here.y), here);
		add(new Point(here.x, here.y - 1), here);
		add(new Point(here.x, here.y + 1), here);

	}

	public List<Point> findPath(Point from, Point to, Point avoid) {

		if (map.maze[to.x][to.y].type != 0)
			new LinkedList<Point>();

		if (path != null && path.contains(from) && path.contains(to)) {
			int idx = path.indexOf(from);
			if (path.get(path.size() - 1) == to) {
				return path.subList(idx, path.size());
			}
		}

		this.target = to;
		this.avoid = avoid;

		this.found = false;

		this.visited = new ArrayList<Point>();
		this.todo = new ArrayList<Point>();
		this.path = new LinkedList<Point>();

		this.todo.add(from);

		while (todo.size() > 0 && !found) {
			expand();
		}
		return path;
	}
}
