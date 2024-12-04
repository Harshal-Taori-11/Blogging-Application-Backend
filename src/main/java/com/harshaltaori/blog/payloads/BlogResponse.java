package com.harshaltaori.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class BlogResponse {
	
	private List<BlogOutputDto> blogOutputDtos;
	private int pageNumber;
	private int pageSize;
	private boolean isLast;
	private int totalPages;
	private long totalElements;
}
