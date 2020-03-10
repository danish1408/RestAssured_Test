package utility;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BaseClass {

    public static Properties prop;
    public static FileInputStream fis;

    public static void init() {
        try {

            prop = new Properties();

            FileInputStream fls = new FileInputStream("src\\test\\java\\config\\config.properties");
            prop.load(fls);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object[][] readTestData( String s) throws IOException {

        File src = new File(s);
        try {
            fis = new FileInputStream(src);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sh1 = wb.getSheetAt(0);
        int rowCount = sh1.getPhysicalNumberOfRows();
        int cellCount = sh1.getRow(0).getPhysicalNumberOfCells();
        Object data[][] = new Object[rowCount-1][cellCount];

        for (int i = 0, k = 1; k < rowCount; i++, k++) {

            for (int j = 0; j < cellCount; j++) {

                data[i][j] = sh1.getRow(i+1).getCell(j).getStringCellValue();
                           }
        }

        return data;

    }


}

