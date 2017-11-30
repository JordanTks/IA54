package fr.utbm.ia54.Event;

import fr.utbm.ia54.Agent.TileAgent;
import fr.utbm.ia54.Agent.position;
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
  
  public position pos;
  
  public Assault(final ArrayList a, final position b) {
    this.path = a;
    this.pos = b;
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
   * Returns a String representation of the Assault event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("path  = ").append(this.path);
    result.append("pos  = ").append(this.pos);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 2749488563L;
}
