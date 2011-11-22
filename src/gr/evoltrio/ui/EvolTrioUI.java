/**
 * This file is part of EvolTrio.
 *
 * EvolTrio is licensed under the GPLv3.
 *
 * For licensing information please see the file license.txt included with EvolTrio
 * or have a look at the top of class gr.evoltrio.core.Evolution which representatively
 * includes the EvolTrio license policy applicable for any file delivered with EvolTrio.
 */
package gr.evoltrio.ui;

import gr.evoltrio.core.MusicChromosome;
import gr.evoltrio.midi.MusicConfiguration;
import gr.evoltrio.midi.SongBuilder;

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
import org.apache.pivot.util.Resources;
import org.apache.pivot.util.concurrent.Task;
import org.apache.pivot.util.concurrent.TaskListener;
import org.apache.pivot.wtk.ActivityIndicator;
import org.apache.pivot.wtk.BoxPane;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.FileBrowserSheet;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.ListButton;
import org.apache.pivot.wtk.ListButtonSelectionListener;
import org.apache.pivot.wtk.Meter;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Sheet;
import org.apache.pivot.wtk.SheetCloseListener;
import org.apache.pivot.wtk.Slider;
import org.apache.pivot.wtk.SliderValueListener;
import org.apache.pivot.wtk.TaskAdapter;
import org.apache.pivot.wtk.Window;

/**
 * @author Konstantinos Georgiadis
 * @since 0.0.1
 */
public class EvolTrioUI extends Window implements Bindable {

    private ArrayList<Component> activeComponents;

    // Music Panel
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
    private ListButton instrumentSetListButton = null;

    // Evolutionary Panel
    private Slider selectFromPrevGenSlider = null;
    private Label selectFromPrevGenLabel = null;

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
    private PushButton startEvolutionButton;
    private PushButton pauseEvolutionButton;
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

    // TODO remove this

    private BoxPane chartHolder;

    public BoxPane getChartHolder() {
        return chartHolder;
    }

    @Override
    public void initialize(Map<String, Object> namespace, URL location,
            Resources resources) {

        URL url = EvolTrioUI.class.getResource("icons/evoltrio.png");
        this.setIcon(url);
        
        activeComponents = new ArrayList<Component>();

        evolutionChart = (LineChartView) namespace
                .get("evolutionLineChartView");

        List data = new ArrayList<ValueSeries<Point>>();
        // data = new SynchronizedList<ValueSeries<Point>>(nonSyncList);
        data.add(new ValueSeries<Point>("cool"));

        data.getListListeners().add(
                new ListListener.Adapter<ValueSeries<Point>>() {

                    @Override
                    public void itemInserted(List<ValueSeries<Point>> list,
                            int index) {
                        System.out.println("cool!!");
                    }

                });

        chartHolder = (BoxPane) namespace.get("chartHolder");
        // evolRunner = new EvolutionRunner(evolTrioUI, evolutionChart, new
        // String[]{"-1"});
        // evolutionTask = new EvolutionTask(this, evolutionChart,
        // new String[] { "-1" });

        // BXMLSerializer bxmlSerializer = new BXMLSerializer();
        // namespace.get
        // -----------------
        // Music Items Panel
        // -----------------
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

        for (String duration : MusicConfiguration.STATIC_DURATION_MAP)
            ((List<Object>) begDurListButton.getListData()).add(duration);
        
        begDurListButton.setSelectedIndex(2); //eight
        activeComponents.add(begDurListButton);

        keyNoteListButton = (ListButton) namespace.get("keyNoteListButton");

        for (String note : MusicConfiguration.NOTES)
            ((List<Object>) keyNoteListButton.getListData()).add(note);

        keyNoteListButton.setSelectedIndex(0);
        activeComponents.add(keyNoteListButton);

        // Tempo List Button
        tempoListButton = (ListButton) namespace.get("tempoListButton");

        for (String tempo : MusicConfiguration.TEMPOS)
            ((List<Object>) tempoListButton.getListData()).add(tempo);

        tempoListButton.setSelectedIndex(((List<Object>) tempoListButton
                .getListData()).indexOf("Allegro"));

        tempoListButton.getListButtonSelectionListeners().add(
                new ListButtonSelectionListener.Adapter() {

                    @Override
                    public void selectedItemChanged(ListButton listButton,
                            Object previousSelectedItem) {
                        updateTempo();
                    }
                });

        
        // Instrument Set List Button
        instrumentSetListButton = (ListButton) namespace.get("instrumentSetListButton");
        
        for (String set: MusicConfiguration.INSTRUMENT_CATEGORIES)
            ((List<Object>) instrumentSetListButton.getListData()).add(set);
        instrumentSetListButton.setSelectedIndex(0);
        
        instrumentSetListButton.getListButtonSelectionListeners().add(
                new ListButtonSelectionListener.Adapter() {

                    @Override
                    public void selectedItemChanged(ListButton listButton,
                            Object previousSelectedItem) {
                        updateInstrumentSet();
                    }
                });
        
        // Instrument List Button
        // TODO rename organ to instrument
        organListButton = (ListButton) namespace.get("organListButton");
        updateInstrumentSet();
        organListButton.setSelectedIndex(0);
        updateOrgan();

        organListButton.getListButtonSelectionListeners().add(
                new ListButtonSelectionListener.Adapter() {

                    @Override
                    public void selectedItemChanged(ListButton listButton,
                            Object previousSelectedItem) {
                        updateOrgan();
                    }
                });

        // evolutionary
        selectFromPrevGenSlider = (Slider) namespace
                .get("selectFromPrevGenSlider");
        activeComponents.add(selectFromPrevGenSlider);
        selectFromPrevGenLabel = (Label) namespace
                .get("selectFromPrevGenLabel");

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

        selectFromPrevGenSlider.getSliderValueListeners().add(
                new SliderValueListener() {

                    @Override
                    public void valueChanged(Slider slider, int previousValue) {
                        updateSelectFromPrevGen();

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

        // reset Button
        resetButton = (PushButton) namespace.get("resetButton");
        resetButton.setEnabled(false);
        resetButton.setTooltipText("Reset current evolution");
        // evolve Button
        evolveButton = (PushButton) namespace.get("evolveButton");
        evolveButton.setTooltipText("Start evolution!");
        // start evolution Button
        startEvolutionButton = (PushButton) namespace
                .get("startEvolutionButton");
        startEvolutionButton.setVisible(false);
        startEvolutionButton.setTooltipText("Resume current evolution");
        // pause evolution
        pauseEvolutionButton = (PushButton) namespace
                .get("pauseEvolutionButton");
        pauseEvolutionButton.setVisible(false);
        pauseEvolutionButton.setTooltipText("Pause current evolution");
        // play current chromosome
        playButton = (PushButton) namespace.get("playButton");
        playButton.setTooltipText("Listen to the best!");
        playButton.setEnabled(false);
        // save current chromsome
        saveButton = (PushButton) namespace.get("saveButton");
        saveButton.setTooltipText("Save the best!");
        saveButton.setEnabled(false);

        resetButton.getButtonPressListeners().add(new ButtonPressListener() {

            @Override
            public void buttonPressed(Button button) {
                evolutionTask.reset();

                activityIndicator.setActive(false);
                enableComponents();
                evolveButton.setVisible(true);
                startEvolutionButton.setVisible(false);
                pauseEvolutionButton.setVisible(false);
                resetButton.setEnabled(false);
                playButton.setEnabled(false);
                saveButton.setEnabled(false);

                reset();
            }
        });

        evolveButton.getButtonPressListeners().add(new ButtonPressListener() {
            @Override
            public void buttonPressed(Button button) {
                startEvolution();
            }
        });

        pauseEvolutionButton.getButtonPressListeners().add(
                new ButtonPressListener() {

                    @Override
                    public void buttonPressed(Button button) {
                        evolutionTask.pause();
                        activityIndicator.setActive(false);

                        startEvolutionButton.setVisible(true);
                        pauseEvolutionButton.setVisible(false);
                    }
                });

        // TODO rename to resume
        startEvolutionButton.getButtonPressListeners().add(
                new ButtonPressListener() {

                    @Override
                    public void buttonPressed(Button button) {
                        evolutionTask.start();
                        activityIndicator.setActive(true);
                        evolutionTask.execute(taskAdapter);

                        startEvolutionButton.setVisible(false);
                        pauseEvolutionButton.setVisible(true);
                    }
                });

        playButton.getButtonPressListeners().add(new ButtonPressListener() {

            @Override
            public void buttonPressed(Button button) {

                PlayMusicTask playTask = new PlayMusicTask(playButton,
                        (MusicChromosome) evolutionTask.getEvolution()
                                .getPopulation().getFittestChromosome());
                playTask.execute(new TaskListener<String>() {
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

                        // System.err.println(task.getFault());
                    }
                });
            }
        });

        saveButton.getButtonPressListeners().add(new ButtonPressListener() {

            @Override
            public void buttonPressed(Button button) {
                final FileBrowserSheet fileBrowserSheet = new FileBrowserSheet();
                fileBrowserSheet.setMode(FileBrowserSheet.Mode
                        .valueOf("SAVE_AS"));
                fileBrowserSheet.setSelectedFile(new File(fileBrowserSheet
                        .getRootDirectory(), "song.mid"));
                fileBrowserSheet.open(EvolTrioUI.this,
                        new SheetCloseListener() {

                            @Override
                            public void sheetClosed(Sheet sheet) {
                                MusicChromosome[] phrases = new MusicChromosome[1];
                                phrases[0] = (MusicChromosome) evolutionTask
                                        .getEvolution().getPopulation()
                                        .getFittestChromosome();
                                // TODO change this!
                                phrases[0].updateAbsolute();
                                File selectedFile = fileBrowserSheet
                                        .getSelectedFile();
                                System.out.println(phrases[0]);
                                SongBuilder sb = new SongBuilder(selectedFile,
                                        phrases, MusicConfiguration
                                                .getInstance());
                                sb.buildSong();
                                sb.saveTheSong();

                            }
                        });

            }
        });

    } // end of startup

    private void startEvolution() {
        // disable all active components: slides and list buttons.
        activityIndicator.setActive(true);

        // Configuration.reset();
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
        pauseEvolutionButton.setVisible(true);
        resetButton.setEnabled(true);
        playButton.setEnabled(true);
        saveButton.setEnabled(true);
        disableComponents();

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
    private void updatePhraseNotes() {
        phraseNotesLabel.setText("" + phraseNotesSlider.getValue());
    }

    private void updateIntJump() {
        intJumpLabel.setText("" + intJumpSlider.getValue());
    }

    private void updateDurJump() {
        durJumpLabel.setText("" + durJumpSlider.getValue());
    }

    private void updateTempo() {
        MusicConfiguration.getInstance().setTempo(
                tempoListButton.getSelectedItem().toString());
    }

    private void updateOrgan() {
        MusicConfiguration.getInstance().setSoloOrgan((instrumentSetListButton.getSelectedIndex()*8) +
                organListButton.getSelectedIndex());
    }
    
    private void updateInstrumentSet() {
        int selectedSet = instrumentSetListButton.getSelectedIndex();
        int firstInstrument = selectedSet * 8;
        int lastInstrument = (selectedSet+1) * 8;
        // remove all previous data
        organListButton.getListData().clear();
        
        for(int i = firstInstrument; i<lastInstrument; i++)
            ((List<Object>) organListButton.getListData()).add(MusicConfiguration.INSTRUMENT_NAMES.get(i));
        organListButton.setSelectedIndex(0);
    }

    private void updateOctave() {
        octaveLabel.setText(Integer.toString(octaveSlider.getValue()));
    }

    private void updateSelectFromPrevGen() {
        selectFromPrevGenLabel.setText("" + selectFromPrevGenSlider.getValue()
                + "%");
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

    // disable all active components
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
        bestChromosomeLabel.setText("-");

    }

    private void setupOptions() {
        MusicConfiguration.getInstance().setPhraseNotes(
                phraseNotesSlider.getValue());
        MusicConfiguration.getInstance().setMaxIntervalJump(
                intJumpSlider.getValue());
        MusicConfiguration.getInstance().setMaxDurationJump(
                durJumpSlider.getValue());
        MusicConfiguration.getInstance().setOctave(octaveSlider.getValue());

        MusicConfiguration.getInstance().setStaticBeginningDuration(
                begDurListButton.getSelectedIndex());

        MusicConfiguration.getInstance().setRootNote(
                keyNoteListButton.getSelectedItem().toString());

        MusicConfiguration.getInstance().setTempo(
                tempoListButton.getSelectedItem().toString());

        evolutionTask
                .getEvolution()
                .getEvolConf()
                .setSelectFromPrevGen(
                        (double) selectFromPrevGenSlider.getValue() / 100d);
        evolutionTask.getEvolution().getEvolConf()
                .setCrossoverRate((double) crossoverSlider.getValue() / 100d);
        evolutionTask.getEvolution().getEvolConf()
                .setMutationRate(mutationSlider.getValue());
        evolutionTask.getEvolution().getEvolConf()
                .setPopSize(popSizeSlider.getValue());
    }
}
