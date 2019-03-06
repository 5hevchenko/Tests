import com.sun.tools.javac.parser.Tokens;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static  org.mockito.internal.verification.VerificationModeFactory.atMost;




public class NumbersToWordsTest {

    private static boolean firstExecuted =false;
    private static boolean secondExecuted =false;
    private static boolean thirdExecuted =false;
    private static boolean fourthExecuted =false;


    NumbersToWords.DefaultProcessor processor = new NumbersToWords.DefaultProcessor();

    @Test
    public void testGetNameZero() throws Exception {

        int zero = 0;
        String ZERO = "ноль";


        System.out.println("Test Programm: test 1 - zero");
        System.out.println(zero + " = " + processor.getName(zero));

        assertEquals("Ошибка, нет нуля", ZERO, processor.getName(zero));
        firstExecuted = true;

        assertFalse( secondExecuted &&thirdExecuted && fourthExecuted);



    }

    @Test
    public void testGetNameUnit() throws Exception {

        String[] TOKENS = new String[]{"один", "два", "три", "четыре",
                "пять", "шесть", "семь", "восемь", "девять", "десять", "одиннадцать", "двенадцать", "тринадцать",
                "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"};

        System.out.println("Test Programm: test 2 - Numbers 1-19");


        //проверка чисел от одного до девятнадцати
        for (int i = 1; i < 20; i++) {
            System.out.println(i + " = " + processor.getName(i));
            assertEquals("Ошибка в числах от 1-ого до 19-и", TOKENS[i - 1], processor.getName(i));
        }
        secondExecuted = true;

        assertFalse( thirdExecuted && fourthExecuted);
    }

    @Test
    public void testGetNameTens() throws Exception {

        String[] TOKENS = new String[]{"двадцать один", "сорок два", "девяносто три", "тридцать четыре",
                "пятьдесят пять", "семьдесят шесть", "восемьдесят семь", "шестьдесят девять"};
        int tokens[] = new int[]{21, 42, 93, 34, 55, 76, 87, 69};

        System.out.println("Test Programm: test 3 - Numbers >20");

        //проверка всех чисел в массиве двузначных чисел
        for (int i = 1; i < 8; i++) {
            System.out.println(tokens[i - 1] + " = " + processor.getName(tokens[i - 1]));


            assertEquals(TOKENS[i - 1], processor.getName(tokens[i - 1]));


        }


        thirdExecuted = true;
        assertFalse( fourthExecuted);
    }

    @Test
    public void testGetNameAllTable() throws Exception {

        System.out.println("Test Programm: test 3 - Different numbers");
        InputStream in = new FileInputStream("TestData/DataExel.xls");
        HSSFWorkbook wb = new HSSFWorkbook(in);

        long inNumber = 0;
        String inString = null;

        Sheet sheet = wb.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();
        while (it.hasNext()) {
            Row row = it.next();

            Iterator<Cell> cells = row.iterator();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                int cellType = cell.getCellType();

                switch (cellType) {
                    case Cell.CELL_TYPE_NUMERIC:
                        System.out.print((inNumber=(long)cell.getNumericCellValue()) + " = ");
                        break;

                    case Cell.CELL_TYPE_STRING:
                        System.out.print((inString=cell.getStringCellValue()));
                        break;

                    default:
                        //System.out.print("|");
                        break;
                }
            }
            System.out.println();
            assertEquals("Ошибка в числе: " + inNumber, inString, processor.getName(inNumber));
        }
        fourthExecuted = true;
        assertTrue( firstExecuted && secondExecuted && thirdExecuted && fourthExecuted);
    }

}