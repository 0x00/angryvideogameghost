package map;

public class Generator {

	public Block maze[][];

	public Generator(int w, int h) {
		maze = new Block[w][h];
		for (int y = 0; y < maze[0].length; y++)
			for (int x = 0; x < maze.length; x++) {
				int v = Math.random() > 0.8 ? 1 : 0;
				maze[x][y] = new Block(v);
			}
	}

	public void dump() {
		for (int y = 0; y < maze[0].length; y++) {

			for (int x = 0; x < maze.length; x++) {
				System.out.print(maze[x][y]);
			}
			System.out.println();
		}
	}
}
