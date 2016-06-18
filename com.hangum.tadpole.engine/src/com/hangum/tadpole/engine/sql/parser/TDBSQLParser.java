/*******************************************************************************
 * Copyright (c) 2015 hangum.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     hangum - initial API and implementation
 ******************************************************************************/
package com.hangum.tadpole.engine.sql.parser;

import com.hangum.tadpole.engine.sql.parser.dto.QueryInfoDTO;

/**
 * Tadpole SQL parser
 * 		
 * @author hangum
 *
 */
public interface TDBSQLParser {
	/**
	 * sql parser
	 * 
	 * @param sql
	 * @return
	 */
	QueryInfoDTO parser(String sql);
}
