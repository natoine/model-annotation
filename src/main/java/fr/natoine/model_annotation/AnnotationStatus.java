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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Entity
@Table(name = "ANNOTATIONSTATUS")
@XmlRootElement
public class AnnotationStatus implements Serializable
{
	@Id @GeneratedValue
	@Column(name = "ANNOTATIONSTATUS_ID")
	private Long id;

	@Column(name = "LABEL" , unique=true, nullable=false)
	private String label;

	@Column(name = "COMMENT")
	private String comment;

	@ManyToOne(cascade = CascadeType.ALL, targetEntity = AnnotationStatus.class)
	@JoinColumn(name = "FATHERANNOTATIONSTATUS_ID")
	private AnnotationStatus father;

	@Lob
	@Column(name = "DESCRIPTEUR_JSON")
	private String descripteur;

	@Column(name = "COLOR")
	private String color;

	@Column(name = "ICON")
	private String icon;
	//Tout statut d'annotation est utilisable en réponse rapide.

	/**
	 * Gets the father of an AnnotationStatus.
	 * AnnotationStatus are organized as a hierarchy.
	 * The father of an AnnotationStatus is the direct more general Status above the current status.
	 * @return
	 */
	public AnnotationStatus getFather() {
		return father;
	}
	/**
	 * Sets the father of an AnnotationStatus.
	 * AnnotationStatus are organized as a hierarchy.
	 * The father of an AnnotationStatus is the direct more general Status above the current status.
	 * @param father
	 */
	public void setFather(AnnotationStatus father) {
		this.father = father;
	}
	/**
	 * Gets the label of the status.
	 * @return
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * Sets the label of the status.
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * Gets the comment of a status.
	 * Comment is used to disambiguate the status.
	 * @return
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * Sets the comment of a status.
	 * Comment is used to disambiguate the status.
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * Gets the Id of the Status
	 * @return
	 */
	public Long getId() {
		return id;
	}
	/**
	 * Sets a JSONArray as a decriptor of the Annotation Form
	 * The JSONArry is made of JSONObjects composed of :
	 * a string equals ANNOTATED or ADDED
	 * a string that indicates the ClassName of the resource
	 * a string that indicates a status for this resource (comment for example)
	 * a String[] to give the possible values if there are
	 * a string infinite or a number to indicate if you con add an infinity of this resource or a specified number, if the number is surrounded by |, it means you must add exactly this number of resources
	 * @param descripteur
	 */
	public void setDescripteur(JSONArray descripteur) {
		this.descripteur = descripteur.toString();
	}
	/**
	 * Gets a JSONArray as a decriptor of the Annotation Form
	 * The JSONArry is made of JSONObjects composed of :
	 * a string type equals ANNOTATED or ADDED
	 * a string className that indicates the ClassName of the resource
	 * a string status that indicates a status for this resource (comment for example)
	 * a String[] choices to give the possible values if there are
	 * a string cardinalite infinite or a number to indicate if you con add an infinity of this resource or a specified number, if the number is surrounded by |, it means you must add exactly this number of resources
	 * @return
	 */
	public JSONArray getDescripteur() {
		try {
			JSONArray descripteur_array = new JSONArray(descripteur);
			return descripteur_array ;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("[model_annotation.AnnotationStatus.getDescripteur] Problem parsing descripteur");
			return new JSONArray();
		}
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getColor() {
		return color;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getIcon() {
		return icon;
	}
	
	private String createCheckbox(String _html , String _className , String _status, String _unique_id, String _value , int _cpt)
	{
		_html = _html.concat("<div class=choice><input type=checkbox name=\"checkbox_" + _className.toLowerCase() + "_" + _status + "_" + _cpt + "_" + _unique_id + "\" value=\"" + _value + "\" /> " + _value + "</div>");
		return _html ;
	}
	
	private String createCheckboxes(String _html , String _className, String _status, String _unique_id, int _cpt , JSONArray _choices, int _choices_length) throws JSONException
	{
		_html = _html.concat("<input type=hidden name=\"checkboxes_annotation_added_" + _className.toLowerCase() + "_" + _status + "_" + _cpt + "_" + _unique_id +"\" id=\"checkboxes_annotation_added_" + _className.toLowerCase() + "_" + _status + "_" + _cpt + "_" + _unique_id +"\" value=\"\" />");
		for(int j=0 ; j< _choices_length ; j++)
		{
			String _value = _choices.getString(j);
			_html = createCheckbox(_html, _className, _status, _unique_id, _value, _cpt);
		}
		return _html ;
	}
	
	private String oneRadioButtonOnly(String _html, String _className, String _status, JSONArray _choices) throws JSONException
	{
		String _value = _choices.getString(0);
		_html = _html.concat("<div class=choice><input type=radio name=\"annotation_added_" + _className.toLowerCase() + "_" + _status + "\" value=\"" + _value + "\" checked /> " + _value + "</div>");
		return _html ;
	}
	
	private String radioButtons(String _html, String _className, String _status, JSONArray _choices, int _choices_length) throws JSONException
	{
		if(_choices_length == 1)
		{
			//un seul choix, radio bouton grisé
			_html = oneRadioButtonOnly(_html, _className, _status, _choices);
		}
		else
		{
			for(int j=0 ; j< _choices_length ; j++)
			{
				String _value = _choices.getString(j);
				_html = _html.concat("<div class=choice><input type=radio name=\"annotation_added_" + _className.toLowerCase() + "_" + _status + "\" value=\"" + _value + "\" /> " + _value + "</div>");
			}
		}
		return _html ;
	}
	
	private String formTags(String _html, String _className, String _status, String _unique_id, int _cpt, JSONObject _elt, String _true_cardinalite, int _card) throws JSONException
	{
		JSONArray _choices = _elt.getJSONArray("choices");
		int _choices_length = _choices.length();
		if(_choices_length > 0)
		{
			if(_true_cardinalite.equalsIgnoreCase("n") || _card > 1)
				//checkboxes
				_html = createCheckboxes(_html, _className, _status, _unique_id, _cpt, _choices, _choices_length);
			else //radioButton
				_html = radioButtons(_html, _className, _status, _choices, _choices_length);
		}
		else _html = _html.concat("<input name=\"annotation_added_" + _className.toLowerCase() + "_" + _status + "\" type=text value=\"\" />");
		return _html ;
	}
	
	private String formMood(String _html, String _className, String _status, String _unique_id, int _cpt, JSONObject _elt, String _true_cardinalite, int _card) throws JSONException
	{
		JSONArray _choices = _elt.getJSONArray("choices");
		int _choices_length = _choices.length();
		if(_choices_length > 0)
		{
			if(_true_cardinalite.equalsIgnoreCase("n") || _card > 1)
			{
				//checkboxes
				_html = _html.concat("<input type=hidden name=\"checkboxes_annotation_added_" + _className.toLowerCase() + "_" + _status + "_" + _cpt + "_" + _unique_id +"\" id=\"checkboxes_annotation_added_" + _className.toLowerCase() + "_" + _status + "_" + _cpt + "_" + _unique_id +"\" value=\"\" />");
				for(int j=0 ; j< _choices_length ; j++)
				{
					String _value = _choices.getString(j);
					//_html = _html.concat("<div class=choice><input type=radio name=annotation_added_mood_" + status + " value=\"" + _value + "\" /> " + _value + "</div>");
					String _mood = "<img src=/PortletAnnotation-1.0.0/images/mood_"+ _value.replaceAll("é", "e").replace("è", "e") +".png title=" + _value + " />";
					_html = _html.concat("<div class=choice><input type=checkbox name=\"checkbox_" + _className.toLowerCase() + "_" + _status + "_" + _cpt + "_" + _unique_id + "\" value=\"" + _value + "\" /> " + _mood + "</div>");
				}
			}
			else //radioButton
			{
				if(_choices_length == 1)
				{
					//un seul choix, radio bouton grisé
					String _value = _choices.getString(0);
					_html = _html.concat("<div class=choice><input type=radio name=\"annotation_added_" + _className.toLowerCase() + "_" + _status + "\" value=\"" + _value + "\" checked/> " + _value + "</div>");
				}
				else
				{
					for(int j=0 ; j< _choices_length ; j++)
					{
						String _value = _choices.getString(j);
						//_html = _html.concat("<div class=choice><input type=radio name=annotation_added_mood_" + status + " value=\"" + _value + "\" /> " + _value + "</div>");
						String _mood = "<img src=/PortletAnnotation-1.0.0/images/mood_"+ _value.replaceAll("é", "e").replace("è", "e") +".png title=" + _value + " />";
						_html = _html.concat("<div class=choice><input type=radio name=\"annotation_added_" + _className.toLowerCase() + "_" + _status + "\" value=\"" + _value + "\" /> " + _mood + "</div>");
					}
				}
			}
		}
		else _html = _html.concat("<input name=\"annotation_added_" + _className.toLowerCase() + "_" + _status + "\" type=text value=\"\" />");
		return _html;
	}
	/**
	 * Returns the content of an HTMLForm as describe by the descriptor.
	 * This is a default HTMLForm, you should define your own.
	 * @return
	 */
	public String getHTMLForm(String unique_id)
	{
		if(descripteur != null)
		{
			JSONArray _descripteur = getDescripteur();
			int nb_elts = _descripteur.length();
			String _html = "";
			try {
				JSONObject _elt ;
				for(int i = 0 ; i< nb_elts ;i++)
				{
					_elt = _descripteur.getJSONObject(i);
					String _type = _elt.getString("type");
					if(_type.equalsIgnoreCase("ADDED") ) //Dans cette version on ne traite pas les ANNOTATED
					{
						String status = _elt.getString("status");
						_html = _html.concat("<div class=formelt>");
						_html = _html.concat("<span class=eltheader>" + status + " : </span>");
						String className = _elt.getString("className");
						_html = _html.concat("<div class=formfield name=\"formfield_" + className + "_" + unique_id + "\" id=\"formfield_" + status + "_" + i + "_" + unique_id + "\" >");
						if(className.equalsIgnoreCase(Post.class.getSimpleName()))
						{
							_html = _html.concat("<textarea rows=3 onfocus=\"this.value=''; this.onfocus=null\" name=\"annotation_added_simpletext_" + status + "\" >Tapez ici votre texte</textarea>");
						}
						else
						{
							String cardinalite = _elt.getString("cardinalite");
							String true_cardinalite = cardinalite.substring(1);
							true_cardinalite = true_cardinalite.substring(0 , true_cardinalite.length() - 1);
							//System.out.println("[AnnotationStatus.getHTMLForm] true_cardinalite : " + true_cardinalite);
							int card = 0 ;
							if(!true_cardinalite.equalsIgnoreCase("n"))
							{
								try
								{
									card = Integer.parseInt(true_cardinalite);
								}
								catch (NumberFormatException e)
								{
									System.out.println("[AnnotationStatus.getHTMLForm] true_cardinalite is not a number ...");
								}
							}
							if(className.equalsIgnoreCase(Mood.class.getSimpleName()))
								_html = formMood(_html, className, status, unique_id, i, _elt, true_cardinalite, card);
							else if(className.equalsIgnoreCase(Tag.class.getSimpleName()) || className.equalsIgnoreCase(Domain.class.getSimpleName()) || className.equalsIgnoreCase(Judgment.class.getSimpleName()))
								_html = formTags(_html, className, status, unique_id, i, _elt, true_cardinalite, card);
						}
						_html = _html.concat("</div>");
						_html = _html.concat("</div>");
					}
				}
				return _html ;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "";
			}
		}
		else return "";
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
		.concat("<rdfs:subClassOf rdf:resource=\"http://www.w3.org/2000/10/annotation-ns#Annotation\"/>")
		.concat("<rdfs:isDefinedBy rdf:resource=\"" + _urlRDF_voc + "#\"/>")
		.concat("</owl:Class>") ;
		return _rdf;
	}

	public String toCSS()
	{
		String css = ".annotation_" + label.replaceAll(" ", "_");
		css = css.concat("\n");
		css = css.concat("{ \n");
		if(color != null)
		{
			css = css.concat("border: 2px solid " + color + " ;\n");
			css = css.concat("background-color : " + color + " ;\n");
		}
		else
		{
			css = css.concat("border: 2px solid yellow ;\n");
			css = css.concat("background-color : yellow ;\n");
		}
		css = css.concat("\n }");
		return css ;
	}
}