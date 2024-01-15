package com.vocabpocker.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

@Entity
@Table(name = "word", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
@Access(AccessType.FIELD)
public class Word extends BaseEntity
{

    private static final long serialVersionUID = -4903367856465260853L;

    @Size(max = 45)
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 100)
    @Column(name = "hint", nullable = false)
    private String hint;

    @Size(max = 15)
    @Column(name = "type", nullable = false)
    private String type;

    @Size(max = 300)
    @Column(name = "meaning", nullable = false)
    private String meaning;

    @Size(max = 400)
    @Column(name = "sentence", nullable = false)
    private String sentence;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "word_group_id", nullable = false)
    private WordGroup wordGroup;
    
	@Size(max = 300)
    @Column(name = "pronunication", nullable = true)
    private String pronunication;

    //    @Size(max = 400)
    //    @Column(name = "synonymes", nullable = false)
    //    private String synonymes;

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public String getHint()
    {
        return hint;
    }

    public void setHint(final String hint)
    {
        this.hint = hint;
    }

    public String getType()
    {
        return type;
    }

    public void setType(final String type)
    {
        this.type = type;
    }

    public String getMeaning()
    {
        return meaning;
    }

    public void setMeaning(final String meaning)
    {
        this.meaning = meaning;
    }

    public String getSentence()
    {
        return sentence;
    }

    public void setSentence(final String sentence)
    {
        this.sentence = sentence;
    }

    public WordGroup getWordGroup()
    {
        return wordGroup;
    }

    public void setWordGroup(final WordGroup wordGroup)
    {
        this.wordGroup = wordGroup;
    }
    

    public String getPronunication() {
		return pronunication;
	}

	public void setPronunication(String pronunication) {
		this.pronunication = pronunication;
	}


    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        final Word other = (Word) obj;
        if (name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (type == null)
        {
            if (other.type != null)
                return false;
        }
        else if (!type.equals(other.type))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "Word [name="
                + name
                + ", hint="
                + hint
                + ", type="
                + type
                + ", meaning="
                + meaning
                + ", sentence="
                + sentence
                + ", prounce="
                + pronunication
                + /* ", wordGroup=" + wordGroup + */ "]";
    }
}
