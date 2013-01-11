package map;

public class Block {
	public int type;
	public int x;
	public int y;
	
	public Block(int type, int x, int y){
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
	public String toString(){
		return type+"";
	}
}
