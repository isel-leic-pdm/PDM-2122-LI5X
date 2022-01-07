package pt.isel.pdm.tictactoe

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.isel.pdm.tictactoe.game.model.*
import pt.isel.pdm.tictactoe.ui.theme.TicTacToeTheme

class GameComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val board = remember { mutableStateOf(Board()) }
                    val currentBoard = board.value
                    Log.v(TAG, "Recomposing with $currentBoard")

                    BoardView(
                        board = currentBoard,
                        onTileSelected = { _, at ->
                            if (currentBoard[at] == null)
                                board.value = currentBoard.makeMove(at)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TileView(player: Player?, onTileSelected: (Player?) -> Unit) = Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier
        .size(128.dp)
        .background(MaterialTheme.colors.background)
        .clickable(enabled = true, onClick = { onTileSelected(player) })
        .padding(32.dp)
) {
    if (player != null) {
        val imageResource = painterResource(id =
            if (player == Player.CIRCLE) R.drawable.circle_2
            else R.drawable.cross_2
        )
        Image(
            painter = imageResource,
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun BoardView(board: Board, onTileSelected: (Player?, Coordinate) -> Unit) {
    val lineThickness = 8.dp
    Column(modifier = Modifier
        .background(Color.Black)
        .fillMaxSize()) {
        repeat(board.side) { rowIndex ->
            Row(modifier = Modifier.fillMaxWidth()) {
                repeat(board.side) { count ->
                    val at = Coordinate(rowIndex.toRow(), count.toColumn())
                    TileView(board.getMove(at) , onTileSelected = { onTileSelected(it, at) })
                    if (count < board.side - 1)
                        Spacer(modifier = Modifier.width(lineThickness))
                }
            }
            if (rowIndex < board.side - 1)
                Spacer(modifier = Modifier.height(lineThickness))
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TicTacToeTheme {
        BoardView(Board(), { _, _ -> })
    }
}