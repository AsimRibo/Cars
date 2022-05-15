/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra;

import hr.algebra.dao.RepositoryFactory;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author asim2
 */
public class CarsApplication extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
       Parent root = FXMLLoader.load(getClass().getResource("view/Cars.fxml"));
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Cars info");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        RepositoryFactory.getRepository().release();
        super.stop();
    }
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
