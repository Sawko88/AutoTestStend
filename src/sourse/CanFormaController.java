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
    public ActionTestCollection actionTestCollection = new ActionTestCollection();
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
        //UpdateNastroika();
    }

    public void ActTbCan(ActionEvent actionEvent) {
        if (tbCAn.isSelected()){
            canCollection.setState(cbCan.getSelectionModel().getSelectedIndex(), 1);
        } else {
            canCollection.setState(cbCan.getSelectionModel().getSelectedIndex(), 0);
        }
        //UpdateNastroika();
    }

    public void SetParam(ActionTest nastroika) {
        int index = nastroika.can.index;
        cbCan.getSelectionModel().select(index);
        if (nastroika.can.curstait == nastroika.can.onstait){
            tbCAn.setSelected(true);
        } else {
            tbCAn.setSelected(false);
        }
        //UpdateNastroika();
    }

    private void UpdateNastroika() {
        //personOneActionController.nastroika1.can = canCollection.GetCan(cbCan.getSelectionModel().getSelectedIndex());
        //personOneActionController.nastroika1.nameParam = canCollection.GetName(cbCan.getSelectionModel().getSelectedIndex());
        //personOneActionController.nastroika1.nameParam += (tbCAn.isSelected())? " вкл" : " выкл";
    }

    public ActionTest GetNastroika(ActionTest nastroikaBuf) {
        nastroikaBuf = actionTestCollection.ActionSpisok.get(nastroikaBuf.number);
        nastroikaBuf.can = canCollection.GetCan(cbCan.getSelectionModel().getSelectedIndex());
        nastroikaBuf.namePosition = canCollection.GetName(cbCan.getSelectionModel().getSelectedIndex());
        if (tbCAn.isSelected()){
            nastroikaBuf.namePosition+=" вкл";
            nastroikaBuf.can.curstait = nastroikaBuf.can.onstait;
        }else {
            nastroikaBuf.namePosition+=" выкл";
            nastroikaBuf.can.curstait = nastroikaBuf.can.offstait;
        }

        return nastroikaBuf;
    }
}
