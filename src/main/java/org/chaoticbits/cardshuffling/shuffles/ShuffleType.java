package org.chaoticbits.cardshuffling.shuffles;

public enum ShuffleType {
	RIFLE, OVERHAND, RANDOM, PICKUP, UNKNOWN;
	public static ShuffleType fromString(String str) {
		ShuffleType type = UNKNOWN;
		for (ShuffleType s : values()) {
			if (str.toUpperCase().contains(s.name()))
				type = s;
		}
		return type;
	}
}
