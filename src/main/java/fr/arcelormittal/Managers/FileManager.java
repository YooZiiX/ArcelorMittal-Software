package fr.arcelormittal.Managers;

import fr.arcelormittal.Models.Mesure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class FileManager {

    private static FileManager instance = null;
    private Scanner sc = null;

    public FileManager() throws FileNotFoundException {
        sc = new Scanner(new File("./src/main/resources/fr/arcelormittal/Orowan/Sensors/1939351_F2.txt"));
    }

    public static FileManager getInstance() throws FileNotFoundException {
        if (instance == null) instance = new FileManager();
        return instance;
    }

    public void read() throws IOException, SQLException {
        sc.useDelimiter(";");
        if (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (!line.isEmpty()){
                Mesure m = new Mesure();
                String[] data = line.split("; ");
                for (int i = 0; i < data.length; i++) {
                    m.getValues().add(data[i].replace(',','.'));
                }
                m.addValue();
            }
        } else {
            sc.close();
        }
    }

    public void write(){
        try {
            FileWriter writer = new FileWriter("./src/main/resources/fr/arcelormittal/Orowan/Input/inv_cst.txt");
            writer.write("Cas\tHe\tHs\tTe\tTs\tDiam_WR\tWRyoung\toffset ini\tmu_ini\tForce\tG\n");
            System.out.println(DAOManager.getInstance().getInput());
            writer.write(DAOManager.getInstance().getInput().replace(",","\t"));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
