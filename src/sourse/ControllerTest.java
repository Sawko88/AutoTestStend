package sourse;

import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static java.awt.event.WindowEvent.WINDOW_CLOSED;
import static java.awt.event.WindowEvent.WINDOW_CLOSING;

public  class ControllerTest implements Initializable{
    public JFXToggleButton Pult;
    public Stage stagePult;
    public ControllerPultMX controllerPultMX;
    public ToolBar tool3;
    public ToolBar tool1;
    public VBox vBoxDrag;
    public Button btnAdd;
    public VBox vbAdd;
    public static Map<ToolBar, TableElement> tableTb = new LinkedHashMap<ToolBar, TableElement>();


    public void PultAction(ActionEvent actionEvent) throws IOException {
        if (Pult.isSelected()){
            stagePult.show();
        } else {
            //controllerPultMX.CloseWin();
            stagePult.hide();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        stagePult = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/PultMX.fxml"));
            stagePult.setScene(new Scene(root));
            stagePult.initModality(Modality.WINDOW_MODAL);

            //stagePult.show();



        } catch (IOException e) {
            e.printStackTrace();
        }

        //controllerPultMX.init(this);

        Pult.setSelected(false);


    }



    //public int count = 0;
    public void ActBtnAdd(ActionEvent actionEvent) throws IOException {
        //ToolBar toolBar = new ToolBar(new Button("name"+count));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ControllerTest.class.getResource("/fxml/Element.fxml"));
        ToolBar toolBarMy = loader.load();
        //ToolBar toolBarMy = (ToolBar) FXMLLoader.load(getClass().getResource("/fxml/Element.fxml"));
        Element element = loader.getController();
        element.SetMainApp(this);
        //count++;


        addWithDragging(vbAdd, toolBarMy);
        int index = vbAdd.getChildren().indexOf(toolBarMy);
        tableTb.put(toolBarMy, new TableElement(index,element));


        element.init();


    }

    private void addWithDragging(final VBox root, final ToolBar toolBar) {
        toolBar.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                toolBar.startFullDrag();
            }
        });

        // next two handlers just an idea how to show the drop target visually:
        toolBar.setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                toolBar.setStyle("-fx-background-color: #77c2bb;");
            }
        });
        toolBar.setOnMouseDragExited(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                toolBar.setStyle("");
            }
        });

        toolBar.setOnMouseDragReleased(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                toolBar.setStyle("-fx-background-color: #77c2bb;");
                int indexOfDraggingNode = root.getChildren().indexOf(event.getGestureSource());
                int indexOfDropTarget = root.getChildren().indexOf(toolBar);
                rotateNodes1(root, indexOfDraggingNode, indexOfDropTarget);
                //RotateIndexTb(root,indexOfDraggingNode, indexOfDropTarget, toolBar);
                event.consume();
            }
        });

        root.getChildren().add(toolBar);
    }

    public void RotateIndexTb(VBox root, int indexOfDraggingNode, int indexOfDropTarget, ToolBar toolBar) {
        //(tableTb.get(toolBar)).element.labPos.setText(String.valueOf(indexOfDraggingNode));
    }

    private void rotateNodes1(final VBox root, final int indexOfDraggingNode,
                             final int indexOfDropTarget) {
        if (indexOfDraggingNode >= 0 && indexOfDropTarget >= 0) {
            final Node node = root.getChildren().remove(indexOfDraggingNode);
            root.getChildren().add(indexOfDropTarget, node);

            int begin =0;
            int end = 0;
            begin = (indexOfDraggingNode>indexOfDropTarget)? indexOfDropTarget: indexOfDraggingNode;
            end = (indexOfDraggingNode>indexOfDropTarget)? indexOfDraggingNode: indexOfDropTarget;
            //int cur = begin;
            while (begin<=end){
                ToolBar tt = (ToolBar) root.getChildren().get(begin);
                (tableTb.get(tt)).element.SetPosition(begin);
                //System.out.println("index = " + tableTb.get(tt).index);
                begin++;
            }

        }
    }
}
