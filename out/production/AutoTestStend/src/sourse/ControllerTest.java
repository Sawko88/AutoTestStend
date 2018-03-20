package sourse;

import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXToggleButton;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.*;

import java.io.*;
import java.net.URL;
import java.nio.CharBuffer;
import java.sql.*;
import java.util.*;

public  class ControllerTest  implements Initializable {
    public JFXToggleButton Pult;
    public Stage stagePult;
    //public ControllerPultMX controllerPultMX;
    public Stage stageSms = new Stage();
    public Stage stageGPRS = new Stage();

    public Button btnAdd;
    public VBox vbAdd;
    public  Map<ToolBar, TableElement> tableTb = new LinkedHashMap<ToolBar, TableElement>();

    public Button btnInfo;
    public Button btnSave;
    public AnchorPane anMain;
    public Button btnLoad;
    public Button btnNewFile;
    public Button btnSettings;
    public ToggleButton tbSms;
    public ToggleButton tbGprs;
    //public Button btnConnect;
    public ToggleButton tbConnect;
    public TextField tfLogNum;
    public JFXSpinner spConnect;
    public ToggleButton tbComPort;
    public Button btStartTest;
    public Button btStopTest;
    public ToggleButton tbLogi;
    public  Label lbIndikacia;

    private Setting setting = new Setting();

    private ObgectTest obgectTest = new ObgectTest();

    private SmsController smsController;
    private SmsController gprsController;

    private Thread ConnectTread;
    public LogickTest logickTest = new LogickTest();

    public ArrayList<String> errorListShow = new ArrayList<String>();

    public void PultAction(ActionEvent actionEvent) throws IOException {


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initialize");

        tfLogNum.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    tfLogNum.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        try {
            LoadfileSettings();
        } catch (IOException e) {

        }

        OpenFormSettings();

        SmsQuipLoad();
        MyTbSmsListener();

        GprsQuipLoad();
        MyTbGprsListener();

        ComPortLoad();
        MyTbComListener();

        PultLoad();
        MyTbPultListener();

        LogLoad();
        MyTbLogListener();

        MyTbConnectListener();

        SetDisable(true);



    }

    private void MyTbLogListener() {
        tbLogi.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (tbLogi.isSelected()){
                    logStage.sizeToScene();
                    logStage.show();
                } else {
                    logStage.hide();
                }
            }
        });
    }


    private Stage logStage = new Stage();
    public LogController logController;
    private void LogLoad() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ControllerTest.class.getResource("/fxml/LOG.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/PersoneElement.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logStage.setResizable(false);
        logStage.setTitle("Логи");
        logStage.setScene(new Scene(root));
        logStage.sizeToScene();
        logStage.initModality(Modality.WINDOW_MODAL);
        logStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                tbLogi.setSelected(false);
            }
        });
        logController = loader.getController();
        logController.SetControllerTest(this);
    }

    private void MyTbPultListener() {
        Pult.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (Pult.isSelected()){
                    pultSrage.show();
                } else {
                    pultSrage.hide();
                }
            }
        });
    }


    private Stage pultSrage = new Stage();
    public ControllerPultMX controllerPultMX;
    private void PultLoad() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ControllerTest.class.getResource("/fxml/PultMX.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/PersoneElement.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pultSrage.setResizable(false);
        pultSrage.setTitle("Пульт");
        pultSrage.setScene(new Scene(root));
        pultSrage.sizeToScene();
        pultSrage.initModality(Modality.WINDOW_MODAL);
        pultSrage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Pult.setSelected(false);
            }
        });
        controllerPultMX = loader.getController();
        controllerPultMX.SetControllerTest(this);
        //comPortController.SetNameModul("Com");
    }


    private void MyTbComListener() {
        tbComPort.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (tbComPort.isSelected()){
                    comPortStage.sizeToScene();
                    comPortStage.show();
                } else {
                    comPortStage.hide();
                }
            }
        });
    }


    private Stage comPortStage = new Stage();
    public ComPortController comPortController;

    private void ComPortLoad() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ControllerTest.class.getResource("/fxml/ComPort.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/PersoneElement.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        comPortStage.setResizable(false);
        comPortStage.setTitle("ComPort-диспетчер");
        comPortStage.setScene(new Scene(root));
        comPortStage.sizeToScene();
        comPortStage.initModality(Modality.WINDOW_MODAL);
        comPortStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                tbComPort.setSelected(false);
            }
        });
        comPortController = loader.getController();
        comPortController.SetControllerTest(this);
        comPortController.SetNameModul("Com");
    }

    private void SetDisable(boolean b) {
        btnAdd.setDisable(b);
        vbAdd.setDisable(b);
        tfLogNum.setDisable(!b);
        btStartTest.setDisable(b);
        btStopTest.setDisable(b);
        Pult.setDisable(b);

    }

    private void MyTbGprsListener() {
        tbGprs.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (tbGprs.isSelected()){
                    stageGPRS.sizeToScene();
                    stageGPRS.show();
                } else {
                    stageGPRS.hide();
                }
            }
        });
    }

    private void GprsQuipLoad() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ControllerTest.class.getResource("/fxml/SMS.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/PersoneElement.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stageGPRS.setResizable(false);
        stageGPRS.setTitle("GPRS-диспетчер");
        stageGPRS.setScene(new Scene(root));
        stageGPRS.sizeToScene();
        stageGPRS.initModality(Modality.WINDOW_MODAL);
        stageGPRS.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                tbGprs.setSelected(false);
            }
        });
        gprsController = loader.getController();
        gprsController.SetControllerTest(this);
        gprsController.SetNameModul("GPRS");
        //GetSettigs();
        //gprsController.SetSettigs(setting.gprsIp, setting.gprsPort, setting.gprsPas);
    }

    private void GetSettigs() {
        this.setting = settingsController.GetSettings();
    }

    private void MyTbConnectListener() {
        tbConnect.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (tbConnect.isSelected()){
                    connectThread= true;
                    ConnectTread = new Thread(new ConnectionStateTread());
                    ConnectTread.start();
                    connectionState = ConnectionState.START;



                } else {
                    connectionState = ConnectionState.STOP;
                    StopSpinner();
                    smsController.DisconnectSMS();
                    gprsController.DisconnectSMS();
                    comPortController.DisconnectCOM();
                    controllerPultMX.DisconnectPult();
                    logController.StopLog();
                    logickTest.StopTherad();
                    SetDisable(true);
                }
            }
        });
    }

    private void StopSpinner() {
        spConnect.setVisible(false);
    }

    private void StartSpinner() {
        spConnect.setVisible(true);
    }

    private String obgectTypeOnly = "Устройство ARKAN CONTROL GPRS (ARKAN_SOBR_GSM_2 и ARKAN_SOBR_GSM_3)";

    private void ConnectToBD(String bdIp, String bdName, String bdUser, String bdPas) {
        System.out.println("-------- MySQL JDBC Connection Testing ------------");

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            connectionState = ConnectionState.ERRORCONNECT;
            return;
        }

        System.out.println("MySQL JDBC Driver Registered!");

        Connection connection = null;
        if (tfLogNum.getText() == "" || tfLogNum.getText() == null || tfLogNum.getText().equals("")){
            SetErrorConnect("Пустая строка логического номера");
            connectionState = ConnectionState.ERRORCONNECT;
            return;
        }
        try {
            connection = DriverManager.getConnection("jdbc:sqlserver://"+bdIp+":1433;" + "databaseName="+bdName+";user="+bdUser+";password="+bdPas);
            Statement statement = connection.createStatement();

           // PreparedStatement preparedStatement = connection.prepareStatement("[autotest].[spu_GetDeviceInfo] @nDeviceID_ = 4783571");
            ResultSet rs = statement.executeQuery("[autotest].[spu_GetDeviceInfo] @nDeviceID_ = "+tfLogNum.getText());
            if (rs.next()==false) {
                SetErrorConnect("Пустое оборудование");
                connectionState = ConnectionState.ERRORCONNECT;
            }else {


                do {
                    obgectTest.logNum = tfLogNum.getText();
                    obgectTest.telefon = rs.getString("phone");
                    System.out.println(obgectTest.telefon);
                    obgectTest.code = rs.getString("password");
                    System.out.println(obgectTest.code);
                    obgectTest.typeDev = rs.getString("device_type");
                    System.out.println(obgectTest.typeDev);
                } while (rs.next());
            }
        } catch (com.microsoft.sqlserver.jdbc.SQLServerException ex){
            System.out.println("Connection Failed! Check data type");

            ex.printStackTrace();
            SetErrorConnect("Не тот формат номера");
            connectionState = ConnectionState.ERRORCONNECT;

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            connectionState = ConnectionState.ERRORCONNECT;
            return;
        }

        if (obgectTest.typeDev.contains(obgectTypeOnly)){
            connectionState = ConnectionState.CONNECTSMS;
        } else {
            SetErrorConnect("Не тот тип оборудования");
            connectionState = ConnectionState.ERRORCONNECT;
        }

        /*if (obgectTest.telefon !=""){
            connectionState = ConnectionState.CONNECTSMS;
        }*/

        if (connection != null) {
            System.out.println("You made it, take control your database now!");

        } else {
            System.out.println("Failed to make connection!");
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void SetErrorConnect(String error) {
        errorConnect = error;
    }

    private void MyTbSmsListener() {
        tbSms.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (tbSms.isSelected()){
                    stageSms.sizeToScene();
                    stageSms.show();
                } else {
                    stageSms.hide();
                }
            }
        });
    }



    private void SmsQuipLoad() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ControllerTest.class.getResource("/fxml/SMS.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/PersoneElement.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stageSms.setTitle("SMS-диспетчер");
        stageSms.setScene(new Scene(root));
        stageSms.setResizable(false);
        stageSms.sizeToScene();
        stageSms.initModality(Modality.WINDOW_MODAL);
        stageSms.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                tbSms.setSelected(false);
            }
        });
        smsController = loader.getController();
        smsController.SetControllerTest(this);
        smsController.SetNameModul("SMS");
       // GetSettigs();
       // smsController.SetSettigs(setting.smsIp, setting.smsPort, setting.smsPas);

    }

    public void TbSmsAction(ActionEvent event) {

    }

    private void LoadfileSettings() throws IOException {
        File fileSet = new File("settingsControl.ini");
        if(!fileSet.exists()) {
            fileSet.createNewFile();

        } else {
            FileReader fileReader = new FileReader(fileSet);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String buf=null;

            while ((buf = bufferedReader.readLine())!=null){
                int index = buf.indexOf("=");
                if (buf.contains("comPort")) {

                    setting.comPort = buf.substring(index+1);
                }
                if (buf.contains("smsIp")){setting.smsIp = buf.substring(index+1);}
                if (buf.contains("smsPort")){setting.smsPort = buf.substring(index+1);}
                if (buf.contains("smsPas")){setting.smsPas = buf.substring(index+1);}
                if (buf.contains("gprsIp")){setting.gprsIp = buf.substring(index+1);}
                if (buf.contains("gprsPort")){setting.gprsPort = buf.substring(index+1);}
                if (buf.contains("gprsPas")){setting.gprsPas = buf.substring(index+1);}
                if (buf.contains("bdIp")){setting.bdIp = buf.substring(index+1);}
                if (buf.contains("bdName")){setting.bdName = buf.substring(index+1);}
                if (buf.contains("bdUser")){setting.bdUser = buf.substring(index+1);}
                if (buf.contains("bdPas")){setting.bdPas = buf.substring(index+1);}

            }
        }
    }






    public void shutdoun() {
        //System.out.println("Stop");
        SaveFileSettings();
        smsController.DisconnectSMS();
        gprsController.DisconnectSMS();
        comPortController.DisconnectCOM();
        logController.StopLog();
        tbConnect.setSelected(false);
        logickTest.StopTherad();
        Platform.exit();
    }

    //private File fileSet=new File("settingsControl.ini");
    private void SaveFileSettings()  {
        File fileSet = new File("settingsControl.ini");
        try {
            FileWriter fileWriter = new FileWriter(fileSet);
            BufferedWriter bf = new BufferedWriter(fileWriter);

            WriteLineSettings(bf, "comPort", setting.comPort);
            WriteLineSettings(bf, "smsIp", setting.smsIp);
            WriteLineSettings(bf, "smsPort", setting.smsPort);
            WriteLineSettings(bf, "smsPas", setting.smsPas);
            WriteLineSettings(bf, "gprsIp", setting.gprsIp);
            WriteLineSettings(bf, "gprsPort", setting.gprsPort);
            WriteLineSettings(bf, "gprsPas", setting.gprsPas);
            WriteLineSettings(bf, "bdIp", setting.bdIp);
            WriteLineSettings(bf, "bdName", setting.bdName);
            WriteLineSettings(bf, "bdUser", setting.bdUser);
            WriteLineSettings(bf, "bdPas", setting.bdPas);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void WriteLineSettings(BufferedWriter bf, String name, String value) {
        try {
            bf.write(name+"="+value);
            bf.newLine();
            bf.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

       /* element.SetPosition(saveParam.pos);
        element.SetName(saveParam.name);
        element.SetActionList(saveParam.personlist);
        element.SetResultat(saveParam.res);*/
        element.init(tableTb.get(toolBarMy));


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
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
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
                mySaveParam.personlist = tableTb.get(myTT).element.GetActinList();//actionList
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
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
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
    SettingsController settingsController;
    Stage stageSettigs = new Stage();
    public void BtnSettingsAction(ActionEvent actionEvent) throws IOException {

        //OpenFormSettings();
        settingsController.SetSettings(setting);
        stageSettigs.showAndWait();
    }

    public void SetSettigs(Setting setting) {
        this.setting = setting;
    }


    public void OpenFormSettings(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ControllerTest.class.getResource("/fxml/Settings.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("/fxml/PersoneElement.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Stage stage = new Stage();
        // stage.setResizable(false);
        stageSettigs.setResizable(false);
        stageSettigs.setScene(new Scene(root));
        stageSettigs.initModality(Modality.WINDOW_MODAL);

        settingsController = loader.getController();
        settingsController.SetTest(this);

        settingsController.SetSettings(setting);
    }

    public String GetObgectPhone() {
        String phone = obgectTest.telefon.substring(1,12);//отсикаем + у номера телефона
        return phone;
    }

    public void setConnectState(ConnectionState stateCon) {
        connectionState = stateCon;
    }


    private ArrayList<SaveParam> listLogickTest = new ArrayList<SaveParam>();

    public void ActionBtStartTest(ActionEvent actionEvent) {
        listLogickTest.clear();
       // LinkedList<SaveParam> listLogickTest = new LinkedList<SaveParam>();
        if (vbAdd.getChildren().size()<= 0){
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Тестирование");
                alert.setContentText("Список тестов пустой");
                alert.showAndWait();
            });
        } else {
            //InitLogickTest();//потом убрать
            for (int i =0 ; i < vbAdd.getChildren().size(); i++){
                ToolBar myTT = (ToolBar) vbAdd.getChildren().get(i);
                SaveParam mySaveParam = new SaveParam();


                mySaveParam.name = tableTb.get(myTT).element.labName.getText();
                mySaveParam.pos = Integer.parseInt(tableTb.get(myTT).element.labPos.getText());
                mySaveParam.personlist = tableTb.get(myTT).element.GetActinList();
                mySaveParam.res = tableTb.get(myTT).element.GetResultat();

                listLogickTest.add(mySaveParam);
                //oos.writeObject();

            }
            StartLogickTest();


        }

    }

    public void ControlButtonTest(boolean b){
        vbAdd.setDisable(b);
        btnAdd.setDisable(b);
        btStopTest.setDisable(!b);
        btStartTest.setDisable(b);
    }

    public void ActionbtStopTest(ActionEvent actionEvent) {
        logickTest.StopLogickTest();

    }

    public void StartOneTest(int index) {
        listLogickTest.clear();
        // LinkedList<SaveParam> listLogickTest = new LinkedList<SaveParam>();

        //InitLogickTest();

        ToolBar myTT = (ToolBar) vbAdd.getChildren().get(index);
        SaveParam mySaveParam = new SaveParam();
        String str = myTT.getStyle();


        mySaveParam.name = tableTb.get(myTT).element.labName.getText();
        mySaveParam.pos = Integer.parseInt(tableTb.get(myTT).element.labPos.getText());
        mySaveParam.personlist = tableTb.get(myTT).element.GetActinList();
        mySaveParam.res = tableTb.get(myTT).element.GetResultat();

        listLogickTest.add(mySaveParam);
                //oos.writeObject();

        StartLogickTest();




    }

    private void StartLogickTest() {
        logickTest.StartLogickTest(listLogickTest);
        btStopTest.setDisable(false);
    }

    public ParserSignalTest parserSignalTest = new ParserSignalTest();

    public void ShowAllert(LogickTest.ErrorFlag erroFlag) {

        switch (erroFlag){

            case XZ:
                errorListShow.clear();
                break;
            case OK:
                Platform.runLater(() -> {
                    Alert alertOk = new Alert(Alert.AlertType.INFORMATION);
                    Image image1 = new Image("res/cat-drunk-icon.png");

                    ImageView imageView = new ImageView(image1);
                    /*imageView.setFitWidth(64);
                    imageView.setFitHeight(64);*/

                    alertOk.setGraphic(imageView);
                    alertOk.initModality(Modality.APPLICATION_MODAL);
                    alertOk.setHeaderText(" ");
                    alertOk.setContentText("Тестирование закончено без ошибок");


                    alertOk.showAndWait();
                });
                errorListShow.clear();
                break;
            case ERROR:
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    Image image1 = new Image("res/cat-pirate-icon.png");

                    ImageView imageView = new ImageView(image1);
                    /*imageView.setFitWidth(64);
                    imageView.setFitHeight(64);*/

                    alert.setGraphic(imageView);

                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.setHeaderText(" ");

                    alert.setContentText("Тестирование закончено с ошибками");

                    VBox dialogPaneContent = new VBox();

                    Label label1 = new Label("Получены следующие ошибки:");

                    //String stackTrace = "errror";
                    TextArea textArea = new TextArea();
                    textArea.setMaxWidth(400);
                    textArea.setMaxHeight(150);
                    //textArea.setMinWidth(200);
                    textArea.setWrapText(true);
                    for (int i = 0 ; i<errorListShow.size(); i++){
                        String messError = errorListShow.get(i);

                        textArea.appendText(parserSignalTest.ParserMess(messError) +"\r\n");
                        //errorListShow.remove(i-1);
                    }
                    errorListShow.clear();
                    textArea.setStyle("-fx-text-fill: #dd1515;");
                    //textArea.setText(stackTrace);

                    dialogPaneContent.getChildren().addAll(label1,textArea);

                    // Set content for Dialog Pane
                    alert.getDialogPane().setContent(dialogPaneContent);

                    alert.showAndWait();
                });
                break;
        }
    }

    public void ShowIndikacia(String name) {
        lbIndikacia.setText(name);
    }

    public void SetElementState(int pos, String text) {
        tableTb.get( vbAdd.getChildren().get(pos)).element.SetStateText(text);

    }


    public static enum ColorTestElement{
        RED, GREAN
    }

    public void SetColorElement(int pos, ColorTestElement color) {
        switch (color){

            case RED:
                vbAdd.getChildren().get(pos).setStyle("-fx-mybase:#da7781;"+
                        "  TOP-COLOR: ladder(" +
                        "          -fx-mybase," +
                        "          derive(-fx-mybase,0%) 0%," +
                        "          derive(-fx-mybase,46%) 100%" +
                        "      );" +
                "-fx-background-color: " +
                        "linear-gradient(to bottom, derive(TOP-COLOR,25%) 0%, -fx-outer-border 90%)," +
                        "linear-gradient(to bottom, TOP-COLOR 2%, derive(-fx-mybase,-2.1%) 95%);");
                SetElementState(pos,"С ошибками");
                break;
            case GREAN:
                vbAdd.getChildren().get(pos).setStyle("-fx-mybase:#77c2bb;"+
                        "  TOP-COLOR: ladder(" +
                        "          -fx-mybase," +
                        "          derive(-fx-mybase,0%) 0%," +
                        "          derive(-fx-mybase,46%) 100%" +
                        "      );" +
                    "-fx-background-color: " +
                    "linear-gradient(to bottom, derive(TOP-COLOR,25%) 0%, -fx-outer-border 90%)," +
                    "linear-gradient(to bottom, TOP-COLOR 2%, derive(-fx-mybase,-2.1%) 95%);");
                SetElementState(pos,"Без ошибок");
                break;
        }

    }

    public void ClearColorElement() {
        for (int i =0 ; i < vbAdd.getChildren().size(); i++){
            vbAdd.getChildren().get(i).setStyle("-fx-mybase: #ececec;" +
                    "  TOP-COLOR: ladder(" +
                    "          -fx-mybase," +
                    "          derive(-fx-mybase,0%) 0%," +
                    "          derive(-fx-mybase,46%) 100%" +
                    "      );" +
                    "      -fx-background-color:" +
                    "          linear-gradient(to bottom, derive(TOP-COLOR,25%) 0%, -fx-outer-border 90%)," +
                    "          linear-gradient(to bottom, TOP-COLOR 2%, derive(-fx-mybase,-2.1%) 95%);" +
                    "      -fx-background-insets: 0 0 0 0, 1 0 1 0;" +
                    "      -fx-padding: 0.416667em 0.5em 0.416667em 0.5em; /* 5 6  5 6 */" +
                    "      -fx-spacing: 0.333em; /* 4 */" +
                    "      -fx-alignment: CENTER_LEFT;");
            tableTb.get( vbAdd.getChildren().get(i)).element.SetStateText("---");

        }

    }

    public enum ConnectionState {

        START, LOGSTART, LOGWAIT, CONNECTCOM, WAITCOM, BDCONNECT, WAITBD, CONNECTSMS, WAITSMS, CONNECTGPRS, WAITGPRS,SATRTLOGICKTEST ,  OKCONNECT, NONE, ERRORCONNECT, STOP
    }
    private ConnectionState connectionState = ConnectionState.NONE;
    private boolean connectThread = false;
    private int countConnection = 0;

    private String errorConnect = "";

    private class ConnectionStateTread implements Runnable {
        @Override
        public void run() {
            while (connectThread){
                switch (connectionState){
                    case START:
                        System.out.println("ConnectionStateTread - START");
                        countConnection = 0;
                        StartSpinner();
                        GetSettigs();
                        connectionState = ConnectionState.LOGSTART;

                        break;
                    case LOGSTART:
                        System.out.println("ConnectionStateTread - LOGSTART");
                        connectionState = ConnectionState.LOGWAIT;
                        logController.StartLog();

                        break;
                    case LOGWAIT:
                        System.out.println("ConnectionStateTread - LOGWAIT");
                        break;
                    case CONNECTCOM:
                        System.out.println("ConnectionStateTread - CONNECTCOM");
                        connectionState = ConnectionState.WAITCOM;
                        comPortController.SetSettings(setting.comPort);
                        comPortController.ConnectCom();
                        controllerPultMX.ConnectionPult();

                        break;
                    case WAITCOM:
                        System.out.println("ConnectionStateTread - WAITCOM");
                        //connectionState = ConnectionState.BDCONNECT;
                        break;
                    case BDCONNECT:
                        System.out.println("ConnectionStateTread - BDCONNECT");
                        connectionState = ConnectionState.WAITBD;
                        ConnectToBD(setting.bdIp, setting.bdName, setting.bdUser, setting.bdPas);

                        break;
                    case WAITBD:
                        System.out.println("ConnectionStateTread - WAITBD");
                        break;
                    case CONNECTSMS:
                        System.out.println("ConnectionStateTread - CONNECTSMS");
                        smsController.SetSettigs(setting.smsIp, setting.smsPort, setting.smsPas);
                        smsController.ConnectSMS();
                        connectionState = ConnectionState.WAITSMS;
                        break;
                    case WAITSMS:
                        System.out.println("ConnectionStateTread - WAITSMS");
                        break;
                    case CONNECTGPRS:
                        System.out.println("ConnectionStateTread - CONNECTGPRS");
                        gprsController.SetSettigs(setting.gprsIp, setting.gprsPort, setting.gprsPas);
                        gprsController.ConnectSMS();
                        connectionState = ConnectionState.WAITGPRS;
                        break;
                    case WAITGPRS:
                        System.out.println("ConnectionStateTread - WAITGPRS");
                        break;
                    case SATRTLOGICKTEST:
                        System.out.println("ConnectionStateTread - SATRTLOGICKTEST");
                        InitLogickTest();
                        connectionState = ConnectionState.OKCONNECT;
                        break;
                    case OKCONNECT:
                        System.out.println("ConnectionStateTread - OKCONNECT");
                        StopSpinner();
                        SetDisable(false);
                        btStopTest.setDisable(true);
                        connectionState = ConnectionState.STOP;
                        break;
                    case NONE:
                        System.out.println("ConnectionStateTread - NONE");
                        break;
                    case ERRORCONNECT:
                        System.out.println("ConnectionStateTread - ERRORCONNECT");
                        tbConnect.setSelected(false);

                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText("Connection");
                            alert.setContentText(errorConnect);
                            alert.showAndWait();
                        });
                        //connectionState = ConnectionState.STOP;
                        break;
                    case STOP:
                        System.out.println("ConnectionStateTread - STOP");

                        connectThread = false;
                        break;
                    default:break;
                }
                countConnection++;
                if (countConnection>180){
                    SetErrorConnect("Не подключился по таймауту");
                    connectionState = ConnectionState.ERRORCONNECT;
                    countConnection = 0;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void InitLogickTest() {
        logickTest.SetTest(this);
        logickTest.SetChannals(smsController, gprsController,comPortController);
        logickTest.SetObject(obgectTest);
        logickTest.InitThread();

    }

    /*public void BtnConnectAction(ActionEvent event) {
        smsController.ConnectSMS();
    }*/
}
