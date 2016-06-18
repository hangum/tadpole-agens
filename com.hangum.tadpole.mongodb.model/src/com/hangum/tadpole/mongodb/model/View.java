/*******************************************************************************
 * Copyright (c) 2013 hangum.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     hangum - initial API and implementation
 ******************************************************************************/
/**
 */
package com.hangum.tadpole.mongodb.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>View</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.hangum.tadpole.mongodb.model.View#getTableName <em>Table Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.hangum.tadpole.mongodb.model.MongodbPackage#getView()
 * @model
 * @generated
 */
public interface View extends Table {
	/**
	 * Returns the value of the '<em><b>Table Name</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Table Name</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Table Name</em>' reference.
	 * @see #setTableName(Table)
	 * @see com.hangum.tadpole.mongodb.model.MongodbPackage#getView_TableName()
	 * @model required="true"
	 * @generated
	 */
	Table getTableName();

	/**
	 * Sets the value of the '{@link com.hangum.tadpole.mongodb.model.View#getTableName <em>Table Name</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Table Name</em>' reference.
	 * @see #getTableName()
	 * @generated
	 */
	void setTableName(Table value);

} // View
