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

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "DEFINITION")
@XmlRootElement
public class Definition extends Post
{
	public String toRDF(String _url_rdf_resource , String _rdf_toinsert)
	{
		String _rdf ="";
		if(getStatus() != null) 
			_rdf = "<jeu2debat:" + getStatus().getRDFLabel() +" rdf:about=\""+ _url_rdf_resource +"?id=" + getId() +"\" >" ;
		else
			_rdf = "<jeu2debat:Definition rdf:about=\""+ _url_rdf_resource +"?id=" + getId() +"\" >" ;
		if(getLabel()!= null)_rdf = _rdf.concat("<dc:title>"+ getLabel() +"</dc:title>");
		if(! _url_rdf_resource.equalsIgnoreCase(getRepresentsResource().getEffectiveURI()))
			_rdf = _rdf.concat("<rdfs:seeAlso rdf:resource=\"" + getRepresentsResource().getEffectiveURI() + "\" />");
		_rdf = _rdf.concat("<dcterms:created>" + getCreation() + "</dcterms:created>");
		_rdf = _rdf.concat("<sioc:content><![CDATA[" + getContent() + "]]></sioc:content>");
		if(_rdf_toinsert != null && _rdf_toinsert.length() > 0) _rdf = _rdf.concat(_rdf_toinsert);
		if(getStatus() != null)
			_rdf = _rdf.concat("</jeu2debat:" + getStatus().getRDFLabel() +">");
		else
			_rdf = _rdf.concat("</jeu2debat:Definition>");
		return _rdf;
	}
	
	public String toRDF(String _url_rdf_resource, String _url_rdf_agent, String _rdf_toinsert)
	{
		String _rdf ="";
		if(getStatus() != null) 
			_rdf = "<jeu2debat:" + getStatus().getRDFLabel() +" rdf:about=\""+ _url_rdf_resource +"?id=" + getId() +"\" >" ;
		else
			_rdf = "<jeu2debat:Definition rdf:about=\""+ _url_rdf_resource +"?id=" + getId() +"\" >" ;
		if(getLabel()!= null)_rdf = _rdf.concat("<dc:title>"+ getLabel() +"</dc:title>");
		if(! _url_rdf_resource.equalsIgnoreCase(getRepresentsResource().getEffectiveURI()))
			_rdf = _rdf.concat("<rdfs:seeAlso rdf:resource=\"" + getRepresentsResource().getEffectiveURI() + "\" />");
		_rdf = _rdf.concat("<dcterms:created>" + getCreation() + "</dcterms:created>");
		_rdf = _rdf.concat("<sioc:content><![CDATA[" + getContent() + "]]></sioc:content>");
		if(getAuthor() != null)
		{
			_rdf.concat("<sioc:has_creator>");
			_rdf.concat(getAuthor().toRDF(_url_rdf_agent , ""));
			_rdf.concat("</sioc:has_creator>");
		}
		if(_rdf_toinsert != null && _rdf_toinsert.length() > 0) _rdf = _rdf.concat(_rdf_toinsert);
		if(getStatus() != null)
			_rdf = _rdf.concat("</jeu2debat:" + getStatus().getRDFLabel() +">");
		else
			_rdf = _rdf.concat("</jeu2debat:Definition>");
		return _rdf;
	}
	
	public String toSeeAlso(String _url_rdf_resource)
	{
		String rdf = "";
		if(getStatus() != null) 
			rdf = "<jeu2debat:" + getStatus().getRDFLabel() +" rdf:about=\""+ _url_rdf_resource +"?id=" + getId() +"\" >" ;
		else
			rdf = "<jeu2debat:Definition rdf:about=\""+ _url_rdf_resource +"?id=" + getId() +"\" >" ;
		if(! _url_rdf_resource.equalsIgnoreCase(getRepresentsResource().getEffectiveURI()))
			rdf = rdf.concat("<rdfs:seeAlso rdf:resource=\"" + getRepresentsResource().getEffectiveURI() + "\" />");
		rdf = rdf.concat("<rdfs:seeAlso rdf:resource=\"").concat(_url_rdf_resource).concat("?id=").concat(getId().toString()).concat("\" />");
		if(getStatus() != null)
			rdf = rdf.concat("</jeu2debat:" + getStatus().getRDFLabel() +">");
		else
			rdf = rdf.concat("</jeu2debat:Definition>");
		return rdf ;
	}
}
