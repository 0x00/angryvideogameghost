package map;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Landscape {

	public int central[][] = { { 1, 1, 1, 1, 1 }, { 1, 0, 0, 0, 1 },
			{ 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1 } };

	public Block random() {
		Collections.shuffle(blocks);
		for (Block b : blocks) {
			if (b.type == 0) {
				return b;
			}
		}
		return null;
	}

	public Block maze[][];
	public List<Block> blocks = new LinkedList<Block>();
	private double threshold = 0.8;

	public Landscape(int w, int h) {
		generate(w, h);
		dump();
	}

	public Landscape(int w, int h, double threshold) {
		this.threshold = threshold;
		generate(w, h);
		dump();
	}

	private void generate(int w, int h) {
		maze = new Block[w][h];

		int cX = 0;
		int cY = 0;
		int cW = w / 2 - central.length / 2;
		int cH = h / 2 - central[0].length / 2;

		for (int y = 0; y < maze[0].length; y++) {
			for (int x = 0; x < maze.length; x++) {

				int v = Math.random() > threshold ? 1 : 0;
				
				if (x >= cW && y >= cH) {
					if (cX < central.length && cY < central[0].length)
						v = central[cX][cY];
					cX++;
				}

				Block block = new Block(v, x, y);
				maze[x][y] = block;
				blocks.add(block);
			}
			if (y >= cH) {
				cY++;
				cX=0;
			}
			System.out.println(cX+" "+cY);
		}
	}

	public void dump() {
		for (int y = 0; y < maze[0].length; y++) {

			for (int x = 0; x < maze.length; x++) {
				String item = maze[x][y].type == 0 ? "_" : "Q";
				System.out.print(item);
			}
			System.out.println();
		}
	}

}
