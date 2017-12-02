package fr.utbm.ia54.gui.fx;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Address;
import io.sarl.lang.core.Event;

/**
 * Triggered when the app need to be exited.
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class AppExit extends Event {
  @SyntheticMember
  public AppExit() {
    super();
  }
  
  @SyntheticMember
  public AppExit(final Address source) {
    super(source);
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 588368462L;
}
