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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.gluonhq.charm.down.common.PlatformFactory;

/**
 * Use this class to store/retrieve properties values in/from a file, with a 
 * JavaFX application runnable in Desktop, Android or iOS devices.
 * 
 * Running like a Desktop application it will store the file in a folder called
 * .gluon inside your home folder.
 * In Android, the file will be stored in the files application private folder.
 * In iOS will be in Library/Caches/gluon folder inside app folder.
 * 
 * Include below lines in build.gradle:
 * ext.CHARM_DOWN_VERSION = "1.0.0"
 * dependencies { 
 *      compile "com.gluonhq:charm-down-common:$CHARM_DOWN_VERSION"
 *      desktopRuntime "com.gluonhq:charm-down-desktop:$CHARM_DOWN_VERSION"
 *      androidRuntime "com.gluonhq:charm-down-android:$CHARM_DOWN_VERSION"
 *      iosRuntime "com.gluonhq:charm-down-ios:$CHARM_DOWN_VERSION" 
 * }
 * 
 * And in jfxmobile / ios section add:
 *      forceLinkClasses = [ 'com.gluonhq.charm.down.**.*' ]
 * 
 * Use "Project menu > Reload project" to download dependencies
 * 
 * @author Javier García Escobedo <javiergarciaescobedo.es>
 * @version 20151115
 */
public class PropertiesJavaFxPorts {

    private final String FILENAME;
    private File path;
    private final Properties props;

    /**
     * 
     * @param fileName
     */
    public PropertiesJavaFxPorts(String fileName) {
        try {
            this.path = PlatformFactory.getPlatform().getPrivateStorage();
        } catch (IOException e) {
            String tmpdir = System.getProperty("java.io.tmpdir");
            this.path = new File(tmpdir);
        }
        this.FILENAME = fileName;
        this.props = new Properties();
    }
        
    /**
     *
     * @param key
     * @param value
     */
    public void setProperty(String key, String value) {
        try {
            props.setProperty(key, value);
            File file = new File(path, FILENAME);
            props.store(new FileWriter(file), FILENAME);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param key
     * @return Value for the property key specified
     */
    public String getProperty(String key) {
        Reader reader = null;
        try {
            File file = new File(path, FILENAME);
            reader = new FileReader(file);
            props.load(reader);
        } catch (FileNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
        String value = props.getProperty(key);
        return value;
    }
    
    /**
     *
     * @return Full path to folder where properties file will be stored
     */
    public String getPath() {
        return path.getPath();
    }
    
}
