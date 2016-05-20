package module11_util;

import static module11_util.ChessPiece.Color.*;
import static module11_util.ChessPiece.Figure.*;

import java.util.EnumMap;
import java.util.EnumSet;

import module11_util.ChessPiece.Color;
import module11_util.ChessPiece.Figure;

public class ChessPieceDemo {
	
	public static void main(String[] args) {
		ChessPiece p1 = new ChessPiece(WHITE, KING);
		ChessPiece p2 = new ChessPiece(BLACK, KING);
		
		System.out.println(p1.getFigure() + ":" + p1.getColor());
		System.out.println(p2.getFigure() + ":" + p2.getColor());
		
		EnumSet<Figure> set = EnumSet.complementOf(EnumSet.of(KING));
		for (ChessPiece.Figure nextFigure : set) {
			System.out.println(nextFigure);
		}
		
		EnumMap<Color, EnumSet<Figure>> map = new EnumMap<>(Color.class);
		map.put(WHITE, EnumSet.allOf(Figure.class));
		map.put(BLACK, EnumSet.allOf(Figure.class));
		
		for (EnumSet<Figure> nextSet : map.values()) {
			System.out.println("Next color: ");
			for (Figure nextFigure : nextSet) {
				System.out.println("  " + nextFigure);
			}
		}
	}
}
