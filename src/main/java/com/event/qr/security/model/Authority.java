package com.event.qr.security.model;





public class Authority {


	private Long id;


	private AuthorityName name;

	public Authority(){

	}

	public Authority(AuthorityName name,Long id){
		this.name=name;
		this.id=id;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AuthorityName getName() {
		return name;
	}

	public void setName(AuthorityName name) {
		this.name = name;
	}


}