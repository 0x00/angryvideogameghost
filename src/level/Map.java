package level;

import java.util.LinkedList;
import java.util.List;

import map.Landscape;
import map.ValidMapGenerator;
import android.graphics.Canvas;
import data.Infos;

public class Map {
	public Landscape map;

	public int transferX(float x) {
		int X = map.maze.length;
		return (int) (x * X);
	}

	public int transferY(float y) {
		int Y = map.maze[0].length;
		return (int) (y * Y);
	}

	public Map() {
		map = new ValidMapGenerator(14, 20).map;
		ghost = new Ghost(Infos.ghostBitmap, this.map);
		ghost.x = 7;
		ghost.y = 10;

		for (int y = 0; y < map.maze[0].length; y++) {
			for (int x = 0; x < map.maze.length; x++) {
				if(map.maze[x][y].type==0){
					Food f = new Food(x,y);
					food.add(f);
					f.border = 15;
				}
			}
		}
	}

	Block block = new Block(Infos.blockBitmap);

	/* this is the Player */
	public Ghost ghost;
	public List<Food> food = new LinkedList<Food>();

	public void draw(Canvas c) {

		float w = c.getWidth();
		float h = c.getHeight();

		float mapW = map.maze.length;
		float mapH = map.maze[0].length;

		block.sizeW = w / mapW;
		block.sizeH = h / mapH;

		ghost.sizeW = block.sizeW;
		ghost.sizeH = block.sizeH;

		for (int y = 0; y < map.maze[0].length; y++) {

			for (int x = 0; x < map.maze.length; x++) {
				boolean oo = map.maze[x][y].type > 0 ? true : false;
				block.x = x / mapW;
				block.y = y / mapH;
				if (oo)
					block.draw(c);
			}
		}

		for (Food f : food) {
			f.sizeW = block.sizeW;
			f.sizeH = block.sizeH;
			f.draw(c);
		}

		ghost.draw(c);
	}
}
