/*
 * Copyright (C) 2015 Javier García Escobedo <javiergarciaescobedo.es>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package es.javiergarciaescobedo.propertiesjavafxports;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SamplePropertiesJavaFxPorts extends Application {

    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();

        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, visualBounds.getWidth(), visualBounds.getHeight());

        stage.getIcons().add(new Image(SamplePropertiesJavaFxPorts.class.getResourceAsStream("/icon.png")));

        stage.setScene(scene);
        stage.show();
        
        Label label = new Label();
        root.getChildren().add(label);
        
        // Create a properties file
        PropertiesJavaFxPorts propertiesJavaFxPorts = new PropertiesJavaFxPorts("app.properties");
        // Get value for "counter" key if exists
        String strCounter = propertiesJavaFxPorts.getProperty("counter");
        if(strCounter == null) {
            strCounter = "0";
        }
        int counter = Integer.valueOf(strCounter);
        counter++;
        strCounter = String.valueOf(counter);
        // Store new value for "counter" key in properties file
        propertiesJavaFxPorts.setProperty("counter", strCounter);
        
        label.setText("Counter = " + strCounter + "\n" + propertiesJavaFxPorts.getPath());
        label.setWrapText(true);
        
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case ESCAPE:
                        Platform.exit();
                        System.exit(0);
                        break;
                }
                event.consume();
            }
        });
    }

}
