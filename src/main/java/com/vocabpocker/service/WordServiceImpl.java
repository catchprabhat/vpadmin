package com.vocabpocker.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vocabpocker.dao.WordDao;
import com.vocabpocker.dto.WordWrapper;
import com.vocabpocker.model.Word;
import com.vocabpocker.util.AppUtils;

@Service("wordService")
@Transactional
public class WordServiceImpl implements WordService
{

    private final static Logger LOG = LogManager.getLogger(WordServiceImpl.class);

    @Autowired
    private WordDao dao;

    @Override
    public Word findById(final Long id)
    {
        return dao.findById(id);
    }

    @Override
    public void saveOrUpdate(final Word t)
    {
        if (null != t.getId())
        {
            final Word savedEntity = findById(t.getId());
            savedEntity.setActive(t.getActive());
            savedEntity.setHint(t.getHint());
            savedEntity.setMeaning(t.getMeaning());
            savedEntity.setName(t.getName());
            savedEntity.setSentence(t.getSentence());
            savedEntity.setPronunication(t.getPronunication());
            savedEntity.setType(t.getType());
            savedEntity.setWordGroup(t.getWordGroup());
            dao.update(t);
        }
        else
        {
            dao.persist(t);
        }
    }

    @Override
    public void deleteById(final Long id)
    {
        dao.deleteById(id);
    }

    @Override
    public Set<Word> findAll()
    {
        return dao.findAll();
    }

    @Override
    public Set<Word> findAllActive()
    {
        return dao.findAllActive();
    }

    @Override
    public boolean isActive(final Long id)
    {
        return dao.isActive(id);
    }

    @Override
    public Map<Long, Word> findByIds(final Collection<Long> ids)
    {
        final Map<Long, Word> wordsMap = new HashMap<>();
        final List<Word> words = dao.findByIds(ids);

        if (!words.isEmpty())
        {
            for (final Word word : words)
            {
                wordsMap.put(word.getId(), word);
            }
        }
        return wordsMap;
    }

    @Override
    public WordWrapper toWordWrapper(final Word word)
    {
        final WordWrapper wordWrapper = new WordWrapper();
        if (null != word)
        {
            wordWrapper.setId(word.getId());
            wordWrapper.setActive(word.getActive());
            wordWrapper.setHint(word.getHint());
            wordWrapper.setMeaning(word.getMeaning());
            wordWrapper.setName(word.getName());
            wordWrapper.setSentence(word.getSentence());
            wordWrapper.setType(word.getType());
            wordWrapper.setGroupId(word.getWordGroup().getId());
            wordWrapper.setGroupName(word.getWordGroup().getName());
            wordWrapper.setSynonym(this.getSynonyms(word.getId(), word.getWordGroup().getId()).toString());
            wordWrapper.setPronunication(word.getPronunication());
        }
        return wordWrapper;
    }

    @Override
    public Set<WordWrapper> toWordWrapper(final Set<Word> words)
    {
        final Set<WordWrapper> wrapperList = new LinkedHashSet<>();
        if (null != words && !words.isEmpty())
        {
            for (final Word word : words)
            {
                wrapperList.add(toWordWrapper(word));
            }
        }
        return wrapperList;
    }

    @Override
    public Set<Word> getAllWordsByGroupIds(final Long[] wordGroupIds)
    {
        final Set<Word> words = dao.getAllWordsByGroupIds(wordGroupIds);
        return words;
    }

    @Override
    public Word findByWordName(final String name)
    {
        return dao.findByWordName(name);
    }

    @Override
    public List<Word> getRandomWords(final int numberOfRandomWords)
    {
        return dao.getRandomWords(numberOfRandomWords);
    }

    @Override
    public List<Word> getSynonyms(final Long wordId, final Long wordGroupId)
    {
        final List<Word> synonyms = dao.getSynonyms(wordId, wordGroupId);
        LOG.info("synonyms size = {}", synonyms.size());
        return synonyms;
    }

    @Override
    public Word getRandomSynonym(final Long wordId, final Long wordGroupId)
    {
        LOG.info("getRandomSynonym: wordId ={}, wordGroupId= {}", wordId, wordGroupId);
        final List<Word> synonyms = this.getSynonyms(wordId, wordGroupId);
        LOG.info("synonyms list size ={}", synonyms.size());

        int randomIndex = 0;
        if (synonyms.size() > 1)
        {
            randomIndex = AppUtils.getRandomNumberInRange(0, synonyms.size() - 1);
        }
        LOG.info("randomIndex ={}", randomIndex);
        if (synonyms.size() >= randomIndex)
        {
            return synonyms.get(randomIndex);
        }
        return null;
    }

    @Override
    public List<Word> getRandomWordsForMultiPlayer(final int numberOfRandomWords, final int multiPlayerLevel)
    {
        return dao.getRandomWordsForMultiPlayer(numberOfRandomWords, multiPlayerLevel);
    }
}
