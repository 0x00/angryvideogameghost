package level;

import data.Infos;
import android.graphics.Canvas;
import android.util.Log;
import map.Landscape;

public class Map {
	public Landscape map;
	
	public int transferX(float x){
		int X = map.maze.length;
		return (int) (x*X+0.5);
	}

	public int transferY(float y){
		int Y = map.maze[0].length;
		return (int) (y*Y+0.5);
	}
	
	public void setPlayerTargetX(float x){
		Log.d("x", x+" "+transferX(x));
		Infos.player.toX = transferX(x)/(float)map.maze.length;
	}
	public void setPlayerTargetY(float y){
		Log.d("y", y+" "+transferY(y));
		Infos.player.toY = transferY(y)/(float)map.maze[0].length;
	}

	public Map(Landscape map) {
		this.map = map;
		Infos.player = ghost;
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
