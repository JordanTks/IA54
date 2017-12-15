package fr.utbm.ia54.Event;

import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class ConfigureSimulation extends Event {
  public final int probSize;
  
  public final boolean distribuited;
  
  public ConfigureSimulation(final int size, final boolean dist) {
    this.probSize = size;
    this.distribuited = dist;
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
    ConfigureSimulation other = (ConfigureSimulation) obj;
    if (other.probSize != this.probSize)
      return false;
    if (other.distribuited != this.distribuited)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + this.probSize;
    result = prime * result + (this.distribuited ? 1231 : 1237);
    return result;
  }
  
  /**
   * Returns a String representation of the ConfigureSimulation event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("probSize  = ").append(this.probSize);
    result.append("distribuited  = ").append(this.distribuited);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 171997137L;
}
