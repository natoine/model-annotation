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
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONArray;
import org.json.JSONException;

import fr.natoine.model_resource.AccessibleResource;
import fr.natoine.model_resource.Resource;
import fr.natoine.model_resource.URI;
import fr.natoine.model_user.Agent;

@Entity
@Table(name = "ANNOTATION")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@XmlRootElement
public class Annotation extends Resource implements AccessibleResource
{
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = URI.class)
	@JoinColumn(name = "ACCESSURI_ID" , nullable = false)
	private URI access;
	
	@ManyToMany(cascade = CascadeType.ALL, targetEntity = Resource.class)
	@JoinTable(
			name="ANNOTATION_TO_ANNOTATED",
			joinColumns=@JoinColumn(name="ANNOTATION_ID"),
	        inverseJoinColumns=@JoinColumn(name="RESOURCE_ID")
				)
	private Collection<Resource> annotated;
	
	@ManyToMany(cascade = CascadeType.ALL, targetEntity = Resource.class)
	@JoinTable(
			name="ANNOTATION_TO_ADDED",
			joinColumns=@JoinColumn(name="ANNOTATION_ID"),
	        inverseJoinColumns=@JoinColumn(name="RESOURCE_ID")
				)
	private Collection<Resource> added;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = AnnotationStatus.class)
	@JoinColumn(name = "ANNOTATIONSTATUS_ID" , nullable = false)
	private AnnotationStatus status;
	
	@Lob
	@Column(name = "DESCRIPTEUR_JSON")
	private String indicesStatus;
	
	@ManyToMany(cascade = CascadeType.ALL, targetEntity = URI.class)
	@JoinTable(
			name="ANNOTATION_TO_ANNOTATEDURIS",
			joinColumns=@JoinColumn(name="ANNOTATION_ID"),
	        inverseJoinColumns=@JoinColumn(name="URI_ID")
				)
	private Collection<URI> annotatedURIs;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Agent.class)
	@JoinColumn(name = "CREATOR_ID" , nullable = true)
	private Agent creator;
	
	public URI getAccess() {
		return access;
	}
	public void setAccess(URI access) {
		this.access = access;
	}
	/**
	 * Gets Resources annotated by the Annotation
	 * @return
	 */
	public Collection<Resource> getAnnotated() {
		return annotated;
	}
	/**
	 * Sets Resources annotated by the Annotation
	 * @param annotated
	 */
	public void setAnnotated(Collection<Resource> annotated) {
		this.annotated = annotated;
	}
	/**
	 * Gets Resources added by the Annotation to the Annotated Resources 
	 * @return
	 */
	public Collection<Resource> getAdded() {
		return added;
	}
	/**
	 * Sets Resources added by the Annotation to the Annotated Resources
	 * @param added
	 */
	public void setAdded(Collection<Resource> added) {
		this.added = added;
	}
	/**
	 * Gets the AnnotationStatus of the Annotation.
	 * Status defines the semantic of the Annotation.
	 * @return
	 */
	public AnnotationStatus getStatus() {
		return status;
	}
	/**
	 * Sets the AnnotationStatus of the Annotation.
	 * Status defines the semantic of the Annotation.
	 * @param status
	 */
	public void setStatus(AnnotationStatus status) {
		this.status = status;
	}
	
	/**
	 * To store the status of each annotated resource to be compliant with the descriptor of the annotationStatus
	 * @param indicesStatus
	 */
	public void setIndicesStatus(String indicesStatus) {
		this.setIndicesStatus(indicesStatus.toString());
	}
	
	public JSONArray getIndicesStatus() {
		try {
			return new JSONArray(indicesStatus);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("[model_annotation.AnnotationStatus.getDescripteur] Problem parsing descripteur");
			return new JSONArray();
		}
	}
	
	/**
	 * Sets the collection of URI annotated by this annotation
	 * For example URI of each WebPage annotated or URI of each WebPAge on wich was done a Selection
	 * Each URI should appear only once in the Collection
	 * @param annotatedURIs
	 */
	public void setAnnotatedURIs(Collection<URI> annotatedURIs) {
		this.annotatedURIs = annotatedURIs;
	}
	/**
	 * Gets the collection of URI annotated by this annotation
	 * For example URI of each WebPage annotated or URI of each WebPAge on wich was done a Selection
	 * Each URI should appear only once in the Collection
	 * @return
	 */
	public Collection<URI> getAnnotatedURIs() {
		return annotatedURIs;
	}
	
	public String toHTMLMax()
	{
		String _html = super.toHTMLMax();
		_html = _html.concat("Status : <span class=status title=" + status.getComment() + ">" + status.getLabel() + "</span>");
		return _html;
	}
	
	public String toHTML()
	{
		String _html = super.toHTML();
		_html = _html.concat("Status : <span class=status title=" + status.getComment() + ">" + status.getLabel() + "</span>");
		return _html;
	}
	//TODO find a more efficient and more generic solution
	public List<String> rdfHeader()
	{
		ArrayList<String> _nms = new ArrayList<String>();
		_nms.add("xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"");
		_nms.add("xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"");
		_nms.add("xmlns:dcterms=\"http://purl.org/dc/terms/\"");
		_nms.add("xmlns:moat=\"http://moat-project.org/ns\"");
		_nms.add("xmlns:nicetag=\"http://ns.inria.fr/nicetag/2009/09/25/voc\"");
		_nms.add("xmlns:content=\"http://purl.org/rss/1.0/modules/content/\"");
		_nms.add("xmlns:irw=\"http://ontologydesignpatterns.org/ont/web/irw.owl\"");
		_nms.add("xmlns:foaf=\"http://xmlns.com/foaf/0.1/\"");
		_nms.add("xmlns:sioc=\"http://rdfs.org/sioc/ns#\"");
		_nms.add("xmlns:annotea=\"http://www.w3.org/2000/10/annotation-ns\"");
		_nms.add("xmlns:dc=\"http://purl.org/dc/elements/1.1/\"");
		return _nms ;
	}
	
	public void setCreator(Agent creator) {
		this.creator = creator;
	}
	public Agent getCreator() {
		return creator;
	}
	
	public String toRDF(String _url_resource , String _url_agent, String _rdf_toinsert)
	{
		ArrayList<Agent> _authors = new ArrayList<Agent>();
		String rdf = "";
		if(status != null)
			rdf = rdf.concat("<jeu2debat:").concat(status.getRDFLabel())
			.concat(" rdf:about=\"").concat(_url_resource).concat("?id=").concat(getId().toString()).concat("\" >");
		else
			rdf = rdf.concat("<annotea:Annotation rdf:about=\"").concat(_url_resource).concat("?id=").concat(getId().toString()).concat("\" >");
		//annotates
		rdf = rdf.concat(annotated(_url_resource));
		//context, sélections
		Collection<Resource> _annotateds = annotated ;
		if(_annotateds!=null && _annotateds.size()>0)
		{
			rdf = rdf.concat("<annotea:context>");
			for(Resource _annotated : _annotateds)
			{
				rdf = rdf.concat(_annotated.toRDF(_url_resource , _url_agent, ""));
			}
			rdf = rdf.concat("</annotea:context>");
		}
		//body, ressources ajoutées
		Collection<Resource> _addeds = added ;
		if(_addeds!= null && _addeds.size()>0)
		{
			rdf = rdf.concat("<annotea:body>");
			for(Resource _added : _addeds)
			{
				rdf = rdf.concat(_added.toRDF(_url_resource , _url_agent, ""));
				if(_added instanceof TagAgent)
				{
					if(((TagAgent)_added).getLabel().contains("isAuthor")) _authors.add(((TagAgent)_added).getAgent());
				}
			}
			rdf = rdf.concat("</annotea:body>");
		}
		//autheurs
		if(_authors.size()>0)
		{
			rdf = rdf.concat("<annotea:author>");
			for(Agent _author : _authors)
			{
				rdf = rdf.concat(_author.toRDF(_url_agent , ""));
			}
			rdf = rdf.concat("</annotea:author>");
		}
		if(_rdf_toinsert != null && _rdf_toinsert.length() > 0) rdf = rdf.concat(_rdf_toinsert);
		//fin
		if(status != null)
			rdf = rdf.concat("</jeu2debat:").concat(status.getRDFLabel()).concat(">");
		else
			rdf = rdf.concat("</annotea:Annotation>");
		return rdf ;
	}
	
	public String toRDF(String _url_resource , String _rdf_toinsert)
	{
		//ArrayList<Agent> _authors = new ArrayList<Agent>();
		String rdf = "";
		if(status != null)
			rdf = rdf.concat("<jeu2debat:").concat(status.getRDFLabel())
			.concat(" rdf:about=\"").concat(_url_resource).concat("?id=").concat(getId().toString()).concat("\" >");
		else
			rdf = rdf.concat("<annotea:Annotation rdf:about=\"").concat(_url_resource).concat("?id=").concat(getId().toString()).concat("\" >");
		//annotates
		rdf = rdf.concat(annotated(_url_resource));
		//context, ressources annotées
		Collection<Resource> _annotateds = annotated ;
		if(_annotateds!=null && _annotateds.size()>0)
		{
			rdf = rdf.concat("<annotea:context>");
			for(Resource _annotated : _annotateds)
			{
				rdf = rdf.concat(_annotated.toRDF(_url_resource , ""));
			}
			rdf = rdf.concat("</annotea:context>");
		}
		//body, ressources ajoutées
		Collection<Resource> _addeds = added ;
		if(_addeds!= null && _addeds.size()>0)
		{
			rdf = rdf.concat("<annotea:body>");
			for(Resource _added : _addeds)
			{
				rdf = rdf.concat(_added.toRDF(_url_resource , ""));
				/*if(_added instanceof TagAgent)
				{
					if(((TagAgent)_added).getLabel().contains("isAuthor")) _authors.add(((TagAgent)_added).getAgent());
				}*/
			}
			rdf = rdf.concat("</annotea:body>");
		}
		//autheurs
		/*if(_authors.size()>0)
		{
			String _url_rdf_agent = _url_resource ;
			_url_rdf_agent = _url_rdf_agent.substring(0 , _url_rdf_agent.lastIndexOf("/") + 1);
			_url_rdf_agent = _url_rdf_agent.concat("ServletViewRDFAgents");
			rdf = rdf.concat("<annotea:author>");
			for(Agent _author : _authors)
			{
				rdf = rdf.concat(_author.toRDF(_url_rdf_agent));
			}
			rdf = rdf.concat("</annotea:author>");
		}*/
		if(_rdf_toinsert != null && _rdf_toinsert.length() > 0) rdf = rdf.concat(_rdf_toinsert);
		//fin
		if(status != null)
			rdf = rdf.concat("</jeu2debat:").concat(status.getRDFLabel()).concat(">");
		else
			rdf = rdf.concat("</annotea:Annotation>");
		return rdf ;
	}
	
	public String toSeeAlso(String _url_rdf_resources)
	{
		String rdf = "";
		if(status != null)
			rdf = rdf.concat("<jeu2debat:").concat(status.getRDFLabel())
			.concat(" rdf:about=\"").concat(_url_rdf_resources).concat("?id=").concat(getId().toString()).concat("\" >");
		else
			rdf = rdf.concat("<annotea:Annotation rdf:about=\"").concat(_url_rdf_resources).concat("?id=").concat(getId().toString()).concat("\" >");
		rdf = rdf.concat("<rdfs:seeAlso rdf:resource=\"").concat(_url_rdf_resources).concat("?id=").concat(getId().toString()).concat("\"/>") ;
		if(status != null)
			rdf = rdf.concat("</jeu2debat:").concat(status.getRDFLabel()).concat(">");
		else
			rdf = rdf.concat("</annotea:Annotation>");
		return rdf ;
	}
	
	private String annotated(String _url_resource)
	{
		String rdf = "";
		if(annotated != null && annotated.size() > 0)
		{
			rdf = rdf.concat("<annotea:annotates>");
			ArrayList<Resource> filtred_annotated = new ArrayList<Resource>();
			for(Resource resource : annotated)
			{
				if(resource instanceof Selection)
				{
					Resource origin = ((Selection)resource).getSelectionOrigin() ;
					if( ! filtred_annotated.contains(origin)) 
					{
						rdf = rdf.concat(origin.toSeeAlso(_url_resource));
						filtred_annotated.add(origin);
					}
				}
				else
				{
					if( ! filtred_annotated.contains(resource)) 
					{
						rdf = rdf.concat(resource.toSeeAlso(_url_resource));
						filtred_annotated.add(resource);
					}
				}
			}
			rdf = rdf.concat("</annotea:annotates>");
		}
		return rdf ;
	}
}