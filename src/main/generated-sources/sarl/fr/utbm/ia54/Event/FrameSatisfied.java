/**
 * Event used to tell the BoardGame when satisfied to avoid active waiting.
 *  Parameter states wether becoming satisfied or dissatisfied
 */
package fr.utbm.ia54.Event;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class FrameSatisfied extends Event {
  public Boolean satisfied;
  
  public FrameSatisfied(final Boolean sat) {
    this.satisfied = sat;
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
    FrameSatisfied other = (FrameSatisfied) obj;
    if (other.satisfied != this.satisfied)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + (this.satisfied ? 1231 : 1237);
    return result;
  }
  
  /**
   * Returns a String representation of the FrameSatisfied event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("satisfied  = ").append(this.satisfied);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 1574040919L;
}
