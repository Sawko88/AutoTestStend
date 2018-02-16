package sourse;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
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
                //UpdateNastroika();
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
        //UpdateNastroika();
    }

    public void SetParam(ActionTest nastroika) {
        //nastroika.pause = slTime.getValue();
        double d = Double.parseDouble(nastroika.currentstait);
        slTime.setValue(d);
        cbTime.getSelectionModel().select(0);
        if (d >= 60){
            cbTime.getSelectionModel().select(1);
            slTime.setValue(d/60);
        }
        if (d>=3600){
            cbTime.getSelectionModel().select(2);
            slTime.setValue(d/3600);
        }
        //UpdateNastroika();
    }
    private ActionTestCollection actionTestCollection = new ActionTestCollection();
    public ActionTest GetNastroika(ActionTest nastroikaBuf) {
        nastroikaBuf = actionTestCollection.ActionSpisok.get(nastroikaBuf.number);
        DecimalFormatSymbols decimalSymbols = DecimalFormatSymbols.getInstance();
        decimalSymbols.setDecimalSeparator('.');
        NumberFormat formater = new DecimalFormat("0.0",decimalSymbols);
        switch (cbTime.getSelectionModel().getSelectedIndex()){
         case 0:

             nastroikaBuf.currentstait = formater.format(slTime.getValue()*1);
             break;

         case 1: nastroikaBuf.currentstait = formater.format(slTime.getValue()*60); break;
         case 2: nastroikaBuf.currentstait = formater.format(slTime.getValue()*3600); break;
         default: nastroikaBuf.currentstait = formater.format(slTime.getValue()*1); break;
        }
        nastroikaBuf.namePosition = lbTime.getText();
        return nastroikaBuf;
    }
}
