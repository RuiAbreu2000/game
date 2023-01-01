package com.example.game;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import android.app.Application;
import android.graphics.Bitmap;

import com.example.game.databases.AppDatabase;
import com.example.game.databases.MonsterDex;
import com.example.game.graphics.MapLayouts;
import com.example.game.graphics.SpriteSheet;

public class SharedViewModel extends AndroidViewModel{

    // MAP CONSTANTS
    public static final int NUMBER_OF_MAP_ROWS = 4;
    public static final int NUMBER_OF_MAP_COLUMNS = 4;
    public static final int SPRITE_WIDTH_PIXELS = 256;
    public static final int SPRITE_HEIGHT_PIXELS = 256;
    public static final int MAP_X_SIZE = 1024;              //SPRITE_WIDTH_PIXELS * NUMBER_OF_MAP_COLUMNS
    public static final int MAP_Y_SIZE = 1024;              //SPRITE_HEIGHT_PIXELS * NUMBER_OF_MAP_ROWS
    public static final int TILESIZE = 256;

    // Important
    public AppDatabase db;
    public MapLayouts maplayouts;


    public SpriteSheet spritesheet;
    public SpriteSheet waterMonsterSpritesheet;
    // Current Zone Vars
    public int currentZoneLevel = 1;                // Level of Current Zone
    public String currentZone = null;

    public SharedViewModel(@NonNull Application application) {
        super(application);
        db = AppDatabase.getInstance(application);
        spritesheet = new SpriteSheet(application, "tiles");
        waterMonsterSpritesheet = new SpriteSheet(application, "waterMonsters");
        maplayouts = new MapLayouts(spritesheet);
    }

    // Get Map Functions
    public AppDatabase getDatabase(){
        return db;
    }

    public Bitmap getBitmap() {     // Returns Map Bitmap
        return maplayouts.getBitmap();
    }

    public int[] getTileMatrix() {  // Returns Array with Tile Numbers
        return maplayouts.getTileIndexMap().clone();
    }

    public int[] getMonsterArray(){ return maplayouts.getMonsterArray().clone();}

    public void getMap(String map) { // Builds map on MapLayouts
        switch(map) {
            case "grass":
                currentZone = "grass";                               // Updates current Zone
                maplayouts.grassMap();                               // Builds Map
                maplayouts.buildMonsterArray(currentZoneLevel);      // Build Array with monster location on map
            case "dirt":
                currentZone = "dirt";
                maplayouts.dirtMap();
            case "clearsky":
                currentZone = "clearsky";
                maplayouts.clearskytMap();
            case "stormmy":
                currentZone = "stormmy";
                maplayouts.stormmyMap();
        }
    }

    public SpriteSheet getWaterMonsterSpriteSheet() {
        return this.waterMonsterSpritesheet;
    }

    // Add to Database Functions
    public void createNewMonster(MonsterDex newMonster){
        db.monsterDexDao().addMonster(newMonster);
    }
}