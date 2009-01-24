package environment;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

/**
 * Text field to get new Position
 * @author Maciej Kupczak
 *
 */
public class PositionField extends JTextField
{
   private int maxPos;
   private int docSize;
   /**
    * @param domwar domy�lna warto�c, jezeli d�u�sza ni� 4 czyfry to przycinana do 4 pierwszych
    * @param rozmiar rozmiar pola tekstowego
    */
   public PositionField(int size, int max)
   {
      super(size);
      maxPos = max;
      docSize = size;
      setDocument(createDefaultModel());
      setInputVerifier(new WerSize(size, maxPos));
   }
   
   protected Document createDefaultModel()
   {
      return new PositionDoc(maxPos, docSize);
   }
   /**
    * sprawdza czy zawartosc pola jest poprawna liczba calkowita czterocyfrowa
    * @return true jesli jest poprawna
    */
   public boolean jestPoprawny()
   {
      return PositionDoc.poprawny(getText(), maxPos);
   }
   /**
    * Pobiera wartosc liczbowa pola tekstowwego
    * @return liczba wpisana w polu, 1337 jesli zawartosc jest nie poprawna
    */
   public Integer pobierzWartosc()
   {
      try
      {
         return Integer.parseInt(getText());
      }
      catch(NumberFormatException w)
      {
         return null;
      }
   }
}

/**
 * Dokument kt�ry moze przechowywa tylko 4 cyfrowe integery
 * @author Maciej Kupczak
 */
class PositionDoc extends PlainDocument
{
   private int maxVal;
   private int size;
   
   public PositionDoc(int max, int s)
   {
      super();
      maxVal = max;
      size = s;
   }
   
   public void insertString(int poz, String str, AttributeSet a) throws BadLocationException
   {
      if (str==null) return;
      
      String dawnyString = getText(0, getLength());
      String nowyString = dawnyString.substring(0,poz)+str+dawnyString.substring(poz);
      
      if (poprawny(nowyString, maxVal))
      {  
         if ((nowyString.length())>size)
         {
            nowyString = nowyString.substring(0, size);
            super.replace(0, size, nowyString, a);
         }
         else 
         {
            super.insertString(poz, str, a);
         }
      }
   }
   /**
    * sprawdza czy podany string jest liczb�
    * @param str sprawdzany ci�g
    * @return true je�li jest liczba, false w przeciwnym wypadku
    */
   static boolean poprawny(String str, int max)
   {
      try
      {
         int value = Integer.parseInt(str);
         if (value < max)
            return true;
         else return false;
      }
      catch(NumberFormatException w)
      {
         return false;
      }
   }
}

class WerSize extends InputVerifier
{
   private int size;
   private int maxVal;
   
   public WerSize(int s, int max)
   {
      size = s;
      maxVal = max;
   }
   
   public boolean verify(JComponent komponent)
   {
      String tekst = ((JTextComponent)komponent).getText();
      if (tekst.length()>size)
         return false;
      else
         return PositionDoc.poprawny(tekst, maxVal);     
   }
}