package org.chaoticbits.cardshuffling.rankcheckers;

import java.util.List;

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
	public Double compareRanks(List<PlayingCard> before, List<PlayingCard> after) {
		double n = before.size();
		double denominator = n * (n * n - 1);
		double rho = 1.0 - (6.0 * sumRankDifferences(before, after)) / denominator;
		return rho;

	}

	private double sumRankDifferences(List<PlayingCard> before, List<PlayingCard> after) {
		double sum = 0.0;
		for (int rank = 1; rank <= before.size(); rank++) {
			// rank = the rank of the card in the second deck. 5th card has a rank 5.
			// beforeRank stuff = look up the beforeRank of the currently ranked card
			double square = after.get(rank - 1).getRank() - before.get(rank - 1).getRank();
			square = square * square;
			sum += square;
		}
		return sum;
	}

	public String name() {
		return "Spearman Rank";
	}
}
