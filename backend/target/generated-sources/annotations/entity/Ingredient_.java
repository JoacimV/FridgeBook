package entity;

import entity.Comestible;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-07T20:28:24")
@StaticMetamodel(Ingredient.class)
public class Ingredient_ { 

    public static volatile SingularAttribute<Ingredient, String> imagePath;
    public static volatile SingularAttribute<Ingredient, String> name;
    public static volatile SingularAttribute<Ingredient, Comestible> comestible;

}