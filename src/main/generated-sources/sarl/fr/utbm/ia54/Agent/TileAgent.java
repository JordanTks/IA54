package fr.utbm.ia54.Agent;

import fr.utbm.ia54.Agent.Position;
import fr.utbm.ia54.Event.AskNeighbourSatisfaction;
import fr.utbm.ia54.Event.Assault;
import fr.utbm.ia54.Event.ResponseNeighbourSatisfaction;
import fr.utbm.ia54.Event.TileSet;
import fr.utbm.ia54.Event.TokenReceived;
import fr.utbm.ia54.Event.TokenReleased;
import fr.utbm.taquin.events.ActionUI;
import io.sarl.core.AgentKilled;
import io.sarl.core.AgentSpawned;
import io.sarl.core.Behaviors;
import io.sarl.core.ContextJoined;
import io.sarl.core.ContextLeft;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Destroy;
import io.sarl.core.Initialize;
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
import io.sarl.util.Scopes;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import javax.inject.Inject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Inline;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(17)
@SuppressWarnings("all")
public class TileAgent extends Agent {
  private boolean pleased = false;
  
  private boolean angry = false;
  
  private int numTile;
  
  private int numTileFromZero;
  
  private int numFrameHost;
  
  private UUID uuidFrameHost;
  
  private int problemSize;
  
  private int tokenPriority;
  
  private UUID parentAgent;
  
  @SyntheticMember
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Class<? extends TileAgent> _class = this.getClass();
    boolean _tripleEquals = (_class == null);
    if (_tripleEquals) {
      return;
    }
    this.parentAgent = occurrence.spawner;
    Object _get = occurrence.parameters[0];
    this.numTile = (((Integer) _get)).intValue();
    this.numTileFromZero = (this.numTile - 1);
    Object _get_1 = occurrence.parameters[1];
    this.uuidFrameHost = ((UUID) _get_1);
    Object _get_2 = occurrence.parameters[2];
    this.numFrameHost = (((Integer) _get_2)).intValue();
    Object _get_3 = occurrence.parameters[3];
    this.problemSize = (((Integer) _get_3)).intValue();
    this.tokenPriority = Math.min(((this.numTile - 1) / this.problemSize), ((this.numTile - 1) % this.problemSize));
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.setLoggingName(("TileAgent" + Integer.valueOf(this.numTile)));
    if ((this.numTileFromZero == this.numFrameHost)) {
      this.pleased = true;
    }
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
    TileSet _tileSet = new TileSet(this);
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_tileSet);
  }
  
  @SyntheticMember
  private void $behaviorUnit$TokenReceived$1(final TokenReceived occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("I received the token.");
    if (this.pleased) {
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
      TokenReleased _tokenReleased = new TokenReleased();
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_tokenReleased);
    } else {
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
      ActionUI _actionUI = new ActionUI("red", this.numTile);
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_2 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.emit(_actionUI, Scopes.addresses(_$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_2.getDefaultSpace().getAddress(this.parentAgent)));
      boolean b = this.chooseTarget();
    }
  }
  
  @Pure
  protected boolean chooseTarget() {
    if (((this.numFrameHost % this.problemSize) == (this.numTileFromZero % this.problemSize))) {
      if ((this.numTileFromZero < this.numFrameHost)) {
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
        Assault _assault = new Assault(Position.NORTH, false);
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_assault, Scopes.identifiers(this.uuidFrameHost));
      } else {
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
        Assault _assault_1 = new Assault(Position.SOUTH, false);
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.emit(_assault_1, Scopes.identifiers(this.uuidFrameHost));
      }
    } else {
      if (((this.numFrameHost / this.problemSize) == (this.numTileFromZero / this.problemSize))) {
        if ((this.numTileFromZero < this.numFrameHost)) {
          DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_2 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
          Assault _assault_2 = new Assault(Position.WEST, false);
          _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_2.emit(_assault_2, Scopes.identifiers(this.uuidFrameHost));
        } else {
          DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_3 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
          Assault _assault_3 = new Assault(Position.EAST, false);
          _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_3.emit(_assault_3, Scopes.identifiers(this.uuidFrameHost));
        }
      } else {
        boolean north = ((this.numFrameHost / this.problemSize) >= (this.numTileFromZero / this.problemSize));
        boolean west = ((this.numFrameHost % this.problemSize) >= (this.numTileFromZero % this.problemSize));
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_4 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
        AskNeighbourSatisfaction _askNeighbourSatisfaction = new AskNeighbourSatisfaction(north, west);
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_4.emit(_askNeighbourSatisfaction, Scopes.identifiers(this.uuidFrameHost));
      }
    }
    return true;
  }
  
  @SyntheticMember
  private void $behaviorUnit$ResponseNeighbourSatisfaction$2(final ResponseNeighbourSatisfaction occurrence) {
    Iterator<Map.Entry<Position, Boolean>> iter = occurrence.neighbourSatisfaction.entrySet().iterator();
    Map.Entry<Position, Boolean> entry = iter.next();
    Boolean _value = entry.getValue();
    boolean _notEquals = ((_value).booleanValue() != false);
    if (_notEquals) {
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
      Position _key = entry.getKey();
      Assault _assault = new Assault(_key, false);
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_assault, Scopes.identifiers(this.uuidFrameHost));
    } else {
      entry = iter.next();
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
      Position _key_1 = entry.getKey();
      Assault _assault_1 = new Assault(_key_1, false);
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.emit(_assault_1, Scopes.identifiers(this.uuidFrameHost));
    }
  }
  
  @SyntheticMember
  private void $behaviorUnit$Assault$3(final Assault occurrence) {
  }
  
  @Pure
  protected int getTokenPriority() {
    return this.tokenPriority;
  }
  
  @Pure
  protected int getNumTile() {
    return this.numTile;
  }
  
  @Pure
  protected boolean getIsHappy() {
    return this.pleased;
  }
  
  @SyntheticMember
  private void $behaviorUnit$Destroy$4(final Destroy occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("The agent was stopped.");
  }
  
  @SyntheticMember
  private void $behaviorUnit$AgentSpawned$5(final AgentSpawned occurrence) {
  }
  
  @SyntheticMember
  private void $behaviorUnit$AgentKilled$6(final AgentKilled occurrence) {
  }
  
  @SyntheticMember
  private void $behaviorUnit$ContextJoined$7(final ContextJoined occurrence) {
  }
  
  @SyntheticMember
  private void $behaviorUnit$ContextLeft$8(final ContextLeft occurrence) {
  }
  
  @SyntheticMember
  private void $behaviorUnit$MemberJoined$9(final MemberJoined occurrence) {
  }
  
  @SyntheticMember
  private void $behaviorUnit$MemberLeft$10(final MemberLeft occurrence) {
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
  
  @Extension
  @ImportedCapacityFeature(Behaviors.class)
  @SyntheticMember
  private transient ClearableReference<Skill> $CAPACITY_USE$IO_SARL_CORE_BEHAVIORS;
  
  @SyntheticMember
  @Pure
  @Inline(value = "$castSkill(Behaviors.class, ($0$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS == null || $0$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS.get() == null) ? ($0$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS = $0$getSkill(Behaviors.class)) : $0$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS)", imported = Behaviors.class)
  private Behaviors $CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER() {
    if (this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS == null || this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS.get() == null) {
      this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS = $getSkill(Behaviors.class);
    }
    return $castSkill(Behaviors.class, this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS);
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
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ContextLeft$8(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ContextJoined(final ContextJoined occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ContextJoined$7(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$MemberLeft(final MemberLeft occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$MemberLeft$10(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AgentSpawned(final AgentSpawned occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AgentSpawned$5(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ResponseNeighbourSatisfaction(final ResponseNeighbourSatisfaction occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ResponseNeighbourSatisfaction$2(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Assault(final Assault occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Assault$3(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Destroy(final Destroy occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Destroy$4(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AgentKilled(final AgentKilled occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AgentKilled$6(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$TokenReceived(final TokenReceived occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$TokenReceived$1(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$MemberJoined(final MemberJoined occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$MemberJoined$9(occurrence));
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
    TileAgent other = (TileAgent) obj;
    if (other.pleased != this.pleased)
      return false;
    if (other.angry != this.angry)
      return false;
    if (other.numTile != this.numTile)
      return false;
    if (other.numTileFromZero != this.numTileFromZero)
      return false;
    if (other.numFrameHost != this.numFrameHost)
      return false;
    if (!Objects.equals(this.uuidFrameHost, other.uuidFrameHost)) {
      return false;
    }
    if (other.problemSize != this.problemSize)
      return false;
    if (other.tokenPriority != this.tokenPriority)
      return false;
    if (!Objects.equals(this.parentAgent, other.parentAgent)) {
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
    result = prime * result + (this.pleased ? 1231 : 1237);
    result = prime * result + (this.angry ? 1231 : 1237);
    result = prime * result + this.numTile;
    result = prime * result + this.numTileFromZero;
    result = prime * result + this.numFrameHost;
    result = prime * result + Objects.hashCode(this.uuidFrameHost);
    result = prime * result + this.problemSize;
    result = prime * result + this.tokenPriority;
    result = prime * result + Objects.hashCode(this.parentAgent);
    return result;
  }
  
  @SyntheticMember
  public TileAgent(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  @Deprecated
  public TileAgent(final BuiltinCapacitiesProvider provider, final UUID parentID, final UUID agentID) {
    super(provider, parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  public TileAgent(final UUID parentID, final UUID agentID, final DynamicSkillProvider skillProvider) {
    super(parentID, agentID, skillProvider);
  }
}
