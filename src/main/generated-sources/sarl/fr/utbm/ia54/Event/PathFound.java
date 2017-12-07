package fr.utbm.ia54.Event;

import fr.utbm.ia54.Agent.FrameAgent;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.ArrayList;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * Path found to the blank tile.
 * Sent directly to the agent that asked for the path calculation.
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class PathFound extends Event {
  public long requestId;
  
  public int jumpCount;
  
  public ArrayList<FrameAgent> path = new ArrayList<FrameAgent>();
  
  public PathFound(final long reqId, final int jump, final ArrayList<FrameAgent> p) {
    this.requestId = reqId;
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
    PathFound other = (PathFound) obj;
    if (other.requestId != this.requestId)
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
    result = prime * result + this.jumpCount;
    return result;
  }
  
  /**
   * Returns a String representation of the PathFound event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("requestId  = ").append(this.requestId);
    result.append("jumpCount  = ").append(this.jumpCount);
    result.append("path  = ").append(this.path);
    return result.toString();
  }
}
