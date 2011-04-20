package org.chaoticbits.cardshuffling.entry;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.chaoticbits.cardshuffling.cards.PlayingCard;

public class TrialReader {

	public List<ShuffleState> readDeck(File file) throws FileNotFoundException, DataEntryException {
		List<ShuffleState> shuffles = new ArrayList<ShuffleState>();
		Scanner scanner = new Scanner(file);
		ShuffleState current = null;
		int sequenceNumber = 0;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (!line.startsWith("#") && line.trim().length() > 0) {// ignore comments and blank lines
				if (line.startsWith(":")) {
					current = new ShuffleState(line.substring(1), ++sequenceNumber);
					shuffles.add(current);
				} else { // otherwise, assume it's a card
					// null here means no first shuffle name
					current.getDeck().add(PlayingCard.fromText(line));
				}
			}
		}
		return shuffles;
	}
}
