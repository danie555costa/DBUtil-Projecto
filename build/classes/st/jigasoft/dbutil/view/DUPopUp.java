/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.view;

import st.jigasoft.dbutil.util.models.DUContentModal;
import st.jigasoft.dbutil.util.models.DUModal;
import java.awt.event.ActionEvent;
import java.util.function.Consumer;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.Timer;

/**
 *
 * @author Servidor
 */
public class DUPopUp extends JPopupMenu implements DUModal<Object>, Consumer<LabelTop.ActionLabel> 
{
    private DUContentModal contentModal;
    private JComponent referencesOpen;
    private boolean outoClose;
    private boolean visible;
    private OpenMode transferFrom;
    private int pointX;
    private int pointY;
    private final JComponent parrent;
    private int secondTransfer;
    private int milisecondPast;
    private Timer timerTransfer;
    private Integer maximunVerticalTranfer;
    private final LabelTop labelTop;
    private Consumer<Object> onPreOpen;
    private Consumer<Object> onPosOpen;
    private Consumer<Object> onPosClose;
    private Consumer<Object> onPreClose;
    
    
    public DUPopUp(DUContentModal contentModal, JComponent referenceOpen, JComponent parrent)
    {
        this.contentModal = contentModal;
        
        this.labelTop = new LabelTop(this);
        
        super.add(this.labelTop);
        super.add(this.contentModal);
        this.referencesOpen = referenceOpen;
        this.transferFrom = OpenMode.APPEAR_POINT; 
        this.parrent = parrent;
        this.contentModal.acceptModal((DUModal) this);
        super.pack();
    }
    
    @Override
    public void open()
    {
        if(this.onPreOpen != null) this.onPreOpen.accept(this);
        
        difinePoints();
        this.visible = true;
        if(this.transferFrom == OpenMode.APPEAR_POINT)
            this.show(this.parrent, pointX, pointY);
        else
        {
            int initX = this.initTranferX();
            int initY = this.initTranferY();
            this.show(this.parrent, initX, initY);
            this.startTransfer();
        }
        
        if(this.onPosOpen != null) this.onPosOpen.accept(this);
    }

    private void difinePoints()
    {
        this.visible = true;
        this.show(this.parrent, 0, 0);
        int parrentHeight = this.parrent.getHeight();
        int parrentWidth = this.parrent.getWidth();
        int height = this.getHeight();
        int width = this.getWidth();
        this.pointY = (parrentHeight - height)/2;
        this.pointX = (parrentWidth - width)/2;
        this.visible = false;
    }
    
    @Override
    public void close()
    {
        if(this.onPreClose != null) this.onPreClose.accept(this);
        this.visible = false;
        this.setVisible(false);
        if(this.onPosClose != null) this.onPosClose.accept(this);
    }

    private int initTranferX() 
    {
        int currentX = this.getX();
        int width = this.getWidth();
        int x = this.pointX;
        switch(this.transferFrom)
        {
            //Esquerda
            case LEFT: case TOP_LEFT: case BOTTOM_LEFT:
                x = currentX-currentX-width;
                break;
            //Dirreita
            case RIGHT: case TOP_RIGHT: case BOTTOM_RIGHT:
                x = currentX+currentX+width;
                break;
            case BOTTOM: case TOP:
                x = pointX;
                break;
                
        }
        return x;
    }

    @Override
    public void setVisible(boolean b) 
    {
        
        super.setVisible(this.visible);
    }
    
    

    private int initTranferY() 
    {
        int currentY = this.getY();
        int height = this.getHeight();
        int y = this.pointX;
        switch(this.transferFrom)
        {
            //Esquerda
            case TOP: case TOP_LEFT: case TOP_RIGHT:
                y = currentY-currentY-height;
                break;
            //Dirreita
            case BOTTOM: case BOTTOM_RIGHT: case BOTTOM_LEFT:
                y = currentY+currentY+height;
                break;
            case LEFT: case RIGHT:
                y = pointY;
                break;
                
        }
        return y;
    }

   

    private void startTransfer()
    {
        //->   DISTANCE/TT_REPEAT = SPEED
        final int refresMilisecond = 1;
        this.milisecondPast = 0;
        int xDistance = this.pointX - this.getX();
        int yDistance = this.pointY - this.getY();
        final int ttRepeats = (this.secondTransfer*1000)/refresMilisecond;
        final double xSpeed = (double) ((double) (double) xDistance/(double)ttRepeats);
        final double ySpeed = (double) ((double) (double) yDistance/(double)ttRepeats);
        
        this.initTransfer();
        
        
        
        
        
        this.timerTransfer = new Timer(refresMilisecond, (act)->nextSpeed(act, xSpeed, ySpeed));
        this.timerTransfer.setRepeats(true);
        //this.timerTransfer.start();
    }
    
    private void nextSpeed(ActionEvent act, double xSpeed, double ySpeed) 
    {
        xSpeed = treatSpeed(xSpeed);
        ySpeed = treatSpeed(ySpeed);
        int x = (int) (this.getX() + xSpeed);
        int y = (int) (this.getY() + ySpeed);
        this.show(this.parrent, x, y);
        if(x == this.pointX || y == this.pointY)
        {
            this.show(this.parrent, this.pointX, this.pointY);
            this.timerTransfer.stop();
        }
    }
    
    private double treatSpeed(double speed) 
    {
        if(speed > -1 && speed < 1)
        {
            if(speed >=0 && speed <1) speed = 1;
            else speed =-1;
        }
        return speed;
    }

    
    private void initTransfer()
    {
        int width = this.getWidth();
        int xPoint = this.pointX;
        int yPint = this.pointY;
        switch(this.transferFrom)
        {
            case RIGHT:
                xPoint = 0-this.pointX-width;
                break;
        }
        this.show(this.parrent, xPoint, yPint);
    }
    
    

    public DUContentModal getShowPanel() {
        return contentModal;
    }

    public void setShowPanel(DUContentModal showPanel) {
        this.contentModal = showPanel;
    }

    public JComponent getReferencesOpen() {
        return referencesOpen;
    }

    public void setReferencesOpen(JComponent referencesOpen) {
        this.referencesOpen = referencesOpen;
    }

    public OpenMode getTransferFrom() {
        return transferFrom;
    }

    public void setTransferFrom(OpenMode transferFrom) {
        this.transferFrom = transferFrom;
    }

   
    public boolean isOutoClose() {
        return outoClose;
    }

    public void setOutoClose(boolean outoClose) {
        this.outoClose = outoClose;
    }

    public void setSecondTransfer(int secondTransfer)
    {
        this.secondTransfer = secondTransfer;
    }

    public int getSecondTransfer() {
        return secondTransfer;
    }

    public boolean isOpen() 
    {
        return this.visible;
    }

    public void setMaximunVerticalTranfer(Integer maximun)
    {
        this.maximunVerticalTranfer = maximun;
    }
    
    
    @Override
    public void accept(LabelTop.ActionLabel t) 
    {
        if(t == LabelTop.ActionLabel.CLOSE)
            this.close();
    }

    @Override
    public void setPreOpen(Consumer<Object> onPreOpen)
    {
        this.onPreOpen = onPreOpen;
    }

    @Override
    public void setPosOpen(Consumer<Object> onPosOpen)
    {
        this.onPosOpen = onPosOpen;
    }

    @Override
    public void setPreClose(Consumer<Object> onPreClose)
    {
        this.onPreClose = onPreClose;
    }

    @Override
    public void setPosClose(Consumer<Object> onPosClose)
    {
        this.onPosClose = onPosClose;
    }

  
    public static enum OpenMode
    {
        APPEAR_POINT,
        RIGHT,
        LEFT,
        TOP,
        BOTTOM,
        TOP_RIGHT,
        TOP_LEFT,
        BOTTOM_RIGHT,
        BOTTOM_LEFT
    }
}
