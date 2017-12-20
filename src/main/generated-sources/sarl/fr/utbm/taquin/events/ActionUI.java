package fr.utbm.taquin.events;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.Objects;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author Moi
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class ActionUI extends Event {
  public String prop;
  
  public int target;
  
  public ActionUI(final String a, final int b) {
    this.prop = a;
    this.target = b;
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
    ActionUI other = (ActionUI) obj;
    if (!Objects.equals(this.prop, other.prop)) {
      return false;
    }
    if (other.target != this.target)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.prop);
    result = prime * result + this.target;
    return result;
  }
  
  /**
   * Returns a String representation of the ActionUI event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("prop  = ").append(this.prop);
    result.append("target  = ").append(this.target);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 2797650337L;
}
