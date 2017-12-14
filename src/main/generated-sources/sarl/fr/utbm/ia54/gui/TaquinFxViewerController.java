package fr.utbm.ia54.gui;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
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
      this.tilePane.setStyle("-fx-background-color: rgba(255, 215, 0, 0.1);");
      this.tilePane.setHgap(this.GAP);
      this.tilePane.setVgap(this.GAP);
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
      for (int i = 0; (i < this.nCols); i++) {
        for (int j = 0; (j < this.nRows); j++) {
          {
            int _size = a.size();
            boolean _lessThan = (this.cpt < _size);
            if (_lessThan) {
              String _string = a.get(this.cpt).toString();
              Text text = new Text(_string);
              text.prefHeight(400);
              this.tilePane.getChildren().add(text);
            } else {
              Text text_1 = new Text(" ");
              text_1.prefHeight(400);
              this.tilePane.getChildren().add(text_1);
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
  
  private TaquinFxViewerController.GridDisplay gridDisplay = new TaquinFxViewerController.GridDisplay(0, 0);
  
  @FXML
  private Pane pane;
  
  @FXML
  private TextField size;
  
  @Pure
  public int getProbSize() {
    return Integer.parseInt(this.size.getText());
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
      int _probSize = this.getProbSize();
      ConfigureSimulation event = new ConfigureSimulation(_probSize);
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
  public void monTest(final List<Integer> a) {
    abstract class __TaquinFxViewerController_0 implements Runnable {
      public abstract void run();
    }
    
    __TaquinFxViewerController_0 ___TaquinFxViewerController_0 = new __TaquinFxViewerController_0() {
      public void run() {
        TaquinFxViewerController.this.gridDisplay.construct(a);
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
            Text t = ((Text) _get);
            boolean _equals = t.getText().equals(Integer.toString((numTile).intValue()));
            if (_equals) {
              boolean _equals_1 = color.equals("red");
              if (_equals_1) {
                t.setFill(Color.RED);
              } else {
                boolean _equals_2 = color.equals("blue");
                if (_equals_2) {
                  t.setFill(Color.BLUE);
                } else {
                  boolean _equals_3 = color.equals("green");
                  if (_equals_3) {
                    t.setFill(Color.GREEN);
                  } else {
                    boolean _equals_4 = color.equals("purple");
                    if (_equals_4) {
                      t.setFill(Color.PURPLE);
                    } else {
                      boolean _equals_5 = color.equals("pink");
                      if (_equals_5) {
                        t.setFill(Color.PINK);
                      } else {
                        boolean _equals_6 = color.equals("black");
                        if (_equals_6) {
                          t.setFill(Color.BLACK);
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
  @Pure
  public void reset() {
    abstract class __TaquinFxViewerController_2 implements Runnable {
      public abstract void run();
    }
    
    __TaquinFxViewerController_2 ___TaquinFxViewerController_2 = new __TaquinFxViewerController_2() {
      public void run() {
        ObservableList<Node> listTiles = TaquinFxViewerController.this.gridDisplay.tilePane.getChildren();
        for (int i = 0; (i < listTiles.size()); i++) {
          {
            Node _get = listTiles.get(i);
            Text t = ((Text) _get);
            t.setFill(Color.BLACK);
          }
        }
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
        for (int i = 0; (i < listTiles.size()); i++) {
          {
            Node _get = listTiles.get(i);
            Text t = ((Text) _get);
            boolean _equals = t.getText().equals(" ");
            if (_equals) {
              tile1 = t;
            }
            boolean _equals_1 = t.getText().equals(Integer.toString(number));
            if (_equals_1) {
              tile2 = t;
            }
          }
        }
        String c = "";
        c = tile1.getText();
        tile1.setText(tile2.getText());
        tile2.setText(c);
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
    return result;
  }
  
  @SyntheticMember
  public TaquinFxViewerController() {
    super();
  }
}
