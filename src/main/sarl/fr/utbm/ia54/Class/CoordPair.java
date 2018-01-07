package fr.utbm.ia54.Class;

// Class used in A* version of the problem

public class CoordPair {
	private int x;
	private int y;

	public CoordPair(int x, int y){this.x=x; this.y=y;}

	public int getX(){return this.x;}
	public int getY(){return this.y;}
	
	public void setYplusOne(){
		this.y++;
	}
	
	public void setYminusOne(){
		this.y--;
	}
	
	public void setXplusOne() {
		this.x++;
	}
	
	public void setXminusOne() {
		this.x--;
	}

	/**
	 * compare a second pair of coordXY with itself
	 * @param pair: second CoordPair
	 * @return booelan: if (X,Y) are similar
	 */
	public boolean equals(CoordPair pair){
		return ((this.x==pair.getX()) && (this.y==pair.getY()));
	}

	@Override
	public String toString() {
		return "CoordPair [x=" + x + ", y=" + y + "]";
	}
}
