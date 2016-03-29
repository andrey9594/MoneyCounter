 
package moneycounter.parts;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class StatisticsPart {
	@Inject
	public StatisticsPart() {
		
	}
	
	@PostConstruct
	public void postConstruct(Composite parent) {
		Label lbl = new Label(parent, SWT.NONE);
		lbl.setText("Статистика будет здесь. Причем разнообразная!");
	}
	
	
	
	
}