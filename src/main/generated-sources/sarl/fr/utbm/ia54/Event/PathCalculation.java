package fr.utbm.ia54.Event;

import fr.utbm.ia54.Agent.FrameAgent;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * infos :
 * 
 * - provenance
 * - timeout
 * - jump count
 * 
 * process :
 * 
 * - check timer
 * - incrase jump count
 * - check if destination myself
 * - forward multicast message
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class PathCalculation extends Event {
  public long requestId;
  
  public UUID requestOrigin;
  
  public int provenanceDirection;
  
  public long timeStamp;
  
  public int jumpCount;
  
  public ArrayList<FrameAgent> path = new ArrayList<FrameAgent>();
  
  public PathCalculation(final long reqId, final UUID origin, final int from, final long time, final int jump, final ArrayList<FrameAgent> p) {
    this.requestId = reqId;
    this.provenanceDirection = from;
    this.requestOrigin = origin;
    this.timeStamp = time;
    this.jumpCount = jump;
    this.path = p;
  }
  
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
    PathCalculation other = (PathCalculation) obj;
    if (other.requestId != this.requestId)
      return false;
    if (!Objects.equals(this.requestOrigin, other.requestOrigin)) {
      return false;
    }
    if (other.provenanceDirection != this.provenanceDirection)
      return false;
    if (other.timeStamp != this.timeStamp)
      return false;
    if (other.jumpCount != this.jumpCount)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + (int) (this.requestId ^ (this.requestId >>> 32));
    result = prime * result + Objects.hashCode(this.requestOrigin);
    result = prime * result + this.provenanceDirection;
    result = prime * result + (int) (this.timeStamp ^ (this.timeStamp >>> 32));
    result = prime * result + this.jumpCount;
    return result;
  }
  
  /**
   * Returns a String representation of the PathCalculation event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("requestId  = ").append(this.requestId);
    result.append("requestOrigin  = ").append(this.requestOrigin);
    result.append("provenanceDirection  = ").append(this.provenanceDirection);
    result.append("timeStamp  = ").append(this.timeStamp);
    result.append("jumpCount  = ").append(this.jumpCount);
    result.append("path  = ").append(this.path);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 3925351587L;
}