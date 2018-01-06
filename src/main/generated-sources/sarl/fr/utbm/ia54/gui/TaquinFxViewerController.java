package fr.utbm.ia54.gui;

import fr.utbm.ia54.Class.StaticVars;
import fr.utbm.ia54.Event.ConfigureSimulation;
import fr.utbm.ia54.gui.fx.FxViewerController;
import io.sarl.lang.annotation.SarlElementType;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.6")
@SarlElementType(9)
@SuppressWarnings("all")
public class TaquinFxViewerController extends FxViewerController {
  @SarlSpecification("0.6")
  @SarlElementType(9)
  public static class GridDisplay {
    private TilePane tilePane = new TilePane();
    
    private Group display = new Group(this.tilePane);
    
    private final double ELEMENT_SIZE = 50;
    
    private final double GAP = (this.ELEMENT_SIZE / 10);
    
    private int nRows;
    
    private int nCols;
    
    private int cpt = 0;
    
    public GridDisplay(final int nRows, final int nCols) {
      this.tilePane.setHgap(5);
      this.tilePane.setVgap(5);
      this.setColumns(nCols);
      this.setRows(nRows);
    }
    
    public void setColumns(final int newColumns) {
      this.nCols = newColumns;
      this.tilePane.setPrefColumns(this.nCols);
    }
    
    public void setRows(final int newRows) {
      this.nRows = newRows;
      this.tilePane.setPrefRows(this.nRows);
    }
    
    public void construct(final List<Integer> a) {
      this.tilePane.getChildren().clear();
      this.cpt = 0;
      ObservableList<Node> list = this.tilePane.getChildren();
      for (int i = 0; (i < this.nCols); i++) {
        for (int j = 0; (j < this.nRows); j++) {
          {
            Group root = new Group();
            int _size = a.size();
            boolean _lessThan = (this.cpt < _size);
            if (_lessThan) {
              String _string = a.get(this.cpt).toString();
              Text text = new Text(_string);
              text.setX(5);
              text.setY(22);
              text.setFill(Color.WHITE);
              text.setStyle("-fx-font-size: 20px;");
              Rectangle rect = new Rectangle(30, 30);
              rect.setFill(Color.GREY);
              root.getChildren().add(rect);
              root.getChildren().add(text);
              this.tilePane.getChildren().add(root);
            } else {
              Text text_1 = new Text(" ");
              text_1.setX(5);
              text_1.setY(22);
              text_1.setFill(Color.WHITE);
              text_1.setStyle("-fx-font-size: 20px;");
              Rectangle rect_1 = new Rectangle(30, 30);
              rect_1.setFill(Color.WHITE);
              root.getChildren().add(rect_1);
              root.getChildren().add(text_1);
              this.tilePane.getChildren().add(root);
            }
            this.cpt++;
          }
        }
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
      GridDisplay other = (GridDisplay) obj;
      if (Double.doubleToLongBits(other.ELEMENT_SIZE) != Double.doubleToLongBits(this.ELEMENT_SIZE))
        return false;
      if (Double.doubleToLongBits(other.GAP) != Double.doubleToLongBits(this.GAP))
        return false;
      if (other.nRows != this.nRows)
        return false;
      if (other.nCols != this.nCols)
        return false;
      if (other.cpt != this.cpt)
        return false;
      return super.equals(obj);
    }
    
    @Override
    @Pure
    @SyntheticMember
    public int hashCode() {
      int result = super.hashCode();
      final int prime = 31;
      result = prime * result + (int) (Double.doubleToLongBits(this.ELEMENT_SIZE) ^ (Double.doubleToLongBits(this.ELEMENT_SIZE) >>> 32));
      result = prime * result + (int) (Double.doubleToLongBits(this.GAP) ^ (Double.doubleToLongBits(this.GAP) >>> 32));
      result = prime * result + this.nRows;
      result = prime * result + this.nCols;
      result = prime * result + this.cpt;
      return result;
    }
  }
  
  private boolean launched = false;
  
  private boolean mapCreated = false;
  
  private int intSpeed = 0;
  
  private boolean istimeFix = false;
  
  private int timeout = 30;
  
  private TaquinFxViewerController.GridDisplay gridDisplay = new TaquinFxViewerController.GridDisplay(0, 0);
  
  @FXML
  private Pane pane;
  
  @FXML
  private TextField size;
  
  @FXML
  private Text timeOut;
  
  @FXML
  private Slider idSliderSpeed;
  
  @FXML
  private Text idTextSpeed;
  
  @FXML
  private RadioButton idRadioCentralized;
  
  @FXML
  private RadioButton idRadioDistributed;
  
  @FXML
  private CheckBox checkTime;
  
  @FXML
  private TextField time;
  
  @FXML
  public void RefreshSpeed() {
    double _value = this.idSliderSpeed.getValue();
    this.intSpeed = ((int) _value);
    String textSpeed = null;
    if ((this.intSpeed == 0)) {
      textSpeed = "SLOW";
      StaticVars.speed = 250;
      this.idTextSpeed.setText(("Speed: " + textSpeed));
    } else {
      if ((this.intSpeed == 1)) {
        textSpeed = "NORMAL";
        StaticVars.speed = 150;
        this.idTextSpeed.setText(("Speed: " + textSpeed));
      } else {
        if ((this.intSpeed == 2)) {
          textSpeed = "FAST";
          StaticVars.speed = 50;
          this.idTextSpeed.setText(("Speed: " + textSpeed));
        } else {
          if ((this.intSpeed == 3)) {
            textSpeed = "FASTER";
            StaticVars.speed = 30;
            this.idTextSpeed.setText(("Speed: " + textSpeed));
          } else {
            textSpeed = "The FLASH";
            StaticVars.speed = 0;
            this.idTextSpeed.setText(("Speed of " + textSpeed));
          }
        }
      }
    }
  }
  
  @Pure
  public int getProbSize() {
    return Integer.parseInt(this.size.getText());
  }
  
  @Pure
  public boolean getDistributedVal() {
    return this.idRadioDistributed.isSelected();
  }
  
  @FXML
  public Boolean startSimu() {
    boolean _xblockexpression = false;
    {
      this.gridDisplay.setColumns(this.getProbSize());
      this.gridDisplay.setRows(this.getProbSize());
      boolean _contains = this.pane.getChildren().contains(this.gridDisplay.display);
      boolean _not = (!_contains);
      if (_not) {
        this.pane.getChildren().add(this.gridDisplay.display);
      }
      StaticVars.problemSize = this.getProbSize();
      StaticVars.isDistributed = this.getDistributedVal();
      StaticVars.isTimeFix = this.istimeFix;
      if (this.istimeFix) {
        StaticVars.timeout = Integer.parseInt(this.time.getText());
      } else {
        StaticVars.timeout = 30;
      }
      ConfigureSimulation event = new ConfigureSimulation();
      boolean _xifexpression = false;
      if ((!this.launched)) {
        boolean _xblockexpression_1 = false;
        {
          final Procedure0 _function = () -> {
            this.emitToAgents(event);
          };
          this.startAgentApplication(_function);
          this.launched = true;
          _xblockexpression_1 = this.mapCreated = false;
        }
        _xifexpression = _xblockexpression_1;
      } else {
        this.emitToAgents(event);
      }
      _xblockexpression = _xifexpression;
    }
    return Boolean.valueOf(_xblockexpression);
  }
  
  @FXML
  @Pure
  public void constructGrid(final List<Integer> list) {
    abstract class __TaquinFxViewerController_0 implements Runnable {
      public abstract void run();
    }
    
    __TaquinFxViewerController_0 ___TaquinFxViewerController_0 = new __TaquinFxViewerController_0() {
      public void run() {
        TaquinFxViewerController.this.gridDisplay.construct(list);
      }
    };
    Platform.runLater(___TaquinFxViewerController_0);
  }
  
  @FXML
  public void setColor(final String color, final Integer numTile) {
    abstract class __TaquinFxViewerController_1 implements Runnable {
      public abstract void run();
    }
    
    __TaquinFxViewerController_1 ___TaquinFxViewerController_1 = new __TaquinFxViewerController_1() {
      public void run() {
        ObservableList<Node> listTiles = TaquinFxViewerController.this.gridDisplay.tilePane.getChildren();
        for (int i = 0; (i < listTiles.size()); i++) {
          {
            Node _get = listTiles.get(i);
            Group group = ((Group) _get);
            Node _get_1 = group.getChildren().get(0);
            Rectangle rect = ((Rectangle) _get_1);
            Node _get_2 = group.getChildren().get(1);
            Text t = ((Text) _get_2);
            boolean _equals = t.getText().equals(Integer.toString((numTile).intValue()));
            if (_equals) {
              boolean _equals_1 = color.equals("red");
              if (_equals_1) {
                rect.setFill(Color.RED);
              } else {
                boolean _equals_2 = color.equals("blue");
                if (_equals_2) {
                  rect.setFill(Color.BLUE);
                } else {
                  boolean _equals_3 = color.equals("green");
                  if (_equals_3) {
                    rect.setFill(Color.GREEN);
                  } else {
                    boolean _equals_4 = color.equals("purple");
                    if (_equals_4) {
                      rect.setFill(Color.PURPLE);
                    } else {
                      boolean _equals_5 = color.equals("orange");
                      if (_equals_5) {
                        rect.setFill(Color.DARKORANGE);
                      } else {
                        boolean _equals_6 = color.equals("turquoise");
                        if (_equals_6) {
                          rect.setFill(Color.DARKTURQUOISE);
                        }
                      }
                    }
                  }
                }
              }
              return;
            }
          }
        }
      }
    };
    Platform.runLater(___TaquinFxViewerController_1);
  }
  
  @FXML
  public boolean disabledTime() {
    boolean _xblockexpression = false;
    {
      this.time.setDisable(this.istimeFix);
      _xblockexpression = this.istimeFix = (!this.istimeFix);
    }
    return _xblockexpression;
  }
  
  @FXML
  @Pure
  public void updateTimeoutUi() {
    abstract class __TaquinFxViewerController_2 implements Runnable {
      public abstract void run();
    }
    
    __TaquinFxViewerController_2 ___TaquinFxViewerController_2 = new __TaquinFxViewerController_2() {
      public void run() {
        TaquinFxViewerController.this.timeOut.setText(("" + Integer.valueOf(StaticVars.timeout)));
      }
    };
    Platform.runLater(___TaquinFxViewerController_2);
  }
  
  @FXML
  @Pure
  public void swap(final int number) {
    abstract class __TaquinFxViewerController_3 implements Runnable {
      public abstract void run();
    }
    
    __TaquinFxViewerController_3 ___TaquinFxViewerController_3 = new __TaquinFxViewerController_3() {
      public void run() {
        ObservableList<Node> listTiles = TaquinFxViewerController.this.gridDisplay.tilePane.getChildren();
        Text tile1 = null;
        Text tile2 = null;
        Rectangle rect1 = null;
        Rectangle rect2 = null;
        for (int i = 0; (i < listTiles.size()); i++) {
          {
            Node _get = listTiles.get(i);
            Group group = ((Group) _get);
            Node _get_1 = group.getChildren().get(0);
            Rectangle rect = ((Rectangle) _get_1);
            Node _get_2 = group.getChildren().get(1);
            Text t = ((Text) _get_2);
            boolean _equals = t.getText().equals(" ");
            if (_equals) {
              tile1 = t;
              rect1 = rect;
            }
            boolean _equals_1 = t.getText().equals(Integer.toString(number));
            if (_equals_1) {
              tile2 = t;
              rect2 = rect;
            }
          }
        }
        rect2.setFill(Color.WHITE);
        rect1.setFill(Color.GREY);
        tile1.setText(tile2.getText());
        tile2.setText(" ");
      }
    };
    Platform.runLater(___TaquinFxViewerController_3);
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
    TaquinFxViewerController other = (TaquinFxViewerController) obj;
    if (other.launched != this.launched)
      return false;
    if (other.mapCreated != this.mapCreated)
      return false;
    if (other.intSpeed != this.intSpeed)
      return false;
    if (other.istimeFix != this.istimeFix)
      return false;
    if (other.timeout != this.timeout)
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    int result = super.hashCode();
    final int prime = 31;
    result = prime * result + (this.launched ? 1231 : 1237);
    result = prime * result + (this.mapCreated ? 1231 : 1237);
    result = prime * result + this.intSpeed;
    result = prime * result + (this.istimeFix ? 1231 : 1237);
    result = prime * result + this.timeout;
    return result;
  }
  
  @SyntheticMember
  public TaquinFxViewerController() {
    super();
  }
}
