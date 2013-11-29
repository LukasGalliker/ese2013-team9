package com.eseteam9.shoppyapp.valuesets;

import android.content.ContentValues;

public abstract class AbstractValueSet{
	abstract public ContentValues getContentValues(boolean withId);

	public ContentValues getContentValues(){
		return getContentValues(true);
	}
}
