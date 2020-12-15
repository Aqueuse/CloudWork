# Load the libraries
library("tm")
library("SnowballC")
library("wordcloud")
library("RColorBrewer")
library("stringi")
library("svglite")

# set the working directory
# first line in for windows / second line is for GNU/Linux
setwd("E://backup_data_files/JavaProjects/poleEmploi/ressources/")
#setwd("/mnt/e/backup_data_files/JavaProjects/poleEmploi/ressources/")

# read the words file
filePath <- ("./words.txt")
text <- readLines(filePath)

# Load the data as a corpus
# first line in for windows / second line is for GNU/Linux
corpus <- VCorpus(x = VectorSource(text), readerControl = list(reader = readPlain, language = "fr"))
#corpus <- Corpus(VectorSource(text))

docs <- tm_map(corpus, removeWords, stopwords())

toSpace <- content_transformer(function(x , pattern ) gsub(pattern, " ", x))
docs <- tm_map(docs, toSpace, "/")
docs <- tm_map(docs, toSpace, "@")
docs <- tm_map(docs, toSpace, "\\|")

# replace non-convertible bytes in the corpus with strings showing their hex codes
# also subtily convert the text to lower case
docs <- tm_map(docs, content_transformer(stri_trans_tolower))

# Remove numbers
docs <- tm_map(docs, removeNumbers)

# Remove french common stopwords
docs <- tm_map(docs, removeWords, stopwords("french"))

# Remove punctuations
docs <- tm_map(docs, removePunctuation)

# Eliminate extra white spaces
docs <- tm_map(docs, stripWhitespace)

dtm <- TermDocumentMatrix(docs)
m <- as.matrix(dtm)
v <- sort(rowSums(m),decreasing = TRUE)
d <- data.frame(word = names(v),freq = v)
head(d, 10)

svglite(file = "./cloud.svg")
#jpeg(filename = "./cloud.jpg")

set.seed(1234)
wordcloud(words = d$word, freq = d$freq, min.freq = 1,
  max.words = 200, random.order = FALSE, rot.per = 0.35, 
  colors = brewer.pal(8, "Dark2"))

dev.off()
