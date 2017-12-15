package fr.utbm.ia54.Event;

import fr.utbm.ia54.Enum.Direction;
import fr.utbm.ia54.Enum.Satisfaction;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.LinkedHashMap;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author Norgannon
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class ResponseNeighbourSatisfaction extends Event {
  public Satisfaction isSatisfied;
  
  public Direction direction;
  
  public LinkedHashMap<Direction, Satisfaction> neighbourSatisfaction;
  
  public ResponseNeighbourSatisfaction(final Satisfaction s, final Direction dir) {
    this.isSatisfied = s;
    this.direction = dir;
  }
  
  public ResponseNeighbourSatisfaction(final LinkedHashMap<Direction, Satisfaction> map) {
    this.neighbourSatisfaction = map;
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    return result;
  }
  
  /**
   * Returns a String representation of the ResponseNeighbourSatisfaction event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("isSatisfied  = ").append(this.isSatisfied);
    result.append("direction  = ").append(this.direction);
    result.append("neighbourSatisfaction  = ").append(this.neighbourSatisfaction);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = -5363292878L;
}
