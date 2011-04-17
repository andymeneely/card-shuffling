CREATE TABLE Decks (
  ID INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  Trial VARCHAR(45) NOT NULL,
  ShuffleOrder INTEGER UNSIGNED NOT NULL,
  ShuffleType VARCHAR(45) NOT NULL,
  Card VARCHAR(45) NOT NULL,
  CardOrder INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY (ID)
)
ENGINE = MYISAM;