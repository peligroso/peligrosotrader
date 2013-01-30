package peligrosotrader.ruleevaluation;

import java.util.Calendar;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import peligrosotrader.ruleevaluation.mining.RuleMiningStarter;
import peligrosotrader.ruleevaluation.mining.RuleMining_QPipe;
import peligrosotrader.ruleevaluation.rules.RuleEval_inOut;
import peligrosotrader.ruleevaluation.rules.RuleStarter;
import peligrosotrader.ruleevaluation.rules.RuleTerm_ATH;
import peligrosotrader.ruleevaluation.rules.RuleTerm_ATL;
import peligrosotrader.ruleevaluation.rules.RuleTerm_BB;
import peligrosotrader.ruleevaluation.rules.RuleTerm_BigDownDay;
import peligrosotrader.ruleevaluation.rules.RuleTerm_BigUpMove;
import peligrosotrader.ruleevaluation.rules.RuleTerm_BigUpMoveNVolume;
import peligrosotrader.ruleevaluation.rules.RuleTerm_BrokenTrendSupport;
import peligrosotrader.ruleevaluation.rules.RuleTerm_CS;
import peligrosotrader.ruleevaluation.rules.RuleTerm_DayHigh;
import peligrosotrader.ruleevaluation.rules.RuleTerm_DayOfMonth;
import peligrosotrader.ruleevaluation.rules.RuleTerm_DaysInRow;
import peligrosotrader.ruleevaluation.rules.RuleTerm_DoubleBigDownDay;
import peligrosotrader.ruleevaluation.rules.RuleTerm_DoubleMA;
import peligrosotrader.ruleevaluation.rules.RuleTerm_DropDay;
import peligrosotrader.ruleevaluation.rules.RuleTerm_EndOfMonth;
import peligrosotrader.ruleevaluation.rules.RuleTerm_HighVolume;
import peligrosotrader.ruleevaluation.rules.RuleTerm_KeyReversal;
import peligrosotrader.ruleevaluation.rules.RuleTerm_LineTrend;
import peligrosotrader.ruleevaluation.rules.RuleTerm_MA;
import peligrosotrader.ruleevaluation.rules.RuleTerm_MAResistance;
import peligrosotrader.ruleevaluation.rules.RuleTerm_MASupport;
import peligrosotrader.ruleevaluation.rules.RuleTerm_MAVar;
import peligrosotrader.ruleevaluation.rules.RuleTerm_MultiOsc;
import peligrosotrader.ruleevaluation.rules.RuleTerm_MyRSI;
import peligrosotrader.ruleevaluation.rules.RuleTerm_PosTrendSupport;
import peligrosotrader.ruleevaluation.rules.RuleTerm_QPipe;
import peligrosotrader.ruleevaluation.rules.RuleTerm_RSI;
import peligrosotrader.ruleevaluation.rules.RuleTerm_RSIAdapt;
import peligrosotrader.ruleevaluation.rules.RuleTerm_RSIAdaptNeg;
import peligrosotrader.ruleevaluation.rules.RuleTerm_RallyDay;
import peligrosotrader.ruleevaluation.rules.RuleTerm_Random;
import peligrosotrader.ruleevaluation.rules.RuleTerm_ResistancePoints;
import peligrosotrader.ruleevaluation.rules.RuleTerm_Scalp;
import peligrosotrader.ruleevaluation.rules.RuleTerm_ScalpEasy;
import peligrosotrader.ruleevaluation.rules.RuleTerm_StrongResistanceScalp;
import peligrosotrader.ruleevaluation.rules.RuleTerm_StrongSupportPoints;
import peligrosotrader.ruleevaluation.rules.RuleTerm_StrongResistancePoints;
import peligrosotrader.ruleevaluation.rules.RuleTerm_SupportPoints;
import peligrosotrader.ruleevaluation.rules.RuleTerm_TrendResistance;
import peligrosotrader.ruleevaluation.rules.RuleTerm_TrendSupport;
import peligrosotrader.ruleevaluation.rules.RuleTerm_TuToFri;
import peligrosotrader.ruleevaluation.rules.RuleType;


public class Activator implements BundleActivator {

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		Calendar startCal = Calendar.getInstance();
		startCal.set(Calendar.YEAR, 2000);
		startCal.set(Calendar.MONTH, Calendar.JANUARY);
		startCal.set(Calendar.DAY_OF_MONTH, 1);
		
		Calendar endCal = Calendar.getInstance();
		endCal.set(Calendar.YEAR, 2008);
		endCal.set(Calendar.MONTH, Calendar.JUNE);
		endCal.set(Calendar.DAY_OF_MONTH, 31);
		
		RuleType type = new RuleEval_inOut();
		RuleStarter starter = new RuleStarter();
		
		starter.startRuleEval(type, new RuleTerm_Scalp(), startCal, endCal);
		
//		RuleMiningStarter mine = new RuleMiningStarter(startCal, endCal, new RuleTerm_Random());
//		mine.startMining();
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
	}

}
