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
public class UpdateProblemInformations extends Event {
  public Boolean start;
  
  public int num;
  
  public UUID id;
  
  public UUID sendAckTo;
  
  public UpdateProblemInformations(final Boolean st, final UUID sendResponseTo) {
    this.start = st;
    this.sendAckTo = sendResponseTo;
  }
  
  public UpdateProblemInformations(final int i, final UUID hostID, final UUID sendLastAckTo) {
    this.start = Boolean.valueOf(false);
    this.num = i;
    this.id = hostID;
    this.sendAckTo = sendLastAckTo;
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
    UpdateProblemInformations other = (UpdateProblemInformations) obj;
    if (other.start != this.start)
      return false;
    if (other.num != this.num)
      return false;
    if (!Objects.equals(this.id, other.id)) {
      return false;
    }
    if (!Objects.equals(this.sendAckTo, other.sendAckTo)) {
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
    result = prime * result + (this.start ? 1231 : 1237);
    result = prime * result + this.num;
    result = prime * result + Objects.hashCode(this.id);
    result = prime * result + Objects.hashCode(this.sendAckTo);
    return result;
  }
  
  /**
   * Returns a String representation of the UpdateProblemInformations event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("start  = ").append(this.start);
    result.append("num  = ").append(this.num);
    result.append("id  = ").append(this.id);
    result.append("sendAckTo  = ").append(this.sendAckTo);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = -490672836L;
}
