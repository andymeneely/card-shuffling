package org.chaoticbits.cardshuffling.cards;

import org.chaoticbits.cardshuffling.entry.DataEntryException;

public enum Value {
	ACE(1, "a"), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(11,
			"j"), QUEEN(12, "q"), KING(13, "k");

	private final int num;
	private final String entry;

	private Value(int num, String entry) {
		this.num = num;
		this.entry = entry;
	}

	private Value(int num) {
		this.num = num;
		this.entry = "" + num;
	}

	public int getNum() {
		return num;
	}

	public String getEntry() {
		return entry;
	}

	public static Value fromText(String text) throws DataEntryException {
		for (Value value : values()) {
			if (value.entry.equals(text))
				return value;
		}
		throw new DataEntryException("No card value found for " + text);
	}

}
