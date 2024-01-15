package com.vocabpocker.dao;

import java.util.List;
import java.util.Set;

import com.vocabpocker.model.Word;

public interface WordDao extends BaseDao<Word>
{

    Set<Word> getSynonymWords(long wordGroupId);

    Set<Word> getAllWordsByGroup(long wordGroupId);

    Set<Word> getAllWordsByGroupIds(Long[] wordGroupIds);

    Word findByWordName(String name);

    List<Word> getRandomWords(int numberOfRandomWords);

    List<Word> getSynonyms(Long wordId, Long wordGroupId);

    //    Word getRandomSynonym(Long wordId, Long wordGroupId);

    List<Word> getRandomWordsForMultiPlayer(final int numberOfRandomWords, final int level);

}
