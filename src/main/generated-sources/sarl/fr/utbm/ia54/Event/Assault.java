package fr.utbm.ia54.Event;

import fr.utbm.ia54.Agent.Position;
import fr.utbm.ia54.Agent.TileAgent;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.ArrayList;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class Assault extends Event {
  public ArrayList<TileAgent> path = new ArrayList<TileAgent>();
  
  public Position direction;
  
  public boolean isAttacked;
  
  public Assault(final ArrayList a, final Position b) {
    this.path = a;
    this.direction = b;
  }
  
  public Assault(final Position pos, final boolean attacked) {
    this.direction = pos;
    this.isAttacked = attacked;
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
    Assault other = (Assault) obj;
    if (other.isAttacked != this.isAttacked)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + (this.isAttacked ? 1231 : 1237);
    return result;
  }
  
  /**
   * Returns a String representation of the Assault event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("path  = ").append(this.path);
    result.append("direction  = ").append(this.direction);
    result.append("isAttacked  = ").append(this.isAttacked);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 1205067947L;
}
