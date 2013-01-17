package map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Navi {

	Landscape map;

	public Navi(Landscape map) {
		this.map = map;
	}
	
	public void dumpPath(){
		for (int y = 0; y < map.maze[0].length; y++) {

			for (int x = 0; x < map.maze.length; x++) {
				
				boolean painted = false;
				for(Point p : path2){
					if(p.x==x && p.y==y && !painted){
						System.out.print("@");
						painted = true;
					}
				}
				
				if(!painted){
					String item = map.maze[x][y].type == 0 ? "_" : "Q";
					System.out.print(item);
				}
			}
			System.out.println();
		}
	}

	public class Point {
		public int x;
		public int y;
		List<Point> path2 = new LinkedList<Point>();

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
	}

	List<Point> visited = new ArrayList<Point>();
	List<Point> todo = new ArrayList<Point>();
	List<Point> path2 = new LinkedList<Point>();

	void add(Point add, Point actual, String dir, final Point target) {

		if (todo.contains(add) || visited.contains(add))
			return;

		if (add.x >= 0 && add.x < map.maze.length && add.y >= 0
				&& add.y < map.maze[0].length) {

			if (map.maze[add.x][add.y].type == 0) {
				todo.add(add);
				
				Point p = new Point(add.x, add.y);
				add.path2 = new ArrayList<Navi.Point>(actual.path2);
				add.path2.add(p);
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
		while(todo.size()>0 && !found){
			expand(new Point(tx, ty));
		}
		return path2;
	}
}
