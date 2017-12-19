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

public class Metka33OneController implements Initializable{
    public Slider slMetka;
    public Label lbPos;
    public PersonOneActionController personOneActionController;

    public void ActslMetka(MouseEvent mouseEvent) {
        //System.out.println("Pos= " + slMetka.getValue());
    }

    public void SetMainApp(PersonOneActionController personOneActionController) {
        this.personOneActionController= personOneActionController;
    }

    public void ActMousePressedSl(MouseEvent mouseEvent) {
        //System.out.println("Pos= " + slMetka.getValue());
        //NumberFormat formater = new DecimalFormat("#0.0");
        //lbPos.setText(formater.format(slMetka.getValue()));
    }

    public void ActMouseRellisSl(MouseEvent mouseEvent) {
        //System.out.println("Pos= " + slMetka.getValue());
        //NumberFormat formater = new DecimalFormat("#0.0");
        //lbPos.setText(formater.format(slMetka.getValue()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        slMetka.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double value = (Math.round(slMetka.getValue()*10));
                //System.out.println(value);
                value = value/10;
                slMetka.setValue(value);
                NumberFormat formater = new DecimalFormat("#0.0");
                lbPos.setText(formater.format(slMetka.getValue()));
                //UpdateNastroika();

            }
        });
    }

    private void UpdateNastroika() {
        personOneActionController.nastroika1.metka33 = slMetka.getValue();
        personOneActionController.nastroika1.nameParam = lbPos.getText();
    }


    public void SetParam(Nastroika nastroika) {
        slMetka.setValue(nastroika.metka33);
    }

    public Nastroika GetNastroika(Nastroika nastroika) {
        nastroika.metka33 = slMetka.getValue();
        nastroika.nameParam = lbPos.getText();
        return nastroika;
    }
}
