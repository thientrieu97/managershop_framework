package com.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProductOrder.class)
public abstract class ProductOrder_ extends com.domain.BaseEntity_ {

	public static volatile SingularAttribute<ProductOrder, Product> product;
	public static volatile SingularAttribute<ProductOrder, Integer> qty;
	public static volatile SingularAttribute<ProductOrder, Integer> id;
	public static volatile SingularAttribute<ProductOrder, Order> order;

	public static final String PRODUCT = "product";
	public static final String QTY = "qty";
	public static final String ID = "id";
	public static final String ORDER = "order";

}

