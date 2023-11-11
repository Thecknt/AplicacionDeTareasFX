package com.cristian.TaskApplication.controller;

import com.cristian.TaskApplication.model.Task;
import com.cristian.TaskApplication.service.TaskService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class IndexController implements Initializable {

    //Creo un logger por si necesito enviar mensajes a la consola, pero podrias hacerlo con un sout tambien
    //private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    private TaskService taskService;

    //Declaro la tabla principal
    @FXML
    private TableView<Task> tableTask;

    //Declaro cada una de las columnas de la tabla
    @FXML
    private TableColumn<Task, Integer> idTaskColumn;

    @FXML
    private TableColumn<Task, String> taskColumn;

    @FXML
    private TableColumn<Task, String> responsibleTask;

    @FXML
    private TableColumn<Task, String> statusTask;

    private Integer internalID;

    //Con esto ya tengo los elementos para poder cargar la tabla
    private final ObservableList<Task> taskList = FXCollections.observableArrayList();

    @FXML
    private TextField nameText;

    @FXML
    private TextField responsibleText;

    //@FXML
    //private TextField statusText;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private String comboBoxEvent() {
        //String dataComboSelected = e.getTarget().toString();
        //Object evt = e.getSource();

        String resultChoise = comboBox.getSelectionModel().getSelectedItem();
        System.out.println(resultChoise);

        return resultChoise;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Configuro para que solo se pueda seleccionar un elemento de la Tabla
        //Para eliminar o editar un registro
        tableTask.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configColumns();
        showTasks();
        comboBox.getItems().add("Pendiente");
        comboBox.getItems().add("En Proceso");
        comboBox.getItems().add("Completado");
    }


    private void configColumns() {
        idTaskColumn.setCellValueFactory(new PropertyValueFactory<>("idTask"));
        taskColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));
        responsibleTask.setCellValueFactory(new PropertyValueFactory<>("taskResponsible"));
        statusTask.setCellValueFactory(new PropertyValueFactory<>("taskStatus"));
    }

    private void showMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showTasks() {
        //Limpio la tabla
        taskList.clear();
        taskList.addAll(taskService.showAllTask());
        tableTask.setItems(taskList);
    }

    public void addButton() {
        if (nameText.getText().isEmpty()) {
            showMessage("Error Campo Vacio", "Ingrese el nombre de la Tarea");
            nameText.requestFocus();
            return;
        }
        else {

            Task task = new Task();
            captureDataForm(task);
            task.setIdTask(null);
            taskService.saveTask(task);
            showMessage("Informacion", "Tarea agregada con Exito!");
            cleanForm();
            showTasks();
        }
    }

    public void loadDataForm() {
        Task task = tableTask.getSelectionModel().getSelectedItem();
        if (task != null) {
            internalID = task.getIdTask();
            nameText.setText(task.getTaskName());
            responsibleText.setText(task.getTaskResponsible());
           // statusText.setText(task.getTaskStatus());
        }
    }

    public void captureDataForm(Task task) {
        if (internalID != null)
            //comboBoxEvent();
            task.setIdTask(internalID);
        task.setTaskName(nameText.getText());
        task.setTaskResponsible(responsibleText.getText());
        task.setTaskStatus(comboBoxEvent());
    }

    private void cleanForm() {
        internalID = null;
        nameText.clear();
        responsibleText.clear();
        //statusText.clear();
    }

    public void deleteButton() {
        Task task = new Task();
        if (internalID == null) {
            showMessage("Campo vacio", "Debe seleccionar una tarea");
            return;
        }
        if (nameText.getText().isEmpty()) {
            showMessage("Campo vacio", "Debe ingresar una tarea");
            nameText.requestFocus();
            return;
        }
        captureDataForm(task);
        taskService.deleteTask(task);
        showMessage("Informacion", "Tarea Eliminada con exito!");
        cleanForm();
        showTasks();
    }

    public void modifyButton() {
        if (internalID == null) {
            showMessage("Campo vacio", "Debe seleccionar una tarea");
            return;
        }
        if (nameText.getText().isEmpty()) {
            showMessage("Campo vacio", "Debe ingresar una tarea");
            nameText.requestFocus();
            return;
        }
        Task task = new Task();
        captureDataForm(task);
        taskService.saveTask(task);
        showMessage("Informacion", "Tarea modificada con exito!");
        cleanForm();
        showTasks();
    }

    public void clearButton() {
        cleanForm();
    }
}
