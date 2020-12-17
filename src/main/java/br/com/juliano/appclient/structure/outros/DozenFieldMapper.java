package br.com.juliano.appclient.structure.outros;

import org.dozer.CustomFieldMapper;
import org.dozer.classmap.ClassMap;
import org.dozer.fieldmap.FieldMap;
import org.hibernate.collection.internal.PersistentBag;
import org.hibernate.proxy.HibernateProxy;

public class DozenFieldMapper implements CustomFieldMapper {

	public boolean mapField(Object source, Object destination, Object sourceFieldValue, ClassMap classMap, FieldMap fieldMapping) {
		
		try {
			
			if ( (sourceFieldValue instanceof PersistentBag) && ( !((PersistentBag)sourceFieldValue).wasInitialized()) ) 
				return true;
			
			// Check if field is a Hibernate PersistentSet
	        if ( !(sourceFieldValue instanceof HibernateProxy) ) {
	            // Allow dozer to map as normal
	            return false;
	        }
	
	        HibernateProxy proxy = null;
	 	   try {
	 	   	  proxy = HibernateProxy.class.cast(sourceFieldValue);
	 	   } catch (Exception ex){
	 	   	  return false;
	 	   }
	 	   
		   if ( proxy != null && proxy.getHibernateLazyInitializer().isUninitialized() ) {
			   destination = null;
			   return true;
		   }
		   else
		   	  return false;
		} catch (NoClassDefFoundError e) {
			return false;
		}
	}


}
