package com.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(BaseEntity.class)
public abstract class BaseEntity_ {

	public static volatile SingularAttribute<BaseEntity, Date> createdAt;
	public static volatile SingularAttribute<BaseEntity, Date> deletedAt;
	public static volatile SingularAttribute<BaseEntity, Date> updatedAt;

	public static final String CREATED_AT = "createdAt";
	public static final String DELETED_AT = "deletedAt";
	public static final String UPDATED_AT = "updatedAt";

}

