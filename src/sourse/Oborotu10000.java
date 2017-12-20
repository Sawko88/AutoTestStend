package sourse;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

public class Oborotu10000 implements Initializable {
    public Slider slOborotu;
    public Label lbPos;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        slOborotu.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double value = Math.round(slOborotu.getValue()/500);
                value = value*500;
                slOborotu.setValue(value);
                lbPos.setText(String.valueOf((int)slOborotu.getValue()));
                UpdateNastroika();
            }
        });
    }

    private void UpdateNastroika() {
        //personOneActionController.nastroika1.obooti = slOborotu.getValue();
        //personOneActionController.nastroika1.nameParam = lbPos.getText();
    }

    public PersonOneActionController personOneActionController;
    public void SetMainApp(PersonOneActionController personOneActionController) {
        this.personOneActionController = personOneActionController;
    }

    public void SetParam(Nastroika nastroika) {
        slOborotu.setValue(nastroika.obooti);
    }

    public Nastroika GetNastoika(Nastroika nastroikaBuf) {
        nastroikaBuf.obooti = slOborotu.getValue();
        nastroikaBuf.nameParam = lbPos.getText();
        return nastroikaBuf;
    }
}
