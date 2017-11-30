package fr.utbm.ia54.Event;

import fr.utbm.ia54.Agent.FrameAgent;
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
public class FrameSet extends Event {
  public FrameAgent frame;
  
  public FrameSet(final FrameAgent f) {
    this.frame = f;
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
   * Returns a String representation of the FrameSet event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("frame  = ").append(this.frame);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = -2720675118L;
}
