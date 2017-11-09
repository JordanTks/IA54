package fr.utbm.ia54.Event;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;

/**
 * @author Norgannon
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class FrameSatisfied extends Event {
  @SyntheticMember
  public FrameSatisfied() {
    super();
  }
  
  @SyntheticMember
  public FrameSatisfied(final Address source) {
    super(source);
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 588368462L;
}
