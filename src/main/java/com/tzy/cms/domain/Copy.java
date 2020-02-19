package com.tzy.cms.domain;

import java.io.Serializable;

public class Copy implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private Integer id;
private String copytext;
private String url;
private String uid;
private String created;
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getCopytext() {
	return copytext;
}
public void setCopytext(String copytext) {
	this.copytext = copytext;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public String getUid() {
	return uid;
}
public void setUid(String uid) {
	this.uid = uid;
}
public String getCreated() {
	return created;
}
public void setCreated(String created) {
	this.created = created;
}
public Copy() {
	super();
}
public Copy(Integer id, String copytext, String url, String uid, String created) {
	super();
	this.id = id;
	this.copytext = copytext;
	this.url = url;
	this.uid = uid;
	this.created = created;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((copytext == null) ? 0 : copytext.hashCode());
	result = prime * result + ((created == null) ? 0 : created.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((uid == null) ? 0 : uid.hashCode());
	result = prime * result + ((url == null) ? 0 : url.hashCode());
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Copy other = (Copy) obj;
	if (copytext == null) {
		if (other.copytext != null)
			return false;
	} else if (!copytext.equals(other.copytext))
		return false;
	if (created == null) {
		if (other.created != null)
			return false;
	} else if (!created.equals(other.created))
		return false;
	if (id == null) {
		if (other.id != null)
			return false;
	} else if (!id.equals(other.id))
		return false;
	if (uid == null) {
		if (other.uid != null)
			return false;
	} else if (!uid.equals(other.uid))
		return false;
	if (url == null) {
		if (other.url != null)
			return false;
	} else if (!url.equals(other.url))
		return false;
	return true;
}
	
}
