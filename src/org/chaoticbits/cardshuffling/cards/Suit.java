package org.chaoticbits.cardshuffling.cards;

import org.chaoticbits.cardshuffling.entry.DataEntryException;

public enum Suit {
	CLUBS("c"), DIAMONDS("d"), HEARTS("h"), SPADES("s");
	private final String entry;

	private Suit(String entry) {
		this.entry = entry;
	}

	public String getEntry() {
		return entry;
	}

	public static Suit fromText(String text) throws DataEntryException {
		for (Suit suit : values()) {
			if (suit.entry.equals(text))
				return suit;
		}
		throw new DataEntryException("No suit found for " + text);
	}
}
