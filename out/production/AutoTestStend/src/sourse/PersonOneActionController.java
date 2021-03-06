package sourse;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class PersonOneActionController implements Initializable {
    public OneActionController oneActionController;
    public ComboBox cbPersonOne;
    public AnchorPane anPersonOne;
    public Button btnCancelPersonOne;
    public Button btnOkPersonOne;
    //private Nastroika nastroika1 = new Nastroika(0);
    private ActionTest actionTest = new ActionTest(0, ActionTest.TypeAction.NONE, "-", " ", "1", "0");




    public void SetOneAction(OneActionController oneActionController) {
        this.oneActionController = oneActionController;

    }

    public void ActCbPersonOne(ActionEvent actionEvent) throws IOException {

    }

    private void LoadResursKomand() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PersonOneActionController.class.getResource("/fxml/KomandaForma.fxml"));
        AnchorPane anchorPane = loader.load();

        KomandaFormaController komandaFormaController  = loader.getController();
        object = loader.getController();
        komandaFormaController.SetMainApp(this);
        komandaFormaController.SetParam(actionTest);

        anPersonOne.getChildren().clear();
        anPersonOne.getChildren().add(anchorPane);

    }

    private void LoadResursCan() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PersonOneActionController.class.getResource("/fxml/CanForma.fxml"));
        AnchorPane anchorPane = loader.load();

        CanFormaController canFormaController  = loader.getController();
        object = loader.getController();
        canFormaController.SetMainApp(this);
        canFormaController.SetParam(actionTest);

        anPersonOne.getChildren().clear();
        anPersonOne.getChildren().add(anchorPane);
    }

    private void LoadResursPausa() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PersonOneActionController.class.getResource("/fxml/Pausa.fxml"));
        AnchorPane anchorPane = loader.load();

        PausaController pausaController  = loader.getController();
        object = loader.getController();
        pausaController.SetMainApp(this);
        pausaController.SetParam(actionTest);

        anPersonOne.getChildren().clear();
        anPersonOne.getChildren().add(anchorPane);
    }

    private void LoadResursOborotu() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PersonOneActionController.class.getResource("/fxml/Oborotu10000.fxml"));
        AnchorPane anchorPane = loader.load();

        Oborotu10000 oborotu10000  = loader.getController();
        object = loader.getController();
        oborotu10000.SetMainApp(this);
        //oborotu10000.SetParam(nastroika1);

        anPersonOne.getChildren().clear();
        anPersonOne.getChildren().add(anchorPane);
    }

    private void LoadREsursNap() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PersonOneActionController.class.getResource("/fxml/Pitanie9_15.fxml"));
        AnchorPane anchorPane = loader.load();

        Pitanie9_15Controller pitanie9_15Controller = loader.getController();
        object = loader.getController();
        pitanie9_15Controller.SetMainApp(this);
        pitanie9_15Controller.SetParam(actionTest);
        anPersonOne.getChildren().clear();
        anPersonOne.getChildren().add(anchorPane);
    }

    private void LoadResursMetka() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PersonOneActionController.class.getResource("/fxml/Metka33One.fxml"));
        AnchorPane anchorPane = loader.load();

        Metka33OneController metka33OneController = loader.getController();
        object = loader.getController();
        metka33OneController.SetMainApp(this);
        //metka33OneController.SetParam(nastroika1);

        anPersonOne.getChildren().clear();
        anPersonOne.getChildren().add(anchorPane);
    }

    private void LoadResurs() {
        anPersonOne.getChildren().clear();
        NullNastroika nullNastroika = new NullNastroika(" ");
        object = nullNastroika;
    }


    public Object object;

    private void LoadResursOn() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PersonOneActionController.class.getResource("/fxml/OnOffOne.fxml"));
        AnchorPane anchorPane = loader.load();

        OnOffOneController onOffOneController = loader.getController();
        object = loader.getController();
        onOffOneController.SetMainApp(this);
        if (actionTest.number == cbPersonOne.getSelectionModel().getSelectedIndex()) {
            onOffOneController.SetParam(actionTest);
        }
        anPersonOne.getChildren().clear();
        anPersonOne.getChildren().add(anchorPane);
        //onOffOneController.SetParam(nastroika);
    }

    public void ActBtnCancelPersonOne(ActionEvent actionEvent) {
        Stage stage = (Stage) btnOkPersonOne.getScene().getWindow();
        stage.close();
    }

    public void ActBtnOkPersonOne(ActionEvent actionEvent) {
        UpdateNastroika();
        oneActionController.SetNastroika(actionTest);
        Stage stage = (Stage) btnCancelPersonOne.getScene().getWindow();
        stage.close();
    }

    public void UpdateNastroika() {

        ActionTest actionTestbuf = new ActionTest(0);

        actionTestbuf.number = cbPersonOne.getSelectionModel().getSelectedIndex();
        actionTestbuf.name = (String) cbPersonOne.getValue();

        //System.out.println(object.getClass());

        OnOffOneController onOffOneController = new OnOffOneController();
        Metka33OneController metka33OneController = new Metka33OneController();
        Pitanie9_15Controller pitanie9_15Controller = new Pitanie9_15Controller();
        Oborotu10000 oborotu10000 = new Oborotu10000();
        PausaController pausaController = new PausaController();
        CanFormaController canFormaController = new CanFormaController();
        KomandaFormaController komandaFormaController = new KomandaFormaController();
        NullNastroika nullNastroika = new NullNastroika();

        //System.out.println(object.(onOffOneController));
        if (object.getClass() == onOffOneController.getClass()) {
           // System.out.println(object.getClass());
            onOffOneController = (OnOffOneController) object;
            actionTestbuf = onOffOneController.GetNastroika(actionTestbuf);
            //oneActionController.SetNastroika(nastroika);
        }
        if (object.getClass() == metka33OneController.getClass()) {
           // System.out.println(object.getClass());
            metka33OneController = (Metka33OneController) object;
            //nastroikaBuf= metka33OneController.GetNastroika(nastroikaBuf);
        }
        if (object.getClass() == pitanie9_15Controller.getClass()) {
            //System.out.println(object.getClass());
            pitanie9_15Controller = (Pitanie9_15Controller) object;
            actionTestbuf = pitanie9_15Controller.GetNastroika(actionTestbuf);
        }
        if (object.getClass() == oborotu10000.getClass()) {
            //System.out.println(object.getClass());
            oborotu10000 = (Oborotu10000) object;
            //nastroikaBuf = oborotu10000.GetNastoika(nastroikaBuf);
        }
        if (object.getClass() == pausaController.getClass()) {
           // System.out.println(object.getClass());
            pausaController= (PausaController) object;
            actionTestbuf = pausaController.GetNastroika(actionTestbuf);
        }
        if (object.getClass() == canFormaController.getClass()) {
            //System.out.println(object.getClass());
            canFormaController = (CanFormaController) object;
            actionTestbuf = canFormaController.GetNastroika(actionTestbuf);
        }
        if (object.getClass() == komandaFormaController.getClass()) {
           // System.out.println(object.getClass());
            komandaFormaController = (KomandaFormaController) object;
            actionTestbuf = komandaFormaController.GetNastroika(actionTestbuf);
        }
        if (object.getClass() == nullNastroika.getClass()) {
           // System.out.println(object.getClass());
            nullNastroika = (NullNastroika) object;
            actionTestbuf = nullNastroika.GetNastroika(actionTestbuf);
        }
        oneActionController.ShowNastroika(actionTestbuf);
        actionTest = actionTestbuf;
        //return nastroikaBuf;
    }
    private ActionTestCollection actionTestCollection = new ActionTestCollection();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*Iterator<String> iterator = spisokCb.iterator();
        while (iterator.hasNext()){
            cbPersonOne.getItems().add(iterator.next());
        }*/
        for (int i =0; i<actionTestCollection.ActionSpisok.size(); i++)
        {
            cbPersonOne.getItems().add(actionTestCollection.ActionSpisok.get(i).name);
        }
        cbPersonOne.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                try {
                    ShowParametr();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //cbPersonOne.getSelectionModel().select(nastroika.index);
    }

    private void ShowParametr() throws IOException {
        int pos = cbPersonOne.getSelectionModel().getSelectedIndex();
        ActionTest.TypeAction typeAction = actionTestCollection.ActionSpisok.get(pos).type;
        switch (typeAction){

            case ONOFF:
                LoadResursOn();
                break;
            case PWRONOFF:
                LoadResursOn();
                break;
            case PWRNAP:
                LoadREsursNap();
                break;
            case CAN:
                LoadResursCan();
                break;
            case MOTOR:
                LoadResursOn();
                break;
            case RARELE:
                LoadResursOn();
                break;
            case METKAONOFF:
                LoadResursOn();
                break;
            case NONE:
                LoadResurs();
                break;
            case GSMCOM:
                LoadResursKomand();
                break;
            case PAUTHA:
                LoadResursPausa();
                break;
            default:break;
        }
        /*switch (cbPersonOne.getSelectionModel().getSelectedIndex()){
            case 0: LoadResurs();break;
            case 1: LoadResursOn();break;
            case 2: LoadResursOn();break;
            case 3: LoadResursOn();break;
            case 4: LoadResursOn();break;
            case 5: LoadResursOn();break;
            case 6: LoadResursMetka();break;
            case 7: LoadResursOn();break;
            case 8: LoadResursMetka();break;
            case 9: LoadResursOn();break;
            case 10: LoadResursOn();break;
            case 11: LoadResursOn();break;
            case 12: LoadResursOn();break;
            case 13: LoadResursOn();break;
            case 14: LoadResursOn();break;
            case 15: LoadResursOn();break;
            case 16: LoadResursOn();break;
            case 17: LoadResursOn();break;
            case 18: LoadResursOn();break;
            case 19: LoadREsursNap();break;
            case 20: LoadResursOn();break;
            case 21: LoadREsursNap();break;
            case 22: LoadResursOborotu(); break;
            case 23: LoadResurs();break;
            case 24: LoadResursOn();break;
            case 25: LoadResursOn();break;
            case 26: LoadResursKomand();break;
            case 27: LoadResursCan(); break;
            case 28: LoadResursPausa();break;
            default: break;
        }*/
        //nastroika.index = cbPersonOne.getSelectionModel().getSelectedIndex();
    }

    public void ShowNastroika(ActionTest nastroika) {
        this.actionTest = nastroika;
        cbPersonOne.getSelectionModel().select(actionTest.number);

    }

    public ActionTest GetNastroika() {
        return  actionTest;
    }

    public void SetNastroika(ActionTest nastroika) {
        this.actionTest = nastroika;
    }
}
