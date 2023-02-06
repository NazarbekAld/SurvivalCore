package me.nazarxexe.survival.core.network;

import cn.nukkit.math.Vector3f;
import cn.nukkit.network.protocol.DataPacket;
import lombok.Getter;

public class SoundPacket extends DataPacket {

    public byte pid = 0x56;

    public SoundPacket setSound(String sound) {
        this.sound = sound;
        return this;
    }

    public SoundPacket setPos(Vector3f pos) {
        this.pos = pos;
        return this;
    }

    public SoundPacket setVol(float vol) {
        this.vol = vol;
        return this;
    }

    public SoundPacket setPitch(float pitch) {
        this.pitch = pitch;
        return this;
    }

    public SoundPacket(String soundID, Vector3f pos){
        this.sound = soundID;
        this.pos = pos;
    }

    private @Getter String sound;
    private @Getter Vector3f pos;

    private @Getter float vol;
    private @Getter float pitch;


    @Override
    public byte pid() {
        return pid;
    }


    /**
     * <b>Lmao, this is only client side.</b>
     *
     *
     */
    @Deprecated
    @Override
    public void decode() {
    }

    @Override
    public void encode() {
        this.reset();
        this.putString(sound);
        this.putBlockVector3((int) (pos.x *8), (int) (pos.y * 8), (int) (pos.z * 8));
        this.putLFloat(vol);
        this.putLFloat(pitch);
    }
}
