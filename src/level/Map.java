package level;

import data.Infos;
import android.graphics.Canvas;
import map.Landscape;

public class Map {
	private Landscape map;

	public Map(Landscape map) {
		this.map = map;
	}

	Block block = new Block(Infos.block);
	Ghost ghost = new Ghost(Infos.ghost);

	public void draw(Canvas c) {

		float w = c.getWidth();
		float h = c.getHeight();

		float mapW = map.maze.length;
		float mapH = map.maze[0].length;

		block.sizeW = w / mapW;
		block.sizeH = h / mapH;
				
		ghost.sizeW = block.sizeW;
		ghost.sizeH = block.sizeH;
		
		ghost.x = 0.5f;
		ghost.y = 0.5f;
		
		for (int y = 0; y < map.maze[0].length; y++) {

			for (int x = 0; x < map.maze.length; x++) {
				boolean oo = map.maze[x][y].type > 0 ? true : false;
				block.x = x / mapW;
				block.y = y / mapH;
				if (oo)
					block.draw(c);
				
				ghost.draw(c);

			}
		}
	}
}
