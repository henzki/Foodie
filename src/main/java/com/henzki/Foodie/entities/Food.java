package com.henzki.Foodie.entities;

import java.util.Arrays;

import javax.persistence.*;


@Entity
@Table(name="foods")
public class Food {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(length=30)
	private String name;
	
	@Column(length=10)
	private String time;
	
	@Column
	private String link;
	
	@Lob
    @Column(name = "image", length = Integer.MAX_VALUE, nullable = true)
	private byte[] image;
	
	@Column
	private String category;
	
	@Column
	private String cuisine;
	
	public Food() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	@Override
	public String toString() {
		return "Food [id=" + id + ", name=" + name + ", time=" + time + ", link=" + link + ", image="
				+ Arrays.toString(image) + ", category=" + category + ", cuisine=" + cuisine + "]";
	}
	
}
