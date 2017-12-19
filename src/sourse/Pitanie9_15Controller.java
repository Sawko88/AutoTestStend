package sourse;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

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
                double value = (Math.round(slNap.getValue()*2));
                //System.out.println(value);
                value = value/2;
                slNap.setValue(value);
                NumberFormat formater = new DecimalFormat("#0.0");
                lbPos.setText(formater.format(slNap.getValue()));
                UpdateNastroika();
            }
        });
    }

    private void UpdateNastroika() {
        //personOneActionController.nastroika1.pitanie = slNap.getValue();
        //personOneActionController.nastroika1.nameParam = lbPos.getText();
    }

    public PersonOneActionController personOneActionController;
    public void SetMainApp(PersonOneActionController personOneActionController) {
        this.personOneActionController = personOneActionController;
    }

    public void MouseReleasedSlNap(MouseEvent mouseEvent) {
        //double value = (Math.round(slNap.getValue()*2));
        //System.out.println(value);
        //value = value/2;
        //System.out.println(value);
        //slNap.setValue(value);
    }

    public void SetParam(Nastroika nastroika) {
        slNap.setValue(nastroika.pitanie);
    }
}
