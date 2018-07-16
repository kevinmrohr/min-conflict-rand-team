package util

import java.io.{File, FileInputStream}

import com.typesafe.scalalogging.Logger
import model.{CoedQuadPlayers, UpperLowerCoedQuadPlayers}
import org.apache.poi.ss.usermodel.{Cell, CellType}
import org.apache.poi.xssf.usermodel.XSSFWorkbook

object ExcelReader {
  val l = Logger("ExcelReader")

  def readCoedQuadsPlayers(inputFileName: String): CoedQuadPlayers = {

    val workbook = new XSSFWorkbook(new FileInputStream(new File(inputFileName)))
    val sheet = workbook.getSheetAt(0)

    l.info("sheet name={}", sheet.getSheetName)


    var men: Set[String] = Set[String]()
    var women: Set[String] = Set[String]()

    sheet.rowIterator()
      .forEachRemaining(r => {
        val manName = r.getCell(0).getStringCellValue
        if (!manName.equals("Men")) men += manName

        val womanName = r.getCell(1).getStringCellValue
        if (!womanName.equals("Women")) women += womanName
      })

    men.foreach(m => println(m))
    women.foreach(w => println(w))

    new CoedQuadPlayers(men, women)
  }


  def addToSet(cell: Cell, s: Set[String]): Set[String] = {
    if (cell != null && cell.getCellTypeEnum.equals(CellType.STRING)) s + cell.getStringCellValue else s
  }

  def readUpperLowerCoedQuadsPlayers(inputFileName: String): UpperLowerCoedQuadPlayers = {

    val workbook = new XSSFWorkbook(new FileInputStream(new File(inputFileName)))
    val sheet = workbook.getSheetAt(0)

    l.info("sheet name={}", sheet.getSheetName)

    var upperMen: Set[String] = Set[String]()
    var upperWomen: Set[String] = Set[String]()
    var lowerMen: Set[String] = Set[String]()
    var lowerWomen: Set[String] = Set[String]()

    val rowIter = sheet.rowIterator()

    val headerRow = rowIter.next()

    var index: Int = 0
    var upperMenIndex: Int = 0
    var lowerMenIndex: Int = 0
    var upperWomenIndex: Int = 0
    var lowerWomenIndex: Int = 0


    headerRow.cellIterator().forEachRemaining(c => {
      if ("Upper Men".equals(c.getStringCellValue)) {
        upperMenIndex = index
      } else if ("Upper Women".equals(c.getStringCellValue)) {
        upperWomenIndex = index
      } else if ("Lower Men".equals(c.getStringCellValue)) {
        lowerMenIndex = index
      } else if ("Lower Women".equals(c.getStringCellValue)) {
        lowerWomenIndex = index
      }
      index = index + 1
    })

    rowIter.forEachRemaining(r => {
      upperMen = addToSet(r.getCell(upperMenIndex), upperMen)
      upperWomen = addToSet(r.getCell(upperWomenIndex), upperWomen)
      lowerMen = addToSet(r.getCell(lowerMenIndex), lowerMen)
      lowerWomen = addToSet(r.getCell(lowerWomenIndex), lowerWomen)
    })

    new UpperLowerCoedQuadPlayers(upperMen, upperWomen, lowerMen, lowerWomen)
  }
}
