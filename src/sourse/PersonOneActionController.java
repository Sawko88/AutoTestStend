package sourse;

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

    public  static List<String> spisokCb = Arrays.asList(
            "",
            "Замок зажигания",
            "Дверь",
            "Капот",
            "Багажник",
            "Метка1, вкл/выкл",
            "Метка1, 0-3.3В",
            "Метка2, вкл/выкл",
            "Метка2, 0-3.3В",
            "Метка3, вкл/выкл",
            "Метка4, вкл/выкл",
            "Метка5, вкл/выкл",
            "КТС",
            "Датчик удара",
            "Датчик перемещения",
            "Датчик объема",
            "Тормоз ручной",
            "Тормоз ножной",
            "Основное питание, вкл/выкл",
            "Основное питание, 9-15В",
            "Резервное питание, вкл/выкл",
            "Резервное питание, 9-15В",
            "Обороты двигателя, 0-10000",
            "Питание реле1",
            "Питание реле2",
            "Питание реле3",
            "Послать команду",
            "Команда CAN"
            );

    public void SetOneAction(OneActionController oneActionController) {
        this.oneActionController = oneActionController;

    }

    public void ActCbPersonOne(ActionEvent actionEvent) throws IOException {
        switch (cbPersonOne.getSelectionModel().getSelectedIndex()){
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
            case 21: break;
            case 22: break;
            case 23: LoadResurs();break;
            case 24: LoadResursOn();break;
            case 25: LoadResursOn();break;
            case 26: break;
            case 27: break;
            case 28: break;
            default: break;
        }
    }

    private void LoadREsursNap() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PersonOneActionController.class.getResource("/fxml/Pitanie9_15.fxml"));
        AnchorPane anchorPane = loader.load();

        Pitanie9_15Controller pitanie9_15Controller = loader.getController();

        pitanie9_15Controller.SetMainApp(this);
        anPersonOne.getChildren().clear();
        anPersonOne.getChildren().add(anchorPane);
    }

    private void LoadResursMetka() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PersonOneActionController.class.getResource("/fxml/Metka33One.fxml"));
        AnchorPane anchorPane = loader.load();

        Metka33OneController metka33OneController = loader.getController();

        metka33OneController.SetMainApp(this);
        anPersonOne.getChildren().clear();
        anPersonOne.getChildren().add(anchorPane);
    }

    private void LoadResurs() {
        anPersonOne.getChildren().clear();
    }

    private void LoadResursOn() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(PersonOneActionController.class.getResource("/fxml/OnOffOne.fxml"));
        AnchorPane anchorPane = loader.load();

        OnOffOneController onOffOneController = loader.getController();

        onOffOneController.SetMainApp(this);
        anPersonOne.getChildren().clear();
        anPersonOne.getChildren().add(anchorPane);
    }

    public void ActBtnCancelPersonOne(ActionEvent actionEvent) {
        Stage stage = (Stage) btnOkPersonOne.getScene().getWindow();
        stage.close();
    }

    public void ActBtnOkPersonOne(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancelPersonOne.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Iterator<String> iterator = spisokCb.iterator();
        while (iterator.hasNext()){
            cbPersonOne.getItems().add(iterator.next());
        }
        cbPersonOne.getSelectionModel().select(0);
    }
}
