import java.io.{File, FileInputStream}

import com.typesafe.scalalogging.Logger
import org.apache.poi.xssf.usermodel.XSSFWorkbook


object Main extends App {
  val l = Logger("Main")
  l.info("Hello, New York!")

  val workbook = new XSSFWorkbook(new FileInputStream(new File("/Users/rohrk/source/min-conflict-rand-team/src/main/resources/actual_vs_potential_2018-06-01_11-00-20.xls")))

  l.info("workbook={}", workbook)

  val sheet = workbook.getSheetAt(0)

  l.info("sheet name={}", sheet.getSheetName)

  sheet.rowIterator()
    .forEachRemaining(r => {
      l.info("rowNumber={}, row={}", r.getRowNum, r)
      r.cellIterator()
        .forEachRemaining(c => l.info(c.getStringCellValue))
    })
}


