package fr.utbm.ia54.Event;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class TokenReleased extends Event {
  public boolean success = true;
  
  public TokenReleased() {
  }
  
  public TokenReleased(final boolean s) {
    this.success = s;
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
    TokenReleased other = (TokenReleased) obj;
    if (other.success != this.success)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + (this.success ? 1231 : 1237);
    return result;
  }
  
  /**
   * Returns a String representation of the TokenReleased event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("success  = ").append(this.success);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 727267381L;
}
