package com.bitnine.angens.manager.core.editors.parts.statistics;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.rap.addons.chart.basic.TimeDataGroup;
import org.eclipse.rap.addons.chart.basic.TimeDataItem2D;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;

import com.bitnine.agens.manager.engine.core.AgensManagerSQLImpl;
import com.bitnine.agens.manager.engine.core.dao.domain.Instance;
import com.bitnine.angens.manager.core.editors.parts.AgensChartComposite;
import com.bitnine.angens.manager.core.editors.template.AgensChartTemplate;
import com.hangum.tadpole.commons.util.ColorsSWTUtils;
import com.hangum.tadpole.engine.query.dao.system.UserDBDAO;

/**
 * Line transaction composite
 * 
 * 
 * http://jsfiddle.net/LukaszWiktor/rcL0uot9/ 의 셈플을 기준으로 timeseries chart로 수정하여야 합니다.
 * @author hangum
 *
 */
public class TimeseriesLineChartComposite extends AgensChartComposite {
	private static final Logger logger = Logger.getLogger(TimeseriesLineChartComposite.class);
	private String _TEMPLATE_CHART_HTML = "";
	
	/** define line chart */
	private Browser browserChart;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public TimeseriesLineChartComposite(Composite parent, UserDBDAO userDB, Instance instance) {
		super(parent, userDB, instance);
		setLayout(new GridLayout(1, false));

		Group grpTransactionStatistics = new Group(this, SWT.NONE);
		grpTransactionStatistics.setLayout(new GridLayout(1, false));
		GridData gd_grpTransactionStatistics = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_grpTransactionStatistics.minimumHeight = 200;
		gd_grpTransactionStatistics.minimumWidth = 200;
		gd_grpTransactionStatistics.heightHint = 200;
		gd_grpTransactionStatistics.widthHint = 200;
		grpTransactionStatistics.setLayoutData(gd_grpTransactionStatistics);
		grpTransactionStatistics.setText("Memory Usage");

		browserChart = new Browser(grpTransactionStatistics, SWT.NONE);
		browserChart.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		initializeUIData();
	}
	
	/**
	 * initialize ui data
	 */
	private void initializeUIData() {
		try {
			_TEMPLATE_CHART_HTML = AgensChartTemplate.getTimeseriesLineChart();
			browserChart.setText(_TEMPLATE_CHART_HTML);
		} catch (IOException e) {
			logger.error("get template htmll", e);
		}
	}
	
	/**
	 * refresh UI
	 * @param listCPU
	 */
	public void refreshUI(final List<?> listData) {
		try {
			final Display display = browserChart.getDisplay();
		    display.asyncExec( new Runnable() {
		    	public void run() {
		    		
		    		try {
		    			TimeDataGroup[] arryDataGroup = listData.toArray(new TimeDataGroup[listData.size()]);
		    			StringBuffer sbJson = new StringBuffer("[");
		    			for (TimeDataGroup timeDataGroup : arryDataGroup) {
		    				String strString = StringUtils.replace(timeDataGroup.toJson().toString(), "\"new Date(\\\"", "new Date(\"");
		    				strString = StringUtils.replace(strString, "\\\")\"", "\")");
		    				sbJson.append(strString).append(",");
						}
		    			String sbFullJson = StringUtils.removeEnd(sbJson.toString(), ",");
		    			sbFullJson += "]";
			    		String strHtml = StringUtils.replace(_TEMPLATE_CHART_HTML, AgensChartTemplate.TIMESERIESCHART_TEMPLATE, sbFullJson);
			    		
			    		browserChart.setText(strHtml);
			   			
		    		} catch(Exception e) {
		    			e.printStackTrace();
		    		}
		   			
		    	}
		    } );
			
		} catch(Exception e) {
			logger.error("initialize data", e);
		}
	}

	/**
	 * get chart data
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<?> getUIData() throws Exception {
		int lastSnapID = getLastSnapId();
		Map<String, Integer> mapParameter = new HashMap<>();
		if((lastSnapID-10) < 0) {
			mapParameter.put("start_snapid", 0);
		} else {
			mapParameter.put("start_snapid", (lastSnapID-10));
		}
		mapParameter.put("end_snapid", lastSnapID);
		
		// get memory data	
		List<Map> listLine = AgensManagerSQLImpl.getSQLMapQueryInfo(getUserDB(), "memory_usage", mapParameter);
		if(logger.isDebugEnabled()) logger.debug("===### data size is : " + listLine.size());
		if (listLine.isEmpty()) return new ArrayList<>();
		
		TimeDataItem2D[] memfree = new TimeDataItem2D[listLine.size()];
		TimeDataItem2D[] buffers = new TimeDataItem2D[listLine.size()];
		TimeDataItem2D[] cached = new TimeDataItem2D[listLine.size()];
		TimeDataItem2D[] swap = new TimeDataItem2D[listLine.size()];
		TimeDataItem2D[] dirty = new TimeDataItem2D[listLine.size()];
		for (int i=0; i<listLine.size(); i++) {
			Map mapData = listLine.get(i);
			
			String strTime = String.format("new Date(\"%s\")", ""+mapData.get("replace"));
			memfree[i] = new TimeDataItem2D(strTime, ((BigDecimal) mapData.get("memfree")).doubleValue());
			buffers[i] 	= new TimeDataItem2D(strTime, ((BigDecimal) mapData.get("buffers")).doubleValue());
			cached[i] 	= new TimeDataItem2D(strTime, ((BigDecimal) mapData.get("cached")).doubleValue());
			swap[i] 	= new TimeDataItem2D(strTime, ((BigDecimal) mapData.get("swap")).doubleValue());
			dirty[i] 	= new TimeDataItem2D(strTime, ((BigDecimal) mapData.get("dirty")).doubleValue());
		}
		
		List<TimeDataGroup> listDataGroup = new ArrayList<>();
		listDataGroup.add(new TimeDataGroup(memfree, "memfree", ColorsSWTUtils.CAT10_COLORS[1]));
		listDataGroup.add(new TimeDataGroup(buffers, "buffers", ColorsSWTUtils.CAT10_COLORS[2]));
		listDataGroup.add(new TimeDataGroup(cached, "cached", 	ColorsSWTUtils.CAT10_COLORS[3]));
		listDataGroup.add(new TimeDataGroup(swap, 	"swap", 	ColorsSWTUtils.CAT10_COLORS[4]));
		listDataGroup.add(new TimeDataGroup(dirty, 	"dirty", 	ColorsSWTUtils.CAT10_COLORS[5]));
		
		return listDataGroup;
	}
}
