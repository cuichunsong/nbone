package org.nbone.mx.datacontrols;

import java.io.Serializable;
/**
 * 
 * @author thinking
 * @since 2016-09-07
 * @version 1.0.2
 *
 */
public class Node  implements Serializable{
	
	private static final long serialVersionUID = -1223499797498109297L;
	
	private String id;
	private String name;
	private String title;
	private String url;
	private String  icon;
	
	private Location location; 
	
	/**
	 * 标记属性可用于图的回环标记等（可选项）
	 */
	private boolean flag ;
	
	public Node() {
	}

	public Node(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Node(String id, String name, String title) {
		this.id = id;
		this.name = name;
		this.title = title;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return new StringBuilder("id:").append(id).append(",name:").append(name).toString();
	}
	

}
