package fr.utbm.ia54.Event;

import fr.utbm.ia54.Agent.EmptyTileAgent;
import fr.utbm.ia54.Agent.TileAgent;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(14)
@SuppressWarnings("all")
public class TileSet extends Event {
  public TileAgent tile;
  
  public EmptyTileAgent blank;
  
  public TileSet(final TileAgent t, final EmptyTileAgent b) {
    this.tile = t;
    this.blank = b;
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
   * Returns a String representation of the TileSet event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("tile  = ").append(this.tile);
    result.append("blank  = ").append(this.blank);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = -1615184137L;
}
