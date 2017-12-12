package sourse;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class PersonTestController {
    public Button btnOk;
    public Button btnCancel;
    public TextField tfName;
    public Element element;
    public TextField tfPos;
    public VBox vbSpisokTest;
    public Button btnAddTest;

    public void BtnOkAct(ActionEvent actionEvent) {
        SetParamElement();
        Stage stage = (Stage) btnOk.getScene().getWindow();
        stage.close();
    }

    private void SetParamElement() {
        element.SetName(tfName.getText());
    }

    public void BtnCancelAct(ActionEvent actionEvent) {
        Stage stage = (Stage) btnOk.getScene().getWindow();
        stage.close();
    }

    public void init() {
    }

    public void SetElement(Element element) {
        this.element = element;
    }

    public void SetPerson() {
        tfName.setText(element.getName());
        tfPos.setText(element.getPos());
    }

    public void BtnAddTestAct(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PersonTestController.class.getResource("/fxml/OneAction.fxml"));
        ToolBar toolBar = loader.load();
        //ToolBar toolBarMy = (ToolBar) FXMLLoader.load(getClass().getResource("/fxml/Element.fxml"));
        OneActionController oneAct = loader.getController();

        addWithDragging(vbSpisokTest, toolBar);

        oneAct.SetMainApp(this);
        oneAct.initOneAct();
        oneAct.SetLabel(vbSpisokTest.getChildren().indexOf(toolBar));
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

                event.consume();
            }
        });

        root.getChildren().add(toolBar);
    }


    private void rotateNodes1(final VBox root, final int indexOfDraggingNode,
                              final int indexOfDropTarget) {
        if (indexOfDraggingNode >= 0 && indexOfDropTarget >= 0) {
            final Node node = root.getChildren().remove(indexOfDraggingNode);
            root.getChildren().add(indexOfDropTarget, node);

           /* int begin =0;
            int end = 0;
            begin = (indexOfDraggingNode>indexOfDropTarget)? indexOfDropTarget: indexOfDraggingNode;
            end = (indexOfDraggingNode>indexOfDropTarget)? indexOfDraggingNode: indexOfDropTarget;
            //int cur = begin;
            while (begin<=end){
                ToolBar tt = (ToolBar) root.getChildren().get(begin);
                (tableTb.get(tt)).element.SetPosition(begin);
                System.out.println("index = " + tableTb.get(tt).index);
                begin++;
            }*/

        }
    }
}
