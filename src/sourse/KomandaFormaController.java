package sourse;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class KomandaFormaController implements Initializable {
    public ComboBox cbKomand;
    public TextField tfKomanda;
    public KomandaCollection komandaCollection = new KomandaCollection();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Iterator<KOmanda> iterator = komandaCollection.komndaSpisok.iterator();
        while (iterator.hasNext()){
            cbKomand.getItems().add((iterator.next()).name);
        }

        cbKomand.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                //System.out.println(cbKomand.getValue());
                if (cbKomand.getSelectionModel().getSelectedIndex() == 0){
                    tfKomanda.setDisable(false);
                    tfKomanda.setText(komandaCollection.GetKonamda(cbKomand.getSelectionModel().getSelectedIndex()));
                } else {
                    tfKomanda.setDisable(true);
                    tfKomanda.clear();
                }
            }
        });

        tfKomanda.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (cbKomand.getSelectionModel().getSelectedIndex()==0) {
                    komandaCollection.SetKomand(tfKomanda.getText());
                    System.out.println(tfKomanda.getText());
                }
            }
        });

        cbKomand.getSelectionModel().select(0);
    }

    public void ActCbKomand(ActionEvent actionEvent) {
        //System.out.println(cbKomand.getValue());
    }

    public void ActTfKomand(ActionEvent actionEvent) {

    }

    public PersonOneActionController personOneActionController;
    public void SetMainApp(PersonOneActionController personOneActionController) {
        this.personOneActionController = personOneActionController;
    }
}
