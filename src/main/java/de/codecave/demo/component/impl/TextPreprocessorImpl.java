package de.codecave.demo.component.impl;

import de.codecave.demo.component.TextPreprocessor;
import org.springframework.stereotype.Component;

@Component
public class TextPreprocessorImpl implements TextPreprocessor {

    /*
    STOPWORDS

['i', 'me', 'my', 'myself', 'we', 'our', 'ours', 'ourselves', 'you', "you're",
"you've", "you'll", "you'd", 'your', 'yours', 'yourself', 'yourselves', 'he',
'him', 'his', 'himself', 'she', "she's", 'her', 'hers', 'herself',
'it', "it's", 'its', 'itself', 'they', 'them', 'their', 'theirs', 'themselves',
'what', 'which', 'who', 'whom', 'this', 'that', "that'll", 'these', 'those', 'am',
'is', 'are', 'was', 'were', 'be', 'been', 'being', 'have', 'has', 'had', 'having',
'do', 'does', 'did', 'doing', 'a', 'an', 'the', 'and', 'but', 'if', 'or', 'because',
'as', 'until', 'while', 'of', 'at', 'by', 'for', 'with', 'about', 'against',
'between', 'into', 'through', 'during', 'before', 'after', 'above', 'below', 'to',
'from', 'up', 'down', 'in', 'out', 'on', 'off', 'over', 'under', 'again', 'further',
'then', 'once', 'here', 'there', 'when', 'where', 'why', 'how', 'all', 'any', 'both',
'each', 'few', 'more', 'most', 'other', 'some', 'such', 'no', 'nor', 'not', 'only', 'own',
'same', 'so', 'than', 'too', 'very', 's', 't', 'can', 'will', 'just', 'don', "don't", 'should',
 "should've", 'now', 'd', 'll', 'm', 'o', 're', 've', 'y', 'ain', 'aren', "aren't", 'couldn',
 "couldn't", 'didn', "didn't", 'doesn', "doesn't", 'hadn', "hadn't", 'hasn', "hasn't", 'haven',
 "haven't", 'isn', "isn't", 'ma', 'mightn', "mightn't", 'mustn', "mustn't", 'needn', "needn't",
 'shan', "shan't", 'shouldn', "shouldn't", 'wasn', "wasn't", 'weren', "weren't", 'won', "won't",
  'wouldn', "wouldn't"]
     */

    private static final int PADDING = 20;


    @Override
    public String cleanText(String text) {
/*
def clean_text(x, stemming=False, lemmatization=True):
    """
    clean text x
    :param x: List of sentences/strings to be tokenized
    :return: Tuple of cleaned data without stopwords, to lower, etc.
    """
    def remove_punctuation(word):
        word = ''.join([char for char in word if char not in string.punctuation])
        return word

    stemmer = PorterStemmer()
    lemmatizer = WordNetLemmatizer()

    cleaned = []
    for text in x:
        tokens = text.split()
        tokens = [tok.lower().strip() for tok in tokens]

        # Remove punctuation
        tokens = [remove_punctuation(tok) for tok in tokens]

        # Remove numbers
        tokens = ['#num#' if tok.isnumeric() else tok for tok in tokens]

        # Remove Stopwords
        tokens = [tok for tok in tokens if tok not in stop_words and tok]

        # Stemming
        if stemming:
            tokens = [stemmer.stem(tok) for tok in tokens]

        if lemmatization:
            tokens = [lemmatizer.lemmatize(tok) for tok in tokens]

        tokens = ' '.join(tokens)
        cleaned.append(tokens)

    return cleaned
  */


        return null;
    }
}
