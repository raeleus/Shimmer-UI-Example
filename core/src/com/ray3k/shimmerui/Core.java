package com.ray3k.shimmerui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ray3k.tenpatch.TenPatchDrawable;

public class Core extends ApplicationAdapter {
    private Skin skin;
    private Stage stage;
    
    @Override
    public void create () {
        skin = new Skin(Gdx.files.internal("shimmer-ui.json"));
        stage = new com.badlogic.gdx.scenes.scene2d.Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    
        final Table root = new Table();
        root.setFillParent(true);
        root.setBackground(skin.getDrawable("bg-tile-ten"));
        stage.addActor(root);
    
        Window window = new Window("Shimmer UI - Skin for libGDX", skin);
        stage.addActor(window);
    
        window.getTitleTable().pad(5);
        Button button = new Button(skin, "close");
        window.getTitleTable().add(button).space(20);
        
        Table top = new Table();
        top.pad(5);
        ScrollPane scrollPaneTop = new ScrollPane(top, skin);
        scrollPaneTop.setFadeScrollBars(false);
        scrollPaneTop.setForceScroll(false, true);
        scrollPaneTop.setFlickScroll(false);
        
        Table bottom = new Table();
        bottom.pad(5);
        ScrollPane scrollPaneBottom = new ScrollPane(bottom, skin);
        scrollPaneBottom.setFadeScrollBars(false);
        scrollPaneBottom.setForceScroll(false, true);
        scrollPaneBottom.setFlickScroll(false);
        
        SplitPane splitPane = new SplitPane(scrollPaneTop, scrollPaneBottom, true, skin);
        window.add(splitPane);
        
        top.defaults().space(5);
        Table table = new Table();
        table.setBackground(skin.getDrawable("libgdx-bg-ten"));
        top.add(table).fillY();
        
        Image image = new Image(skin, "libgdx-animation");
        image.setScaling(Scaling.none);
        table.add(image);
    
        table = new Table();
        top.add(table);
        
        ButtonGroup<Button> buttonGroup = new ButtonGroup<Button>();
        buttonGroup.setMinCheckCount(0);
        
        table.defaults().space(2);
        TextButton textButton = new TextButton("Clip Manager", skin, "toggle");
        buttonGroup.add(textButton);
        table.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                TenPatchDrawable tenPatchDrawable = (TenPatchDrawable) ((TextButton) actor).getStyle().checked;
                tenPatchDrawable.setTime(0);
            }
        });
        textButton.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                TenPatchDrawable tenPatchDrawable = (TenPatchDrawable) ((TextButton) event.getListenerActor()).getStyle().over;
                tenPatchDrawable.setTime(0);
                event.stop();
            }
        });
    
        table.row();
        textButton = new TextButton("Video Editor", skin, "toggle");
        buttonGroup.add(textButton);
        table.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                TenPatchDrawable tenPatchDrawable = (TenPatchDrawable) ((TextButton) actor).getStyle().checked;
                tenPatchDrawable.setTime(0);
            }
        });
    
        table.row();
        textButton = new TextButton("Export", skin, "toggle");
        buttonGroup.add(textButton);
        table.add(textButton);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                TenPatchDrawable tenPatchDrawable = (TenPatchDrawable) ((TextButton) actor).getStyle().checked;
                tenPatchDrawable.setTime(0);
            }
        });
        
        top.row();
        
        Slider slider = new Slider(0, 1, .01f, false, skin, "scrubber");
        slider.setName("slider-scrubber");
        top.add(slider).growX();
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                updateProgressBars(((Slider) actor).getValue());
            }
        });
        
        top.row();
        table = new Table();
        top.add(table).growX();
        
        buttonGroup = new ButtonGroup<Button>();
        
        table.defaults().space(10);
        button = new Button(skin, "stop");
        button.setName("stop-button");
        button.setProgrammaticChangeEvents(false);
        buttonGroup.add(button);
        table.add(button);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((TenPatchDrawable) skin.getDrawable("libgdx-animation")).setTime(0);
                ((TenPatchDrawable) skin.getDrawable("libgdx-animation")).update(0);
            }
        });
    
        button = new Button(skin, "play");
        button.setProgrammaticChangeEvents(false);
        buttonGroup.add(button);
        table.add(button);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((TenPatchDrawable) skin.getDrawable("libgdx-animation")).setAutoUpdate(true);
            }
        });
    
        button = new Button(skin, "pause");
        button.setProgrammaticChangeEvents(false);
        buttonGroup.add(button);
        table.add(button);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((TenPatchDrawable) skin.getDrawable("libgdx-animation")).setAutoUpdate(false);
            }
        });
        
        bottom.defaults().space(5);
        table = new Table();
        bottom.add(table);
        
        table.defaults().space(5);
        Label label = new Label("Title:", skin);
        table.add(label).right();
        
        TextField textField = new TextField("", skin);
        table.add(textField);
    
        table.row();
        label = new Label("Cypher:", skin);
        table.add(label).right();
    
        textField = new TextField("", skin);
        textField.setPasswordCharacter('•');
        textField.setPasswordMode(true);
        table.add(textField);
        
        table.row();
        ImageTextButton imageTextButton = new ImageTextButton("Continuous Playback", skin);
        table.add(imageTextButton).colspan(2).left();
        
        table.row();
        label = new Label("Play Mode:", skin);
        table.add(label).right();
        
        SelectBox<String> selectBox = new SelectBox<String>(skin);
        selectBox.setItems("Normal", "Reverse", "Loop", "Loop Reversed", "Loop PingPong", "Loop Random");
        selectBox.setSelectedIndex(2);
        table.add(selectBox);
        selectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((TenPatchDrawable) skin.getDrawable("libgdx-animation")).setPlayMode(((SelectBox<String>) actor).getSelectedIndex());
                ((TenPatchDrawable) skin.getDrawable("libgdx-animation")).setTime(0);
            }
        });
    
        table.row();
        label = new Label("Rendering:", skin, "small");
        table.add(label).left().colspan(2).spaceTop(10);
        
        table.row();
        ProgressBar progressBar = new ProgressBar(0, 1, .01f, false, skin);
        progressBar.setName("progress-bar");
        table.add(progressBar).growX().colspan(2);
        
        buttonGroup = new ButtonGroup<Button>();
        table.defaults().colspan(2).left();
        table.row();
        imageTextButton = new ImageTextButton("Shining", skin, "radio");
        buttonGroup.add(imageTextButton);
        table.add(imageTextButton).spaceTop(10);
        
        table.row();
        imageTextButton = new ImageTextButton("Shimmering", skin, "radio");
        buttonGroup.add(imageTextButton);
        table.add(imageTextButton);
    
        table.row();
        imageTextButton = new ImageTextButton("Glistening", skin, "radio");
        buttonGroup.add(imageTextButton);
        table.add(imageTextButton);
        
        table = new Table();
        bottom.add(table).grow();
        
        table.defaults().space(5);
        Tree<Node, String> tree = new Tree<Node, String>(skin);
        table.add(tree).left().colspan(2);
    
        Node parentNode = new Node("glint");
        tree.add(parentNode);
    
        Node node = new Node("flash");
        parentNode.add(node);
        tree.expandAll();
    
        node = new Node("twinkle");
        parentNode.add(node);
        tree.expandAll();
    
        node = new Node("blink");
        parentNode.add(node);
        tree.expandAll();
    
        node = new Node("glare");
        parentNode.add(node);
        tree.expandAll();
        
        table.row();
        slider = new Slider(0, 1, .01f, true, skin);
        slider.setName("slider");
        table.add(slider).growY();
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                updateProgressBars(((Slider) actor).getValue());
            }
        });
        
        Touchpad touchpad = new Touchpad(0, skin);
        table.add(touchpad);
        
        window.pack();
        window.setPosition(stage.getWidth() / 2.0f, stage.getHeight() / 2.0f, Align.center);
        window.setPosition((int) window.getX(), (int) window.getY());
    }
    
    class Node extends Tree.Node<Node, String, Label> {
        public Node(String text) {
            super(new Label(text, skin, "small"));
            setValue(text);
        }
    }
    
    private void updateProgressBars(float value) {
        ((Slider) stage.getRoot().findActor("slider-scrubber")).setValue(value);
        ((Slider) stage.getRoot().findActor("slider")).setValue(value);
        ((ProgressBar) stage.getRoot().findActor("progress-bar")).setValue(value);
        ((TenPatchDrawable) skin.getDrawable("libgdx-animation")).setTime(value * 100f / 30f);
        ((TenPatchDrawable) skin.getDrawable("libgdx-animation")).update(0);
        ((Button) stage.getRoot().findActor("stop-button")).toggle();
    }
    
    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyJustPressed(Input.Keys.F5)) {
            dispose();
            create();
        }
        
        stage.act();
        stage.draw();
    }
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    
    @Override
    public void dispose () {
        stage.dispose();
        skin.dispose();
    }
}
