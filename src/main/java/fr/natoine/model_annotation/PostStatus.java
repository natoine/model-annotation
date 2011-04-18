/*
 * Copyright 2010 Antoine Seilles (Natoine)
 *   This file is part of model-annotation.

    model-annotation is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    model-annotation is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with model-annotation.  If not, see <http://www.gnu.org/licenses/>.

 */
package fr.natoine.model_annotation;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import fr.natoine.model_resource.RDFexportable;

@Entity
@Table(name = "POSTSTATUS")
@XmlRootElement
public class PostStatus implements Serializable
{
	@Id @GeneratedValue
    @Column(name = "POSTSTATUS_ID")
	private Long id;
	
	@Column(name = "LABEL" , unique=true, nullable=false)
	private String label;
	
	@Column(name = "COMMENT")
	private String comment;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = PostStatus.class)
	@JoinColumn(name = "FATHERPOSTSTATUS_ID")
	private PostStatus father;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public PostStatus getFather() {
		return father;
	}

	public void setFather(PostStatus father) {
		this.father = father;
	}

	public Long getId() {
		return id;
	}

	public String getRDFLabel()
	{
		String _labelRDF = this.label.replaceAll(" ", "");
		_labelRDF = _labelRDF.replaceAll("'", "");
		_labelRDF = _labelRDF.replaceAll("é", "e");
		_labelRDF = _labelRDF.replaceAll("è", "e");
		_labelRDF = _labelRDF.replaceAll("ê", "e");
		_labelRDF = _labelRDF.replaceAll("ë", "e");
		_labelRDF = _labelRDF.replaceAll("à", "a");
		_labelRDF = _labelRDF.substring(0,1).toUpperCase() + _labelRDF.substring(1 , _labelRDF.length());
		return _labelRDF ;
	}
	
	public String toRDF(String _urlRDF_voc)
	{
		String _rdf = "<owl:Class rdf:about=\"" + _urlRDF_voc + "#" + getRDFLabel() + "\">".concat("<rdfs:label xml:lang=\"fr\">" + label + "</rdfs:label>")
			.concat("<rdfs:comment xml:lang=\"fr\">"+ comment + "</rdfs:comment>")
			.concat("<rdfs:subClassOf rdf:resource=\"http://rdfs.org/sioc/ns#Post\"/>")
			.concat("<rdfs:isDefinedBy rdf:resource=\"" + _urlRDF_voc + "#\"/>")
			.concat("</owl:Class>") ;
		//System.out.println("[PostStatus.toRDF] rdf : " + _rdf);
		return _rdf;
	}
}
