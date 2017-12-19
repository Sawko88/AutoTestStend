package sourse;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class PausaController implements Initializable{
    public Slider slTime;
    public ComboBox cbTime;
    public Label lbTime;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbTime.getItems().add("сек.");
        cbTime.getItems().add("мин.");
        cbTime.getItems().add("час.");
        cbTime.getSelectionModel().select(0);

        slTime.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double value = Math.round(slTime.getValue());
                slTime.setValue(value);
                lbTime.setText(String.valueOf((int)slTime.getValue())+" "+cbTime.getValue());
                UpdateNastroika();
            }
        });


    }

    private void UpdateNastroika() {
       //switch (cbTime.getSelectionModel().getSelectedIndex()){
           // case 0: personOneActionController.nastroika1.pause = slTime.getValue()*1; break;
            //case 1: personOneActionController.nastroika1.pause = slTime.getValue()*60; break;
           // case 2: personOneActionController.nastroika1.pause = slTime.getValue()*3600; break;
           // default: personOneActionController.nastroika1.pause = slTime.getValue()*1; break;
        //}
        //personOneActionController.nastroika1.nameParam = lbTime.getText();
    }

    public PersonOneActionController personOneActionController;
    public void SetMainApp(PersonOneActionController personOneActionController) {
        this.personOneActionController = personOneActionController;
    }

    public void ActCbTime(ActionEvent actionEvent) {
        double value = Math.round(slTime.getValue());
        slTime.setValue(value);
        lbTime.setText(String.valueOf((int)slTime.getValue())+" "+cbTime.getValue());
        UpdateNastroika();
    }

    public void SetParam(Nastroika nastroika) {
        //nastroika.pause = slTime.getValue();

        slTime.setValue(nastroika.pause);
        cbTime.getSelectionModel().select(0);
        if (nastroika.pause >= 60){
            cbTime.getSelectionModel().select(1);
            slTime.setValue(nastroika.pause/60);
        }
        if (nastroika.pause>=3600){
            cbTime.getSelectionModel().select(2);
            slTime.setValue(nastroika.pause/3600);
        }
        UpdateNastroika();
    }
}
