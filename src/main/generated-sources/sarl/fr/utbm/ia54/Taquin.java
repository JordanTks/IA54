package fr.utbm.ia54;

import fr.utbm.ia54.Agent.BootAgent;
import fr.utbm.ia54.gui.TaquinFxApplication;
import fr.utbm.ia54.gui.fx.FxApplication;
import fr.utbm.ia54.gui.fx.FxBootAgent;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.BuiltinCapacitiesProvider;
import io.sarl.lang.core.DynamicSkillProvider;
import java.util.UUID;
import javax.inject.Inject;

@SarlSpecification("0.6")
@SarlElementType(17)
@SuppressWarnings("all")
public class Taquin extends FxBootAgent {
  protected Class<? extends FxApplication> getFxApplicationType() {
    return TaquinFxApplication.class;
  }
  
  protected Class<? extends Agent> getApplicationBootAgentType() {
    return BootAgent.class;
  }
  
  @SyntheticMember
  public Taquin(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  @Deprecated
  public Taquin(final BuiltinCapacitiesProvider provider, final UUID parentID, final UUID agentID) {
    super(provider, parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  public Taquin(final UUID parentID, final UUID agentID, final DynamicSkillProvider skillProvider) {
    super(parentID, agentID, skillProvider);
  }
}
