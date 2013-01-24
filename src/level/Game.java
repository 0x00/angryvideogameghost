package level;

import game.pachunter.FullscreenActivity;

import java.util.LinkedList;
import java.util.List;

import map.Landscape;
import map.ValidMapGenerator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import data.Infos;

public class Game {
	public Landscape map;

	public enum States {
		TITLE, GAME, PACMANKILLED, GAMEOVER, GHOSTKILLED
	};

	public States actual = States.TITLE;
	boolean busy;

	public int transferX(float x) {
		int X = map.maze.length;
		return (int) (x * X);
	}

	public int transferY(float y) {
		int Y = map.maze[0].length;
		return (int) (y * Y);
	}

	public void initGame() {

		food.clear();

		busy = false;
		actual = States.TITLE;
		map = new ValidMapGenerator(14, 20).map;
		ghost = new Ghost(Infos.ghostBitmap, this.map);
		ghost.x = 7;
		ghost.y = 10;

		pacman = new Pacman(Infos.ghostBitmap, this.map, this.food);
		pacman.x = 1;
		pacman.y = 1;

		pacman.avoid = ghost;
		ghost.pacman = pacman;

		for (int y = 0; y < map.maze[0].length; y++) {
			for (int x = 0; x < map.maze.length; x++) {
				if (map.maze[x][y].type == 0) {
					Food f = new Food(x, y);
					food.add(f);
					f.border = 15;
				}
			}
		}
	}

	public Game() {
		try {
			initGame();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	Block blockW = new Block(Infos.blockWhiteBitmap);
	Block block = new Block(Infos.blockBitmap);

	/* this is the Player */
	public Ghost ghost;
	public List<Food> food = new LinkedList<Food>();
	public Pacman pacman;

	public float frame = 0;

	Paint p = new Paint();

	long pills = 0;
	public FullscreenActivity activity;

	public void draw(Canvas c) {

		float w = c.getWidth();
		float h = c.getHeight();

		float mapW = map.maze.length;
		float mapH = map.maze[0].length;

		block.sizeW = w / mapW;
		block.sizeH = h / mapH;
		blockW.sizeW = w / mapW;
		blockW.sizeH = h / mapH;

		ghost.sizeW = block.sizeW;
		ghost.sizeH = block.sizeH;
		pacman.sizeW = block.sizeW;
		pacman.sizeH = block.sizeH;

		int color = Color.WHITE;

		for (int y = 0; y < map.maze[0].length; y++) {

			for (int x = 0; x < map.maze.length; x++) {
				boolean oo = map.maze[x][y].type > 0 ? true : false;

				if (pacman.powerUp && ((int) frame) % 2 == 0) {
					blockW.x = x / mapW;
					blockW.y = y / mapH;
					if (oo)
						blockW.draw(c, color);
					continue;
				}

				block.x = x / mapW;
				block.y = y / mapH;
				if (oo)
					block.draw(c, color);
			}
		}

		pills = 0;
		for (Food f : food) {
			f.sizeW = block.sizeW;
			f.sizeH = block.sizeH;
			f.draw(c);
			if (f.active)
				pills++;
		}

		if (actual == States.GHOSTKILLED) {
			return;
		}

		if (actual == States.PACMANKILLED) {
			return;
		}

		if (actual == States.GAMEOVER) {
			return;
		}

		pacman.draw(c);
		ghost.draw(c);

		if (actual != States.GAME)
			return;
		p.setColor(Color.rgb(255, 255, 255));
		p.setTextSize(20);
		c.drawText("Pills: " + pills, 10, 30, p);

	}

	public void startGame() {
		initGame();
		actual = States.GAME;
		ghost.autoplay = false;
	}

	public void action(double delta) {
		frame += delta * 0.1;

		if (ghost == null || ghost.target == null || pacman == null
				|| pacman.target == null)
			return;

		if (ghost.target.intersect(pacman.target) && !busy) {

			if (actual == States.TITLE) {
				initGame();
				return;
			}

			if (pacman.powerUp) {
				actual = States.GHOSTKILLED;
				frame = 0;
				busy = true;
			}

			if (!pacman.powerUp) {
				actual = States.PACMANKILLED;
				frame = 0;
				busy = true;
			}

		}

		if (busy) {
			if (frame > 10) {
				// startGame();
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						activity.showUI();
					}
				});
			}
		}

	}
}
