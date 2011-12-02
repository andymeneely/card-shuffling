package org.chaoticbits.cardshuffling.shuffles;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.chaoticbits.cardshuffling.IShuffle;
import org.chaoticbits.cardshuffling.cards.PlayingCard;

/**
 * Given a pool of IShuffles, this method will randomly choose one shuffle out of that list and
 * execute it. That one IShuffle gets set aside and the pool shrinks until it's gone - then resets.
 * This guarantees that all shuffle algorithms are executed evenly, but in random order.
 * 
 * @author apmeneel
 * 
 */
public class RandomAlgorithmShuffle implements IShuffle {

	private List<? extends IShuffle> pool;
	private List<IShuffle> donePool;
	private String name = "(empty pool)";
	private final Random rnd;

	public RandomAlgorithmShuffle(List<? extends IShuffle> pool, Random rnd) {
		this.pool = pool;
		this.rnd = rnd;
		if (pool.size() > 0)
			name = pool.get(0).name();
		donePool = new ArrayList<IShuffle>(pool.size());
	}

	public List<PlayingCard> shuffle(List<PlayingCard> deck) {
		if (pool.isEmpty())
			pool = donePool;
		IShuffle nextShuffle = pool.remove(rnd.nextInt(pool.size()));
		donePool.add(nextShuffle);
		return nextShuffle.shuffle(deck);
	}

	public String name() {
		return "Random algorithm of " + name;
	}

	public ShuffleType type() {
		return ShuffleType.RANDOM;
	}

}
