/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.controller;

//import java.awt.Button;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import java.awt.Dialog;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import org.json.simple.JSONArray;
import org.model.DDFormat;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.json.simple.JSONObject;
import com.google.gson.JsonObject;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TablePosition;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import javax.swing.ToolTipManager;
import org.apache.poi.openxml4j.opc.OPCPackage;

import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import java.io.FileOutputStream;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.driver.dbConnection;
import org.functions.Function;
import static org.functions.Function.infoBoxKosong;
import static org.functions.Function.infoFileTersimpan;
import org.model.External;
import org.model.Process;
import org.w3c.dom.NamedNodeMap;

/**
 * FXML Controller class
 *
 * @author erwin
 */
public class Controller2018 implements Initializable {

    ArrayList<External> allExternal = new ArrayList<>();
    ArrayList<Process> allProcess = new ArrayList<>();
    Function function = new Function();
    ArrayList<String> radio = new ArrayList();
    @FXML
    private TextArea TADocumentDescription;

    @FXML
    private ListView fileListView;

    @FXML
    private RadioButton RBDokumenTercetak;

    @FXML
    private RadioButton RBFormulir;

    @FXML
    private RadioButton RBDigital;

    @FXML
    private RadioButton RBLaporanTercetak;
    @FXML
    private TableView<DDFormat> tableView;

    @FXML
    private TableColumn<DDFormat, String> fieldNameColumn;

    @FXML
    private TableColumn<DDFormat, String> dataTypeColumn;

    @FXML
    private TableColumn<DDFormat, String> lengthColumn;

    @FXML
    private TableColumn<DDFormat, String> aliasColumn;

    @FXML
    private TableColumn<DDFormat, String> descriptionColumn;

    ArrayList<String> data_flow_temp = new ArrayList();

    @FXML
    private ComboBox CBdataType;

    @FXML
    private ComboBox CBfieldName;

    @FXML
    private TextField TFfieldSize;

    @FXML
    private TextField TFalias;

    @FXML
    private TextArea TAdescription;

    @FXML
    private TextArea TAJson;

    @FXML
    private Button tambah;

    @FXML
    private Button batal;

    @FXML
    private Button createJson;

    @FXML
    private TextField TFKode;

    @FXML
    private TextField TFKodeProses;

    @FXML
    private TextField TFAktor;

    @FXML
    private TextField TFAktifitas;

    @FXML
    private TextField TFProses;

    @FXML
    private TextField TFDataFlow;

    final ObservableList<DDFormat> datas = FXCollections.observableArrayList();

    dbConnection con = new dbConnection();
    private ObservableList<String> masterData;
    private String nameProses, codeProses;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fieldNameColumn.setCellValueFactory(new PropertyValueFactory<DDFormat, String>("fieldName"));
        aliasColumn.setCellValueFactory(new PropertyValueFactory<DDFormat, String>("alias"));
        dataTypeColumn.setCellValueFactory(new PropertyValueFactory<DDFormat, String>("dataType"));
        lengthColumn.setCellValueFactory(new PropertyValueFactory<DDFormat, String>("length"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<DDFormat, String>("description"));
        tableView.setItems(datas);
        selectfileListView();
        masterData = FXCollections.observableArrayList();
        masterData.add("String");
        masterData.add("Integer");
//        masterData.add("Boolean");
//        masterData.add("Double");
        masterData.add("Currency");
        masterData.add("Date");

        tableView.setEditable(true);
        aliasColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        dataTypeColumn.setCellFactory(ComboBoxTableCell.forTableColumn(new DefaultStringConverter(), masterData));

        lengthColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Double Click to edit");
        tableView.setTooltip(tooltip);
    }

    @FXML
    public void changeAliasColumn(TableColumn.CellEditEvent editEvent) {
        DDFormat ddFormat = tableView.getSelectionModel().getSelectedItem();
        ddFormat.setAlias(editEvent.getNewValue().toString());
    }

    @FXML
    public void changeDataTypeColumn(TableColumn.CellEditEvent editEvent) {
        DDFormat ddFormat = tableView.getSelectionModel().getSelectedItem();
        ddFormat.setDataType(editEvent.getNewValue().toString());
    }

    @FXML
    public void changeLengthColumn(TableColumn.CellEditEvent editEvent) {
        DDFormat ddFormat = tableView.getSelectionModel().getSelectedItem();
        ddFormat.setLength(editEvent.getNewValue().toString());
    }

    @FXML
    public void changeDescriptionColumn(TableColumn.CellEditEvent editEvent) {
        DDFormat ddFormat = tableView.getSelectionModel().getSelectedItem();
        ddFormat.setDescription(editEvent.getNewValue().toString());
    }

    public void notifKosongTambahAtribut() {
        if (CBfieldName.getItems().isEmpty()) {
            infoBoxKosong("Tidak dapat menambah ke Tabel", "Field Name tidak boleh kosong", "Warning");
        } else if (CBdataType.getItems().isEmpty()) {
            infoBoxKosong("Tidak dapat menambah ke Tabel", "Data Type tidak boleh kosong", "Warning");
        } else if (TFalias.getText().isEmpty()) {
            infoBoxKosong("Tidak dapat menambah ke Tabel", "Alias tidak boleh kosong", "Warning");
        } else if (TAdescription.getText().isEmpty()) {
            infoBoxKosong("Tidak dapat menambah ke Tabel", "Description tidak boleh kosong", "Warning");
        }
    }

    public void notifKosongGenerate() {
        if (TADocumentDescription.getText().isEmpty()) {
            infoBoxKosong("Tidak dapat men-generate ke Json", "Document Descrition tidak boleh kosong", "Warning");
        } else if (!RBDigital.isSelected() && !RBDokumenTercetak.isSelected()) {
            infoBoxKosong("Tidak dapat men-generate ke Json", "Data Format Tidak Boleh Kosong", "Warning");
        }

    }

    private String path = "";

    //fungsi ini untuk mengenerate data dictionary ke dalam bentuk JSON
    @FXML
    public void createToJsonAction(ActionEvent event) throws JAXBException, SQLException {
        ObservableList<DDFormat> items = tableView.getItems();
        JsonArray datasets = new JsonArray();
        JsonObject title = new JsonObject();

        if (TFKode.getText().isEmpty()
                || TFDataFlow.getText().isEmpty()
                || TFProses.getText().isEmpty()
                || TFAktor.getText().isEmpty()
                || tableView.getItems().isEmpty()
                || TFAktifitas.getText().isEmpty()
                || TADocumentDescription.getText().isEmpty()) {
            notifKosongGenerate();
        } else {
            if (RBDigital.isSelected()) {
                radio.add(RBDigital.getText());
            }
            if (RBDokumenTercetak.isSelected()) {
                radio.add(RBDokumenTercetak.getText());
            }
            if (RBFormulir.isSelected()) {
                radio.add(RBFormulir.getText());
            }
            if (RBLaporanTercetak.isSelected()) {
                radio.add(RBLaporanTercetak.getText());
            }

            String dataFormat = radio.toString();
            dataFormat = dataFormat.substring(dataFormat.indexOf("[") + 1);
            dataFormat = dataFormat.substring(0, dataFormat.indexOf("]"));
            title.addProperty("Kode Data Dictionary", TFKode.getText());
            title.addProperty("Nama Dokumen", TFDataFlow.getText());
            title.addProperty("Bentuk Data", dataFormat);
            title.addProperty("Kode Proses", TFKodeProses.getText());
            title.addProperty("Nama Proses", TFProses.getText());
            title.addProperty("Aktivitas", TFAktifitas.getText());
            title.addProperty("Aktor", TFAktor.getText());
            title.addProperty("Relasi", "-");
            for (DDFormat i : items) {
                JsonObject obj = new JsonObject();
                obj.addProperty("name", i.getFieldName());
                obj.addProperty("alias", i.getAlias());
                obj.addProperty("type", i.getDataType());
                obj.addProperty("length", i.getLength());
                obj.addProperty("description", i.getDescription());
                datasets.add(obj);
            }

            try {
                Connection conn = con.getConnection();

                try {
                    PreparedStatement ps = conn.prepareStatement("SELECT nama_dokumen FROM data_dictionary WHERE nama_dokumen = ?");
                    ps.setString(1, TFDataFlow.getText());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        System.out.println("sudah ada");
                        String query = "UPDATE data_dictionary SET bentuk_data = ? WHERE nama_dokumen = ?";
                        PreparedStatement preparedStmt = conn.prepareStatement(query);
                        preparedStmt.setString(1, dataFormat);
                        preparedStmt.setString(2, TFDataFlow.getText());
                        preparedStmt.execute();
                    } else {
                        String query = "INSERT INTO data_dictionary (code, nama_dokumen, bentuk_data, aktifitas, kode_proses, proses, aktor, deskripsi_dokumen) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
                        PreparedStatement preparedStmt = conn.prepareStatement(query);
                        preparedStmt.setString(1, TFKode.getText());
                        preparedStmt.setString(2, TFDataFlow.getText());
                        preparedStmt.setString(3, dataFormat);
                        preparedStmt.setString(4, TFAktifitas.getText());
                        preparedStmt.setString(5, TFKodeProses.getText());
                        preparedStmt.setString(6, TFProses.getText());
                        preparedStmt.setString(7, TFAktor.getText());
                        preparedStmt.setString(8, TADocumentDescription.getText());
                        preparedStmt.execute();
                    }
                } catch (Exception e) {
                    System.out.println("error" + e);
                }

                for (int row = 0; row < datas.size(); row++) {
                    PreparedStatement ps = conn.prepareStatement("SELECT field FROM atribut WHERE field = ?");
                    ps.setString(1, datas.get(row).getFieldName().toString());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        System.out.println("Udah Ada");
                        String query = "UPDATE atribut SET field = ?, alias = ?, data_type = ?, length = ?, description = ? WHERE field = ?";
                        PreparedStatement preparedStmt = conn.prepareStatement(query);
                        preparedStmt.setString(1, datas.get(row).getFieldName().toString());
                        preparedStmt.setString(2, datas.get(row).getAlias().toString());
                        preparedStmt.setString(3, datas.get(row).getDataType().toString());
                        preparedStmt.setString(4, datas.get(row).getLength().toString());
                        preparedStmt.setString(5, datas.get(row).getDescription().toString());
                        preparedStmt.setString(6, datas.get(row).getFieldName().toString());
                        preparedStmt.execute();
                    } else {
                        String query = "INSERT INTO atribut (field, alias, data_type, length, description) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement preparedStmt = conn.prepareStatement(query);
                        preparedStmt.setString(1, datas.get(row).getFieldName().toString());
                        preparedStmt.setString(2, datas.get(row).getAlias().toString());
                        preparedStmt.setString(3, datas.get(row).getDataType().toString());
                        preparedStmt.setString(4, datas.get(row).getLength().toString());
                        preparedStmt.setString(5, datas.get(row).getDescription().toString());
                        preparedStmt.execute();
                    }
                }

                try {
                    int id_DD = 0;
                    PreparedStatement ps = conn.prepareStatement("SELECT id FROM data_dictionary WHERE code=?");
                    ps.setString(1, TFKode.getText());
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        id_DD = rs.getInt("id");
                    }
                    System.out.println("Data Dictionary = " + id_DD);
                    System.out.println("Attribut = " + datas.size());
                    PreparedStatement psp = conn.prepareStatement("SELECT id_datadictionary FROM datadictionary_atribut WHERE id_datadictionary = ?");
                    psp.setString(1, String.valueOf(id_DD));
                    ResultSet rsp = psp.executeQuery();
                    if (rsp.next()) {
                        System.out.println("sudah boss");
                    } else {

                        if (id_DD != 0) {
                            for (int row = 0; row < datas.size(); row++) {
                                int id_attribute = 0;
                                PreparedStatement psi = conn.prepareStatement("SELECT id, field FROM atribut WHERE field = ?");
                                psi.setString(1, datas.get(row).getFieldName().toString());
                                ResultSet rsi = psi.executeQuery();
                                if (rsi.next()) {
                                    id_attribute = rsi.getInt("id");

                                    String query = "INSERT INTO datadictionary_atribut (id_datadictionary, id_atribut) VALUES (?, ?)";
                                    PreparedStatement preparedStmt = conn.prepareStatement(query);
                                    preparedStmt.setString(1, String.valueOf(id_DD));
                                    preparedStmt.setString(2, String.valueOf(id_attribute));
                                    preparedStmt.execute();
                                    System.out.println("Success to insert datadictionary_attribut " + rsi.getString("field"));
                                    fileListView.setDisable(false);

                                } else {
                                    System.out.println("attribut gagal di insert");
                                }
                            }
                        } else {
                            System.out.println("Datadictionary belum di insert");
                        }
                    }
                } catch (Exception e) {
                    System.out.println("error " + e.toString());
                }
                conn.close();
                System.out.println("Data berhasil disimpan pada database " + TFAktor.getText());
                radioDisable();
            } catch (Exception e) {
                System.out.println("koneksi gagal : " + e);
            }
            title.add("Atribut", datasets);
            Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

            System.out.println(gson.toJson(title));
            TAJson.setText(gson.toJson(title));
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
            fileChooser.getExtensionFilters().add(extFilter);
            Window primaryStage = null;

            //Show save file dialog
            File file = fileChooser.showSaveDialog(primaryStage);

            try {
                if (file != null) {
                    SaveFile(gson.toJson(title), file);
                    path = file.getAbsolutePath().replace(file.getName(), "");
                }
                //(FileWriter file = new FileWriter("E:\\Kuliah\\TA\\File Save\\" + TFDataFlow.getText() + ".json")) {
//                file.write(gson.toJson(title));
//                file.flush();
                if (fileListView.getSelectionModel().getSelectedIndex() == -1) {
                    System.out.println("NOTHING SELECTED!");
                } else {
                    fileListView.getItems().remove(fileListView.getSelectionModel().getSelectedItem());
                    System.out.println("bisa delete");
                }

                try {
                    msWordGenerate();
                } catch (Exception e) {
                    System.out.println("error " + e);
                }
                infoFileTersimpan("File Tersimpan dengan nama : " + TFDataFlow.getText(), "Information");

                tableView.getItems().clear();
                TFAktor.clear();
                TFKode.clear();
                TFDataFlow.clear();
//                TAJson.clear();

                clear();
            } catch (Exception e) {
                System.out.println("error" + e);;
            }

        }
    }

    private void SaveFile(String content, File file) {
        try {
            FileWriter fileWriter;

            fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            System.out.println("eror save " + ex);
        }

    }

    public void clear() {
        fileListView.setDisable(false);
        tableView.getItems().clear();
        CBdataType.setDisable(false);
        CBfieldName.setDisable(false);
        TFfieldSize.setDisable(false);
        TFalias.setDisable(false);
        TAdescription.setDisable(false);
        tambah.setDisable(false);
        CBdataType.getItems().clear();
        CBfieldName.getItems().clear();
        TFfieldSize.clear();
        TFalias.clear();
        TAdescription.clear();
        TFKode.clear();
        TFDataFlow.clear();
        TFAktor.clear();
        TAdescription.clear();
        TADocumentDescription.clear();
        TFProses.clear();
        TFAktifitas.clear();
//        TFProses.clear();
    }

    @FXML
    public void actionCancel(ActionEvent event) {
        clear();
    }

    //fungsi ini untuk menambahkan atribut kedalam tabel
    @FXML
    public void actionTambah(ActionEvent event) throws JAXBException {
        String name = (String) CBfieldName.getValue();
        String type = (String) CBdataType.getValue();
        String length = TFfieldSize.getText();
        String alias = TFalias.getText();
        String description = TAdescription.getText();

        DDFormat ddFormat = new DDFormat(name, alias, type, length, description);
        try {
            if (CBfieldName.getValue() == null
                    || CBdataType.getValue() == null
                    || TFfieldSize.getText().isEmpty()
                    || TFalias.getText().isEmpty()
                    || TAdescription.getText().isEmpty()) {
                notifKosongTambahAtribut();
            } else {
                tableView.getItems().add(ddFormat);
                CBfieldName.getItems().remove(CBfieldName.getValue());
                TFalias.clear();
                TFfieldSize.clear();
                TAdescription.clear();
            }
        } catch (Exception e) {
            System.out.println("error tambah tabel " + e.toString());
        }
    }

    protected Node getNode(String tagName, NodeList nodes) {
        for (int x = 0; x < nodes.getLength(); x++) {
            Node node = nodes.item(x);
            if (node.getNodeName().equalsIgnoreCase(tagName)) {
                return node;
            }
        }

        return null;
    }

    //fungsi ini digunakan untuk mengambil atribut yang ada pada file xml
    protected String getNodeAttr(String attrName, Node node) {
        NamedNodeMap attrs = node.getAttributes();
        for (int y = 0; y < attrs.getLength(); y++) {
            Node attr = attrs.item(y);
            if (attr.getNodeName().equalsIgnoreCase(attrName)) {
                return attr.getNodeValue();
            }
        }
        return "";
    }

    //fungsi ini digunakan untuk meload file xml dan mengambil tag" yang diperlukan pada tool
    @FXML
    public void loadFile(ActionEvent event) throws JAXBException, ParserConfigurationException, SAXException, IOException {
        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("E:\\Kuliah\\TA\\prasidang"));
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("xml file", "*.xml"));
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            fileListView.getItems().clear();
            data_flow_temp.clear();
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(selectedFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nListExternal = doc.getElementsByTagName("DFExternalEntity");

            NodeList nlistP = doc.getElementsByTagName("DataFlowDiagram");
            String s = getNodeAttr("Name", nlistP.item(0));
            s = s.substring(s.indexOf("[") + 1);
            s = s.substring(0, s.indexOf("]"));
            codeProses = s;
            String n = getNodeAttr("Name", nlistP.item(0));

            String np = n.replace(s, "");
            String tanda = "[]";
            np = np.replace(tanda, "");
            nameProses = np;

            for (int i = 0; i < nListExternal.getLength(); i++) {
                Node nodeFrom = getNode("FromSimpleRelationships", nListExternal.item(i).getChildNodes());
                Node nodeTo = getNode("ToSimpleRelationships", nListExternal.item(i).getChildNodes());
                ArrayList<String> from = new ArrayList<>();
                ArrayList<String> to = new ArrayList<>();
                if (nodeFrom != null) {
                    Element nodeFromElement = (Element) nodeFrom;
                    NodeList nlistDataFlow = nodeFromElement.getElementsByTagName("DFDataFlow");
                    for (int j = 0; j < nlistDataFlow.getLength(); j++) {
                        from.add(getNodeAttr("Name", nlistDataFlow.item(j)));
                    }
                }
                if (nodeTo != null) {
                    Element nodeToElement = (Element) nodeTo;
                    NodeList nlistDataFlow = nodeToElement.getElementsByTagName("DFDataFlow");
                    for (int j = 0; j < nlistDataFlow.getLength(); j++) {
                        to.add(getNodeAttr("Name", nlistDataFlow.item(j)));
                    }
                }
                External external = new External(getNodeAttr("Name", nListExternal.item(i)), from, to);
                allExternal.add(external);
            }
            NodeList nListProcess = doc.getElementsByTagName("DFProcess");
            for (int i = 0; i < nListProcess.getLength(); i++) {
                Node nodeFrom = getNode("FromSimpleRelationships", nListProcess.item(i).getChildNodes());
                Node nodeTo = getNode("ToSimpleRelationships", nListProcess.item(i).getChildNodes());
                ArrayList<String> from = new ArrayList<>();
                ArrayList<String> to = new ArrayList<>();
                if (nodeFrom != null && nodeTo != null) {
                    Element nodeFromElement = (Element) nodeFrom;
                    NodeList nlistDataFlow = nodeFromElement.getElementsByTagName("DFDataFlow");
                    for (int j = 0; j < nlistDataFlow.getLength(); j++) {
                        from.add(getNodeAttr("Name", nlistDataFlow.item(j)));
                    }
                    Element nodeToElement = (Element) nodeTo;
                    nlistDataFlow = nodeToElement.getElementsByTagName("DFDataFlow");
                    for (int j = 0; j < nlistDataFlow.getLength(); j++) {
                        to.add(getNodeAttr("Name", nlistDataFlow.item(j)));
                    }
                    Process process = new Process(getNodeAttr("Name", nListProcess.item(i)), getNodeAttr("DfId", nListProcess.item(i)), from, to);
                    allProcess.add(process);
                }
            }
            //untuk menampilkan ke list view
            NodeList nList = doc.getElementsByTagName("DFDataFlow");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    data_flow_temp.add(eElement.getAttribute("Name"));
                }
            }
            //bertujuan untuk mengeecek kalau ada data yang redundan
            Set<String> aSet = new HashSet<String>(data_flow_temp);
            loadCombo(aSet);
        } else {
            System.out.println("file is not valid");
        }
    }

    //fungsi ini digunakan untuk mengambil data dari database apabila atribut yang ingin di edit sudah ada
    @FXML
    public void actionCBFieldName() throws Exception {
        Connection conn = con.getConnection();
        try {
            fileListView.setDisable(true);
            PreparedStatement ps = conn.prepareStatement("SELECT alias, data_type, length, description FROM atribut WHERE field = ?");
            ps.setString(1, CBfieldName.getValue().toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                //  String field = rs.getString("field");
                String alias = rs.getString("alias");
                String data_type = rs.getString("data_type");
                String length = rs.getString("length");
                String description = rs.getString("description");

                TFalias.setText(alias);
                CBdataType.setValue(data_type);
                TFfieldSize.setText(length);
                TAdescription.setText(description);
            } else {
                TFalias.clear();
                TFfieldSize.clear();
                TAdescription.clear();
                System.out.println("Ga da");
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("eror cek field name " + e);
        }
    }

    //fungsi ini digunakan untuk menentukan nilai dari data type
    @FXML
    public void actionDataType() {
        if (CBdataType.getValue() == "String") {
            TFfieldSize.setText("255");
        } else if (CBdataType.getValue() == "Integer") {
            TFfieldSize.setText("11");
        } //        else if (CBdataType.getValue() == "Double") {
        //            TFfieldSize.setText("18");
        //        } else if (CBdataType.getValue() == "Boolean") {
        //            TFfieldSize.setText("1");
        //        } 
        else if (CBdataType.getValue() == "Date") {
            TFfieldSize.setText("22");
        } else if (CBdataType.getValue() == "Currency") {
            TFfieldSize.setText("14,4");
        }
    }

    //fungsi ini digunakan untuk menampung nilai dari data flow
    public void loadCombo(Set<String> data_flow_temp) {
        for (int i = 0; i < data_flow_temp.size(); i++) {
            String cari = "[";
            if (data_flow_temp.toArray()[i].toString().toLowerCase().indexOf(cari) != -1) {
                fileListView.getItems().add(data_flow_temp.toArray()[i]);
            } else {
                System.out.println("gakbisa itu");
            }
        }
    }

    //fungsi ini digunakan untuk mengecek apbila dokumen yang ingin digerate sudah ada sebelumnya
    @FXML
    public void klikMouseListView(MouseEvent arg0) throws Exception {
        tableView.getItems().clear();
        TAJson.clear();
        Connection conn = con.getConnection();
        try {

            PreparedStatement ps = conn.prepareStatement("SELECT id, aktor, aktifitas, code, nama_dokumen, bentuk_data, kode_proses, proses, deskripsi_dokumen FROM data_dictionary WHERE nama_dokumen= ?");
            ps.setString(1, TFDataFlow.getText());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                radioDisable();
                CBfieldName.setDisable(true);
                CBdataType.setDisable(true);
                TFalias.setDisable(true);
                TFfieldSize.setDisable(true);
                TAdescription.setDisable(true);
                tambah.setDisable(true);
                String code = rs.getString("code");
                String nama_dokumen = rs.getString("nama_dokumen");
                String kode_proses = rs.getString("kode_proses");
                String proses = rs.getString("proses");
                String data_format = rs.getString("bentuk_data");
                String deskripsi_dokumen = rs.getString("deskripsi_dokumen");
                String aktor = rs.getString("aktor");
                String aktifitas = rs.getString("aktifitas");
                int id = rs.getInt("id");
                TFKode.setText(code);
                System.out.println("bisa nig" + data_format);
                TFDataFlow.setText(nama_dokumen);
                TFProses.setText(proses);
                TFKodeProses.setText(kode_proses);
                TADocumentDescription.setText(deskripsi_dokumen);
                TFAktor.setText(aktor);
                TFAktifitas.setText(aktifitas);
                //coba
                String[] ss = data_format.split(", ");
                for (int i = 0; i < ss.length; i++) {
                    ss[i] = ss[i].replaceAll("", "");
                    if (ss[i].equals("Digital")) {
                        RBDigital.setSelected(true);
                    }
                    if (ss[i].equals("Dokumen Tercetak")) {
                        RBDokumenTercetak.setSelected(true);
                    }
                    if (ss[i].equals("Formulir")) {
                        RBFormulir.setSelected(true);
                    }
                    if (ss[i].equals("Laporan Tercetak")) {
                        RBLaporanTercetak.setSelected(true);
                    }

                }

                PreparedStatement psp = conn.prepareStatement("SELECT atribut.field, atribut.alias, atribut.data_type, atribut.length, atribut.description FROM atribut JOIN datadictionary_atribut ON atribut.id = datadictionary_atribut.id_atribut AND datadictionary_atribut.id_datadictionary = ?");
                psp.setString(1, String.valueOf(id));
                ResultSet rsp = psp.executeQuery();
                while (rsp.next()) {
                    String field = rsp.getString("atribut.field");
                    String alias = rsp.getString("atribut.alias");
                    String dataType = rsp.getString("atribut.data_type");
                    String length = rsp.getString("atribut.length");
                    String description = rsp.getString("atribut.description");
                    DDFormat ddFormat = new DDFormat(field, alias, dataType, length, description);
                    try {
                        tableView.getItems().add(ddFormat);
                    } catch (Exception e) {
                        System.out.println("eror list view " + e.toString());
                    }
                }
                conn.close();
            } else {
                tableView.getItems().clear();
                TADocumentDescription.clear();
            }

        } catch (Exception e) {
            System.out.println("error : " + e.getMessage());
        }
    }

    private void radioDisable() {
        RBDigital.setDisable(false);
        RBDokumenTercetak.setDisable(false);
        RBFormulir.setDisable(false);
        RBLaporanTercetak.setDisable(false);
        RBDigital.setSelected(false);
        RBDokumenTercetak.setSelected(false);
        RBFormulir.setSelected(false);
        RBLaporanTercetak.setSelected(false);
    }

    //fungsi ini bertujuan untuk memisahakan atribut dari nama data flow-nya pada saat filelistview di klik
    private void selectfileListView() {

        fileListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    Connection conn = con.getConnection();
                    TFKode.clear();
                    TFProses.setText(nameProses);
                    TFKodeProses.setText(codeProses);
                    radioDisable();
                    ArrayList<String> actors = new ArrayList<>();

                    try {
                        String sql = "select * from data_dictionary order by code desc";
                        Statement st = conn.createStatement();
                        ResultSet rs = st.executeQuery(sql);
                        if (rs.next()) {
                            String code = rs.getString("code").substring(12);
                            String AN = "" + (Integer.parseInt(code) + 1);
                            String Nol = "";

                            if (AN.length() == 1) {
                                Nol = "0";
                            } else if (AN.length() == 2) {
                                Nol = "";
                            }
                            TFKode.setText(("DD" + codeProses.toString().substring(2)) + "-" + Nol + AN);
                            conn.close();
                        } else {
                            TFKode.setText(("DD" + codeProses.toString().substring(2)) + "-" + "01");
                        }
                    } catch (Exception e) {
                        System.out.println("error code " + e);
                    }
                    String namasAktifitas = function.getNamaByDFlow(newValue, allProcess, allExternal);
                    TFAktifitas.setText(namasAktifitas.toString());
                    function.getActorByDFlow(actors, newValue, allExternal, allProcess);
                    Set<String> actorsSet = new HashSet<String>(actors);
                    String b = actorsSet.toString();
                    b = b.substring(b.indexOf("[") + 1);
                    b = b.substring(0, b.indexOf("]"));
                    System.out.println(b);
                    TFAktor.setText(b);

                    //untuk mengambil nama dokumen
                    String s = newValue;
                    s = s.substring(s.indexOf("[") + 1);
                    s = s.substring(0, s.indexOf("]"));
                    String np = newValue.replace(s, "");
                    String tanda = "[]";
                    np = np.replace(tanda, "");
                    TFDataFlow.setText(np);

                    //untuk memisahkan atribut dari setiap koma
                    String[] ss = s.split(",");
                    for (int i = 0; i < ss.length; i++) {
                        ss[i] = ss[i].toLowerCase().replaceAll(" ", "");
                    }
                    ObservableList<String> options = FXCollections.observableArrayList(ss);
                    CBfieldName.setItems(options);

                    CBfieldName.setDisable(false);
                    TFalias.setDisable(false);
                    TAdescription.setDisable(false);
                    TFfieldSize.setDisable(false);
                    CBdataType.setDisable(false);
                    tambah.setDisable(false);
                    TFKode.setDisable(false);
                    TFDataFlow.setDisable(false);
                    TFKodeProses.setDisable(false);
                    TFProses.setDisable(false);
                    TFAktor.setDisable(false);
                    batal.setDisable(false);
                    createJson.setDisable(false);
                    TFAktifitas.setDisable(false);
                    TADocumentDescription.setDisable(false);
                    //untuk set nilai data type
                    if (CBdataType.getItems().isEmpty()) {
                        CBdataType.getItems().addAll(
                                masterData
                        );
                    } else {
                        CBfieldName.getItems().removeAll(CBfieldName);
                    }

                } catch (Exception e) {
                    System.out.println("Error at selectfileListView() : " + e.getMessage());
                }
            }
        });
    }

    private void msWordGenerate() throws Exception {
        XWPFDocument document = new XWPFDocument();
        try (FileOutputStream out = new FileOutputStream(new File(path + TFDataFlow.getText() + ".docx"))) {

            String dataFormat = radio.toString();
            dataFormat = dataFormat.substring(dataFormat.indexOf("[") + 1);
            dataFormat = dataFormat.substring(0, dataFormat.indexOf("]"));

            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.addBreak();
            run.setText("Kode Data Dictionary");
            run.addTab();
            run.setText(": " + TFKode.getText());
            run.addBreak();
            run.setText("Nama Dokumen");
            run.addTab();
            run.setText(": " + TFDataFlow.getText());
            run.addBreak();
            run.setText("Bentuk Data");
            run.addTab();
            run.addTab();
            run.setText(": " + dataFormat);
            run.addBreak();
            run.setText("Kode Proses");
            run.addTab();
            run.addTab();
            run.setText(": " + TFKodeProses.getText());
            run.addBreak();
            run.setText("Nama Proses");
            run.addTab();
            run.addTab();
            run.setText(": " + TFProses.getText());
            run.addBreak();
            run.setText("Aktivitas");
            run.addTab();
            run.addTab();
            run.setText(": " + TFAktifitas.getText());
            run.addBreak();
            run.setText("Aktor");
            run.addTab();
            run.addTab();
            run.addTab();
            run.setText(": " + TFAktor.getText());
            run.addBreak();
            run.setText("Atribut");
            run.addTab();
            run.addTab();
            run.addTab();
            run.setText(":");

            XWPFTable table = document.createTable();

            XWPFTableRow tableRowOne = table.getRow(0);
            tableRowOne.getCell(0).setText("Nama Atribut");
            tableRowOne.addNewTableCell().setText("Alias");
            tableRowOne.addNewTableCell().setText("Data Type");
            tableRowOne.addNewTableCell().setText("Length");
            tableRowOne.addNewTableCell().setText("Description");

            for (int row = 0; row < datas.size(); row++) {

                XWPFTableRow tableRows = table.createRow();
                tableRows.getCell(0).setText(datas.get(row).getFieldName().toString());
                tableRows.getCell(1).setText(datas.get(row).getAlias().toString());
                tableRows.getCell(2).setText(datas.get(row).getDataType().toString());
                tableRows.getCell(3).setText(datas.get(row).getLength().toString());
                tableRows.getCell(4).setText(datas.get(row).getDescription().toString());

            }

            run.setText(table.getText());

            XWPFParagraph paragraph2 = document.createParagraph();
            XWPFRun run2 = paragraph2.createRun();

            run2.setText("Deskripsi");
            run2.addTab();
            run2.addTab();
            run2.setText(": " + TADocumentDescription.getText());
            run2.addBreak();

            document.write(out);
            System.out.println("Data dictionary berhasil digenerate dalam bentuk word!");
        } catch (Exception e) {
            System.out.println("eror to word " + e);
        }
    }
}
