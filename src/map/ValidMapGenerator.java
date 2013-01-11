package map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidMapGenerator {

	Landscape map;

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
						String path = new Navi(map).findPath(startX, startY, x,
								y);
						if (path.length() == 0) {
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

	public Landscape landscape;

	public ValidMapGenerator(int w, int h) {

		long round = 0;
		boolean valid = false;

		System.out.println("Generator init");
		while (!valid) {
			map = new Landscape(w, h, 0.8);
			round++;
			System.out.println("next round #" + round);
			map.dump();

			System.out.println("generated. testing for plausibility..");
			valid = check(w / 2, h / 2);

			if (!valid) {
				System.out.println("not plausible next try");
				System.out.println();

				remove(10);

			}
			if (valid) {
				System.out.println("world is ok");
				landscape = map;
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
