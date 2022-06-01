package org.dekuan.airpassport.lib.models.rest;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.dekuan.airpassport.lib.exceptions.AirExceptions;

import java.util.ArrayList;
import java.util.List;


/**
 *	REST Array List Response
 *
 * 	{
 * 		"header ..."
 *		"body" :
 *		{
 *		 	"list" : [ ... ],
 *		 	"count" : n
 *		}
 * 	}
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder( builderMethodName = "responseArrayListBuilder" )
public class RestResponseArrayList<T> extends RestHeader
{
	@Builder.Default
	private final Body<T> body	= new Body<>();


	public void addListItem( T item )
	{
		if ( null == item )
		{
			throw new AirExceptions.InvalidParameter( "invalid item" );
		}

		this.body.getList().add( item );
	}
	public void setListItemAll( List<T> arrItems )
	{
		if ( null == arrItems )
		{
			throw new AirExceptions.InvalidParameter( "invalid arrItems" );
		}

		this.clearList();
		this.body.getList().addAll( arrItems );
	}
	public void removeListItem( T item )
	{
		if ( null == item )
		{
			throw new AirExceptions.InvalidParameter( "invalid item" );
		}

		this.body.getList().remove( item );
	}
	public void clearList()
	{
		this.body.getList().clear();
	}


	/**
	 *	@class	Body
	 */
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@SuperBuilder
	public static class Body<T>
	{
		@Builder.Default
		public List<T> list = new ArrayList<>();

		public int getCount()
		{
			return this.list.size();
		}
	}
}