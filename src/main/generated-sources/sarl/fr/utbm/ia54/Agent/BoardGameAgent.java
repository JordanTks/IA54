package fr.utbm.ia54.Agent;

import com.google.common.base.Objects;
import fr.utbm.ia54.Agent.EmptyTileAgent;
import fr.utbm.ia54.Agent.FrameAgent;
import fr.utbm.ia54.Agent.TileAgent;
import fr.utbm.ia54.Class.CoordPair;
import fr.utbm.ia54.Class.InfosFrame;
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
  private int PROBLEM_SIZE = 3;
  
  private int nbFrameSet = 0;
  
  private int nbTileSet = 0;
  
  private ArrayList<FrameAgent> frameList = new ArrayList<FrameAgent>();
  
  private ArrayList<UUID> frameUUIDList = new ArrayList<UUID>();
  
  private ArrayList<CoordPair> listCoordPairsOfFrames = new ArrayList<CoordPair>();
  
  private ArrayList<InfosFrame> openListOfFrames = new ArrayList<InfosFrame>();
  
  private ArrayList<InfosFrame> closedListOfFrames = new ArrayList<InfosFrame>();
  
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
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info(((("*************** nbFrameSet=" + Integer.valueOf(this.nbFrameSet)) + " /nbTileSet=") + Integer.valueOf(this.nbTileSet)));
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3.info("!!!!!!!!!!!!! BEGIN TEST OF the A-star ALGORITHM !!!!!!!!!!!!!");
      this.findPath();
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
  private void $behaviorUnit$ActionUI$4(final ActionUI occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("JE VAIS FAIRE CHANGER COULEUR.");
    this.ctrl.setColor("rouge", Integer.valueOf(occurrence.target));
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
  protected InfosFrame addNeighbourFrames(final FrameAgent currentFrame) {
    InfosFrame _xblockexpression = null;
    {
      UUID _northNeighbour = currentFrame.getNorthNeighbour();
      boolean _notEquals = (!Objects.equal(_northNeighbour, null));
      if (_notEquals) {
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("+++++NORTH exists");
        int index = this.frameUUIDList.indexOf(currentFrame.getNorthNeighbour());
        boolean _isSatisfied = this.frameList.get(index).getIsSatisfied();
        boolean _not = (!_isSatisfied);
        if (_not) {
          CoordPair coordNeighbour = this.listCoordPairsOfFrames.get(index);
          Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
          String _string = coordNeighbour.toString();
          String _plus = ("+++++NORTH) coordNeighbour=" + _string);
          _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info(_plus);
          boolean _isInFrameList = this.isInFrameList(coordNeighbour, this.closedListOfFrames);
          boolean _not_1 = (!_isInFrameList);
          if (_not_1) {
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info("+++++NORTH) not in closedList");
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
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3.info(("+++++NORTH) indexForClosedList=" + Integer.valueOf(indexForClosedList)));
            int _costG = this.closedListOfFrames.get(indexForClosedList).getCostG();
            int _distEuclidian = this.distEuclidian(this.frameList.get(index), currentFrame);
            int _plus_1 = (_costG + _distEuclidian);
            infosFrame.setCostG(_plus_1);
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_4 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            int _costG_1 = infosFrame.getCostG();
            String _plus_2 = ("+++++NORTH) infosFrame.costG=" + Integer.valueOf(_costG_1));
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_4.info(_plus_2);
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_5 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            String _string_1 = infosFrame.getCoordsCurrentFrame().toString();
            String _plus_3 = ("+++++NORTH) infosFrame.coords=" + _string_1);
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_5.info(_plus_3);
            FrameAgent arrivalFrame = this.frameList.get(((this.PROBLEM_SIZE * this.PROBLEM_SIZE) - 1));
            infosFrame.setCostH(this.distEuclidian(this.frameList.get(index), arrivalFrame));
            int _costG_2 = infosFrame.getCostG();
            int _costH = infosFrame.getCostH();
            int _plus_4 = (_costG_2 + _costH);
            infosFrame.setCostF(_plus_4);
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_6 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            int _costH_1 = infosFrame.getCostH();
            String _plus_5 = ("+++++NORTH) infosFrame.costH=" + Integer.valueOf(_costH_1));
            String _plus_6 = (_plus_5 + " /infosFrame.costF=");
            int _costF = infosFrame.getCostF();
            String _plus_7 = (_plus_6 + Integer.valueOf(_costF));
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_6.info(_plus_7);
            infosFrame.setUuidPreviousFrame(currentFrame.getID());
            infosFrame.setCoordsPreviousFrame(currentFrame.getCoordPair());
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_7 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            String _string_2 = infosFrame.getCoordsPreviousFrame().toString();
            String _plus_8 = ("+++++NORTH) infosFrame.coordsPreviousFrame.coords=" + _string_2);
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_7.info(_plus_8);
            int indexForOpenList = (-1);
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
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_8 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_8.info(("+++++NORTH) indexForOpenList=" + Integer.valueOf(indexForOpenList)));
            boolean _isInFrameList_1 = this.isInFrameList(coordNeighbour, this.openListOfFrames);
            if (_isInFrameList_1) {
              Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_9 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
              _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_9.info("+++++NORTH) already exists in openList");
              int _costF_1 = infosFrame.getCostF();
              int _costF_2 = this.openListOfFrames.get(indexForOpenList).getCostF();
              boolean _lessThan = (_costF_1 < _costF_2);
              if (_lessThan) {
                Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_10 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
                _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_10.info("+++++NORTH) newPath is shorter: UPDATE it");
                this.openListOfFrames.set(indexForOpenList, infosFrame);
              }
            } else {
              this.openListOfFrames.add(infosFrame);
              Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_11 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
              int _size = this.openListOfFrames.size();
              String _plus_9 = ("+++++NORTH) doesn\'t exists yet in openList: ADD it /openListOfFrames.size=" + Integer.valueOf(_size));
              _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_11.info(_plus_9);
            }
          }
        }
      }
      UUID _southNeighbour = currentFrame.getSouthNeighbour();
      boolean _notEquals_1 = (!Objects.equal(_southNeighbour, null));
      if (_notEquals_1) {
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_12 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_12.info("+++++SOUTH exists");
        int index_1 = this.frameUUIDList.indexOf(currentFrame.getSouthNeighbour());
        boolean _isSatisfied_1 = this.frameList.get(index_1).getIsSatisfied();
        boolean _not_2 = (!_isSatisfied_1);
        if (_not_2) {
          CoordPair coordNeighbour_1 = this.listCoordPairsOfFrames.get(index_1);
          Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_13 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
          String _string_3 = coordNeighbour_1.toString();
          String _plus_10 = ("+++++SOUTH) coordNeighbour=" + _string_3);
          _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_13.info(_plus_10);
          boolean _isInFrameList_2 = this.isInFrameList(coordNeighbour_1, this.closedListOfFrames);
          boolean _not_3 = (!_isInFrameList_2);
          if (_not_3) {
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_14 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_14.info("+++++SOUTH) not in closedList");
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
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_15 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_15.info(("+++++SOUTH) indexForClosedList=" + Integer.valueOf(indexForClosedList_1)));
            int _costG_3 = this.closedListOfFrames.get(indexForClosedList_1).getCostG();
            int _distEuclidian_1 = this.distEuclidian(this.frameList.get(index_1), currentFrame);
            int _plus_11 = (_costG_3 + _distEuclidian_1);
            infosFrame_1.setCostG(_plus_11);
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_16 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            int _costG_4 = infosFrame_1.getCostG();
            String _plus_12 = ("+++++SOUTH) infosFrame.costG=" + Integer.valueOf(_costG_4));
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_16.info(_plus_12);
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_17 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            String _string_4 = infosFrame_1.getCoordsCurrentFrame().toString();
            String _plus_13 = ("+++++SOUTH) infosFrame.coords=" + _string_4);
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_17.info(_plus_13);
            FrameAgent arrivalFrame_1 = this.frameList.get(((this.PROBLEM_SIZE * this.PROBLEM_SIZE) - 1));
            infosFrame_1.setCostH(this.distEuclidian(this.frameList.get(index_1), arrivalFrame_1));
            int _costG_5 = infosFrame_1.getCostG();
            int _costH_2 = infosFrame_1.getCostH();
            int _plus_14 = (_costG_5 + _costH_2);
            infosFrame_1.setCostF(_plus_14);
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_18 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            int _costH_3 = infosFrame_1.getCostH();
            String _plus_15 = ("+++++SOUTH) infosFrame.costH=" + Integer.valueOf(_costH_3));
            String _plus_16 = (_plus_15 + " /infosFrame.costF=");
            int _costF_3 = infosFrame_1.getCostF();
            String _plus_17 = (_plus_16 + Integer.valueOf(_costF_3));
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_18.info(_plus_17);
            infosFrame_1.setUuidPreviousFrame(currentFrame.getID());
            infosFrame_1.setCoordsPreviousFrame(currentFrame.getCoordPair());
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_19 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            String _string_5 = infosFrame_1.getCoordsPreviousFrame().toString();
            String _plus_18 = ("+++++SOUTH) infosFrame.coordsPreviousFrame.coords=" + _string_5);
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_19.info(_plus_18);
            int indexForOpenList_1 = (-1);
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
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_20 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_20.info(("+++++SOUTH) indexForOpenList=" + Integer.valueOf(indexForOpenList_1)));
            boolean _isInFrameList_3 = this.isInFrameList(coordNeighbour_1, this.openListOfFrames);
            if (_isInFrameList_3) {
              Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_21 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
              _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_21.info("+++++SOUTH) already exists in openList");
              int _costF_4 = infosFrame_1.getCostF();
              int _costF_5 = this.openListOfFrames.get(indexForOpenList_1).getCostF();
              boolean _lessThan_1 = (_costF_4 < _costF_5);
              if (_lessThan_1) {
                Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_22 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
                _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_22.info("+++++SOUTH) newPath is shorter: UPDATE it");
                this.openListOfFrames.set(indexForOpenList_1, infosFrame_1);
              }
            } else {
              this.openListOfFrames.add(infosFrame_1);
              Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_23 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
              int _size_1 = this.openListOfFrames.size();
              String _plus_19 = ("+++++SOUTH) doesn\'t exists yet in openList: ADD it /openListOfFrames.size=" + Integer.valueOf(_size_1));
              _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_23.info(_plus_19);
            }
          }
        }
      }
      UUID _eastNeighbour = currentFrame.getEastNeighbour();
      boolean _notEquals_2 = (!Objects.equal(_eastNeighbour, null));
      if (_notEquals_2) {
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_24 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_24.info("+++++EAST exists");
        int index_2 = this.frameUUIDList.indexOf(currentFrame.getEastNeighbour());
        boolean _isSatisfied_2 = this.frameList.get(index_2).getIsSatisfied();
        boolean _not_4 = (!_isSatisfied_2);
        if (_not_4) {
          CoordPair coordNeighbour_2 = this.listCoordPairsOfFrames.get(index_2);
          Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_25 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
          String _string_6 = coordNeighbour_2.toString();
          String _plus_20 = ("+++++EAST) coordNeighbour=" + _string_6);
          _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_25.info(_plus_20);
          boolean _isInFrameList_4 = this.isInFrameList(coordNeighbour_2, this.closedListOfFrames);
          boolean _not_5 = (!_isInFrameList_4);
          if (_not_5) {
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_26 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_26.info("+++++EAST) not in closedList");
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
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_27 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_27.info(("+++++EAST) indexForClosedList=" + Integer.valueOf(indexForClosedList_2)));
            int _costG_6 = this.closedListOfFrames.get(indexForClosedList_2).getCostG();
            int _distEuclidian_2 = this.distEuclidian(this.frameList.get(index_2), currentFrame);
            int _plus_21 = (_costG_6 + _distEuclidian_2);
            infosFrame_2.setCostG(_plus_21);
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_28 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            int _costG_7 = infosFrame_2.getCostG();
            String _plus_22 = ("+++++EAST) infosFrame.costG=" + Integer.valueOf(_costG_7));
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_28.info(_plus_22);
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_29 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            String _string_7 = infosFrame_2.getCoordsCurrentFrame().toString();
            String _plus_23 = ("+++++EAST) infosFrame.coords=" + _string_7);
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_29.info(_plus_23);
            FrameAgent arrivalFrame_2 = this.frameList.get(((this.PROBLEM_SIZE * this.PROBLEM_SIZE) - 1));
            infosFrame_2.setCostH(this.distEuclidian(this.frameList.get(index_2), arrivalFrame_2));
            int _costG_8 = infosFrame_2.getCostG();
            int _costH_4 = infosFrame_2.getCostH();
            int _plus_24 = (_costG_8 + _costH_4);
            infosFrame_2.setCostF(_plus_24);
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_30 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            int _costH_5 = infosFrame_2.getCostH();
            String _plus_25 = ("+++++EAST) infosFrame.costH=" + Integer.valueOf(_costH_5));
            String _plus_26 = (_plus_25 + " /infosFrame.costF=");
            int _costF_6 = infosFrame_2.getCostF();
            String _plus_27 = (_plus_26 + Integer.valueOf(_costF_6));
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_30.info(_plus_27);
            infosFrame_2.setUuidPreviousFrame(currentFrame.getID());
            infosFrame_2.setCoordsPreviousFrame(currentFrame.getCoordPair());
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_31 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            String _string_8 = infosFrame_2.getCoordsPreviousFrame().toString();
            String _plus_28 = ("+++++EAST) infosFrame.coordsPreviousFrame.coords=" + _string_8);
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_31.info(_plus_28);
            int indexForOpenList_2 = (-1);
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
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_32 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_32.info(("+++++EAST) indexForOpenList=" + Integer.valueOf(indexForOpenList_2)));
            boolean _isInFrameList_5 = this.isInFrameList(coordNeighbour_2, this.openListOfFrames);
            if (_isInFrameList_5) {
              Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_33 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
              _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_33.info("+++++EAST) already exists in openList");
              int _costF_7 = infosFrame_2.getCostF();
              int _costF_8 = this.openListOfFrames.get(indexForOpenList_2).getCostF();
              boolean _lessThan_2 = (_costF_7 < _costF_8);
              if (_lessThan_2) {
                Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_34 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
                _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_34.info("+++++EAST) newPath is shorter: UPDATE it");
                this.openListOfFrames.set(indexForOpenList_2, infosFrame_2);
              }
            } else {
              this.openListOfFrames.add(infosFrame_2);
              Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_35 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
              int _size_2 = this.openListOfFrames.size();
              String _plus_29 = ("+++++EAST) doesn\'t exists yet in openList: ADD it /openListOfFrames.size=" + Integer.valueOf(_size_2));
              _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_35.info(_plus_29);
            }
          }
        }
      }
      InfosFrame _xifexpression = null;
      UUID _westNeighbour = currentFrame.getWestNeighbour();
      boolean _notEquals_3 = (!Objects.equal(_westNeighbour, null));
      if (_notEquals_3) {
        InfosFrame _xblockexpression_1 = null;
        {
          Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_36 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
          _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_36.info("+++++WEST exists");
          int index_3 = this.frameUUIDList.indexOf(currentFrame.getWestNeighbour());
          InfosFrame _xifexpression_1 = null;
          boolean _isSatisfied_3 = this.frameList.get(index_3).getIsSatisfied();
          boolean _not_6 = (!_isSatisfied_3);
          if (_not_6) {
            InfosFrame _xblockexpression_2 = null;
            {
              CoordPair coordNeighbour_3 = this.listCoordPairsOfFrames.get(index_3);
              Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_37 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
              String _string_9 = coordNeighbour_3.toString();
              String _plus_30 = ("+++++WEST) coordNeighbour=" + _string_9);
              _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_37.info(_plus_30);
              InfosFrame _xifexpression_2 = null;
              boolean _isInFrameList_6 = this.isInFrameList(coordNeighbour_3, this.closedListOfFrames);
              boolean _not_7 = (!_isInFrameList_6);
              if (_not_7) {
                InfosFrame _xblockexpression_3 = null;
                {
                  Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_38 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
                  _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_38.info("+++++WEST) not in closedList");
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
                  Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_39 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
                  _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_39.info(("+++++WEST) indexForClosedList=" + Integer.valueOf(indexForClosedList_3)));
                  int _costG_9 = this.closedListOfFrames.get(indexForClosedList_3).getCostG();
                  int _distEuclidian_3 = this.distEuclidian(this.frameList.get(index_3), currentFrame);
                  int _plus_31 = (_costG_9 + _distEuclidian_3);
                  infosFrame_3.setCostG(_plus_31);
                  Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_40 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
                  int _costG_10 = infosFrame_3.getCostG();
                  String _plus_32 = ("+++++WEST) infosFrame.costG=" + Integer.valueOf(_costG_10));
                  _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_40.info(_plus_32);
                  Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_41 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
                  String _string_10 = infosFrame_3.getCoordsCurrentFrame().toString();
                  String _plus_33 = ("+++++WEST) infosFrame.coords=" + _string_10);
                  _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_41.info(_plus_33);
                  FrameAgent arrivalFrame_3 = this.frameList.get(((this.PROBLEM_SIZE * this.PROBLEM_SIZE) - 1));
                  infosFrame_3.setCostH(this.distEuclidian(this.frameList.get(index_3), arrivalFrame_3));
                  int _costG_11 = infosFrame_3.getCostG();
                  int _costH_6 = infosFrame_3.getCostH();
                  int _plus_34 = (_costG_11 + _costH_6);
                  infosFrame_3.setCostF(_plus_34);
                  Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_42 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
                  int _costH_7 = infosFrame_3.getCostH();
                  String _plus_35 = ("+++++WEST) infosFrame.costH=" + Integer.valueOf(_costH_7));
                  String _plus_36 = (_plus_35 + " /infosFrame.costF=");
                  int _costF_9 = infosFrame_3.getCostF();
                  String _plus_37 = (_plus_36 + Integer.valueOf(_costF_9));
                  _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_42.info(_plus_37);
                  infosFrame_3.setUuidPreviousFrame(currentFrame.getID());
                  infosFrame_3.setCoordsPreviousFrame(currentFrame.getCoordPair());
                  Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_43 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
                  String _string_11 = infosFrame_3.getCoordsPreviousFrame().toString();
                  String _plus_38 = ("+++++WEST) infosFrame.coordsPreviousFrame.coords=" + _string_11);
                  _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_43.info(_plus_38);
                  int indexForOpenList_3 = (-1);
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
                  Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_44 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
                  _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_44.info(("+++++WEST) indexForOpenList=" + Integer.valueOf(indexForOpenList_3)));
                  InfosFrame _xifexpression_3 = null;
                  boolean _isInFrameList_7 = this.isInFrameList(coordNeighbour_3, this.openListOfFrames);
                  if (_isInFrameList_7) {
                    InfosFrame _xblockexpression_4 = null;
                    {
                      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_45 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
                      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_45.info("+++++WEST) already exists in openList");
                      InfosFrame _xifexpression_4 = null;
                      int _costF_10 = infosFrame_3.getCostF();
                      int _costF_11 = this.openListOfFrames.get(indexForOpenList_3).getCostF();
                      boolean _lessThan_3 = (_costF_10 < _costF_11);
                      if (_lessThan_3) {
                        InfosFrame _xblockexpression_5 = null;
                        {
                          Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_46 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
                          _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_46.info("+++++WEST) newPath is shorter: UPDATE it");
                          _xblockexpression_5 = this.openListOfFrames.set(indexForOpenList_3, infosFrame_3);
                        }
                        _xifexpression_4 = _xblockexpression_5;
                      }
                      _xblockexpression_4 = _xifexpression_4;
                    }
                    _xifexpression_3 = _xblockexpression_4;
                  } else {
                    this.openListOfFrames.add(infosFrame_3);
                    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_45 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
                    int _size_3 = this.openListOfFrames.size();
                    String _plus_39 = ("+++++WEST) doesn\'t exists yet in openList: ADD it /openListOfFrames.size=" + Integer.valueOf(_size_3));
                    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_45.info(_plus_39);
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
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    int _size = listFrames.size();
    String _plus = ("////////////////////////getBestFrames(): listFrames.size" + Integer.valueOf(_size));
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info(_plus);
    boolean _isEmpty = listFrames.isEmpty();
    boolean _not = (!_isEmpty);
    if (_not) {
      int costF = listFrames.get(0).getCostF();
      InfosFrame infoFrame = listFrames.get(0);
      for (final InfosFrame frame : listFrames) {
        {
          Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
          String _string = frame.getCoordsCurrentFrame().toString();
          String _plus_1 = ("////////////////////////getBestFrames(): frame.coords=" + _string);
          _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info(_plus_1);
          Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
          String _stringGHF = frame.toStringGHF();
          String _plus_2 = ("////////////////////////getBestFrames(): frame.costGHF=" + _stringGHF);
          _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info(_plus_2);
          int _costF = frame.getCostF();
          boolean _lessThan = (_costF < costF);
          if (_lessThan) {
            Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
            _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3.info("////////////////////////getBestFrames(): get a better one (lower)");
            costF = frame.getCostF();
            infoFrame = frame;
          }
        }
      }
      Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
      String _string = infoFrame.getCoordsCurrentFrame().toString();
      String _plus_1 = ("////////////////////////getBestFrames(): return " + _string);
      _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info(_plus_1);
      return infoFrame;
    }
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info("////////////////////////getBestFrames(): return NULL");
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
  
  protected void findPath() {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("--------------BEGIN function findPath--------------");
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_1.info(("######## 0)nbFrameSet=" + Integer.valueOf(this.nbFrameSet)));
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_2.info(("######## 0)lastNumber=" + Integer.valueOf(((this.PROBLEM_SIZE * this.PROBLEM_SIZE) - 1))));
    UUID _get = this.frameUUIDList.get(1);
    CoordPair _get_1 = this.listCoordPairsOfFrames.get(8);
    final InfosFrame beginningFrame = new InfosFrame(_get, _get_1);
    UUID _get_2 = this.frameUUIDList.get(((this.PROBLEM_SIZE * this.PROBLEM_SIZE) - 1));
    CoordPair _get_3 = this.listCoordPairsOfFrames.get(((this.PROBLEM_SIZE * this.PROBLEM_SIZE) - 1));
    final InfosFrame arrivalFrame = new InfosFrame(_get_2, _get_3);
    InfosFrame currentFrame = beginningFrame;
    this.openListOfFrames.add(beginningFrame);
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    int _size = this.openListOfFrames.size();
    String _plus = ("######## 1)openListOfFrames.size=" + Integer.valueOf(_size));
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_3.info(_plus);
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_4 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    String _string = arrivalFrame.getCoordsCurrentFrame().toString();
    String _plus_1 = ("######## 1)arrivalFrame.coords=" + _string);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_4.info(_plus_1);
    this.addInClosedList(beginningFrame.getCoordsCurrentFrame());
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_5 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    int _size_1 = this.openListOfFrames.size();
    String _plus_2 = ("######## 2)openListOfFrames.size=" + Integer.valueOf(_size_1));
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_5.info(_plus_2);
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_6 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    int _size_2 = this.closedListOfFrames.size();
    String _plus_3 = ("######## 2)closedListOfFrames.size=" + Integer.valueOf(_size_2));
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_6.info(_plus_3);
    int indexOfFrameList = (-1);
    for (final FrameAgent ite : this.frameList) {
      {
        indexOfFrameList++;
        boolean _equals = ite.getCoordPair().equals(beginningFrame.getCoordsCurrentFrame());
        if (_equals) {
          break;
        }
      }
    }
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_7 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    int _size_3 = this.frameList.size();
    String _plus_4 = ("######## 3)frameList=" + Integer.valueOf(_size_3));
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_7.info(_plus_4);
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_8 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_8.info(("######## 3)indexOfFrameList=" + Integer.valueOf(indexOfFrameList)));
    this.addNeighbourFrames(this.frameList.get(indexOfFrameList));
    while (((!currentFrame.getCoordsCurrentFrame().equals(arrivalFrame.getCoordsCurrentFrame())) && (!this.openListOfFrames.isEmpty()))) {
      {
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_9 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_9.info("######## 4)while++");
        currentFrame = this.getBestFrame(this.openListOfFrames);
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_10 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
        String _string_1 = currentFrame.getCoordsCurrentFrame().toString();
        String _plus_5 = ("######## 4)bestFrame=" + _string_1);
        String _plus_6 = (_plus_5 + " /openListOfFrames.size=");
        int _size_4 = this.openListOfFrames.size();
        String _plus_7 = (_plus_6 + Integer.valueOf(_size_4));
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_10.info(_plus_7);
        this.addInClosedList(currentFrame.getCoordsCurrentFrame());
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_11 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
        int _size_5 = this.closedListOfFrames.size();
        String _plus_8 = ("######## 4)addInClosedList-->closedListOfFrames.size=" + Integer.valueOf(_size_5));
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_11.info(_plus_8);
      }
    }
    int i = (-1);
    for (final InfosFrame ite_1 : this.closedListOfFrames) {
      {
        i++;
        Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_9 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
        String _string_1 = ite_1.getCoordsCurrentFrame().toString();
        String _plus_5 = ((("closed[" + Integer.valueOf(i)) + "]=") + _string_1);
        _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_9.info(_plus_5);
      }
    }
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_9 = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER_9.info("--------------END function findPath--------------");
  }
  
  @SyntheticMember
  private void $behaviorUnit$Destroy$5(final Destroy occurrence) {
    Logging _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER = this.$castSkill(Logging.class, (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING == null || this.$CAPACITY_USE$IO_SARL_CORE_LOGGING.get() == null) ? (this.$CAPACITY_USE$IO_SARL_CORE_LOGGING = this.$getSkill(Logging.class)) : this.$CAPACITY_USE$IO_SARL_CORE_LOGGING);
    _$CAPACITY_USE$IO_SARL_CORE_LOGGING$CALLER.info("The agent was stopped.");
  }
  
  @SyntheticMember
  private void $behaviorUnit$AgentSpawned$6(final AgentSpawned occurrence) {
  }
  
  @SyntheticMember
  private void $behaviorUnit$AgentKilled$7(final AgentKilled occurrence) {
  }
  
  @SyntheticMember
  private void $behaviorUnit$ContextJoined$8(final ContextJoined occurrence) {
  }
  
  @SyntheticMember
  private void $behaviorUnit$ContextLeft$9(final ContextLeft occurrence) {
  }
  
  @SyntheticMember
  private void $behaviorUnit$MemberJoined$10(final MemberJoined occurrence) {
  }
  
  @SyntheticMember
  private void $behaviorUnit$MemberLeft$11(final MemberLeft occurrence) {
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
  private void $guardEvaluator$ActionUI(final ActionUI occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ActionUI$4(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ContextLeft(final ContextLeft occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ContextLeft$9(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$ContextJoined(final ContextJoined occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$ContextJoined$8(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$MemberLeft(final MemberLeft occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$MemberLeft$11(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AgentSpawned(final AgentSpawned occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AgentSpawned$6(occurrence));
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
  private void $guardEvaluator$Destroy(final Destroy occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$Destroy$5(occurrence));
  }
  
  @SyntheticMember
  @PerceptGuardEvaluator
  private void $guardEvaluator$AgentKilled(final AgentKilled occurrence, final Collection<Runnable> ___SARLlocal_runnableCollection) {
    assert occurrence != null;
    assert ___SARLlocal_runnableCollection != null;
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$AgentKilled$7(occurrence));
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
    ___SARLlocal_runnableCollection.add(() -> $behaviorUnit$MemberJoined$10(occurrence));
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
