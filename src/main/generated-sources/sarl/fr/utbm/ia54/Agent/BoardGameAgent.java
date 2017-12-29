package fr.utbm.ia54.Agent;

import com.google.common.base.Objects;
import fr.utbm.ia54.Agent.EmptyTileAgent;
import fr.utbm.ia54.Agent.FrameAgent;
import fr.utbm.ia54.Agent.TileAgent;
import fr.utbm.ia54.Class.CoordPair;
import fr.utbm.ia54.Class.InfosFrame;
import fr.utbm.ia54.Class.StaticVars;
import fr.utbm.ia54.Event.EndAgent;
import fr.utbm.ia54.Event.FindPathWithAstarAlgo;
import fr.utbm.ia54.Event.FrameSet;
import fr.utbm.ia54.Event.TileSet;
import fr.utbm.ia54.Event.TokenReceived;
import fr.utbm.ia54.Event.TokenReleased;
import fr.utbm.ia54.Event.UpdateGUI;
import fr.utbm.ia54.gui.TaquinFxViewerController;
import fr.utbm.taquin.events.ActionUI;
import io.sarl.core.AgentTask;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Inline;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(17)
@SuppressWarnings("all")
public class BoardGameAgent extends Agent {
  private int PROBLEM_SIZE = 3;
  
  private Integer nbFrameSet = new Integer(0);
  
  private Integer nbTileSet = new Integer(0);
  
  private ArrayList<FrameAgent> frameList = new ArrayList<FrameAgent>();
  
  private ArrayList<UUID> frameUUIDList = new ArrayList<UUID>();
  
  private ArrayList<CoordPair> listCoordPairsOfFrames = new ArrayList<CoordPair>();
  
  private ArrayList<InfosFrame> openListOfFrames = new ArrayList<InfosFrame>();
  
  private ArrayList<InfosFrame> closedListOfFrames = new ArrayList<InfosFrame>();
  
  private FrameAgent haveTokenFrame;
  
  private InfosFrame haveTokenInfosFrame;
  
  private UUID uuidFRAMEwithTokenTile;
  
  private CoordPair coordsFRAMEwithTokenTile;
  
  private FrameAgent arrivalFrame;
  
  private InfosFrame arrivalInfosFrame;
  
  private UUID uuidFRAMEwithBlankTile;
  
  private CoordPair coordsFRAMEwithBlankTile;
  
  private FrameAgent beginningFrame;
  
  private InfosFrame beginningInfosFrame;
  
  private UUID uuidFRAMEbeginning;
  
  private CoordPair coordsFRAMEbeginning;
  
  private EmptyTileAgent blankTile;
  
  private ArrayList<TileAgent> tileList = new ArrayList<TileAgent>();
  
  private ArrayList<ArrayList<TileAgent>> tokenPriorityList = new ArrayList<ArrayList<TileAgent>>();
  
  private TaquinFxViewerController ctrl;
  
  private int flagCorner = 0;
  
  private boolean isCorner = false;
  
  private boolean isDistributed = false;
  
  private int nbTriesAstarAlgo = 0;
  
  private ArrayList<Integer> topCornersList = new ArrayList<Integer>();
  
  private ArrayList<Integer> bottomCornersList = new ArrayList<Integer>();
  
  @SyntheticMember
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.setLoggingName("BoardGameAgent");
    this.PROBLEM_SIZE = StaticVars.problemSize;
    Object _get = occurrence.parameters[1];
    this.ctrl = ((TaquinFxViewerController) _get);
    this.isDistributed = StaticVars.isDistributed;
    final List<Integer> numFrameList = new ArrayList<Integer>();
    for (int i = 1; (i <= Math.pow(this.PROBLEM_SIZE, 2)); i++) {
      numFrameList.add(Integer.valueOf(i));
    }
    for (int i = 0; (i <= (Math.pow(this.PROBLEM_SIZE, 2) - 1)); i++) {
      {
        Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$castSkill(Lifecycle.class, (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = this.$getSkill(Lifecycle.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE);
        Integer _get_1 = numFrameList.get(i);
        this.frameUUIDList.add(_$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.spawn(FrameAgent.class, new Object[] { _get_1, Integer.valueOf((i / this.PROBLEM_SIZE)), Integer.valueOf((i % this.PROBLEM_SIZE)) }));
        CoordPair _coordPair = new CoordPair((i / this.PROBLEM_SIZE), (i % this.PROBLEM_SIZE));
        this.listCoordPairsOfFrames.add(_coordPair);
      }
    }
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("Board game has been set.");
  }
  
  @SyntheticMember
  private void $behaviorUnit$FrameSet$1(final FrameSet occurrence) {
    synchronized (this.nbFrameSet) {
      this.nbFrameSet++;
      this.frameList.add(occurrence.frame);
      long _round = Math.round(Math.pow(this.PROBLEM_SIZE, 2));
      boolean _tripleEquals = ((this.nbFrameSet).intValue() == _round);
      if (_tripleEquals) {
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Every single FrameAgent is set. Spawning every single TileAgent.");
        for (final FrameAgent f : this.frameList) {
          if ((f == null)) {
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.error("ERROR 1 : ONE OF THE FRAME RETURNED NULL POINTER");
            return;
          }
        }
        final Comparator<FrameAgent> _function = (FrameAgent a, FrameAgent b) -> {
          int _numFrame = a.getNumFrame();
          int _numFrame_1 = b.getNumFrame();
          return (_numFrame - _numFrame_1);
        };
        Collections.<FrameAgent>sort(this.frameList, _function);
        int c = 0;
        for (final FrameAgent f_1 : this.frameList) {
          {
            if (((c % this.PROBLEM_SIZE) != 0)) {
              f_1.setWestNeighbour(this.frameList.get((c - 1)).getID());
            }
            if (((c / this.PROBLEM_SIZE) != 0)) {
              f_1.setNorthNeighbour(this.frameList.get((c - this.PROBLEM_SIZE)).getID());
            }
            if (((c + this.PROBLEM_SIZE) < (this.PROBLEM_SIZE * this.PROBLEM_SIZE))) {
              f_1.setSouthNeighbour(this.frameList.get((c + this.PROBLEM_SIZE)).getID());
            }
            if (((c % this.PROBLEM_SIZE) != (this.PROBLEM_SIZE - 1))) {
              f_1.setEastNeighbour(this.frameList.get((c + 1)).getID());
            }
            c++;
          }
        }
        List<Integer> startingTiles = new ArrayList<Integer>();
        for (int i = 1; (i < Math.pow(this.PROBLEM_SIZE, 2)); i++) {
          startingTiles.add(Integer.valueOf(i));
        }
        Collections.shuffle(startingTiles);
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info(("Starting positions " + startingTiles));
        this.ctrl.monTest(startingTiles);
        for (int i = 0; (i < (Math.pow(this.PROBLEM_SIZE, 2) - 1)); i++) {
          {
            Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$castSkill(Lifecycle.class, (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = this.$getSkill(Lifecycle.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE);
            Integer _get = startingTiles.get(i);
            UUID _iD = this.frameList.get(i).getID();
            UUID id = _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.spawn(TileAgent.class, new Object[] { _get, _iD, Integer.valueOf(i) });
            FrameAgent _get_1 = this.frameList.get(i);
            _get_1.setHostedTile(id);
            FrameAgent _get_2 = this.frameList.get(i);
            _get_2.setHostedNumTile((startingTiles.get(i)).intValue());
            FrameAgent _get_3 = this.frameList.get(i);
            Integer _get_4 = startingTiles.get(i);
            int _numFrame = this.frameList.get(i).getNumFrame();
            boolean _tripleEquals_1 = ((_get_4).intValue() == _numFrame);
            _get_3.setIsSatisfied(_tripleEquals_1);
          }
        }
        Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$castSkill(Lifecycle.class, (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = this.$getSkill(Lifecycle.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE);
        UUID _iD = this.frameList.get(((this.PROBLEM_SIZE * this.PROBLEM_SIZE) - 1)).getID();
        UUID id = _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.spawn(EmptyTileAgent.class, new Object[] { _iD, Integer.valueOf((this.PROBLEM_SIZE * this.PROBLEM_SIZE)) });
        FrameAgent _get = this.frameList.get(((this.PROBLEM_SIZE * this.PROBLEM_SIZE) - 1));
        _get.setHostedTile(id);
        this.uuidFRAMEwithBlankTile = this.frameList.get(((this.PROBLEM_SIZE * this.PROBLEM_SIZE) - 1)).getID();
        this.coordsFRAMEwithBlankTile = this.frameList.get(((this.PROBLEM_SIZE * this.PROBLEM_SIZE) - 1)).getCoordPair();
        InfosFrame _infosFrame = new InfosFrame(this.uuidFRAMEwithBlankTile, this.coordsFRAMEwithBlankTile);
        this.arrivalInfosFrame = _infosFrame;
        this.arrivalFrame = this.frameList.get(((this.PROBLEM_SIZE * this.PROBLEM_SIZE) - 1));
        this.arrivalFrame.setHostedNumTile(0);
        for (int top = 1; (top < ((this.PROBLEM_SIZE * this.PROBLEM_SIZE) - (this.PROBLEM_SIZE * 2))); top = ((top + this.PROBLEM_SIZE) + 1)) {
          this.topCornersList.add(Integer.valueOf(top));
        }
        for (int bottom = ((this.PROBLEM_SIZE * this.PROBLEM_SIZE) - (this.PROBLEM_SIZE - 1)); (bottom < ((this.PROBLEM_SIZE * this.PROBLEM_SIZE) - 1)); bottom++) {
          this.bottomCornersList.add(Integer.valueOf(bottom));
        }
      }
    }
  }
  
  @SyntheticMember
  private void $behaviorUnit$TileSet$2(final TileSet occurrence) {
    synchronized (this.nbTileSet) {
      this.nbTileSet++;
      if ((occurrence.tile != null)) {
        this.tileList.add(occurrence.tile);
      } else {
        this.blankTile = occurrence.blank;
      }
      long _round = Math.round(Math.pow(this.PROBLEM_SIZE, 2));
      boolean _tripleEquals = ((this.nbTileSet).intValue() == _round);
      if (_tripleEquals) {
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("Every single TileAgent is set. Setting token priority lists.");
        for (final TileAgent t : this.tileList) {
          if ((t == null)) {
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.error("ERROR 2 : ONE OF THE TILE RETURNED NULL POINTER");
            return;
          }
        }
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
        for (final TileAgent t_1 : this.tileList) {
          this.tokenPriorityList.get(t_1.getTokenPriority()).add(t_1);
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
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info("TokenPriorityList data set up.");
        Behaviors _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER = this.$castSkill(Behaviors.class, (this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS == null || this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS = this.$getSkill(Behaviors.class)) : this.$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS);
        TokenReleased _tokenReleased = new TokenReleased();
        _$CAPACITY_USE$IO_SARL_CORE_BEHAVIORS$CALLER.wake(_tokenReleased);
      }
    }
  }
  
  @SyntheticMember
  private void $behaviorUnit$TokenReleased$3(final TokenReleased occurrence) {
    if ((!occurrence.success)) {
      int _timeout = StaticVars.timeout;
      StaticVars.timeout = (_timeout * 2);
    } else {
      StaticVars.timeout = ((int) (0.9 * StaticVars.timeout));
      int _timeout_1 = StaticVars.timeout;
      StaticVars.timeout = (_timeout_1 + 1);
    }
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(("Current timeout : " + Integer.valueOf(StaticVars.timeout)));
    ArrayList<TileAgent> _get = this.tokenPriorityList.get(0);
    for (final TileAgent t : _get) {
      boolean _isHappy = t.getIsHappy();
      if (_isHappy) {
        for (final FrameAgent ite : this.frameList) {
          UUID _uuidFrameHost = t.getUuidFrameHost();
          UUID _iD = ite.getID();
          boolean _tripleEquals = (_uuidFrameHost == _iD);
          if (_tripleEquals) {
            ite.setDidMyTileMateHaveTheToken(true);
            this.ctrl.setColor("purple", Integer.valueOf(t.getNumTile()));
            break;
          }
        }
      } else {
        break;
      }
    }
    do {
      {
        ArrayList<TileAgent> _get_1 = this.tokenPriorityList.get(0);
        for (final TileAgent t_1 : _get_1) {
          boolean _isHappy_1 = t_1.getIsHappy();
          boolean _not = (!_isHappy_1);
          if (_not) {
            for (int i = 0; (i < this.frameList.size()); i++) {
              UUID _hostedTile = this.frameList.get(i).getHostedTile();
              UUID _iD_1 = t_1.getID();
              boolean _tripleEquals_1 = (_hostedTile == _iD_1);
              if (_tripleEquals_1) {
                this.uuidFRAMEwithTokenTile = this.frameList.get(i).getID();
                this.coordsFRAMEwithTokenTile = this.frameList.get(i).getCoordPair();
                InfosFrame _infosFrame = new InfosFrame(this.uuidFRAMEwithTokenTile, this.coordsFRAMEwithTokenTile);
                this.haveTokenInfosFrame = _infosFrame;
                this.haveTokenFrame = this.frameList.get(i);
                break;
              }
            }
            DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
            TokenReceived _tokenReceived = new TokenReceived();
            DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
            _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_tokenReceived, Scopes.addresses(_$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.getDefaultSpace().getAddress(t_1.getID())));
            return;
          }
        }
        ArrayList<TileAgent> _get_2 = this.tokenPriorityList.get(0);
        for (final TileAgent t_2 : _get_2) {
          for (final FrameAgent f : this.frameList) {
            int _numTile = t_2.getNumTile();
            int _idNum = f.getIdNum();
            boolean _tripleEquals_1 = (_numTile == _idNum);
            if (_tripleEquals_1) {
              f.setIsBlocked(true);
              this.ctrl.setColor("orange", Integer.valueOf(t_2.getNumTile()));
              break;
            }
          }
        }
        this.tokenPriorityList.remove(0);
        boolean _isEmpty = this.tokenPriorityList.isEmpty();
        if (_isEmpty) {
          Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
          _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.error("PUZZLE SEEMS TO BE SOLVED ! Congratulations, you win!");
          return;
        }
        int _size = this.tokenPriorityList.get(0).size();
        int _divide = (_size / 2);
        int firstCorner = this.tokenPriorityList.get(0).get(_divide).getNumTile();
        int _size_1 = this.tokenPriorityList.get(0).size();
        int _minus = (_size_1 - 1);
        int secondCorner = this.tokenPriorityList.get(0).get(_minus).getNumTile();
        for (final FrameAgent f_1 : this.frameList) {
          {
            int _idNum_1 = f_1.getIdNum();
            boolean _tripleEquals_2 = (_idNum_1 == firstCorner);
            if (_tripleEquals_2) {
              f_1.setNbActiveNeighbours(2);
            }
            int _idNum_2 = f_1.getIdNum();
            boolean _tripleEquals_3 = (_idNum_2 == secondCorner);
            if (_tripleEquals_3) {
              f_1.setNbActiveNeighbours(2);
            }
          }
        }
      }
    } while((!this.tokenPriorityList.isEmpty()));
  }
  
  @SyntheticMember
  private void $behaviorUnit$ActionUI$4(final ActionUI occurrence) {
    this.ctrl.setColor(occurrence.prop, Integer.valueOf(occurrence.target));
  }
  
  @Pure
  protected int distManhattan(final FrameAgent a, final FrameAgent b) {
    int _xRow = a.getXRow();
    int _xRow_1 = b.getXRow();
    int _minus = (_xRow - _xRow_1);
    int _abs = Math.abs(_minus);
    int _yCol = a.getYCol();
    int _yCol_1 = b.getYCol();
    int _minus_1 = (_yCol - _yCol_1);
    int _abs_1 = Math.abs(_minus_1);
    return (_abs + _abs_1);
  }
  
  @Pure
  protected boolean isInFrameList(final CoordPair pair, final ArrayList<InfosFrame> listOfFrames) {
    for (final InfosFrame fa : listOfFrames) {
      boolean _equals = pair.equals(fa.getCoordsCurrentFrame());
      if (_equals) {
        return true;
      }
    }
    return false;
  }
  
  protected Object addOneNeighbourFrameImplements(final FrameAgent currentFrame, final UUID neighbour) {
    Object _xblockexpression = null;
    {
      int index = this.frameUUIDList.indexOf(neighbour);
      CoordPair coordNeighbour = this.listCoordPairsOfFrames.get(index);
      Object _xifexpression = null;
      boolean _isInFrameList = this.isInFrameList(coordNeighbour, this.closedListOfFrames);
      boolean _not = (!_isInFrameList);
      if (_not) {
        Object _xblockexpression_1 = null;
        {
          InfosFrame infosFrame = new InfosFrame(neighbour, coordNeighbour);
          int indexForClosedList = (-1);
          for (final InfosFrame iterator : this.closedListOfFrames) {
            {
              indexForClosedList++;
              UUID _uuidCurrentFrame = iterator.getUuidCurrentFrame();
              UUID _iD = currentFrame.getID();
              boolean _equals = Objects.equal(_uuidCurrentFrame, _iD);
              if (_equals) {
                break;
              }
            }
          }
          int _costG = this.closedListOfFrames.get(indexForClosedList).getCostG();
          int _distManhattan = this.distManhattan(this.frameList.get(index), currentFrame);
          int _plus = (_costG + _distManhattan);
          infosFrame.setCostG(_plus);
          infosFrame.setCostH(this.distManhattan(this.frameList.get(index), this.arrivalFrame));
          int _costG_1 = infosFrame.getCostG();
          int _costH = infosFrame.getCostH();
          int _plus_1 = (_costG_1 + _costH);
          infosFrame.setCostF(_plus_1);
          infosFrame.setUuidPreviousFrame(currentFrame.getID());
          infosFrame.setCoordsPreviousFrame(currentFrame.getCoordPair());
          int indexForOpenList = (-1);
          boolean _isEmpty = this.openListOfFrames.isEmpty();
          boolean _not_1 = (!_isEmpty);
          if (_not_1) {
            for (final InfosFrame iterator_1 : this.openListOfFrames) {
              {
                indexForOpenList++;
                UUID _uuidCurrentFrame = iterator_1.getUuidCurrentFrame();
                boolean _equals = Objects.equal(_uuidCurrentFrame, neighbour);
                if (_equals) {
                  break;
                }
              }
            }
          }
          Object _xifexpression_1 = null;
          boolean _isInFrameList_1 = this.isInFrameList(coordNeighbour, this.openListOfFrames);
          if (_isInFrameList_1) {
            InfosFrame _xifexpression_2 = null;
            if (((indexForOpenList > (-1)) && (infosFrame.getCostF() < this.openListOfFrames.get(indexForOpenList).getCostF()))) {
              _xifexpression_2 = this.openListOfFrames.set(indexForOpenList, infosFrame);
            }
            _xifexpression_1 = _xifexpression_2;
          } else {
            _xifexpression_1 = Boolean.valueOf(this.openListOfFrames.add(infosFrame));
          }
          _xblockexpression_1 = _xifexpression_1;
        }
        _xifexpression = _xblockexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  protected Object addOneNeighbourFrame(final FrameAgent currentFrame, final UUID neighbour) {
    Object _xblockexpression = null;
    {
      int index = this.frameUUIDList.indexOf(neighbour);
      Object _xifexpression = null;
      if (((((this.flagCorner == 0) && ((!this.frameList.get(index).getDidMyTileMateHaveTheToken()) || 
        (!this.frameList.get(index).getIsSatisfied()))) && 
        (!this.frameList.get(index).getIsBlocked())) && (!Objects.equal(this.frameList.get(index).getID(), this.uuidFRAMEwithTokenTile)))) {
        _xifexpression = this.addOneNeighbourFrameImplements(currentFrame, neighbour);
      } else {
        Object _xifexpression_1 = null;
        if (((((this.flagCorner == 1) && 
          (!this.frameList.get(index).getIsSatisfied())) && 
          (!this.frameList.get(index).getIsBlocked())) && (!Objects.equal(this.frameList.get(index).getID(), this.uuidFRAMEwithTokenTile)))) {
          _xifexpression_1 = this.addOneNeighbourFrameImplements(currentFrame, neighbour);
        } else {
          Object _xifexpression_2 = null;
          if (((((this.flagCorner == 2) && 
            (!this.frameList.get(index).getDidMyTileMateHaveTheToken())) && 
            (!this.frameList.get(index).getIsBlocked())) && (!Objects.equal(this.frameList.get(index).getID(), this.uuidFRAMEwithTokenTile)))) {
            _xifexpression_2 = this.addOneNeighbourFrameImplements(currentFrame, neighbour);
          }
          _xifexpression_1 = _xifexpression_2;
        }
        _xifexpression = _xifexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  protected Object addNeighbourFrames(final FrameAgent currentFrame) {
    Object _xblockexpression = null;
    {
      UUID _southNeighbour = currentFrame.getSouthNeighbour();
      boolean _notEquals = (!Objects.equal(_southNeighbour, null));
      if (_notEquals) {
        this.addOneNeighbourFrame(currentFrame, currentFrame.getSouthNeighbour());
      }
      UUID _northNeighbour = currentFrame.getNorthNeighbour();
      boolean _notEquals_1 = (!Objects.equal(_northNeighbour, null));
      if (_notEquals_1) {
        this.addOneNeighbourFrame(currentFrame, currentFrame.getNorthNeighbour());
      }
      UUID _westNeighbour = currentFrame.getWestNeighbour();
      boolean _notEquals_2 = (!Objects.equal(_westNeighbour, null));
      if (_notEquals_2) {
        this.addOneNeighbourFrame(currentFrame, currentFrame.getWestNeighbour());
      }
      Object _xifexpression = null;
      UUID _eastNeighbour = currentFrame.getEastNeighbour();
      boolean _notEquals_3 = (!Objects.equal(_eastNeighbour, null));
      if (_notEquals_3) {
        _xifexpression = this.addOneNeighbourFrame(currentFrame, currentFrame.getEastNeighbour());
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  @Pure
  protected InfosFrame getBestFrame(final ArrayList<InfosFrame> listFrames) {
    boolean _isEmpty = listFrames.isEmpty();
    boolean _not = (!_isEmpty);
    if (_not) {
      int costF = listFrames.get(0).getCostF();
      InfosFrame infoFrame = listFrames.get(0);
      for (final InfosFrame frame : listFrames) {
        int _costF = frame.getCostF();
        boolean _lessThan = (_costF < costF);
        if (_lessThan) {
          costF = frame.getCostF();
          infoFrame = frame;
        }
      }
      return infoFrame;
    }
    return null;
  }
  
  @Pure
  protected InfosFrame getBestFrameForCorner(final ArrayList<InfosFrame> listFrames) {
    boolean _isEmpty = listFrames.isEmpty();
    boolean _not = (!_isEmpty);
    if (_not) {
      int costF = listFrames.get(0).getCostF();
      InfosFrame infoFrame = listFrames.get(0);
      for (final InfosFrame frame : listFrames) {
        {
          for (final FrameAgent l : this.frameList) {
            UUID _iD = l.getID();
            UUID _uuidCurrentFrame = frame.getUuidCurrentFrame();
            boolean _tripleEquals = (_iD == _uuidCurrentFrame);
            if (_tripleEquals) {
              boolean _isBlocked = l.getIsBlocked();
              if (_isBlocked) {
                return frame;
              }
            }
          }
          int _costF = frame.getCostF();
          boolean _lessThan = (_costF < costF);
          if (_lessThan) {
            costF = frame.getCostF();
            infoFrame = frame;
          }
        }
      }
      return infoFrame;
    }
    return null;
  }
  
  protected InfosFrame addInClosedList(final CoordPair coords) {
    InfosFrame _xblockexpression = null;
    {
      int index = (-1);
      for (final InfosFrame ite : this.openListOfFrames) {
        {
          index++;
          boolean _equals = ite.getCoordsCurrentFrame().equals(coords);
          if (_equals) {
            this.closedListOfFrames.add(ite);
            break;
          }
        }
      }
      _xblockexpression = this.openListOfFrames.remove(index);
    }
    return _xblockexpression;
  }
  
  protected ArrayList<InfosFrame> buildChainAggression(final InfosFrame beginningFrame) {
    ArrayList<InfosFrame> chain = new ArrayList<InfosFrame>();
    int _size = this.closedListOfFrames.size();
    int index = (_size - 1);
    InfosFrame tmpFrame = null;
    InfosFrame prevFrame = null;
    boolean flagEnd = false;
    tmpFrame = this.closedListOfFrames.get(index);
    chain.add(tmpFrame);
    UUID _uuidPreviousFrame = tmpFrame.getUuidPreviousFrame();
    CoordPair _coordsPreviousFrame = tmpFrame.getCoordsPreviousFrame();
    InfosFrame _infosFrame = new InfosFrame(_uuidPreviousFrame, _coordsPreviousFrame);
    prevFrame = _infosFrame;
    while ((!prevFrame.equals(beginningFrame))) {
      {
        chain.add(prevFrame);
        int i = (-1);
        for (final InfosFrame ite : this.closedListOfFrames) {
          {
            CoordPair _coordsPreviousFrame_1 = tmpFrame.getCoordsPreviousFrame();
            boolean _equals = Objects.equal(_coordsPreviousFrame_1, null);
            if (_equals) {
              flagEnd = true;
              break;
            }
            i++;
            boolean _equals_1 = ite.getCoordsCurrentFrame().equals(tmpFrame.getCoordsPreviousFrame());
            if (_equals_1) {
              break;
            }
          }
        }
        if ((!flagEnd)) {
          tmpFrame = this.closedListOfFrames.get(i);
          UUID _uuidCurrentFrame = this.closedListOfFrames.get(i).getUuidCurrentFrame();
          CoordPair _coordsCurrentFrame = this.closedListOfFrames.get(i).getCoordsCurrentFrame();
          InfosFrame _infosFrame_1 = new InfosFrame(_uuidCurrentFrame, _coordsCurrentFrame);
          prevFrame = _infosFrame_1;
        } else {
          break;
        }
      }
    }
    return chain;
  }
  
  protected void changeSatisfaction() {
    for (int i = 0; (i < (this.frameList.size() - 1)); i++) {
      int _idNum = this.frameList.get(i).getIdNum();
      int _hostedNumTile = this.frameList.get(i).getHostedNumTile();
      boolean _tripleEquals = (_idNum == _hostedNumTile);
      if (_tripleEquals) {
        FrameAgent _get = this.frameList.get(i);
        _get.setIsSatisfied(true);
      } else {
        FrameAgent _get_1 = this.frameList.get(i);
        _get_1.setIsSatisfied(false);
      }
    }
    for (final TileAgent iteTile : this.tileList) {
      int _numTile = iteTile.getNumTile();
      int _numFrameHost = iteTile.getNumFrameHost();
      boolean _tripleEquals = (_numTile == _numFrameHost);
      if (_tripleEquals) {
        iteTile.setIsHappy(true);
      } else {
        iteTile.setIsHappy(false);
      }
    }
  }
  
  protected Object findPath() {
    try {
      AgentTask _xblockexpression = null;
      {
        InfosFrame currentFrame = this.beginningInfosFrame;
        this.openListOfFrames.add(this.beginningInfosFrame);
        this.addInClosedList(this.beginningInfosFrame.getCoordsCurrentFrame());
        for (int indexOfFrameList = 0; (indexOfFrameList < this.frameList.size()); indexOfFrameList++) {
          boolean _equals = this.frameList.get(indexOfFrameList).getCoordPair().equals(this.beginningInfosFrame.getCoordsCurrentFrame());
          if (_equals) {
            this.addNeighbourFrames(this.frameList.get(indexOfFrameList));
            break;
          }
        }
        while (((!currentFrame.getCoordsCurrentFrame().equals(this.arrivalInfosFrame.getCoordsCurrentFrame())) && (!this.openListOfFrames.isEmpty()))) {
          {
            currentFrame = this.getBestFrame(this.openListOfFrames);
            this.addInClosedList(currentFrame.getCoordsCurrentFrame());
            for (int indexOfFrameList2 = 0; (indexOfFrameList2 < this.frameList.size()); indexOfFrameList2++) {
              boolean _equals = this.frameList.get(indexOfFrameList2).getCoordPair().equals(currentFrame.getCoordsCurrentFrame());
              if (_equals) {
                this.addNeighbourFrames(this.frameList.get(indexOfFrameList2));
                break;
              }
            }
          }
        }
        AgentTask _xifexpression = null;
        if (((!this.isCorner) && (this.flagCorner < 2))) {
          AgentTask _xifexpression_1 = null;
          boolean _equals = currentFrame.getCoordsCurrentFrame().equals(this.arrivalInfosFrame.getCoordsCurrentFrame());
          if (_equals) {
            this.nbTriesAstarAlgo = 0;
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("## A-star findPath : destination IS reached! ##");
            this.doSwaps(null);
            this.flagCorner = 0;
            DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
            TokenReleased _tokenReleased = new TokenReleased();
            _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_tokenReleased);
          } else {
            AgentTask _xblockexpression_1 = null;
            {
              this.nbTriesAstarAlgo++;
              Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
              _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info((("## A-star findPath : destination IS NOT reached! (" + Integer.valueOf(this.nbTriesAstarAlgo)) + ") ##"));
              AgentTask _xifexpression_2 = null;
              if ((this.nbTriesAstarAlgo < 2)) {
                for (final FrameAgent ite : this.frameList) {
                  UUID _iD = ite.getID();
                  UUID _uuidCurrentFrame = currentFrame.getUuidCurrentFrame();
                  boolean _tripleEquals = (_iD == _uuidCurrentFrame);
                  if (_tripleEquals) {
                    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
                    int _hostedNumTile = ite.getHostedNumTile();
                    String _plus = ("CURRENTtile=" + Integer.valueOf(_hostedNumTile));
                    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.error(_plus);
                    this.ctrl.setColor("turquoise", Integer.valueOf(ite.getHostedNumTile()));
                    Thread.sleep(StaticVars.speed);
                    break;
                  }
                }
                DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
                TokenReleased _tokenReleased_1 = new TokenReleased();
                _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.emit(_tokenReleased_1);
              } else {
                _xifexpression_2 = this.doSwapsForCorner(currentFrame);
              }
              _xblockexpression_1 = _xifexpression_2;
            }
            _xifexpression_1 = _xblockexpression_1;
          }
          _xifexpression = _xifexpression_1;
        } else {
          if (((!this.isCorner) && (this.flagCorner == 2))) {
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("## A-star findPath : top right corner of 3x3 ##");
            this.doSwapsForTopRightCornerThreeByThree();
          }
        }
        _xblockexpression = _xifexpression;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @Pure
  protected int whichCorner(final InfosFrame currentFrame) {
    boolean _contains = this.topCornersList.contains(Integer.valueOf(currentFrame.getNumFrame()));
    if (_contains) {
      return 1;
    } else {
      boolean _contains_1 = this.bottomCornersList.contains(Integer.valueOf(currentFrame.getNumFrame()));
      if (_contains_1) {
        int _size = this.bottomCornersList.size();
        int _minus = (_size - 1);
        Integer _get = this.bottomCornersList.get(_minus);
        int _numFrame = currentFrame.getNumFrame();
        boolean _equals = ((_get).intValue() == _numFrame);
        if (_equals) {
          return 3;
        } else {
          return 2;
        }
      }
    }
    return 0;
  }
  
  protected ArrayList<InfosFrame> buildChainAggressionForCorner(final InfosFrame currentInfosFrame, final InfosFrame firstNeighbourInfosFrame, final InfosFrame secondNeighbourInfosFrame, final InfosFrame thirdNeighbourInfosFrame) {
    ArrayList<InfosFrame> tmpChainAggression = this.buildChainAggression(this.beginningInfosFrame);
    int _size = tmpChainAggression.size();
    int _minus = (_size - 1);
    InfosFrame tmpFrame = tmpChainAggression.get(_minus);
    ArrayList<InfosFrame> chainAggression = new ArrayList<InfosFrame>();
    chainAggression.add(tmpFrame);
    for (int i = (tmpChainAggression.size() - 2); (i >= 0); i--) {
      UUID _uuidCurrentFrame = tmpFrame.getUuidCurrentFrame();
      UUID _uuidCurrentFrame_1 = tmpChainAggression.get(i).getUuidCurrentFrame();
      boolean _tripleEquals = (_uuidCurrentFrame == _uuidCurrentFrame_1);
      boolean _not = (!_tripleEquals);
      if (_not) {
        tmpFrame = tmpChainAggression.get(i);
        chainAggression.add(tmpFrame);
      }
    }
    boolean _notEquals = (!Objects.equal(thirdNeighbourInfosFrame, null));
    if (_notEquals) {
      chainAggression.add(0, thirdNeighbourInfosFrame);
    }
    boolean _notEquals_1 = (!Objects.equal(secondNeighbourInfosFrame, null));
    if (_notEquals_1) {
      chainAggression.add(0, secondNeighbourInfosFrame);
    }
    chainAggression.add(0, firstNeighbourInfosFrame);
    chainAggression.add(0, currentInfosFrame);
    return chainAggression;
  }
  
  protected void doSwapsForTopRightCornerThreeByThree() {
    FrameAgent currentFrame = null;
    for (final FrameAgent ite : this.frameList) {
      int _idNum = ite.getIdNum();
      boolean _tripleEquals = (_idNum == ((this.PROBLEM_SIZE * this.PROBLEM_SIZE) - (this.PROBLEM_SIZE * 2)));
      if (_tripleEquals) {
        currentFrame = ite;
        break;
      }
    }
    UUID _iD = currentFrame.getID();
    CoordPair _coordPair = currentFrame.getCoordPair();
    InfosFrame currentInfosFrame = new InfosFrame(_iD, _coordPair);
    currentInfosFrame.setNumFrame(currentFrame.getIdNum());
    UUID firstNeighbourUUID = currentFrame.getWestNeighbour();
    int _x = currentFrame.getCoordPair().getX();
    int _y = currentFrame.getCoordPair().getY();
    CoordPair _coordPair_1 = new CoordPair(_x, _y);
    InfosFrame firstNeighbourInfosFrame = new InfosFrame(firstNeighbourUUID, _coordPair_1);
    firstNeighbourInfosFrame.getCoordsCurrentFrame().setYminusOne();
    FrameAgent firstNeighbourFrame = null;
    for (final FrameAgent ite_1 : this.frameList) {
      UUID _iD_1 = ite_1.getID();
      boolean _tripleEquals_1 = (_iD_1 == firstNeighbourUUID);
      if (_tripleEquals_1) {
        firstNeighbourFrame = ite_1;
        break;
      }
    }
    UUID secondNeighbourUUID = firstNeighbourFrame.getWestNeighbour();
    int _x_1 = firstNeighbourFrame.getCoordPair().getX();
    int _y_1 = firstNeighbourFrame.getCoordPair().getY();
    CoordPair _coordPair_2 = new CoordPair(_x_1, _y_1);
    InfosFrame secondNeighbourInfosFrame = new InfosFrame(secondNeighbourUUID, _coordPair_2);
    secondNeighbourInfosFrame.getCoordsCurrentFrame().setYminusOne();
    FrameAgent secondNeighbourFrame = null;
    for (final FrameAgent ite_2 : this.frameList) {
      UUID _iD_2 = ite_2.getID();
      boolean _tripleEquals_2 = (_iD_2 == secondNeighbourUUID);
      if (_tripleEquals_2) {
        secondNeighbourFrame = ite_2;
        break;
      }
    }
    UUID thirdNeighbourUUID = secondNeighbourFrame.getSouthNeighbour();
    int _x_2 = secondNeighbourFrame.getCoordPair().getX();
    int _y_2 = secondNeighbourFrame.getCoordPair().getY();
    CoordPair _coordPair_3 = new CoordPair(_x_2, _y_2);
    InfosFrame thirdNeighbourInfosFrame = new InfosFrame(thirdNeighbourUUID, _coordPair_3);
    thirdNeighbourInfosFrame.getCoordsCurrentFrame().setXplusOne();
    FrameAgent thirdNeighbourFrame = null;
    for (final FrameAgent ite_3 : this.frameList) {
      UUID _iD_3 = ite_3.getID();
      boolean _tripleEquals_3 = (_iD_3 == thirdNeighbourUUID);
      if (_tripleEquals_3) {
        thirdNeighbourFrame = ite_3;
        break;
      }
    }
    this.isCorner = true;
    this.initBeginningInfosFrame(thirdNeighbourFrame.getIdNum(), thirdNeighbourUUID, thirdNeighbourFrame.getCoordPair());
    this.findPath();
    ArrayList<InfosFrame> newChainAggression = this.buildChainAggressionForCorner(currentInfosFrame, firstNeighbourInfosFrame, secondNeighbourInfosFrame, null);
    this.doSwaps(newChainAggression);
    this.flagCorner = 0;
    DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
    TokenReleased _tokenReleased = new TokenReleased();
    _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_tokenReleased);
  }
  
  protected AgentTask doSwapsForCorner(final InfosFrame currentInfosFrame) {
    AgentTask _xblockexpression = null;
    {
      FrameAgent currentFrame = null;
      for (final FrameAgent ite : this.frameList) {
        UUID _iD = ite.getID();
        UUID _uuidCurrentFrame = currentInfosFrame.getUuidCurrentFrame();
        boolean _tripleEquals = (_iD == _uuidCurrentFrame);
        if (_tripleEquals) {
          currentFrame = ite;
          currentInfosFrame.setNumFrame(ite.getNumFrame());
          break;
        }
      }
      int whichCorner = this.whichCorner(currentInfosFrame);
      AgentTask _xifexpression = null;
      if ((whichCorner > 0)) {
        AgentTask _xblockexpression_1 = null;
        {
          this.isCorner = true;
          AgentTask _xifexpression_1 = null;
          if ((whichCorner == 1)) {
            AgentTask _xblockexpression_2 = null;
            {
              UUID firstNeighbourUUID = currentFrame.getEastNeighbour();
              int _x = currentInfosFrame.getCoordsCurrentFrame().getX();
              int _y = currentInfosFrame.getCoordsCurrentFrame().getY();
              CoordPair _coordPair = new CoordPair(_x, _y);
              InfosFrame firstNeighbourInfosFrame = new InfosFrame(firstNeighbourUUID, _coordPair);
              firstNeighbourInfosFrame.getCoordsCurrentFrame().setYplusOne();
              FrameAgent firstNeighbourFrame = null;
              for (final FrameAgent ite_1 : this.frameList) {
                UUID _iD_1 = ite_1.getID();
                boolean _tripleEquals_1 = (_iD_1 == firstNeighbourUUID);
                if (_tripleEquals_1) {
                  firstNeighbourFrame = ite_1;
                  break;
                }
              }
              UUID secondNeighbourUUID = firstNeighbourFrame.getEastNeighbour();
              int _x_1 = firstNeighbourFrame.getCoordPair().getX();
              int _y_1 = firstNeighbourFrame.getCoordPair().getY();
              CoordPair _coordPair_1 = new CoordPair(_x_1, _y_1);
              InfosFrame secondNeighbourInfosFrame = new InfosFrame(secondNeighbourUUID, _coordPair_1);
              secondNeighbourInfosFrame.getCoordsCurrentFrame().setYplusOne();
              FrameAgent secondNeighbourFrame = null;
              for (final FrameAgent ite_2 : this.frameList) {
                UUID _iD_2 = ite_2.getID();
                boolean _tripleEquals_2 = (_iD_2 == secondNeighbourUUID);
                if (_tripleEquals_2) {
                  secondNeighbourFrame = ite_2;
                  break;
                }
              }
              this.initBeginningInfosFrame(secondNeighbourFrame.getIdNum(), secondNeighbourUUID, secondNeighbourFrame.getCoordPair());
              this.findPath();
              ArrayList<InfosFrame> newChainAggression = this.buildChainAggressionForCorner(currentInfosFrame, firstNeighbourInfosFrame, null, null);
              this.doSwaps(newChainAggression);
              Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER = this.$castSkill(Schedules.class, (this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES == null || this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES = this.$getSkill(Schedules.class)) : this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES);
              final Procedure1<Agent> _function = (Agent it) -> {
                DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
                TokenReleased _tokenReleased = new TokenReleased();
                _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_tokenReleased);
              };
              _xblockexpression_2 = _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER.in(1000, _function);
            }
            _xifexpression_1 = _xblockexpression_2;
          } else {
            if ((whichCorner == 2)) {
              UUID firstNeighbourUUID = currentFrame.getNorthNeighbour();
              int _x = currentInfosFrame.getCoordsCurrentFrame().getX();
              int _y = currentInfosFrame.getCoordsCurrentFrame().getY();
              CoordPair _coordPair = new CoordPair(_x, _y);
              InfosFrame firstNeighbourInfosFrame = new InfosFrame(firstNeighbourUUID, _coordPair);
              firstNeighbourInfosFrame.getCoordsCurrentFrame().setXminusOne();
              FrameAgent firstNeighbourFrame = null;
              for (final FrameAgent ite_1 : this.frameList) {
                UUID _iD_1 = ite_1.getID();
                boolean _tripleEquals_1 = (_iD_1 == firstNeighbourUUID);
                if (_tripleEquals_1) {
                  firstNeighbourFrame = ite_1;
                  break;
                }
              }
              UUID secondNeighbourUUID = firstNeighbourFrame.getNorthNeighbour();
              int _x_1 = firstNeighbourFrame.getCoordPair().getX();
              int _y_1 = firstNeighbourFrame.getCoordPair().getY();
              CoordPair _coordPair_1 = new CoordPair(_x_1, _y_1);
              InfosFrame secondNeighbourInfosFrame = new InfosFrame(secondNeighbourUUID, _coordPair_1);
              secondNeighbourInfosFrame.getCoordsCurrentFrame().setXminusOne();
              FrameAgent secondNeighbourFrame = null;
              for (final FrameAgent ite_2 : this.frameList) {
                UUID _iD_2 = ite_2.getID();
                boolean _tripleEquals_2 = (_iD_2 == secondNeighbourUUID);
                if (_tripleEquals_2) {
                  secondNeighbourFrame = ite_2;
                  break;
                }
              }
              this.initBeginningInfosFrame(secondNeighbourFrame.getIdNum(), secondNeighbourUUID, secondNeighbourFrame.getCoordPair());
              this.findPath();
              ArrayList<InfosFrame> newChainAggression = this.buildChainAggressionForCorner(currentInfosFrame, firstNeighbourInfosFrame, null, null);
              for (final InfosFrame ite_3 : newChainAggression) {
                Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
                String _string = ite_3.getCoordsCurrentFrame().toString();
                String _plus = ("newChain= " + _string);
                _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(_plus);
              }
              this.doSwaps(newChainAggression);
              this.flagCorner = 1;
              DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
              TokenReleased _tokenReleased = new TokenReleased();
              _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_tokenReleased);
            } else {
              if ((whichCorner == 3)) {
                UUID firstNeighbourUUID_1 = currentFrame.getNorthNeighbour();
                int _x_2 = currentInfosFrame.getCoordsCurrentFrame().getX();
                int _y_2 = currentInfosFrame.getCoordsCurrentFrame().getY();
                CoordPair _coordPair_2 = new CoordPair(_x_2, _y_2);
                InfosFrame firstNeighbourInfosFrame_1 = new InfosFrame(firstNeighbourUUID_1, _coordPair_2);
                firstNeighbourInfosFrame_1.getCoordsCurrentFrame().setXminusOne();
                FrameAgent firstNeighbourFrame_1 = null;
                for (final FrameAgent ite_4 : this.frameList) {
                  UUID _iD_3 = ite_4.getID();
                  boolean _tripleEquals_3 = (_iD_3 == firstNeighbourUUID_1);
                  if (_tripleEquals_3) {
                    firstNeighbourFrame_1 = ite_4;
                    break;
                  }
                }
                UUID secondNeighbourUUID_1 = firstNeighbourFrame_1.getNorthNeighbour();
                int _x_3 = firstNeighbourFrame_1.getCoordPair().getX();
                int _y_3 = firstNeighbourFrame_1.getCoordPair().getY();
                CoordPair _coordPair_3 = new CoordPair(_x_3, _y_3);
                InfosFrame secondNeighbourInfosFrame_1 = new InfosFrame(secondNeighbourUUID_1, _coordPair_3);
                secondNeighbourInfosFrame_1.getCoordsCurrentFrame().setXminusOne();
                FrameAgent secondNeighbourFrame_1 = null;
                for (final FrameAgent ite_5 : this.frameList) {
                  UUID _iD_4 = ite_5.getID();
                  boolean _tripleEquals_4 = (_iD_4 == secondNeighbourUUID_1);
                  if (_tripleEquals_4) {
                    secondNeighbourFrame_1 = ite_5;
                    break;
                  }
                }
                UUID thirdNeighbourUUID = secondNeighbourFrame_1.getEastNeighbour();
                int _x_4 = secondNeighbourFrame_1.getCoordPair().getX();
                int _y_4 = secondNeighbourFrame_1.getCoordPair().getY();
                CoordPair _coordPair_4 = new CoordPair(_x_4, _y_4);
                InfosFrame thirdNeighbourInfosFrame = new InfosFrame(thirdNeighbourUUID, _coordPair_4);
                thirdNeighbourInfosFrame.getCoordsCurrentFrame().setYplusOne();
                FrameAgent thirdNeighbourFrame = null;
                for (final FrameAgent ite_6 : this.frameList) {
                  UUID _iD_5 = ite_6.getID();
                  boolean _tripleEquals_5 = (_iD_5 == thirdNeighbourUUID);
                  if (_tripleEquals_5) {
                    thirdNeighbourFrame = ite_6;
                    break;
                  }
                }
                UUID fourthNeighbourUUID = thirdNeighbourFrame.getEastNeighbour();
                int _x_5 = thirdNeighbourFrame.getCoordPair().getX();
                int _y_5 = thirdNeighbourFrame.getCoordPair().getY();
                CoordPair _coordPair_5 = new CoordPair(_x_5, _y_5);
                InfosFrame fourthNeighbourInfosFrame = new InfosFrame(fourthNeighbourUUID, _coordPair_5);
                fourthNeighbourInfosFrame.getCoordsCurrentFrame().setYplusOne();
                FrameAgent fourthNeighbourFrame = null;
                for (final FrameAgent ite_7 : this.frameList) {
                  UUID _iD_6 = ite_7.getID();
                  boolean _tripleEquals_6 = (_iD_6 == fourthNeighbourUUID);
                  if (_tripleEquals_6) {
                    fourthNeighbourFrame = ite_7;
                    break;
                  }
                }
                this.initBeginningInfosFrame(fourthNeighbourFrame.getIdNum(), fourthNeighbourUUID, fourthNeighbourFrame.getCoordPair());
                this.findPath();
                ArrayList<InfosFrame> newChainAggression_1 = this.buildChainAggressionForCorner(currentInfosFrame, firstNeighbourInfosFrame_1, secondNeighbourInfosFrame_1, thirdNeighbourInfosFrame);
                this.doSwaps(newChainAggression_1);
                for (final FrameAgent ite_8 : this.frameList) {
                  int _idNum = ite_8.getIdNum();
                  boolean _equals = (_idNum == ((this.PROBLEM_SIZE * this.PROBLEM_SIZE) - 2));
                  if (_equals) {
                    ite_8.setDidMyTileMateHaveTheToken(true);
                    ite_8.setIsSatisfied(true);
                    for (final TileAgent tile : this.tileList) {
                      int _numFrameHost = tile.getNumFrameHost();
                      int _idNum_1 = ite_8.getIdNum();
                      boolean _equals_1 = (_numFrameHost == _idNum_1);
                      if (_equals_1) {
                        tile.setIsHappy(true);
                      }
                    }
                  }
                }
                this.changeSatisfaction();
                this.flagCorner = 2;
                DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1 = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
                TokenReleased _tokenReleased_1 = new TokenReleased();
                _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER_1.emit(_tokenReleased_1);
              }
            }
          }
          _xblockexpression_1 = _xifexpression_1;
        }
        _xifexpression = _xblockexpression_1;
      } else {
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.error("Sorry.. You lose! Try again. =D");
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  protected boolean doSwaps(final ArrayList<InfosFrame> chain) {
    try {
      boolean _xblockexpression = false;
      {
        ArrayList<InfosFrame> chainAggression = null;
        if ((!this.isCorner)) {
          ArrayList<InfosFrame> tmpChainAggression = this.buildChainAggression(this.beginningInfosFrame);
          int _size = tmpChainAggression.size();
          int _minus = (_size - 1);
          InfosFrame tmpFrame = tmpChainAggression.get(_minus);
          ArrayList<InfosFrame> _arrayList = new ArrayList<InfosFrame>();
          chainAggression = _arrayList;
          chainAggression.add(tmpFrame);
          for (int i = (tmpChainAggression.size() - 2); (i >= 0); i--) {
            UUID _uuidCurrentFrame = tmpFrame.getUuidCurrentFrame();
            UUID _uuidCurrentFrame_1 = tmpChainAggression.get(i).getUuidCurrentFrame();
            boolean _tripleEquals = (_uuidCurrentFrame == _uuidCurrentFrame_1);
            boolean _not = (!_tripleEquals);
            if (_not) {
              tmpFrame = tmpChainAggression.get(i);
              chainAggression.add(tmpFrame);
            }
          }
        } else {
          chainAggression = chain;
        }
        ArrayList<Integer> listSwap = new ArrayList<Integer>();
        for (int i = 0; (i < chainAggression.size()); i++) {
          boolean _equals = chainAggression.get(i).getCoordsCurrentFrame().equals(this.beginningFrame.getCoordPair());
          if (_equals) {
            for (int j = 0; (j < this.frameList.size()); j++) {
              UUID _iD = this.frameList.get(j).getID();
              UUID _uuidCurrentFrame = chainAggression.get(i).getUuidCurrentFrame();
              boolean _tripleEquals = (_iD == _uuidCurrentFrame);
              if (_tripleEquals) {
                listSwap.add(Integer.valueOf(this.frameList.get(j).getHostedNumTile()));
                this.ctrl.setColor("green", Integer.valueOf(this.frameList.get(j).getHostedNumTile()));
                Thread.sleep(StaticVars.speed);
                break;
              }
            }
          } else {
            boolean _equals_1 = chainAggression.get(i).getCoordsCurrentFrame().equals(this.arrivalFrame.getCoordPair());
            boolean _not = (!_equals_1);
            if (_not) {
              for (int j = 0; (j < this.frameList.size()); j++) {
                UUID _iD = this.frameList.get(j).getID();
                UUID _uuidCurrentFrame = chainAggression.get(i).getUuidCurrentFrame();
                boolean _tripleEquals = (_iD == _uuidCurrentFrame);
                if (_tripleEquals) {
                  listSwap.add(Integer.valueOf(this.frameList.get(j).getHostedNumTile()));
                  this.ctrl.setColor("blue", Integer.valueOf(this.frameList.get(j).getHostedNumTile()));
                  Thread.sleep(StaticVars.speed);
                  break;
                }
              }
            } else {
            }
          }
        }
        Collections.reverse(listSwap);
        for (final Integer number : listSwap) {
          {
            this.ctrl.swap((number).intValue());
            Thread.sleep(StaticVars.speed);
          }
        }
        this.ctrl.swap(this.haveTokenFrame.getHostedNumTile());
        this.swapTilesInChainAggression(chainAggression);
        this.swapTokenAndBlank();
        this.resetAllObjects();
        boolean _xifexpression = false;
        if (this.isCorner) {
          _xifexpression = this.isCorner = false;
        }
        _xblockexpression = _xifexpression;
      }
      return _xblockexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  protected void resetAllObjects() {
    this.openListOfFrames.clear();
    this.closedListOfFrames.clear();
    this.ctrl.reset();
  }
  
  @SyntheticMember
  private void $behaviorUnit$FindPathWithAstarAlgo$5(final FindPathWithAstarAlgo occurrence) {
    this.uuidFRAMEbeginning = occurrence.beginningFrame;
    for (int i = 0; (i < this.frameList.size()); i++) {
      UUID _iD = this.frameList.get(i).getID();
      boolean _tripleEquals = (_iD == this.uuidFRAMEbeginning);
      if (_tripleEquals) {
        this.beginningFrame = this.frameList.get(i);
        this.coordsFRAMEbeginning = this.frameList.get(i).getCoordPair();
        InfosFrame infosFrame = new InfosFrame(this.uuidFRAMEbeginning, this.coordsFRAMEbeginning);
        this.beginningInfosFrame = infosFrame;
        break;
      }
    }
    UUID _iD = this.beginningFrame.getID();
    UUID _iD_1 = this.arrivalFrame.getID();
    boolean _notEquals = (!Objects.equal(_iD, _iD_1));
    if (_notEquals) {
      if ((this.PROBLEM_SIZE < 10)) {
        Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER = this.$castSkill(Schedules.class, (this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES == null || this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES = this.$getSkill(Schedules.class)) : this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES);
        final Procedure1<Agent> _function = (Agent it) -> {
          this.findPath();
        };
        _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER.in((400 * this.PROBLEM_SIZE), _function);
      } else {
        Schedules _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1 = this.$castSkill(Schedules.class, (this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES == null || this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES = this.$getSkill(Schedules.class)) : this.$CAPACITY_USE$IO_SARL_CORE_SCHEDULES);
        final Procedure1<Agent> _function_1 = (Agent it) -> {
          this.findPath();
        };
        _$CAPACITY_USE$IO_SARL_CORE_SCHEDULES$CALLER_1.in((600 * this.PROBLEM_SIZE), _function_1);
      }
    } else {
      this.ctrl.swap(this.haveTokenFrame.getHostedNumTile());
      this.swapTokenAndBlank();
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
      TokenReleased _tokenReleased = new TokenReleased();
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_tokenReleased);
    }
  }
  
  protected void swapTokenAndBlank() {
    UUID tmpTOKENtileUuid = null;
    int tmpTOKENtileNum = 0;
    UUID tmpBLANKtileUuid = null;
    int tmpBLANKtileNum = 0;
    TileAgent tokenTile = null;
    for (int i = 0; (i < this.tileList.size()); i++) {
      UUID _uuidFrameHost = this.tileList.get(i).getUuidFrameHost();
      UUID _iD = this.haveTokenFrame.getID();
      boolean _tripleEquals = (_uuidFrameHost == _iD);
      if (_tripleEquals) {
        tokenTile = this.tileList.get(i);
        tmpTOKENtileUuid = this.tileList.get(i).getUuidFrameHost();
        tmpTOKENtileNum = this.tileList.get(i).getNumFrameHost();
      }
    }
    tmpBLANKtileUuid = this.blankTile.getUuidFrameHost();
    tmpBLANKtileNum = this.blankTile.getNumFrameHost();
    tokenTile.setUuidFrameHost(tmpBLANKtileUuid);
    tokenTile.setNumFrameHost(tmpBLANKtileNum);
    int _numFrameHost = tokenTile.getNumFrameHost();
    int _minus = (_numFrameHost - 1);
    tokenTile.setNumFrameHostFromZero(_minus);
    this.blankTile.setUuidFrameHost(tmpTOKENtileUuid);
    this.blankTile.setNumFrameHost(tmpTOKENtileNum);
    this.initArrivalInfosFrame(this.haveTokenFrame.getNumFrame(), this.haveTokenFrame.getID(), this.haveTokenFrame.getCoordPair());
    this.initBeginningInfosFrame(this.arrivalFrame.getNumFrame(), this.arrivalFrame.getID(), this.arrivalFrame.getCoordPair());
    FrameAgent tmpF = this.haveTokenFrame;
    this.haveTokenFrame = this.arrivalFrame;
    this.arrivalFrame = tmpF;
    UUID tmpHostedTile = this.haveTokenFrame.getHostedTile();
    int tmpHostedNumTile = this.haveTokenFrame.getHostedNumTile();
    this.haveTokenFrame.setHostedTile(this.arrivalFrame.getHostedTile());
    this.haveTokenFrame.setHostedNumTile(this.arrivalFrame.getHostedNumTile());
    this.arrivalFrame.setHostedTile(tmpHostedTile);
    this.arrivalFrame.setHostedNumTile(tmpHostedNumTile);
    this.changeSatisfaction();
  }
  
  protected void initArrivalInfosFrame(final int num, final UUID uuid, final CoordPair coord) {
    InfosFrame _infosFrame = new InfosFrame(uuid, coord);
    this.arrivalInfosFrame = _infosFrame;
    this.arrivalInfosFrame.setNumFrame(num);
  }
  
  protected void initBeginningInfosFrame(final int num, final UUID uuid, final CoordPair coord) {
    InfosFrame _infosFrame = new InfosFrame(uuid, coord);
    this.beginningInfosFrame = _infosFrame;
    this.beginningInfosFrame.setNumFrame(num);
  }
  
  protected void swapTilesInChainAggression(final ArrayList<InfosFrame> chainAggression) {
    UUID tmpFRAMEuuidHostedTile = null;
    int tmpFRAMEnumHostedTile = 0;
    UUID tmpTILEuuidFrameHost = null;
    int tmpTILEnumFrameHost = 0;
    UUID tmp1 = null;
    int tmp2 = 0;
    UUID tmp11 = null;
    int tmp22 = 0;
    for (int i = (chainAggression.size() - 1); (i >= 0); i--) {
      for (int j = 0; (j < this.frameList.size()); j++) {
        UUID _iD = this.frameList.get(j).getID();
        UUID _uuidCurrentFrame = chainAggression.get(i).getUuidCurrentFrame();
        boolean _tripleEquals = (_iD == _uuidCurrentFrame);
        if (_tripleEquals) {
          EmptyTileAgent tmpBlankTile = null;
          TileAgent tmpTile = null;
          for (int iTile = 0; (iTile < this.tileList.size()); iTile++) {
            UUID _iD_1 = this.tileList.get(iTile).getID();
            UUID _hostedTile = this.frameList.get(j).getHostedTile();
            boolean _tripleEquals_1 = (_iD_1 == _hostedTile);
            if (_tripleEquals_1) {
              tmpTile = this.tileList.get(iTile);
              break;
            }
          }
          int _size = chainAggression.size();
          int _minus = (_size - 1);
          boolean _equals = (i == _minus);
          if (_equals) {
            tmpFRAMEuuidHostedTile = this.frameList.get(j).getHostedTile();
            tmpFRAMEnumHostedTile = this.frameList.get(j).getHostedNumTile();
            tmpTILEuuidFrameHost = this.blankTile.getUuidFrameHost();
            tmpTILEnumFrameHost = this.blankTile.getNumFrameHost();
          }
          if ((i > 0)) {
            for (int k = 0; (k < this.frameList.size()); k++) {
              UUID _iD_1 = this.frameList.get(k).getID();
              UUID _uuidCurrentFrame_1 = chainAggression.get((i - 1)).getUuidCurrentFrame();
              boolean _tripleEquals_1 = (_iD_1 == _uuidCurrentFrame_1);
              if (_tripleEquals_1) {
                for (int jTile = 0; (jTile < this.tileList.size()); jTile++) {
                  UUID _iD_2 = this.tileList.get(jTile).getID();
                  UUID _hostedTile = this.frameList.get(k).getHostedTile();
                  boolean _tripleEquals_2 = (_iD_2 == _hostedTile);
                  if (_tripleEquals_2) {
                    tmp1 = this.tileList.get(jTile).getUuidFrameHost();
                    tmp2 = this.tileList.get(jTile).getNumFrameHost();
                    int _size_1 = chainAggression.size();
                    int _minus_1 = (_size_1 - 1);
                    boolean _equals_1 = (i == _minus_1);
                    if (_equals_1) {
                      tmp11 = this.tileList.get(jTile).getUuidFrameHost();
                      tmp22 = this.tileList.get(jTile).getNumFrameHost();
                      TileAgent _get = this.tileList.get(jTile);
                      _get.setUuidFrameHost(this.blankTile.getUuidFrameHost());
                      TileAgent _get_1 = this.tileList.get(jTile);
                      _get_1.setNumFrameHost(this.blankTile.getNumFrameHost());
                      TileAgent _get_2 = this.tileList.get(jTile);
                      int _numFrameHost = this.blankTile.getNumFrameHost();
                      int _minus_2 = (_numFrameHost - 1);
                      _get_2.setNumFrameHostFromZero(_minus_2);
                    } else {
                      TileAgent _get_3 = this.tileList.get(jTile);
                      _get_3.setUuidFrameHost(tmp11);
                      TileAgent _get_4 = this.tileList.get(jTile);
                      _get_4.setNumFrameHost(tmp22);
                      TileAgent _get_5 = this.tileList.get(jTile);
                      int _numFrameHost_1 = this.tileList.get(jTile).getNumFrameHost();
                      int _minus_3 = (_numFrameHost_1 - 1);
                      _get_5.setNumFrameHostFromZero(_minus_3);
                      tmp11 = tmp1;
                      tmp22 = tmp2;
                    }
                    break;
                  }
                }
                FrameAgent _get = this.frameList.get(j);
                _get.setHostedTile(this.frameList.get(k).getHostedTile());
                FrameAgent _get_1 = this.frameList.get(j);
                _get_1.setHostedNumTile(this.frameList.get(k).getHostedNumTile());
                break;
              }
            }
          } else {
            FrameAgent _get = this.frameList.get(j);
            _get.setHostedTile(this.blankTile.getID());
            FrameAgent _get_1 = this.frameList.get(j);
            _get_1.setHostedNumTile(this.blankTile.getNumTile());
            this.blankTile.setUuidFrameHost(this.frameList.get(j).getID());
            this.blankTile.setNumFrameHost(this.frameList.get(j).getIdNum());
            this.arrivalFrame = this.frameList.get(j);
          }
        }
      }
    }
    this.changeSatisfaction();
  }
  
  @SyntheticMember
  private void $behaviorUnit$UpdateGUI$6(final UpdateGUI occurrence) {
    try {
      for (final FrameAgent number : occurrence.frameList) {
        {
          this.ctrl.swap(number.getHostedNumTile());
          Thread.sleep(StaticVars.speed);
        }
      }
      DefaultContextInteractions _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER = this.$castSkill(DefaultContextInteractions.class, (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS == null || this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS = this.$getSkill(DefaultContextInteractions.class)) : this.$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS);
      TokenReleased _tokenReleased = new TokenReleased();
      _$CAPACITY_USE$IO_SARL_CORE_DEFAULTCONTEXTINTERACTIONS$CALLER.emit(_tokenReleased);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  @SyntheticMember
  private void $behaviorUnit$EndAgent$7(final EndAgent occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("I kill myself! R.I.P");
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
  private void $guardEvaluator$ActionUI(final ActionUI occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ActionUI$4(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$TokenReleased(final TokenReleased occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$TokenReleased$3(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$EndAgent(final EndAgent occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$EndAgent$7(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$FindPathWithAstarAlgo(final FindPathWithAstarAlgo occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$FindPathWithAstarAlgo$5(occurrence));
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
  private void $guardEvaluator$UpdateGUI(final UpdateGUI occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$UpdateGUI$6(occurrence));
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
    if (!java.util.Objects.equals(this.uuidFRAMEwithTokenTile, other.uuidFRAMEwithTokenTile)) {
      return false;
    }
    if (!java.util.Objects.equals(this.uuidFRAMEwithBlankTile, other.uuidFRAMEwithBlankTile)) {
      return false;
    }
    if (!java.util.Objects.equals(this.uuidFRAMEbeginning, other.uuidFRAMEbeginning)) {
      return false;
    }
    if (other.flagCorner != this.flagCorner)
      return false;
    if (other.isCorner != this.isCorner)
      return false;
    if (other.isDistributed != this.isDistributed)
      return false;
    if (other.nbTriesAstarAlgo != this.nbTriesAstarAlgo)
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
    result = prime * result + java.util.Objects.hashCode(this.uuidFRAMEwithTokenTile);
    result = prime * result + java.util.Objects.hashCode(this.uuidFRAMEwithBlankTile);
    result = prime * result + java.util.Objects.hashCode(this.uuidFRAMEbeginning);
    result = prime * result + this.flagCorner;
    result = prime * result + (this.isCorner ? 1231 : 1237);
    result = prime * result + (this.isDistributed ? 1231 : 1237);
    result = prime * result + this.nbTriesAstarAlgo;
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
