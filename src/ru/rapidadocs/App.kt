package ru.rapidadocs

import ru.rapidadocs.model.Doc
import java.io.File
import java.nio.charset.Charset
import java.time.LocalDate
import java.time.format.DateTimeFormatter

var docs: ArrayList<Doc> = ArrayList()

fun main(args: Array<String>) {

    //val rootDirPath = "/media/fomakin/Samsung_Flash/RapidaCheck/docs"
    val rootDirPath = "/media/fomakin/Samsung_Flash/RapidaCheck/090118"

    File(rootDirPath).walkTopDown().forEach {
        val filePath = it.absolutePath
        if (File(filePath).isFile) {
            // Ищем балансовые документы
            if (it.name.startsWith("rp_") && it.name.endsWith(".bal")) {
                parseDocFile(filePath)
            }
            // Ищем межбанковские документы
            if (it.name.startsWith("rp_") && it.name.endsWith(".mb")) {
                parseDocFile(filePath)
            }
        }
    }

    //var commisDocs: List<Doc> = docs.stream().filter { c -> c.kredit.startsWith("70601") }.toList().sortedWith(compareBy { it.date })
    File("/media/fomakin/Samsung_Flash/RapidaCheck/res.csv").printWriter(Charset.defaultCharset()).use { out ->
        out.println("Дата;Номер;Дебет;Кредит;Сумма;Примечание")
        docs.forEach {
            out.println("${it.date.toString()};${it.number.toString()};${it.debet};${it.kredit};${it.sum.toString()};${it.purpose}")
        }
    }
    println("Done")
}

fun parseDocFile(filePath: String) {
    println(filePath)
    var docData = ArrayList<String>()

    File(filePath).forEachLine(Charset.forName("CP866")) {
        when {
            it.startsWith("#ДОКУМЕНТ") || it.startsWith("%БУФДОК") -> docData.clear()
            it.startsWith("#END") || it.startsWith("%END") -> parseToDoc(docData)
            else -> docData.add(it)
        }
    }
}

fun parseToDoc(docData: ArrayList<String>) {
    var doc = Doc()

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    docData.forEach {
        val key = it.split(":")[0].trim()
        val item = it.split(":")[1].trim()
        when (key) {
            "ДАТА" -> doc.date = LocalDate.parse(item, formatter)
            "НОМЕР" -> doc.number = item.toInt()
            "ДЕБЕТ" -> doc.debet = item
            "КРЕДИТ" -> doc.kredit = item
            "СУММА" -> doc.sum = item.toBigDecimal()
        }
        if (key.startsWith("ПРИМ") && !item.isNullOrEmpty()) {
            doc.purpose += " $item"
        }
    }
    if (!docs.contains(doc)) {
        docs.add(doc)
    }
}
