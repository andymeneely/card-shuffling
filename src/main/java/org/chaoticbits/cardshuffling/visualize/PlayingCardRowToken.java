package org.chaoticbits.cardshuffling.visualize;

import org.chaoticbits.cardshuffling.cards.PlayingCard;

public class PlayingCardRowToken {
	private PlayingCard card;
	private int row;

	public PlayingCardRowToken(PlayingCard card, int row) {
		this.card = card;
		this.row = row;
	}

	public PlayingCard getCard() {
		return card;
	}

	public int getRow() {
		return row;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((card == null) ? 0 : card.hashCode());
		result = prime * result + row;
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
		PlayingCardRowToken other = (PlayingCardRowToken) obj;
		if (card == null) {
			if (other.card != null)
				return false;
		} else if (!card.equals(other.card))
			return false;
		if (row != other.row)
			return false;
		return true;
	}

}
