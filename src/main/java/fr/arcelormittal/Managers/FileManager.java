package fr.arcelormittal.Managers;

import fr.arcelormittal.Models.Mesure;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class FileManager {

    private static FileManager instance = null;

    public static FileManager getInstance(){
        if (instance == null) instance = new FileManager();
        return instance;
    }

    public void read() throws IOException, SQLException {
        Scanner sc = new Scanner(new File("Orowan/Sensors/1939351_F2.txt"));
        sc.useDelimiter(";");
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (!line.isEmpty()){
                Mesure m = new Mesure();
                String[] data = line.split("; ");
                for (int i = 0; i < data.length; i++) {
                    m.getValues().add(data[i].replace(',','.'));
                }
                m.addValue();
            }
        }
        sc.close();
    }
}
