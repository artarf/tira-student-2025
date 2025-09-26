package oy.interact.tira.task_08_09;

import oy.interact.tira.util.Pair;

/**
 * Really bad book. Uses linear arrays with O(n^2) loops within loops,
 * bubble sort etc.
 */
class BadBookImplementation extends BookBase {

    private class WordCount {
        WordCount() {
            word = "";
            count = 0;
        }

        String word;
        int count;
    }

    private static final int MAX_WORDS = 100_000;
    private WordCount[] words = null;
    private int uniqueWordCount = 0;

    @Override
    protected void createDataStructure() {
        words = new WordCount[MAX_WORDS];
        uniqueWordCount = 0;
    }

    @Override
    protected void insert(String word) {
        boolean handled = false;
        // Go through the words found so far...
        // NB: ...the !handled in evaluating if loop should be continued...
        for (int index = 0; index < words.length && !handled; index++) {
            // If we meet an empty word, this word was not found so far so add it then.
            if (words[index] == null) {
                WordCount newWord = new WordCount();
                newWord.word = word;
                newWord.count = 1;
                words[index] = newWord;
                uniqueWordCount += 1;
                handled = true;
                // If array is full, then abort after cleaning up.
                if (uniqueWordCount >= MAX_WORDS) {
                    throw new OutOfMemoryError("No room for more words in array");
                }
            } else if (words[index].word.equals(word)) {
                // Word was found so update the counts, flag.
                words[index].count += 1;
                handled = true;
            }
        }
        // If word was not added or an existing count updated, array is full.
        if (!handled) {
            throw new OutOfMemoryError("No room for more words in array");
        }
    }

    @Override
    protected int getWordCount(String word) {
        for (final WordCount wordCount : words) {
            if (wordCount == null) {
                return -1;
            }
            if (wordCount.word.equals(word)) {
                return wordCount.count;
            }
        }
        return -1;
    }

    @Override
    protected int uniqueWordCount() {
        return this.uniqueWordCount;
    }

    @Override
    protected Pair<String, Integer>[] getAllWordCounts() {
        @SuppressWarnings("unchecked")
        Pair<String, Integer>[] array = new Pair[uniqueWordCount];
        int index = 0;
        for (final WordCount wordCount : words) {
            if (wordCount == null) {
                break;
            }
            array[index++] = new Pair<>(wordCount.word, wordCount.count);
        }
        return array;
    }

    @Override
    protected void resetDataStructure() {
        words = null;
        this.uniqueWordCount = 0;
    }

    @Override
    public String getImplementationName() {
        return "BadBookImplementation";
    }

}
