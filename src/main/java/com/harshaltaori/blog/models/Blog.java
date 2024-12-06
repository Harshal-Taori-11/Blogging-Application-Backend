package com.harshaltaori.blog.models;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "blogs")
@NoArgsConstructor
@Getter
@Setter 
public class Blog {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer blogId;
	
	private String blogTitle;
	
	private String blogContent;
	
	private String currentStatus;
	
	private Date addedOn;
	
	private Date lastUpdatedOn;
	
	private String imageUrl;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToMany
	@JoinTable(name = "blog_category",joinColumns = @JoinColumn(name = "blog_id"),inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories = new HashSet<>();
	
	@OneToMany(mappedBy = "blog" ,cascade = CascadeType.ALL,fetch = FetchType.LAZY )
	private List<Comment> comments;
}
