package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	private int turn;
	private Color currentPlayer;
	private Board board;

	public ChessMatch() {
		this.board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.RED;
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getcurrentPlayer() {
		return currentPlayer;
	}

	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return mat;
	}
	
	public boolean[][] possiblesMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
		
	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		nextTurn();
		return (ChessPiece) capturedPiece;
	}

	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturePiece = board.removePiece(target);
		board.placePiece(p, target);

		return capturePiece;
	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsApiece(position)) {
			throw new ChessExecption("There is no piece on source position");
		}
		if(currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
			throw new ChessExecption("the chosen piece is not yours");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessExecption("there is no possible moves for the chosen piece");
		}
	}

	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessExecption("The chosen piece can't move to target position");
		}
	}

	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.RED) ? Color.BLUE : Color.RED;
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}

	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.RED));
		placeNewPiece('c', 2, new Rook(board, Color.RED));
		placeNewPiece('d', 2, new Rook(board, Color.RED));
		placeNewPiece('e', 2, new Rook(board, Color.RED));
		placeNewPiece('e', 1, new Rook(board, Color.RED));
		placeNewPiece('d', 1, new King(board, Color.RED));

		placeNewPiece('c', 7, new Rook(board, Color.BLUE));
		placeNewPiece('c', 8, new Rook(board, Color.BLUE));
		placeNewPiece('d', 7, new Rook(board, Color.BLUE));
		placeNewPiece('e', 7, new Rook(board, Color.BLUE));
		placeNewPiece('e', 8, new Rook(board, Color.BLUE));
		placeNewPiece('d', 8, new King(board, Color.BLUE));
	}
}
