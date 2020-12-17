package br.com.juliano.appclient.model;

import java.io.Serializable;

import javax.persistence.Transient;

public abstract class BaseModel implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Override  
    public Object clone() throws CloneNotSupportedException {  
        Object copia = super.clone();
        return copia;  
    } 
	
	@Transient
	public abstract Object getKeyValue();
	
	public boolean equals(Object other) {
		return other instanceof BaseModel && (this.getKeyValue() != null) ? this.getKeyValue().equals(((BaseModel) other).getKeyValue()) : (other == this);
	}
		
	public int hashCode() {
		return getKeyValue() != null ? this.getClass().hashCode() + getKeyValue().hashCode() : super.hashCode();
	}
}
