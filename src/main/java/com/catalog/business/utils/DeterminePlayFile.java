/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catalog.business.utils;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Gile
 */
public class DeterminePlayFile {

    public enum OperationType {
        PLAY_FILE,OPEN_FOLDER;
    };

    public static void main(String[] args) {
        File test = new File("e:\\serije\\castle");
        String path = "e:\\serije\\castle";

        try {
            Desktop.browse(test.toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void performActionOnTitle(String path, OperationType operation){
        File title = new File(path);
        if(operation.equals(OperationType.OPEN_FOLDER)){
            if(title.isDirectory()){
                try {
                    Desktop.browse(title.toURI());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else if(operation.equals(OperationType.PLAY_FILE)){
            File f = determineFile(path);
            try {
                Desktop.open(f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static File determineFile(String path) {

        File name = new File(path);

            ArrayList<File> potentialFiles = new ArrayList<File>();
            ArrayList<File> encounteredFolders = new ArrayList<File>();
            File determinedFile = null;
            try {
                if (name.isFile()) {
                    //u pitanje je fajl i pokrecemo ga preko default player-a
                    determinedFile = name;
                } else {
                    System.out.println("Name is: "+name);
                    //u pitanje je folder i moramo da odredimo koji je fajl koji se pusta
                    for (File f : name.listFiles()) {
                        if(f.isDirectory())
                            encounteredFolders.add(f);
                        String fileName = f.getName().toLowerCase();
                        if (fileName.endsWith(".avi") || fileName.endsWith(".mp4") || fileName.endsWith(".mkv") || fileName.endsWith(".wmv")) {
                            potentialFiles.add(f);
                        }
                    }

                    if (potentialFiles.isEmpty()) {
                        //nije nadjeno nista, verovatno je folder u folderu
                        if (encounteredFolders.size() == 1) {
                            //provereno je SAMO JEDAN folder u folderu, sad samo gledamo koji fajl unutra moze da bude za pustanje
                            File innerFolder = encounteredFolders.get(0);
                            for (File f : innerFolder.listFiles()) {
                                String fileName = f.getName().toLowerCase();
                                if (fileName.endsWith(".avi") || fileName.endsWith(".mp4") || fileName.endsWith(".mkv") || fileName.endsWith(".wmv")) {
                                    potentialFiles.add(f);
                                }
                            }

                            if(potentialFiles.isEmpty())
                            {
                                determinedFile=encounteredFolders.get(0);
                            }
                            else if (potentialFiles.size() == 1)
                            {
                                //nadjen je jedan potencijalni fajl u folderu i njega pustamo
                                determinedFile = potentialFiles.get(0);
                            }
                            else
                            {
                                //verovatno je nadjen i sample pored fajla za pustanje
                                File max = potentialFiles.get(0);
                                for (int i = 0; i < potentialFiles.size(); i++) {
                                    if (potentialFiles.get(i).length() > max.length()) {
                                        max = potentialFiles.get(i);
                                    }
                                }
                                determinedFile = max;
                            }
                        } else {
                            //previse zakomplikovano, otvaramo folder
                            determinedFile=name;
                        }

                    } else if (potentialFiles.size() == 1) {
                        //nadjen je jedan potencijalni fajl u folderu i njega pustamo
                        determinedFile = potentialFiles.get(0);
                    } else {
                        //verovatno je nadjen i sample pored fajla za pustanje
                        File max = potentialFiles.get(0);
                        for (int i = 0; i < potentialFiles.size(); i++) {
                            if (potentialFiles.get(i).length() > max.length()) {
                                max = potentialFiles.get(i);
                            }
                        }
                        determinedFile = max;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        return determinedFile;
    }

}
