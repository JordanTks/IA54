package fr.utbm.ia54.Event;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author Norgannon
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class TokenReceived extends Event {
  public int targetFrame;
  
  public TokenReceived(final int t) {
    this.targetFrame = t;
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
    TokenReceived other = (TokenReceived) obj;
    if (other.targetFrame != this.targetFrame)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + this.targetFrame;
    return result;
  }
  
  /**
   * Returns a String representation of the TokenReceived event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("targetFrame  = ").append(this.targetFrame);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = -1516015903L;
}
