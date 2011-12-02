package org.chaoticbits.cardshuffling.entry;

import java.util.ArrayList;
import java.util.List;

import org.chaoticbits.cardshuffling.cards.PlayingCard;

/**
 * ShuffleState represent the state of a given deck of cards. That is, the ordered list of cards. Intended to
 * be used in a sequence of ShuffleStates such that the sequenceNumber represents one shuffle state to the
 * next.
 * @author andy
 * 
 */
public class ShuffleState {
	private String description;
	private int sequenceNumber;
	private List<PlayingCard> deck = new ArrayList<PlayingCard>(55);

	/**
	 * Given the description and where this
	 * @param description
	 * @param sequenceNumber
	 */
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
