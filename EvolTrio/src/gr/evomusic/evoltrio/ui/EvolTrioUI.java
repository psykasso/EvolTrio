/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gr.evomusic.evoltrio.ui;


import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.charts.LineChartView;
import org.apache.pivot.charts.content.Point;
import org.apache.pivot.charts.content.ValueSeries;
import org.apache.pivot.collections.List;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.ActivityIndicator;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.BoxPane;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.Meter;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Slider;
import org.apache.pivot.wtk.SliderValueListener;
import org.apache.pivot.wtk.TabPane;
import org.apache.pivot.wtk.Window;

public class EvolTrioUI implements Application {
	private Window window = null;
	
	private BoxPane evolutionBoxPane = null;

	private Slider crossoverSlider = null;
	private Label crossoverLabel = null;

	private Slider iterationsSlider = null;
	private Label iterationsLabel = null;

	private Slider minimumPopSlider = null;
	private Label minimumPopLabel = null;

	private Slider mutationsSlider = null;
	private Label mutationsLabel = null;

	private Slider newPopSlider = null;
	private Label newPopLabel = null;
	
	private PushButton evolveButton = null;
	private PushButton resetButton = null;
	private PushButton playButton = null;

	private LineChartView evolutionChart = null;
	
	private Meter evolutionMeter = null;
	private ActivityIndicator evolutionIndicator = null;

	// private ValueSeries<Point> fitnessData = new ValueSeries<Point>();

	private int cnt = 0;
	List<ValueSeries<Point>> data;
	
	private EvolTrioUI evolTrioUI;
	EvolutionRunner evolRunner;
	Thread evolThread;

	@Override
	public void startup(Display display, Map<String, String> properties)
			throws Exception {
		evolTrioUI = this;
		
		BXMLSerializer bxmlSerializer = new BXMLSerializer();
		window = (Window) bxmlSerializer.readObject(EvolTrioUI.class,
				"EvolTrioUI.bxml");
		window.open(display);
		
		evolRunner = new EvolutionRunner(evolTrioUI,new String[]{"-1"});
		evolThread = new Thread(evolRunner);
		
		evolutionBoxPane = (BoxPane) bxmlSerializer.getNamespace().get(
		"evolutionBoxPane");
		

		crossoverSlider = (Slider) bxmlSerializer.getNamespace().get(
				"crossoverSlider");
		crossoverLabel = (Label) bxmlSerializer.getNamespace().get(
				"crossoverLabel");

		crossoverSlider.getSliderValueListeners().add(
				new SliderValueListener() {

					@Override
					public void valueChanged(Slider slider, int previousValue) {
						updateCrossover();

					}
				});

		updateCrossover();

		iterationsSlider = (Slider) bxmlSerializer.getNamespace().get(
				"iterationsSlider");
		iterationsLabel = (Label) bxmlSerializer.getNamespace().get(
				"iterationsLabel");

		iterationsSlider.getSliderValueListeners().add(
				new SliderValueListener() {

					@Override
					public void valueChanged(Slider slider, int previousValue) {
						updateIterations();

					}
				});

		updateIterations();

		minimumPopSlider = (Slider) bxmlSerializer.getNamespace().get(
				"minimumPopSlider");
		minimumPopLabel = (Label) bxmlSerializer.getNamespace().get(
				"minimumPopLabel");

		minimumPopSlider.getSliderValueListeners().add(
				new SliderValueListener() {

					@Override
					public void valueChanged(Slider slider, int previousValue) {
						updateMinimumPop();

					}
				});

		updateMinimumPop();

		mutationsSlider = (Slider) bxmlSerializer.getNamespace().get(
				"mutationsSlider");
		mutationsLabel = (Label) bxmlSerializer.getNamespace().get(
				"mutationsLabel");

		mutationsSlider.getSliderValueListeners().add(
				new SliderValueListener() {

					@Override
					public void valueChanged(Slider slider, int previousValue) {
						updateMutations();

					}
				});

		updateMutations();

		newPopSlider = (Slider) bxmlSerializer.getNamespace().get(
				"newPopSlider");
		newPopLabel = (Label) bxmlSerializer.getNamespace().get("newPopLabel");

		newPopSlider.getSliderValueListeners().add(new SliderValueListener() {

			@Override
			public void valueChanged(Slider slider, int previousValue) {
				updateNewPop();

			}
		});

		updateNewPop();

		resetButton = (PushButton)bxmlSerializer.getNamespace().get("resetButton");
		evolveButton = (PushButton)bxmlSerializer.getNamespace().get("evolveButton");		
		playButton = (PushButton)bxmlSerializer.getNamespace().get("playButton");
		 
		
		
		resetButton.getButtonPressListeners().add(new ButtonPressListener() {
			
			@Override
			public void buttonPressed(Button button) {
				//evolutionBoxPane.setEnabled(false);
				
				evolRunner.resetEvolConf();
				evolveButton.setEnabled(true);
				
			}
		});
		
		evolveButton.getButtonPressListeners().add(new ButtonPressListener() {
			
			@Override
			public void buttonPressed(Button button) {
				button.setEnabled(false);
				
				// TODO Auto-generated method stub
				
				evolutionChart.setVisible(false);
				//evolutionIndicator.setActive(!evolutionIndicator.isActive());
				evolRunner.setup();			
				evolThread.start();
			}
		});

		evolutionChart = (LineChartView) bxmlSerializer.getNamespace().get(
				"evolutionChart");
		
//		evolutionIndicator = (ActivityIndicator) bxmlSerializer.getNamespace().get(
//		"evolutionIndicator");
		
		evolutionMeter = (Meter)bxmlSerializer.getNamespace().get("evolutionMeter");

	} // end of startup
	
	public Window getWindow(){
		return window;
	}

	/**
	 * @return the evolutionMeter
	 */
	public Meter getEvolutionMeter() {
		return evolutionMeter;
	}
	
	public ActivityIndicator getEvolutionIndicator(){
		return evolutionIndicator;
	}

	/**
	 * @return the evolutionChart
	 */
	public LineChartView getEvolutionChart() {
		return evolutionChart;
	}

	private void updateCrossover() {
		crossoverLabel.setText(Integer.toString(crossoverSlider.getValue())
				+ "%");
	}

	private void updateIterations() {
		iterationsLabel.setText(Integer.toString(iterationsSlider.getValue()));
		if (evolutionChart != null) {
			 Point p = new Point();
			 p.setX(cnt++);
			 p.setY((float) (Math.random()*100));

			 ValueSeries<Point> ser1 = data.get(0);
			 ser1.add(p);
			 data.update(0, ser1);

			 evolutionChart.setChartData(data);


		}
	}

	private void updateMinimumPop() {
		minimumPopLabel.setText(Integer.toString(minimumPopSlider.getValue())
				+ "%");
	}

	private void updateMutations() {
		mutationsLabel.setText(Integer.toString(mutationsSlider.getValue())
				+ "%");
	}

	private void updateNewPop() {
		newPopLabel.setText(Integer.toString(newPopSlider.getValue()) + "%");
	}

	@Override
	public boolean shutdown(boolean optional) {
		if (window != null) {
			window.close();
		}

		return false;
	}

	@Override
	public void suspend() {
	}

	@Override
	public void resume() {
	}
}
