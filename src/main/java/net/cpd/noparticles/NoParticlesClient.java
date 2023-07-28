package net.cpd.noparticles;

import net.cpd.noparticles.event.KeyInputHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.PotionUtil;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Collection;

public class NoParticlesClient implements ClientModInitializer {
    private WeakReference<Entity> lastCam;
    private TrackedData<Integer> colors;
    public static Boolean particlesDisabled = Boolean.TRUE;

    private void onClientTick(MinecraftClient client) {
        if(particlesDisabled) {
            Entity entity = client.getCameraEntity();
            Entity lastEntity = lastCam.get();


            if (lastEntity != entity) {
                if (lastEntity instanceof LivingEntity) {
                    Collection<StatusEffectInstance> effects = ((LivingEntity) lastEntity).getStatusEffects();
//                if (!effects.isEmpty())
                    lastEntity.getDataTracker().set(colors, PotionUtil.getColor(effects));
                }
                lastCam = new WeakReference<Entity>(entity);
            }

            if (entity instanceof LivingEntity)
                entity.getDataTracker().set(colors, 0);
        }
    }


    private void setupEvents() {
        try {
            Field f = LivingEntity.class.getDeclaredField(FabricLoader.getInstance().getMappingResolver().mapFieldName("intermediary", "net.minecraft.class_1309", "field_6240", "Lnet/minecraft/class_2940;"));
            f.setAccessible(true);
            colors = (TrackedData<Integer>) f.get(null);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }

        lastCam = new WeakReference<Entity>(null);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            onClientTick(client);
        });
    }

    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();
        setupEvents();

    }
}
