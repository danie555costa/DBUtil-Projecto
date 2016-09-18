package st.jigasoft.dbutil.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author ildo
 */
public class DUDateTime
{
    
    /**
     * Converter uma data de java.util.Date em uma data de java.sql.Date
     * @param utilDate A entrada de data java
     * @return  Saida de data SQL
     */
    public static java.sql.Date toSQLDate (java.util.Date utilDate)
    {
        return ((utilDate==null) ?  null : new  java.sql.Date(utilDate.getTime()));
    }
    
    
    /**
     * Converter a java.util.Date em DD-MM-YYYY
     * @param dataCast objecto java.util.Date
     * @return 
     */
    public static String toStringDDMMYYY(Date dataCast)
    {
        return new SimpleDateFormat("dd-MM-yyyy").format(dataCast);
    }
    
    /**
     * Converter a java.util.Date em YYYY-MM-DD
     * @param dataCast objecto java.util.Date
     * @return 
     */
    public static String toStringYYYYMMDD (Date dataCast)
    {
        return new SimpleDateFormat("yyyy-MM-dd").format(dataCast);
    }
    
    
    
    /**
     * Essa funcao compara a data actual com a data fornecida
     * @param otherDate A data que deve ser usada para efectura a compracao
     * @return 
     * 0 - Siginifica que a data fornecida e igula a data actual
     * 1 - Siginifica que a data fornecida e mairo que a data atual
     * -1 Siginifica que a data fornecida e menor que a data atual
     */
    public static int compareToSysDate (Date otherDate)
    {
        Date sysDate = new Date();
        return DUDateTime.compareDates(sysDate, otherDate);
    }
    
    
    /**
     * Essa funcao serva para compara duas datas e dezer se em relacao a dada base
     * A outra data e maior menor ou igual
     * @param baseDate A data que sera tomada como a data base
     * @param otherDate A data que sera comparada em relacao a ela
     * @return 
     *      0 - Singnifica que as duas datas sao iguais
     *      <br/> 1 - Siginifica que em relacao a data base a outra data e maior
     *      <br/> -1 - Significa que em ralacao a data base a outra data e menor
     */
    public static int compareDates (Date baseDate, Date otherDate)
    {
        return otherDate.compareTo(baseDate);
    }
    
    /**
     * Validar a data de nascimento
     * @param dataNascimento
     * @return 
     * true data de nascimento valido
     * flase data de nascimento invaldo
     */
    public static boolean isValidDateNascimento (Date dataNascimento)
    {
        return (compareToSysDate(dataNascimento) <= 0);
    }
    
    
    /**
     * Essa funcoa converte uma String date em uma data do java.util.Date
     * @param dateYYYYMMDD
     * @return 
     */
    public static java.util.Date stringToJavaDate (String dateYYYYMMDD)
    {
        return stringFormatToDate(dateYYYYMMDD, "yyyy-MM-dd");
    }
    
    public static java.util.Date  stringFormatToDate(String dataValues, String formatDate)
    {
        try
        {
            DateFormat formatter = new SimpleDateFormat(formatDate);
            Date date = (Date) formatter.parse(dataValues);
            return  date;
        } catch (ParseException ex) {
            System.err.println("Falha na conversao de string para dade "+ex.getMessage());
            ex.printStackTrace();
            return null;
        }     
    }
    
    /**
     * Verificar se uma dada string tem o forado da data
     * @param format o valor do fomato
     * @param preferedFormat Coresponde ao formato desejado que se quer obter
     * @return 
     */
    public static DataFormat isDateFormat(String format, Formats preferedFormat)
    {
        ArrayList<Formats> list = Formats.findByValue(format);
        
        if(list.contains(preferedFormat)) return new DataFormat(format, preferedFormat);
        else if(list.size() > 0 ) return  new DataFormat(format, list.get(0));
        return null;
    }
    
    
    
    /**
     * Classe para obter o formato de data e o seu valor
     */
    public static class DataFormat
    {
        private String dataValue;
        private Formats dataFormat;

        public DataFormat(String dataValue, Formats dataFormat) {
            this.dataValue = dataValue;
            this.dataFormat = dataFormat;
        }

        public String getDataValue() {
            return dataValue;
        }

        public Formats getDataFormat() {
            return dataFormat;
        }

        @Override
        public String toString() 
        {
            return this.dataFormat.format;
        }
        
        
    }
    
    public static enum Formats
    {
        // AS DATAS COMPOSTAS APENAS POR NUMEROS
           //DIA MES ANO
        DDMMYYYY_TRASO ("DD-MM-YYYY", 1, "-", 31, 12, 9999),
        DDMMYYYY_BARA("DD/MM/YYYY", 1, "/", 31, 12, 9999),
        DDMMYYYY_SPACE("DD MM YYYY", 1, " ", 31, 12, 9999),
        
           //ANO MES DIA
        YYYYMMDD_TRASO("YYYY-MM-DD", 1, "-", 9999, 12, 31),
        YYYYMMDD_BARA("YYYY/MM/DD", 1, "/", 9999, 12, 31),
        YYYYMMDD_SPACE("YYYY MM DD", 1, " ", 9999, 12, 31),
        
        
           //ANO MES
        YYYYMM_TRASO("YYYY-MM", 1, "-", 9999, 12),
        YYYYMM_BARA("YYYY/MM", 1, "/", 9999, 12),
        YYYYMM_SPACE("YYYY MM", 1, " ", 9999, 12),
        
          //MES ANO
        MMYYYY_TRACO("MM-YYYY", 1, "-", 12, 9999),
        MMYYYY_BARA("MM/YYYY", 1, "/", 12, 9999),
        MMYYYY_SPACE("MM YYYY", 1, " ", 12, 9999),
        
        
        
        //AS DATAS QUE CONTEM O NOME DOS MESES
           //DIA MES_NOME ANO
        DDMONYYYY_TRASO("DD-MON-YYYY", 2, "-", 31, "MON", 9999),
        DDMONYYYY_BARA("DD/MON/YYYY", 2, "/", 31, "MON", 9999),
        DDMONYYYY_SPACE("DD MON YYYY", 2, " ", 31, "MON", 9999),
        
           //ANO MES_NOME DIA
        YYYYMONDD_TRASO("YYYY-MON-DD", 2, "-", 9999, "MON", 31),
        YYYYMONDD_BARA("YYYY/MON/DD", 2, " ", 9999, "MON", 31),
        YYYYMONDD_SPACE("YYYY MON DD", 2, "-", 9999, "MON", 31),
        
           
           //ANO MES_NOME
        YYYYMON_TRASO("YYYY-MON", 2, "-", 9999, "MON"),
        YYYYMON_BARA("YYYY/MON", 2, "/", 9999, "MON"),
        YYYYMON_SPACE("YYYY MON", 2, " ", 9999, "MON"),
        
           //MES_NOME ANO
        MONYYYY_TRASO("MON-YYYY", 2, "-", "MON", 9999),
        MONYYYY_BARA("MON/YYYY", 2, "/", "MON", 9999),
        MONYYYY_SPACE("MON YYYY", 2, " ", "MON", 9999)
        ;
        
        
        public static final String MM = "MM";
        public static final String DD = "DD";
        public static final String YYYY = "YYYY";
        public static final String MON = "MON";
        public static final String MONTH = "MONT";
        public static final String DAY = "DAY";
        public static final String YEAR = "YEAR";

        public static String cast(String valueDate, Formats formats)
        {
            for(Formats s: findByValue(valueDate))
                return s.castTo(valueDate, formats);
            return null;
        }
        
        
        
        /**
         * Proucurar toda a  enumerocao que tem o formato enviado
         * @param format
         * @return 
         */
        public static ArrayList<Formats> findByFormat(String format)
        {
            ArrayList<Formats> fomats = new ArrayList<>();
            if(format == null || format.length()<1) return null;
            for (Formats f: values())
                if(f.getFormat().toUpperCase().equals(format.toUpperCase()))
                    fomats.add(f);
            return fomats;
        }
        
        /**
         * Proucurar todos os formatos que possuem o valor 
         * @param value
         * @return 
         */
        public static ArrayList<Formats> findByValue (String value)
        {
            ArrayList<Formats> formats = new ArrayList<>();
            if(value == null || value.length()<1) return formats;
            for (Formats f: values())
            {
                if(f.toMap(value) != null)
                    formats.add(f);
            }
            return formats;
        }
        
        /**
         * Efetuar a conversa da data de um formato para o outro
         * @param value
         * @param fromFormat
         * @param toFormat
         * @return 
         */
        public static String cast(String value, Formats fromFormat, Formats toFormat)
        {
            if(value == null 
                    || fromFormat == null
                    || toFormat == null) return  null;
            HashMap<String, Object> map = fromFormat.toMap(value);
            if(map == null) return null;
            return toFormat.toStringDate(map);
        }

        
        
        private final String format;
        private final int type;
        private final String separator;
        private final String camposFormat[];
        private final Object [] lengthCasas;
        
        private Formats(String format, int type, String separator, Object ... lengthCasas)
        {
            this.format = format;
            this.type = type;
            this.separator = separator;
            this.camposFormat = format.split(separator);
            this.lengthCasas = lengthCasas;
        }

        public String getFormat()
        {
            return format;
        }
        
        /**
         * Mapiar uma data a pariti do formato fornecido
         * @param value
         * @return 
         */
        public HashMap<String, Object> toMap(String value)
        {
            HashMap<String, Object> mapData = null;
            
            Integer day=null, year=null;
            Month mon = null;
            //se o valor for nulo ou o tamanho do valor for diferente do tamanho do formato
                // ou o valor nao contiver nenhum separador doformato | INVALIDO
            if(value == null 
                    || value.length() != this.format.length()
                    || !value.contains(this.separator)) return null;
            String camposValues [] = value.split(this.separator);
            //Quando o tamnho da separacao do campo nao for au tamanho de separacao do formato | INVALIDO
            if(camposValues.length != this.camposFormat.length) return null;
            
            //Correr na data fornecida e aplicar as validacoes
            for(int i =0; i<this.camposFormat.length; i++)
            {
                
                
                //Quando for so numerica ou o campo for dia ou ano efetuara as validacoes inteira
                if(this.type == 1 
                        ||(type == 2
                               && (camposFormat[i].equals("DD") || camposFormat[i].equals("YYYY"))))
                {
                    Integer intTest = DUStringNumber.castInteger(camposValues[i]);
                    
                    if(intTest == null || intTest<1 || intTest > (int)this.lengthCasas[i]) return null; // Noa e numero ou fora do range | INCALIDO
                    if(camposValues[i].length() != camposFormat[i].length()) return null; //tamanho dos campos nao corespondem | INVALIDO
                    //Atribuir os valor em separados
                    if(camposFormat[i].equals("MM")) //Se for o formato do mes pegar o mes corespondente
                    {
                        mon = Month.findMonth(intTest);
                        if(mon == null) return null; //Mes noa exite retornar nulo
                    }
                    else if(camposFormat[i].equals("DD")) day = intTest; //se for dia pegar o dia
                    else if(camposFormat[i].equals("YYYY"))  year = intTest;  //se for mes nem dia pega o ano
                }
                //Quando os meses sao escritos em nome
                else if(this.type == 2 && camposFormat[i].equals("MON")) //for o campo mes em nome para tipo data nome
                {
                    try
                    {
                        mon = Month.valueOf(camposValues[i].toUpperCase());
                    }catch(Exception ex){return null;}
                }
            }
            
            if(mon == null || year == null) return  null;
            
            if(day != null && day > mon.getMaxDay()) return null;
            mapData = new HashMap<>();
            mapData.put(Formats.DAY, DUString.completIni(day+"", '0', 2));
            mapData.put(Formats.DD, DUString.completIni(day+"", '0', 2));
            mapData.put(Formats.MON, mon);
            mapData.put(Formats.MM, DUString.completIni(mon.getNumMonth()+"", '0', 2));
            mapData.put(Formats.MONTH, mon.getName());
            mapData.put(Formats.YEAR, DUString.completIni(year+"", '0', 2));
            mapData.put(Formats.YYYY, DUString.completIni(year+"", '0', 2));
            return mapData;
        }
        
        
        private String toStringDate(HashMap<String, Object> mapData) 
        {
            String formatValue = "";
            for(int i =0; i<this.camposFormat.length; i++)
            {
               
                Object o = mapData.get(camposFormat[i]);
                if(camposFormat[i].equals("DD"))
                    if(o == null || o.toString().toUpperCase().equals("NULL")) 
                        o = 1;
                else if(o == null) throw new RuntimeException ("Dada Invalida");
                formatValue= formatValue +o.toString();
                if((i) < this.camposFormat.length-1) formatValue = formatValue + this.separator;
            }
            return formatValue;
        }
        
        
        /**
         * Converter de qualquer formato para o formato actual
         * @param value
         * @return 
         */
        public String castFromAny (String value)
        {
            ArrayList<Formats> list = findByValue(value);
            if(!list.isEmpty())
            {
                Formats currentFormat = list.get(0);
                return Formats.cast(value, currentFormat, this);
            }
            return null;
        }
        
        /**
         * 
         * @param vaString
         * @param toFormat
         * @return 
         */
        public String castTo(String vaString, Formats toFormat)
        {
            return cast(vaString, this, toFormat);
        }
    }
    
    public static class Clock
    {
        private static  String get (String formart)
        {
            SimpleDateFormat sdf = new SimpleDateFormat(formart);
            Date hora = Calendar.getInstance().getTime(); // Ou qualquer outra forma que tem
            String dataFormatada = sdf.format(hora);
            return dataFormatada;
        }
        
        public static String getHHMMSS()
        {
            return get("HH:mm:ss");
        }
        
        public static String getYYYYMMDD()
        {
            return get("yyyy-MM-dd");
        }
        
        
    }
            
    
    
   
    
    
    public static enum Month
    {
        JAN(1, 31, "JANEIRO"),
        FEB(2, 29, "FEVERIRO"),
        MAR(3, 31, "MARCO"),
        APR(4, 30, "ABRIL"),
        MAY(5, 31, "MAIO"),
        JUN(6, 30, "JUNHO"),
        JUL(7, 31, "JULHO"),
        AUG(8, 31, "AGOSTO"),
        SEP(9, 30, "SETEMBRO"),
        OCT(10, 31, "OUTOBRO"),
        NOV(11, 30, "NOVEMBRO"),
        DEC(12, 31, "DEZEMBRO");
        private final int numMonth;
        private final int maxDay;
        private final Object useName;
        
        private Month(int numberMonth, int maxDay, String name)
        {
            this.numMonth = numberMonth;
            this.maxDay = maxDay;
            this.useName = name;
        }

        public int getNumMonth() {
            return numMonth;
        }

        public int getMaxDay() {
            return maxDay;
        }
        
        /**
         * Obter o mes que tem o numero fornecido
         * @param monthNumber
         * @return 
         */
        public static Month findMonth(int monthNumber)
        {
            for(Month m: values()) if(m.getNumMonth() == monthNumber) return m;
            return null;
        }
        private Object getName() 
        {
            return this.useName;
        }
    }
    
}
