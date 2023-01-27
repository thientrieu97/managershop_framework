package com.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Order.class)
public abstract class Order_ extends com.domain.BaseEntity_ {

	public static volatile SingularAttribute<Order, Double> totalPrice;
	public static volatile SingularAttribute<Order, String> name;
	public static volatile SingularAttribute<Order, Integer> id;

	public static final String TOTAL_PRICE = "totalPrice";
	public static final String NAME = "name";
	public static final String ID = "id";

}

