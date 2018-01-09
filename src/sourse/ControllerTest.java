package sourse;

import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.*;
import java.net.URL;
import java.util.*;

public  class ControllerTest implements Initializable{
    public JFXToggleButton Pult;
    public Stage stagePult;
    public ControllerPultMX controllerPultMX;


    public Button btnAdd;
    public VBox vbAdd;
    public  Map<ToolBar, TableElement> tableTb = new LinkedHashMap<ToolBar, TableElement>();

    public Button btnInfo;
    public Button btnSave;
    public AnchorPane anMain;
    public Button btnLoad;
    public Button btnNewFile;
    public Button btnSettings;


    public void PultAction(ActionEvent actionEvent) throws IOException {
        if (Pult.isSelected()){
            stagePult.show();
        } else {
            //controllerPultMX.CloseWin();
            stagePult.hide();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        stagePult = new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/PultMX.fxml"));
            stagePult.setScene(new Scene(root));
            stagePult.initModality(Modality.WINDOW_MODAL);

            //stagePult.show();



        } catch (IOException e) {
            e.printStackTrace();
        }

        //controllerPultMX.init(this);

        Pult.setSelected(false);


    }



    //public int count = 0;
    public void ActBtnAdd(ActionEvent actionEvent) throws IOException {
        //ToolBar toolBar = new ToolBar(new Button("name"+count));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ControllerTest.class.getResource("/fxml/Element.fxml"));
        ToolBar toolBarMy = loader.load();
        //ToolBar toolBarMy = (ToolBar) FXMLLoader.load(getClass().getResource("/fxml/Element.fxml"));
        Element element = loader.getController();
        element.SetMainApp(this);
        //count++;


        addWithDragging(vbAdd, toolBarMy);
        int index = vbAdd.getChildren().indexOf(toolBarMy);
        tableTb.put(toolBarMy, new TableElement(index,element));


        element.init();


    }

    private void addWithDragging(final VBox root, final ToolBar toolBar) {
        toolBar.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                toolBar.startFullDrag();
            }
        });

        // next two handlers just an idea how to show the drop target visually:
        toolBar.setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                toolBar.setStyle("-fx-background-color: #77c2bb;");
            }
        });
        toolBar.setOnMouseDragExited(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                toolBar.setStyle("");
            }
        });

        toolBar.setOnMouseDragReleased(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                toolBar.setStyle("-fx-background-color: #77c2bb;");
                int indexOfDraggingNode = root.getChildren().indexOf(event.getGestureSource());
                int indexOfDropTarget = root.getChildren().indexOf(toolBar);
                rotateNodes1(root, indexOfDraggingNode, indexOfDropTarget);
                //RotateIndexTb(root,indexOfDraggingNode, indexOfDropTarget, toolBar);
                event.consume();
            }
        });

        root.getChildren().add(toolBar);
    }

    public void RotateIndexTb(VBox root, int indexOfDraggingNode, int indexOfDropTarget, ToolBar toolBar) {
        //(tableTb.get(toolBar)).element.labPos.setText(String.valueOf(indexOfDraggingNode));
    }

    private void rotateNodes1(final VBox root, final int indexOfDraggingNode,
                             final int indexOfDropTarget) {
        if (indexOfDraggingNode >= 0 && indexOfDropTarget >= 0) {
            final Node node = root.getChildren().remove(indexOfDraggingNode);
            root.getChildren().add(indexOfDropTarget, node);

            int begin =0;
            int end = 0;
            begin = (indexOfDraggingNode>indexOfDropTarget)? indexOfDropTarget: indexOfDraggingNode;
            end = (indexOfDraggingNode>indexOfDropTarget)? indexOfDraggingNode: indexOfDropTarget;
            //int cur = begin;
            while (begin<=end){
                ToolBar tt = (ToolBar) root.getChildren().get(begin);
                (tableTb.get(tt)).element.SetPosition(begin);
                //System.out.println("index = " + tableTb.get(tt).index);
                begin++;
            }

        }
    }

    public void DeleteTest(ToolBar tbTest) {
        int begin = vbAdd.getChildren().indexOf(tbTest);
        //System.out.println("begin="+begin);
        tableTb.remove(tbTest);
        //tbOne.getParent().getChildrenUnmodifiable().remove(tbOne);
        vbAdd.getChildren().remove(tbTest);

        int index = vbAdd.getChildren().size();
       // System.out.println("index="+index);

        while (begin<=(index-1)){
            ToolBar tt = (ToolBar) vbAdd.getChildren().get(begin);
            (tableTb.get(tt)).element.SetPosition(begin);
            begin++;
        }
    }

    public void BtnInfoAction(ActionEvent actionEvent) {

    }

    public void BtnSaveAction(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Contro files (*.control)", "*.control");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Save control");

        //Show save file dialog
        File file = fileChooser.showSaveDialog(anMain.getScene().getWindow());

        if(file != null){
            SaveFile( file);
        }



    }

    private void SaveFile( File file) throws IOException {
        /*try {
            FileWriter fileWriter = null;

            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(ControllerTest.class.getName()).log(Level.SEVERE, null, ex);
        }*/

        ArrayList<SaveParam> savePAramList = new ArrayList<SaveParam>();
        if (vbAdd.getChildren().size()<= 0){

        } else {
            FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            for (int i =0 ; i < vbAdd.getChildren().size(); i++){
                ToolBar myTT = (ToolBar) vbAdd.getChildren().get(i);
                SaveParam mySaveParam = new SaveParam();


                mySaveParam.name = tableTb.get(myTT).element.labName.getText();
                mySaveParam.pos = Integer.parseInt(tableTb.get(myTT).element.labPos.getText());
                mySaveParam.personlist = tableTb.get(myTT).element.actionList;
                mySaveParam.res = tableTb.get(myTT).element.GetResultat();

                savePAramList.add(mySaveParam);
                //oos.writeObject();

            }
            oos.writeObject(savePAramList);
            oos.close();
            fout.close();
        }

    }



    private void LoadFile(File file) throws IOException, ClassNotFoundException {
        FileInputStream fin = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fin);
        ArrayList<SaveParam> loadParamList = new ArrayList<SaveParam>();
        loadParamList = (ArrayList<SaveParam>) ois.readObject();
        vbAdd.getChildren().clear();
        tableTb.clear();
        for (int i = 0 ; i < loadParamList.size(); i ++){
            AddElement(loadParamList.get(i));
        }
        ois.close();
        fin.close();
    }

    private void AddElement(SaveParam saveParam) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ControllerTest.class.getResource("/fxml/Element.fxml"));
        ToolBar toolBarMy = loader.load();
        //ToolBar toolBarMy = (ToolBar) FXMLLoader.load(getClass().getResource("/fxml/Element.fxml"));
        Element element = loader.getController();
        element.SetMainApp(this);
        //count++;


        addWithDragging(vbAdd, toolBarMy);
        int index = vbAdd.getChildren().indexOf(toolBarMy);
        tableTb.put(toolBarMy, new TableElement(index,element));
        element.SetPosition(saveParam.pos);
        element.SetName(saveParam.name);
        element.SetActionList(saveParam.personlist);
        element.SetResultat(saveParam.res);
        //element.init();
    }

    public void BtnLoadAction(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Contro files (*.control)", "*.control");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Load control");

        //Show save file dialog
        File file = fileChooser.showOpenDialog(anMain.getScene().getWindow());

        if(file != null){
            LoadFile( file);
        }
    }

    public void BtnNewFileAction(ActionEvent actionEvent) {
        vbAdd.getChildren().clear();
        tableTb.clear();
    }

    public void BtnSettingsAction(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ControllerTest.class.getResource("/fxml/Settings.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/PersoneElement.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        // stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);

        SettingsController settingsController = loader.getController();
        settingsController.SetTest(this);

        stage.showAndWait();
    }
}
