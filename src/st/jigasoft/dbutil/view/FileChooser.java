/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import st.jigasoft.dbutil.view.callback.OnFileChoserCansel;
import st.jigasoft.dbutil.view.callback.OnFileChoserAproved;

/**
 *
 * @author xdata
 */
public class FileChooser extends JFileChooser{

    private List<OnFileChoserAproved> listOnFileChoserAproved;
    private List<OnFileChoserCansel> listOnFileChoserCanselable;
    
    public FileChooser() {
        
        this.listOnFileChoserAproved = new ArrayList<>();
        this.listOnFileChoserCanselable = new ArrayList<>();
        this.onCreate();
        
    }

    public FileChooser(String currentDirectoryPath) {
        super(currentDirectoryPath);
        this.onCreate();
    }

    public FileChooser(File currentDirectory) {
        super(currentDirectory);
        this.onCreate();
    }

    public FileChooser(FileSystemView fsv) {
        super(fsv);
        this.onCreate();
    }

    public FileChooser(File currentDirectory, FileSystemView fsv) {
        super(currentDirectory, fsv);
        this.onCreate();
    }

    public FileChooser(String currentDirectoryPath, FileSystemView fsv) {
        super(currentDirectoryPath, fsv);
        this.onCreate();
    }
    
    private void onCreate() 
    {
        super.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getActionCommand().equals(APPROVE_SELECTION))
                    onAprovedAction();
                else if(e.getActionCommand().equals(CANCEL_SELECTION)) onReprovedAction();
            }

            
        });
    }
    
    private void onAprovedAction() {
        this.listOnFileChoserAproved.forEach( (ap) ->
        {
            ap.accept(this);
        });
    }

    private void onReprovedAction() 
    {
        listOnFileChoserCanselable.stream().forEach((cansel) -> {
            cansel.accept(FileChooser.this);
        });
    }
    
    public void addOnCanselableListiner(OnFileChoserCansel cansel){
        this.listOnFileChoserCanselable.add(cansel);
    }
    
    public void addOnAprovedListiner(OnFileChoserAproved save)
    {
        this.listOnFileChoserAproved.add(save);
    }
}
