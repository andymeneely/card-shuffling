package org.chaoticbits.cardshuffling.cards;

import java.util.ArrayList;
import java.util.List;

import org.chaoticbits.cardshuffling.entry.DataEntryException;

public class PlayingCard implements Comparable<PlayingCard> {
	private Suit suit;
	private Value value;

	public PlayingCard(Value value, Suit suit) {
		this.suit = suit;
		this.value = value;
	}

	public Suit getSuit() {
		return suit;
	}

	public Value getValue() {
		return value;
	}

	public static PlayingCard fromText(String text) throws DataEntryException {
		text = text.trim().toLowerCase();
		Suit suit = Suit.fromText(text.substring(text.length() - 1));
		Value value = Value.fromText(text.substring(0, text.length() - 1));
		return new PlayingCard(value, suit);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayingCard other = (PlayingCard) obj;
		if (suit != other.suit)
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return value.name() + " of " + suit.name();
	}

	/**
	 * Compare the order to another playing card
	 */
	public int compareTo(PlayingCard o) {
		throw new IllegalStateException("unimplemented!");
	}

	public int getRank() {
		return value.getNum() + suit.getRank() * 13;
	}
	
	public static List<PlayingCard> newDeck() {
		ArrayList<PlayingCard> newDeck = new ArrayList<PlayingCard>(52);
		for (Suit suit : Suit.values()) {
			for (Value value : Value.values()) {
				newDeck.add(new PlayingCard(value, suit));
			}
		}
		return newDeck;
	}

}
