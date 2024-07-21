package xyz.tberghuis.noteboat.tmp2

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.noteboat.vm.HomeViewModel
import xyz.tberghuis.noteboat.data.Note

@Composable
fun TmpActionsCard(
  note: Note,
  viewModel: HomeViewModel = viewModel()
) {


  Box(
    Modifier
      .fillMaxWidth(),
    contentAlignment = Alignment.CenterEnd
  ) {
    IconButton(onClick = {
      // delete that note no warning
      viewModel.deleteNote(note)
    }) {
      Icon(Icons.Filled.Delete, "delete")
    }
  }
}