package de.maxhenkel.voicechat.net;

import de.maxhenkel.voicechat.Voicechat;
import de.maxhenkel.voicechat.config.ServerConfig;
import de.maxhenkel.voicechat.plugins.PluginManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public class SecretPacket implements Packet<SecretPacket> {

    public static final ResourceLocation SECRET = new ResourceLocation(Voicechat.MODID, "secret");

    private UUID secret;
    private int serverPort;
    private ServerConfig.Codec codec;
    private int mtuSize;
    private double voiceChatDistance;
    private double voiceChatFadeDistance;
    private double crouchDistanceMultiplier;
    private double whisperDistanceMultiplier;
    private int keepAlive;
    private boolean groupsEnabled;
    private String voiceHost;
    private boolean allowRecording;

    public SecretPacket() {

    }

    public SecretPacket(ServerPlayer player, UUID secret, int port, ServerConfig serverConfig) {
        this.secret = secret;
        this.serverPort = port;
        this.codec = serverConfig.voiceChatCodec.get();
        this.mtuSize = serverConfig.voiceChatMtuSize.get();
        this.voiceChatDistance = serverConfig.voiceChatDistance.get();
        this.voiceChatFadeDistance = serverConfig.voiceChatFadeDistance.get();
        this.crouchDistanceMultiplier = serverConfig.crouchDistanceMultiplier.get();
        this.whisperDistanceMultiplier = serverConfig.whisperDistanceMultiplier.get();
        this.keepAlive = serverConfig.keepAlive.get();
        this.groupsEnabled = serverConfig.groupsEnabled.get();
        this.voiceHost = PluginManager.instance().getVoiceHost(player, serverConfig.voiceHost.get());
        this.allowRecording = serverConfig.allowRecording.get();
    }

    public UUID getSecret() {
        return secret;
    }

    public int getServerPort() {
        return serverPort;
    }

    public ServerConfig.Codec getCodec() {
        return codec;
    }

    public int getMtuSize() {
        return mtuSize;
    }

    public double getVoiceChatDistance() {
        return voiceChatDistance;
    }

    public double getVoiceChatFadeDistance() {
        return voiceChatFadeDistance;
    }

    public double getCrouchDistanceMultiplier() {
        return crouchDistanceMultiplier;
    }

    public double getWhisperDistanceMultiplier() {
        return whisperDistanceMultiplier;
    }

    public int getKeepAlive() {
        return keepAlive;
    }

    public boolean groupsEnabled() {
        return groupsEnabled;
    }

    public String getVoiceHost() {
        return voiceHost;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return SECRET;
    }

    @Override
    public int getID() {
        return 4;
    }

    public boolean allowRecording() {
        return allowRecording;
    }

    @Override
    public SecretPacket fromBytes(FriendlyByteBuf buf) {
        secret = buf.readUUID();
        serverPort = buf.readInt();
        codec = ServerConfig.Codec.values()[buf.readByte()];
        mtuSize = buf.readInt();
        voiceChatDistance = buf.readDouble();
        voiceChatFadeDistance = buf.readDouble();
        crouchDistanceMultiplier = buf.readDouble();
        whisperDistanceMultiplier = buf.readDouble();
        keepAlive = buf.readInt();
        groupsEnabled = buf.readBoolean();
        voiceHost = buf.readUtf(32767);
        allowRecording = buf.readBoolean();
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(secret);
        buf.writeInt(serverPort);
        buf.writeByte(codec.ordinal());
        buf.writeInt(mtuSize);
        buf.writeDouble(voiceChatDistance);
        buf.writeDouble(voiceChatFadeDistance);
        buf.writeDouble(crouchDistanceMultiplier);
        buf.writeDouble(whisperDistanceMultiplier);
        buf.writeInt(keepAlive);
        buf.writeBoolean(groupsEnabled);
        buf.writeUtf(voiceHost);
        buf.writeBoolean(allowRecording);
    }

}
