package map;

public class Landscape {

	public Block maze[][];
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
		for (int y = 0; y < maze[0].length; y++)
			for (int x = 0; x < maze.length; x++) {
				int v = Math.random() > threshold ? 1 : 0;
				maze[x][y] = new Block(v,x,y);
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
