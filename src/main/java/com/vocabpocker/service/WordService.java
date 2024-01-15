package com.vocabpocker.service;

import java.util.List;
import java.util.Set;

import com.vocabpocker.dto.WordWrapper;
import com.vocabpocker.model.Word;

public interface WordService extends BaseService<Word>
{
    WordWrapper toWordWrapper(Word word);

    Set<WordWrapper> toWordWrapper(Set<Word> words);

    Set<Word> getAllWordsByGroupIds(Long[] wordGroupIds);

    Word findByWordName(final String name);

    List<Word> getRandomWords(final int numberOfRandomWords);

    List<Word> getSynonyms(Long wordId, Long wordGroupId);

    Word getRandomSynonym(Long wordId, Long wordGroupId);

    List<Word> getRandomWordsForMultiPlayer(final int numberOfRandomWords, final int multiPlayerLevel);
}
