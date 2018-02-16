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
import javafx.scene.control.ComboBox;
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
    public JFXCheckBox cbIndikacia;
    public ComboBox cboxIndikacia;
    public JFXCheckBox cbReleProv;
    public ComboBox cboxReleProv;
    public JFXCheckBox cbReleBezprov;
    public ComboBox cboxReleBezprov;
    public JFXCheckBox cbSirena;
    public ComboBox cboxSirena;
    public JFXCheckBox cbBagagnik;
    public ComboBox cboxBagagnik;
    public JFXCheckBox cbWebasto;
    public ComboBox cboxWebasto;
    public JFXCheckBox cbAutozap;
    public ComboBox cboxAutozap;
    public JFXCheckBox cbZumerSnatie;
    public ComboBox cboxZumerSnatie;
    public JFXCheckBox cbZumerMetki;
    public ComboBox cboxZumerMetki;
    public JFXCheckBox cbPovorot;
    public ComboBox cboxPovorot;
    private SignalCollection signalCollection = new SignalCollection();
    private IndikaciaCollection indikaciaCollection = new IndikaciaCollection();
    private ReleProvcollection releProvcollection = new ReleProvcollection();
    private ReleBezProvCollection releBezProvCollection = new ReleBezProvCollection();
    private SirenaCollection sirenaCollection = new SirenaCollection();
    private BagagnikCollection bagagnikCollection = new BagagnikCollection();
    private Webastocollection webastocollection = new Webastocollection();
    private AutoZapCollection autoZapCollection = new AutoZapCollection();
    private ZumerSniatiaCollection zumerSniatiaCollection = new ZumerSniatiaCollection();
    private ZumerMetkiCollection zumerMetkiCollection = new ZumerMetkiCollection();
    private PovorotCollection povorotCollection = new PovorotCollection();

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

        for (int i = 0; i < indikaciaCollection.indikaciaSpisok.size(); i++) {
            cboxIndikacia.getItems().add(indikaciaCollection.indikaciaSpisok.get(i).name);
        }
        cboxIndikacia.getSelectionModel().select(0);
        cboxIndikacia.setDisable(true);
        cbIndikacia.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (cbIndikacia.isSelected()){
                    cboxIndikacia.setDisable(false);
                } else {
                    cboxIndikacia.setDisable(true);
                    cboxIndikacia.getSelectionModel().select(0);
                }
            }
        });

        for (int i = 0; i < releProvcollection.releProvSpisok.size(); i++) {
            cboxReleProv.getItems().add(releProvcollection.releProvSpisok.get(i).name);
        }
        cboxReleProv.getSelectionModel().select(0);
        cboxReleProv.setDisable(true);
        cbReleProv.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (cbReleProv.isSelected()){
                    cboxReleProv.setDisable(false);
                } else {
                    cboxReleProv.setDisable(true);
                    cboxReleProv.getSelectionModel().select(0);
                }
            }
        });

        for (int i = 0; i < releBezProvCollection.releBezProvSpisok.size(); i++) {
            cboxReleBezprov.getItems().add(releBezProvCollection.releBezProvSpisok.get(i).name);
        }
        cboxReleBezprov.getSelectionModel().select(0);
        cboxReleBezprov.setDisable(true);
        cbReleBezprov.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (cbReleBezprov.isSelected()){
                    cboxReleBezprov.setDisable(false);
                } else {
                    cboxReleBezprov.setDisable(true);
                    cboxReleBezprov.getSelectionModel().select(0);
                }
            }
        });

        for (int i = 0; i < sirenaCollection.sirenaSpisok.size(); i++) {
            cboxSirena.getItems().add(sirenaCollection.sirenaSpisok.get(i).name);
        }
        cboxSirena.getSelectionModel().select(0);
        cboxSirena.setDisable(true);
        cbSirena.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (cbSirena.isSelected()){
                    cboxSirena.setDisable(false);
                } else {
                    cboxSirena.setDisable(true);
                    cboxSirena.getSelectionModel().select(0);
                }
            }
        });

        for (int i = 0; i < bagagnikCollection.bagagnikSpisok.size(); i++) {
            cboxBagagnik.getItems().add(bagagnikCollection.bagagnikSpisok.get(i).name);
        }
        cboxBagagnik.getSelectionModel().select(0);
        cboxBagagnik.setDisable(true);
        cbBagagnik.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (cbBagagnik.isSelected()){
                    cboxBagagnik.setDisable(false);
                } else {
                    cboxBagagnik.setDisable(true);
                    cboxBagagnik.getSelectionModel().select(0);
                }
            }
        });

        for (int i = 0; i < webastocollection.webastoSpisok.size(); i++) {
            cboxWebasto.getItems().add(webastocollection.webastoSpisok.get(i).name);
        }
        cboxWebasto.getSelectionModel().select(0);
        cboxWebasto.setDisable(true);
        cbWebasto.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (cbWebasto.isSelected()){
                    cboxWebasto.setDisable(false);
                } else {
                    cboxWebasto.setDisable(true);
                    cboxWebasto.getSelectionModel().select(0);
                }
            }
        });

        for (int i = 0; i < autoZapCollection.autoZaps.size(); i++) {
            cboxAutozap.getItems().add(autoZapCollection.autoZaps.get(i).name);
        }
        cboxAutozap.getSelectionModel().select(0);
        cboxAutozap.setDisable(true);
        cbAutozap.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (cbAutozap.isSelected()){
                    cboxAutozap.setDisable(false);
                } else {
                    cboxAutozap.setDisable(true);
                    cboxAutozap.getSelectionModel().select(0);
                }
            }
        });

        for (int i = 0; i < zumerSniatiaCollection.zumerSniatias.size(); i++) {
            cboxZumerSnatie.getItems().add(zumerSniatiaCollection.zumerSniatias.get(i).name);
        }
        cboxZumerSnatie.getSelectionModel().select(0);
        cboxZumerSnatie.setDisable(true);
        cbZumerSnatie.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (cbZumerSnatie.isSelected()){
                    cboxZumerSnatie.setDisable(false);
                } else {
                    cboxZumerSnatie.setDisable(true);
                    cboxZumerSnatie.getSelectionModel().select(0);
                }
            }
        });

        for (int i = 0; i < zumerMetkiCollection.zumerMetkis.size(); i++) {
            cboxZumerMetki.getItems().add(zumerMetkiCollection.zumerMetkis.get(i).name);
        }
        cboxZumerMetki.getSelectionModel().select(0);
        cboxZumerMetki.setDisable(true);
        cbZumerMetki.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (cbZumerMetki.isSelected()){
                    cboxZumerMetki.setDisable(false);
                } else {
                    cboxZumerMetki.setDisable(true);
                    cboxZumerMetki.getSelectionModel().select(0);
                }
            }
        });

        for (int i = 0; i < povorotCollection.povorots.size(); i++) {
            cboxPovorot.getItems().add(povorotCollection.povorots.get(i).name);
        }
        cboxPovorot.getSelectionModel().select(0);
        cboxPovorot.setDisable(true);
        cbPovorot.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (cbPovorot.isSelected()){
                    cboxPovorot.setDisable(false);
                } else {
                    cboxPovorot.setDisable(true);
                    cboxPovorot.getSelectionModel().select(0);
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
        element.ActionListClear();// actionList.clear();
        //System.out.println(vbSpisokTest.getChildren().size());
        if (vbSpisokTest.getChildren().size()>0) {
            for (int i = 0; i < (vbSpisokTest.getChildren().size()); i++) {
                OneActionController one = controllerList.get(vbSpisokTest.getChildren().get(i));
                ActionTest actBuf ;
                actBuf = one.GetNastroika();

                //element.actionList.put(i, new TableTest(actBuf));
                element.ActionListPut(i, new TableTest(actBuf));
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
        for (int i = 0; i<element.GetActionListSize() ; i++) {
            LoadTests((element.GetActionListAction(i)));//actionList.get(i)).actionTest
        }
    }

    private void LoadTests(ActionTest nastroika) throws IOException {//OneActionController oneActionController, ToolBar toolBar
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

        resbuf.stateIndikacia = cbIndikacia.isSelected();
        resbuf.indikaciaResultat = IndikaciaCollection.indikaciaSpisok.get(cboxIndikacia.getSelectionModel().getSelectedIndex());

        resbuf.stateReleProv = cbReleProv.isSelected();
        resbuf.releProvResultat = releProvcollection.releProvSpisok.get(cboxReleProv.getSelectionModel().getSelectedIndex());

        resbuf.stateReleBezProv = cbReleBezprov.isSelected();
        resbuf.releBezProvResultat = releBezProvCollection.releBezProvSpisok.get(cboxReleBezprov.getSelectionModel().getSelectedIndex());

        resbuf.stateSirena = cbSirena.isSelected();
        resbuf.sirenaResultat = sirenaCollection.sirenaSpisok.get(cboxSirena.getSelectionModel().getSelectedIndex());

        resbuf.stateBagagnik = cbBagagnik.isSelected();
        resbuf.bagagnikResultat = bagagnikCollection.bagagnikSpisok.get(cboxBagagnik.getSelectionModel().getSelectedIndex());

        resbuf.stateWebasto = cbWebasto.isSelected();
        resbuf.webastoResultat = webastocollection.webastoSpisok.get(cboxWebasto.getSelectionModel().getSelectedIndex());

        resbuf.stateAutoZap = cbAutozap.isSelected();
        resbuf.autoZapResultat = autoZapCollection.autoZaps.get(cboxAutozap.getSelectionModel().getSelectedIndex());

        resbuf.stateZumerSniatia = cbZumerSnatie.isSelected();
        resbuf.zumerSniatiaResultat = zumerSniatiaCollection.zumerSniatias.get(cboxZumerSnatie.getSelectionModel().getSelectedIndex());

        resbuf.stateZumerMetki = cbZumerMetki.isSelected();
        resbuf.zumerMetkiResultat = zumerMetkiCollection.zumerMetkis.get(cboxZumerMetki.getSelectionModel().getSelectedIndex());

        resbuf.statePovorot = cbPovorot.isSelected();
        resbuf.povorotResultat = povorotCollection.povorots.get(cboxPovorot.getSelectionModel().getSelectedIndex());

        return resbuf;
    }

    public void SerResultat(Resultat resElement) {
        cbSignal.setSelected(resElement.stateSignal);
        if (resElement.stateSignal) {
            for (int i = 0; i < resElement.signalSResultat.size(); i++) {
                ccbSignal.getCheckModel().check(resElement.signalSResultat.get(i).index);
            }
        }

        cbIndikacia.setSelected(resElement.stateIndikacia);
        cboxIndikacia.getSelectionModel().select(resElement.indikaciaResultat.index);

        cbReleProv.setSelected(resElement.stateReleProv);
        cboxReleProv.getSelectionModel().select(resElement.releProvResultat.index);

        cbReleBezprov.setSelected(resElement.stateReleBezProv);
        cboxReleBezprov.getSelectionModel().select(resElement.releBezProvResultat.index);

        cbSirena.setSelected(resElement.stateSirena);
        cboxSirena.getSelectionModel().select(resElement.sirenaResultat.index);

        cbBagagnik.setSelected(resElement.stateBagagnik);
        cboxBagagnik.getSelectionModel().select(resElement.bagagnikResultat.index);

        cbWebasto.setSelected(resElement.stateWebasto);
        cboxWebasto.getSelectionModel().select(resElement.webastoResultat.index);

        cbAutozap.setSelected(resElement.stateAutoZap);
        cboxAutozap.getSelectionModel().select(resElement.autoZapResultat.index);

        cbZumerSnatie.setSelected(resElement.stateZumerSniatia);
        cboxZumerSnatie.getSelectionModel().select(resElement.zumerSniatiaResultat.index);

        cbZumerMetki.setSelected(resElement.stateZumerMetki);
        cboxZumerMetki.getSelectionModel().select(resElement.zumerMetkiResultat.index);

        cbPovorot.setSelected(resElement.statePovorot);
        cboxPovorot.getSelectionModel().select(resElement.povorotResultat.index);
    }

    public void CbIndikaciaAction(ActionEvent actionEvent) {

    }

    public void CbReleProvAction(ActionEvent actionEvent) {
    }

    public void CbReleBezprovAction(ActionEvent actionEvent) {
    }

    public void CbSirenaAction(ActionEvent actionEvent) {
    }

    public void CbBagagnikAction(ActionEvent actionEvent) {
    }

    public void CbWebastoAction(ActionEvent actionEvent) {
    }

    public void CbAutozapAction(ActionEvent actionEvent) {
    }

    public void CbZumerSnatieAction(ActionEvent actionEvent) {
    }

    public void CbZumerMetkiAction(ActionEvent actionEvent) {
    }
}
