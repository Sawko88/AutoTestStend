package sourse;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;

public class Pitanie9_15Controller implements Initializable {
    public Slider slNap;
    public Label lbPos;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        slNap.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                NumberFormat formater = new DecimalFormat("#0.0");
                lbPos.setText(formater.format(slNap.getValue()));
            }
        });
    }

    public PersonOneActionController personOneActionController;
    public void SetMainApp(PersonOneActionController personOneActionController) {
        this.personOneActionController = personOneActionController;
    }
}
