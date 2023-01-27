package com.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserRole.class)
public abstract class UserRole_ extends com.domain.BaseEntity_ {

	public static volatile SingularAttribute<UserRole, String> roleName;
	public static volatile SingularAttribute<UserRole, Integer> id;
	public static volatile SingularAttribute<UserRole, String> userId;

	public static final String ROLE_NAME = "roleName";
	public static final String ID = "id";
	public static final String USER_ID = "userId";

}

