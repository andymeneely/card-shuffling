package org.chaoticbits.cardshuffling;

import java.util.List;

import org.chaoticbits.cardshuffling.cards.PlayingCard;
import org.chaoticbits.cardshuffling.shuffles.ShuffleType;

public interface IShuffle {

	public abstract List<PlayingCard> shuffle(List<PlayingCard> deck);

	public String name();
	
	public ShuffleType type();
}