package com.vocabpocker.dao;

import org.springframework.stereotype.Repository;

import com.vocabpocker.model.WordGroup;

@Repository("wordGroupDao")
public class WordGroupDaoImpl extends BaseDaoImpl<WordGroup> implements WordGroupDao {

}
