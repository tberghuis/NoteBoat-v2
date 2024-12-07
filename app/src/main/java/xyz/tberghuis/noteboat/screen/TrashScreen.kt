package xyz.tberghuis.noteboat.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import xyz.tberghuis.noteboat.composable.TrashNotesList
import xyz.tberghuis.noteboat.composable.TrashTopBar

@Composable
fun TrashScreen() {
  Scaffold(
    topBar = { TrashTopBar() },
    content = { contentPadding ->
      Box(
        Modifier
          .padding(contentPadding)
          .navigationBarsPadding()
      ) {
        TrashNotesList()
      }
    },
  )
}