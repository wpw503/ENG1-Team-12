package com.teamonehundred.pixelboat;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.teamonehundred.pixelboat.entities.AIBoat;
import com.teamonehundred.pixelboat.entities.Boat;
import com.teamonehundred.pixelboat.entities.CollisionObject;
import com.teamonehundred.pixelboat.entities.Obstacle;
import com.teamonehundred.pixelboat.entities.ObstacleBranch;
import com.teamonehundred.pixelboat.entities.ObstacleDuck;
import com.teamonehundred.pixelboat.entities.ObstacleFloatingBranch;
import com.teamonehundred.pixelboat.entities.ObstacleLaneWall;
import com.teamonehundred.pixelboat.entities.PlayerBoat;

public class GameState implements Serializable {

    private enum ObjectType {
        BOAT,
        DUCK,
        BRANCH,
        FLOATING_BRANCH,
        LANE_WALL,
    }

    private class SerializableGameObject implements Serializable {
        
        /**
         *
         */
        private static final long serialVersionUID = 304000522049061736L;
        float x;
        float y;
        float width;
        float height;
        float rotation;
        ObjectType type;
        float speed;
        Boolean is_shown;
        
        // Boat specific attributes
        protected String name = "default boat name";

        protected float durability = 1.f;  // from 0 to 1
        protected float durability_per_hit = .1f;
        protected float stamina = 1.f;  // from 0 to 1, percentage of stamina max
        protected float stamina_usage = 0.005f;  //todo change this after testing
        protected float stamina_regen = .002f;

        protected List<Long> leg_times = new ArrayList<>();  // times for every previous leg
        protected long start_time = -1;
        protected long end_time = -1;  // ms since epoch when starting and finishing current leg
        protected long frames_raced = 0;  // number of frames taken to do current leg
        protected long time_to_add = 0;  // ms to add to the end time for this leg. Accumulated by crossing the lines

        protected int frames_to_animate = 0;
        protected int current_animation_frame = 0;
        protected int frames_elapsed = 0;

        public boolean has_finished_leg = false;
        public boolean has_started_leg = false;


        SerializableGameObject(float x, float y, float width, float height, float rotation, ObjectType type, float speed, Boolean is_shown) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.rotation = rotation;
            this.type = type;
            this.speed = speed;
            this.is_shown = is_shown;

        }

    }

    
    List<SerializableGameObject> gameObjects;
    int playerBoatIndex = 0;
    public int legNumber;
    public boolean lastRun;
    public boolean isFinished;
    public long totalFrames;

    public GameState (List<Boat> allBoats, PlayerBoat playerBoat, List<CollisionObject> obstacles, int legNumber, boolean lastRun, boolean isFinsihed, long totalFrames) {
        
        this.legNumber = legNumber;
        this.lastRun = lastRun;
        this.isFinished = isFinsihed;
        this.totalFrames = totalFrames;
        this.gameObjects = new ArrayList<SerializableGameObject>();

        for (Boat boat : allBoats) {
            if (boat instanceof PlayerBoat) {
                this.playerBoatIndex = allBoats.indexOf(boat);
                System.out.println(String.format("Player boat at %f %f", boat.getSprite().getX(), boat.getSprite().getY()));
            }
            float x = boat.getSprite().getX();
            float y = boat.getSprite().getY();
            float width = boat.getSprite().getWidth();
            float height = boat.getSprite().getHeight();
            float rotation = boat.getSprite().getRotation();
            ObjectType type = ObjectType.BOAT;
            float speed = boat.speed;
            Boolean is_shown = boat.isShown() || true;
            
            SerializableGameObject obj = new SerializableGameObject(x, y, width, height, rotation, type, speed, is_shown);

            // Set boat specific stats
            obj.name = boat.getName();
            obj.durability = boat.durability;
            obj.durability_per_hit = boat.durability_per_hit;
            obj.stamina = boat.stamina;
            obj.stamina_usage = boat.stamina_usage;
            obj.stamina_usage = boat.stamina_regen;

            obj.leg_times.addAll(boat.getLegTimes());
            obj.start_time = boat.getStartTime(false);
            obj.end_time = boat.getEndTime(false);
            obj.frames_raced = boat.getFramesRaced();
            obj.time_to_add = boat.getTimeToAdd();
            
            obj.frames_to_animate = boat.frames_to_animate;
            obj.current_animation_frame = boat.current_animation_frame;
            obj.frames_elapsed = boat.frames_elapsed;

            obj.has_finished_leg = boat.has_finished_leg;
            obj.has_started_leg = boat.has_started_leg;


            this.gameObjects.add(obj);

            
        }

        for (CollisionObject obstacle : obstacles){

            ObjectType type = null;

            if (obstacle instanceof ObstacleBranch) {
                type = ObjectType.BRANCH;
            } else if (obstacle instanceof ObstacleFloatingBranch) {
                type = ObjectType.FLOATING_BRANCH;
            } else if (obstacle instanceof ObstacleDuck) {
                type = ObjectType.DUCK;
            } else if (obstacle instanceof ObstacleLaneWall) {
                type = ObjectType.LANE_WALL;
            }


            if (type == null) {
                continue; // We skip this object because we don't know what it is
            } else {
                // We know it's a type of obstacle

                Obstacle obstacle_cast = (Obstacle) obstacle; 
                
                float x = obstacle_cast.getSprite().getX();
                float y = obstacle_cast.getSprite().getY();
                float width = obstacle_cast.getSprite().getWidth();
                float height = obstacle_cast.getSprite().getHeight();
                float rotation = obstacle_cast.getSprite().getRotation();
                float speed = obstacle_cast.speed;
                Boolean is_shown = obstacle.isShown();

                SerializableGameObject obj = new SerializableGameObject(x, y, width, height, rotation, type, speed, is_shown);
                
                gameObjects.add(obj);
            }
                
        }

    }


    public int getPlayerIndex() {
        return playerBoatIndex;
    }

    public List<Boat> getBoatList() {
        
        List<Boat> output = new ArrayList<Boat>();
        int i = 0;
        for (SerializableGameObject obj : gameObjects) {

            if (obj.type.equals(ObjectType.BOAT)) {
                
                if (i == playerBoatIndex) { // Create a PlayerBoat object instead
                    PlayerBoat player = new PlayerBoat(obj.x, obj.y);
                    player.speed = obj.speed;
                    player.is_shown = obj.is_shown;
                    player.getSprite().setRotation(obj.rotation);

                    player.name = obj.name;
                    player.durability = obj.durability;
                    player.durability_per_hit = obj.durability_per_hit;
                    player.stamina = obj.stamina;
                    player.stamina_usage = obj.stamina_usage;
                    player.stamina_usage = obj.stamina_regen;

                    player.leg_times = obj.leg_times;
                    player.start_time = obj.start_time;
                    player.end_time = obj.end_time;
                    player.frames_raced = obj.frames_raced;
                    player.time_to_add = obj.time_to_add;
                    
                    player.frames_to_animate = obj.frames_to_animate;
                    player.current_animation_frame = obj.current_animation_frame;
                    player.frames_elapsed = obj.frames_elapsed;

                    player.has_finished_leg = obj.has_finished_leg;
                    player.has_started_leg = obj.has_started_leg;

                    output.add(player);

                } else {
                    // Create an AIBoat
                    AIBoat ai = new AIBoat(obj.x, obj.y);
                    ai.speed = obj.speed;
                    ai.is_shown = obj.is_shown;
                    ai.getSprite().setRotation(obj.rotation);

                    ai.name = obj.name;
                    ai.durability = obj.durability;
                    ai.durability_per_hit = obj.durability_per_hit;
                    ai.stamina = obj.stamina;
                    ai.stamina_usage = obj.stamina_usage;
                    ai.stamina_usage = obj.stamina_regen;

                    ai.leg_times = obj.leg_times;
                    ai.start_time = obj.start_time;
                    ai.end_time = obj.end_time;
                    ai.frames_raced = obj.frames_raced;
                    ai.time_to_add = obj.time_to_add;
                    
                    ai.frames_to_animate = obj.frames_to_animate;
                    ai.current_animation_frame = obj.current_animation_frame;
                    ai.frames_elapsed = obj.frames_elapsed;

                    ai.has_finished_leg = obj.has_finished_leg;
                    ai.has_started_leg = obj.has_started_leg;

                    output.add(ai);

                }
            }
            i++;

        }


        return output;


    }

    public List<CollisionObject> getCollisionObjects () {

        List<CollisionObject> output = new ArrayList<CollisionObject>();


        for (SerializableGameObject obj : gameObjects) {
            switch (obj.type) {
                case DUCK:
                    ObstacleDuck duck = new ObstacleDuck(obj.x, obj.y);
                    duck.speed = obj.speed;
                    duck.is_shown = obj.is_shown;
                    duck.getSprite().setRotation(obj.rotation);

                    output.add(duck);
                    break;
            
                case BRANCH:
                    ObstacleBranch branch = new ObstacleBranch(obj.x, obj.y);
                    branch.speed = obj.speed;
                    branch.is_shown = obj.is_shown;
                    branch.getSprite().setRotation(obj.rotation);

                    output.add(branch);
                    break;

                case FLOATING_BRANCH:
                    ObstacleFloatingBranch floating_branch = new ObstacleFloatingBranch(obj.x, obj.y);
                    floating_branch.speed = obj.speed;
                    floating_branch.is_shown = obj.is_shown;
                    floating_branch.getSprite().setRotation(obj.rotation);

                    output.add(floating_branch);
                    break;

                case LANE_WALL:
                    ObstacleLaneWall lane_wall = new ObstacleLaneWall(obj.x, obj.y, "lane_buoy.png");
                    lane_wall.is_shown = obj.is_shown;

                    output.add(lane_wall);
                    break;
                default:
                    break;
            }
        }


        return output;

    }


}

