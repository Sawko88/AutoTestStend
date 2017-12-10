package sourse;

import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class CanFormaController implements Initializable {

    public CanCollection canCollection = new CanCollection();
    public ComboBox cbCan;
    public JFXToggleButton tbCAn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Iterator<Can> iterator = canCollection.canSpisok.iterator();
        while (iterator.hasNext()){
            cbCan.getItems().add((iterator.next()).name);
        }
        cbCan.getSelectionModel().select(0);


    }
    public PersonOneActionController personOneActionController;
    public void SetMainApp(PersonOneActionController personOneActionController) {
        this.personOneActionController = personOneActionController;
    }

    public void ActCbCan(ActionEvent actionEvent) {
        tbCAn.setSelected(canCollection.getState(cbCan.getSelectionModel().getSelectedIndex()));
    }

    public void ActTbCan(ActionEvent actionEvent) {
        if (tbCAn.isSelected()){
            canCollection.setState(cbCan.getSelectionModel().getSelectedIndex(), 1);
        } else {
            canCollection.setState(cbCan.getSelectionModel().getSelectedIndex(), 0);
        }
    }
}
