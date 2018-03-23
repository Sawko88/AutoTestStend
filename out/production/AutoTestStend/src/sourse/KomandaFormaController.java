package sourse;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class KomandaFormaController implements Initializable {
    public ComboBox cbKomand;
    //public TextField tfKomanda;
    public KomandaCollection komandaCollection = new KomandaCollection();
    public TextArea taKomanda;

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
                if (cbKomand.getSelectionModel().getSelectedIndex() == 0 || cbKomand.getSelectionModel().getSelectedIndex() == 13  ){
                    taKomanda.setDisable(false);
                    taKomanda.setText(komandaCollection.GetKonamda(cbKomand.getSelectionModel().getSelectedIndex()));
                } else {
                    taKomanda.setDisable(true);
                    taKomanda.clear();
                }
                //Updatenastroika();
            }
        });

        taKomanda.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (cbKomand.getSelectionModel().getSelectedIndex()==0 || cbKomand.getSelectionModel().getSelectedIndex() == 13 ) {
                    komandaCollection.SetKomand( cbKomand.getSelectionModel().getSelectedIndex(), taKomanda.getText());

                }
                //Updatenastroika();
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

    public void SetParam(ActionTest nastroika) {
        int index = nastroika.kOmanda.index;
        cbKomand.getSelectionModel().select(index);
        if (index == 0 || index == 13){
            taKomanda.setText(nastroika.kOmanda.komanda);
        }
        //Updatenastroika();

    }

    private void Updatenastroika() {
        if (personOneActionController!=null) {
            int index = cbKomand.getSelectionModel().getSelectedIndex();
            //personOneActionController.nastroika1.komanda = komandaCollection.Get(index);
            //personOneActionController.nastroika1.nameParam = komandaCollection.GetName(index);
            //personOneActionController.nastroika1.nameParam += " "+komandaCollection.GetKonamda(index);
        }//
    }
    private ActionTestCollection actionTestCollection = new ActionTestCollection();

    public ActionTest GetNastroika(ActionTest nastroikaBuf) {
        nastroikaBuf = actionTestCollection.ActionSpisok.get(nastroikaBuf.number);
        if (personOneActionController!=null) {
            int index = cbKomand.getSelectionModel().getSelectedIndex();
            nastroikaBuf.kOmanda = komandaCollection.komndaSpisok.get(index);
            nastroikaBuf.namePosition = komandaCollection.komndaSpisok.get(index).name;
            if (nastroikaBuf.kOmanda.index == 0 || nastroikaBuf.kOmanda.index == 13) {
                nastroikaBuf.namePosition += " " + komandaCollection.komndaSpisok.get(index).komanda;
            }
        }//
        return nastroikaBuf;
    }
}
