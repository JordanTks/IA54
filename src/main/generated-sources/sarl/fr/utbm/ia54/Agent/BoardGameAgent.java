package fr.utbm.ia54.Agent;

import fr.utbm.ia54.Agent.EmptyTileAgent;
import fr.utbm.ia54.Agent.FrameAgent;
import fr.utbm.ia54.Agent.TileAgent;
import fr.utbm.ia54.Event.FrameSet;
import fr.utbm.ia54.Event.TileSet;
import fr.utbm.ia54.Event.TokenReceived;
import fr.utbm.ia54.Event.TokenReleased;
import io.sarl.core.AgentKilled;
import io.sarl.core.AgentSpawned;
import io.sarl.core.Behaviors;
import io.sarl.core.ContextJoined;
import io.sarl.core.ContextLeft;
import io.sarl.core.DefaultContextInteractions;
import io.sarl.core.Destroy;
import io.sarl.core.Initialize;
import io.sarl.core.Lifecycle;
import io.sarl.core.Logging;
import io.sarl.core.MemberJoined;
import io.sarl.core.MemberLeft;
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
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Inline;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(17)
@SuppressWarnings("all")
public class BoardGameAgent extends Agent {
  private final int PROBLEM_SIZE = 3;
  
  private int nbFrameSet = 0;
  
  private int nbTileSet = 0;
  
  private ArrayList<FrameAgent> frameList = new ArrayList<FrameAgent>();
  
  private ArrayList<TileAgent> tileList = new ArrayList<TileAgent>();
  
  private ArrayList<ArrayList<TileAgent>> tokenPriorityList = new ArrayList<ArrayList<TileAgent>>();
  
  @SyntheticMember
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.setLoggingName("BoardGameAgent");
    final List<Integer> frameList = new ArrayList<Integer>();
    for (int i = 1; (i <= Math.pow(this.PROBLEM_SIZE, 2)); i++) {
      frameList.add(Integer.valueOf(i));
    }
    for (int i = 0; (i <= (Math.pow(this.PROBLEM_SIZE, 2) - 1)); i++) {
      Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$castSkill(Lifecycle.class, (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = this.$getSkill(Lifecycle.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE);
      Integer _get = frameList.get(i);
      _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.spawn(FrameAgent.class, new Object[] { _get, Integer.valueOf((i / this.PROBLEM_SIZE)), Integer.valueOf((i % this.PROBLEM_SIZE)), Integer.valueOf(this.PROBLEM_SIZE) });
    }
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("Board game has been set.");
  }
  
  @SyntheticMember
  private void $behaviorUnit$FrameSet$1(final FrameSet occurrence) {
    this.nbFrameSet++;
    this.frameList.add(occurrence.frame);
    long _round = Math.round(Math.pow(this.PROBLEM_SIZE, 2));
    boolean _tripleEquals = (this.nbFrameSet == _round);
    if (_tripleEquals) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Every single FrameAgent is set. Spawning every single TileAgent.");
      final Comparator<FrameAgent> _function = (FrameAgent a, FrameAgent b) -> {
        int _numFrame = a.getNumFrame();
        int _numFrame_1 = b.getNumFrame();
        return (_numFrame - _numFrame_1);
      };
      Collections.<FrameAgent>sort(this.frameList, _function);
      int c = 0;
      for (final FrameAgent f : this.frameList) {
        {
          if (((c % this.PROBLEM_SIZE) != 0)) {
            f.setWestNeighbour(this.frameList.get((c - 1)).getID());
          }
          if (((c / this.PROBLEM_SIZE) != 0)) {
            f.setNorthNeighbour(this.frameList.get((c - this.PROBLEM_SIZE)).getID());
          }
          if (((c + this.PROBLEM_SIZE) < (this.PROBLEM_SIZE * this.PROBLEM_SIZE))) {
            f.setSouthNeighbour(this.frameList.get((c + this.PROBLEM_SIZE)).getID());
          }
          if (((c % this.PROBLEM_SIZE) != (this.PROBLEM_SIZE - 1))) {
            f.setEastNeighbour(this.frameList.get((c + 1)).getID());
          }
          c++;
        }
      }
      List<Integer> startingTiles = new ArrayList<Integer>();
      for (int i = 1; (i < Math.pow(this.PROBLEM_SIZE, 2)); i++) {
        startingTiles.add(Integer.valueOf(i));
      }
      Collections.shuffle(startingTiles);
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info(("Starting positions " + startingTiles));
      for (int i = 0; (i < (Math.pow(this.PROBLEM_SIZE, 2) - 1)); i++) {
        Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$castSkill(Lifecycle.class, (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = this.$getSkill(Lifecycle.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE);
        Integer _get = startingTiles.get(i);
        UUID _iD = this.frameList.get(i).getID();
        _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.spawn(TileAgent.class, new Object[] { _get, _iD, Integer.valueOf(i), Integer.valueOf(this.PROBLEM_SIZE) });
      }
      Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$castSkill(Lifecycle.class, (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = this.$getSkill(Lifecycle.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE);
      _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.spawn(EmptyTileAgent.class);
    }
  }
  
  @SyntheticMember
  private void $behaviorUnit$TileSet$2(final TileSet occurrence) {
    this.nbTileSet++;
    if ((occurrence.tile != null)) {
      this.tileList.add(occurrence.tile);
    }
    long _round = Math.round(Math.pow(this.PROBLEM_SIZE, 2));
    boolean _tripleEquals = (this.nbTileSet == _round);
    if (_tripleEquals) {
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Every single TileAgent is set. Setting token priority lists.");
      final Comparator<TileAgent> _function = (TileAgent a, TileAgent b) -> {
        int _numTile = a.getNumTile();
        int _numTile_1 = b.getNumTile();
        return (_numTile - _numTile_1);
      };
      Collections.<TileAgent>sort(this.tileList, _function);
      ArrayList<TileAgent> tempList = new ArrayList<TileAgent>();
      for (int i = 1; (i < this.PROBLEM_SIZE); i++) {
        ArrayList<TileAgent> _arrayList = new ArrayList<TileAgent>();
        this.tokenPriorityList.add(_arrayList);
      }
      for (final TileAgent t : this.tileList) {
        this.tokenPriorityList.get(t.getTokenPriority()).add(t);
      }
      for (final ArrayList<TileAgent> l : this.tokenPriorityList) {
        {
          for (int tilesToSwitch = ((l.size() / 2) + 1); (tilesToSwitch > 0); tilesToSwitch--) {
            {
              tempList.add(l.get(0));
              l.remove(0);
            }
          }
          while ((!tempList.isEmpty())) {
            {
              l.add(0, tempList.get(0));
              tempList.remove(0);
            }
          }
        }
      }
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("TokenPriorityList data set up.");
      Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$castSkill(Behaviors.class, (this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS == null || this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS = this.$getSkill(Behaviors.class)) : this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS);
      TokenReleased _tokenReleased = new TokenReleased();
      _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.wake(_tokenReleased);
    }
  }
  
  @SyntheticMember
  private void $behaviorUnit$TokenReleased$3(final TokenReleased occurrence) {
    do {
      {
        ArrayList<TileAgent> _get = this.tokenPriorityList.get(0);
        for (final TileAgent t : _get) {
          boolean _isHappy = t.getIsHappy();
          boolean _not = (!_isHappy);
          if (_not) {
            DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
            TokenReceived _tokenReceived = new TokenReceived();
            DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
            _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_tokenReceived, Scopes.addresses(_$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.getDefaultSpace().getAddress(t.getID())));
            return;
          }
        }
        this.tokenPriorityList.remove(0);
        boolean _isEmpty = this.tokenPriorityList.isEmpty();
        if (_isEmpty) {
          return;
        }
      }
    } while((!this.tokenPriorityList.isEmpty()));
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
  private void $guardEvaluator$TokenReleased(final TokenReleased occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$TokenReleased$3(occurrence));
  }
  
  /**
   * PROBABLEMENT DEJA OBSOLETE
   * 
   * on FrameSatisfied {
   * 
   * if (!occurrence.satisfied)
   * nbFrameSatisfied--
   * else
   * if (nbFrameSatisfied === Math.round(Math.pow(PROBLEM_SIZE, 2)) - 1)
   * {
   * info("Checking satisfaction of every Frame.") // in case of false positive due to synchronization issues
   * // todo
   * 
   * if (1 === 1)
   * {
   * info("Every Frame satisfied. Game completed.")
   * emit(new GameCompleted)
   * 
   * }
   * }
   * }
   */
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
  private void $guardEvaluator$FrameSet(final FrameSet occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$FrameSet$1(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$TileSet(final TileSet occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$TileSet$2(occurrence));
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
    BoardGameAgent other = (BoardGameAgent) obj;
    if (other.PROBLEM_SIZE != this.PROBLEM_SIZE)
      return false;
    if (other.nbFrameSet != this.nbFrameSet)
      return false;
    if (other.nbTileSet != this.nbTileSet)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + this.PROBLEM_SIZE;
    result = prime * result + this.nbFrameSet;
    result = prime * result + this.nbTileSet;
    return result;
  }
  
  @SyntheticMember
  public BoardGameAgent(final UUID parentID, final UUID agentID) {
    super(parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  @Deprecated
  public BoardGameAgent(final BuiltinCapacitiesProvider provider, final UUID parentID, final UUID agentID) {
    super(provider, parentID, agentID);
  }
  
  @SyntheticMember
  @Inject
  public BoardGameAgent(final UUID parentID, final UUID agentID, final DynamicSkillProvider skillProvider) {
    super(parentID, agentID, skillProvider);
  }
}
