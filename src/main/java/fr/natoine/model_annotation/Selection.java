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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import fr.natoine.model_resource.Resource;

@Entity
@Table(name = "SELECTION")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@XmlRootElement
public abstract class Selection extends Resource
{
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Resource.class)
	@JoinColumn(name = "SELECTIONORIGINRESOURCE_ID" , nullable = false)
	private Resource selectionOrigin;

	/**
	 * Gets the Resource on wich the selection is done.
	 * @return
	 */
	public Resource getSelectionOrigin() 
	{
		return selectionOrigin;
	}
	/**
	 * Sets the Resource on wich the selection is done.
	 * @param selectionOrigin
	 */
	public void setSelectionOrigin(Resource selectionOrigin) {
		this.selectionOrigin = selectionOrigin;
	}
	
	public String toHTML()
	{
		String _html =
			"<div class=resource_header>" + 
			"<span class=resource_type>" + getClass().getSimpleName() + "</span> : <a href=" + getRepresentsResource().getEffectiveURI() + "?id=" + getId() + ">" + getLabel() + "</a>" + 
			" <span class=selection_origin>Cette sélection est un fragment de : <a href=" + getSelectionOrigin().getRepresentsResource().getEffectiveURI() + ">" + getSelectionOrigin().getLabel() + " </a></span>" + 
			"</div>";
		return _html;
	}
	
	public String toHTMLMax()
	{
		String _html =
			"<div class=resource_header>" + 
			"<span class=resource_type>" + getClass().getSimpleName() + "</span> : <a href=" + getRepresentsResource().getEffectiveURI() + "?id=" + getId() + ">" + getLabel() + "</a>" + 
			" <span class=selection_origin>Cette sélection est un fragment de : <a href=" + getSelectionOrigin().getRepresentsResource().getEffectiveURI() + ">" + getSelectionOrigin().getLabel() + " </a></span>" + 
			"</div>"
			+ "<div class=creation>Créée le : " + getCreation() + " via : " + getContextCreation() + "</div>" ;
		return _html;
	}
	public String toRDF(String _url_rdf_resource, String _rdf_toinsert)
	{	
		String _rdf = "<nicetag:PartOfWebRepresentation rdf:about=\""+ _url_rdf_resource +"?id=" + this.getId() +"\" >" ;
		if( this.getLabel()!=null) _rdf = _rdf.concat("<rdfs:label>"+ this.getLabel() +"</rdfs:label>");
		if(! _url_rdf_resource.equalsIgnoreCase(getRepresentsResource().getEffectiveURI()))
			_rdf = _rdf.concat("<rdfs:seeAlso rdf:resource=\"" + getRepresentsResource().getEffectiveURI() + "\" />");
		_rdf = _rdf.concat("<dcterms:created>" + this.getCreation() + "</dcterms:created>");
		if(_rdf_toinsert != null && _rdf_toinsert.length() > 0) _rdf = _rdf.concat(_rdf_toinsert);
		_rdf = _rdf.concat("</nicetag:PartOfWebRepresentation>");
		return _rdf;
	}
	
	public String toSeeAlso(String _url_rdf_resource)
	{
		String rdf = "<nicetag:PartOfWebRepresentation rdf:about=\""+ _url_rdf_resource +"?id=" + getId() +"\" >" ;
		if(! _url_rdf_resource.equalsIgnoreCase(getRepresentsResource().getEffectiveURI()))
			rdf = rdf.concat("<rdfs:seeAlso rdf:resource=\"" + getRepresentsResource().getEffectiveURI() + "\" />");
		rdf = rdf.concat("<rdfs:seeAlso rdf:resource=\"").concat(_url_rdf_resource).concat("?id=").concat(getId().toString()).concat("\" />");
		rdf = rdf.concat("</nicetag:PartOfWebRepresentation>");
		return rdf ;
	}
	
	public List<String> rdfHeader()
	{
		ArrayList<String> _nms = new ArrayList<String>();
		_nms.add("xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"");
		_nms.add("xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"");
		_nms.add("xmlns:dcterms=\"http://purl.org/dc/terms/\"");
		//_nms.add("xmlns:content=\"http://purl.org/rss/1.0/modules/content/\"");
		_nms.add("xmlns:nicetag=\"http://ns.inria.fr/nicetag/2009/09/25/voc\"");//http://ns.inria.fr/nicetag/2009/09/25/voc.rdf
		//_nms.add("xmlns:irw=\"http://ontologydesignpatterns.org/ont/web/irw.owl\"");
		_nms.add("xmlns:annotea=\"http://www.w3.org/2000/10/annotation-ns\"");
		return _nms ;
	}
}