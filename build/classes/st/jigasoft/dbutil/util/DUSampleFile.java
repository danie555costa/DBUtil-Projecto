/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Servidor
 */
public class DUSampleFile
{
    public static ArrayList<String> loadData(String configbin)
    {
        try
        {
            File file = new File(configbin);
            Scanner sc = new Scanner(file);
            ArrayList<String> listData = new ArrayList<>();
            while (sc.hasNextLine())
                listData.add(sc.nextLine());
            sc.close();
            return  listData;
            
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return null;
    }
    
    
    public static boolean exportDataTxt(ArrayList<String> listData, String dirFile, boolean continueWriter)
    {
        try
        {
            try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File(dirFile), continueWriter)))
            {
                listData.stream().forEach((s) -> pw.println(s));
                pw.close();
                return true;
            }catch(Exception ex1)
            {
                return false;
            }
        }catch(Exception ex) 
        {
            return false;
        }
    }
}
