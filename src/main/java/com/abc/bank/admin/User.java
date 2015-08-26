package com.abc.bank.admin;

import java.util.Objects;

public abstract class User implements Comparable<User>{

	private String name;
	private Integer uniqueId;

	protected User(String name,Integer uniqueId){
		this.name = name;
		this.uniqueId = uniqueId;
	}
	
	  @Override
	    public int hashCode(){
	    	return Objects.hash(name,uniqueId);
	    }
	    
	    @Override
	    public boolean equals(Object o){
	    	if (o == null){
	    		return false;
	    	}
	    	if (getClass() != o.getClass()){
	    		return false;
	    	}
	    	return isEquals((User)o);
	    	
	    }

		private boolean isEquals(User o) {
			return Objects.equals(name, o.name)
				&& Objects.equals(uniqueId, o.uniqueId);
		}

		public int compareTo(User o) {
			return uniqueId-o.uniqueId;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getUniqueId() {
			return uniqueId;
		}

		public void setUniqueId(Integer uniqueId) {
			this.uniqueId = uniqueId;
		}

}
