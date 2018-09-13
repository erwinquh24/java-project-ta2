/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.functions;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.model.External;
import org.model.Process;

/**
 *
 * @author erwin
 */
public class Function {

    public void printAllExternal(List<External> allExternal) {
        for (External external : allExternal) {
            System.out.println(external.nama);
            System.out.println(external.from);
        }
    }

    public void printAllProcess(List<Process> allProcess) {
        for (Process process : allProcess) {
            System.out.println(process.nama);
            System.out.println(process.from);
            System.out.println(process.to);
        }
    }

    public void getActorByDFlow(ArrayList<String> actors, String dataFlow, List<External> allExternal, List<Process> allProcess) {

        for (External external : allExternal) {
            if (external.from.contains(dataFlow)) {
                actors.add(external.nama);
            }
        }
        for (Process process : allProcess) {
            if (process.from.contains(dataFlow)) {
                for (String to : process.to) {
                    getActorByDFlow(actors, to, allExternal, allProcess);
                }
            }
        }

    }

    public String getCodeByDFlow(String dataFlow, List<Process> allProcess, List<External> allExternal) {
        for (Process process : allProcess) {
            if (process.from.contains(dataFlow)) {
//                String ret[] = {process.getNama(),process.getCode()}; 
                return process.getCode();
            }
        }
        for (External external : allExternal) {
            if (external.from.contains(dataFlow)) {
                for (String to : external.getTo()) {
                    return getCodeByDFlow(to, allProcess, allExternal);
                }
            }
        }

        return null;
    }

    public String getNamaByDFlow(String dataFlow, List<Process> allProcess, List<External> allExternal) {
        for (Process process : allProcess) {
            if (process.from.contains(dataFlow)) {
//                String ret[] = {process.getNama(),process.getCode()}; 
                return process.getNama();
            }
        }
        for (External external : allExternal) {
            if (external.from.contains(dataFlow)) {
                for (String to : external.getTo()) {
                    return getCodeByDFlow(to, allProcess, allExternal);
                }
            }
        }

        return null;
    }

    public static void infoBoxKosong(String headerMessage, String infoMessage, String titleBar) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    public static void infoFileTersimpan(String infoMessage, String titleBar) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleBar);
        //alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    public void getProcess(ArrayList<String> proses) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
