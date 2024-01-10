package net.sevendev.hack.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

public class RotationUtil {


    // Easy converted a 1.8 method to a 1.19
    public static float[] getRotations(Entity target) {
        double playerX = MinecraftClient.getInstance().player.getX();
        double playerZ = MinecraftClient.getInstance().player.getZ();
        double playerY = MinecraftClient.getInstance().player.getY();
        double deltaX = target.getX() - playerX;
        double deltaY = target.getY() - playerY;
        double deltaZ = target.getZ() - playerZ;
        double distance = Math.sqrt(deltaX*deltaX + deltaZ*deltaZ);
        double yaw = Math.atan2(deltaZ, deltaX);
        double pitch = deltaY/distance;
        return new float[]{(float) Math.toDegrees(yaw) - 90, (float) -Math.toDegrees(pitch)};
    }

    private static double calculateAngle(double x, double y) {
        double distanceSquared = x * x + y * y;
        if (Double.isNaN(distanceSquared)) {
            return Double.NaN;
        }
        double absX = Math.abs(x);
        double absY = Math.abs(y);
        boolean swap = absX > absY;
        if (swap) {
            double temp = absX;
            absX = absY;
            absY = temp;
        }
        double angle = 0;
        if (absY > 0) {
            double t = absX / absY;
            double u = 0.125D + t * (0.5D + t * (0.125D + t * 0.0625D));
            angle = u * absY;
        }
        if (swap) {
            angle = Math.PI / 2 - angle;
        }
        if (y < 0) {
            angle = Math.PI - angle;
        }
        if (x < 0) {
            angle = -angle;
        }
        return angle;
    }
}
