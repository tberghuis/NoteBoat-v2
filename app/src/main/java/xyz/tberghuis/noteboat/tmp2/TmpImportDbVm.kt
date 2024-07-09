package xyz.tberghuis.noteboat.tmp2

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import java.io.File
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import xyz.tberghuis.noteboat.IMPORT_DB_FILENAME
import xyz.tberghuis.noteboat.MainApplication
import xyz.tberghuis.noteboat.data.AppDatabase
import xyz.tberghuis.noteboat.utils.logd

class TmpImportDbVm(
  application: Application,
) : AndroidViewModel(application) {
  private val mainApp = (application as MainApplication)

  fun importDb(importDbUri: Uri) {
    viewModelScope.launch(IO) {
      // todo use CONSTANT for filename
      val importDbFile = File(mainApp.filesDir, IMPORT_DB_FILENAME)
      val inputStream = mainApp.contentResolver.openInputStream(importDbUri)
      // https://www.baeldung.com/kotlin/inputstream-to-file
      inputStream?.use { input ->
        importDbFile.outputStream().use { output ->
          input.copyTo(output)
        }
      }

      // open import-notes.db
      val importNotesFile = File(mainApp.filesDir, IMPORT_DB_FILENAME)
      logd("importNotesFile ${importNotesFile.path}")
      // create room instance
      val roomImport = Room.databaseBuilder(
        mainApp,
        AppDatabase::class.java,
        importNotesFile.path
      )
        .build()
      // read all notes
      // if invalid file, room will log error and give me empty notes list
      val importNotesList = roomImport.noteDao().getAll().first().map {
        it.copy(noteId = 0)
      }
      // write to appDatabase
      mainApp.appDatabase.noteDao().insertAll(importNotesList)
      // close import db
      roomImport.close()
      // delete import db
      importNotesFile.delete()
    }
  }
}