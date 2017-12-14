package fr.utbm.ia54.Agent;

import fr.utbm.ia54.Agent.Position;
import fr.utbm.ia54.Event.AskNeighbourSatisfaction;
import fr.utbm.ia54.Event.Assault;
import fr.utbm.ia54.Event.EndAgent;
import fr.utbm.ia54.Event.ResponseNeighbourSatisfaction;
import fr.utbm.ia54.Event.SafetyWait;
import fr.utbm.ia54.Event.TileSet;
import fr.utbm.ia54.Event.TokenReceived;
import fr.utbm.ia54.Event.TokenReleased;
import fr.utbm.ia54.Event.UpdateProblemInformations;
import fr.utbm.taquin.events.ActionUI;
import io.sarl.core.Behaviors;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Initialize;
import io.sarl.core.Lifecycle;
import io.sarl.core.Logging;
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
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(17)
@SuppressWarnings("all")
public class TileAgent extends Agent {
  private boolean isHappy = false;
  
  private boolean angry = false;
  
  private int numTile;
  
  private int numTileFromZero;
  
  private int numFrameHost;
  
  private int numFrameHostFromZero;
  
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
    this.numFrameHostFromZero = (((Integer) _get_2)).intValue();
    Object _get_3 = occurrence.parameters[3];
    this.problemSize = (((Integer) _get_3)).intValue();
    this.numFrameHost = (this.numFrameHostFromZero + 1);
    this.tokenPriority = Math.min(((this.numTile - 1) / this.problemSize), ((this.numTile - 1) % this.problemSize));
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.setLoggingName(("TileAgent" + Integer.valueOf(this.numTile)));
    if ((this.numTileFromZero == this.numFrameHostFromZero)) {
      this.isHappy = true;
    }
    Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER = this.$castSkill(Schedules.class, (this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES == null || this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES = this.$getSkill(Schedules.class)) : this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES);
    final Procedure1<Agent> _function = (Agent it) -> {
      Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$castSkill(Behaviors.class, (this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS == null || this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS = this.$getSkill(Behaviors.class)) : this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS);
      SafetyWait _safetyWait = new SafetyWait();
      _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.wake(_safetyWait);
    };
    _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER.in(1000, _function);
  }
  
  @SyntheticMember
  private void $behaviorUnit$SafetyWait$1(final SafetyWait occurrence) {
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
    TileSet _tileSet = new TileSet(this, null);
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_tileSet);
  }
  
  @SyntheticMember
  private void $behaviorUnit$TokenReceived$2(final TokenReceived occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("INFO : I received the token.");
    if (this.isHappy) {
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
    if ((((this.numTileFromZero % this.problemSize) + 1) == (this.numTileFromZero / this.problemSize))) {
      if ((this.numTileFromZero != (this.numFrameHostFromZero + 1))) {
        this.numTileFromZero = (this.numTileFromZero - 1);
      }
    } else {
      if (((this.numTileFromZero / this.problemSize) == (this.problemSize - 2))) {
        if ((this.numTileFromZero != (this.numFrameHostFromZero - this.problemSize))) {
          this.numTileFromZero = (this.numTileFromZero + this.problemSize);
        }
      } else {
        if (((this.numTileFromZero % this.problemSize) == (this.numTileFromZero / this.problemSize))) {
          if ((this.numTileFromZero != (this.numFrameHostFromZero - this.problemSize))) {
            this.numTileFromZero = (this.numTileFromZero + this.problemSize);
          }
        } else {
          if (((this.numTileFromZero / this.problemSize) == (this.problemSize - 1))) {
            if ((this.numTileFromZero != (this.numFrameHostFromZero - 1))) {
              this.numTileFromZero = (this.numTileFromZero + 1);
            }
          }
        }
      }
    }
    if (((this.numFrameHostFromZero % this.problemSize) == (this.numTileFromZero % this.problemSize))) {
      if ((this.numTileFromZero < this.numFrameHostFromZero)) {
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
        Assault _assault = new Assault(Position.NORTH, false);
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_assault, Scopes.identifiers(this.uuidFrameHost));
      } else {
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
        Assault _assault_1 = new Assault(Position.SOUTH, false);
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.emit(_assault_1, Scopes.identifiers(this.uuidFrameHost));
      }
    } else {
      if (((this.numFrameHostFromZero / this.problemSize) == (this.numTileFromZero / this.problemSize))) {
        if ((this.numTileFromZero < this.numFrameHostFromZero)) {
          DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_2 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
          Assault _assault_2 = new Assault(Position.WEST, false);
          _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_2.emit(_assault_2, Scopes.identifiers(this.uuidFrameHost));
        } else {
          DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_3 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
          Assault _assault_3 = new Assault(Position.EAST, false);
          _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_3.emit(_assault_3, Scopes.identifiers(this.uuidFrameHost));
        }
      } else {
        boolean north = ((this.numFrameHostFromZero / this.problemSize) >= (this.numTileFromZero / this.problemSize));
        boolean west = ((this.numFrameHostFromZero % this.problemSize) >= (this.numTileFromZero % this.problemSize));
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_4 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
        AskNeighbourSatisfaction _askNeighbourSatisfaction = new AskNeighbourSatisfaction(north, west);
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_4.emit(_askNeighbourSatisfaction, Scopes.identifiers(this.uuidFrameHost));
      }
    }
    if ((((this.numTileFromZero % this.problemSize) + 1) == (this.numTileFromZero / this.problemSize))) {
      this.numTileFromZero = (this.numTileFromZero + 1);
    } else {
      if (((this.numTileFromZero / this.problemSize) == (this.problemSize - 2))) {
        this.numTileFromZero = (this.numTileFromZero - this.problemSize);
      } else {
        if (((this.numTileFromZero % this.problemSize) == (this.numTileFromZero / this.problemSize))) {
          this.numTileFromZero = (this.numTileFromZero - this.problemSize);
        } else {
          if (((this.numTileFromZero / this.problemSize) == (this.problemSize - 1))) {
            this.numTileFromZero = (this.numTileFromZero - 1);
          }
        }
      }
    }
    return true;
  }
  
  @SyntheticMember
  private void $behaviorUnit$ResponseNeighbourSatisfaction$3(final ResponseNeighbourSatisfaction occurrence) {
    Iterator<Map.Entry<Position, Boolean>> iter = occurrence.neighbourSatisfaction.entrySet().iterator();
    Map.Entry<Position, Boolean> entry = iter.next();
    Boolean _value = entry.getValue();
    boolean _tripleEquals = (_value == Boolean.valueOf(false));
    if (_tripleEquals) {
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
  private void $behaviorUnit$UpdateProblemInformations$4(final UpdateProblemInformations occurrence) {
    this.uuidFrameHost = occurrence.id;
    this.numFrameHost = occurrence.num;
    this.numFrameHostFromZero = (this.numFrameHost - 1);
    if ((this.numFrameHost == this.numTile)) {
      this.isHappy = true;
    } else {
      this.isHappy = false;
    }
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
    UUID _iD = this.getID();
    UpdateProblemInformations _updateProblemInformations = new UpdateProblemInformations(this.numTile, _iD, occurrence.sendAckTo);
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_updateProblemInformations, Scopes.addresses(_$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.getDefaultSpace().getAddress(occurrence.id)));
  }
  
  @SyntheticMember
  private void $behaviorUnit$Assault$5(final Assault occurrence) {
  }
  
  @Pure
  protected int getNumFrameHost() {
    return this.numFrameHost;
  }
  
  protected void setNumFrameHost(final int num) {
    this.numFrameHost = num;
  }
  
  @Pure
  protected int getNumFrameHostFromZero() {
    return this.numFrameHostFromZero;
  }
  
  protected void setNumFrameHostFromZero(final int num) {
    this.numFrameHostFromZero = num;
  }
  
  @Pure
  protected UUID getUuidFrameHost() {
    return this.uuidFrameHost;
  }
  
  protected void setUuidFrameHost(final UUID uuid) {
    this.uuidFrameHost = uuid;
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
    return this.isHappy;
  }
  
  protected void setIsHappy(final boolean happy) {
    this.isHappy = happy;
  }
  
  @Pure
  protected int getNumTileFromZero() {
    return this.numTileFromZero;
  }
  
  @SyntheticMember
  private void $behaviorUnit$EndAgent$6(final EndAgent occurrence) {
    Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$castSkill(Lifecycle.class, (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = this.$getSkill(Lifecycle.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE);
    _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.killMe();
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
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Initialize(final Initialize occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Initialize$0(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$SafetyWait(final SafetyWait occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$SafetyWait$1(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ResponseNeighbourSatisfaction(final ResponseNeighbourSatisfaction occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ResponseNeighbourSatisfaction$3(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Assault(final Assault occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Assault$5(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$EndAgent(final EndAgent occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$EndAgent$6(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$UpdateProblemInformations(final UpdateProblemInformations occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$UpdateProblemInformations$4(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$TokenReceived(final TokenReceived occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$TokenReceived$2(occurrence));
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
    if (other.isHappy != this.isHappy)
      return false;
    if (other.angry != this.angry)
      return false;
    if (other.numTile != this.numTile)
      return false;
    if (other.numTileFromZero != this.numTileFromZero)
      return false;
    if (other.numFrameHost != this.numFrameHost)
      return false;
    if (other.numFrameHostFromZero != this.numFrameHostFromZero)
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
    result = prime * result + (this.isHappy ? 1231 : 1237);
    result = prime * result + (this.angry ? 1231 : 1237);
    result = prime * result + this.numTile;
    result = prime * result + this.numTileFromZero;
    result = prime * result + this.numFrameHost;
    result = prime * result + this.numFrameHostFromZero;
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
