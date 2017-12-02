package fr.utbm.taquin.events;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.List;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author Moi
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class InitDone extends Event {
  public List<Integer> listPos;
  
  public InitDone(final List<Integer> list) {
    this.listPos = list;
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
   * Returns a String representation of the InitDone event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("listPos  = ").append(this.listPos);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 2289170875L;
}
