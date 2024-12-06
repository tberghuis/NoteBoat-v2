package xyz.tberghuis.noteboat.tmp2

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import xyz.tberghuis.noteboat.composable.ActionsCard
import xyz.tberghuis.noteboat.screen.NoteCard
import xyz.tberghuis.noteboat.vm.HomeViewModel

@Composable
fun NotesList() {
  val viewModel: HomeViewModel = viewModel()
  val allNotes = viewModel.allNotes.collectAsState(listOf())
  val offsetNotes = viewModel.offsetNotes.collectAsState(setOf())
  LazyColumn(
    contentPadding = PaddingValues(10.dp)
  ) {
    // when key = note_id there was a bug, could not swipe to reveal after toggling note.pinned
    items(allNotes.value,
      key = { it.hashCode() }
    ) { note ->
      Box(
        Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
      ) {
        ActionsCard(note)
        NoteCard(
          note,
          offsetNotes.value.contains(note),
          viewModel::onRevealActions,
          viewModel::onHideActions
        )
      }
    }
    item {
      // clear FAB
      Spacer(Modifier.height(70.dp))
    }
  }
}
