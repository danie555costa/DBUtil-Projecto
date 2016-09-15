package st.jigasoft.dbutil.view;

import st.jigasoft.dbutil.util.DUMoney;
import st.jigasoft.dbutil.util.DUStringNumber;
import st.jigasoft.dbutil.util.DUValidate;
import st.jigasoft.dbutil.util.models.DUComponetValidate;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.event.MouseInputAdapter;

/**
 *
 * @author Servidor
 */
public class EditText extends JTextField implements DUComponetValidate
{
    private String textHint;
    private String oldText;
    private boolean hint;
    boolean edit;
    private Color hintBackgraudColor;
    private Color hintFontColor;
    private Color defaultBackGraudColor;
    private Color defaultFontColor;
    private String lastText;
    private InputType inputType = InputType.UNKNOW;
    private final DUMoney moneyValue = new DUMoney();
    private JFileChooser choseFile;
    private String messageError;
    private Color coloError;
    private boolean nullable;
    private int maxLength;
    private int minLength;
    
    
    public  EditText ()
    {
        super();
        this.init();
    }

    public EditText(String textHint, String text) {
        super(text);
        this.textHint = textHint;
        this.init();
    }

    public EditText(String textHint, Color hintBackgraudColor, Color hintFontColor, String text) {
        super(text);
        this.textHint = textHint;
        this.hintBackgraudColor = hintBackgraudColor;
        this.hintFontColor = hintFontColor;
        this.init();
    }
    
    /**
     * Au iniciar a caixa
     */
    private void init()
    {
        this.hint = false;
        this.maxLength = -1;
        this.minLength = -1;
        this.nullable = true;
        this.coloError = Color.RED.brighter();
        this.messageError = "Invalid data!";
        this.defaultBackGraudColor = super.getBackground();
        this.defaultFontColor = super.getForeground();
        
        this.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) 
            {
                
                
     apliHint(evt);
                validateType(evt);
                EditText.this.oldText = EditText.super.getText();
            }
        });
        this.addMouseListener(new MouseInputAdapter()
        {

            @Override
            public void mouseClicked(MouseEvent e)
            {
                Clicked(e);
            }
            
        });
        
        this.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                EditText.this.focusLost(evt);
            }

            @Override
            public void focusGained(FocusEvent e)
            {
                EditText.this.focusGained(e);
            }
        });
    }
    
    private boolean validateType(KeyEvent evt)
    {
        // cod 16 = shift esquerdo
        boolean valid = false;
        if(this.getText().length() == 0) 
        {
            this.lastText = "";
            return true;
        } 
        Number number = DUStringNumber.castNumber(this.getText());
        String text = this.getText();
        if (!isHint())
        {
            switch(inputType)
            {
                case UNKNOW:  
                    valid = true;
                    break;
                case MONEY:
                    //number = StringNumber.castNumber(this.getText());
                    if(DUValidate.isNumber(text))
                    {
                        if(!"-".equals(text) && !".".equals(text))
                            this.moneyValue.setRealValue(DUStringNumber.castDouble(text));
                        valid = true;
                    }
                    break;
                case NUMBER:
                    valid = DUValidate.isNumber(text);
                    break;
                case NUMBER_INTEGRE:
                    number = DUStringNumber.castInteger(this.getText());
                    if(number != null) valid = true;
                    else if (text.equals("-")) valid = true;
                    break;
                case NUMBER_NEGATIVE:
                    number = DUStringNumber.castDouble(this.getText());
                    if(number != null && number.doubleValue()<=0) valid = true;
                    else if (text.equals("-")) valid = true;
                    break;
                case NUMBER_POSITIVE:
                    number = DUStringNumber.castDouble(this.getText());
                    if(number != null && number.doubleValue()>=0) valid = true;
                    break;
                case TEXT:
                    valid = DUValidate.isText(this.getText());
                    break;
                case NUMBER_NEGATIVE_INTEGER:
                    number = DUStringNumber.castInteger(super.getText());
                    if(number != null && number.intValue()<=0) valid = true;
                    else if (text.equals("-")) valid = true;
                    break;
                case NUMBER_REAL:
                    String txt = super.getText().replace(",", ".");
                    number = DUStringNumber.castFloat(txt);
                    if(number != null) valid = true;
                    else if (text.equals("-")) valid = true;
                    break;
                case NUMBER_POSITIVE_INTEGER:
                    number = DUStringNumber.castInteger(super.getText());
                    if(number != null && number.intValue()>=0)
                        valid = true;
                    break;
            }
            if (valid) this.lastText = this.getText();
            
            else if(this.getText().length()> 0 
                    && evt.getKeyCode() != KeyEvent.ALT_MASK)
            {
                if(evt.getKeyCode() != 16 )
                {
                    super.setText(this.lastText);
                }
            }
            else this.setText("");
            return valid;
        }else return true;
        
    }
    
    public void setTextHint (String textHint)
    {
        this.textHint = textHint;
        super.setToolTipText(textHint);
        this.apliHint(null);
    }
    
    @Override
    public void setToolTipText(String text)
    {
        if(text != null && text.length()>0) super.setToolTipText(text);
        else if(this.textHint != null) super.setToolTipText(this.textHint);
    }
    
    public String getTextHint()
    {
        return this.textHint;
    }

    public boolean isHint() {
        return hint;
    }
    
    

    public InputType getInputType() {
        return inputType;
    }

    public void setInputType(InputType inputType) {
        if(inputType == null) inputType = InputType.UNKNOW;
        this.inputType = inputType;
        
        switch(this.inputType)
        {
            case FILE: //Quando a entrada for do tipo ficheiro carregar o ficheiro ao clicar na caixa
                this.setEditable(false);
                this.choseFile = new JFileChooser();
                break;
            case MONEY:
                this.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
                break;
            case NUMBER: case  NUMBER_INTEGRE: case NUMBER_NEGATIVE_INTEGER: case NUMBER_POSITIVE: case NUMBER_POSITIVE_INTEGER: case NUMBER_REAL:
                this.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
            default:
                this.setHorizontalAlignment(javax.swing.JTextField.LEFT);
                this.choseFile = null;
                break;
        }
    }

    public DUMoney getMoneyValue() {
        return moneyValue;
    }
    

    public Color getHintBackgraudColor() {
        return hintBackgraudColor;
    }

    public Color getHintFontColor() {
        return hintFontColor;
    }

    public Color getDefaultBackGraudColor() {
        return defaultBackGraudColor;
    }

    public Color getDefaultFontColor() {
        return defaultFontColor;
    }

    public void setHintBackgraudColor(Color hintBackgraudColor) {
        this.hintBackgraudColor = hintBackgraudColor;
        this.apliHint(null);
    }

    public void setHintFontColor(Color hintFontColor) {
        this.hintFontColor = hintFontColor;
        this.apliHint(null);
    }
    
    
    
    /**
     * Aplicar a validação da texto hint
     * Validar se pode ou nao colocar o texto hint
     * @param evt 
     */
    private void apliHint(java.awt.event.KeyEvent evt)
    {
       
        if(evt != null)
        {
            if(this.isHint())
            {
                
                if(evt.getKeyCode() == KeyEvent.ALT_MASK)
                {
                    super.setText("");
                    this.toDefault();
                }
                else if(this.textHint.length()< super.getText().length())
                {
                    
                    int startSel = this.textHint.length();
                    super.setText(super.getText().substring(startSel, super.getText().length()));
                    this.toDefault();
                }
                return;
            }
        }
        
         
        else if(!this.isFocusOwner())
        {
           
            if(this.getDocument() == null
                    || this.getText() == null 
                    || this.getText().length() ==0)
                this.toHint();
            return;
        }
        if (!this.isHint()
                && this.textHint != null
                && (super.getDocument() == null
                    || super.getText() == null 
                    || super.getText().length()==0) 
                )
        {
            this.toHint();
        }
        else if (this.isHint())
        {
            this.hint = false;
            super.setText("");
            //if(evt.getKeyCode() )
//            if(super.getText() != null
//                    && super.getText().length()>this.textHint.length())
//                super.setText(evt.getKeyChar()+"");
            this.toDefault();
        }
    }

    @Override
    public void setForeground(Color fg)
    {
        super.setForeground(fg); //To change body of generated methods, choose Tools | Templates.
        this.defaultFontColor = fg;
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg); //To change body of generated methods, choose Tools | Templates.
        this.defaultBackGraudColor = bg;
    }

    @Override
    public void setText(String t)
    {
        super.setText(t);
        DUMoney aux = DUMoney.createByParrent(this.getText(), this.moneyValue, false);
        if(this.inputType == InputType.MONEY 
                &&  aux != null)
        {
            this.moneyValue.setRealValue(aux.getRealValue());
            
            //Se nao tiver focus então colocar o tesr em formato moeda 
            if(!isFocusOwner()) super.setText(this.moneyValue.format());
            else super.setText(this.moneyValue.getRealValue()+"");
        }
        if(t == null || t.length() == 0) this.apliHint(null);
        else toDefault();
    }
    
    public void setRealText(String txt)
    {
        if(this.inputType == InputType.MONEY
            && DUValidate.isNumber(txt))
        {
            Number n = DUStringNumber.castDouble(txt);
            this.moneyValue.setRealValue(n.doubleValue());
            super.setText(this.moneyValue.format());
            this.toDefault();
        }
    }
    

    @Override
    public String getText()
    {
        if(this.isHint()) return "";
        return super.getText(); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Re-Aplicar o estilo default da caixa
     */
    private void toDefault()
    {
        super.setForeground(this.defaultFontColor);
        super.setBackground(this.defaultBackGraudColor);
        this.hint = false;
    }
    
    /**
     * Color o texto hint na caixa
     */
    private void toHint() 
    {
        this.hint = true;
        super.setForeground((hintFontColor == null)? Color.GRAY: this.hintFontColor);
        super.setBackground((hintBackgraudColor == null)? Color.WHITE: this.hintBackgraudColor);
        if(this.getDocument() != null)super.setText(this.textHint);
    }
    
    /**
     * Evento para click do mouse
     * @param e 
     */
    private void Clicked(MouseEvent e) 
    {
        switch(this.inputType)
        {
            case FILE:
                if(!this.choseFile.isShowing())
                {
                    this.choseFile = new JFileChooser();
                    this.choseFile.addActionListener((act) ->
                    {
                        File f = this.choseFile.getSelectedFile();
                        this.choseFile.setVisible(false);
                        if(f != null && act.getActionCommand().equals(JFileChooser.APPROVE_SELECTION))
                            this.setText(choseFile.getSelectedFile().getAbsolutePath());

                    });
                    if (this.getText().length() != 0)
                    {
                        File f = new File(this.getText());
                        this.choseFile.setSelectedFile(f);
                    }
                    this.choseFile.showOpenDialog(this);
                }
            break;
        }
    }

    private void focusGained(FocusEvent e)
    {
        this.edit = false;
        if(inputType == InputType.MONEY)
        {
            String modela = moneyValue.format().replace(moneyValue.getSplitChar()+"", "");
            modela = modela.replace(moneyValue.getSepInteiroDecimal()+"", ".");
            if(this.getText().length() >0)
                super.setText(modela);
            if(getText().length()>=4)
            {
                this.setSelectionStart(getText().length()-3);
                this.setSelectionEnd(getText().length()-3);
            }
        }
    }
    
    /**
     * Quando perder o fou
     * @param evt 
     */
    private void focusLost(FocusEvent evt) 
    {
        apliHint(null);
        switch(this.inputType)
        {
            case MONEY: // se o tipo for moeda entao colocar um texto em formato moeda na caixa
                if(getText().length() != 0)
                    setText(moneyValue.format());
                break;
        }
    }
    
    public void clear()
    {
        Double value = null;
        this.setText("");
        if(this.inputType ==  InputType.MONEY && this.moneyValue != null)
            this.moneyValue.setRealValue(value);
    }
    
    public void clearBox()
    {
        super.setText("");
    }
    
    public Object getValue()
    {
        Object value;
        switch(this.inputType)
        {
            case NUMBER_REAL:
                String txt = super.getText();
                txt=txt.replace(",", ".");
                value = Float.valueOf(txt);
                break;
            case MONEY:
                value = moneyValue.getRealValue();
                break;
            case NUMBER: case NUMBER_NEGATIVE: case NUMBER_POSITIVE:
                value = Double.valueOf(this.getText());
                break;
            case NUMBER_INTEGRE: case NUMBER_NEGATIVE_INTEGER: case NUMBER_POSITIVE_INTEGER:
                value = Integer.valueOf(this.getText());
                break;
            default: value = super.getText();
                break;
        }
        return value;
    }
    

    public boolean isEmpty() 
    {
        String txt = this.getText();
        return (txt == null || txt.isEmpty());
    }

    @Override
    public String toString() {
        return super.toString()+" | DUTextField{" + "textHint=" + textHint + ", oldText=" + oldText + ", hint=" + hint + ", edit=" + edit + ", hintBackgraudColor=" + hintBackgraudColor + ", hintFontColor=" + hintFontColor + ", defaultBackGraudColor=" + defaultBackGraudColor + ", defaultFontColor=" + defaultFontColor + ", lastText=" + lastText + ", inputType=" + inputType + ", moneyValue=" + moneyValue + ", choseFile=" + choseFile + '}';
    }

    public String getLastText() {
        return lastText;
    }

    public void setLastText(String lastText) {
        this.lastText = lastText;
    }

    public String getMessageError() {
        return messageError;
    }

    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }

    public Color getColoError() {
        return coloError;
    }

    public void setColoError(Color coloError) {
        this.coloError = coloError;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    @Override
    public boolean isValidData() 
    {
        return true;
    }
    
    
    /**
     * Enumeraçao dos tipos de entradas dos cararcteres
     */
    public enum InputType
    {
        /**
         * nao sera feita quaisquer validacao aceita dodos os carateres
         *//**
         * nao sera feita quaisquer validacao aceita dodos os carateres
         */
        UNKNOW,
        
        /**
         * So aceita carateres de texto (Letras)
         */
        TEXT,
        
        /**
         * So aceita numeroas positivos ou negativos, inteiros ou decimal
         */
        NUMBER,
        
        
        /**
         * So aceita a entrada de data
         */
        DATE,
        
        /**
         * So aceita a entrada da hora
         */
        TIME,
        
        /**
         * Faz busca do feicheiro ao clicar na caixa
         */
        FILE,
        
        /**
         * So aceita numeros positivos
         */
        NUMBER_POSITIVE,
        
        /**
         * So aceita numeros negativos
         */
        NUMBER_NEGATIVE,
        
        /**
         * So aceita numeros inteiros
         */
        NUMBER_INTEGRE,
        
        /**
         * So aceita numeros positivos inteiros
         */
        NUMBER_POSITIVE_INTEGER,
        
        /**
         * So aceita numeros positivos negativos
         */
        NUMBER_NEGATIVE_INTEGER,
        
        /**
         * Numeros com mascaras de moedas
         */
        MONEY,
        
        TEXT_NUMBER,
        
        NUMBER_REAL,
    }
}
