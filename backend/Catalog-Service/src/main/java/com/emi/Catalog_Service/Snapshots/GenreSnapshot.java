package com.emi.Catalog_Service.Snapshots;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class GenreSnapshot {

	  @Column(name="genre_id", nullable=false)
	  private UUID id;
	  
	  @Column(name="genre_name", nullable=false)
	  private String name;
}
