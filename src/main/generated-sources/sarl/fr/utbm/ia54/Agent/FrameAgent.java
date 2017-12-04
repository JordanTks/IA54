package fr.utbm.ia54.Agent;

import com.google.common.base.Objects;
import fr.utbm.ia54.Class.CoordPair;
import fr.utbm.ia54.Event.Assault;
import fr.utbm.ia54.Event.FrameSet;
import io.sarl.core.AgentKilled;
import io.sarl.core.AgentSpawned;
import io.sarl.core.ContextJoined;
import io.sarl.core.ContextLeft;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Destroy;
import io.sarl.core.Initialize;
import io.sarl.core.InnerContextAccess;
import io.sarl.core.Lifecycle;
import io.sarl.core.Logging;
import io.sarl.core.MemberJoined;
import io.sarl.core.MemberLeft;
import io.sarl.core.Schedules;
import io.sarl.lang.annotation.ImportedCapacityFeature;
import io.sarl.lang.annotation.PerceptGuardEvaluator;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Agent;
import io.sarl.lang.core.BuiltinCapacitiesProvider;
import io.sarl.lang.core.DynamicSkillProvider;
import io.sarl.lang.core.Skill;
import io.sarl.lang.util.ClearableReference;
import java.util.Collection;
import java.util.UUID;
import javax.inject.Inject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Inline;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author Jordan
 */
@SarlSpecification("0.6")
@SarlElementType(17)
@SuppressWarnings("all")
public class FrameAgent extends Agent {
  private boolean isSatisfied = false;
  
  private boolean isBlocked = false;
  
  private int nbNeighbours;
  
  private UUID northNeighbour;
  
  private UUID eastNeighbour;
  
  private UUID southNeighbour;
  
  private UUID westNeighbour;
  
  private UUID hostedTile;
  
  private boolean ready = false;
  
  private int dimension = (-1);
  
  private int idNum = (-1);
  
  private int xRow = (-1);
  
  private int yCol = (-1);
  
  private CoordPair coordPair;
  
  private int costG = 0;
  
  private int costH = 0;
  
  private int costF = 0;
  
  private UUID uuidPreviousFrame;
  
  private CoordPair coordsXYPreviousFrame;
  
  @SyntheticMember
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Object _get = occurrence.parameters[0];
    this.idNum = (((Integer) _get)).intValue();
    Object _get_1 = occurrence.parameters[1];
    this.xRow = (((Integer) _get_1)).intValue();
    Object _get_2 = occurrence.parameters[2];
    this.yCol = (((Integer) _get_2)).intValue();
    CoordPair _coordPair = new CoordPair(this.xRow, this.yCol);
    this.coordPair = _coordPair;
    this.nbNeighbours = 0;
    this.northNeighbour = null;
    this.eastNeighbour = null;
    this.southNeighbour = null;
    this.westNeighbour = null;
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.setLoggingName(("FrameAgent" + Integer.valueOf(this.idNum)));
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
    FrameSet _frameSet = new FrameSet(this);
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_frameSet);
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("Agent is set up.");
  }
  
  @Pure
  protected int getXRow() {
    return this.xRow;
  }
  
  @Pure
  protected int getYCol() {
    return this.yCol;
  }
  
  @Pure
  protected CoordPair getCoordPair() {
    return this.coordPair;
  }
  
  @Pure
  protected boolean getIsSatisfied() {
    return this.isSatisfied;
  }
  
  protected void setIsSatisfied(final boolean b) {
    this.isSatisfied = b;
  }
  
  @Pure
  protected boolean getIsBlocked() {
    return this.isBlocked;
  }
  
  protected void setIsBlocked(final boolean b) {
    this.isBlocked = b;
  }
  
  @Pure
  protected int getCostG() {
    return this.costG;
  }
  
  protected void setCostG(final int costG) {
    this.costG = costG;
  }
  
  @Pure
  protected int getCostH() {
    return this.costH;
  }
  
  protected void setCostH(final int costH) {
    this.costH = costH;
  }
  
  @Pure
  protected int getCostF() {
    return this.costF;
  }
  
  protected void setCostF(final int costF) {
    this.costF = costF;
  }
  
  @Pure
  protected UUID getUuidPreviousFrame() {
    return this.uuidPreviousFrame;
  }
  
  protected void setUuidPreviousFrame(final UUID uuidPreviousFrame) {
    this.uuidPreviousFrame = uuidPreviousFrame;
  }
  
  @Pure
  protected CoordPair getCoordsXYPreviousFrame() {
    return this.coordsXYPreviousFrame;
  }
  
  protected void setCoordsXYPreviousFrame(final CoordPair coordsXYPreviousFrame) {
    this.coordsXYPreviousFrame = coordsXYPreviousFrame;
  }
  
  @Pure
  protected String getCostsGHF() {
    return ((((("costG=" + Integer.valueOf(this.costG)) + " /costH=") + Integer.valueOf(this.costH)) + " /costF=") + Integer.valueOf(this.costF));
  }
  
  @SyntheticMember
  private void $behaviorUnit$Assault$1(final Assault occurrence) {
    boolean _equals = Objects.equal(occurrence.pos, null);
    if (_equals) {
    }
  }
  
  @Pure
  protected int getNumFrame() {
    return this.idNum;
  }
  
  protected int setNorthNeighbour(final UUID id) {
    int _xblockexpression = (int) 0;
    {
      this.northNeighbour = id;
      _xblockexpression = this.nbNeighbours++;
    }
    return _xblockexpression;
  }
  
  protected int setEastNeighbour(final UUID id) {
    int _xblockexpression = (int) 0;
    {
      this.eastNeighbour = id;
      _xblockexpression = this.nbNeighbours++;
    }
    return _xblockexpression;
  }
  
  protected int setSouthNeighbour(final UUID id) {
    int _xblockexpression = (int) 0;
    {
      this.southNeighbour = id;
      _xblockexpression = this.nbNeighbours++;
    }
    return _xblockexpression;
  }
  
  protected int setWestNeighbour(final UUID id) {
    int _xblockexpression = (int) 0;
    {
      this.westNeighbour = id;
      _xblockexpression = this.nbNeighbours++;
    }
    return _xblockexpression;
  }
  
  @Pure
  protected int getNbNeighbours() {
    return this.nbNeighbours;
  }
  
  @Pure
  protected UUID getNorthNeighbour() {
    return this.northNeighbour;
  }
  
  @Pure
  protected UUID getEastNeighbour() {
    return this.eastNeighbour;
  }
  
  @Pure
  protected UUID getSouthNeighbour() {
    return this.southNeighbour;
  }
  
  @Pure
  protected UUID getWestNeighbour() {
    return this.westNeighbour;
  }
  
  protected void setHostedTile(final UUID id) {
    this.hostedTile = id;
  }
  
  @SyntheticMember
  private void $behaviorUnit$Destroy$2(final Destroy occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("The agent was stopped.");
  }
  
  @SyntheticMember
  private void $behaviorUnit$AgentSpawned$3(final AgentSpawned occurrence) {
  }
  
  @SyntheticMember
  private void $behaviorUnit$AgentKilled$4(final AgentKilled occurrence) {
  }
  
  @SyntheticMember
  private void $behaviorUnit$ContextJoined$5(final ContextJoined occurrence) {
  }
  
  @SyntheticMember
  private void $behaviorUnit$ContextLeft$6(final ContextLeft occurrence) {
  }
  
  @SyntheticMember
  private void $behaviorUnit$MemberJoined$7(final MemberJoined occurrence) {
  }
  
  @SyntheticMember
  private void $behaviorUnit$MemberLeft$8(final MemberLeft occurrence) {
  }
  
  @Extension
  @ImportedCapacityFeature(Logging.class)
  @SyntheticMember
  private transient ClearableReference<Skill> $CAPACITY_USE$IO_SARL_CORE_LOGGING;
  
  @SyntheticMember
  @Pure
  @Inline(value = "$castSkill(Logging.class, ($0$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || $0$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? ($0$CAPACITY_USE$IO_SARL_CORE_LOGGING = $0$getSkill(Logging.class)) : $0$CAPACITY_USE$IO_SARL_CORE_LOGGING)", imported = Logging.class)
  private Logging $CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = $getSkill(Logging.class);
    }
    return $castSkill(Logging.class, this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
  }
  
  @Extension
  @ImportedCapacityFeature(Lifecycle.class)
  @SyntheticMember
  private transient ClearableReference<Skill> $CAPACITY_USE$IO_SARL_CORE_LIFECYCLE;
  
  @SyntheticMember
  @Pure
  @Inline(value = "$castSkill(Lifecycle.class, ($0$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || $0$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) ? ($0$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = $0$getSkill(Lifecycle.class)) : $0$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE)", imported = Lifecycle.class)
  private Lifecycle $CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = $getSkill(Lifecycle.class);
    }
    return $castSkill(Lifecycle.class, this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE);
  }
  
  @Extension
  @ImportedCapacityFeature(Schedules.class)
  @SyntheticMember
  private transient ClearableReference<Skill> $CAPACITY_USE$IO_SARL_CORE_SCHEDULES;
  
  @SyntheticMember
  @Pure
  @Inline(value = "$castSkill(Schedules.class, ($0$CAPACITY_USE$IO_SARL_CORE_SCHEDULES == null || $0$CAPACITY_USE$IO_SARL_CORE_SCHEDULES.get() == null) ? ($0$CAPACITY_USE$IO_SARL_CORE_SCHEDULES = $0$getSkill(Schedules.class)) : $0$CAPACITY_USE$IO_SARL_CORE_SCHEDULES)", imported = Schedules.class)
  private Schedules $CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES == null || this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES = $getSkill(Schedules.class);
    }
    return $castSkill(Schedules.class, this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES);
  }
  
  @Extension
  @ImportedCapacityFeature(InnerContextAccess.class)
  @SyntheticMember
  private transient ClearableReference<Skill> $CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS;
  
  @SyntheticMember
  @Pure
  @Inline(value = "$castSkill(InnerContextAccess.class, ($0$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS == null || $0$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS.get() == null) ? ($0$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS = $0$getSkill(InnerContextAccess.class)) : $0$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS)", imported = InnerContextAccess.class)
  private InnerContextAccess $CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS == null || this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS = $getSkill(InnerContextAccess.class);
    }
    return $castSkill(InnerContextAccess.class, this.$CAPACITY_USE$IO_SARL_CORE_INNERCONTEXTACCESS);
  }
  
  @Extension
  @ImportedCapacityFeature(DefaultContextInteractions.class)
  @SyntheticMember
  private transient ClearableReference<Skill> $CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS;
  
  @SyntheticMember
  @Pure
  @Inline(value = "$castSkill(DefaultContextInteractions.class, ($0$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || $0$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? ($0$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = $0$getSkill(DefaultContextInteractions.class)) : $0$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS)", imported = DefaultContextInteractions.class)
  private DefaultContextInteractions $CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = $getSkill(DefaultContextInteractions.class);
    }
    return $castSkill(DefaultContextInteractions.class, this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Initialize(final Initialize occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Initialize$0(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ContextLeft(final ContextLeft occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ContextLeft$6(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ContextJoined(final ContextJoined occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ContextJoined$5(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$MemberLeft(final MemberLeft occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$MemberLeft$8(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AgentSpawned(final AgentSpawned occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AgentSpawned$3(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Assault(final Assault occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Assault$1(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Destroy(final Destroy occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Destroy$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AgentKilled(final AgentKilled occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AgentKilled$4(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$MemberJoined(final MemberJoined occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$MemberJoined$7(occurrence));
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
    FrameAgent other = (FrameAgent) obj;
    if (other.isSatisfied != this.isSatisfied)
      return false;
    if (other.isBlocked != this.isBlocked)
      return false;
    if (other.nbNeighbours != this.nbNeighbours)
      return false;
    if (!java.util.Objects.equals(this.northNeighbour, other.northNeighbour)) {
      return false;
    }
    if (!java.util.Objects.equals(this.eastNeighbour, other.eastNeighbour)) {
      return false;
    }
    if (!java.util.Objects.equals(this.southNeighbour, other.southNeighbour)) {
      return false;
    }
    if (!java.util.Objects.equals(this.westNeighbour, other.westNeighbour)) {
      return false;
    }
    if (!java.util.Objects.equals(this.hostedTile, other.hostedTile)) {
      return false;
    }
    if (other.ready != this.ready)
      return false;
    if (other.dimension != this.dimension)
      return false;
    if (other.idNum != this.idNum)
      return false;
    if (other.xRow != this.xRow)
      return false;
    if (other.yCol != this.yCol)
      return false;
    if (other.costG != this.costG)
      return false;
    if (other.costH != this.costH)
      return false;
    if (other.costF != this.costF)
      return false;
    if (!java.util.Objects.equals(this.uuidPreviousFrame, other.uuidPreviousFrame)) {
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
    result = prime * result + (this.isSatisfied ? 1231 : 1237);
    result = prime * result + (this.isBlocked ? 1231 : 1237);
    result = prime * result + this.nbNeighbours;
    result = prime * result + java.util.Objects.hashCode(this.northNeighbour);
    result = prime * result + java.util.Objects.hashCode(this.eastNeighbour);
    result = prime * result + java.util.Objects.hashCode(this.southNeighbour);
    result = prime * result + java.util.Objects.hashCode(this.westNeighbour);
    result = prime * result + java.util.Objects.hashCode(this.hostedTile);
    result = prime * result + (this.ready ? 1231 : 1237);
    result = prime * result + this.dimension;
    result = prime * result + this.idNum;
    result = prime * result + this.xRow;
    result = prime * result + this.yCol;
    result = prime * result + this.costG;
    result = prime * result + this.costH;
    result = prime * result + this.costF;
    result = prime * result + java.util.Objects.hashCode(this.uuidPreviousFrame);
    return result;
  }
  
  @SyntheticMember
  public FrameAgent(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  @Deprecated
  public FrameAgent(final BuiltinCapacitiesProvider provider, final UUID parentID, final UUID agentID) {
    super(provider, parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  public FrameAgent(final UUID parentID, final UUID agentID, final DynamicSkillProvider skillProvider) {
    super(parentID, agentID, skillProvider);
  }
}
