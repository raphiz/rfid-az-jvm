package rfid.jna.posix

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.Structure

typealias FileDescriptor = Int

interface LibC : Library {
    companion object {
        const val O_RDONLY: Int = 82
        const val EVIOCGRAB = 1074021776
        fun createInstance(): LibC = Native.load("libc", LibC::class.java)
    }


    fun open(name: String, flags: Int, mode: Int): FileDescriptor

    fun close(fd: FileDescriptor): Int

    fun read(fd: FileDescriptor, buf: Any?, nbyte: Int): Int

    fun ioctl(fd: FileDescriptor, request: Int, i1: Int): Int

}


@Suppress("unused", "PropertyName")
@Structure.FieldOrder("tv_sec", "tv_usec")
class Timeval : Structure() {
    @JvmField
    var tv_sec: Long? = null

    @JvmField
    var tv_usec: Long? = null

}

@Suppress("unused")
@Structure.FieldOrder("time", "type", "code", "value")
class Input : Structure() {
    @JvmField
    var code: Short? = null

    @JvmField
    var time: Timeval? = null

    @JvmField
    var type: Short? = null

    @JvmField
    var value: Int? = null

}
