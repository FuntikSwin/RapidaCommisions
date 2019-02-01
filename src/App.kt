import java.io.BufferedReader
import java.io.File
import java.nio.charset.Charset

fun main(args: Array<String>) {
    println("123")

    File("/media/fomakin/Samsung_Flash/RapidaCheck/docs").walkTopDown().forEach {
        println(it)
        if (File(it.absolutePath).isFile) {
            File(it.absolutePath).forEachLine(Charset.defaultCharset()) {
                println(it)
            }
        }
    }
}