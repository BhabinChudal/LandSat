package com.wecodeforfood.core;

import com.wecodeforfood.util.Trackable;
import com.wecodeforfood.util.Vector;


/**
 * The {@code Landsat} class represents the LANDSAT satellite.
 *
 * <p> The class grants the user access to get time until the satellite
 * reaches a specified target location, to display the data of a specified
 * location, and send notifications to the user indicating when the LANDSAT
 * satellite will reach the location
 * </p>
 *
 * This class implements the {@link Trackable} interface to enable tracking
 * of the satellite's current position and other related functionalities.
 *
 * @author WeCodeForFood
 * @since   1.0
 *
 * @see Trackable
 */
public class Landsat implements Trackable {

    // Instance variables
    private SatelliteLocation currentPosition;
    private int cloudCoverage;

    // CONSTRUCTOR
    /**
     * Default constructor for the {@code Landsat} class.
     * Initializes the Landsat satellite and begins tracking its position.
     */
    public Landsat(int cloudCoverage) {
        track(); //Sets the current position when object is created
        this.cloudCoverage = cloudCoverage;
    }

    /**
     * This method calculates the time until the satellite reaches the
     * target location.
     * It will factor in the satellite's speed, its current trajectory, and the
     * distance it needs to travel to reach the target location.
     *
     * @param targetLocation The target location the satellite is approaching.
     * @return The estimated time in hours until the satellite reaches the
     * target location.
     *
     * @author Mac
     */
    public double getTimeUntilTarget(TargetLocation targetLocation){
        track();

        // Calculate the vector from the current position to the target
        double deltaX = targetLocation.getLatitude() - currentPosition.getLatitude();
        double deltaY = targetLocation.getLongitude() - currentPosition.getLongitude();

        // Create vectors for velocity and direction to the target
        Vector satelliteVelocity = currentPosition.getVelocity();
        Vector toTarget = new Vector(deltaX, deltaY);

        // Calculate magnitudes
        double magnitudeVelocity = satelliteVelocity.getMagnitude();
        double magnitudeTarget = toTarget.getMagnitude();

        // Calculate the dot product between the velocity and the target
        // direction
        double dot_product = satelliteVelocity.dotProduct(toTarget);

        // Calculate the angle between the satellite's direction and the target
        double angle =
                Math.acos(dot_product/(magnitudeTarget * magnitudeVelocity));
        double angleInDegrees = Math.toDegrees(angle);


        // Calculate Haversine distance
        double haversineDistance = targetLocation.getDistanceTo(currentPosition);

        // Check if the satellite is moving towards the target within a
        // threshold, and if the haversineDistance is less than 20, it would
        // imply the satellite is moving towards the point, and the point is
        // not on the other side of the earth.
        if (angleInDegrees < 3.0 && haversineDistance < 20){
            Vector normalVector = new Vector(0, 0, 1);

            // Calculate the angle between the satellite's velocity vector and the normal vector
            double normalDotProduct = satelliteVelocity.dotProduct(normalVector);
            double normalAngle = Math.acos(normalDotProduct / (magnitudeVelocity * normalVector.getMagnitude()));
            double normalAngleInDegrees = Math.toDegrees(normalAngle);

            // Check if the satellite is directly above the target
            if (normalAngleInDegrees < 1.0) {
                double distance = targetLocation.getDistanceTo(currentPosition);
                return distance / magnitudeVelocity;
            }
        }
        return 16*24;
    }

    /**
     * Displays a 3x3 grid of the data recorded by the Landsat satellite at
     * its current position keeping in mind the cloudCoverage
     */
    public void displayData(int cloudCoverage){
        // Using the resources from the SPACE APPS website, try and figure out
        // how you can get the 3x3 grid of data if you enter the latitude and
        // the longitude.
        // The way you want the data to show is up to you. However,
        // consider using javaFX to display the image you fetch from the
        // internet and put in on a screen using javaFX
        // HINT: You do not need to know the target location. You can use the
        // current position of the satellite as this method will only be
        // called if getDistance() method returns a small distance between
        // the two. Make sure to also use the cloud coverage while displaying
        // the data

        //Feel free to change the arguments in case you cannot do it without
        // using the target location lat and long.
        return;
    }

    /**
     * This method sends a notification to the user indicating when the
     * LANDSAT is near the {@code TargetLocation}
     */
    public void sendNotification(){
        // Using twilio API, make an account and just send a simple
        // notification stating that the LANDSAT will be hovering over the
        // specified target location soon
        return;
    }

    /**
     * Sets the cloud coverage percentage for the current observation.
     *
     * @param cloudCoverage The cloud coverage percentage (0-100).
     */
    public void setCloudCoverage(int cloudCoverage) {
        this.cloudCoverage = cloudCoverage;
    }

    /**
     * Tracks the current position of the Landsat satellite and updates it.
     * This method should include logic to obtain the satellite's current
     * coordinates and update the {@code currentPosition} variable.
     */
    @Override
    public void track() {
        // Using the resources from the SPACE APPS website, try and figure out
        // how you can get the Lat Long for the satellite. In case you can
        // not do so, try using the speed at which it orbits the earth, and
        // an initial fixed starting position.
        // Using speed and the starting location, you can calculate the
        // current position of the satellite using the formula
        // speed = distance/time. you know the speed, and the distance.
        // Note that the value of distance will not be the "initial position"
        // It will be the distance it travelled since the initial location
        // In case you cannot figure out the current position  using speed,
        // using maths can help you figure out when the satellite will
        // reach the target location. But, how will we use that information?
        // Suppose you know that the satellite will be at coords x,y after 20
        // hours 30 mins. How will I use it to set the current position and
        // calculate distance. All up to you guys to decide
    }


    // GETTERS
    public SatelliteLocation getCurrentPosition() {
        return currentPosition;
    }

    public int getCloudCoverage() {
        return cloudCoverage;
    }
}
