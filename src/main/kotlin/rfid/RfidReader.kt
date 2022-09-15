package rfid

import com.sun.jna.Native
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import rfid.jna.posix.Input
import rfid.jna.posix.LibC

class RfidReader(
    private val libC: LibC = LibC.createInstance()
) {
    suspend fun streamRfidTags(): Flow<String> = flow {
        usingRfidKeyboard { fd ->
            val buffer = StringBuilder()
            while (true) {
                val event = readEvent(fd)
                if (event.type == 1.toShort() && event.value == 0) {
                    if (event.code == 28.toShort()) {
                        emit(buffer.toString())
                        buffer.clear()
                    } else {
                        buffer.append(KEYS[event.code!!.toInt()])
                    }
                }
            }

        }
    }

    private suspend fun usingRfidKeyboard(block: suspend (fd: Int) -> Unit) {
        val fd = libC.open("/dev/rfid", 0, LibC.O_RDONLY)
        if (-1 == fd) {
            throw IllegalStateException("Failed to open device")
        }
        try {
            libC.ioctl(fd, LibC.EVIOCGRAB, 1)
            try {
                block(fd)
            } finally {
                libC.ioctl(fd, LibC.EVIOCGRAB, 0)
            }
        } finally {
            libC.close(fd)
        }
    }

    private fun readEvent(fd: Int): Input {
        val i = Input()
        libC.read(fd, i, Native.getNativeSize(Input::class.java, i))
        return i
    }

    companion object {
        private const val KEYS = "X^1234567890XXXXqwertzuiopXXXXasdfghjklXXXXXyxcvbnmXXXXXXXXXXXXXXXXXXXXXXX"

    }
}

