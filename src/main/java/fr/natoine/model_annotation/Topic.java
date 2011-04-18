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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

import fr.natoine.model_resource.Resource;

@Entity
@Table(name = "TOPIC", 
		uniqueConstraints=@UniqueConstraint(columnNames = {"LABEL"}))
@XmlRootElement
public class Topic extends Resource
{
	//TODO rdf exports ...
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Topic.class)
	@JoinColumn(name = "FATHERTOPIC_ID")
	private Topic father ;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Topic getFather() {
		return father;
	}

	public void setFather(Topic father) {
		this.father = father;
	}
}