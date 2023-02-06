package me.nazarxexe.survival.core.network;

import cn.nukkit.Player;
import cn.nukkit.math.Vector3f;
import cn.nukkit.network.protocol.DataPacket;
import cn.nukkit.network.protocol.LevelEventPacket;
import cn.nukkit.plugin.Plugin;
import lombok.Getter;

import java.util.List;

public class Sound {

    private Plugin plugin;
    private String sound;

    private @Getter float vol;

    public Sound setLocation(Vector3f location) {
        this.location = location;
        return this;
    }

    private @Getter Vector3f location;

    public Sound setVol(float vol) {
        this.vol = vol;
        return this;
    }

    public Sound setPitch(float pitch) {
        this.pitch = pitch;
        return this;
    }

    private @Getter float pitch;


    public Sound (Plugin plugin, String sound) {
        this.plugin = plugin;
        this.sound = sound;
    }
    public Sound (Plugin plugin, String sound, float tone) {
        this.plugin = plugin;
        this.sound = sound;
        this.pitch = tone;
    }

    public Sound (Plugin plugin, String sound, float vol, float tone) {
        this.plugin = plugin;
        this.sound = sound;
        this.vol = vol;
        this.pitch = tone;
    }

    public void play(Player player){
        if (this.location != null){
            play(player, this.location);
        }else {
            play(player, player.getLocation().asVector3f());
        }
    }

    public void play(List<Player> players){
        if (players.isEmpty()) return;

        for (Player player : players) play(player);
    }

    public void play(Player player, Vector3f location){

        plugin.getServer().batchPackets(new Player[]{player}, new DataPacket[]{
                new SoundPacket(sound, location)
                        .setPitch(pitch)
                        .setVol(vol)
                }
                , false);
    }


}
