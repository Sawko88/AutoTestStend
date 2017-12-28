package sourse;

import com.jfoenix.controls.JFXCheckBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PersonTestController implements Initializable{
    public Button btnOk;
    public Button btnCancel;
    public TextField tfName;
    public Element element;
    public TextField tfPos;
    public VBox vbSpisokTest;
    public Button btnAddTest;

    //public Map<Integer, TableTest> actionListbuf = new LinkedHashMap<Integer, TableTest>();
    public Map<ToolBar, OneActionController> controllerList = new LinkedHashMap<ToolBar, OneActionController>();
    public JFXCheckBox cbSignal;
    public CheckComboBox ccbSignal;
    private SignalCollection signalCollection = new SignalCollection();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //final ObservableList<String> strings = FXCollections.observableArrayList();
        for (int i = 0; i < signalCollection.signalSpisok.size(); i++) {
            ccbSignal.getItems().add(signalCollection.signalSpisok.get(i).name);

        }
        ccbSignal.setDisable(true);
        cbSignal.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (cbSignal.isSelected()){
                    ccbSignal.setDisable(false);
                } else {
                    ccbSignal.setDisable(true);
                    ccbSignal.getCheckModel().clearChecks();
                }
            }
        });

    }

    public void BtnOkAct(ActionEvent actionEvent) {
        SetParamElement();
        Stage stage = (Stage) btnOk.getScene().getWindow();
        stage.close();
    }

    private void SetParamElement() {
        element.SetName(tfName.getText());
        element.actionList.clear();
        //System.out.println(vbSpisokTest.getChildren().size());
        if (vbSpisokTest.getChildren().size()>0) {
            for (int i = 0; i < (vbSpisokTest.getChildren().size()); i++) {
                OneActionController one = controllerList.get(vbSpisokTest.getChildren().get(i));
                Nastroika nasBuf ;
                nasBuf = one.GetNastroika();

                element.actionList.put(i, new TableTest(nasBuf));//
            }
        }
        Resultat resultat = getResultat();
        element.SetResultat(resultat);

        //System.out.println("finish");
    }

    public void BtnCancelAct(ActionEvent actionEvent) {
        //element.actionList = actionListbuf;
        Stage stage = (Stage) btnOk.getScene().getWindow();
        stage.close();
    }

    public void init() {
    }

    public void SetElement(Element element) {
        this.element = element;
        //actionListbuf = element.actionList;
    }

    public void SetPerson() throws IOException {
        tfName.setText(element.getName());
        tfPos.setText(element.getPos());
        for (int i = 0; i<element.actionList.size() ; i++) {
            LoadTests((element.actionList.get(i)).nnn);//element.actionList.get(i).oneActionController, element.actionList.get(i).toolBar
        }
    }

    private void LoadTests(Nastroika nastroika) throws IOException {//OneActionController oneActionController, ToolBar toolBar
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PersonTestController.class.getResource("/fxml/OneAction.fxml"));
        ToolBar toolBar = loader.load();
        //ToolBar toolBarMy = (ToolBar) FXMLLoader.load(getClass().getResource("/fxml/Element.fxml"));
        OneActionController oneAct = loader.getController();

        addWithDragging(vbSpisokTest, toolBar);

        oneAct.SetMainApp(this);
        oneAct.SetNastroika(nastroika);
        oneAct.SetLabel(vbSpisokTest.getChildren().indexOf(toolBar));
        controllerList.put(toolBar, oneAct);

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
        //actionList.put(toolBar, new TableTest(vbSpisokTest.getChildren().indexOf(toolBar),oneAct, toolBar));
        controllerList.put(toolBar, oneAct);
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
                //RotatePos();
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


            int begin =0;
            int end = 0;
            begin = (indexOfDraggingNode>indexOfDropTarget)? indexOfDropTarget: indexOfDraggingNode;
            end = (indexOfDraggingNode>indexOfDropTarget)? indexOfDraggingNode: indexOfDropTarget;
            //int cur = begin;
            while (begin<=end){
                ToolBar tt = (ToolBar) root.getChildren().get(begin);
                (controllerList.get(tt)).SetLabel(begin);
                //System.out.println("index = " + tableTb.get(tt).index);
                begin++;
            }

        }
    }

    public void DeleteTest(ToolBar tbOne) {
        int begin = vbSpisokTest.getChildren().indexOf(tbOne);
        //System.out.println("begin="+begin);
        controllerList.remove(tbOne);
        //tbOne.getParent().getChildrenUnmodifiable().remove(tbOne);
        vbSpisokTest.getChildren().remove(tbOne);

        int index = vbSpisokTest.getChildren().size();
        System.out.println("index="+index);

        while (begin<=(index-1)){
            ToolBar tt = (ToolBar) vbSpisokTest.getChildren().get(begin);
            (controllerList.get(tt)).SetLabel(begin);
            begin++;
        }

    }

    public void CbSignalAction(ActionEvent actionEvent) {

    }


    public Resultat getResultat() {
        Resultat resbuf = new Resultat();
        resbuf.stateSignal = cbSignal.isSelected();
        if (cbSignal.isSelected()){
            for (int i = 0; i < ccbSignal.getItems().size(); i ++){
                if (ccbSignal.getCheckModel().isChecked(i)){
                    resbuf.signalSResultat.add(SignalCollection.signalSpisok.get(i));
                }
            }
        }else {
            resbuf.signalSResultat.clear();
        }
        return resbuf;
    }

    public void SerResultat(Resultat resElement) {
        cbSignal.setSelected(resElement.stateSignal);
        if (resElement.stateSignal) {
            for (int i = 0; i < resElement.signalSResultat.size(); i++) {
                ccbSignal.getCheckModel().check(resElement.signalSResultat.get(i).index);
            }
        }
    }
}
