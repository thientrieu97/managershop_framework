package com.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Product.class)
public abstract class Product_ extends com.domain.BaseEntity_ {

	public static volatile SingularAttribute<Product, Integer> quantity;
	public static volatile SingularAttribute<Product, String> address;
	public static volatile SingularAttribute<Product, Integer> price;
	public static volatile SingularAttribute<Product, String> name;
	public static volatile SingularAttribute<Product, Double> weight;
	public static volatile SingularAttribute<Product, Integer> id;

	public static final String QUANTITY = "quantity";
	public static final String ADDRESS = "address";
	public static final String PRICE = "price";
	public static final String NAME = "name";
	public static final String WEIGHT = "weight";
	public static final String ID = "id";

}

