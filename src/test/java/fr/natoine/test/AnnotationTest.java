package fr.natoine.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.json.JSONArray;

import fr.natoine.model_annotation.Annotation;
import fr.natoine.model_annotation.AnnotationStatus;
import fr.natoine.model_annotation.Cardinalite;
import fr.natoine.model_annotation.Definition;
import fr.natoine.model_annotation.DescripteurAnnotationStatus;
import fr.natoine.model_annotation.PostStatus;
import fr.natoine.model_resource.Resource;
import fr.natoine.model_resource.URI;
import fr.natoine.model_annotation.Tag;
import fr.natoine.model_annotation.TagAgent;
import fr.natoine.model_user.Agent;
import fr.natoine.model_user.AgentStatus;
import fr.natoine.model_user.Application;

import junit.framework.TestCase;

public class AnnotationTest extends TestCase
{
	public AnnotationTest(String name) 
	{
	    super(name);
	}
	
	public void testCreateAnnotation()
	{
		//annotation simple doc et doc
		Annotation _testAnnotation = new Annotation();
		URI _access = new URI();
		_access.setEffectiveURI("http://annotation.test");
		_testAnnotation.setAccess(_access);
		_testAnnotation.setContextCreation("AnnotationTest");
		_testAnnotation.setCreation(new Date());
		_testAnnotation.setLabel("Annotation simple");
		_testAnnotation.setRepresentsResource(_access); //same uri to access and represents
		AnnotationStatus _status = new AnnotationStatus();
		_status.setLabel("simple status");
		JSONArray descripteur = new JSONArray();
		DescripteurAnnotationStatus _instance_descripteur_1 = new DescripteurAnnotationStatus();
		_instance_descripteur_1.setType(DescripteurAnnotationStatus.AnnotatedOrAdded.ANNOTATED);
		_instance_descripteur_1.setClassName("Resource");
		_instance_descripteur_1.setStatus("sélection");
		_instance_descripteur_1.setPossibleValues(null);
		Cardinalite cardinalite_selection = new Cardinalite();
		cardinalite_selection.setInfinite(true);
		_instance_descripteur_1.setCardinalite(cardinalite_selection);
		DescripteurAnnotationStatus _instance_descripteur_2 = new DescripteurAnnotationStatus();
		_instance_descripteur_2.setType(DescripteurAnnotationStatus.AnnotatedOrAdded.ADDED);
		_instance_descripteur_2.setClassName("Post");
		_instance_descripteur_2.setStatus("commentaire");
		_instance_descripteur_2.setPossibleValues(null);
		Cardinalite cardinalite_ajout = new Cardinalite();
		cardinalite_ajout.setInfinite(false);
		cardinalite_ajout.setMax(false);
		cardinalite_ajout.setValue(1);
		_instance_descripteur_2.setCardinalite(cardinalite_ajout);
		//descripteur.put(_instance_descripteur_1.toString());
		//descripteur.put(_instance_descripteur_2.toString());
		descripteur.put(_instance_descripteur_1.toJSONObject());
		descripteur.put(_instance_descripteur_2.toJSONObject());
		_status.setDescripteur(descripteur);
		_testAnnotation.setStatus(_status);
		
		Collection<Resource> _annotated = new ArrayList<Resource>();
		Resource _annotatedResource = new Resource();
		_annotatedResource.setContextCreation("AnnotationTest");
		_annotatedResource.setCreation(new Date());
		_annotatedResource.setLabel("Resource annotée");
		URI _annotationResourceRepresents = new URI();
		_annotationResourceRepresents.setEffectiveURI("http://annotation.test.resource.annote.representsURI");
		_annotatedResource.setRepresentsResource(_annotationResourceRepresents);
		_annotated.add(_annotatedResource);
		_testAnnotation.setAnnotated(_annotated);
		
		Collection<Resource> _added = new ArrayList<Resource>();
		Resource _addedResource = new Resource();
		_addedResource.setContextCreation("AnnotationTest");
		_addedResource.setCreation(new Date());
		_addedResource.setLabel("Resource ajoutée");
		URI _annotationAddedResourceRepresents = new URI();
		_annotationAddedResourceRepresents.setEffectiveURI("http://annotation.test.resource.added.representsURI");
		_addedResource.setRepresentsResource(_annotationAddedResourceRepresents);
		_added.add(_addedResource);
		_testAnnotation.setAdded(_added);
		
		//annotation complexe sélection et doc
	/*	Annotation _testComplexAnnotation = new Annotation();
		URI _access2 = new URI();
		_access2.setEffectiveURI("http://annotation.testcomplex");
		_testComplexAnnotation.setAccess(_access2);
		_testComplexAnnotation.setContextCreation("AnnotationTest");
		_testComplexAnnotation.setCreation(new Date());
		_testComplexAnnotation.setLabel("Annotation complexe");
		_testComplexAnnotation.setRepresentsResource(_access2); //same uri to access and represents
		AnnotationStatus _status2 = new AnnotationStatus();
		_status2.setLabel("complexe status");
		_testComplexAnnotation.setStatus(_status2);
		
		Collection<Resource> _annotated2 = new ArrayList<Resource>();
		Selection _annotatedResource2 = new Selection();
		_annotatedResource2.setSelectionOrigin(_annotatedResource);
		//Resource _annotatedResource2 = new Resource();
		_annotatedResource2.setContextCreation("AnnotationTest");
		_annotatedResource2.setCreation(new Date());
		_annotatedResource2.setLabel("Resource annotée 2");
		URI _annotationResourceRepresents2 = new URI();
		_annotationResourceRepresents2.setEffectiveURI("http://annotation.test.complexe.resource.annote.representsURI2");
		_annotatedResource2.setRepresentsResource(_annotationResourceRepresents2);
		_annotated2.add(_annotatedResource2);
		_testComplexAnnotation.setAnnotated(_annotated2);
		
		Collection<Resource> _added2 = new ArrayList<Resource>();
		Resource _addedResource2 = new Resource();
		_addedResource2.setContextCreation("AnnotationTest");
		_addedResource2.setCreation(new Date());
		_addedResource2.setLabel("Resource ajoutée 2");
		URI _annotationAddedResourceRepresents2 = new URI();
		_annotationAddedResourceRepresents2.setEffectiveURI("http://annotation.test.resource.added.representsURI2");
		_addedResource2.setRepresentsResource(_annotationAddedResourceRepresents2);
		_added2.add(_addedResource2);
		_testComplexAnnotation.setAdded(_added2);
		*/
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("annotations");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try{
        	tx.begin();
        	em.persist(_testAnnotation);
        	//em.persist(_testComplexAnnotation);
        	tx.commit();
        }
        catch(Exception e)
        {
        	 System.out.println( "[AnnotationTest] unable to persist" );
        	 System.out.println(e.getMessage());
        	 System.out.println(e.getStackTrace());
        }
        em.close();
	}
	
	public void testRetrieve()
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("annotations");
        EntityManager newEm = emf.createEntityManager();
        EntityTransaction newTx = newEm.getTransaction();
        newTx.begin();
        List annotations = newEm.createQuery("from Annotation").getResultList();
        System.out.println( annotations.size() + " annotation(s) found" );
        Annotation loadedAnnotation ;
        for (Object u : annotations) 
        {
        	loadedAnnotation = (Annotation) u;
            System.out.println("[AnnotationTest] Annotation id : " + loadedAnnotation.getId()  
            		+ " Creation context : " + loadedAnnotation.getContextCreation()
            		+ " Label : " + loadedAnnotation.getLabel()
            		+ " Date creation : " + loadedAnnotation.getCreation()
            		);
        }
        newTx.commit();
        newEm.close();
        // Shutting down the application
        emf.close();
	}

	public void testCreateTag()
	{
		Tag _test = new Tag();
		_test.setContextCreation("TagTest.testCreate");
		_test.setCreation(new Date());
		_test.setLabel("tag de test");
		URI representsResource = new URI();
		representsResource.setEffectiveURI("http://uri.test.tag");
		_test.setRepresentsResource(representsResource);
		
		TagAgent _testTA = new TagAgent();
		Agent agent = new Agent();
		Application _app = new Application();
		_app.setLabel("application test tag");
		_app.setInscription(new Date());
		_app.setDescription("pour créer des tags en test");
		Resource representsApp = new Resource();
		representsApp.setContextCreation("test tags");
		representsApp.setCreation(new Date());
		representsApp.setLabel("resource pour app tag");
		URI representsResourceApp = new URI();
		representsResourceApp.setEffectiveURI("http://wwwmon.test.app.tag");
		representsApp.setRepresentsResource(representsResourceApp);
		_app.setRepresents(representsApp);
		agent.setContextInscription(_app);
		agent.setDescription("agent pour tester le TagAgent");
		agent.setInscription(new Date());
		agent.setLabel("agent TagAgent");
		_testTA.setAgent(agent);
		AgentStatus status = new AgentStatus();
		status.setComment("commentaire du status d'agent de test de TagAgent");
		status.setLabel("test TagAgent Status");
		_testTA.setStatus(status);
		_testTA.setContextCreation("TagTest.testCreate");
		_testTA.setCreation(new Date());
		_testTA.setLabel("TagAgentTest");
		URI representsResource2 = new URI();
		representsResource2.setEffectiveURI("http://uri.test.TagAgent");
		_testTA.setRepresentsResource(representsResource2);
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("annotations");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try{
        	tx.begin();
        	em.persist(_test);
        	tx.commit();
        }
        catch(Exception e)
        {
        	 System.out.println( "[TagTest] unable to persist simple tag" );
        	 System.out.println(e.getMessage());
        }
        try{
        	tx.begin();
        	em.persist(_testTA);
        	tx.commit();
        }
        catch(Exception e)
        {
        	 System.out.println( "[TagTest] unable to persist TagAgent" );
        	 System.out.println(e.getMessage());
        }
        em.close();
	}
	
	public void testGetHTMLFormAnnotationStatus()
	{
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("annotations");
        EntityManager newEm = emf.createEntityManager();
        EntityTransaction newTx = newEm.getTransaction();
        newTx.begin();
        List annotationstatus = newEm.createQuery("from AnnotationStatus").getResultList();
        System.out.println( annotationstatus.size() + " annotationStatus found" );
        AnnotationStatus loadedAnnotation ;
        for (Object u : annotationstatus) 
        {
        	loadedAnnotation = (AnnotationStatus) u;
            System.out.println("[AnnotationTest] AnnotationStatus id : " + loadedAnnotation.getId()  
            		+ " comment : " + loadedAnnotation.getComment()
            		+ " Label : " + loadedAnnotation.getLabel()
            		+ " html form : " + loadedAnnotation.getHTMLForm("uid")
            		);
        }
        newTx.commit();
        newEm.close();
        // Shutting down the application
        emf.close();
	}
}
