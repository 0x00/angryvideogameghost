package map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Navi {

	Landscape map;
	int avoidX = -1;
	int avoidY = -1;
	public double avoider = 190;

	public Navi(Landscape map) {
		this.map = map;
	}

	public Navi(Landscape map, int avoidX, int avoidY) {
		this.map = map;
		this.avoidX = avoidX;
		this.avoidY = avoidY;
	}

	public void dumpPath() {
		for (int y = 0; y < map.maze[0].length; y++) {

			for (int x = 0; x < map.maze.length; x++) {

				boolean painted = false;
				for (Point p : path2) {
					if (p.x == x && p.y == y && !painted) {
						System.out.print("@");
						painted = true;
					}
				}

				if (!painted) {
					if (x == avoidX && y == avoidY) {
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


	List<Point> visited = new ArrayList<Point>();
	List<Point> todo = new ArrayList<Point>();
	List<Point> path2 = new LinkedList<Point>();

	double distance(Point a, Point b) {
		return Math.sqrt(Math.pow(a.x - b.x, 2) - Math.pow(a.y - b.y, 2));
	}

	void add(Point add, Point actual, String dir, final Point target) {

		if (todo.contains(add) || visited.contains(add))
			return;

		if (avoidX == add.x && avoidY == add.y)
			return;

		if (add.x >= 0 && add.x < map.maze.length && add.y >= 0
				&& add.y < map.maze[0].length) {

			if (map.maze[add.x][add.y].type == 0) {
				todo.add(add);

				Point p = new Point(add.x, add.y);
				add.path2 = new ArrayList<Point>(actual.path2);
				add.path2.add(p);
				add.allcosts = actual.allcosts + 1;
			}
		}

		Comparator<Point> distanceCompo = new Comparator<Point>() {
			@Override
			public int compare(Point o1, Point o2) {

				double d1 = o1.distance(target);
				double d2 = o2.distance(target);

				if (avoidX != -1 && avoidY != -1) {
					d1 += o1.allcosts + avoider
							/ (o1.distance(new Point(avoidX, avoidY)) + 1);
					d2 += o2.allcosts + avoider
							/ (o2.distance(new Point(avoidX, avoidY)) + 1);
				}

				return (int) ((d1 - d2) * 10000);
			}
		};
		Collections.sort(todo, distanceCompo);
	}

	boolean found = false;

	void expand(Point target) {
		Point here = todo.get(0);

		todo.remove(here);
		visited.add(here);

		if (here.equals(target)) {
			path2 = here.path2;
			found = true;
			return;
		}

		add(new Point(here.x - 1, here.y), here, "w", target);
		add(new Point(here.x + 1, here.y), here, "e", target);
		add(new Point(here.x, here.y - 1), here, "n", target);
		add(new Point(here.x, here.y + 1), here, "s", target);

	}

	public List<Point> findPath(int x, int y, int tx, int ty) {
		todo.add(new Point(x, y));
		while (todo.size() > 0 && !found) {
			expand(new Point(tx, ty));
		}
		return path2;
	}
}
