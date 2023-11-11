package com.cristian.TaskApplication.presentation;

import com.cristian.TaskApplication.TaskApplication;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.io.IOException;

public class SystemTaskFX extends Application {
/*    public static void main(String[] args) {
        launch(args);
    }*/

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        this.applicationContext = new SpringApplicationBuilder(TaskApplication.class).run();
    }

    //Aca hago la integracion Spring
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(TaskApplication.class.getResource("/templates/index.fxml"));
    loader.setControllerFactory(applicationContext::getBean);
    //Ahora llamo la escena de javaFX
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setResizable(false); //Con esta linea logro que no se pueda maximizar la pantalla.
        stage.show();
    }

    //Voy a utilizar el metodo stop para cerrar las conexiones que tenga abierta a la base de datos
    //y tambien la aplicacion en si de FX
    @Override
    public void stop(){
        applicationContext.close();
        Platform.exit();
    }
}
