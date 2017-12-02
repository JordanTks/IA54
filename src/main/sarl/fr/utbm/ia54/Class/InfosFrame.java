package fr.utbm.ia54.Class;

import java.util.UUID;

public class InfosFrame {

	private UUID uuidCurrentFrame;
	private CoordPair coordsCurrentFrame;
	private int costG; // from beginning to current
	private int costH; // from current to arrival
	private int costF; // sum of the previous (memorize)
	private UUID uuidPreviousFrame;
	private CoordPair coordsXYPreviousFrame;
	
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

	public CoordPair getCoordsXYPreviousFrame() {
		return coordsXYPreviousFrame;
	}

	public void setCoordsXYPreviousFrame(CoordPair coordsXYPreviousFrame) {
		this.coordsXYPreviousFrame = coordsXYPreviousFrame;
	}

	public UUID getUuidCurrentFrame() {
		return uuidCurrentFrame;
	}

	public CoordPair getCoordsCurrentFrame() {
		return coordsCurrentFrame;
	}
		
	
}
