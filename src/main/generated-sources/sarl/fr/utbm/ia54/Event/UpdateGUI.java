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
  
  public int numFrame;
  
  public UpdateGUI(final ArrayList<FrameAgent> list) {
    this.frameList = list;
  }
  
  public UpdateGUI(final int numeroFrame) {
    this.numFrame = numeroFrame;
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
    UpdateGUI other = (UpdateGUI) obj;
    if (other.numFrame != this.numFrame)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + this.numFrame;
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
    result.append("numFrame  = ").append(this.numFrame);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = -1982986322L;
}
