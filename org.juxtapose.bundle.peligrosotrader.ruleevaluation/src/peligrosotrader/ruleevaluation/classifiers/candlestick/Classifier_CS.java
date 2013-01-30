package peligrosotrader.ruleevaluation.classifiers.candlestick;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import peligrosotrader.ruleevaluation.util.OmxFeed;
import peligrosotrader.util.db.Sample;

import weka.associations.Apriori;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.rules.M5Rules;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.M5P;
import weka.core.Instances;

public class Classifier_CS {

	private J48 m_classifier = null;
	
	public void createClassifier(Vector<Vector<Sample>> inVec)throws Exception{
		
		m_classifier = new J48();
		m_classifier.setMinNumObj(50);
		m_classifier.setUnpruned(true);
		
		Instances data = ExampleCreator_CS.createData();
		data = ExampleCreator_CS.createInstances(inVec, data);
		m_classifier.buildClassifier(data);
		
		String tree = m_classifier.toString();
		String treeRows[] = tree.split("\n");
		
		double bestRuleSplit = 0;
		String bestSplitStr = "";
		
		for(int i = 0; i < treeRows.length; i++){
			String[] split = treeRows[i].split("[()]");
			if(split.length > 1){
				String[] divs = split[1].split("[^0-9.]");
				if(divs.length == 2){
					double talg = new Double(divs[0]);
					double namn = new Double(divs[1]);
					
					double div = talg/namn;
					
					if(bestRuleSplit < div){
						bestRuleSplit = div;
						bestSplitStr = split[1];
					}
				}
			}
			
		}
		System.out.println(m_classifier.toString());
		
		System.out.println(bestSplitStr+"   "+bestRuleSplit);		

	}
	
	public String evaluateClassifier(Vector<Vector<Sample>> inVec)throws Exception{
	
		Instances data = ExampleCreator_CS.createData();
		data = ExampleCreator_CS.createInstances(inVec, data);
		
		Evaluation eval = new Evaluation(data);
		eval.evaluateModel(m_classifier, data);
		
		return eval.toSummaryString();
	}
	
	public static void main(String args[]){
		
		Classifier_CS classifier = new Classifier_CS();
		ArrayList<String> tickers = OmxFeed.getOmxTickers();
		
		Calendar startCal = Calendar.getInstance();
		startCal.set(Calendar.YEAR, 2000);
		startCal.set(Calendar.MONTH, Calendar.JANUARY);
		startCal.set(Calendar.DAY_OF_MONTH, 1);
		
		Calendar endCal = Calendar.getInstance();
		endCal.set(Calendar.YEAR, 2005);
		endCal.set(Calendar.MONTH, Calendar.JULY);
		endCal.set(Calendar.DAY_OF_MONTH, 1);
		
		Vector<Vector<Sample>> samples = OmxFeed.getSampleMatrix(tickers, startCal, endCal);

		try{
			classifier.createClassifier(samples);
			
			
			Calendar startCalT = Calendar.getInstance();
			startCalT.set(Calendar.YEAR, 2005);
			startCalT.set(Calendar.MONTH, Calendar.JANUARY);
			startCalT.set(Calendar.DAY_OF_MONTH, 1);
			
			Calendar endCalT = Calendar.getInstance();
			endCalT.set(Calendar.YEAR, 2006);
			endCalT.set(Calendar.MONTH, Calendar.JULY);
			endCalT.set(Calendar.DAY_OF_MONTH, 12);
			
			Vector<Vector<Sample>> testSamples = OmxFeed.getSampleMatrix(tickers, startCalT, endCalT);

			
			System.out.println(classifier.evaluateClassifier(testSamples));
			
		}catch(Exception e){e.printStackTrace();}	
	}
}
