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
package gr.evoltrio.ui;

import gr.evoltrio.exception.InvalidConfigurationException;
import gr.evoltrio.midi.MusicConfiguration;
import gr.evoltrio.test.ui.SleepTask;

import java.net.URL;

import org.apache.pivot.beans.Bindable;
import org.apache.pivot.charts.LineChartView;
import org.apache.pivot.charts.content.Point;
import org.apache.pivot.charts.content.ValueSeries;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
import org.apache.pivot.collections.Map;
import org.apache.pivot.collections.concurrent.SynchronizedList;
import org.apache.pivot.util.Resources;
import org.apache.pivot.util.concurrent.Task;
import org.apache.pivot.util.concurrent.TaskListener;
import org.apache.pivot.wtk.ActivityIndicator;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.ListButton;
import org.apache.pivot.wtk.Meter;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Slider;
import org.apache.pivot.wtk.SliderValueListener;
import org.apache.pivot.wtk.TaskAdapter;
import org.apache.pivot.wtk.Window;
import org.apache.pivot.wtk.media.Image;

public class EvolTrioUI extends Window implements Bindable {

    private ArrayList<Component> activeComponents;

    // Music Panel
    private Slider restOffsetSlider = null;
    private Label restOffsetLabel = null;

    private Slider phraseNotesSlider = null;
    private Label phraseNotesLabel = null;

    private Slider intJumpSlider = null;
    private Label intJumpLabel = null;

    private Slider durJumpSlider = null;
    private Label durJumpLabel = null;

    private Slider octaveSlider = null;
    private Label octaveLabel = null;

    private ListButton begDurListButton = null;
    private ListButton keyNoteListButton = null;
    private ListButton tempoListButton = null;
    private ListButton organListButton = null;

    // Evolutionary Panel
    private Slider crossoverSlider = null;
    private Label crossoverLabel = null;

    private Slider mutationSlider = null;
    private Label mutationLabel = null;

    private Slider popSizeSlider = null;
    private Label popSizeLabel = null;

    // Info Panel
    private Label iterationLabel = null;
    private Label fitnessLabel = null;
    private Label bestChromosomeLabel = null;

    private Meter evolutionMeter = null;
    private ActivityIndicator evolutionIndicator = null;

    // Buttons
    private PushButton resetButton;
    private PushButton evolveButton;
    private PushButton toggleEvolutionButton;
    private PushButton playButton;
    private PushButton saveButton;

    // Fitness chart
    private LineChartView evolutionChart = null;

    // private ValueSeries<Point> fitnessData = new ValueSeries<Point>();

    private int cnt = 0;
    List<ValueSeries<Point>> data;

    // EvolutionRunner evolRunner;
    private EvolutionTask evolutionTask;

    private ActivityIndicator activityIndicator = null;

    @Override
    public void initialize(Map<String, Object> namespace, URL location,
            Resources resources) {

        activeComponents = new ArrayList<Component>();

        evolutionChart = (LineChartView) namespace
                .get("evolutionLineChartView");
        // evolutionChart.set
        evolutionChart.setHeightLimits(100, 100);
        evolutionChart.setHeight(10000);
        evolutionChart.repaint(true);
        evolutionChart.setVisible(true);

        // evolRunner = new EvolutionRunner(evolTrioUI, evolutionChart, new
        // String[]{"-1"});
        evolutionTask = new EvolutionTask(this, evolutionChart,
                new String[] { "-1" });

        // BXMLSerializer bxmlSerializer = new BXMLSerializer();
        // namespace.get
        // -----------------
        // Music Items Panel
        // -----------------
        restOffsetSlider = (Slider) namespace.get("restOffsetSlider");
        activeComponents.add(restOffsetSlider);
        restOffsetLabel = (Label) namespace.get("restOffsetLabel");

        phraseNotesSlider = (Slider) namespace.get("phraseNotesSlider");
        activeComponents.add(phraseNotesSlider);
        phraseNotesLabel = (Label) namespace.get("phraseNotesLabel");

        intJumpSlider = (Slider) namespace.get("intJumpSlider");
        activeComponents.add(intJumpSlider);
        intJumpLabel = (Label) namespace.get("intJumpLabel");

        durJumpSlider = (Slider) namespace.get("durJumpSlider");
        activeComponents.add(durJumpSlider);
        durJumpLabel = (Label) namespace.get("durJumpLabel");

        octaveSlider = (Slider) namespace.get("octaveSlider");
        activeComponents.add(octaveSlider);
        octaveLabel = (Label) namespace.get("octaveLabel");

        begDurListButton = (ListButton) namespace.get("begDurListButton");
        activeComponents.add(begDurListButton);
        keyNoteListButton = (ListButton) namespace.get("keyNoteListButton");
        activeComponents.add(keyNoteListButton);
        tempoListButton = (ListButton) namespace.get("tempoListButton");
        activeComponents.add(tempoListButton);
        organListButton = (ListButton) namespace.get("organListButton");
        activeComponents.add(organListButton);

        crossoverSlider = (Slider) namespace.get("crossoverSlider");
        activeComponents.add(crossoverSlider);
        crossoverLabel = (Label) namespace.get("crossoverLabel");

        mutationSlider = (Slider) namespace.get("mutationSlider");
        activeComponents.add(mutationSlider);
        mutationLabel = (Label) namespace.get("mutationLabel");

        popSizeSlider = (Slider) namespace.get("popSizeSlider");
        activeComponents.add(popSizeSlider);
        popSizeLabel = (Label) namespace.get("popSizeLabel");

        iterationLabel = (Label) namespace.get("iterationLabel");
        fitnessLabel = (Label) namespace.get("fitnessLabel");
        bestChromosomeLabel = (Label) namespace.get("bestChromosomeLabel");

        activityIndicator = (ActivityIndicator) namespace
                .get("activityIndicator");

        // iterationLabel.getLabelListeners().add(
        // new LabelListener() {
        //
        // @Override
        // public void textChanged(Label label, String previousText) {
        // label.setText(previousText);
        // System.out.println(previousText);
        // }
        // });

        // Music Panel Listeners
        restOffsetSlider.getSliderValueListeners().add(
                new SliderValueListener() {

                    @Override
                    public void valueChanged(Slider slider, int previousValue) {
                        updateRestOffset();

                    }
                });

        phraseNotesSlider.getSliderValueListeners().add(
                new SliderValueListener() {

                    @Override
                    public void valueChanged(Slider slider, int previousValue) {
                        updatePhraseNotes();

                    }
                });

        intJumpSlider.getSliderValueListeners().add(new SliderValueListener() {

            @Override
            public void valueChanged(Slider slider, int previousValue) {
                updateIntJump();

            }
        });

        durJumpSlider.getSliderValueListeners().add(new SliderValueListener() {

            @Override
            public void valueChanged(Slider slider, int previousValue) {
                updateDurJump();

            }
        });

        octaveSlider.getSliderValueListeners().add(new SliderValueListener() {

            @Override
            public void valueChanged(Slider slider, int previousValue) {
                updateOctave();

            }
        });

        crossoverSlider.getSliderValueListeners().add(
                new SliderValueListener() {

                    @Override
                    public void valueChanged(Slider slider, int previousValue) {
                        updateCrossover();

                    }
                });

        mutationSlider.getSliderValueListeners().add(new SliderValueListener() {

            @Override
            public void valueChanged(Slider slider, int previousValue) {
                updateMutation();

            }
        });

        popSizeSlider.getSliderValueListeners().add(new SliderValueListener() {

            @Override
            public void valueChanged(Slider slider, int previousValue) {
                updatePopSize();

            }
        });

        resetButton = (PushButton) namespace.get("resetButton");
        resetButton.setEnabled(false);
        evolveButton = (PushButton) namespace.get("evolveButton");
        toggleEvolutionButton = (PushButton) namespace
                .get("toggleEvolutionButton");
        toggleEvolutionButton.setVisible(false);
        playButton = (PushButton) namespace.get("playButton");
        playButton.setEnabled(false);
        saveButton = (PushButton) namespace.get("saveButton");
        saveButton.setEnabled(false);

        resetButton.getButtonPressListeners().add(new ButtonPressListener() {

            @Override
            public void buttonPressed(Button button) {
                enableComponents();
            }
        });

        evolveButton.getButtonPressListeners().add(new ButtonPressListener() {
            @Override
            public void buttonPressed(Button button) {
                activityIndicator.setActive(true);
                // setEnabled(false);

                System.out.println("Starting evolution.");

                TaskListener<String> taskListener = new TaskListener<String>() {
                    @Override
                    public void taskExecuted(Task<String> task) {
                        // activityIndicator.setActive(false);
                        setEnabled(true);

                        System.out
                                .println("Synchronous task execution complete: \""
                                        + task.getResult() + "\"");
                    }

                    @Override
                    public void executeFailed(Task<String> task) {
                        // activityIndicator.setActive(false);
                        setEnabled(true);

                        //System.err.println(task.getFault());
                    }
                };

                evolutionTask.setupEvolutionTask();
                evolutionTask.start();
                // Image pauseImg = new Image();
                // evolveButton.setButtonData();
                evolutionTask.execute(new TaskAdapter<String>(taskListener));

                evolveButton.setVisible(false);
                toggleEvolutionButton.setVisible(true);
                disableComponents();
            }
        });
        
        toggleEvolutionButton.getButtonPressListeners().add(new ButtonPressListener() {

            @Override
            public void buttonPressed(Button button) {
                if(evolutionTask.getState() == EvolutionTask.RUNNING) {
                    evolutionTask.setState(evolutionTask.PAUSED);
                    activityIndicator.setEnabled(false);
                }
                else if(evolutionTask.getState() == EvolutionTask.PAUSED) {
                    evolutionTask.setState(evolutionTask.RUNNING);
                    activityIndicator.setEnabled(true);
                }
            }
        });

        playButton.getButtonPressListeners().add(new ButtonPressListener() {

            @Override
            public void buttonPressed(Button button) {
                activityIndicator.setEnabled(false);
            }
        });

    } // end of startup

    /**
     * @return the evolutionMeter
     */
    public Meter getEvolutionMeter() {
        return evolutionMeter;
    }

    public ActivityIndicator getEvolutionIndicator() {
        return evolutionIndicator;
    }

    /**
     * @return the evolutionChart
     */
    public LineChartView getEvolutionChart() {
        return evolutionChart;
    }

    public synchronized Label getIterationLabel() {
        return iterationLabel;
    }

    public Label getFitnessLabel() {
        return fitnessLabel;
    }

    public Label getBestChromosomeLabel() {
        return bestChromosomeLabel;
    }

    // Updater functions for the slider listeners
    private void updateRestOffset() {
        restOffsetLabel.setText(Integer.toString(restOffsetSlider.getValue()));
        MusicConfiguration.getInstance().setRestOffset(
                restOffsetSlider.getValue());
    }

    private void updatePhraseNotes() {
        phraseNotesLabel.setText("" + phraseNotesSlider.getValue());
        MusicConfiguration.getInstance().setPhraseNotes(
                phraseNotesSlider.getValue());
    }

    private void updateIntJump() {
        intJumpLabel.setText("" + intJumpSlider.getValue());
        MusicConfiguration.getInstance().setMaxIntervalJump(
                phraseNotesSlider.getValue());
    }

    private void updateDurJump() {
        durJumpLabel.setText("" + durJumpSlider.getValue());
        MusicConfiguration.getInstance().setMaxDurationJump(
                phraseNotesSlider.getValue());
    }

    private void updateOctave() {
        octaveLabel.setText(Integer.toString(octaveSlider.getValue()));
        MusicConfiguration.getInstance()
                .setOctave(phraseNotesSlider.getValue());
    }

    private void updateCrossover() {
        crossoverLabel.setText("" + crossoverSlider.getValue() + "%");
        evolutionTask.getEvolution().getEvolConf()
                .setCrossoverRate((double) crossoverSlider.getValue() / 100d);
    }

    private void updateMutation() {
        mutationLabel.setText("" + mutationSlider.getValue() + "%");
        evolutionTask.getEvolution().getEvolConf()
                .setMutationRate(mutationSlider.getValue());
    }

    private void updatePopSize() {
        popSizeLabel.setText("" + popSizeSlider.getValue());
        evolutionTask.getEvolution().getEvolConf()
                .setPopSize(popSizeSlider.getValue());
    }

    private void updateIterations() {
        // iterationsLabel.setText(Integer.toString(iterationsSlider.getValue()));
        if (evolutionChart != null) {
            Point p = new Point();
            p.setX(cnt++);
            p.setY((float) (Math.random() * 100));

            ValueSeries<Point> ser1 = data.get(0);
            ser1.add(p);
            data.update(0, ser1);

            evolutionChart.setChartData(data);

        }
    }

    public void disableComponents() {
        for (Component comp : activeComponents) {
            comp.setEnabled(false);
        }
    }

    public void enableComponents() {
        for (Component comp : activeComponents) {
            if (comp != null)
                comp.setEnabled(true);
            else
                System.out.println(comp);
        }
    }
}
