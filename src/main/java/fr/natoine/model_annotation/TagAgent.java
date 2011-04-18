/*
 * Copyright 2010 Antoine Seilles (Natoine)
 *   This file is part of model-annotation.

    model-tag is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    model-tag is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with model-tag.  If not, see <http://www.gnu.org/licenses/>.

 */
package fr.natoine.model_annotation;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

import fr.natoine.model_resource.ResourceRepresentable;
import fr.natoine.model_user.Agent;
import fr.natoine.model_user.AgentStatus;
import fr.natoine.model_user.Person;
import fr.natoine.model_user.UserAccount;


@Entity
@Table(name = "TAGAGENT"  , uniqueConstraints=
    @UniqueConstraint(columnNames = {"AGENTSTATUS_ID", "AGENT_ID", "LABEL"}))
@XmlRootElement
public class TagAgent extends Tag
{	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = AgentStatus.class)
	@JoinColumn(name = "AGENTSTATUS_ID" , nullable=false)
	private AgentStatus status;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Agent.class)
	@JoinColumn(name = "AGENT_ID" , nullable=false)
	private Agent agent;

	/**
	 * Gets the AgentStatus of the TagAgent
	 * For example isAuthor could be a AgentStatus and the TagAgent means agent is author
	 * Is author of what ? Is author of a resource annotated by this TagAgent
	 * @return
	 */
	public AgentStatus getStatus() {
		return status;
	}
	/**
	 * Sets the AgentStatus of the TagAgent
	 * @param status
	 */
	public void setStatus(AgentStatus status) {
		this.status = status;
	}
	/**
	 * Gets the Agent involved in the TagAgent
	 * @return
	 */
	public Agent getAgent() {
		return agent;
	}
	/**
	 * Sets the Agent involved in the TagAgent
	 * @param agent
	 */
	public void setAgent(Agent agent) {
		this.agent = agent;
	}
	
	public String toHTMLMax()
	{
		String _html = super.toHTMLMax();
		String _uri_agent = null ;
		if(agent instanceof Person)
		{
			_uri_agent = ((Person)agent).getRepresents().getRepresentsResource().getEffectiveURI();
		}
		else if (agent instanceof UserAccount)
		{
			_uri_agent = ((UserAccount)agent).getRepresents().getRepresentsResource().getEffectiveURI();
		}
		if(_uri_agent != null)
		{
			_html = _html.concat("<div class=agent>" + agent.getClass().getSimpleName() + ":" + "<a href=\"" + _uri_agent + "\">" + agent.getLabel() + "</a></div>");
		}
		else
		{
			_html = _html.concat("<div class=agent>" + agent.getClass().getSimpleName() + ":" + agent.getLabel() + "</div>");
		}
		_html = _html.concat("<div class=status title=\"" + status.getComment() + "\"> status : " + status.getLabel()+ "</div>");
		return _html;
	}
	
	public String toHTML()
	{
		String _html = super.toHTML();
		String _uri_agent = null ;
		if(agent instanceof Person)
		{
			_uri_agent = ((Person)agent).getRepresents().getRepresentsResource().getEffectiveURI();
		}
		else if (agent instanceof UserAccount)
		{
			_uri_agent = ((UserAccount)agent).getRepresents().getRepresentsResource().getEffectiveURI();
		}
		if(_uri_agent != null)
		{
			_html = _html.concat("<div class=agent>" + agent.getClass().getSimpleName() + ":" + "<a href=\"" + _uri_agent + "\">" + agent.getLabel() + "</a></div>");
		}
		else
		{
			_html = _html.concat("<div class=agent>" + agent.getClass().getSimpleName() + ":" + agent.getLabel() + "</div>");
		}
		_html = _html.concat("<div class=status title=\"" + status.getComment() + "\"> status : " + status.getLabel()+ "</div>");
		return _html;
	}
	
	public String toRDF(String _url_rdf_resource, String _url_rdf_agent, String _rdf_toinsert)
	{
		String _rdf = "<moat:Tag rdf:about=\""+ _url_rdf_resource +"?id=" + getId() +"\" >" ;
		_rdf = _rdf.concat("<moat:name><![CDATA[" + getLabel() + "]]></moat:name>");
		_rdf = _rdf.concat("<rdfs:label>" + getRDFLabel() + "</rdfs:label>");
		if(! _url_rdf_resource.equalsIgnoreCase(getRepresentsResource().getEffectiveURI()))
			_rdf = _rdf.concat("<rdfs:seeAlso rdf:resource=\"" + getRepresentsResource().getEffectiveURI() + "\" />");
		if(status.getComment()!=null) _rdf = _rdf.concat("<rdfs:comment xml:lang=\"fr\">"+ status.getComment() + "</rdfs:comment>");
		if(agent instanceof ResourceRepresentable)
		{
			_rdf = _rdf.concat("<moat:hasMeaning>");
			_rdf = _rdf.concat("<moat:Meaning>");
			_rdf = _rdf.concat("<rdfs:label>"+ agent.getLabel() +"</rdfs:label>");
			String _url_represent_agent = ((ResourceRepresentable)agent).getRepresents().getRepresentsResource().getEffectiveURI() ;
			_rdf = _rdf.concat("<moat:meaningURI rdf:resource=\""+ _url_rdf_agent + "?id=" + agent.getId() + "\"/>");
			if(! _url_rdf_agent.equalsIgnoreCase(_url_represent_agent))
				_rdf = _rdf.concat("<moat:meaningURI rdf:resource=\""+ _url_represent_agent +"\"/>");
			_rdf = _rdf.concat("</moat:Meaning>");
			_rdf = _rdf.concat("</moat:hasMeaning>");
		}
		_rdf = _rdf.concat("<dcterms:created>" + getCreation() + "</dcterms:created>");
		if(_rdf_toinsert != null && _rdf_toinsert.length() > 0) _rdf = _rdf.concat(_rdf_toinsert);
		_rdf = _rdf.concat("</moat:Tag>");
		return _rdf;
	}
	
	public String toRDF(String _url_rdf_resource, String _rdf_toinsert)
	{
		String _rdf = "<moat:Tag rdf:about=\""+ _url_rdf_resource +"?id=" + getId() +"\" >" ;
		_rdf = _rdf.concat("<moat:name><![CDATA[" + getLabel() + "]]></moat:name>");
		_rdf = _rdf.concat("<rdfs:label>" + getRDFLabel() + "</rdfs:label>");
		if(! _url_rdf_resource.equalsIgnoreCase(getRepresentsResource().getEffectiveURI()))
			_rdf = _rdf.concat("<rdfs:seeAlso rdf:resource=\"" + getRepresentsResource().getEffectiveURI() + "\" />");
		if(status.getComment()!=null) _rdf = _rdf.concat("<rdfs:comment xml:lang=\"fr\">"+ status.getComment() + "</rdfs:comment>");
		if(agent instanceof ResourceRepresentable)
		{
			_rdf = _rdf.concat("<moat:hasMeaning>");
			_rdf = _rdf.concat("<moat:Meaning>");
			_rdf = _rdf.concat("<rdfs:label>"+ agent.getLabel() +"</rdfs:label>");
			//TODO gérer l'url de l'affichage de l'agent autrement
			/*String _url_rdf_user = _url_rdf_resource ;
			_url_rdf_user = _url_rdf_user.substring(0 , _url_rdf_user.lastIndexOf("/") + 1);
			_url_rdf_user = _url_rdf_user.concat("ServletViewRDFAgents");
			String _url_represent_agent = ((ResourceRepresentable)agent).getRepresents().getRepresentsResource().getEffectiveURI() ;
			_rdf = _rdf.concat("<moat:meaningURI rdf:resource=\""+ _url_rdf_user + "?id=" + agent.getId() + "\"/>");
			if(! _url_rdf_user.equalsIgnoreCase(_url_represent_agent))
				_rdf = _rdf.concat("<moat:meaningURI rdf:resource=\""+ _url_represent_agent +"\"/>");
			*/
			_rdf = _rdf.concat("</moat:Meaning>");
			_rdf = _rdf.concat("</moat:hasMeaning>");
		}
		_rdf = _rdf.concat("<dcterms:created>" + getCreation() + "</dcterms:created>");
		if(_rdf_toinsert != null && _rdf_toinsert.length() > 0) _rdf = _rdf.concat(_rdf_toinsert);
		_rdf = _rdf.concat("</moat:Tag>");
		return _rdf;
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