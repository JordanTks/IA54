package fr.utbm.ia54.Class;

import java.util.UUID;

public class InfosFrame {

	private UUID uuidCurrentFrame;
	private CoordPair coordsCurrentFrame;
	private int costG; // from beginning to current
	private int costH; // from current to arrival
	private int costF; // sum of the previous (memorize)
	private UUID uuidPreviousFrame;
	private CoordPair coordsPreviousFrame;
	private int numFrame=-1;
	
	public InfosFrame(UUID uuidCurrentFrame, CoordPair coordsCurrentFrame) {
		super();
		this.uuidCurrentFrame = uuidCurrentFrame;
		this.coordsCurrentFrame = coordsCurrentFrame;
	}

	public int getCostG() {
		return costG;
	}

	public void setCostG(int costG) {
		this.costG = costG;
	}

	public int getCostH() {
		return costH;
	}

	public void setCostH(int costH) {
		this.costH = costH;
	}

	public int getCostF() {
		return costF;
	}

	public void setCostF(int costF) {
		this.costF = costF;
	}

	public UUID getUuidPreviousFrame() {
		return uuidPreviousFrame;
	}

	public void setUuidPreviousFrame(UUID uuidPreviousFrame) {
		this.uuidPreviousFrame = uuidPreviousFrame;
	}

	public CoordPair getCoordsPreviousFrame() {
		return coordsPreviousFrame;
	}

	public void setCoordsPreviousFrame(CoordPair coordsXYPreviousFrame) {
		this.coordsPreviousFrame = coordsXYPreviousFrame;
	}

	public UUID getUuidCurrentFrame() {
		return uuidCurrentFrame;
	}

	public CoordPair getCoordsCurrentFrame() {
		return coordsCurrentFrame;
	}

	public String toStringGHF() {
		return "costG=" + costG + ", costH=" + costH + ", costF=" + costF;
	}

	public int getNumFrame() {
		return numFrame;
	}

	public void setNumFrame(int numFrame) {
		this.numFrame = numFrame;
	}
	
	
	
}
