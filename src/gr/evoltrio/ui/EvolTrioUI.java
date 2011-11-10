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
import org.apache.pivot.wtk.ListButton;
import org.apache.pivot.wtk.Meter;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Slider;
import org.apache.pivot.wtk.SliderValueListener;
import org.apache.pivot.wtk.TabPane;
import org.apache.pivot.wtk.Window;

public class EvolTrioUI implements Application {
    private Window window = null;

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

    private Meter evolutionMeter = null;
    private ActivityIndicator evolutionIndicator = null;

    // Buttons
    private PushButton resetButton;
    private PushButton evolveButton;
    private PushButton playButton;

    // Fitness chart
    private LineChartView evolutionChart = null;
    
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

        // -----------------
        // Music Items Panel
        // -----------------
        restOffsetSlider = (Slider) bxmlSerializer.getNamespace().get(
                "restOffsetSlider");
        restOffsetLabel = (Label) bxmlSerializer.getNamespace().get(
                "restOffsetLabel");

        phraseNotesSlider = (Slider) bxmlSerializer.getNamespace().get(
                "phraseNotesSlider");
        phraseNotesLabel = (Label) bxmlSerializer.getNamespace().get(
                "phraseNotesLabel");

        intJumpSlider = (Slider) bxmlSerializer.getNamespace().get(
                "intJumpSlider");
        intJumpLabel = (Label) bxmlSerializer.getNamespace()
                .get("intJumpLabel");

        durJumpSlider = (Slider) bxmlSerializer.getNamespace().get(
                "durJumpSlider");
        durJumpLabel = (Label) bxmlSerializer.getNamespace()
                .get("durJumpLabel");

        octaveSlider = (Slider) bxmlSerializer.getNamespace().get(
                "octaveSlider");
        octaveLabel = (Label) bxmlSerializer.getNamespace().get("octaveLabel");

        begDurListButton = (ListButton) bxmlSerializer.getNamespace().get(
                "begDurListButton");
        keyNoteListButton = (ListButton) bxmlSerializer.getNamespace().get(
                "keyNoteListButton");
        tempoListButton = (ListButton) bxmlSerializer.getNamespace().get(
                "tempoButton");
        organListButton = (ListButton) bxmlSerializer.getNamespace().get(
                "organButton");

        crossoverSlider = (Slider) bxmlSerializer.getNamespace().get(
                "crossoverSlider");
        crossoverLabel = (Label) bxmlSerializer.getNamespace().get(
                "crossoverLabel");

        mutationSlider = (Slider) bxmlSerializer.getNamespace().get(
                "mutationSlider");
        mutationLabel = (Label) bxmlSerializer.getNamespace().get(
                "mutationLabel");

        popSizeSlider = (Slider) bxmlSerializer.getNamespace().get(
                "popSizeSlider");
        popSizeLabel = (Label) bxmlSerializer.getNamespace()
                .get("popSizeLabel");

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

        resetButton = (PushButton) bxmlSerializer.getNamespace().get(
                "resetButton");
        evolveButton = (PushButton) bxmlSerializer.getNamespace().get(
                "evolveButton");
        playButton = (PushButton) bxmlSerializer.getNamespace().get(
                "playButton");

        resetButton.getButtonPressListeners().add(new ButtonPressListener() {

            @Override
            public void buttonPressed(Button button) {
                // evolutionBoxPane.setEnabled(false);

                // evolRunner.resetEvolConf();
                evolRunner.setEvolutionRunning(false);
                evolveButton.setEnabled(true);

            }
        });

        evolveButton.getButtonPressListeners().add(new ButtonPressListener() {

            @Override
            public void buttonPressed(Button button) {
                button.setEnabled(false);

                // TODO Auto-generated method stub

                //evolutionChart.setVisible(false);
                // evolutionIndicator.setActive(!evolutionIndicator.isActive());
                // evolRunner.setup();
                evolThread.start();
            }
        });

        evolutionChart = (LineChartView) bxmlSerializer.getNamespace().get(
                "evolutionLineChartView");
        
        evolRunner = new EvolutionRunner(evolTrioUI, evolutionChart, new String[]{"-1"});
        evolThread = new Thread(evolRunner);

        // evolutionIndicator = (ActivityIndicator)
        // bxmlSerializer.getNamespace().get(
        // "evolutionIndicator");

        evolutionMeter = (Meter) bxmlSerializer.getNamespace().get(
                "evolutionMeter");

    } // end of startup

    public Window getWindow() {
        return window;
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

    // Updater functions for the slider listeners
    private void updateRestOffset() {
        restOffsetLabel.setText(Integer.toString(restOffsetSlider.getValue()));
    }

    private void updatePhraseNotes() {
        phraseNotesLabel
                .setText(Integer.toString(phraseNotesSlider.getValue()));
    }

    private void updateIntJump() {
        intJumpLabel.setText(Integer.toString(intJumpSlider.getValue()));
    }

    private void updateDurJump() {
        durJumpLabel.setText(Integer.toString(durJumpSlider.getValue()));
    }

    private void updateOctave() {
        octaveLabel.setText(Integer.toString(octaveSlider.getValue()));
    }

    private void updateCrossover() {
        crossoverLabel.setText(Integer.toString(crossoverSlider.getValue())
                + "%");
    }

    private void updateMutation() {
        mutationLabel
                .setText(Integer.toString(mutationSlider.getValue()) + "%");
    }

    private void updatePopSize() {
        popSizeLabel.setText(Integer.toString(popSizeSlider.getValue()));
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
