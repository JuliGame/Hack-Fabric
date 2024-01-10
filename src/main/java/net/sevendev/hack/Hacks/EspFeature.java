package net.sevendev.hack.Hacks;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.sevendev.hack.utils.RenderUtils;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class EspFeature {
    private static String blockType;

    // Register the ESP feature
    public static void register(String block) {
        blockType = block;

        // Register client render event handler
        ClientTickEvents.END_CLIENT_TICK.register(EspFeature::onClientTick);
    }

    public static List<BlockPos> toEspBlocks = new ArrayList<>();
    private static void onClientTick(MinecraftClient client) {
        if (client.world != null && client.player != null) {
            BlockPos playerPos = new BlockPos((int) client.player.getX(), (int) client.player.getY(), (int) client.player.getZ());

            int range = 10;
            for (int x = -range; x < range; x++) {
                for (int y = -range; y < range; y++) {
                    for (int z = -range; z < range; z++) {
                        // Get the block at the current position
                        BlockPos blockPos = playerPos.add(x, y, z);
                        BlockState blockState = client.world.getBlockState(blockPos);

                        // Check if it's the special block
                        if (blockState.getBlock().getName().getString().toLowerCase().endsWith(blockType)) {
                            if (!toEspBlocks.contains(blockPos))
                                toEspBlocks.add(blockPos);
                        } else {
                            toEspBlocks.remove(blockPos);
                        }
                    }
                }
            }
        }
    }

    public static void BigUpdate(MinecraftClient client) {
        if (client.world != null && client.player != null) {
            BlockPos playerPos = new BlockPos((int) client.player.getX(), (int) client.player.getY(), (int) client.player.getZ());

            int range = 100;
            for (int x = -range; x < range; x++) {
                for (int y = client.world.getBottomY(); y < client.world.getTopY(); y++) {
                    for (int z = -range; z < range; z++) {
                        // Get the block at the current position
                        BlockPos blockPos = playerPos.add(x, y, z);
                        BlockState blockState = client.world.getBlockState(blockPos);

                        // Check if it's the special block
                        if (blockState.getBlock().getName().getString().toLowerCase().endsWith(blockType)) {
                            if (!toEspBlocks.contains(blockPos))
                                toEspBlocks.add(blockPos);
                        } else {
                            toEspBlocks.remove(blockPos);
                        }
                    }
                }
            }
        }
    }

    public static void render(WorldRenderContext context) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        Camera camera = context.camera();
        Vec3d cameraPos = camera.getPos();

        toEspBlocks.forEach(blockPos -> {
            drawHighlight(buffer, blockPos);
        });

        VertexBuffer vertexBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);

        vertexBuffer.bind();
        vertexBuffer.upload(buffer.end());
        VertexBuffer.unbind();

        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        MatrixStack poseStack = RenderSystem.getModelViewStack();
        poseStack.push();

        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.applyModelViewMatrix();
        RenderSystem.depthFunc(GL11.GL_ALWAYS);

        context.projectionMatrix().lookAt(cameraPos.toVector3f(), cameraPos.toVector3f().add(camera.getHorizontalPlane()), camera.getVerticalPlane());
        vertexBuffer.bind();
        vertexBuffer.draw(poseStack.peek().getPositionMatrix(), new Matrix4f(context.projectionMatrix()), RenderSystem.getShader());
        VertexBuffer.unbind();
        RenderSystem.depthFunc(GL11.GL_LEQUAL);

        poseStack.pop();
        RenderSystem.applyModelViewMatrix();
    }

    public static void drawHighlight(BufferBuilder bufferBuilder, BlockPos blockPos) {
        Box box = new Box(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos.getX() + 1, blockPos.getY() + 1, blockPos.getZ() + 1);
        RenderUtils.drawBox(bufferBuilder, box, 0, 0, 1, 1f);
    }

}
