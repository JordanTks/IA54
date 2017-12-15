package fr.utbm.ia54.Agent;

import com.google.common.base.Objects;
import fr.utbm.ia54.Agent.Position;
import fr.utbm.ia54.Class.CoordPair;
import fr.utbm.ia54.Event.AcknowledgmentDataUpdated;
import fr.utbm.ia54.Event.AskNeighbourSatisfaction;
import fr.utbm.ia54.Event.Assault;
import fr.utbm.ia54.Event.EndAgent;
import fr.utbm.ia54.Event.FindPathWithAstarAlgo;
import fr.utbm.ia54.Event.FrameSet;
import fr.utbm.ia54.Event.PathCalculation;
import fr.utbm.ia54.Event.PathCalculationTimeOut;
import fr.utbm.ia54.Event.PathFound;
import fr.utbm.ia54.Event.ResponseNeighbourSatisfaction;
import fr.utbm.ia54.Event.SafetyWait;
import fr.utbm.ia54.Event.TokenReleased;
import fr.utbm.ia54.Event.UpdateGUI;
import fr.utbm.ia54.Event.UpdateProblemInformations;
import io.sarl.core.AgentTask;
import io.sarl.core.Behaviors;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Initialize;
import io.sarl.core.InnerContextAccess;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.UUID;
import javax.inject.Inject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Inline;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Pure;

/**
 * @author Jordan
 */
@SarlSpecification("0.6")
@SarlElementType(17)
@SuppressWarnings("all")
public class FrameAgent extends Agent {
  private final int TIMEOUT = 150;
  
  private long previousPathCalculationId;
  
  private int previousPathCalculationJumpValue;
  
  private boolean previousPathDidMoveHappyTiles;
  
  private ArrayList<FrameAgent> bestPath;
  
  private ArrayList<FrameAgent> bestForcedPath;
  
  private int nbAck;
  
  private boolean isSatisfied = false;
  
  private boolean isBlocked = false;
  
  private boolean didMyTileMateHaveTheToken = false;
  
  private int nbNeighbours;
  
  private UUID northNeighbour;
  
  private UUID eastNeighbour;
  
  private UUID southNeighbour;
  
  private UUID westNeighbour;
  
  private UUID hostedTile;
  
  private int hostedNumTile;
  
  private LinkedHashMap<Position, Boolean> neighbourSatisfaction = new LinkedHashMap<Position, Boolean>();
  
  private boolean ready = false;
  
  private int dimension = (-1);
  
  private int idNum = (-1);
  
  private int idNumFromZero;
  
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
    FrameSet _frameSet = new FrameSet(this);
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_frameSet);
  }
  
  @SyntheticMember
  private void $behaviorUnit$AskNeighbourSatisfaction$2(final AskNeighbourSatisfaction occurrence) {
    this.neighbourSatisfaction.clear();
    if (occurrence.north) {
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
      AskNeighbourSatisfaction _askNeighbourSatisfaction = new AskNeighbourSatisfaction(true, Position.NORTH);
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_askNeighbourSatisfaction, Scopes.identifiers(this.northNeighbour));
    } else {
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
      AskNeighbourSatisfaction _askNeighbourSatisfaction_1 = new AskNeighbourSatisfaction(true, Position.SOUTH);
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.emit(_askNeighbourSatisfaction_1, Scopes.identifiers(this.southNeighbour));
    }
    if (occurrence.west) {
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_2 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
      AskNeighbourSatisfaction _askNeighbourSatisfaction_2 = new AskNeighbourSatisfaction(true, Position.WEST);
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_2.emit(_askNeighbourSatisfaction_2, Scopes.identifiers(this.westNeighbour));
    } else {
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_3 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
      AskNeighbourSatisfaction _askNeighbourSatisfaction_3 = new AskNeighbourSatisfaction(true, Position.EAST);
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_3.emit(_askNeighbourSatisfaction_3, Scopes.identifiers(this.eastNeighbour));
    }
  }
  
  @SyntheticMember
  @Pure
  private boolean $behaviorUnitGuard$AskNeighbourSatisfaction$2(final AskNeighbourSatisfaction it, final AskNeighbourSatisfaction occurrence) {
    return (!it.isAsked);
  }
  
  @SyntheticMember
  private void $behaviorUnit$AskNeighbourSatisfaction$3(final AskNeighbourSatisfaction occurrence) {
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
    ResponseNeighbourSatisfaction _responseNeighbourSatisfaction = new ResponseNeighbourSatisfaction(this.isSatisfied, occurrence.direction);
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_responseNeighbourSatisfaction, Scopes.identifiers(occurrence.getSource().getUUID()));
  }
  
  @SyntheticMember
  @Pure
  private boolean $behaviorUnitGuard$AskNeighbourSatisfaction$3(final AskNeighbourSatisfaction it, final AskNeighbourSatisfaction occurrence) {
    return it.isAsked;
  }
  
  @SyntheticMember
  private void $behaviorUnit$ResponseNeighbourSatisfaction$4(final ResponseNeighbourSatisfaction occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(((("DEBUGG : ResponseNeighbourSatisfaction [frame] : direction " + occurrence.direction) + "    isSatisfied ") + Boolean.valueOf(occurrence.isSatisfied)));
    this.neighbourSatisfaction.put(occurrence.direction, Boolean.valueOf(occurrence.isSatisfied));
    int _size = this.neighbourSatisfaction.size();
    boolean _greaterEqualsThan = (_size >= 2);
    if (_greaterEqualsThan) {
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
      ResponseNeighbourSatisfaction _responseNeighbourSatisfaction = new ResponseNeighbourSatisfaction(this.neighbourSatisfaction);
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_responseNeighbourSatisfaction, Scopes.identifiers(this.hostedTile));
    }
  }
  
  @SyntheticMember
  private void $behaviorUnit$Assault$5(final Assault occurrence) {
    this.bestPath = null;
    this.bestForcedPath = null;
    if ((occurrence.direction == Position.NORTH)) {
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
      Assault _assault = new Assault(Position.SOUTH, true);
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_assault, Scopes.identifiers(this.northNeighbour));
    } else {
      if ((occurrence.direction == Position.SOUTH)) {
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
        Assault _assault_1 = new Assault(Position.NORTH, true);
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.emit(_assault_1, Scopes.identifiers(this.southNeighbour));
      } else {
        if ((occurrence.direction == Position.WEST)) {
          DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_2 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
          Assault _assault_2 = new Assault(Position.EAST, true);
          _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_2.emit(_assault_2, Scopes.identifiers(this.westNeighbour));
        } else {
          if ((occurrence.direction == Position.EAST)) {
            DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_3 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
            Assault _assault_3 = new Assault(Position.WEST, true);
            _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_3.emit(_assault_3, Scopes.identifiers(this.eastNeighbour));
          }
        }
      }
    }
  }
  
  @SyntheticMember
  @Pure
  private boolean $behaviorUnitGuard$Assault$5(final Assault it, final Assault occurrence) {
    return (!it.isAttacked);
  }
  
  @SyntheticMember
  private void $behaviorUnit$Assault$6(final Assault occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("DEBUGG : I GOT ATTACKED !");
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
    UUID _iD = this.getID();
    FindPathWithAstarAlgo _findPathWithAstarAlgo = new FindPathWithAstarAlgo(_iD);
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_findPathWithAstarAlgo);
  }
  
  @SyntheticMember
  @Pure
  private boolean $behaviorUnitGuard$Assault$6(final Assault it, final Assault occurrence) {
    return it.isAttacked;
  }
  
  @SyntheticMember
  private void $behaviorUnit$PathCalculation$7(final PathCalculation occurrence) {
    long _currentTimeMillis = System.currentTimeMillis();
    long _minus = (_currentTimeMillis - this.TIMEOUT);
    boolean _greaterThan = (_minus > occurrence.timeStamp);
    if (_greaterThan) {
      return;
    }
    UUID _iD = this.getID();
    boolean _equals = Objects.equal(occurrence.requestOrigin, _iD);
    if (_equals) {
      return;
    }
    if ((this.previousPathCalculationId == occurrence.requestId)) {
      if ((!occurrence.forcePath)) {
        if ((!this.previousPathDidMoveHappyTiles)) {
          if ((this.previousPathCalculationJumpValue <= occurrence.jumpCount)) {
            return;
          }
        }
      } else {
        if ((!this.previousPathDidMoveHappyTiles)) {
          return;
        } else {
          if ((this.previousPathCalculationJumpValue < occurrence.jumpCount)) {
            return;
          }
        }
      }
    }
    this.previousPathCalculationId = occurrence.requestId;
    this.previousPathCalculationJumpValue = occurrence.jumpCount;
    this.previousPathDidMoveHappyTiles = occurrence.forcePath;
    ArrayList<FrameAgent> p = null;
    ArrayList<FrameAgent> _arrayList = new ArrayList<FrameAgent>();
    p = _arrayList;
    p.addAll(occurrence.path);
    p.add(this);
    if ((this.hostedNumTile == 0)) {
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
      PathFound _pathFound = new PathFound(occurrence.requestId, occurrence.forcePath, occurrence.jumpCount, p);
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_pathFound, 
        Scopes.addresses(_$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.getDefaultSpace().getAddress(occurrence.requestOrigin)));
      return;
    }
    boolean forcingAPath = false;
    if (occurrence.forcePath) {
      forcingAPath = true;
    } else {
      if (this.isSatisfied) {
        forcingAPath = true;
      }
    }
    if (this.didMyTileMateHaveTheToken) {
      if (((occurrence.provenanceDirection == Position.NORTH) && (this.southNeighbour != null))) {
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_2 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
        PathCalculation _pathCalculation = new PathCalculation(occurrence.requestId, occurrence.requestOrigin, Position.NORTH, 
          occurrence.timeStamp, forcingAPath, (occurrence.jumpCount + 1), p);
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_3 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_2.emit(_pathCalculation, 
          Scopes.addresses(_$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_3.getDefaultSpace().getAddress(this.southNeighbour)));
        return;
      }
      if (((occurrence.provenanceDirection == Position.SOUTH) && (this.northNeighbour != null))) {
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_4 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
        PathCalculation _pathCalculation_1 = new PathCalculation(occurrence.requestId, occurrence.requestOrigin, Position.SOUTH, 
          occurrence.timeStamp, forcingAPath, (occurrence.jumpCount + 1), p);
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_5 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_4.emit(_pathCalculation_1, 
          Scopes.addresses(_$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_5.getDefaultSpace().getAddress(this.northNeighbour)));
        return;
      }
      if (((occurrence.provenanceDirection == Position.EAST) && (this.westNeighbour != null))) {
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_6 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
        PathCalculation _pathCalculation_2 = new PathCalculation(occurrence.requestId, occurrence.requestOrigin, Position.EAST, 
          occurrence.timeStamp, forcingAPath, (occurrence.jumpCount + 1), p);
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_7 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_6.emit(_pathCalculation_2, 
          Scopes.addresses(_$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_7.getDefaultSpace().getAddress(this.westNeighbour)));
        return;
      }
      if (((occurrence.provenanceDirection == Position.WEST) && (this.eastNeighbour != null))) {
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_8 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
        PathCalculation _pathCalculation_3 = new PathCalculation(occurrence.requestId, occurrence.requestOrigin, Position.WEST, 
          occurrence.timeStamp, forcingAPath, (occurrence.jumpCount + 1), p);
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_9 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_8.emit(_pathCalculation_3, 
          Scopes.addresses(_$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_9.getDefaultSpace().getAddress(this.eastNeighbour)));
        return;
      }
    }
    if (((this.northNeighbour != null) && (occurrence.provenanceDirection != Position.NORTH))) {
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_10 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
      PathCalculation _pathCalculation_4 = new PathCalculation(occurrence.requestId, occurrence.requestOrigin, Position.SOUTH, 
        occurrence.timeStamp, forcingAPath, (occurrence.jumpCount + 1), p);
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_11 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_10.emit(_pathCalculation_4, 
        Scopes.addresses(_$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_11.getDefaultSpace().getAddress(this.northNeighbour)));
    }
    if (((this.eastNeighbour != null) && (occurrence.provenanceDirection != Position.EAST))) {
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_12 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
      PathCalculation _pathCalculation_5 = new PathCalculation(occurrence.requestId, occurrence.requestOrigin, Position.WEST, 
        occurrence.timeStamp, forcingAPath, (occurrence.jumpCount + 1), p);
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_13 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_12.emit(_pathCalculation_5, 
        Scopes.addresses(_$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_13.getDefaultSpace().getAddress(this.eastNeighbour)));
    }
    if (((this.southNeighbour != null) && (occurrence.provenanceDirection != Position.SOUTH))) {
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_14 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
      PathCalculation _pathCalculation_6 = new PathCalculation(occurrence.requestId, occurrence.requestOrigin, Position.NORTH, 
        occurrence.timeStamp, forcingAPath, (occurrence.jumpCount + 1), p);
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_15 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_14.emit(_pathCalculation_6, 
        Scopes.addresses(_$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_15.getDefaultSpace().getAddress(this.southNeighbour)));
    }
    if (((this.westNeighbour != null) && (occurrence.provenanceDirection != Position.WEST))) {
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_16 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
      PathCalculation _pathCalculation_7 = new PathCalculation(occurrence.requestId, occurrence.requestOrigin, Position.EAST, 
        occurrence.timeStamp, forcingAPath, (occurrence.jumpCount + 1), p);
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_17 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_16.emit(_pathCalculation_7, 
        Scopes.addresses(_$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_17.getDefaultSpace().getAddress(this.westNeighbour)));
    }
  }
  
  @SyntheticMember
  private void $behaviorUnit$PathFound$8(final PathFound occurrence) {
    if ((!occurrence.forcePath)) {
      if ((this.bestPath == null)) {
        this.bestPath = occurrence.path;
        return;
      }
      int _size = this.bestPath.size();
      int _size_1 = occurrence.path.size();
      boolean _greaterThan = (_size > _size_1);
      if (_greaterThan) {
        this.bestPath = occurrence.path;
        return;
      }
    }
    if ((this.bestPath == null)) {
      if ((this.bestForcedPath == null)) {
        this.bestForcedPath = occurrence.path;
        return;
      }
      int _size_2 = this.bestForcedPath.size();
      int _size_3 = occurrence.path.size();
      boolean _greaterThan_1 = (_size_2 > _size_3);
      if (_greaterThan_1) {
        this.bestForcedPath = occurrence.path;
        return;
      }
    }
  }
  
  @SyntheticMember
  private void $behaviorUnit$PathCalculationTimeOut$9(final PathCalculationTimeOut occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("DEBUGG : PathCalculationTimeOut");
    if ((this.bestPath != null)) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
      int _size = this.bestPath.size();
      String _plus = ("DEBUGG : There is a good path (length : " + Integer.valueOf(_size));
      String _plus_1 = (_plus + ")");
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info(_plus_1);
      for (final FrameAgent f : this.bestPath) {
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
        int _numFrame = f.getNumFrame();
        String _plus_2 = ("DEBUGG : GOOD PATH > " + Integer.valueOf(_numFrame));
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info(_plus_2);
      }
    }
    if ((this.bestForcedPath != null)) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
      int _size_1 = this.bestForcedPath.size();
      String _plus_3 = ("DEBUGG : There is a bad path (length : " + Integer.valueOf(_size_1));
      String _plus_4 = (_plus_3 + ")");
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3.info(_plus_4);
      for (final FrameAgent f_1 : this.bestForcedPath) {
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_4 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
        int _numFrame_1 = f_1.getNumFrame();
        String _plus_5 = ("DEBUGG : BAD PATH > " + Integer.valueOf(_numFrame_1));
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_4.info(_plus_5);
      }
    }
    if ((this.bestPath != null)) {
      this.iterateMovementChain(this.bestPath);
    } else {
      if ((this.bestForcedPath != null)) {
        this.iterateMovementChain(this.bestForcedPath);
      } else {
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_5 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_5.error("Error 03 : No path found in time at the end of PathCalculation spreading.");
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
        TokenReleased _tokenReleased = new TokenReleased();
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_tokenReleased);
      }
    }
  }
  
  protected AgentTask iterateMovementChain(final ArrayList<FrameAgent> path) {
    AgentTask _xblockexpression = null;
    {
      int _size = path.size();
      int _plus = (_size + 1);
      this.nbAck = _plus;
      Collections.reverse(path);
      ArrayList<FrameAgent> gui = null;
      ArrayList<FrameAgent> _arrayList = new ArrayList<FrameAgent>();
      gui = _arrayList;
      gui.addAll(path);
      gui.remove(0);
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
      UpdateGUI _updateGUI = new UpdateGUI(gui, this.hostedNumTile);
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_updateGUI);
      Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER = this.$castSkill(Schedules.class, (this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES == null || this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES = this.$getSkill(Schedules.class)) : this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES);
      final Procedure1<Agent> _function = (Agent it) -> {
        this.iterateMovementChain2(path);
      };
      _xblockexpression = _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER.in(2000, _function);
    }
    return _xblockexpression;
  }
  
  protected void iterateMovementChain2(final ArrayList<FrameAgent> path) {
    UUID tempID = null;
    while ((path.size() >= 2)) {
      {
        tempID = path.get(0).hostedTile;
        FrameAgent _get = path.get(0);
        _get.hostedTile = path.get(1).hostedTile;
        FrameAgent _get_1 = path.get(1);
        _get_1.hostedTile = tempID;
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
        UUID _iD = this.getID();
        UpdateProblemInformations _updateProblemInformations = new UpdateProblemInformations(Boolean.valueOf(true), _iD);
        DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
        _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_updateProblemInformations, 
          Scopes.addresses(_$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.getDefaultSpace().getAddress(path.get(0).getID())));
        path.remove(0);
      }
    }
    tempID = path.get(0).hostedTile;
    FrameAgent _get = path.get(0);
    _get.hostedTile = this.hostedTile;
    this.hostedTile = tempID;
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
    UUID _iD = this.getID();
    UpdateProblemInformations _updateProblemInformations = new UpdateProblemInformations(Boolean.valueOf(true), _iD);
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_updateProblemInformations, Scopes.addresses(_$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.getDefaultSpace().getAddress(path.get(0).getID())));
    Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$castSkill(Behaviors.class, (this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS == null || this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS = this.$getSkill(Behaviors.class)) : this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS);
    UUID _iD_1 = this.getID();
    UpdateProblemInformations _updateProblemInformations_1 = new UpdateProblemInformations(Boolean.valueOf(true), _iD_1);
    _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.wake(_updateProblemInformations_1);
  }
  
  @SyntheticMember
  private void $behaviorUnit$UpdateProblemInformations$10(final UpdateProblemInformations occurrence) {
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
    UUID _iD = this.getID();
    UpdateProblemInformations _updateProblemInformations = new UpdateProblemInformations(this.idNum, _iD, occurrence.sendAckTo);
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_updateProblemInformations, Scopes.addresses(_$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.getDefaultSpace().getAddress(this.hostedTile)));
  }
  
  @SyntheticMember
  @Pure
  private boolean $behaviorUnitGuard$UpdateProblemInformations$10(final UpdateProblemInformations it, final UpdateProblemInformations occurrence) {
    return (it.start).booleanValue();
  }
  
  @SyntheticMember
  private void $behaviorUnit$UpdateProblemInformations$11(final UpdateProblemInformations occurrence) {
    this.hostedNumTile = occurrence.num;
    if ((this.hostedNumTile == this.idNum)) {
      this.isSatisfied = true;
    } else {
      this.isSatisfied = false;
    }
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
    AcknowledgmentDataUpdated _acknowledgmentDataUpdated = new AcknowledgmentDataUpdated();
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_acknowledgmentDataUpdated, Scopes.addresses(_$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.getDefaultSpace().getAddress(occurrence.sendAckTo)));
  }
  
  @SyntheticMember
  @Pure
  private boolean $behaviorUnitGuard$UpdateProblemInformations$11(final UpdateProblemInformations it, final UpdateProblemInformations occurrence) {
    return (!(it.start).booleanValue());
  }
  
  @SyntheticMember
  private void $behaviorUnit$AcknowledgmentDataUpdated$12(final AcknowledgmentDataUpdated occurrence) {
    this.nbAck--;
    if ((this.nbAck == 0)) {
      if (this.isSatisfied) {
        this.didMyTileMateHaveTheToken = true;
      }
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
      TokenReleased _tokenReleased = new TokenReleased();
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_tokenReleased);
    }
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
  
  @Pure
  protected int getIdNum() {
    return this.idNum;
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
  
  @Pure
  protected UUID getHostedTile() {
    return this.hostedTile;
  }
  
  protected void setHostedNumTile(final int num) {
    this.hostedNumTile = num;
  }
  
  @Pure
  protected int getHostedNumTile() {
    return this.hostedNumTile;
  }
  
  @SyntheticMember
  private void $behaviorUnit$EndAgent$13(final EndAgent occurrence) {
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
  private void $guardEvaluator$PathFound(final PathFound occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$PathFound$8(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ResponseNeighbourSatisfaction(final ResponseNeighbourSatisfaction occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ResponseNeighbourSatisfaction$4(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AcknowledgmentDataUpdated(final AcknowledgmentDataUpdated occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AcknowledgmentDataUpdated$12(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$Assault(final Assault occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    if ($behaviorUnitGuard$Assault$5(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Assault$5(occurrence));
    }
    if ($behaviorUnitGuard$Assault$6(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Assault$6(occurrence));
    }
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$PathCalculation(final PathCalculation occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$PathCalculation$7(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AskNeighbourSatisfaction(final AskNeighbourSatisfaction occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    if ($behaviorUnitGuard$AskNeighbourSatisfaction$2(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AskNeighbourSatisfaction$2(occurrence));
    }
    if ($behaviorUnitGuard$AskNeighbourSatisfaction$3(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AskNeighbourSatisfaction$3(occurrence));
    }
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$EndAgent(final EndAgent occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$EndAgent$13(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$PathCalculationTimeOut(final PathCalculationTimeOut occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$PathCalculationTimeOut$9(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$UpdateProblemInformations(final UpdateProblemInformations occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    if ($behaviorUnitGuard$UpdateProblemInformations$10(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$UpdateProblemInformations$10(occurrence));
    }
    if ($behaviorUnitGuard$UpdateProblemInformations$11(occurrence, occurrence)) {
      ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$UpdateProblemInformations$11(occurrence));
    }
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
    if (other.TIMEOUT != this.TIMEOUT)
      return false;
    if (other.previousPathCalculationId != this.previousPathCalculationId)
      return false;
    if (other.previousPathCalculationJumpValue != this.previousPathCalculationJumpValue)
      return false;
    if (other.previousPathDidMoveHappyTiles != this.previousPathDidMoveHappyTiles)
      return false;
    if (other.nbAck != this.nbAck)
      return false;
    if (other.isSatisfied != this.isSatisfied)
      return false;
    if (other.isBlocked != this.isBlocked)
      return false;
    if (other.didMyTileMateHaveTheToken != this.didMyTileMateHaveTheToken)
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
    if (other.hostedNumTile != this.hostedNumTile)
      return false;
    if (other.ready != this.ready)
      return false;
    if (other.dimension != this.dimension)
      return false;
    if (other.idNum != this.idNum)
      return false;
    if (other.idNumFromZero != this.idNumFromZero)
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
    result = prime * result + this.TIMEOUT;
    result = prime * result + (int) (this.previousPathCalculationId ^ (this.previousPathCalculationId >>> 32));
    result = prime * result + this.previousPathCalculationJumpValue;
    result = prime * result + (this.previousPathDidMoveHappyTiles ? 1231 : 1237);
    result = prime * result + this.nbAck;
    result = prime * result + (this.isSatisfied ? 1231 : 1237);
    result = prime * result + (this.isBlocked ? 1231 : 1237);
    result = prime * result + (this.didMyTileMateHaveTheToken ? 1231 : 1237);
    result = prime * result + this.nbNeighbours;
    result = prime * result + java.util.Objects.hashCode(this.northNeighbour);
    result = prime * result + java.util.Objects.hashCode(this.eastNeighbour);
    result = prime * result + java.util.Objects.hashCode(this.southNeighbour);
    result = prime * result + java.util.Objects.hashCode(this.westNeighbour);
    result = prime * result + java.util.Objects.hashCode(this.hostedTile);
    result = prime * result + this.hostedNumTile;
    result = prime * result + (this.ready ? 1231 : 1237);
    result = prime * result + this.dimension;
    result = prime * result + this.idNum;
    result = prime * result + this.idNumFromZero;
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
