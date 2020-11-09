package com.teamonehundred.pixelboat;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

class BoatRace {
    protected List<Boat> boats;

    protected BitmapFont font; //TimingTest

    protected List<CollisionObject> obstacles;

    protected int start_y = 200;
    protected int end_y = 10000;

    protected int lane_width = 400;
    protected int penalty_per_frame = 1; // ms to add per frame when over the lane

    protected boolean is_finished = false;
    protected long frames_elapsed = 0;  // used when working out how long a boat ran in the background would have taken

    BoatRace(List<Boat> race_boats) {
        boats = new ArrayList<>();
        boats.addAll(race_boats);

        int race_width = boats.size() * lane_width;

        for (int i = 0; i < boats.size(); i++) {
            boats.get(i).has_started_leg = false;
            boats.get(i).has_finished_leg = false;

            boats.get(i).reset_motion();
            boats.get(i).sprite.setPosition((-race_width/2)+(lane_width*i)-(lane_width/2), 40);  // reset boats y and place in lane

            if(boats.get(i) instanceof PlayerBoat)
                ((PlayerBoat)boats.get(i)).resetCameraPos();
        }

        obstacles = new ArrayList<>();

        // add some random obstacles
        for (int i = 0; i < 100; i++)
            obstacles.add(new ObstacleBranch(
                    (int) (-(lane_width*boats.size()/2) + Math.random() * (lane_width*boats.size())),
                    (int) (start_y + 50 + Math.random() * (end_y-start_y-50))));

        for (int i = 0; i < 100; i++)
            obstacles.add(new ObstacleFloatingBranch((int) (-(lane_width*boats.size()/2) + Math.random() * (lane_width*boats.size())),
                    (int) (start_y + 50 + Math.random() * (end_y-start_y-50))));

        for (int i = 0; i < 100; i++)
            obstacles.add(new ObstacleDuck((int) (-(lane_width*boats.size()/2) + Math.random() * (lane_width*boats.size())),
                    (int) (start_y + 50 + Math.random() * (end_y-start_y-50))));

        //Timing Test
//        for (Boat b : boats) {
//            if (b instanceof PlayerBoat) {
//                ((PlayerBoat) b).setStartTime(System.currentTimeMillis());
//            }
//        }

        font = new BitmapFont();
        font.setColor(Color.RED);
    }

    protected void runSimulation() {
        boolean not_finished = false;
        for (Boat b : boats) {
            if (!b.hasFinishedLeg()) not_finished = true;
        }
        is_finished = !not_finished;

        for (Boat b : boats) {
            // update boat (handles inputs if player, etc)
            if (b instanceof AIBoat) {
                //((AIBoat) b).updatePosition(obstacles);
            } else if (b instanceof PlayerBoat) {
                b.updatePosition();
            }
            // check for collisions
            for (CollisionObject obstacle : obstacles) {
                //long st = System.nanoTime();
                b.checkCollisions(obstacle);
                //long et = System.nanoTime();
                //System.out.print("took ");
                //System.out.print((et-st)/1000.0);
                //System.out.println(" ms to run AIBoat.checkCollisions");
            }
        }

        for (CollisionObject c : obstacles) {
            if (c instanceof Obstacle)
                ((Obstacle) c).updatePosition();
        }
    }

    public void runBackgroundStep() {
        for (Boat b : boats) {
            // check if any boats have finished
            if (!b.hasFinishedLeg() && b.getSprite().getY() > end_y) {
                // store the leg time in the object calculated based on a 60fps game
                b.setEndTime((long)(b.getStartTime(false) + ((1000.0/60.0)*frames_elapsed)));
                b.setLegTime();

                b.setHasFinishedLeg(true);

                System.out.print("a boat ended race with time (ms) ");
                System.out.println(b.getCalcTime());
            }
            // check if any boats have started
            else if (!b.hasStartedLeg() && b.getSprite().getY() > start_y) {
                b.setStartTime(0);
                b.setHasStartedLeg(true);
            }
        }

        runSimulation();
        frames_elapsed++;
    }

    public void runStep() {
        for (Boat b : boats) {
            // check if any boats have finished
            if (!b.hasFinishedLeg() && b.getSprite().getY() > end_y) {
                // store the leg time in the object
                b.setEndTime(System.currentTimeMillis());
                b.setLegTime();

                b.setHasFinishedLeg(true);

                System.out.print("a boat ended race with time (ms) ");
                System.out.println(b.getCalcTime());
            }
            // check if any boats have started
            else if (!b.hasStartedLeg() && b.getSprite().getY() > start_y) {
                b.setStartTime(System.currentTimeMillis());
                b.setHasStartedLeg(true);
            }
        }

        runSimulation();
    }

    public boolean isFinished() {
        return is_finished;
    }

    public List<Sprite> getSprites() {
        List<Sprite> all_sprites = new ArrayList<>();

        for (Boat b : boats) {
            all_sprites.add(b.getSprite());
            if (b instanceof PlayerBoat)
                all_sprites.addAll(((PlayerBoat) b).getUISprites());
        }

        for (CollisionObject obs : obstacles) {
            // check if can be cast back up
            if (obs instanceof Obstacle && obs.isShown())
                all_sprites.add(((Obstacle) obs).getSprite());
        }

        return all_sprites;
    }

    public void draw(SpriteBatch batch) {
        // Timing Testing

        for (Sprite sp : getSprites())
            sp.draw(batch);

        for (Boat b : boats) {
            if (b instanceof PlayerBoat) {
                long i = (System.currentTimeMillis() - ((PlayerBoat) b).getStartTime(false));
                font.draw(batch, String.format("Time (min:sec) = %02d:%02d", i / 60000, i / 1000 % 60), -((PlayerBoat) b).ui_bar_width / 2, -50 + ((PlayerBoat) b).getSprite().getY());//TimingTest
            }
        }

        Texture temp = new Texture("object_placeholder.png");

        batch.draw(temp, -400, start_y, 800, 5);
        batch.draw(temp, -400, end_y, 800, 5);

        temp.dispose();
    }


}
