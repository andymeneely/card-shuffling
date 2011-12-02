package org.chaoticbits.cardshuffling.entry;

import java.util.ArrayList;
import java.util.List;

import org.chaoticbits.cardshuffling.cards.PlayingCard;

public class ShuffleState {
	private String description;
	private int sequenceNumber;
	private List<PlayingCard> deck = new ArrayList<PlayingCard>(55);

	public ShuffleState(String description, int sequenceNumber) {
		this.description = description;
		this.sequenceNumber = sequenceNumber;
	}

	public String getDescription() {
		return description;
	}

	public List<PlayingCard> getDeck() {
		return deck;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	@Override
	public String toString() {
		return getDescription() + " (#" + getSequenceNumber() + ")";
	}
}
