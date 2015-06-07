/**
 * Created by dmitriy on 06.06.15.
 */
package org.diaspora.pages;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Helpers{

    /**
     * Execute cleardiaspora shell script, which will: drop, create db, then seed some data to it (users: bob, alice, eve)
     *
     * In order to work, the following should be done:
     *
     * 0 $ mkdir ~/.scripts
     *
     * 1 $ nano ~/.scripts/cleardiaspora
     *   Edit: put the following code into it:
     *          #!/usr/bin/env sh
     *
     *          cd /Home/<your user>/<your parth to diaspora>/diaspora
     *          RAILS_ENV=production  DB=mysql bin/rake db:drop db:create db:schema:load db:seed
     *   Ctrl + X
     *   Y
     * 2 $ chmod a+x ~/.scripts/cleardiaspora
     *
     * 3 $ echo 'export PATH="$PATH:$HOME/.scripts"' >> ~/.bashrc
     *
     * 4 Restart your IDE (if you run tests from it...)
     *
     */
    public static void dropCreateSeedDiasporaDB(){
        String output = Helpers.executeCommand("/home/dmitriy/.scripts/cleardiaspora");
        System.out.println(output);
    }

    public static String executeCommand(String command) {

        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }
}