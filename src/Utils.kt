import java.io.File
import java.util.Scanner

fun readFile(filepath: String): String {
    var file: File = File(filepath)

    var fileScanner: Scanner = Scanner(file)

    var data: String = ""

    while (fileScanner.hasNextLine()) {
        data += fileScanner.nextLine() + "\n"
    }

    return data
}
