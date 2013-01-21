package level;

public class Entity {
	public float x = -1;
	public float y = -1;

	public boolean active = true;

	public double distance(Entity to) {
		return Math.sqrt(Math.pow(x - to.x, 2) - Math.pow(y - to.y, 2));
	}

}
