/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Views;

import Controls.CtlUser;
import Models.MUser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javaswingdev.main.Main;

public class Launcher {

    private static CtlUser user = new CtlUser();
    private static ArrayList<MUser> array;

    public static void main() {
        init();
    }

    private static void init() {
        File folder = new File("files");       
        
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();

            String fileNames = "";

            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        fileNames = file.getName();
                        break;
                    }
                }
            }

            if (!fileNames.isEmpty()) {
                String userName = null;
                String password = null;
                String role1 = null;
                File file = new File("files/" + fileNames);
                if (file.exists()) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            if (line.startsWith("User: ")) {
                                userName = line.split(": ")[1];
                                continue;
                            } else if (line.startsWith("Password: ")) {
                                password = line.split(": ")[1];
                                continue;
                            } else {
                                role1 = line.split(": ")[1];
                                break;
                            }
                        }
                        if (logIn(userName, password, role1)) {
                            new javaswingdev.main.Main(role1).setVisible(true);
                        } else {
                            new Main().logOut();
                            new Login().setVisible(true);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                new Login().setVisible(true);
            }
        }
    }

    private static boolean logIn(String userName, String password, String Role) {
        array = user.qeuryLogin(userName.trim());

        for (MUser read : array) {
            if (read.getPassword().equals(password) & read.getUsername().equalsIgnoreCase(userName.trim()) & Role.equalsIgnoreCase(read.getRole())) {
                return true;
            }
        }
        return false;
    }

}
