package net.sevendev.hack.utils;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.math.Box;

public class RenderUtils {

    public static void drawBox(VertexConsumer vertexConsumer, Box box, float red, float green, float blue, float alpha) {
        drawBox(vertexConsumer, box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, red, green, blue, alpha, red, green, blue);
    }

    public static void drawBox(VertexConsumer vertexConsumer, double x1, double y1, double z1, double x2, double y2, double z2, float red, float green, float blue, float alpha) {
        drawBox(vertexConsumer, x1, y1, z1, x2, y2, z2, red, green, blue, alpha, red, green, blue);
    }

    public static void drawBox(VertexConsumer vertexConsumer, double x1, double y1, double z1, double x2, double y2, double z2, float red, float green, float blue, float alpha, float xAxisRed, float yAxisGreen, float zAxisBlue) {
        vertexConsumer.vertex(x1, y1, z1).color(red, yAxisGreen, zAxisBlue, alpha).normal(1.0f, 0.0f, 0.0f).next();
        vertexConsumer.vertex(x2, y1, z1).color(red, yAxisGreen, zAxisBlue, alpha).normal(1.0f, 0.0f, 0.0f).next();
        vertexConsumer.vertex(x1, y1, z1).color(xAxisRed, green, zAxisBlue, alpha).normal(0.0f, 1.0f, 0.0f).next();
        vertexConsumer.vertex(x1, y2, z1).color(xAxisRed, green, zAxisBlue, alpha).normal(0.0f, 1.0f, 0.0f).next();
        vertexConsumer.vertex(x1, y1, z1).color(xAxisRed, yAxisGreen, blue, alpha).normal(0.0f, 0.0f, 1.0f).next();
        vertexConsumer.vertex(x1, y1, z2).color(xAxisRed, yAxisGreen, blue, alpha).normal(0.0f, 0.0f, 1.0f).next();
        vertexConsumer.vertex(x2, y1, z1).color(red, green, blue, alpha).normal(0.0f, 1.0f, 0.0f).next();
        vertexConsumer.vertex(x2, y2, z1).color(red, green, blue, alpha).normal(0.0f, 1.0f, 0.0f).next();
        vertexConsumer.vertex(x2, y2, z1).color(red, green, blue, alpha).normal(-1.0f, 0.0f, 0.0f).next();
        vertexConsumer.vertex(x1, y2, z1).color(red, green, blue, alpha).normal(-1.0f, 0.0f, 0.0f).next();
        vertexConsumer.vertex(x1, y2, z1).color(red, green, blue, alpha).normal(0.0f, 0.0f, 1.0f).next();
        vertexConsumer.vertex(x1, y2, z2).color(red, green, blue, alpha).normal(0.0f, 0.0f, 1.0f).next();
        vertexConsumer.vertex(x1, y2, z2).color(red, green, blue, alpha).normal(0.0f, -1.0f, 0.0f).next();
        vertexConsumer.vertex(x1, y1, z2).color(red, green, blue, alpha).normal(0.0f, -1.0f, 0.0f).next();
        vertexConsumer.vertex(x1, y1, z2).color(red, green, blue, alpha).normal(1.0f, 0.0f, 0.0f).next();
        vertexConsumer.vertex(x2, y1, z2).color(red, green, blue, alpha).normal(1.0f, 0.0f, 0.0f).next();
        vertexConsumer.vertex(x2, y1, z2).color(red, green, blue, alpha).normal(0.0f, 0.0f, -1.0f).next();
        vertexConsumer.vertex(x2, y1, z1).color(red, green, blue, alpha).normal(0.0f, 0.0f, -1.0f).next();
        vertexConsumer.vertex(x1, y2, z2).color(red, green, blue, alpha).normal(1.0f, 0.0f, 0.0f).next();
        vertexConsumer.vertex(x2, y2, z2).color(red, green, blue, alpha).normal(1.0f, 0.0f, 0.0f).next();
        vertexConsumer.vertex(x2, y1, z2).color(red, green, blue, alpha).normal(0.0f, 1.0f, 0.0f).next();
        vertexConsumer.vertex(x2, y2, z2).color(red, green, blue, alpha).normal(0.0f, 1.0f, 0.0f).next();
        vertexConsumer.vertex(x2, y2, z1).color(red, green, blue, alpha).normal(0.0f, 0.0f, 1.0f).next();
        vertexConsumer.vertex(x2, y2, z2).color(red, green, blue, alpha).normal(0.0f, 0.0f, 1.0f).next();
    }

}
