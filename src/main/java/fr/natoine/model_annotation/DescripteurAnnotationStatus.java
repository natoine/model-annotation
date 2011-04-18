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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DescripteurAnnotationStatus implements Serializable
{
	public static enum AnnotatedOrAdded { ANNOTATED , ADDED };
	private AnnotatedOrAdded type ;
	private String className ;
	private String status ;
	private String[] possibleValues ;
	private Cardinalite cardinalite;
	
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String[] getPossibleValues() {
		return possibleValues;
	}
	public void setPossibleValues(String[] possibleValues) {
		this.possibleValues = possibleValues;
	}
	public Cardinalite getCardinalite() {
		return cardinalite;
	}
	public void setCardinalite(Cardinalite cardinalite) {
		this.cardinalite = cardinalite;
	}
	public void setType(AnnotatedOrAdded type) {
		this.type = type;
	}
	public AnnotatedOrAdded getType() {
		return type;
	}
	
	public String toString()
	{
		return toJSONObject().toString();
	}
	
	public JSONObject toJSONObject()
	{
		JSONObject _toJSON = new JSONObject();
		try {
			_toJSON.put("type", type);
		_toJSON.put("className", className);
		_toJSON.put("status", status);
		JSONArray _choices = new JSONArray();
		if(possibleValues != null)
		{
			int _choices_size = possibleValues.length ;
			for(int i = 0 ; i <  _choices_size; i++)
			{
				_choices.put(possibleValues[i]);
			}
		}
		_toJSON.put("choices", _choices);
		if(cardinalite.isInfinite())
		{
			_toJSON.put("cardinalite", "infinite");
		}
		else if(cardinalite.isMax())
		{
			_toJSON.put("cardinalite", cardinalite.getValue());
		}
		else _toJSON.put("cardinalite", "|" + cardinalite.getValue() + "|");
		} catch (JSONException e) {
			System.out.println("[model_annotation.DescripteurAnnotationStatus.toJSONObject] problem generating JSONObject");
			e.printStackTrace();
		}
		return _toJSON ;
	}
}
