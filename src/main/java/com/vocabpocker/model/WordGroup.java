package com.vocabpocker.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "word_group", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
@Access(AccessType.FIELD)
public class WordGroup extends BaseEntity
{

    private static final long serialVersionUID = -5174961637031724399L;

    @Size(max = 10)
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "wordGroup")
    private Set<Word> words = new HashSet<>(0);

    @Column(name = "multiPlayerLevel")
    private int multiPlayerLevel = 0;

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    @JsonIgnore
    public Set<Word> getWords()
    {
        return words;
    }

    public void setWords(final Set<Word> words)
    {
        this.words = words;
    }

    public int getMultiPlayerLevel()
    {
        return multiPlayerLevel;
    }

    public void setMultiPlayerLevel(final int multiPlayerLevel)
    {
        this.multiPlayerLevel = multiPlayerLevel;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        final WordGroup other = (WordGroup) obj;
        if (name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "WordGroup [id=" + id + ", name=" + name + ", multiPlayerLevel=" + multiPlayerLevel + "]";
    }

}
