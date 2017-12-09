package fr.utbm.ia54.Event;

import fr.utbm.ia54.Agent.Position;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class AskNeighbourSatisfaction extends Event {
  public boolean west;
  
  public boolean north;
  
  public boolean isAsked;
  
  public Position direction;
  
  public AskNeighbourSatisfaction(final boolean n, final boolean w) {
    this.north = n;
    this.west = w;
    this.isAsked = false;
  }
  
  public AskNeighbourSatisfaction(final boolean a, final Position dir) {
    this.isAsked = true;
    this.direction = dir;
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
    AskNeighbourSatisfaction other = (AskNeighbourSatisfaction) obj;
    if (other.west != this.west)
      return false;
    if (other.north != this.north)
      return false;
    if (other.isAsked != this.isAsked)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + (this.west ? 1231 : 1237);
    result = prime * result + (this.north ? 1231 : 1237);
    result = prime * result + (this.isAsked ? 1231 : 1237);
    return result;
  }
  
  /**
   * Returns a String representation of the AskNeighbourSatisfaction event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("west  = ").append(this.west);
    result.append("north  = ").append(this.north);
    result.append("isAsked  = ").append(this.isAsked);
    result.append("direction  = ").append(this.direction);
    return result.toString();
  }
}
