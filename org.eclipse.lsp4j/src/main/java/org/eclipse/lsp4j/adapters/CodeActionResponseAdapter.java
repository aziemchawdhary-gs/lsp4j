/*******************************************************************************
 * Copyright (c) 2018 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.lsp4j.adapters;

import java.util.ArrayList;

import org.eclipse.lsp4j.CodeAction;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.jsonrpc.json.adapters.CollectionTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapter;
import org.eclipse.lsp4j.jsonrpc.json.adapters.EitherTypeAdapter.PropertyChecker;
import org.eclipse.lsp4j.jsonrpc.messages.Either;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

public class CodeActionResponseAdapter implements TypeAdapterFactory {
	
	private static final TypeToken<Either<Command, CodeAction>> ELEMENT_TYPE
			= new TypeToken<Either<Command, CodeAction>>() {};

	@SuppressWarnings("unchecked")
	@Override
	public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
		TypeAdapter<Either<Command, CodeAction>> elementTypeAdapter = new EitherTypeAdapter<>(gson,
				ELEMENT_TYPE,
				new PropertyChecker("command", JsonPrimitive.class),
				new PropertyChecker("edit").or(new PropertyChecker("command", JsonObject.class)));
		return (TypeAdapter<T>) new CollectionTypeAdapter<>(gson, ELEMENT_TYPE.getType(), elementTypeAdapter, ArrayList::new);
	}
	
}
