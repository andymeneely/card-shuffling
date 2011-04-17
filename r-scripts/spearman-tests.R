#Simple four cards, non-standard ranks
deck1 <- c(1,4,6,3,2,5)
deck2 <- c(4,2,1,6,3,5)
cor(deck1, deck2, method="spearman")

cor(c(4,2,1,6,3,5), c(1,4,6,3,2,5), method="spearman")

#If we swap just the first two cards, what's the spearman?
deck1 <- c(1:52)
deck2 <- c(2,1,3:52)
cor(deck1, deck2, method="spearman")

#If we rotate the first three cards, what's the spearman?
deck1 <- c(1:52)
deck2 <- c(2,3,1,4:52)
cor(deck1, deck2, method="spearman")

#If we swap the first and last, what's the spearman?
deck1 <- c(1:52)
deck2 <- c(52, 2:51, 1)
cor(deck1, deck2, method="spearman")

#If we reverse the entire deck, what's the spearman?
deck1 <- c(1:52)
deck2 <- c(52:1)
cor(deck1, deck2, method="spearman")
