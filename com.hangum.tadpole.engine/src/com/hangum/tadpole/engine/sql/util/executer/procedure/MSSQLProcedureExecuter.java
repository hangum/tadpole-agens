/*******************************************************************************
 * Copyright (c) 2013 hangum.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     hangum - initial API and implementation
 ******************************************************************************/
package com.hangum.tadpole.engine.sql.util.executer.procedure;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.hangum.tadpole.engine.query.dao.mysql.ProcedureFunctionDAO;
import com.hangum.tadpole.engine.query.dao.rdb.InOutParameterDAO;
import com.hangum.tadpole.engine.query.dao.system.UserDBDAO;

/**
 * mssql procedure executer
 * 
 * @author hangum
 * @author nilriri
 * 
 */

public class MSSQLProcedureExecuter extends ProcedureExecutor {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(MSSQLProcedureExecuter.class);

	/**
	 * 
	 * @param procedureDAO
	 * @param userDB
	 */
	public MSSQLProcedureExecuter(ProcedureFunctionDAO procedureDAO, UserDBDAO userDB) {
		super(procedureDAO, userDB);
	}
	
	/**
	 * execute script
	 */
	public String getMakeExecuteScript() throws Exception {
		StringBuffer sbQuery = new StringBuffer();
		if ("FUNCTION".equalsIgnoreCase(procedureDAO.getType())){
			if(!"".equals(procedureDAO.getPackagename())){
				sbQuery.append("SELECT " + procedureDAO.getPackagename() + "." + procedureDAO.getSysName() + "(");
			}else{
				sbQuery.append("SELECT " + procedureDAO.getSysName() + "(");
			}
			
			List<InOutParameterDAO> inList = getInParameters();
			for(int i=0; i<inList.size(); i++) {
				InOutParameterDAO inOutParameterDAO = inList.get(i);

				String name = StringUtils.removeStart(inOutParameterDAO.getName(), "@");
				if(i == (inList.size()-1)) sbQuery.append(String.format(":%s", name));
				else sbQuery.append(String.format(":%s, ", name));
			}
			sbQuery.append(");");
			
		} else {
			
			// 프로시저 본체 만들기.
			if(!"".equals(procedureDAO.getPackagename())){
				sbQuery.append(String.format("EXEC %s.%s ", procedureDAO.getPackagename(), procedureDAO.getSysName()));
			} else {
				sbQuery.append(String.format("EXEC %s ", procedureDAO.getSysName()));
			}
			
			// out 설정
			List<InOutParameterDAO> inList = getInParameters();
			for(int i=0; i<inList.size(); i++) {
				InOutParameterDAO inOutParameterDAO = inList.get(i);
				String name = inOutParameterDAO.getName();
				String nameVal = StringUtils.removeStart(inOutParameterDAO.getName(), "@");
				if(i != (inList.size()-1)) sbQuery.append(String.format("%s = :%s, ", name, nameVal));
				else sbQuery.append(String.format("%s = :%s", name, nameVal));
			}

		}

		if(logger.isDebugEnabled()) logger.debug("Execute Procedure query is\t  " + sbQuery.toString());
		
		return sbQuery.toString();
	}
	
	@Override
	public boolean exec(List<InOutParameterDAO> parameterList, String strNullValue)  throws Exception {
		throw new Exception("Do now use the method");
	}
	
}
