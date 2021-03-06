/* @filename $RCSfile: GermanTextProcessing.java,v $ */

package de.citytwin;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
// import org.apache.lucene.analysis.Analyzer;
// import org.apache.lucene.analysis.TokenStream;
// import org.apache.lucene.analysis.custom.CustomAnalyzer;
// import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.stemmer.snowball.SnowballStemmer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

/**
 * @author Maik, FH Erfurt
 * @version $Revision: 1.0 $
 * @since CityTwin_KeyWord_Extraction_ProtoType 1.0
 */
public class GermanTextProcessing {

    private static boolean isInitialzied = false;

    /** Klassenspezifischer, aktueller Logger (Server: org.apache.log4j.Logger; Client: java.util.logging.Logger) */
    private static transient final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    /** Aktuelle Versionsinformation */
    private static final String VERSION = "$Revision: 1.00 $";
    private static POSTaggerME posTagger = null;
    private static SentenceDetectorME sentenceDetector = null;
    private static Set<String> stopwords = new HashSet<String>();
    private static Tokenizer tokenizer = null;
    private static SnowballStemmer snowballStemmer = null;

    public GermanTextProcessing() throws IOException {

        if (!isInitialzied) {
            initialize();
        }
    }

    // @Override
    // public void close() throws Exception {
    // isInitialzied = false;
    // posTagger = null;
    // sentenceDetector = null;
    // snowballStemmer = null;
    // stopwords.clear();
    // stopwords = null;
    // tokenizer = null;
    //
    // }

    /**
     * This count a specific char in a sentence,
     * <p>
     *
     * @param sentence
     * @return {@code int}
     */
    public int countChar(String sentence, char charToCount) {
        char[] chars = sentence.toCharArray();
        int count = 0;
        for (char ch : chars) {
            if (ch == charToCount) {
                count++;
            }
        }
        return count;
    }

    /**
     * this mehtod count digits in a sentence
     *
     * @param sentence
     * @return
     */
    public int countDigits(String sentence) {
        char chars[] = sentence.toCharArray();
        int result = 0;
        for (char ch : chars) {
            if (Character.isDigit(ch)) {
                result++;
            }
        }
        return result;
    }

    /**
     * This count new lines in a sentence,
     * <p>
     *
     * @param sentence
     * @return {@code int}
     */
    public int countNewLines(String sentence) {
        return countChar(sentence, '\n');
    }

    /**
     * this method keeps terms they tagged with necessary pos tag and included in pos-list
     *
     * @param termsWithPosTags
     * @param posTags {@link GermanTextProcessing#getPOSTags(List)}
     * @return new reference of {@code List<String>}
     */
    public List<String> filterByPosTags(List<Pair<String, String>> termsWithPosTags) {
        List<String> results = new ArrayList<String>();

        for (Pair<String, String> pair : termsWithPosTags) {
            for (String posFilter : Config.GERMAN_TEXT_PROCESSING_POSTAGS) {
                if (posFilter.equals(pair.getRight())) {
                    results.add(pair.getLeft());
                }
            }
        }
        logger.info("filter by pos tags completed. (terms keeping)");
        return results;
    }

    /**
     * This method removes terms they are in the stopword-list.
     * <p>
     * {@link GermanTextProcessing#stopwords}
     *
     * @param terms {@code List<String>}
     * @return new reference of {@code List<String>} filtered
     */
    public List<String> filterByStopWords(List<String> terms) {

        List<String> results = new ArrayList<String>();
        for (String term : terms) {
            if (!stopwords.contains(term.toLowerCase())) {
                results.add(term);
            }
        }
        logger.info("filter by stopwords completed. (terms removal)");
        return results;

    }

    /**
     * this method return pos tags of a german sentence. <br>
     * <strong> not stemmed or filtered by stopwords </strong>
     *
     * @param terms
     * @return new reference of {@code List<Pair<String, String>>} (term : postag)
     */
    public List<Pair<String, String>> getPOSTags(final List<String> terms) {
        List<Pair<String, String>> results = new ArrayList<Pair<String, String>>(terms.size());
        String[] strings = new String[terms.size()];
        strings = terms.toArray(strings);
        String[] tags = posTagger.tag(strings);
        int tagIndex = 0;
        for (String term : terms) {
            Pair<String, String> pair = Pair.of(term, tags[tagIndex++]);
            results.add(pair);
        }
        logger.info("pos tagging completed.");
        return results;
    }

    /**
     * this method return SentenceDetector
     *
     * @return static reference of {@code SentenceDetectorME}
     */
    public SentenceDetectorME getSentenceDetectorME() {
        return sentenceDetector;
    }

    /**
     * this method initialize nlp components
     *
     * @throws IOException
     */
    private void initialize() throws IOException {

        InputStream inputStream = new FileInputStream(Config.NLP_SENTENCE_DETECTOR);
        SentenceModel sentenceModel = new SentenceModel(inputStream);
        sentenceDetector = new SentenceDetectorME(sentenceModel);
        inputStream.close();

        inputStream = new FileInputStream(Config.NLP_POS_TAGGER);
        posTagger = new POSTaggerME(new POSModel(inputStream));
        inputStream.close();

        inputStream = new FileInputStream(Config.NLP_SENTENCE_TOKENIZER);
        TokenizerModel tokenizerModel = new TokenizerModel(inputStream);
        tokenizer = new TokenizerME(tokenizerModel);
        inputStream.close();

        inputStream = new FileInputStream(Config.STOPWORD_CATALOG);
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\r\\n");

        while (scanner.hasNext()) {
            stopwords.add(scanner.next());
        }
        inputStream.close();
        scanner.close();

        snowballStemmer = new SnowballStemmer(SnowballStemmer.ALGORITHM.GERMAN);

        isInitialzied = true;

    }

    /**
     * this method calculate the probability (calculate by dots, digits and blanks in sentence)
     *
     * @param sentence
     * @return {@code int} in percent
     */
    public int probabilityIsSentenceTabelofContent(String sentence) {

        int countDots = this.countChar(sentence, '.');
        int countDigits = this.countDigits(sentence);
        int countBlanks = this.countChar(sentence, ' ');
        float accurany = (float)countDots / (float)sentence.length() * 100.0f + (float)countDigits / (float)sentence.length() * 100.0f
                + (float)countBlanks / (float)sentence.length() * 100.0f;
        return (int)Math.ceil(accurany);

    }

    /**
     * this method stemmed a list of terms
     *
     * @param terms {@code List<String> terms}
     * @return new reference of {@code List<Pair<String, String>>} (term : stemmed term)
     */
    public List<Pair<String, String>> stemm(final List<String> terms) {

        List<Pair<String, String>> results = new ArrayList<Pair<String, String>>(terms.size());
        String stemmed = "";
        for (String term : terms) {
            stemmed = snowballStemmer.stem(term).toString();
            Pair<String, String> pair = Pair.of(term, stemmed);
            results.add(pair);
        }
        return results;

    }

    /**
     * This method split articles in each sentences by opennlp an article can contain more as one sentence
     *
     * @param {@code List<String> articles}
     * @return new reference{@code List<String>}
     */
    public List<String> tokenizeArticlesToSencences(final List<String> articles) {
        List<String> results = new ArrayList<String>();
        for (String atricle : articles) {
            String[] sentences = sentenceDetector.sentDetect(atricle);
            for (String sentence : sentences) {
                results.add(sentence);
            }
        }
        logger.info(MessageFormat.format("textcorpus contains {0} sentences.", results.size()));
        return results;
    }

    /**
     * This method split textcorpus in each senteces by opennlp
     * <p>
     * remove newline pattern {@code "-\n"} and
     * <p>
     * newline threshold is {@link GermanTextProcessing#maxNewLines}
     * <p>
     *
     * @param bodyContentHandler
     * @return {@code List<String>}
     * @throws IOException
     */
    public List<String> tokenizeBodyContentToSencences(BodyContentHandler bodyContentHandler) throws IOException {

        List<String> results = new ArrayList<String>();
        String[] sentences = sentenceDetector.sentDetect(bodyContentHandler.toString());
        String temp = "";
        for (String sentence : sentences) {
            temp = sentence.replaceAll("-\n", "");
            temp = sentence.replaceAll("\n", "");
            if (countNewLines(sentence) < Config.GERMAN_TEXT_PROCESSING_MAX_NEWLINES) {
                results.add(temp);
            }
        }
        logger.info(MessageFormat.format("textcorpus contains {0} sentences.", results.size()));
        return results;
    }

    /**
     * This method split a sentence in each term by apache opennlp works better with terms like "Bebauungsplan-Entwurf"
     *
     * @param sentence
     * @return {@code List<String>}
     */
    public List<String> tokenizeOpenNLP(final String sentence) {

        String temp = "";
        List<String> results = new ArrayList<String>();
        for (String term : tokenizer.tokenize(sentence)) {
            temp = term.trim().replaceAll(Config.GERMAN_TEXT_PROCESSING_CLEANING_PATTERN, "");
            temp = tryToRemoveHypen(temp);
            if (temp.length() >= Config.GERMAN_TEXT_PROCESSING_MIN_TERM_LENGTH) {
                results.add(temp);
            }

        }
        logger.info(MessageFormat.format("tokenize completed, sentence contains {0} terms", results.size()));
        return results;
    }

    /**
     * R&uuml;ckgabe der Klasseninformation.
     * <p>
     * Gibt den Klassennamen und die CVS Revisionsnummer zur&uuml;ck.
     * <p>
     *
     * @return Klasseninformation
     */
    @Override
    public String toString() {
        return this.getClass().getName() + " " + VERSION;
    }

    /**
     * this method try to remove unimportant information like headings, table of content and listing
     *
     * @param sentence
     * @param isOpenNLP
     * @param tableOfContentThreshold (Nullable, default 50)
     * @return new refernece of {@code List <String>}
     * @throws IOException
     */

    public List<String> tryToCleanSentence(String sentence) throws IOException {

        List<String> results = new ArrayList<String>();
        // List<String> terms = (isOpenNLP) ? tokenizeOpenNLP(sentence) : tokenizeLucene(sentence);
        List<String> terms = tokenizeOpenNLP(sentence);

        if (terms.size() <= Config.GERMAN_TEXT_PROCESSING_MIN_TERM_COUNT) {
            return results;

        }
        if (probabilityIsSentenceTabelofContent(sentence) > Config.GERMAN_TEXT_PROCESSING_TABLE_OF_CONTENT_THRESHOLD) {
            return results;
        }
        for (String term : terms) {
            String temp = tryToRemoveHypen(term);
            results.add(temp);
        }
        return results;

    }

    /**
     * this method try to remove hypen(-) in a term like <br>
     * fr??hr-er --> fr??hrer <br>
     * U-Bahn --> U-Bahn <br>
     * Robert-August-Sta??e --> Robert-August-Sta??e
     *
     * @param term
     * @return
     */
    public String tryToRemoveHypen(String term) {

        String[] parts = term.split("-");
        if (parts.length != 2) {
            return term;
        }

        char[] charArray = parts[1].toCharArray();
        if (Character.isLowerCase(charArray[0])) {
            return parts[0] + parts[1];
        }
        return term;
    }

}
