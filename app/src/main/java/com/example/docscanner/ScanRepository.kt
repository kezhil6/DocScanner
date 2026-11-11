package com.example.docscanner

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/** Stores scanned PDFs in the app's private storage (filesDir/scans). */
class ScanRepository(private val context: Context) {

    private val dir: File
        get() = File(context.filesDir, "scans").apply { mkdirs() }

    fun list(): List<File> =
        dir.listFiles { f -> f.isFile && f.extension.equals("pdf", ignoreCase = true) }
            ?.sortedByDescending { it.lastModified() }
            ?: emptyList()

    /** Copies the PDF produced by the scanner into permanent app storage. */
    fun saveFromUri(uri: Uri): File? {
        val stamp = SimpleDateFormat("yyyy-MM-dd HH.mm.ss", Locale.getDefault()).format(Date())
        val out = uniqueFile("Scan $stamp")
        val input = context.contentResolver.openInputStream(uri) ?: return null
        input.use { i -> out.outputStream().use { o -> i.copyTo(o) } }
        return out
    }

    fun rename(file: File, newName: String): File? {
        val base = newName.replace(Regex("[\\\\/:*?\"<>|]"), "").trim().removeSuffix(".pdf")
        if (base.isBlank()) return null
        val target = File(dir, "$base.pdf")
        if (target.exists() && target != file) return null
        return if (file.renameTo(target)) target else null
    }

    fun delete(file: File): Boolean = file.delete()

    fun uriFor(file: File): Uri =
        FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)

    private fun uniqueFile(base: String): File {
        var f = File(dir, "$base.pdf")
        var n = 1
        while (f.exists()) {
            f = File(dir, "$base ($n).pdf")
            n++
        }
        return f
    }
}
