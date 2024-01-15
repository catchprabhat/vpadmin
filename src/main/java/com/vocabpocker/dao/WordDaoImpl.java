package com.vocabpocker.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.vocabpocker.model.Word;

@Repository("wordDao")
public class WordDaoImpl extends BaseDaoImpl<Word> implements WordDao
{

    private final static Logger LOG = LogManager.getLogger(WordDaoImpl.class);

    @SuppressWarnings("unchecked")
    @Override
    public Set<Word> getSynonymWords(final long wordGroupId)
    {
        final Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("wordGroup.id", wordGroupId));
        return new HashSet<>(criteria.list());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Word> getAllWordsByGroup(final long wordGroupId)
    {
        final Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("wordGroup.id", wordGroupId));
        return new HashSet<>(criteria.list());
    }

    @SuppressWarnings("unchecked")
    @Override
    public Set<Word> getAllWordsByGroupIds(final Long[] wordGroupIds)
    {
        final Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.in("wordGroup.id", wordGroupIds));
        return new HashSet<>(criteria.list());
    }

    @Override
    public Word findByWordName(final String name)
    {
        final Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("name", name));
        return (Word) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Word> getRandomWords(final int numberOfRandomWords)
    {
        //SELECT w.* FROM vocabpocker.word w ORDER BY RAND() LIMIT 6;
        //SELECT w.* FROM vocabpocker.word w WHERE RAND()<=0.01 limit 6;
        //6 rows from 600 total rows = 6/600 = 0.01

        final Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.sqlRestriction("1=1 order by rand()"));
        //        criteria.add(Restrictions.sqlRestriction(" RAND()<=0.01"));
        criteria.setMaxResults(numberOfRandomWords);

        final List<Word> words = criteria.list();
        LOG.info("getRandomWords() :: words size = {}", words.size());
        return words;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Word> getSynonyms(final Long wordId, final Long wordGroupId)
    {

        final Criteria criteria = createEntityCriteria();
        //criteria.add(Restrictions.eq("id", wordId));
        criteria.add(Restrictions.eq("wordGroup.id", wordGroupId));
        final List<Word> synonyms = criteria.list();
        LOG.info("synonyms list size ={}", synonyms.size());
        return synonyms;
    }

    //    @Override
    //    public Word getRandomSynonym(final Long wordId, final Long wordGroupId)
    //    {
    //
    //        final Criteria criteria = createEntityCriteria();
    //        criteria.add(Restrictions.eq("id", wordId));
    //        criteria.add(Restrictions.eq("wordGroup.id", wordGroupId));
    //        criteria.setMaxResults(1);
    //        final List<Word> synonyms = criteria.list();
    //        LOG.info("synonyms list size ={}", synonyms.size());
    //        final int randomIndex = AppUtils.getRandomNumberInRange(0, synonyms.size() - 1);
    //        return synonyms.get(randomIndex);
    //    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Word> getRandomWordsForMultiPlayer(final int numberOfRandomWords, final int multiPlayerLevel)
    {
        LOG.info(
                "getRandomWordsForMultiPlayer() :: multiPlayerLevel = {} , numberOfRandomWords ={}",
                multiPlayerLevel,
                numberOfRandomWords);

        //        select word.*
        //        -- , word.word_group_id, wordgrp.id, wordgrp.active, wordgrp.multiPlayerLevel, wordgrp.name 
        //        from word word 
        //        inner join word_group wordgrp on word.word_group_id=wordgrp.id 
        //        -- where 1=1 order by rand() limit 10
        //         where wordgrp.multiPlayerLevel=1 order by rand() limit 10
        //        ;

        final Session session = getSession();

        final StringBuilder hqlUpdateStrBuilder = new StringBuilder()
                .append("select word.* from word word inner join word_group wordgrp on word.word_group_id=wordgrp.id ")
                .append(" where wordgrp.multiPlayerLevel = :multiPlayerLevel order by rand() limit :numberOfRandomWords");

        final String hqlQuery = hqlUpdateStrBuilder.toString();
        LOG.info("getRandomWordsForMultiPlayer query={}", hqlQuery);

        final List<Word> randomWordsForMultiPlayer = session.createSQLQuery(hqlQuery).addEntity(Word.class)
                .setInteger("numberOfRandomWords", numberOfRandomWords)
                .setInteger("multiPlayerLevel", multiPlayerLevel)
                .list();
        LOG.info("getRandomWordsForMultiPlayer randomWordsForMultiPlayer = {}", randomWordsForMultiPlayer);
        return randomWordsForMultiPlayer;
    }
}
