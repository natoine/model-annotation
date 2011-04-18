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

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "JUDGMENT", 
		uniqueConstraints=@UniqueConstraint(columnNames = {"LABEL"}))
@XmlRootElement
public class Judgment extends Tag
{
	public String toRDF(String _url_rdf_resource, String _rdf_toinsert)
	{
		String _rdf = "<jeu2debat:Judgment rdf:about=\""+ _url_rdf_resource +"?id=" + getId() +"\" >" ;
		_rdf = _rdf.concat("<moat:name><![CDATA[" + getLabel() + "]]></moat:name>");
		_rdf = _rdf.concat("<rdfs:label>" + getRDFLabel() + "</rdfs:label>");
		if(! _url_rdf_resource.equalsIgnoreCase(getRepresentsResource().getEffectiveURI()))
			_rdf = _rdf.concat("<rdfs:seeAlso rdf:resource=\"" + getRepresentsResource().getEffectiveURI() + "\" />");
		_rdf = _rdf.concat("<dcterms:created>" + getCreation() + "</dcterms:created>");
		if(_rdf_toinsert != null && _rdf_toinsert.length() > 0) _rdf = _rdf.concat(_rdf_toinsert);
		_rdf = _rdf.concat("</jeu2debat:Judgment>");
		return _rdf;
	}
	
	public String toSeeAlso(String _url_rdf_resource)
	{
		String rdf = "<jeu2debat:Judgment rdf:about=\""+ _url_rdf_resource +"?id=" + getId() +"\" >" ;
		if(! _url_rdf_resource.equalsIgnoreCase(getRepresentsResource().getEffectiveURI()))
			rdf = rdf.concat("<rdfs:seeAlso rdf:resource=\"" + getRepresentsResource().getEffectiveURI() + "\" />");
		rdf = rdf.concat("<rdfs:seeAlso rdf:resource=\"").concat(_url_rdf_resource).concat("?id=").concat(getId().toString()).concat("\" />");
		rdf = rdf.concat("</jeu2debat:Judgment>");
		return rdf ;
	}
	
	public List<String> rdfHeader()
	{
		ArrayList<String> _nms = new ArrayList<String>();
		_nms.add("xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"");
		_nms.add("xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"");
		_nms.add("xmlns:dcterms=\"http://purl.org/dc/terms/\"");
		_nms.add("xmlns:moat=\"http://moat-project.org/ns\"");
		_nms.add("xmlns:annotea=\"http://www.w3.org/2000/10/annotation-ns\"");
		return _nms ;
	}
}
