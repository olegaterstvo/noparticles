package net.cpd.noparticles.event;

import net.cpd.noparticles.NoParticlesClient;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_CHRONOS = "key.category.cpd.noparticles";
    public static final String KEY_TOGGLE_PARTICLES = "key.noparticles.toggle_particles";

    public static KeyBinding toggleParticles;

    public static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(toggleParticles.wasPressed()){
                NoParticlesClient.particlesDisabled = !NoParticlesClient.particlesDisabled;
                assert client.player != null;
                if(NoParticlesClient.particlesDisabled) {
                    client.player.sendMessage(Text.of("Particles is now disabled"));
                } else {
                    client.player.sendMessage(Text.of("Particles is now enabled"));
                }
            }
        });
    }


    public static void register(){
        toggleParticles = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_TOGGLE_PARTICLES,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F9,
                KEY_CATEGORY_CHRONOS
        ));

        registerKeyInputs();
    }

}
