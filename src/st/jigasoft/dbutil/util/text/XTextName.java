/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package st.jigasoft.dbutil.util.text;



import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * Essa classe permite recriar um ficeiro xml baseando da estrutura de uma classe em java
 * desde que essa implemente a interface ClassXML
 * @author Servidor
 */
public class XTextName
{
    /**
     * Essa função mapea-se a classe java a um ficheiro xml
     * @param xNameClass Coresponde a classe em java que sera mapeada - Essa classe deve Implementar a interface ClassXML
     */
    public void apllyText(Class <? extends XFieldName> xNameClass, ApplyMode apllyMode, ApplyRange range) throws TextException
    {
        try
        {
            ArrayList<Class> listClass = new ArrayList<>();
            //Assim como das subClasse detro das mesmas classes

            switch (range)
            {
                case CLASS_ONLY: listClass.add(xNameClass); break;
                case CLASS_SUBCLASS:
                    listClass.add(xNameClass);
                    for(Class cc: xNameClass.getDeclaredClasses())
                        listClass.add(cc);
                    break;
                case CLASS_AND_SUPERCLASS: break;
                case CLASS_AND_SUPERCLASS_SUBCLASS: break;
                case CLASS_SUBCLASS_AND_SUPERCLASS_SUBCLASS: break;
                case  CLASS_SUBCLASS_AND_SUPERCLASS: break;
            }


            
            // A primeira classe que aparece é aprimeira que deve aparecer no ficheiro
            //O Primeiro atributo que aparece deve ser o primeiro no XML

            String text;
            for(int i =0  ; i< listClass.size(); i++)
            {
                //Adicionar a classe na estrutura do ficheiro
                Class fieldClass = listClass.get(i);

                for(Field field: fieldClass.getDeclaredFields())
                {

                    Class<?> type = field.getType();

                    if(type.equals(String.class))
                    {
                        switch (apllyMode)
                        {
                            case ORIGINAL: text = field.getName(); break;
                            case TREAT:
                            case TREAT_INIT_UPPER:
                            case TREAT_FIRST_INIT_UPPER:
                            case TREAT_LOWER:
                            case TREAT_UPPER:
                                text = treatText(field.getName(), apllyMode);
                            break;
                            default: text = field.getName(); break;
                        }
                        setFieldValue(field, fieldClass, text);
                    }
                }
            }

        } catch (Exception ex)
        {
            throw new TextException(ex.getMessage(), ex);
        }
    }

    private static void setFieldValue(Field field, Class aClass, String value)
    {
        try
        {
            field.setAccessible(true);
            field.set(aClass, value);
        } catch (Exception ex)
        {
            String message  = "XTextName-> rename{class:\""+aClass.getName()+"\", field:\""+field.getName()+"\", value:\""+value+"\"} FAILED ";
            throw new TextException(ex.getMessage() +"\n"+message, ex);
        }
    }

    /**
     * Essa funcão serve para tratar os nomes das variaveis de modo que a mesma seja
     * mais legivel de ler
     * Isso so sera invocado para as
     * @param modevariaveis que nao tiver um valor definido no ficheiro XML
     * @param mode
     * @param argment O nome da variavel a que se seve tratar
     * @return 
     */
    public String treatText(String argment, ApplyMode mode)
    {
        final int NEXT_POSITION = 1;
        
        String treatedText = "";
        String[] campos = argment.split("_");
        char oldChar;
        char currentChar;
        int start;
        boolean hasPreview;
        boolean first = true;
        
        for(String textPart: campos)
        {
            if(textPart.length() >0)
            {
                oldChar = textPart.charAt(0);
                start =  0;
                //Verificara as questoes de maiusculas e minusculas
                for(int i = 0; i<textPart.length(); i++)
                {
                    currentChar = textPart.charAt(i);
                    hasPreview = ((i+NEXT_POSITION) < textPart.length());
                    
                    //Quando o antigo for minuscula e o novo for maiuscula
                    if(oldChar >= 'a' && oldChar <= 'z' 
                            && (currentChar >= 'A' && currentChar <= 'Z'))
                    {
                        String textSubPart = textPart.substring(start, i);
                        if(!first && !isCompletUpper(textSubPart))
                            textSubPart = textSubPart.toLowerCase();
                        treatedText = treatedText + " " + textSubPart;
                        start = i;
                        first = false;
                    }
                    
                    //Quando terminado a parte
                    //OlaChamomeDANIEL
                    else if(!hasPreview)
                    {
                        String textSubPart = textPart.substring(start, i+NEXT_POSITION);
                        if(!first && !isCompletUpper(textSubPart))
                            textSubPart = textSubPart.toLowerCase();
                        treatedText = treatedText + " " +textSubPart;
                        start = i;
                        first = false;
                    }
                    oldChar = currentChar;
                }
            }
            first = false;
        }
        treatedText = treatTextModo(treatedText, mode);
        return treatedText.trim();
    }

    public String treatTextModo(String text, ApplyMode mode)
    {
        text = text.trim();
        switch (mode)
        {
            case TREAT_LOWER: return text.toLowerCase();
            case TREAT_UPPER: return text.toUpperCase();
            case TREAT_FIRST_INIT_UPPER: return initFristUpper(text);
            case TREAT_INIT_UPPER: return initTextUpper(text);
            default: return text;
        }
    }

    private String initTextUpper(String newText) {
        String [] strings = newText.split(" ");
        newText = "";
        int iCount =0;
        for(String part: strings)
        {
            if(part.length()>=1)
                part = initFristUpper(part);
            newText = newText + part+ ((++iCount< strings.length)? " ":"");
        }
        return newText;
    }

    private String initFristUpper(String text)
    {
        text.trim();
        text = text.toUpperCase();
        if(text.length()>=1)
            text = text.substring(0, 1) + (text.substring(1, text.length()).toLowerCase());
        return text;
    }
    
    public boolean isCompletUpper(String argment)
    {
        for(char c: argment.toCharArray())
            if(c >= 'a' && c<='z') return false;
        return true;
    }
    
    public boolean isCompletLower (String argment)
    {
        for(char c: argment.toCharArray())
            if(c >= 'a' && c<='z') return false;
        return true;
    }


    public enum ApplyMode
    {
        ORIGINAL,
        ORIGINAL_UPPER,
        ORIGINAL_LOWER,
        ORIGINAL_INIT_UPPER,
        ORIGINAL_INIT_LOWER,
        TREAT,
        TREAT_UPPER,
        TREAT_LOWER,
        TREAT_INIT_UPPER,
        TREAT_FIRST_INIT_UPPER,
    }


    public enum ApplyRange
    {
        CLASS_ONLY,
        CLASS_SUBCLASS,
        CLASS_SUBCLASS_AND_SUPERCLASS,
        CLASS_SUBCLASS_AND_SUPERCLASS_SUBCLASS,
        CLASS_AND_SUPERCLASS,
        CLASS_AND_SUPERCLASS_SUBCLASS
    }

}
