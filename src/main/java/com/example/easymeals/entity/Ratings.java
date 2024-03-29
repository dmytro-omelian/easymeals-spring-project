package com.example.easymeals.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ratings")
@IdClass(RatingsId.class)
public class Ratings {

    @Id private Long userId;
    @Id private Long recipeId;

    @Min(0) @Max(5) private Double score;

}
