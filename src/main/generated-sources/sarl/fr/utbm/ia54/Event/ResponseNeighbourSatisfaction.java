package fr.utbm.ia54.Event;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.LinkedHashMap;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author Norgannon
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class ResponseNeighbourSatisfaction extends Event {
  public boolean isSatisfied;
  
  public LinkedHashMap<UUID, Boolean> neighbourSatisfaction;
  
  public ResponseNeighbourSatisfaction(final boolean b) {
    this.isSatisfied = b;
  }
  
  public ResponseNeighbourSatisfaction(final LinkedHashMap<UUID, Boolean> map) {
    this.neighbourSatisfaction = map;
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
    ResponseNeighbourSatisfaction other = (ResponseNeighbourSatisfaction) obj;
    if (other.isSatisfied != this.isSatisfied)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + (this.isSatisfied ? 1231 : 1237);
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
    result.append("neighbourSatisfaction  = ").append(this.neighbourSatisfaction);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 1839289197L;
}
