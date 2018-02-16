package sourse;

import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class OnOffOneController implements Initializable{
    public PersonOneActionController personOneActionController;
    public JFXToggleButton tbOn;
    private ActionTestCollection actionTestCollection = new ActionTestCollection();
    //public Nastroika nastroika;

    public void SetMainApp(PersonOneActionController personOneActionController) {
        this.personOneActionController = personOneActionController;

    }

    public void SetParam(ActionTest nastroika) {
        //this.nastroika = nastroika;
        if (nastroika.currentstait == nastroika.onstait){
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


    public ActionTest GetNastroika(ActionTest nastroika) {
        nastroika = actionTestCollection.ActionSpisok.get(nastroika.number);
        if (tbOn.isSelected()){
            nastroika.currentstait = nastroika.onstait;
            nastroika.namePosition = "вкл";

        } else {
            nastroika.currentstait = nastroika.offstait;
            nastroika.namePosition = "выкл";
        }

        return nastroika;
    }
}
