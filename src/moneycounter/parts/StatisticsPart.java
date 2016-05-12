 
package moneycounter.parts;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import moneycounter.model.OperationCategory;
import moneycounter.model.OperationData;
import moneycounter.model.TableModel;
import moneycounter.views.StatisticsView;

public class StatisticsPart {
	private StatisticsView view;
	
	private TableModel model = TableModel.getInstance();
	
	private static final Map <Integer, String> monthName = new TreeMap<>();
	private static final Map <Integer, Integer> monthDayCount = new TreeMap<>();
	
	static {
		monthName.put(0, "Январь");
		monthName.put(1, "Февраль");
		monthName.put(2, "Март");
		monthName.put(3, "Апрель");
		monthName.put(4, "Май");
		monthName.put(5, "Июнь");
		monthName.put(6, "Июль");
		monthName.put(7, "Август");
		monthName.put(8, "Сентябрь");
		monthName.put(9, "Октябрь");
		monthName.put(10, "Ноябрь");
		monthName.put(11, "Декабрь");
		
		monthDayCount.put(0, 31); 
		monthDayCount.put(1, 28); // ошибка в подсчете будет лишь в 2020 году, допилю к этому времени ;)
		monthDayCount.put(2, 31);
		monthDayCount.put(3, 30);
		monthDayCount.put(4, 31);
		monthDayCount.put(5, 30);
		monthDayCount.put(6, 31);
		monthDayCount.put(7, 31);
		monthDayCount.put(8, 30);
		monthDayCount.put(9, 31);
		monthDayCount.put(10, 30);
		monthDayCount.put(11, 31);
	}
	
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
	

	
	public void updateStatistics() {
		view.clear();
		// 3 последних месяца
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		//Integer months[] = {timestamp.getMonth() - 2, timestamp.getMonth() - 1, timestamp.getMonth()};
		ArrayList<Integer> months = new ArrayList<Integer>() {
			{
				int currentMonth = timestamp.getMonth();
				add(currentMonth - 2 >= 0 ? currentMonth - 2 : currentMonth + 11);
				add(currentMonth - 1 >= 0 ? currentMonth - 1 : currentMonth + 11);
				add(currentMonth);
			}
		};
		
		Map <OperationCategory, Double> allSumMap = new HashMap<>();
		double allTotalSum = 0;
		
		Map <OperationCategory, Double> sumMap[] = new HashMap[3];
		double totalSum[] = new double[3];
		for (int i = 0; i < 3; i++) {
			sumMap[i] =  new HashMap<>();
		}
		
		Timestamp earlestDate = new Timestamp(System.currentTimeMillis());
		Timestamp latestDate = new Timestamp(System.currentTimeMillis());
		
		for (OperationData data : model.getData()) {
			// for the all time
			Timestamp currentTimestamp = data.getDate();
			if (earlestDate.after(currentTimestamp)) {
				earlestDate = new Timestamp(currentTimestamp.getTime());
			}
			if (latestDate.before(currentTimestamp)) {
				latestDate = new Timestamp(currentTimestamp.getTime());
			}
			Double currentSum = allSumMap.get(data.getCategory());
			if (currentSum == null)
				currentSum = 0.;
			currentSum += data.getSum();
			allTotalSum += data.getSum();
			allSumMap.put(data.getCategory(), currentSum);
			
			// for the 3 last months 
			int currentMonthIndex = months.indexOf(data.getDate().getMonth());
			if (currentMonthIndex == -1)
				continue;
			
			currentSum = sumMap[currentMonthIndex].get(data.getCategory());
			if (currentSum == null)
				currentSum = 0.;
			currentSum += data.getSum();
			totalSum[currentMonthIndex] += data.getSum();
			sumMap[currentMonthIndex].put(data.getCategory(), currentSum);
		}
		
		
		
		double totalFst = 0;
		int countFst = monthDayCount.get(months.get(0));
		double totalSnd = 0;
		int countSnd = monthDayCount.get(months.get(1));
		double totalThrd = 0;
		int countThrd = latestDate.getDate();
		double totalThree = 0;
		int threeCount = countFst + countSnd + countThrd;
		double totalAll = 0;
		long allCount = getDateDiff(earlestDate, latestDate, TimeUnit.DAYS);
		double dohodFst = 0;
		double dohodSnd = 0;
		double dohodThrd = 0;
		double dohodThree = 0;
		double dohodAll = 0;

		view.add("", monthName.get(months.get(0)), monthName.get(months.get(1)), monthName.get(months.get(2)), "За 3 месяца", "Всего");
		for (OperationCategory category : OperationCategory.values()) {
			Double fst = sumMap[0].get(category);
			if (fst == null)
				fst = 0.;
			Double snd = sumMap[1].get(category);
			if (snd == null)
				snd = 0.;
			Double thrd = sumMap[2].get(category);
			if (thrd == null)
				thrd = 0.;
			double three = fst + snd + thrd;
			Double all = allSumMap.get(category);
			if (all == null)
				all = 0.;
			
			if (category != OperationCategory.ЗАРПЛАТА) {
				totalFst += fst;
				totalSnd += snd;
				totalThrd += thrd;
				totalThree += three;
				totalAll += all;
			} else {
				dohodFst += fst;
				dohodSnd += snd;
				dohodThrd += thrd;
				dohodThree += three;
				dohodAll += all;
			}
					
			view.add(category.getDescription(), String.format("%.2f", fst) + " | " + String.format("%.2f", fst / countFst)
										 , String.format("%.2f", snd) + " | " + String.format("%.2f", snd / countSnd)
										 , String.format("%.2f", thrd) + " | " + String.format("%.2f", thrd / countThrd)
										 , String.format("%.2f", three) + " | " + String.format("%.2f", three / threeCount)
										 , String.format("%.2f", all) + " | " + String.format("%.2f", all / allCount));
		}
			view.add("Всё", String.format("%.2f", totalFst) + " | " + String.format("%.2f", totalFst / countFst)
			 , String.format("%.2f", totalSnd) + " | " + String.format("%.2f", totalSnd / countSnd)
			 , String.format("%.2f", totalThrd) + " | " + String.format("%.2f", totalThrd / countThrd)
			 , String.format("%.2f", totalThree) + " | " + String.format("%.2f", totalThree / threeCount)
			 , String.format("%.2f", totalAll) + " | " + String.format("%.2f", totalAll / allCount));
			
			view.add("______", "______", "______", "______", "______", "______");
			view.add("Доход", "" + String.format("%.2f", dohodFst), "" + String.format("%.2f", dohodSnd), "" 
					+ String.format("%.2f", dohodThrd), "" + String.format("%.2f", dohodThree), "" + String.format("%.2f", dohodAll));
			view.add("Дельта +/-", "" + String.format("%.2f", (dohodFst - totalFst)), "" + String.format("%.2f", (dohodSnd - totalSnd))
					, "" + String.format("%.2f", (dohodThrd - totalThrd))
					, "" + String.format("%.2f", (dohodThree - totalThree)), "" + String.format("%.2f", (dohodAll - totalAll)));
	}
	
	@PostConstruct
	public void postConstruct(Composite parent) {
		GridLayout gl = new GridLayout(1, false);
        parent.setLayout(gl);
        
		Label lbl = new Label(parent, SWT.NONE);
		lbl.setText("Статистика будет здесь. Причем разнообразная!");
        
		view = new StatisticsView(parent);
        
        updateStatistics();
	}
}