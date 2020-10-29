package com.teamonehundred.pixelboat;

public class AIBoat extends Boat{
    /* ################################### //
                   ATTRIBUTES
    // ################################### */
    float number_of_rays;
    float ray_angle_range;
    float ray_range;
    float ray_step_size;

    /* ################################### //
              CONSTRUCTORS
    // ################################### */
    AIBoat(int x, int y, int w, int h, String texture_path) { // So this section will just initialise the AI boat, it doesn't need the intialise method of playerboat due to the fact it doesn't have any textures for durability / stamina
        super(x, y, w, h, texture_path);

        initialise();
    }

    AIBoat(int x, int y, int w, int h, String texture_path, String name, float durability_per_hit, float stamina_usage, float stamina_regen) {
        super(x, y, w, h, texture_path, name, durability_per_hit, stamina_usage, stamina_regen); // This should be the init that is used mostly (but the other one is needed incase someone messes up)

        initialise();

    }

    public void initialise(){
        number_of_rays = 10; // how many rays are fired from the boat
        ray_angle_range = 180; // the range of the angles that the boat will fire rays out at
        ray_range = 30; // the range of each ray
        ray_step_size = 1;
    }

    public void updatePosition(){
        /* Fire a number of rays with limited distance out the front of the boat,
         select a ray that isn't obstructed by an object,
         preference the middle (maybe put a preference to side as well)
         if every ray is obstructed either (keep turning [left or right] on the spot until one is, or choose the one that is obstructed furthest away
         I am pretty sure the second option (choose the one that is obstructed furthest away) is better
         */

        //Firing rays

        //select an area of 180 degrees (pi radians)
        boolean cheeky_bit_of_coding = true; // this is a very cheeky way of solving the problem, but has a few benefits
        //TODO: Explain the cheeky_bit_of_coding better

        for (int ray = 0; ray <= number_of_rays; ray++){
            if (cheeky_bit_of_coding){
                ray--;
                float ray_angle = sprite.getRotation() + ((ray_angle_range/(number_of_rays/2)) * ray);
                cheeky_bit_of_coding = false;
            }
            else{
                float ray_angle = sprite.getRotation() - ((ray_angle_range/(number_of_rays/2)) * ray);
                cheeky_bit_of_coding = true;
            }

            float ray_angle = ((ray_angle_range/number_of_rays) * ray) + sprite.getRotation();
            for (float dist = 0; dist <= ray_range; dist+= ray_step_size){
                double tempx = Math.cos(Math.toRadians(ray_angle)) * dist;
                double tempy = Math.sin(Math.toRadians(ray_angle)) * dist;
                //check if there is a collision hull (other than self) at (tempx, tempy)
            }
        }

    }

}
