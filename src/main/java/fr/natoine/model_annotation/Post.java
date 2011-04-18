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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import fr.natoine.model_resource.Resource;
import fr.natoine.model_user.Agent;

@Entity
@Table(name = "POST")
@XmlRootElement
public class Post extends Resource 
{
	@Lob
	@Column(name = "CONTENT" , nullable=false)
	private String content ;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = PostStatus.class)
	@JoinColumn(name = "STATUS_ID" , nullable = false)
	private PostStatus status ;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Agent.class)
	@JoinColumn(name = "AUTHOR_ID" , nullable = true)
	private Agent author ;
	
	/**
	 * Gets the content of a Post
	 * @return
	 */
	public String getContent() {
		return content;
	}
	/**
	 * Sets the content of a Post
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * Gets the status of a simpleText
	 * @return
	 */
	public PostStatus getStatus() {
		return status;
	}
	/**
	 * Sets the status of a simpleText
	 * @param status
	 */
	public void setStatus(PostStatus status) {
		this.status = status;
	}
	
	public String toHTML()
	{
		String _html =
			"<div class=resource_header>" + "<a href=" + getRepresentsResource().getEffectiveURI() + "?id=" + getId() + ">" + getLabel() + "</a></div>";
		_html = _html.concat("<div class=content><span class=status title=\"" + getStatus().getComment() + "\" >" + getStatus().getLabel() + " : </span>" + getContent() + "</div>");
		return _html;
	}
	
	public String toHTMLMax()
	{
		String _html =
			"<div class=resource_header>" + "<a href=" + getRepresentsResource().getEffectiveURI() + "?id=" + getId() + ">" + getLabel() + "</a></div>"
			+ "<div class=creation>Autheur : <strong>" + author.getLabel() + "</strong> Créée le : " + getCreation() + " via : " + getContextCreation() + "</div>" ;
		_html = _html.concat("<div class=content><span class=status title=\"" + getStatus().getComment() + "\" >" + getStatus().getLabel() + " : </span>" + getContent() + "</div>");
		return _html;
	}
	
	public String toRDF(String _url_rdf_resource, String _url_rdf_agent, String _rdf_toinsert)
	{
		String _rdf ="";
		if(status != null) 
			_rdf = "<jeu2debat:" + status.getRDFLabel() +" rdf:about=\""+ _url_rdf_resource +"?id=" + getId() +"\" >" ;
		else
			_rdf = "<sioc:Post rdf:about=\""+ _url_rdf_resource +"?id=" + getId() +"\" >" ;
		if(getLabel()!= null)_rdf = _rdf.concat("<dc:title>"+ getLabel() +"</dc:title>");
		if(! _url_rdf_resource.equalsIgnoreCase(getRepresentsResource().getEffectiveURI()))
			_rdf = _rdf.concat("<rdfs:seeAlso rdf:resource=\"" + getRepresentsResource().getEffectiveURI() + "\" />");
		_rdf = _rdf.concat("<dcterms:created>" + getCreation() + "</dcterms:created>");
		_rdf = _rdf.concat("<sioc:content><![CDATA[" + getContent() + "]]></sioc:content>");
		if(author != null)
		{
			_rdf.concat("<sioc:has_creator>");
			_rdf.concat(author.toRDF(_url_rdf_agent , ""));
			_rdf.concat("</sioc:has_creator>");
		}
		if(_rdf_toinsert != null && _rdf_toinsert.length() > 0) _rdf = _rdf.concat(_rdf_toinsert);
		if(status != null)
			_rdf = _rdf.concat("</jeu2debat:" + status.getRDFLabel() +">");
		else
			_rdf = _rdf.concat("</sioc:Post>");
		return _rdf;
	}
	
	public String toRDF(String _url_rdf_resource , String _rdf_toinsert)
	{
		String _rdf ="";
		if(status != null) 
			_rdf = "<jeu2debat:" + status.getRDFLabel() +" rdf:about=\""+ _url_rdf_resource +"?id=" + getId() +"\" >" ;
		else
			_rdf = "<sioc:Post rdf:about=\""+ _url_rdf_resource +"?id=" + getId() +"\" >" ;
		if(getLabel()!= null)_rdf = _rdf.concat("<dc:title>"+ getLabel() +"</dc:title>");
		if(! _url_rdf_resource.equalsIgnoreCase(getRepresentsResource().getEffectiveURI()))
			_rdf = _rdf.concat("<rdfs:seeAlso rdf:resource=\"" + getRepresentsResource().getEffectiveURI() + "\" />");
		_rdf = _rdf.concat("<dcterms:created>" + getCreation() + "</dcterms:created>");
		_rdf = _rdf.concat("<sioc:content><![CDATA[" + getContent() + "]]></sioc:content>");
		if(_rdf_toinsert != null && _rdf_toinsert.length() > 0) _rdf = _rdf.concat(_rdf_toinsert);
		if(status != null)
			_rdf = _rdf.concat("</jeu2debat:" + status.getRDFLabel() +">");
		else
			_rdf = _rdf.concat("</sioc:Post>");
		return _rdf;
	}
	
	public List<String> rdfHeader()
	{
		ArrayList<String> _nms = new ArrayList<String>();
		_nms.add("xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"");
		_nms.add("xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"");
		_nms.add("xmlns:dcterms=\"http://purl.org/dc/terms/\"");
		_nms.add("xmlns:dc=\"http://purl.org/dc/elements/1.1/\"");
		_nms.add("xmlns:sioc=\"http://rdfs.org/sioc/ns#\"");
		_nms.add("xmlns:annotea=\"http://www.w3.org/2000/10/annotation-ns\"");
		return _nms ;
	}
	
	public void setAuthor(Agent author) {
		this.author = author;
	}
	public Agent getAuthor() {
		return author;
	}
	
	public String toSeeAlso(String _url_rdf_resource)
	{
		String rdf = "";
		if(getStatus() != null) 
			rdf = "<jeu2debat:" + getStatus().getRDFLabel() +" rdf:about=\""+ _url_rdf_resource +"?id=" + getId() +"\" >" ;
		else
			rdf = "<sioc:Post rdf:about=\""+ _url_rdf_resource +"?id=" + getId() +"\" >" ;
		if(! _url_rdf_resource.equalsIgnoreCase(getRepresentsResource().getEffectiveURI()))
			rdf = rdf.concat("<rdfs:seeAlso rdf:resource=\"" + getRepresentsResource().getEffectiveURI() + "\" />");
		rdf = rdf.concat("<rdfs:seeAlso rdf:resource=\"").concat(_url_rdf_resource).concat("?id=").concat(getId().toString()).concat("\" />");
		if(getStatus() != null)
			rdf = rdf.concat("</jeu2debat:" + getStatus().getRDFLabel() +">");
		else
			rdf = rdf.concat("</sioc:Post>");
		return rdf ;
	}
}