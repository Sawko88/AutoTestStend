package sourse;

import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class OnOffOneController implements Initializable{
    public PersonOneActionController personOneActionController;
    public JFXToggleButton tbOn;
    //public Nastroika nastroika;

    public void SetMainApp(PersonOneActionController personOneActionController) {
        this.personOneActionController = personOneActionController;

    }

    public void SetParam(Nastroika nastroika) {
        //this.nastroika = nastroika;
        if (nastroika.onOff == 1){
            tbOn.setSelected(true);
        } else {
            tbOn.setSelected(false);
        }
        //GetTbState();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //GetTbState();
        //nastroika.nameParam = "0";
    }



    public void ActTbOn(ActionEvent actionEvent) {
        //GetTbState();
    }


    public Nastroika GetNastroika(Nastroika nastroika) {
        if (tbOn.isSelected()){
            nastroika.onOff = 1;
            nastroika.nameParam = "вкл";

        } else {
            nastroika.onOff = 0;
            nastroika.nameParam = "выкл";
        }

        return nastroika;
    }
}
