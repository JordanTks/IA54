package fr.utbm.ia54.Event;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.Objects;
import java.util.UUID;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class FindPathWithAstarAlgo extends Event {
  public UUID beginningFrame;
  
  public FindPathWithAstarAlgo(final UUID firstFrame) {
    this.beginningFrame = firstFrame;
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
    FindPathWithAstarAlgo other = (FindPathWithAstarAlgo) obj;
    if (!Objects.equals(this.beginningFrame, other.beginningFrame)) {
      return false;
    }
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + Objects.hashCode(this.beginningFrame);
    return result;
  }
  
  /**
   * Returns a String representation of the FindPathWithAstarAlgo event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("beginningFrame  = ").append(this.beginningFrame);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 4614236309L;
}
