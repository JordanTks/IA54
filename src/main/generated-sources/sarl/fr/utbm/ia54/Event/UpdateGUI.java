package fr.utbm.ia54.Event;

import fr.utbm.ia54.Agent.FrameAgent;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.ArrayList;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author Norgannon
 */
@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class UpdateGUI extends Event {
  public ArrayList<FrameAgent> frameList;
  
  public UpdateGUI(final ArrayList<FrameAgent> list) {
    this.frameList = list;
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
   * Returns a String representation of the UpdateGUI event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("frameList  = ").append(this.frameList);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = -74404880L;
}
