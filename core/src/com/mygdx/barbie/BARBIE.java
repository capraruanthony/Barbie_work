package com.mygdx.barbie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import Screens.OpeningScreen;
import Screens.PlayScreen;

public class BARBIE extends Game {
	public static final int V_WIDTH = 400; //virtual width for our game
	public static final int V_HEIGHT = 208; //virtual width for our game
	public static final float PPM = 100;
	public static final short NOTHING_BIT = 0;
	public static final short GROUND_BIT = 1;
	public static final short BARBIE_BIT = 2;
	public static final short OBJECT_BIT = 4;
	public static final short ENEMY_BIT = 8;
	public static final short ENEMY_HEAD_BIT = 16;

	public SpriteBatch batch;

	public static AssetManager manager;

	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("barbie_music.ogg", Music.class);
		manager.load("barbiedie.wav", Sound.class);
		manager.finishLoading();


		setScreen(new OpeningScreen(this));




	}

	@Override
	public void render () {
		super.render();

	}

	@Override
	public void dispose() {
		super.dispose();
		manager.dispose();
		batch.dispose();
	}




}




