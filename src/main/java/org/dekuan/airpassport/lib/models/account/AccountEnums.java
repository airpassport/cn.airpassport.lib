package org.dekuan.airpassport.lib.models.account;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Getter
@Setter
@ToString
@SuperBuilder
public class AccountEnums
{
	@Getter
	@Setter
	@ToString
	@SuperBuilder
	public final static class Gender
	{
		public static final int UNSET		= 0;	//
		public static final int MALE		= 1;	//	男
		public static final int FEMALE		= 2;	//	女
		public static final int GENDERLESS	= 3;	//	中性
		public static final int UNKNOWN		= 4;	//	保密

		public static final Map<Integer, String> nameMap = new HashMap<Integer, String>(){{
			put( UNSET,		"未设置"   );
			put( MALE,		"男性" );
			put( FEMALE,		"女性" );
			put( GENDERLESS,	"中性" );
			put( UNKNOWN,		"保密" );
		}};
		public static Map<Integer, String> getValidGenders()
		{
			return nameMap.entrySet().stream()
				.filter( entry -> UNSET != entry.getKey() )
				.collect( Collectors.toMap( Map.Entry::getKey, Map.Entry::getValue ));
		}
		public static boolean isValid( int value )
		{
			return null != nameMap.getOrDefault( value, null );
		}
		public static boolean isValid( Integer gender )
		{
			return null != gender && isValid( gender.intValue() );
		}
	}

	@Getter
	@Setter
	@ToString
	@SuperBuilder
	public final static class Region
	{
		public static final String UNSET	= "-";
		public static final String CN		= "CN";
		public static final String US		= "US";

		public static final Map<String, String> nameMap = new HashMap<String, String>(){{
			put( UNSET,	"未设置" );
			put( CN,	"中国" );
			put( US,	"美国" );
		}};

		public static Map<String, String> getValidRegions()
		{
			return nameMap.entrySet().stream()
				.filter( entry -> ! UNSET.equalsIgnoreCase( entry.getKey() ) )
				.collect( Collectors.toMap( Map.Entry::getKey, Map.Entry::getValue ));
		}
		public static boolean isValid( String value )
		{
			return null != nameMap.getOrDefault( value, null );
		}
	}

	@Getter
	@Setter
	@ToString
	@SuperBuilder
	public final static class AuthType
	{
		public static final int UNSET		= 0;	//
		public static final int PHONE		= 1;	//	手机号码验证
		public static final int EMAIL		= 2;	//	邮件验证
		public static final int OAUTH_WECHAT	= 3;	//	微信授权

		public static final Map<Integer, String> nameMap = new HashMap<Integer, String>(){{
			put( UNSET,		"未设置"   );
			put( PHONE,		"手机号码" );
			put( EMAIL,		"电子邮件" );
			put( OAUTH_WECHAT,	"微信授权" );
		}};
		public static Map<Integer, String> getValidAuthTypes()
		{
			return nameMap.entrySet().stream()
				.filter( entry -> UNSET != entry.getKey() )
				.collect( Collectors.toMap( Map.Entry::getKey, Map.Entry::getValue ));
		}
		public static boolean isValid( int value )
		{
			return null != nameMap.getOrDefault( value, null );
		}
	}
}
