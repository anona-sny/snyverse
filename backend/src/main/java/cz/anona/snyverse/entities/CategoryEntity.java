package cz.anona.snyverse.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "subcategory_of")
    private CategoryEntity subcategoryOf;
    private String name;

}
