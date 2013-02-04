package level;

import game.pachunter.FullscreenActivity;
import game.pachunter.R;

import java.util.LinkedList;
import java.util.List;

import map.Landscape;
import map.ValidMapGenerator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import audio.Sounds;
import data.Infos;

public class Game {
	public Landscape map;

	public boolean active = false;

	public enum States {
		TITLE, GAME, PACMANKILLED, GAMEOVER, GHOSTKILLED
	};

	public States actual = States.TITLE;
	public boolean busy;

	public int transferX(float x) {
		int X = map.maze.length;
		return (int) (x * X);
	}

	public int transferY(float y) {
		int Y = map.maze[0].length;
		return (int) (y * Y);
	}

	public synchronized void initGame() {
		
		Infos.silent = true;
		Sounds.stopBackground();

		active = false;
		food.clear();

		busy = false;
		actual = States.TITLE;
		map = new ValidMapGenerator(14, 20).map;
		ghost = new Ghost(Infos.ghostBitmap, this.map);
		ghost.x = 7;
		ghost.y = 10;
		ghost.showpath = false;

		pacman = new Pacman(Infos.ghostBitmap, this.map, this.food);
		pacman.x = 1;
		pacman.y = 1;

		pacman.avoid = ghost;
		ghost.pacman = pacman;

		pacman.speed = 0.20;
		ghost.speed = 0.18;

		for (int y = 0; y < map.maze[0].length; y++) {
			for (int x = 0; x < map.maze.length; x++) {
				if (map.maze[x][y].type == 0) {

					if (x >= map.maze.length / 2 - 2
							&& x < map.maze.length / 2 + 2
							&& y >= map.maze[0].length / 2 - 2
							&& y < map.maze[0].length / 2 + 2) {
						continue;
					}
					Food f = new Food(x, y);
					food.add(f);
					f.border = 15;

				}
			}
		}
		active = true;
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

		if (!active) {
			c.drawColor(Color.BLACK);
			p.setColor(Color.WHITE);

			p.setTextSize(30);

			String text = "wait while computing..";
			Rect bound = new Rect();
			p.getTextBounds(text, 0, text.length(), bound);

			c.drawText(text, c.getWidth() / 2 - bound.width() / 2,
					c.getHeight() / 2 - bound.height() / 2, p);

			return;
		}

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

				if ((pacman.powerUp || actual==States.GAMEOVER) && ((int) frame) % 2 == 0) {
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

		if (actual == States.PACMANKILLED) {
			c.drawColor(Color.BLACK);
			p.setColor(Color.WHITE);

			p.setTextSize(30);

			String text = "get ready for\nthe next level";
			Rect bound = new Rect();
			p.getTextBounds(text, 0, text.length(), bound);

			c.drawText(text, c.getWidth() / 2 - bound.width() / 2,
					c.getHeight() / 2 - bound.height() / 2, p);

			return;
		}

		if (actual == States.GAMEOVER) {
			p.setColor(Color.WHITE);

			p.setTextSize(30);

			String text = "Pacman wins, Game Over";
			Rect bound = new Rect();
			p.getTextBounds(text, 0, text.length(), bound);

			c.drawText(text, c.getWidth() / 2 - bound.width() / 2,
					c.getHeight() / 2 - bound.height() / 2, p);

			return;
		}

		pacman.draw(c);
		ghost.draw(c);

		if (actual == States.GHOSTKILLED) {
			return;
		}

		if (actual != States.GAME) {

			if ((((int) frame) % 2) == 0)
				return;
			p.setColor(Color.WHITE);
			p.setTextSize(70);

			String text = "touch trigger";
			Rect bound = new Rect();
			p.getTextBounds(text, 0, text.length(), bound);

			c.drawText(text, c.getWidth() / 2 - bound.width() / 2,
					c.getHeight() / 2 - bound.height() / 2, p);

		}

		if (actual != States.GAME)
			return;

		p.setColor(Color.rgb(255, 255, 255));
		p.setTextSize(34);
		c.drawText("Pills: " + pills, 10, 10, p);
		Rect bound = new Rect();
		
		String level = "Level: " + Infos.level;
		p.getTextBounds(level, 0, level.length(), bound);
		c.drawText(level, c.getWidth()-bound.width()-10, 10, p);

	}

	public synchronized void startGame() {
		initGame();

		pacman.speed = 0.10 + Infos.level*0.01;
		pacman.speed = 0.10 + Math.min(10,Infos.level)*0.01;
		ghost.speed = 0.14;

		actual = States.GAME;
		ghost.autoplay = false;
		ghost.showpath = true;
		
		Infos.silent = false;
		Sounds.startBackground(R.raw.pacman_background1);
	}

	public void action(double delta) {
		frame += delta * 0.1;

		if (actual==States.GAME && pills == 0) {
			frame = 0;
			busy = true;
			actual = States.GAMEOVER;
		}

		if (ghost == null || ghost.target == null || pacman == null
				|| pacman.target == null)
			return;

		if (ghost.target.intersect(pacman.target) && !busy) {

			if (actual == States.TITLE && !pacman.powerUp) {
				initGame();
				return;
			}

			if (pacman.powerUp) {
				Sounds.playMus(R.raw.pacman_getghost);
				actual = States.GHOSTKILLED;
				frame = 0;
				pacman.powerUp = false;
				pacman.avoidDanger = true;
				busy = true;
			} else if (!pacman.powerUp) {
				Sounds.playMus(R.raw.pacman_death);
				actual = States.PACMANKILLED;
				frame = 0;
				busy = true;
			}

		}

		if (busy) {
			
			if(actual == States.GAMEOVER){
				if(frame>9){
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							activity.showUI();
						}
					});
				}
			}

			if (actual == States.GHOSTKILLED) {

				ghost.toX = map.maze.length / 2;
				ghost.toY = map.maze[0].length / 2;
				ghost.freeze = true;
				ghost.avoidDanger = false;

				if (ghost.toX == ghost.x && ghost.toY == ghost.y) {
					actual = States.GAME;
					busy = false;
					ghost.freeze = false;
				}

			}

			if (frame > 10 && actual == States.PACMANKILLED) {
				if(ghost.autoplay){
					initGame();
				}else{
					Infos.level++;
					startGame();
				}
			}
		}

		ghost.action(delta);
		pacman.action(delta);
	}
}
