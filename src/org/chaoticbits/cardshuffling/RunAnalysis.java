package org.chaoticbits.cardshuffling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.chaoticbits.cardshuffling.entry.DataEntryException;
import org.chaoticbits.cardshuffling.entry.EntryCorrection;
import org.chaoticbits.cardshuffling.entry.ShuffleState;
import org.chaoticbits.cardshuffling.entry.TrialReader;

public class RunAnalysis {

	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(RunAnalysis.class);

	public static void main(String[] args) throws FileNotFoundException, DataEntryException {
		PropertyConfigurator.configure("log4j.properties");
		log.info("Loading trial files...");
		List<ShuffleState> states = loadTrialFiles();
		log.info("Checking data entry...");
		checkShuffleStates(states);
		log.info("Deriving shuffle transformations...");
		List<ShuffleTransformation> shuffles = deriveShuffles(states);

		log.info("Loaded and checked " + states.size() + " deck states");
		log.info("Derived " + shuffles.size() + " shuffles");
		log.info("Done.");
	}

	private static List<ShuffleState> loadTrialFiles() throws FileNotFoundException, DataEntryException {
		List<File> trialFiles = listTrialFiles(new File("data/"));
		List<ShuffleState> shuffles = new ArrayList<ShuffleState>();
		for (File trialFile : trialFiles) {
			log.info("\tLoading file " + trialFile);
			shuffles.addAll(new TrialReader().readDeck(trialFile));
		}
		return shuffles;
	}

	private static List<File> listTrialFiles(File dir) {
		String[] names = dir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.startsWith("trial") && name.endsWith(".txt");
			}
		});
		List<File> trialFiles = new ArrayList<File>();
		for (String fileName : names) {
			trialFiles.add(new File(dir, fileName));
		}
		return trialFiles;
	}

	private static void checkShuffleStates(List<ShuffleState> states) throws DataEntryException {
		for (ShuffleState shuffleState : states) {
			log.info("\tChecking... " + shuffleState);
			new EntryCorrection().checkDeckEntry(shuffleState.getDeck());
		}
	}

	private static List<ShuffleTransformation> deriveShuffles(List<ShuffleState> states) {
		List<ShuffleTransformation> shuffles = new ArrayList<ShuffleTransformation>();
		for (int i = 0; i < states.size() - 1; i++) {
			// if consecutive shuffle state
			if (states.get(i).getSequenceNumber() < states.get(i + 1).getSequenceNumber())
				shuffles.add(new ShuffleTransformation(states.get(i).getDeck(), states.get(i + 1).getDeck()));
		}
		return shuffles;
	}
}
