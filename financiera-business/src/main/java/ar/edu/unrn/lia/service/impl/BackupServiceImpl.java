package ar.edu.unrn.lia.service.impl;

import ar.edu.unrn.lia.service.BackupService;

import javax.inject.Named;

/**
 * Created by Federico on 23/11/2017.
 */
@Named("buckupService")
public class BackupServiceImpl implements BackupService{

    private static String pathSQL = "C:\\wamp64\\bin\\mysql\\mysql5.7.14\\bin\\mysqldump ";

    private static String command = "-uroot -proot --add-drop-database -B financiera_dev -r ";

    private static String pathBack = "C:\\Users\\Federico\\Desktop\\backup\\" + "financiera" + ".sql";

    public void backup() {
        Process p = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            p = runtime.exec(pathSQL + command + pathBack);
//change the dbpass and dbname with your dbpass and dbname
            int processComplete = p.waitFor();

            if (processComplete == 0) {
                System.out.println("Backup created successfully!");
            } else {
                System.out.println("Could not create the backup");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
