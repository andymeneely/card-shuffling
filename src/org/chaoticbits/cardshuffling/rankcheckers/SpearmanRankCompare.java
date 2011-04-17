package org.chaoticbits.cardshuffling.rankcheckers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.chaoticbits.cardshuffling.cards.PlayingCard;

public class SpearmanRankCompare implements IRankChecker {

	/**
	 * This method runs the Spearman rank correlation coefficient (called rho) on two decks of cards. The
	 * method returns a number in [-1,1] where -1 is where the two decks are in complete reverse order.
	 * 
	 * Since these are all distinct playing cards, we know there aren't any ties in the ranks. So we use the
	 * simpler version from here: http://en.wikipedia.org/wiki/Spearman%27s_rank_correlation_coefficient
	 * 
	 * @param List
	 *            <PlayingCard> before - the ordered deck before shuffling
	 * @param List
	 *            <PlayingCard> after - the ordered deck after shuffling
	 */
	@Override
	public Double compareRanks(List<PlayingCard> before, List<PlayingCard> after) {
		double n = before.size();
		double denominator = n * (n * n - 1);
		double rho = 1.0 - (6.0 * sumRankDifferences(before, after)) / denominator;
		return rho;

	}

	private double sumRankDifferences(List<PlayingCard> before, List<PlayingCard> after) {
		double sum = 0.0;
		Map<PlayingCard, Integer> beforeRank = initBeforeRankMap(before);
		for (int rank = 0; rank < before.size(); rank++) {
			// rank = the rank of the card in the second deck. 5th card has a rank 5.
			// beforeRank stuff = look up the beforeRank of the currently ranked card
			double square = rank - beforeRank.get(after.get(rank));
			square = square * square;
			sum += square;
		}
		return sum;
	}

	/**
	 * Treat the before rank as the 1:52
	 * @param before
	 * @return
	 */
	private Map<PlayingCard, Integer> initBeforeRankMap(List<PlayingCard> before) {
		Map<PlayingCard, Integer> beforeRank = new HashMap<PlayingCard, Integer>(52);
		for (int rank = 0; rank < before.size(); rank++) {
			beforeRank.put(before.get(rank), rank);
		}
		return beforeRank;

	}
}
