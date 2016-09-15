/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util;

/**
 *
 * @author projecto1
 */
public class DUMoney implements Comparable<DUMoney>
{
    private static DUMoney money;

    public static DUMoney getReferenceMoney()
    {
        return money;
    }
    
    public static void setReferenceMoney(DUMoney money)
    {
        DUMoney.money = money;
    }
    private Double realValue;
    private int decimalCase;
    private int numPorCasas;
    private char sepInteiroDecimal;
    private char sepCasasCasas;
    
    /**
     * Inicializar a moeda com as caracteristicas default
     * O separador entre as casas inteira é: ponto(.)
     * O separador entre a parate interira e decimal é: virgula(,)
     * A quantidade de casa decimal é: 2 (duas casas decimais)
     * A quantidade de numeros inteiros por casa é 3: (trez casas decimais)
     * Exemplo  1000000 = 1.000.000,00
     */
    public DUMoney()
    {
        this.sepInteiroDecimal = ',';
        this.sepCasasCasas = '.';
        this.numPorCasas = 3;
        this.decimalCase = 2;
        this.realValue = 0.0;
        
        
    }
    
    
    /**
     * Inicializar a moeda com as caracteristicas default
     * O separador entre as casas inteira é: ponto(.)
     * O separador entre a parate interira e decimal é: virgula(,)
     * A quantidade de casa decimal é: 2 (duas casas decimais)
     * A quantidade de numeros inteiros por casa é 3: (trez casas decimais)
     * Exemplo  realValue é 1000000 = 1.000.000,00
     * @param realValue
     */
    public DUMoney(double realValue)
    {
        this();
        this.realValue = realValue;
    }

    /**
     * 
     * @param realValue O valor original em double
     * @param decimalCase O total de casas decimais que deveraao aparecer
     * @param numPorCasas A quantidade de caractere em  dasa casa inteiros
     * @param sepInteiroDecimal O caracter que ira separar a casa decimal
     * @param sepCasasCasas O caracter que ira separar cada casa
     */
    public DUMoney(double realValue, int decimalCase, int numPorCasas, char sepInteiroDecimal, char sepCasasCasas)
    {
        this.realValue = realValue;
        this.decimalCase = decimalCase;
        this.numPorCasas = numPorCasas;
        this.sepInteiroDecimal = sepInteiroDecimal;
        this.sepCasasCasas = sepCasasCasas;
    }
    
    
    
    /**
     * 
     * @param formatValues O valor formatado
     * @param decimalCase O total de casas decimais que deveraao aparecer
     * @param numPorCasas A quantidade de caractere em  dasa casa inteiros
     * @param sepInteiroDecimal O caracter que ira separar a casa decimal
     * @param sepCasasCasas O caracter que ira separar cada casa
     */
    public DUMoney(String formatValues, int decimalCase, int numPorCasas, char sepInteiroDecimal, char sepCasasCasas) throws NumberFormatException
    {
        this.decimalCase = decimalCase;
        this.numPorCasas = numPorCasas;
        this.sepInteiroDecimal = sepInteiroDecimal;
        this.sepCasasCasas = sepCasasCasas;
        formatValues = formatValues.replace(sepCasasCasas+"", "");
        formatValues = formatValues.replace(sepInteiroDecimal+"", ".");
        this.realValue = Double.parseDouble(formatValues);
    }
    
    /**
     * Criar uma moeda a partir de um valor formatado por default
     * @param formatValues -- O valor formatado
     */
    public DUMoney (String formatValues)
    {
        this();
        try
        {
            formatValues = formatValues.replace(sepCasasCasas+"", "");
            formatValues = formatValues.replace(sepInteiroDecimal, '.');
            this.realValue = Double.parseDouble(formatValues);
        }catch(Exception ex)
        {
            throw  new  RuntimeException(ex);
        }
    }
    
    
    
    public Double getRealValue()
    {
        return realValue;
    }

    public void setRealValue(Double realValue) {
        this.realValue = realValue;
    }

    public int getDecimalCase() {
        return decimalCase;
    }

    public void setDecimalCase(int decimalCase) {
        this.decimalCase = decimalCase;
    }

    public int getNumPorCasas() {
        return numPorCasas;
    }

    public void setNumPorCasas(int numPorCasas) {
        this.numPorCasas = numPorCasas;
    }

    public char getSepInteiroDecimal() {
        return sepInteiroDecimal;
    }

    public void setSepInteiroDecimal(char sepInteiroDecimal) {
        this.sepInteiroDecimal = sepInteiroDecimal;
    }

    public char getSplitChar() {
        return sepCasasCasas;
    }

    public void setSplitChar(char splitChar) {
        this.sepCasasCasas = splitChar;
    }
    
    
    @Override
    public String toString()
    {
        return this.format();
    }
    
    /**
     * Funcoa para cria a formatacao a parit do valor actual
     * @return 
     */
    public String format()
    {
        if(this.realValue == null) return "";
        String format = "";
        
        //Forcar o valor a ser positivo
        double valueUsar = (this.realValue <0)?this.realValue*-1: this.realValue;
        
        //Separas as partes inteiras das partes
        long intPart = (long) valueUsar;
        double doubepart = valueUsar - intPart;
        String parte = (intPart+"");
        int count =1;
        
        //Formatar a parte inteira
        for (int i =parte.length()-1; i>=0; i--)
        {
            format = parte.charAt(i)+format;
            if (count == this.numPorCasas && i>0)
            {
                count = 1;
                format = this.sepCasasCasas+format;
            }
            else count++;
        }
        if (this.decimalCase>0)
            format=format+this.sepInteiroDecimal;
        
        parte = doubepart+"";
        //Foramatar a parte decimal
        for (int i =0; i<this.decimalCase; i++)
        {
            if (i+2< parte.length())
                format = format + parte.charAt(i+(2));
            else format = format +'0';
        }
        
        //Se o valor original for negativo entao adicionar o -
        return (this.realValue<0? "-":"")+format;
    }
    
    /**
     * Utilizar a formatacao defaulte para o valor do parametro
     * @param value O valor que sera formatado
     * @return O valor formatado
     */
    public static String format (Double value)
    {
        return DUMoney.format(value, '.', ',');
    }
    
    
    /**
     * Criara a foarmatacao a parir do valor fornecido definido o numero o que ira 
     * separa as casas entre os numeros inteiros e o que ira separar as casas inteirars das casas decimais
     * @param value O valor que sera formatdo
     * @param separadorCasas O separadores entre as casas
     * @param separadorInteiroDecimal O separador entre a casa inteira e a casa decimal
     * @return O valor formatado nas condecoes fornecidas
     */
    public static String format (double value, char separadorCasas, char separadorInteiroDecimal)
    {
        return DUMoney.format(value, 3, 2, separadorCasas, separadorInteiroDecimal);
    }
    
    /**
     * Criar a formatacao usando o valor dado definindo:
     * A quantidade dos numeros por casa
     * A quantidade de casas decimais
     * O caracter que separa cada casa
     * O caracetr que separa a parte inteira da parte decimal
     * @param value O valor a ser formatado
     * @param numerosPorCasas A quantidade de numeros em cada casa
     * @param casasDecimais O umeros de casas decimais que sera apresentada na formatacao
     * @param separadorCasas O caracter que ira separas as casas inteiras
     * @param separadorInteiroDecimal O caracter que ira separar as casas inteirar da casa decimal
     * @return 
     */
    public static String format (Double value, int numerosPorCasas, int casasDecimais, char separadorCasas, char separadorInteiroDecimal)
    {
        DUMoney m = new DUMoney(value, casasDecimais, numerosPorCasas, separadorInteiroDecimal, separadorCasas);
        return m.format();
    }
    
    
    /**
     * Formatar uma String a partir do formato de uma outra
     * de uma moeda
     * @param font A String que sera formatada
     * @param parent A moeda que se tera como base
     * @param validateParentFormat
     * @return Uma nova moeda com a mesma formatacao
     */
    public static DUMoney createByParrent(String font, DUMoney parent, boolean validateParentFormat)
    {
        try
        {
            font = font.replace(' ', ' ');
            if (parent != null)
            {
                if(validateParentFormat
                        && font.contains(parent.sepInteiroDecimal+""))
                {
                    
                    String campos [] = font.split(parent.sepInteiroDecimal+"");
                    if(campos.length == 2
                            && campos[1].length() == parent.decimalCase
                            && campos[0].length()>0)
                    {
                        campos = campos[0].split(parent.sepCasasCasas+"");
                        for (String s: campos)
                        {
                            if(s.length() > parent.numPorCasas
                                    || DUStringNumber.castInteger(s) == null)
                            {
                                return null;
                            }
                        }
                    }
                }
                DUMoney children = new DUMoney(font, parent.decimalCase, parent.numPorCasas, parent.sepInteiroDecimal, parent.sepCasasCasas);
                return children;
                
            }
        }catch(Exception ex) {}
        return null;
    }

    @Override
    public int compareTo(DUMoney otherMoney)
    {
        // 1 este e maoior
        //-1 este e menor
        //0 este e igual
        
        Double otherValue = Double.parseDouble(this.getStringDouble());
        Double thisValue = Double.parseDouble(otherMoney.getStringDouble());
        return thisValue.compareTo(otherValue);
    }
    
    public String getStringDouble()
    {
        String thisMoneyString = this.toString();
        thisMoneyString = thisMoneyString.replace(this.sepCasasCasas+"", "").replace(this.sepInteiroDecimal+"", ".");
        return thisMoneyString;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.realValue) ^ (Double.doubleToLongBits(this.realValue) >>> 32));
        hash = 79 * hash + this.decimalCase;
        hash = 79 * hash + this.numPorCasas;
        hash = 79 * hash + this.sepInteiroDecimal;
        hash = 79 * hash + this.sepCasasCasas;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DUMoney other = (DUMoney) obj;
        if (Double.doubleToLongBits(this.realValue) != Double.doubleToLongBits(other.realValue)) {
            return false;
        }
        if (this.decimalCase != other.decimalCase) {
            return false;
        }
        if (this.numPorCasas != other.numPorCasas) {
            return false;
        }
        if (this.sepInteiroDecimal != other.sepInteiroDecimal) {
            return false;
        }
        if (this.sepCasasCasas != other.sepCasasCasas) {
            return false;
        }
        return true;
    }

    public void setRealValue(Float realValue) 
    {
        if(realValue != null)this.setRealValue(realValue.doubleValue());
        else this.setRealValue((Double) null);
    }
    
    
}
