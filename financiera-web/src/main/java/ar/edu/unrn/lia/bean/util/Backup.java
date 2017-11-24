package ar.edu.unrn.lia.bean.util;

import ar.edu.unrn.lia.config.ParamValue;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Federico on 24/11/2017.
 */
@Configuration
public class Backup {

        @ParamValue(key = "backup.pathSQL")
        private static String pathSQL;

        @ParamValue(key = "backup.command")
        private static String command;

        @ParamValue(key = "backup.pathBack")
        private static String pathBack;

        public static void backup() {
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
