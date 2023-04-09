package fr.arcelormittal.Models;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class FileReader {

    private static FileReader instance = null;

    public static FileReader getInstance(){
        if (instance == null) instance = new FileReader();
        return instance;
    }

    public void read(String filePath) throws IOException, SQLException {
        Scanner sc = new Scanner(new File("C:/.dev/eclipse_workspace/FileReader/" +
                "src/Models/1939351_F2.txt"));
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
