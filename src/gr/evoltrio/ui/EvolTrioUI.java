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

import gr.evoltrio.core.MusicChromosome;
import gr.evoltrio.exception.InvalidConfigurationException;
import gr.evoltrio.midi.MusicConfiguration;
import gr.evoltrio.midi.SongBuilder;
import gr.evoltrio.test.ui.SleepTask;

import java.io.File;
import java.net.URL;

import org.apache.pivot.beans.Bindable;
import org.apache.pivot.charts.LineChartView;
import org.apache.pivot.charts.content.Point;
import org.apache.pivot.charts.content.ValueSeries;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.List;
import org.apache.pivot.collections.ListListener;
import org.apache.pivot.collections.Map;
import org.apache.pivot.collections.concurrent.SynchronizedList;
import org.apache.pivot.util.Resources;
import org.apache.pivot.util.concurrent.Task;
import org.apache.pivot.util.concurrent.TaskListener;
import org.apache.pivot.wtk.ActivityIndicator;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.FileBrowserSheet;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.ListButton;
import org.apache.pivot.wtk.Meter;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Sheet;
import org.apache.pivot.wtk.SheetCloseListener;
import org.apache.pivot.wtk.Slider;
import org.apache.pivot.wtk.SliderValueListener;
import org.apache.pivot.wtk.TaskAdapter;
import org.apache.pivot.wtk.Window;
import org.apache.pivot.wtk.media.Image;
import org.jgap.Configuration;

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

    private TaskListener<String> taskListener;
    private TaskAdapter<String> taskAdapter;

    @Override
    public void initialize(Map<String, Object> namespace, URL location,
            Resources resources) {

        activeComponents = new ArrayList<Component>();

        evolutionChart = (LineChartView) namespace
                .get("evolutionLineChartView");
        evolutionChart.setEnabled(false);
//        evolutionChart.validate();
//        // evolutionChart.set
//        evolutionChart.setHeightLimits(100, 100);
//        evolutionChart.setHeight(10000);
//        evolutionChart.repaint(true);
        
       
        List data = new ArrayList<ValueSeries<Point>>();
        //data = new SynchronizedList<ValueSeries<Point>>(nonSyncList);
        data.add(new ValueSeries<Point>("cool"));
        
        data.getListListeners().add(new ListListener.Adapter<ValueSeries<Point>>() {

            @Override
            public void itemInserted(List<ValueSeries<Point>> list, int index) {
                System.out.println("cool!!");
            }
            
        });


        // evolRunner = new EvolutionRunner(evolTrioUI, evolutionChart, new
        // String[]{"-1"});
//        evolutionTask = new EvolutionTask(this, evolutionChart,
//                new String[] { "-1" });

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
                evolutionTask.setState(evolutionTask.RESET);
                
                activityIndicator.setActive(false);
                enableComponents();
                evolveButton.setVisible(true);
                toggleEvolutionButton.setVisible(false);
                resetButton.setEnabled(false);
                
                
                reset();
            }
        });

        evolveButton.getButtonPressListeners().add(new ButtonPressListener() {
            @Override
            public void buttonPressed(Button button) {
                startEvolution();
            }
        });

        toggleEvolutionButton.getButtonPressListeners().add(
                new ButtonPressListener() {

                    @Override
                    public void buttonPressed(Button button) {

                        if (evolutionTask.getState() == EvolutionTask.RUNNING) {
                            evolutionTask.setState(evolutionTask.PAUSED);
                            activityIndicator.setActive(false);

                            saveButton.setEnabled(true);
                            playButton.setEnabled(true);
                        } else if (evolutionTask.getState() == EvolutionTask.PAUSED) {
                            evolutionTask.setState(evolutionTask.RUNNING);
                            activityIndicator.setActive(true);
                            evolutionTask.execute(taskAdapter);

                            saveButton.setEnabled(false);
                            playButton.setEnabled(false);
                        }
                    }
                });

        playButton.getButtonPressListeners().add(new ButtonPressListener() {

            @Override
            public void buttonPressed(Button button) {
                activityIndicator.setEnabled(false);
            }
        });
        
        saveButton.getButtonPressListeners().add(new ButtonPressListener() {

            @Override
            public void buttonPressed(Button button) {
                final FileBrowserSheet fileBrowserSheet = new FileBrowserSheet();
                fileBrowserSheet.setMode(FileBrowserSheet.Mode.valueOf("SAVE_AS")) ;
                fileBrowserSheet.setSelectedFile(new File(fileBrowserSheet.getRootDirectory(), "song.mid"));
                fileBrowserSheet.open(EvolTrioUI.this, new SheetCloseListener() {
                    
                    @Override
                    public void sheetClosed(Sheet sheet) {
                        MusicChromosome[] phrases = new MusicChromosome[1];
                        phrases[0] = (MusicChromosome) evolutionTask.getEvolution().getPopulation().getFittestChromosome();                       
                        //TODO change this!
                        phrases[0].updateAbsolute();
                        File selectedFile = fileBrowserSheet.getSelectedFile();
                        System.out.println(phrases[0]);
                        SongBuilder sb = new SongBuilder(selectedFile, phrases, MusicConfiguration.getInstance());
                        sb.buildSong();
                        sb.saveTheSong();
                        
                    }
                });
                
                
            }
        });

    } // end of startup

    private void startEvolution() {
        //disable all active components: slides and list buttons.
        activityIndicator.setActive(true);
        
//        Configuration.reset();
        System.out.println("Starting evolution.");

        

        
        evolutionTask = new EvolutionTask(this, evolutionChart,
                new String[] { "-1" });
        setupOptions();
        taskListener = initTaskListener();
        taskAdapter = new TaskAdapter<String>(taskListener);

        evolutionTask.setupEvolutionTask();
        evolutionTask.start();
        // Image pauseImg = new Image();
        // evolveButton.setButtonData();
        evolutionTask.execute(taskAdapter);

        evolveButton.setVisible(false);
        toggleEvolutionButton.setVisible(true);
        resetButton.setEnabled(true);
        
    }

    public TaskListener<String> initTaskListener() {
        return new TaskListener<String>() {
            @Override
            public void taskExecuted(Task<String> task) {
                // activityIndicator.setActive(false);
                setEnabled(true);

                System.out.println("Synchronous task execution complete: \""
                        + task.getResult() + "\"");
            }

            @Override
            public void executeFailed(Task<String> task) {
                // activityIndicator.setActive(false);
                setEnabled(true);

                // System.err.println(task.getFault());
            }
        };
    }

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
    }

    private void updatePhraseNotes() {
        phraseNotesLabel.setText("" + phraseNotesSlider.getValue());
    }

    private void updateIntJump() {
        intJumpLabel.setText("" + intJumpSlider.getValue());
    }

    private void updateDurJump() {
        durJumpLabel.setText("" + durJumpSlider.getValue());
    }

    private void updateOctave() {
        octaveLabel.setText(Integer.toString(octaveSlider.getValue()));
    }

    private void updateCrossover() {
        crossoverLabel.setText("" + crossoverSlider.getValue() + "%");
    }

    private void updateMutation() {
        mutationLabel.setText("" + mutationSlider.getValue() + "%");
    }

    private void updatePopSize() {
        popSizeLabel.setText("" + popSizeSlider.getValue());
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

    public void reset() {

        iterationLabel.setText("" + 0);
        fitnessLabel.setText("" + 0);
        
    }
    
    private void setupOptions() {
        
        MusicConfiguration.getInstance().setRestOffset(
                restOffsetSlider.getValue());
        MusicConfiguration.getInstance().setPhraseNotes(
                phraseNotesSlider.getValue());
        MusicConfiguration.getInstance().setMaxIntervalJump(
                intJumpSlider.getValue());
        MusicConfiguration.getInstance().setMaxDurationJump(
                durJumpSlider.getValue());
        MusicConfiguration.getInstance()
                .setOctave(octaveSlider.getValue());

        evolutionTask.getEvolution().getEvolConf()
                .setCrossoverRate((double) crossoverSlider.getValue() / 100d);
        evolutionTask.getEvolution().getEvolConf()
                .setMutationRate(mutationSlider.getValue());
        evolutionTask.getEvolution().getEvolConf()
                .setPopSize(popSizeSlider.getValue());
    }
}
