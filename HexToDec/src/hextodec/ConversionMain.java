/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hextodec;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Bartosz Kawalkiewicz
 * @version 2.0
 */
public class ConversionMain extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Calculus calculator");
        ConversionView functionView = new ConversionView();
        ConversionModel model = new ConversionModel();
        ConversionController functionController = new ConversionController(model, functionView);
        Scene scene = new Scene(functionController.getView());
        primaryStage.setScene(scene);
        primaryStage.show();
        functionController.calculateByParameters(this.getParameters().getRaw());
    }

    /**
     * @param args the command line arguments format for example 10 Hex To Dec
     * 10 means the value to be transformed "Hex To Dec" or "Dec To Hex" means
     * the calculation method
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

}
