package com.rupakpaul.hashtwit.backend.containers;

import com.hedera.hashgraph.sdk.FileId;
import com.hedera.hashgraph.sdk.TopicId;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class AccountMetadata {
    private String fileIdOfProfile;
    private String topicIdOfFollowing;
    private String topicIdOfFollowers;
    private String topicIdOfPosts;
    private String topicIdOfInbox;

    public AccountMetadata(String fileIdOfProfile, String topicIdOfFollowing, String topicIdOfFollowers, String topicIdOfPosts, String topicIdOfInbox) {
        if(fileIdOfProfile == null) this.fileIdOfProfile = "";
        else this.fileIdOfProfile = fileIdOfProfile;

        if(topicIdOfFollowing == null) this.topicIdOfFollowing = "";
        else this.topicIdOfFollowing = topicIdOfFollowing;

        if(topicIdOfFollowers == null) this.topicIdOfFollowers = "";
        else this.topicIdOfFollowers = topicIdOfFollowers;

        if(topicIdOfPosts == null) this.topicIdOfPosts = "";
        else this.topicIdOfPosts = topicIdOfPosts;

        if(topicIdOfInbox == null) this.topicIdOfInbox = "";
        else this.topicIdOfInbox = topicIdOfInbox;
    }

    public AccountMetadata(byte[] dataStream) throws Exception {
        ByteBuffer buffer = ByteBuffer.wrap(dataStream);

        int fileIdOfProfileLength = buffer.getInt();
        byte[] fileIdOfProfileBytes = new byte[fileIdOfProfileLength];
        buffer.get(fileIdOfProfileBytes);
        this.fileIdOfProfile = new String(fileIdOfProfileBytes, StandardCharsets.UTF_8);

        int topicIdOfFollowingLength = buffer.getInt();
        byte[] topicIdOfFollowingBytes = new byte[topicIdOfFollowingLength];
        buffer.get(topicIdOfFollowingBytes);
        this.topicIdOfFollowing = new String(topicIdOfFollowingBytes, StandardCharsets.UTF_8);

        int topicIdOfFollowersLength = buffer.getInt();
        byte[] topicIdOfFollowersBytes = new byte[topicIdOfFollowersLength];
        buffer.get(topicIdOfFollowersBytes);
        this.topicIdOfFollowers = new String(topicIdOfFollowersBytes, StandardCharsets.UTF_8);

        int topicIdOfPostsLength = buffer.getInt();
        byte[] topicIdOfPostsBytes = new byte[topicIdOfPostsLength];
        buffer.get(topicIdOfPostsBytes);
        this.topicIdOfPosts = new String(topicIdOfPostsBytes, StandardCharsets.UTF_8);

        int topicIdOfInboxLength = buffer.getInt();
        byte[] topicIdOfInboxBytes = new byte[topicIdOfInboxLength];
        buffer.get(topicIdOfInboxBytes);
        this.topicIdOfInbox = new String(topicIdOfInboxBytes, StandardCharsets.UTF_8);
    }

    public AccountMetadata(String dataStream) throws Exception {
        String[] id = dataStream.split(",", -1);
        this.fileIdOfProfile = id[0];
        this.topicIdOfFollowing = id[1];
        this.topicIdOfFollowers = id[2];
        this.topicIdOfPosts = id[3];
        this.topicIdOfInbox = id[4];
    }

    public byte[] serialize() {
        int size = Integer.BYTES + fileIdOfProfile.getBytes(StandardCharsets.UTF_8).length +
                Integer.BYTES + topicIdOfFollowing.getBytes(StandardCharsets.UTF_8).length +
                Integer.BYTES + topicIdOfFollowers.getBytes(StandardCharsets.UTF_8).length +
                Integer.BYTES + topicIdOfPosts.getBytes(StandardCharsets.UTF_8).length +
                Integer.BYTES + topicIdOfInbox.getBytes(StandardCharsets.UTF_8).length;

        ByteBuffer buffer = ByteBuffer.allocate(size);

        byte[] fileIdOfProfileBytes = fileIdOfProfile.getBytes(StandardCharsets.UTF_8);
        buffer.putInt(fileIdOfProfileBytes.length);
        buffer.put(fileIdOfProfileBytes);

        byte[] topicIdOfFollowingBytes = topicIdOfFollowing.getBytes(StandardCharsets.UTF_8);
        buffer.putInt(topicIdOfFollowingBytes.length);
        buffer.put(topicIdOfFollowingBytes);

        byte[] topicIdOfFollowersBytes = topicIdOfFollowers.getBytes(StandardCharsets.UTF_8);
        buffer.putInt(topicIdOfFollowersBytes.length);
        buffer.put(topicIdOfFollowersBytes);

        byte[] topicIdOfPostsBytes = topicIdOfPosts.getBytes(StandardCharsets.UTF_8);
        buffer.putInt(topicIdOfPostsBytes.length);
        buffer.put(topicIdOfPostsBytes);

        byte[] topicIdOfInboxBytes = topicIdOfInbox.getBytes(StandardCharsets.UTF_8);
        buffer.putInt(topicIdOfInboxBytes.length);
        buffer.put(topicIdOfInboxBytes);

        return buffer.array();
    }

    public String serializeToString() {
        return fileIdOfProfile + "," +
                topicIdOfFollowing + "," +
                topicIdOfFollowers + "," +
                topicIdOfPosts + "," +
                topicIdOfInbox;
    }

    public FileId getFileIdOfProfile() throws Exception {
        return FileId.fromString(fileIdOfProfile);
    }

    public TopicId getTopicIdOfFollowing() throws Exception {
        return TopicId.fromString(topicIdOfFollowing);
    }

    public TopicId getTopicIdOfFollowers() throws Exception {
        return TopicId.fromString(topicIdOfFollowers);
    }

    public TopicId getTopicIdOfPosts() throws Exception {
        return TopicId.fromString(topicIdOfPosts);
    }

    public TopicId getTopicIdOfInbox() throws Exception {
        return TopicId.fromString(topicIdOfInbox);
    }
}