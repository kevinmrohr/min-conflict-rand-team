package util

import java.io.{BufferedOutputStream, File, FileOutputStream}

import model.Round
import org.apache.poi.xssf.usermodel.XSSFWorkbook

object ExcelWriter {

  def writeOutput(rounds: Set[Round], outputFileName: String): XSSFWorkbook = {

    val output = new XSSFWorkbook()

    var roundNumber = 0
    rounds.foreach(r => {
      roundNumber += 1
      var rowNumber = 0
      val sheet = output.createSheet("Round " + roundNumber)
      r.teams.foreach(quad => {
        val row = sheet.createRow(rowNumber)
        rowNumber = rowNumber + 1

        var cellNumber: Int = 0
        quad.foreach(player => {
          val cell = row.createCell(cellNumber)
          cell.setCellValue(player)
          cellNumber += 1
        })
      })
    })


    output.write(new BufferedOutputStream(new FileOutputStream(new File(outputFileName))))
    output
  }
}
