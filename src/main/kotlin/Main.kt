import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import rfid.RfidReader
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.nio.file.StandardOpenOption.APPEND
import java.time.LocalDateTime


val tags = mapOf(
    "0007607995" to "Admin",
    "0007557184" to "Pause",
    "0007565508" to "Daily",
    "0007584024" to "Other",
    "0007364898" to "PR Review",
    "0007586333" to "Entwicklung",
    "0007611008" to "Meeting",
)

fun main() = runBlocking {
    println("Waiting for RFID tags...")

    RfidReader().streamRfidTags().collect {
        val now = LocalDateTime.now()
        val activity = tags.getOrElse(it) { it }

        println("Starting $activity")

        withContext(Dispatchers.IO) {
            Files.write(
                Paths.get("rfid_log.csv"),
                "${now.toLocalDate()},${now.toLocalTime()},$activity\n".encodeToByteArray(),
                APPEND, StandardOpenOption.CREATE
            )
        }
    }

}
