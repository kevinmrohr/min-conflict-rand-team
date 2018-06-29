import java.io.{BufferedOutputStream, File, FileInputStream, FileOutputStream}

import com.typesafe.scalalogging.Logger
import org.apache.poi.xssf.usermodel.{XSSFRow, XSSFSheet, XSSFWorkbook}


object Main extends App {
  val l = Logger("Main")

  val workbook = new XSSFWorkbook(new FileInputStream(new File("/Users/rohrk/source/min-conflict-rand-team/src/main/resources/players.xlsx")))
  val sheet = workbook.getSheetAt(0)

  l.info("sheet name={}", sheet.getSheetName)


  var men:Set[String] = Set[String]()
  var women:Set[String] = Set[String]()

  sheet.rowIterator()
    .forEachRemaining(r => {
      val manName = r.getCell(0).getStringCellValue
      if (!manName.equals("Men")) men += manName

      val womanName = r.getCell(1).getStringCellValue
      if (!womanName.equals("Women")) women += womanName
    })

  men.foreach(m => println(m))
  women.foreach(w => println(w))

  val rounds = new UniqQuadGenerator().generateRounds(5, men, women)

  val output = new XSSFWorkbook()

  var roundNumber = 0
  rounds.foreach(r => {
    roundNumber += 1
    var rowNumber = 0
    val sheet = output.createSheet("Round " + roundNumber)
    r.quads.foreach(quad => {
      val row = sheet.createRow(rowNumber)
      rowNumber = rowNumber + 1

      var cellNumber:Int = 0
      quad.foreach(player => {
        val cell = row.createCell(cellNumber)
        cell.setCellValue(player)
        cellNumber += 1
      })
    })
  })


  output.write(new BufferedOutputStream( new FileOutputStream(new File("./output.xls")) ))
}


