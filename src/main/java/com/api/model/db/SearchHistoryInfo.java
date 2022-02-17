package com.api.model.db;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(name="search_history")
public class SearchHistoryInfo {
	@Id
	private String keyword;
	private Long count = 0L;
	@CreationTimestamp
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime modifiedAt;
	
	@Builder
	private SearchHistoryInfo(String keyword, Long count) {
		this.keyword = keyword;
		this.count = count;
	}
	
}
