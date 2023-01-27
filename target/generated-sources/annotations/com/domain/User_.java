package com.domain;

import com.model.bo.Gender;
import com.model.bo.StatusCommon;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ extends com.domain.BaseEntity_ {

	public static volatile SingularAttribute<User, LocalDate> birthDay;
	public static volatile SingularAttribute<User, String> address;
	public static volatile SingularAttribute<User, String> role;
	public static volatile SingularAttribute<User, Gender> gender;
	public static volatile SingularAttribute<User, String> facebook;
	public static volatile SingularAttribute<User, BigDecimal> totalMoney;
	public static volatile SingularAttribute<User, String> fullName;
	public static volatile SingularAttribute<User, String> description;
	public static volatile SingularAttribute<User, String> activationKey;
	public static volatile SingularAttribute<User, String> userName;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, String> twitter;
	public static volatile SingularAttribute<User, String> phone;
	public static volatile SingularAttribute<User, String> identity;
	public static volatile SingularAttribute<User, String> imageUrl;
	public static volatile SingularAttribute<User, String> id;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, Integer> age;
	public static volatile SingularAttribute<User, StatusCommon> status;

	public static final String BIRTH_DAY = "birthDay";
	public static final String ADDRESS = "address";
	public static final String ROLE = "role";
	public static final String GENDER = "gender";
	public static final String FACEBOOK = "facebook";
	public static final String TOTAL_MONEY = "totalMoney";
	public static final String FULL_NAME = "fullName";
	public static final String DESCRIPTION = "description";
	public static final String ACTIVATION_KEY = "activationKey";
	public static final String USER_NAME = "userName";
	public static final String PASSWORD = "password";
	public static final String TWITTER = "twitter";
	public static final String PHONE = "phone";
	public static final String IDENTITY = "identity";
	public static final String IMAGE_URL = "imageUrl";
	public static final String ID = "id";
	public static final String EMAIL = "email";
	public static final String AGE = "age";
	public static final String STATUS = "status";

}

