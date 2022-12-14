package ru.moongl.minecraft.dataheadfix;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class ItemSerializer {

    public static byte[] toBytes(ItemStack itemStack) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             BukkitObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(outputStream)
        ) {
            objectOutputStream.writeObject(itemStack);
            return outputStream.toByteArray();
        }
    }

    public static ItemStack fromBytes(byte[] input) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(input);
             BukkitObjectInputStream objectInputStream = new BukkitObjectInputStream(inputStream)
        ) {
            return (ItemStack) objectInputStream.readObject();
        }
    }

    public static String toBase64(ItemStack itemStack) throws IOException {
        return Base64.getEncoder().encodeToString(toBytes(itemStack));
    }

    public static ItemStack fromBase64(String input) throws IOException, ClassNotFoundException {
        return fromBytes(Base64.getDecoder().decode(input));
    }

    public static byte[] toBytesBlock(Block block) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             BukkitObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(outputStream)
        ) {
            objectOutputStream.writeObject(block);
            return outputStream.toByteArray();
        }
    }

    public static Block fromBlock(byte[] input) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(input);
             BukkitObjectInputStream objectInputStream = new BukkitObjectInputStream(inputStream)
        ) {
            return (Block) objectInputStream.readObject();
        }
    }
}