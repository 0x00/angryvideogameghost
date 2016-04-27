package city.felix.angryvideogameghost.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidMapGenerator {

	public Landscape map;
	private Navi navi;

	boolean check2(int startX, int startY) {

		if (map.maze[startX][startY].type != 0)
			return false;

		List<Point> path = new Navi(map).findPath(new Point(startX, startY),
				new Point(0, 0), null);
		if (path.size() <= 1) {
			return false;
		}

		path = new Navi(map).findPath(new Point(0, 0), new Point(
				map.maze.length - 1, map.maze[0].length - 1), null);
		if (path.size() <= 1) {
			return false;
		}

		return true;
	}

	boolean check(int startX, int startY) {
		boolean valid = true;

		if (map.maze[startX][startY].type != 0)
			return false;

		for (int y = 0; y < map.maze[0].length; y++) {
			for (int x = 0; x < map.maze.length; x++) {

				if (startX == x && startY == y)
					continue;

				if (valid) {
					Block m = map.maze[x][y];
					if (m.type == 0) {
						List<Point> path = navi.findPath(new Point(startX,
								startY), new Point(x, y), null);
						if (path.size() <= 1) {
							valid = false;
							break;
						}
					}
				}
			}
			System.out.print(".");
		}
		System.out.println("");

		return valid;
	}

	@SuppressWarnings("unused")
	public ValidMapGenerator(int w, int h) {

		long round = 0;
		boolean valid = false;

		System.out.println("Generator init");
		while (!valid) {
			map = new Landscape(w, h, 0.8);
			navi = new Navi(map);
			round++;
			System.out.println("next round #" + round);
			map.dump();

			System.out.println("generated. testing for plausibility..");
			valid = check(w / 2, h / 2);

			if (!valid) {
				System.out.println("not plausible next try");
				System.out.println();

				while (!valid && false) {
					System.out.println("removing blocks...");
					remove(10);
					map.dump();
					valid = check(w / 2, h / 2);
				}
			}
			if (valid) {
				System.out.println("world is ok");
			}
		}
	}

	private void remove(int amount) {
		List<Block> blocked = new ArrayList<Block>();
		for (int y = 0; y < map.maze[0].length; y++)
			for (int x = 0; x < map.maze.length; x++) {
				Block b = map.maze[x][y];
				if (b.type != 0) {
					blocked.add(b);
				}
			}
		for (int i = 0; i < amount; i++) {
			if (blocked.size() == 0)
				return;
			Collections.shuffle(blocked);
			Block b = blocked.get(0);
			map.maze[b.x][b.y].type = 0;
			blocked.remove(b);
		}
	}
}
