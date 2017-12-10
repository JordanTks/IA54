package fr.utbm.ia54.Agent;

import com.google.common.base.Objects;
import fr.utbm.ia54.Agent.EmptyTileAgent;
import fr.utbm.ia54.Agent.FrameAgent;
import fr.utbm.ia54.Agent.TileAgent;
import fr.utbm.ia54.Class.CoordPair;
import fr.utbm.ia54.Class.InfosFrame;
import fr.utbm.ia54.Event.FindPathWithAstarAlgo;
import fr.utbm.ia54.Event.FrameSet;
import fr.utbm.ia54.Event.TileSet;
import fr.utbm.ia54.Event.TokenReceived;
import fr.utbm.ia54.Event.TokenReleased;
import fr.utbm.ia54.gui.TaquinFxViewerController;
import fr.utbm.taquin.events.ActionUI;
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
  
  private int nbFrameSet = 0;
  
  private int nbTileSet = 0;
  
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
  
  @SyntheticMember
  private void $behaviorUnit$Initialize$0(final Initialize occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.setLoggingName("BoardGameAgent");
    Object _get = occurrence.parameters[0];
    this.PROBLEM_SIZE = (((Integer) _get)).intValue();
    Object _get_1 = occurrence.parameters[1];
    this.ctrl = ((TaquinFxViewerController) _get_1);
    final List<Integer> numFrameList = new ArrayList<Integer>();
    for (int i = 1; (i <= Math.pow(this.PROBLEM_SIZE, 2)); i++) {
      numFrameList.add(Integer.valueOf(i));
    }
    for (int i = 0; (i <= (Math.pow(this.PROBLEM_SIZE, 2) - 1)); i++) {
      {
        Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$castSkill(Lifecycle.class, (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = this.$getSkill(Lifecycle.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE);
        Integer _get_2 = numFrameList.get(i);
        this.frameUUIDList.add(_$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.spawn(FrameAgent.class, new Object[] { _get_2, Integer.valueOf((i / this.PROBLEM_SIZE)), Integer.valueOf((i % this.PROBLEM_SIZE)), Integer.valueOf(this.PROBLEM_SIZE) }));
        CoordPair _coordPair = new CoordPair((i / this.PROBLEM_SIZE), (i % this.PROBLEM_SIZE));
        this.listCoordPairsOfFrames.add(_coordPair);
      }
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
      this.ctrl.monTest(startingTiles);
      for (int i = 0; (i < (Math.pow(this.PROBLEM_SIZE, 2) - 1)); i++) {
        {
          Lifecycle _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER = this.$castSkill(Lifecycle.class, (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE == null || this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE = this.$getSkill(Lifecycle.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE);
          Integer _get = startingTiles.get(i);
          UUID _iD = this.frameList.get(i).getID();
          UUID id = _$CAPACITY_USE$IO_SARL_CORE_LIFECYCLE$CALLER.spawn(TileAgent.class, new Object[] { _get, _iD, Integer.valueOf(i), Integer.valueOf(this.PROBLEM_SIZE) });
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
    }
  }
  
  @SyntheticMember
  private void $behaviorUnit$TileSet$2(final TileSet occurrence) {
    this.nbTileSet++;
    if ((occurrence.tile != null)) {
      this.tileList.add(occurrence.tile);
    } else {
      this.blankTile = occurrence.blank;
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
            for (int i = 0; (i < this.frameList.size()); i++) {
              UUID _hostedTile = this.frameList.get(i).getHostedTile();
              UUID _iD = t.getID();
              boolean _tripleEquals = (_hostedTile == _iD);
              if (_tripleEquals) {
                this.uuidFRAMEwithTokenTile = this.frameList.get(i).getID();
                this.coordsFRAMEwithTokenTile = this.frameList.get(i).getCoordPair();
                InfosFrame _infosFrame = new InfosFrame(this.uuidFRAMEwithTokenTile, this.coordsFRAMEwithTokenTile);
                this.haveTokenInfosFrame = _infosFrame;
                this.haveTokenFrame = this.frameList.get(i);
                Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
                int _hostedNumTile = this.haveTokenFrame.getHostedNumTile();
                String _plus = ("on TokenReleased: hostedNumTile=" + Integer.valueOf(_hostedNumTile));
                _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(_plus);
                break;
              }
            }
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
  private void $behaviorUnit$ActionUI$4(final ActionUI occurrence) {
    this.ctrl.setColor(occurrence.prop, Integer.valueOf(occurrence.target));
  }
  
  /**
   * calculate the euclidean distance between 2 frames
   * @params a,b : 2 FrameAgent
   * @return int: square euclidean distance
   */
  @Pure
  protected int distEuclidian(final FrameAgent a, final FrameAgent b) {
    int _xRow = a.getXRow();
    int _xRow_1 = b.getXRow();
    int _minus = (_xRow - _xRow_1);
    int _xRow_2 = a.getXRow();
    int _xRow_3 = b.getXRow();
    int _minus_1 = (_xRow_2 - _xRow_3);
    int _multiply = (_minus * _minus_1);
    int _yCol = a.getYCol();
    int _yCol_1 = b.getYCol();
    int _minus_2 = (_yCol - _yCol_1);
    int _yCol_2 = a.getYCol();
    int _yCol_3 = b.getYCol();
    int _minus_3 = (_yCol_2 - _yCol_3);
    int _multiply_1 = (_minus_2 * _minus_3);
    return (_multiply + _multiply_1);
  }
  
  /**
   * determinate if the frame is in the list
   * @params pair, listOfFrames : CoordPair & ArrayList<InfosFrame>
   * @return boolean: true if the CoordPair correspond to coords of an FrameAgent in the list
   */
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
  
  /**
   * add the neighbour's frames of the current frame
   * @params currentFrame: the current FrameAgent
   */
  protected Object addNeighbourFrames(final FrameAgent currentFrame) {
    Object _xblockexpression = null;
    {
      UUID _northNeighbour = currentFrame.getNorthNeighbour();
      boolean _notEquals = (!Objects.equal(_northNeighbour, null));
      if (_notEquals) {
        int index = this.frameUUIDList.indexOf(currentFrame.getNorthNeighbour());
        if ((((!this.frameList.get(index).getIsSatisfied()) && (!this.frameList.get(index).getIsBlocked())) && (!Objects.equal(this.frameList.get(index).getID(), this.uuidFRAMEwithTokenTile)))) {
          CoordPair coordNeighbour = this.listCoordPairsOfFrames.get(index);
          boolean _isInFrameList = this.isInFrameList(coordNeighbour, this.closedListOfFrames);
          boolean _not = (!_isInFrameList);
          if (_not) {
            UUID _northNeighbour_1 = currentFrame.getNorthNeighbour();
            InfosFrame infosFrame = new InfosFrame(_northNeighbour_1, coordNeighbour);
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
            int _distEuclidian = this.distEuclidian(this.frameList.get(index), currentFrame);
            int _plus = (_costG + _distEuclidian);
            infosFrame.setCostG(_plus);
            infosFrame.setCostH(this.distEuclidian(this.frameList.get(index), this.arrivalFrame));
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
                  UUID _northNeighbour_2 = currentFrame.getNorthNeighbour();
                  boolean _equals = Objects.equal(_uuidCurrentFrame, _northNeighbour_2);
                  if (_equals) {
                    break;
                  }
                }
              }
            }
            boolean _isInFrameList_1 = this.isInFrameList(coordNeighbour, this.openListOfFrames);
            if (_isInFrameList_1) {
              if (((indexForOpenList > (-1)) && (infosFrame.getCostF() < this.openListOfFrames.get(indexForOpenList).getCostF()))) {
                this.openListOfFrames.set(indexForOpenList, infosFrame);
              }
            } else {
              this.openListOfFrames.add(infosFrame);
            }
          }
        }
      }
      UUID _southNeighbour = currentFrame.getSouthNeighbour();
      boolean _notEquals_1 = (!Objects.equal(_southNeighbour, null));
      if (_notEquals_1) {
        int index_1 = this.frameUUIDList.indexOf(currentFrame.getSouthNeighbour());
        if ((((!this.frameList.get(index_1).getIsSatisfied()) && (!this.frameList.get(index_1).getIsBlocked())) && (!Objects.equal(this.frameList.get(index_1).getID(), this.uuidFRAMEwithTokenTile)))) {
          CoordPair coordNeighbour_1 = this.listCoordPairsOfFrames.get(index_1);
          boolean _isInFrameList_2 = this.isInFrameList(coordNeighbour_1, this.closedListOfFrames);
          boolean _not_2 = (!_isInFrameList_2);
          if (_not_2) {
            UUID _southNeighbour_1 = currentFrame.getSouthNeighbour();
            InfosFrame infosFrame_1 = new InfosFrame(_southNeighbour_1, coordNeighbour_1);
            int indexForClosedList_1 = (-1);
            for (final InfosFrame iterator_2 : this.closedListOfFrames) {
              {
                indexForClosedList_1++;
                UUID _uuidCurrentFrame = iterator_2.getUuidCurrentFrame();
                UUID _iD = currentFrame.getID();
                boolean _equals = Objects.equal(_uuidCurrentFrame, _iD);
                if (_equals) {
                  break;
                }
              }
            }
            int _costG_2 = this.closedListOfFrames.get(indexForClosedList_1).getCostG();
            int _distEuclidian_1 = this.distEuclidian(this.frameList.get(index_1), currentFrame);
            int _plus_2 = (_costG_2 + _distEuclidian_1);
            infosFrame_1.setCostG(_plus_2);
            infosFrame_1.setCostH(this.distEuclidian(this.frameList.get(index_1), this.arrivalFrame));
            int _costG_3 = infosFrame_1.getCostG();
            int _costH_1 = infosFrame_1.getCostH();
            int _plus_3 = (_costG_3 + _costH_1);
            infosFrame_1.setCostF(_plus_3);
            infosFrame_1.setUuidPreviousFrame(currentFrame.getID());
            infosFrame_1.setCoordsPreviousFrame(currentFrame.getCoordPair());
            int indexForOpenList_1 = (-1);
            boolean _isEmpty_1 = this.openListOfFrames.isEmpty();
            boolean _not_3 = (!_isEmpty_1);
            if (_not_3) {
              for (final InfosFrame iterator_3 : this.openListOfFrames) {
                {
                  indexForOpenList_1++;
                  UUID _uuidCurrentFrame = iterator_3.getUuidCurrentFrame();
                  UUID _southNeighbour_2 = currentFrame.getSouthNeighbour();
                  boolean _equals = Objects.equal(_uuidCurrentFrame, _southNeighbour_2);
                  if (_equals) {
                    break;
                  }
                }
              }
            }
            if (((indexForOpenList_1 > (-1)) && this.isInFrameList(coordNeighbour_1, this.openListOfFrames))) {
              int _costF = infosFrame_1.getCostF();
              int _costF_1 = this.openListOfFrames.get(indexForOpenList_1).getCostF();
              boolean _lessThan = (_costF < _costF_1);
              if (_lessThan) {
                this.openListOfFrames.set(indexForOpenList_1, infosFrame_1);
              }
            } else {
              this.openListOfFrames.add(infosFrame_1);
            }
          }
        }
      }
      UUID _eastNeighbour = currentFrame.getEastNeighbour();
      boolean _notEquals_2 = (!Objects.equal(_eastNeighbour, null));
      if (_notEquals_2) {
        int index_2 = this.frameUUIDList.indexOf(currentFrame.getEastNeighbour());
        if ((((!this.frameList.get(index_2).getIsSatisfied()) && (!this.frameList.get(index_2).getIsBlocked())) && (!Objects.equal(this.frameList.get(index_2).getID(), this.uuidFRAMEwithTokenTile)))) {
          CoordPair coordNeighbour_2 = this.listCoordPairsOfFrames.get(index_2);
          boolean _isInFrameList_3 = this.isInFrameList(coordNeighbour_2, this.closedListOfFrames);
          boolean _not_4 = (!_isInFrameList_3);
          if (_not_4) {
            UUID _eastNeighbour_1 = currentFrame.getEastNeighbour();
            InfosFrame infosFrame_2 = new InfosFrame(_eastNeighbour_1, coordNeighbour_2);
            int indexForClosedList_2 = (-1);
            for (final InfosFrame iterator_4 : this.closedListOfFrames) {
              {
                indexForClosedList_2++;
                UUID _uuidCurrentFrame = iterator_4.getUuidCurrentFrame();
                UUID _iD = currentFrame.getID();
                boolean _equals = Objects.equal(_uuidCurrentFrame, _iD);
                if (_equals) {
                  break;
                }
              }
            }
            int _costG_4 = this.closedListOfFrames.get(indexForClosedList_2).getCostG();
            int _distEuclidian_2 = this.distEuclidian(this.frameList.get(index_2), currentFrame);
            int _plus_4 = (_costG_4 + _distEuclidian_2);
            infosFrame_2.setCostG(_plus_4);
            infosFrame_2.setCostH(this.distEuclidian(this.frameList.get(index_2), this.arrivalFrame));
            int _costG_5 = infosFrame_2.getCostG();
            int _costH_2 = infosFrame_2.getCostH();
            int _plus_5 = (_costG_5 + _costH_2);
            infosFrame_2.setCostF(_plus_5);
            infosFrame_2.setUuidPreviousFrame(currentFrame.getID());
            infosFrame_2.setCoordsPreviousFrame(currentFrame.getCoordPair());
            int indexForOpenList_2 = (-1);
            boolean _isEmpty_2 = this.openListOfFrames.isEmpty();
            boolean _not_5 = (!_isEmpty_2);
            if (_not_5) {
              for (final InfosFrame iterator_5 : this.openListOfFrames) {
                {
                  indexForOpenList_2++;
                  UUID _uuidCurrentFrame = iterator_5.getUuidCurrentFrame();
                  UUID _eastNeighbour_2 = currentFrame.getEastNeighbour();
                  boolean _equals = Objects.equal(_uuidCurrentFrame, _eastNeighbour_2);
                  if (_equals) {
                    break;
                  }
                }
              }
            }
            if (((indexForOpenList_2 > (-1)) && this.isInFrameList(coordNeighbour_2, this.openListOfFrames))) {
              int _costF_2 = infosFrame_2.getCostF();
              int _costF_3 = this.openListOfFrames.get(indexForOpenList_2).getCostF();
              boolean _lessThan_1 = (_costF_2 < _costF_3);
              if (_lessThan_1) {
                this.openListOfFrames.set(indexForOpenList_2, infosFrame_2);
              }
            } else {
              this.openListOfFrames.add(infosFrame_2);
            }
          }
        }
      }
      Object _xifexpression = null;
      UUID _westNeighbour = currentFrame.getWestNeighbour();
      boolean _notEquals_3 = (!Objects.equal(_westNeighbour, null));
      if (_notEquals_3) {
        Object _xblockexpression_1 = null;
        {
          int index_3 = this.frameUUIDList.indexOf(currentFrame.getWestNeighbour());
          Object _xifexpression_1 = null;
          if ((((!this.frameList.get(index_3).getIsSatisfied()) && (!this.frameList.get(index_3).getIsBlocked())) && (!Objects.equal(this.frameList.get(index_3).getID(), this.uuidFRAMEwithTokenTile)))) {
            Object _xblockexpression_2 = null;
            {
              CoordPair coordNeighbour_3 = this.listCoordPairsOfFrames.get(index_3);
              Object _xifexpression_2 = null;
              boolean _isInFrameList_4 = this.isInFrameList(coordNeighbour_3, this.closedListOfFrames);
              boolean _not_6 = (!_isInFrameList_4);
              if (_not_6) {
                Object _xblockexpression_3 = null;
                {
                  UUID _westNeighbour_1 = currentFrame.getWestNeighbour();
                  InfosFrame infosFrame_3 = new InfosFrame(_westNeighbour_1, coordNeighbour_3);
                  int indexForClosedList_3 = (-1);
                  for (final InfosFrame iterator_6 : this.closedListOfFrames) {
                    {
                      indexForClosedList_3++;
                      UUID _uuidCurrentFrame = iterator_6.getUuidCurrentFrame();
                      UUID _iD = currentFrame.getID();
                      boolean _equals = Objects.equal(_uuidCurrentFrame, _iD);
                      if (_equals) {
                        break;
                      }
                    }
                  }
                  int _costG_6 = this.closedListOfFrames.get(indexForClosedList_3).getCostG();
                  int _distEuclidian_3 = this.distEuclidian(this.frameList.get(index_3), currentFrame);
                  int _plus_6 = (_costG_6 + _distEuclidian_3);
                  infosFrame_3.setCostG(_plus_6);
                  infosFrame_3.setCostH(this.distEuclidian(this.frameList.get(index_3), this.arrivalFrame));
                  int _costG_7 = infosFrame_3.getCostG();
                  int _costH_3 = infosFrame_3.getCostH();
                  int _plus_7 = (_costG_7 + _costH_3);
                  infosFrame_3.setCostF(_plus_7);
                  infosFrame_3.setUuidPreviousFrame(currentFrame.getID());
                  infosFrame_3.setCoordsPreviousFrame(currentFrame.getCoordPair());
                  int indexForOpenList_3 = (-1);
                  boolean _isEmpty_3 = this.openListOfFrames.isEmpty();
                  boolean _not_7 = (!_isEmpty_3);
                  if (_not_7) {
                    for (final InfosFrame iterator_7 : this.openListOfFrames) {
                      {
                        indexForOpenList_3++;
                        UUID _uuidCurrentFrame = iterator_7.getUuidCurrentFrame();
                        UUID _westNeighbour_2 = currentFrame.getWestNeighbour();
                        boolean _equals = Objects.equal(_uuidCurrentFrame, _westNeighbour_2);
                        if (_equals) {
                          break;
                        }
                      }
                    }
                  }
                  Object _xifexpression_3 = null;
                  if (((indexForOpenList_3 > (-1)) && this.isInFrameList(coordNeighbour_3, this.openListOfFrames))) {
                    InfosFrame _xifexpression_4 = null;
                    int _costF_4 = infosFrame_3.getCostF();
                    int _costF_5 = this.openListOfFrames.get(indexForOpenList_3).getCostF();
                    boolean _lessThan_2 = (_costF_4 < _costF_5);
                    if (_lessThan_2) {
                      _xifexpression_4 = this.openListOfFrames.set(indexForOpenList_3, infosFrame_3);
                    }
                    _xifexpression_3 = _xifexpression_4;
                  } else {
                    _xifexpression_3 = Boolean.valueOf(this.openListOfFrames.add(infosFrame_3));
                  }
                  _xblockexpression_3 = _xifexpression_3;
                }
                _xifexpression_2 = _xblockexpression_3;
              }
              _xblockexpression_2 = _xifexpression_2;
            }
            _xifexpression_1 = _xblockexpression_2;
          }
          _xblockexpression_1 = _xifexpression_1;
        }
        _xifexpression = _xblockexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  /**
   * get the frame with best cost from the frameList
   * @param listFrames: list of InfosFrame
   * @return InfosFrame: the infosFrame with the best (lower) cost OR null if the list is empty
   */
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
  
  /**
   * add the InfosFrame in the closedList and remove it from the openList
   * @param coords: CoordPair of the frame to manipulate
   */
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
  
  /**
   * get the chain of aggression in a list of InfosFrame
   * return ArrayList<InfosFrame>: the list of InfosFrame for the aggression in the right order
   */
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
  
  /**
   * execute the A-star algorithm
   */
  protected void findPath() {
    try {
      InfosFrame currentFrame = this.beginningInfosFrame;
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
      String _string = this.beginningInfosFrame.toString();
      String _plus = ("/// beginningInfosFrame=" + _string);
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(_plus);
      this.openListOfFrames.add(this.beginningInfosFrame);
      this.addInClosedList(this.beginningInfosFrame.getCoordsCurrentFrame());
      int indexOfFrameList = (-1);
      for (final FrameAgent ite : this.frameList) {
        {
          indexOfFrameList++;
          boolean _equals = ite.getCoordPair().equals(this.beginningInfosFrame.getCoordsCurrentFrame());
          if (_equals) {
            break;
          }
        }
      }
      this.addNeighbourFrames(this.frameList.get(indexOfFrameList));
      while (((!currentFrame.getCoordsCurrentFrame().equals(this.arrivalInfosFrame.getCoordsCurrentFrame())) && (!this.openListOfFrames.isEmpty()))) {
        {
          currentFrame = this.getBestFrame(this.openListOfFrames);
          this.addInClosedList(currentFrame.getCoordsCurrentFrame());
          int indexOfFrameList2 = (-1);
          for (final FrameAgent ite_1 : this.frameList) {
            {
              indexOfFrameList2++;
              boolean _equals = ite_1.getCoordPair().equals(currentFrame.getCoordsCurrentFrame());
              if (_equals) {
                break;
              }
            }
          }
          this.addNeighbourFrames(this.frameList.get(indexOfFrameList2));
        }
      }
      boolean _equals = currentFrame.getCoordsCurrentFrame().equals(this.arrivalInfosFrame.getCoordsCurrentFrame());
      if (_equals) {
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("## A-star : destination IS reached! ##");
        ArrayList<InfosFrame> tmpChainAggression = this.buildChainAggression(this.beginningInfosFrame);
        ArrayList<InfosFrame> chainAggression = new ArrayList<InfosFrame>();
        int _size = tmpChainAggression.size();
        int _minus = (_size - 1);
        InfosFrame tmpFrame = tmpChainAggression.get(_minus);
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
        ArrayList<Integer> listSwap = new ArrayList<Integer>();
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info("---- Chain of aggression below ----");
        for (int i = 0; (i < chainAggression.size()); i++) {
          boolean _equals_1 = chainAggression.get(i).getCoordsCurrentFrame().equals(this.beginningFrame.getCoordPair());
          if (_equals_1) {
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            String _string_1 = chainAggression.get(i).getCoordsCurrentFrame().toString();
            String _plus_1 = ("BeginningFrame= " + _string_1);
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3.info(_plus_1);
            for (int j = 0; (j < this.frameList.size()); j++) {
              UUID _iD = this.frameList.get(j).getID();
              UUID _uuidCurrentFrame = chainAggression.get(i).getUuidCurrentFrame();
              boolean _tripleEquals = (_iD == _uuidCurrentFrame);
              if (_tripleEquals) {
                listSwap.add(Integer.valueOf(this.frameList.get(j).getHostedNumTile()));
                this.ctrl.setColor("green", Integer.valueOf(this.frameList.get(j).getHostedNumTile()));
                Thread.sleep(1000);
                break;
              }
            }
          } else {
            boolean _equals_2 = chainAggression.get(i).getCoordsCurrentFrame().equals(this.arrivalFrame.getCoordPair());
            boolean _not = (!_equals_2);
            if (_not) {
              Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_4 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
              String _string_2 = chainAggression.get(i).getCoordsCurrentFrame().toString();
              String _plus_2 = ((("Frame" + Integer.valueOf((i + 1))) + "= ") + _string_2);
              _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_4.info(_plus_2);
              for (int j = 0; (j < this.frameList.size()); j++) {
                UUID _iD = this.frameList.get(j).getID();
                UUID _uuidCurrentFrame = chainAggression.get(i).getUuidCurrentFrame();
                boolean _tripleEquals = (_iD == _uuidCurrentFrame);
                if (_tripleEquals) {
                  listSwap.add(Integer.valueOf(this.frameList.get(j).getHostedNumTile()));
                  this.ctrl.setColor("blue", Integer.valueOf(this.frameList.get(j).getHostedNumTile()));
                  Thread.sleep(1000);
                  break;
                }
              }
            } else {
              Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_5 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
              String _string_3 = chainAggression.get(i).getCoordsCurrentFrame().toString();
              String _plus_3 = ("ArrivalFrame= " + _string_3);
              _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_5.info(_plus_3);
            }
          }
        }
        this.swapTilesInChainAggression(chainAggression);
        this.swapTokenAndBlank();
      } else {
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3.info("## A-star : destination IS NOT reached! ##");
      }
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
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
      this.swapTokenAndBlank();
    }
  }
  
  protected void changeSatisfaction() {
    for (int i = 0; (i < (this.frameList.size() - 1)); i++) {
      int _idNum = this.frameList.get(i).getIdNum();
      int _hostedNumTile = this.frameList.get(i).getHostedNumTile();
      boolean _tripleEquals = (_idNum == _hostedNumTile);
      if (_tripleEquals) {
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
        int _idNum_1 = this.frameList.get(i).getIdNum();
        String _plus = ("--> frame[" + Integer.valueOf(_idNum_1));
        String _plus_1 = (_plus + "] is satisfied !");
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(_plus_1);
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
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
        int _numTile_1 = iteTile.getNumTile();
        String _plus = ("--> tile[" + Integer.valueOf(_numTile_1));
        String _plus_1 = (_plus + "] is satisfied TOO !");
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(_plus_1);
      }
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
    this.blankTile.setUuidFrameHost(tmpTOKENtileUuid);
    this.blankTile.setNumFrameHost(tmpTOKENtileNum);
    FrameAgent tmpF = this.haveTokenFrame;
    this.haveTokenFrame = this.arrivalFrame;
    this.arrivalFrame = tmpF;
    UUID tmpHostedTile = this.haveTokenFrame.getHostedTile();
    int tmpHostedNumTile = this.haveTokenFrame.getHostedNumTile();
    this.haveTokenFrame.setHostedTile(this.arrivalFrame.getHostedTile());
    this.haveTokenFrame.setHostedNumTile(this.arrivalFrame.getHostedNumTile());
    this.arrivalFrame.setHostedTile(tmpHostedTile);
    this.arrivalFrame.setHostedNumTile(tmpHostedNumTile);
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(" ");
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info("=============== swapTokenAndBlank ===============");
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info("---------- Infos FRAMES for numTile/uuidTile after the swap ----------");
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    int _idNum = this.haveTokenFrame.getIdNum();
    String _plus = ("haveTokenFrame: idNum=" + Integer.valueOf(_idNum));
    String _plus_1 = (_plus + " /numHostedTile=");
    int _hostedNumTile = this.haveTokenFrame.getHostedNumTile();
    String _plus_2 = (_plus_1 + Integer.valueOf(_hostedNumTile));
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3.info(_plus_2);
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_4 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    int _idNum_1 = this.arrivalFrame.getIdNum();
    String _plus_3 = ("arrivalFrame: idNum=" + Integer.valueOf(_idNum_1));
    String _plus_4 = (_plus_3 + " /numHostedTile=");
    int _hostedNumTile_1 = this.arrivalFrame.getHostedNumTile();
    String _plus_5 = (_plus_4 + Integer.valueOf(_hostedNumTile_1));
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_4.info(_plus_5);
    for (int n = 0; (n < this.frameList.size()); n++) {
      for (final FrameAgent ite : this.frameList) {
        int _idNum_2 = ite.getIdNum();
        boolean _tripleEquals = (_idNum_2 == (n + 1));
        if (_tripleEquals) {
          Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_5 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
          int _hostedNumTile_2 = ite.getHostedNumTile();
          String _plus_6 = ((("frame[" + Integer.valueOf((n + 1))) + "]= tile: ") + Integer.valueOf(_hostedNumTile_2));
          String _plus_7 = (_plus_6 + " / ");
          UUID _hostedTile = ite.getHostedTile();
          String _plus_8 = (_plus_7 + _hostedTile);
          _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_5.info(_plus_8);
        }
      }
    }
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_5 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_5.info("---------- Infos TILES for numFrame/uuidFrame after the swap ----------");
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_6 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    int _numFrameHost = this.blankTile.getNumFrameHost();
    String _plus_6 = ("tile[0]= frame: " + Integer.valueOf(_numFrameHost));
    String _plus_7 = (_plus_6 + " /");
    UUID _uuidFrameHost = this.blankTile.getUuidFrameHost();
    String _plus_8 = (_plus_7 + _uuidFrameHost);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_6.info(_plus_8);
    for (int n = 0; (n < this.tileList.size()); n++) {
      for (final TileAgent ite : this.tileList) {
        int _numTile = ite.getNumTile();
        boolean _tripleEquals = (_numTile == (n + 1));
        if (_tripleEquals) {
          Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_7 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
          int _numFrameHost_1 = ite.getNumFrameHost();
          String _plus_9 = ((("tile[" + Integer.valueOf((n + 1))) + "]= frame: ") + Integer.valueOf(_numFrameHost_1));
          String _plus_10 = (_plus_9 + " / ");
          UUID _uuidFrameHost_1 = ite.getUuidFrameHost();
          String _plus_11 = (_plus_10 + _uuidFrameHost_1);
          _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_7.info(_plus_11);
        }
      }
    }
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_7 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_7.info("------------------------------------------------------------------");
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_8 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_8.info(" ");
    this.changeSatisfaction();
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
                    } else {
                      TileAgent _get_2 = this.tileList.get(jTile);
                      _get_2.setUuidFrameHost(tmp11);
                      TileAgent _get_3 = this.tileList.get(jTile);
                      _get_3.setNumFrameHost(tmp22);
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
  private void $behaviorUnit$Destroy$6(final Destroy occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("The agent was stopped.");
  }
  
  @SyntheticMember
  private void $behaviorUnit$AgentSpawned$7(final AgentSpawned occurrence) {
  }
  
  @SyntheticMember
  private void $behaviorUnit$AgentKilled$8(final AgentKilled occurrence) {
  }
  
  @SyntheticMember
  private void $behaviorUnit$ContextJoined$9(final ContextJoined occurrence) {
  }
  
  @SyntheticMember
  private void $behaviorUnit$ContextLeft$10(final ContextLeft occurrence) {
  }
  
  @SyntheticMember
  private void $behaviorUnit$MemberJoined$11(final MemberJoined occurrence) {
  }
  
  @SyntheticMember
  private void $behaviorUnit$MemberLeft$12(final MemberLeft occurrence) {
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
  private void $guardEvaluator$ActionUI(final ActionUI occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ActionUI$4(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$MemberLeft(final MemberLeft occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$MemberLeft$12(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AgentSpawned(final AgentSpawned occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AgentSpawned$7(occurrence));
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
  private void $guardEvaluator$AgentKilled(final AgentKilled occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AgentKilled$8(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$MemberJoined(final MemberJoined occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$MemberJoined$11(occurrence));
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
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ContextLeft$10(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ContextJoined(final ContextJoined occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ContextJoined$9(occurrence));
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
  private void $guardEvaluator$Destroy(final Destroy occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Destroy$6(occurrence));
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
