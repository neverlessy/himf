package ru.moongl.minecraft.dataheadfix.events;

import com.jeff_media.customblockdata.CustomBlockData;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import ru.moongl.minecraft.dataheadfix.Dataheadfix;
import ru.moongl.minecraft.dataheadfix.ItemSerializer;

import java.io.IOException;

public class HeadFixListener implements Listener {

    private NamespacedKey itemHeadKey;

    @EventHandler
    public void onBlockBreak(final BlockBreakEvent event) {
        if(event.getBlock().getType() == Material.PLAYER_HEAD) {
            event.setDropItems(false);
            final PersistentDataContainer customBlockData = new CustomBlockData(event.getBlock(), Dataheadfix.getInstance());
            if (!customBlockData.has(getItemHeadKey(), PersistentDataType.BYTE_ARRAY)) return;
            try {
                final ItemStack itemStack = ItemSerializer.fromBytes(customBlockData.get(getItemHeadKey(), PersistentDataType.BYTE_ARRAY));
                final ItemMeta itemMeta = itemStack.getItemMeta();
                final ItemStack newItems = new ItemStack(Material.PLAYER_HEAD, 1);
                newItems.setItemMeta(itemMeta);

                if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), newItems);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            customBlockData.remove(getItemHeadKey());
        }
    }

    @EventHandler
    public void onPlayerPlace(BlockPlaceEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if(event.getPlayer().getInventory().getItemInMainHand().getType() == Material.PLAYER_HEAD) {
            setItemHeadKey(new NamespacedKey(Dataheadfix.getInstance(), "k"));
            final ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();
            if (itemStack.getAmount() == 0) return;
            final Block block = event.getBlockPlaced();
            final PersistentDataContainer customBlockData = new CustomBlockData(block, Dataheadfix.getInstance());

            byte[] encodedItem;
            try {
                encodedItem = ItemSerializer.toBytes(itemStack);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            customBlockData.set(getItemHeadKey(), PersistentDataType.BYTE_ARRAY, encodedItem);

        }
    }

    public NamespacedKey getItemHeadKey() {
        return itemHeadKey;
    }

    public void setItemHeadKey(NamespacedKey itemHeadKey) {
        this.itemHeadKey = itemHeadKey;
    }
}
