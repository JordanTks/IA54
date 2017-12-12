package fr.utbm.ia54.Event;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;

/**
 * @author Moi
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class EndAgent extends Event {
  @SyntheticMember
  public EndAgent() {
    super();
  }
  
  @SyntheticMember
  public EndAgent(final Address source) {
    super(source);
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 588368462L;
}
