package fr.utbm.ia54.Event;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;

/**
 * @author Aloïs
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class TokenReleased extends Event {
  @SyntheticMember
  public TokenReleased() {
    super();
  }
  
  @SyntheticMember
  public TokenReleased(final Address source) {
    super(source);
  }
}
