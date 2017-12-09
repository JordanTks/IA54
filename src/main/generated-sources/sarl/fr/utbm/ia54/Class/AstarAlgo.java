package fr.utbm.ia54.Class;

import fr.utbm.ia54.Agent.FrameAgent;
import fr.utbm.ia54.Agent.TileAgent;
import fr.utbm.ia54.Class.CoordPair;
import fr.utbm.ia54.Class.InfosFrame;
import fr.utbm.ia54.gui.TaquinFxViewerController;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public class AstarAlgo {
  private int PROBLEM_SIZE = 3;
  
  private int nbFrameSet = 0;
  
  private int nbTileSet = 0;
  
  private ArrayList<FrameAgent> frameList = new ArrayList<FrameAgent>();
  
  private ArrayList<UUID> frameUUIDList = new ArrayList<UUID>();
  
  private ArrayList<CoordPair> listCoordPairsOfFrames = new ArrayList<CoordPair>();
  
  private ArrayList<InfosFrame> openListOfFrames = new ArrayList<InfosFrame>();
  
  private ArrayList<InfosFrame> closedListOfFrames = new ArrayList<InfosFrame>();
  
  private FrameAgent beginningFrame;
  
  private InfosFrame beginningInfosFrame;
  
  private UUID uuidFRAMEwithTokenTile;
  
  private CoordPair coordsFRAMEwithTokenTile;
  
  private FrameAgent arrivalFrame;
  
  private InfosFrame arrivalInfosFrame;
  
  private UUID uuidFRAMEwithBlankTile;
  
  private CoordPair coordsFRAMEwithBlankTile;
  
  private ArrayList<TileAgent> tileList = new ArrayList<TileAgent>();
  
  private ArrayList<ArrayList<TileAgent>> tokenPriorityList = new ArrayList<ArrayList<TileAgent>>();
  
  private TaquinFxViewerController ctrl;
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AstarAlgo other = (AstarAlgo) obj;
    if (other.PROBLEM_SIZE != this.PROBLEM_SIZE)
      return false;
    if (other.nbFrameSet != this.nbFrameSet)
      return false;
    if (other.nbTileSet != this.nbTileSet)
      return false;
    if (!Objects.equals(this.uuidFRAMEwithTokenTile, other.uuidFRAMEwithTokenTile)) {
      return false;
    }
    if (!Objects.equals(this.uuidFRAMEwithBlankTile, other.uuidFRAMEwithBlankTile)) {
      return false;
    }
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + this.PROBLEM_SIZE;
    result = prime * result + this.nbFrameSet;
    result = prime * result + this.nbTileSet;
    result = prime * result + Objects.hashCode(this.uuidFRAMEwithTokenTile);
    result = prime * result + Objects.hashCode(this.uuidFRAMEwithBlankTile);
    return result;
  }
  
  @SyntheticMember
  public AstarAlgo() {
    super();
  }
}
