package fr.utbm.ia54.Event;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author Jordan
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class Agresse extends Event {
  public int posAtaquantX;
  
  public int posAtaquantY;
  
  public Agresse(final int a, final int b) {
    this.posAtaquantX = a;
    this.posAtaquantY = b;
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
    Agresse other = (Agresse) obj;
    if (other.posAtaquantX != this.posAtaquantX)
      return false;
    if (other.posAtaquantY != this.posAtaquantY)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + this.posAtaquantX;
    result = prime * result + this.posAtaquantY;
    return result;
  }
  
  /**
   * Returns a String representation of the Agresse event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("posAtaquantX  = ").append(this.posAtaquantX);
    result.append("posAtaquantY  = ").append(this.posAtaquantY);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 2435824736L;
}
