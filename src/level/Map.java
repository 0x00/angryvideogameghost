package level;

import data.Infos;
import android.graphics.Canvas;
import map.Generator;

public class Map {
	private Generator map;

	public Map(Generator map) {
		this.map = map;
	}

	Block block = new Block(Infos.block);

	public void draw(Canvas c) {

		float w = c.getWidth();
		float h = c.getHeight();

		float mapW = map.maze.length;
		float mapH = map.maze[0].length;

		block.sizeW = w / mapW;
		block.sizeH = h / mapH;
		
		for (int y = 0; y < map.maze[0].length; y++) {

			for (int x = 0; x < map.maze.length; x++) {
				boolean oo = map.maze[x][y].type > 0 ? true : false;
				block.x = x / mapW;
				block.y = y / mapH;
				if (oo)
					block.draw(c);

			}
		}
	}
}
