package org.chaoticbits.cardshuffling.cards;

import org.chaoticbits.cardshuffling.entry.DataEntryException;

public enum Suit {
	CLUBS("c", 0), DIAMONDS("d", 1), HEARTS("h", 2), SPADES("s", 3);
	private final String entry;
	private final int rank;

	private Suit(String entry, int rank) {
		this.entry = entry;
		this.rank = rank;
	}

	public String getEntry() {
		return entry;
	}

	public int getRank() {
		return rank;
	}

	public static Suit fromText(String text) throws DataEntryException {
		for (Suit suit : values()) {
			if (suit.entry.equals(text))
				return suit;
		}
		throw new DataEntryException("No suit found for " + text);
	}
}
